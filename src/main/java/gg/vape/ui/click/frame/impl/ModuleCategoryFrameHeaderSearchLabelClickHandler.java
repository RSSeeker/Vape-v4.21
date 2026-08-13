package gg.vape.ui.click.frame.impl;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.frame.impl.ModuleCategoryFrameHeader;

public class ModuleCategoryFrameHeaderSearchLabelClickHandler
implements GuiClickListener {
    final ModuleCategoryFrameHeader r;


    @Override
    public void onPrimaryClick() {
        boolean bl;
        ClientSettings.moduleSearchActive = bl = !ClientSettings.moduleSearchActive;
        ClientSettings.refreshModuleCategoryHeaders();
    }

    public ModuleCategoryFrameHeaderSearchLabelClickHandler(ModuleCategoryFrameHeader moduleCategoryFrameHeader) {
        this.r = moduleCategoryFrameHeader;
    }
}

