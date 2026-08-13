package gg.vape.ui.click.frame.impl;

import gg.vape.module.Mod;
import gg.vape.ui.click.frame.impl.TextGuiOverlayComponent;
import java.util.Comparator;

public class TextGuiModuleComparator
implements Comparator<Mod> {
    final TextGuiOverlayComponent j;

    @Override
    public int compare(Mod mod, Mod mod2) {
        return this.W(mod, mod2);
    }


    public TextGuiModuleComparator(TextGuiOverlayComponent textGuiOverlayComponent) {
        this.j = textGuiOverlayComponent;
    }

    public int W(Mod mod, Mod mod2) {
        String string = mod.getName();
        String string2 = mod.getSuffixForMode(TextGuiOverlayComponent.F(this.j).getSuffixModeIndex());
        double d = this.j.b.Y(string, TextGuiOverlayComponent.G(this.j)) + this.j.K.Y(string2.isEmpty() ? "" : string2 + " ", TextGuiOverlayComponent.G(this.j));
        String string3 = mod2.getName();
        String string4 = mod2.getSuffixForMode(TextGuiOverlayComponent.F(this.j).getSuffixModeIndex());
        double d2 = this.j.b.Y(string3, TextGuiOverlayComponent.G(this.j)) + this.j.K.Y(string4.isEmpty() ? "" : string4 + " ", TextGuiOverlayComponent.G(this.j));
        return Double.compare(d2, d);
    }
}
