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
        // ClientSettings may not be initialized yet when the language is
        // applied early during Vape.initializeManagers(); skip the layout
        // refresh in that case.
        if (ClientSettings.INSTANCE != null) {
            ClientSettings.INSTANCE.requestFrameLayoutRefresh();
        }
    }

    static {
        String[] stringArray = new String[]{"English", "Chinese"};
        j = new IdentityFontOption(stringArray[0]);
        S = new FontOptionVariantC("Spanish");
        c = new NotoFontOption(stringArray[1]);
        a = new FontOptionVariantA("Portuguese");
        P = new FontOptionVariantB("French");
    }

    public FontSelector() {
        S.g(j);
        c.g(j);
        a.g(j);
        P.g(j);
    }
}

