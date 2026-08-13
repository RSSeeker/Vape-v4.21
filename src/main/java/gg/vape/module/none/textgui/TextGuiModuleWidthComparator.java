package gg.vape.module.none.textgui;

import gg.vape.Vape;
import gg.vape.module.Mod;
import gg.vape.module.none.TextGuiSettings;
import gg.vape.ui.font.SmoothFontRenderer;
import java.util.Comparator;

public class TextGuiModuleWidthComparator
implements Comparator<Mod> {
    @Override
    public int compare(Mod mod, Mod mod2) {
        SmoothFontRenderer smoothFontRenderer = Vape.INSTANCE.getFontManager().Y();
        return Double.compare(smoothFontRenderer.N(mod2.getName() + mod2.getSuffixForMode(TextGuiSettings.INSTANCE.getSuffixModeIndex())), smoothFontRenderer.N(mod.getName() + mod.getSuffixForMode(TextGuiSettings.INSTANCE.getSuffixModeIndex())));
    }

    public TextGuiModuleWidthComparator() {
    }
}
