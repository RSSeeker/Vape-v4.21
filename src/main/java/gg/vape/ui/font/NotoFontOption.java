package gg.vape.ui.font;

import gg.vape.ui.font.FontFamily;
import gg.vape.ui.font.FontOption;

public class NotoFontOption
extends FontOption {
    public NotoFontOption(String string) {
        super(string);
    }

    @Override
    public FontFamily b() {
        return FontFamily.NOTO;
    }
}

