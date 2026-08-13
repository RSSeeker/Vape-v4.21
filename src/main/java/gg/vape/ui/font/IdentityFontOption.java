package gg.vape.ui.font;

import gg.vape.ui.font.BaseFontOption;

public class IdentityFontOption
extends BaseFontOption {
    @Override
    public String s(String string) {
        return string;
    }

    public IdentityFontOption(String string) {
        super(string);
    }
}

