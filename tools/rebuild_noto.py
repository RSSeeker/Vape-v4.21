#!/usr/bin/env python3
"""Regenerates resources/noto.ttf from the system Noto Sans SC variable font.

Covers every character used by chinese.properties.txt (values only, mirroring
tools/FontCoverageCheck.java) plus ASCII 32-126 and Latin-1 160-255, then
flattens the variable font to a static instance (removing the variable tables)
so the stb renderer can consume it directly.

Usage: python tools/rebuild_noto.py
"""

import argparse
import re
import sys
from pathlib import Path

from fontTools import subset
from fontTools.fontBuilder import FontBuilder
from fontTools.ttLib import TTFont
from fontTools.varLib import instancer

DEFAULT_SOURCE = r"C:\Windows\Fonts\NotoSansSC-VF.ttf"
WEIGHT = 600  # SemiBold: the previous release used this weight for legibility


def collect_unicodes(properties_path: Path) -> set:
    needed = set(range(32, 127)) | set(range(160, 256))
    content = properties_path.read_text(encoding="utf-8")
    for line in content.splitlines():
        if "=" not in line or line.startswith("#"):
            continue
        _, value = line.split("=", 1)
        for ch in value:
            if ch not in ("\r", "\n"):
                needed.add(ord(ch))
    return needed


def pin_weight(font: TTFont, weight: int) -> TTFont:
    """Pin the variable font to a specific weight (default instance).

    NotoSansSC-VF defaults to Thin (100), which renders illegibly in the GUI;
    the previous release shipped a SemiBold (600) subset. instancer freezes the
    wght axis and removes the variable tables so stb can consume the static
    outline directly.
    """
    if "fvar" in font:
        font = instancer.instantiateVariableFont(font, {"wght": weight},
                                                 inplace=False)
    if "fvar" in font:
        del font["fvar"]
    for table_tag in ("gvar", "avar", "STAT", "cvar", "HVAR", "MVAR", "cvar"):
        if table_tag in font:
            del font[table_tag]
    if "OS/2" in font:
        font["OS/2"].usWeightClass = weight
    return font


def flatten_variable_font(font: TTFont) -> TTFont:
    """Drop any remaining variable tables (defensive; pin_weight handles them)."""
    for table_tag in ("fvar", "gvar", "avar", "STAT", "cvar", "HVAR", "MVAR"):
        if table_tag in font:
            del font[table_tag]
    return font


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--source", default=DEFAULT_SOURCE,
                        help="source Noto Sans SC font (default: system VF)")
    parser.add_argument("--props",
                        default="src/main/resources/resources/chinese.properties.txt",
                        help="translation table to cover")
    parser.add_argument("--output",
                        default="src/main/resources/resources/noto.ttf",
                        help="output font path")
    args = parser.parse_args()

    source = Path(args.source)
    props = Path(args.props)
    output = Path(args.output)
    if not source.is_file():
        print(f"source font not found: {source}", file=sys.stderr)
        return 2
    if not props.is_file():
        print(f"translations not found: {props}", file=sys.stderr)
        return 2

    unicodes = collect_unicodes(props)
    print(f"covering {len(unicodes)} codepoints")

    font = TTFont(str(source))
    font = pin_weight(font, WEIGHT)

    options = subset.Options()
    options.flavor = None
    options.layout_features = []
    options.name_IDs = ["*"]
    options.name_legacy = True
    options.recalc_bounds = True
    options.recalc_timestamp = False
    options.drop_tables = [
        "FFTM", "GSUB", "GPOS", "GDEF", "kern", "morx", "kerx",
    ]
    options.notdef_glyph = True
    options.notdef_outline = True
    options.recommended_glyphs = False

    subber = subset.Subsetter(options=options)
    subber.populate(unicodes=unicodes)
    subber.subset(font)

    flatten_variable_font(font)
    font.save(str(output))
    print(f"wrote {output} ({output.stat().st_size} bytes)")
    return 0


if __name__ == "__main__":
    sys.exit(main())
