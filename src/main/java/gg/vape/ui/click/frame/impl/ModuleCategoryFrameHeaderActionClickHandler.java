package gg.vape.ui.click.frame.impl;

import gg.vape.module.Mod;
import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.frame.impl.ModuleCategoryFrameHeader;

public class ModuleCategoryFrameHeaderActionClickHandler
implements GuiClickListener {
    final ModuleCategoryFrameHeader d;
    final Mod E;

    public ModuleCategoryFrameHeaderActionClickHandler(ModuleCategoryFrameHeader moduleCategoryFrameHeader, Mod mod) {
        this.d = moduleCategoryFrameHeader;
        this.E = mod;
    }

    @Override
    public void onPrimaryClick() {
        ModuleCategoryFrameHeader.e(this.d).G(this.E);
        ModuleCategoryFrameHeader.e(this.d).W(this.E).expandValueComponents();
        ModuleCategoryFrameHeader.e(this.d).l$src$V$1mibm4x();
    }
}
