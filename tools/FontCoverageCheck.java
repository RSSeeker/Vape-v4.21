import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Verifies that resources/noto.ttf covers every character used by the
 * chinese.properties translation table. Parses the TTF cmap table directly
 * (format 0/4/6/12) with plain JDK code, so it can run without the game's
 * stb renderer and without third-party font libraries.
 *
 * Usage: java FontCoverageCheck <noto.ttf> <chinese.properties>
 * Exit 0 when covered; exit 1 and print missing codepoints otherwise.
 */
public final class FontCoverageCheck {
    private static final int ASCII_START = 32;
    private static final int ASCII_END = 126;
    private static final int LATIN1_START = 160;
    private static final int LATIN1_END = 255;

    private FontCoverageCheck() {
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("usage: FontCoverageCheck <noto.ttf> <chinese.properties>");
            System.exit(2);
            return;
        }
        Path fontPath = Paths.get(args[0]);
        Path propsPath = Paths.get(args[1]);
        if (!Files.isRegularFile(fontPath)) {
            System.err.println("font not found: " + fontPath.toAbsolutePath());
            System.exit(2);
            return;
        }
        if (!Files.isRegularFile(propsPath)) {
            System.err.println("translations not found: " + propsPath.toAbsolutePath());
            System.exit(2);
            return;
        }

        Set<Integer> needed = collectNeededCharacters(propsPath);
        Set<Integer> covered = parseCmap(Files.readAllBytes(fontPath));

        Set<Integer> missing = new TreeSet<>();
        for (Integer cp : needed) {
            if (!covered.contains(cp)) {
                missing.add(cp);
            }
        }

        if (missing.isEmpty()) {
            System.out.println("FONT COVERAGE OK: " + needed.size()
                    + " translation characters all present in " + fontPath.getFileName());
            System.exit(0);
        }

        StringBuilder chars = new StringBuilder();
        StringBuilder codepoints = new StringBuilder();
        for (Integer cp : missing) {
            chars.appendCodePoint(cp);
            codepoints.append(String.format("U+%04X ", cp));
        }
        System.out.println("FONT COVERAGE FAILED: " + missing.size() + " character(s) missing:");
        System.out.println("  chars: " + chars);
        System.out.println("  codepoints: " + codepoints.toString().trim());
        System.out.println();
        System.out.println("The translation table uses characters absent from noto.ttf.");
        System.out.println("Re-generate the font subset from the latest chinese.properties,");
        System.out.println("e.g. with fontTools against a full Noto Sans SC source:");
        System.out.println("  pyftsubset NotoSansSC-VF.ttf --unicodes=... --output-file=noto.ttf");
        System.exit(1);
    }

    private static Set<Integer> collectNeededCharacters(Path propsPath) throws Exception {
        Set<Integer> needed = new HashSet<>();
        for (int cp = ASCII_START; cp <= ASCII_END; cp++) {
            needed.add(cp);
        }
        for (int cp = LATIN1_START; cp <= LATIN1_END; cp++) {
            needed.add(cp);
        }
        String content = new String(Files.readAllBytes(propsPath), StandardCharsets.UTF_8);
        content.codePoints().forEach(cp -> {
            if (cp != '\r' && cp != '\n' && cp != 0) {
                needed.add(cp);
            }
        });
        return needed;
    }

    /** Parses all cmap subtables (formats 0, 4, 6, 12) into a coverage set. */
    private static Set<Integer> parseCmap(byte[] font) {
        Set<Integer> covered = new HashSet<>();
        int numTables = u16(font, 4);
        int cmapOffset = -1;
        for (int i = 0; i < numTables; i++) {
            int rec = 12 + i * 16;
            if (font.length >= rec + 4
                    && font[rec] == 'c' && font[rec + 1] == 'm'
                    && font[rec + 2] == 'a' && font[rec + 3] == 'p') {
                cmapOffset = u32(font, rec + 8);
                break;
            }
        }
        if (cmapOffset < 0 || cmapOffset >= font.length) {
            return covered;
        }
        int numSubtables = u16(font, cmapOffset + 2);
        for (int i = 0; i < numSubtables; i++) {
            int rec = cmapOffset + 4 + i * 8;
            if (rec + 6 > font.length) {
                break;
            }
            int subtableOffset = u32(font, rec + 4);
            int base = cmapOffset + subtableOffset;
            if (base < 0 || base + 2 > font.length) {
                continue;
            }
            int format = u16(font, base);
            switch (format) {
                case 0:
                    parseFormat0(font, base, covered);
                    break;
                case 4:
                    parseFormat4(font, base, covered);
                    break;
                case 6:
                    parseFormat6(font, base, covered);
                    break;
                case 12:
                case 13:
                    parseFormat12(font, base, covered);
                    break;
                default:
                    break;
            }
        }
        return covered;
    }

    private static void parseFormat0(byte[] font, int base, Set<Integer> covered) {
        int length = u16(font, base + 2);
        if (length < 262) {
            return;
        }
        for (int i = 0; i < 256; i++) {
            int glyph = font[base + 6 + i] & 0xff;
            if (glyph != 0) {
                covered.add(i);
            }
        }
    }

    private static void parseFormat4(byte[] font, int base, Set<Integer> covered) {
        int segCountX2 = u16(font, base + 6);
        int segCount = segCountX2 / 2;
        int endCodeBase = base + 14;
        int startCodeBase = endCodeBase + segCountX2 + 2;
        for (int seg = 0; seg < segCount; seg++) {
            int start = u16(font, startCodeBase + seg * 2);
            int end = u16(font, endCodeBase + seg * 2);
            if (end == 0xffff) {
                end = 0x10ffff;
            }
            for (int cp = start; cp <= end; cp++) {
                covered.add(cp);
            }
        }
    }

    private static void parseFormat6(byte[] font, int base, Set<Integer> covered) {
        int first = u16(font, base + 6);
        int count = u16(font, base + 8);
        for (int i = 0; i < count; i++) {
            int glyph = u16(font, base + 10 + i * 2);
            if (glyph != 0) {
                covered.add(first + i);
            }
        }
    }

    private static void parseFormat12(byte[] font, int base, Set<Integer> covered) {
        int nGroups = u32(font, base + 12);
        for (int g = 0; g < nGroups; g++) {
            int groupBase = base + 16 + g * 12;
            long start = u32(font, groupBase);
            long end = u32(font, groupBase + 4);
            for (long cp = start; cp <= end && cp <= 0x10ffff; cp++) {
                covered.add((int) cp);
            }
        }
    }

    private static int u16(byte[] data, int offset) {
        if (offset + 1 >= data.length) {
            return 0;
        }
        return ((data[offset] & 0xff) << 8) | (data[offset + 1] & 0xff);
    }

    private static int u32(byte[] data, int offset) {
        if (offset + 3 >= data.length) {
            return 0;
        }
        return ((data[offset] & 0xff) << 24)
                | ((data[offset + 1] & 0xff) << 16)
                | ((data[offset + 2] & 0xff) << 8)
                | (data[offset + 3] & 0xff);
    }
}
