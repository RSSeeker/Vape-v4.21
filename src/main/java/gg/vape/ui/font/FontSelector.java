package gg.vape.ui.font;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.font.BaseFontOption;
import gg.vape.ui.font.FontOption;
import gg.vape.ui.font.FontOptionVariantA;
import gg.vape.ui.font.FontOptionVariantB;
import gg.vape.ui.font.FontOptionVariantC;
import gg.vape.ui.font.IdentityFontOption;
import gg.vape.ui.font.NotoFontOption;

public class FontSelector {
    private FontOption r = j;
    public static final FontOption S;
    public static final FontOption a;
    public static final FontOption P;
    public static final FontOption c;
    public static final BaseFontOption j;

    public FontOption W() {
        return this.r;
    }

    public void N(FontOption fontOption) {
        this.r = fontOption;
        ClientSettings.INSTANCE.requestFrameLayoutRefresh();
    }

    static {
        String[] stringArray = new String[]{"French", "English", "Portuguese", "Spanish", "Chinese"};
        j = new IdentityFontOption(stringArray[1]);
        S = new FontOptionVariantC(stringArray[3]);
        c = new NotoFontOption(stringArray[4]);
        a = new FontOptionVariantA(stringArray[2]);
        P = new FontOptionVariantB(stringArray[0]);
    }

    public FontSelector() {
        S.g(j);
        c.g(j);
        a.g(j);
        P.g(j);
    }
}

