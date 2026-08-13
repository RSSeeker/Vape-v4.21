package gg.vape.ui.click.frame.impl.hud;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.frame.impl.hud.HudModuleOverviewFrame;
import gg.vape.ui.click.frame.impl.hud.HudModuleSelectorFrame;

class HudModuleSelectorOpenOverviewClickHandler
implements GuiClickListener {
    @Override
    public void onPrimaryClick() {
        HudModuleOverviewFrame hudModuleOverviewFrame = ClientSettings.getFrame(HudModuleOverviewFrame.class);
        HudModuleSelectorFrame hudModuleSelectorFrame = ClientSettings.getFrame(HudModuleSelectorFrame.class);
        if (hudModuleOverviewFrame == null || hudModuleSelectorFrame == null) {
            return;
        }
        hudModuleOverviewFrame.setVisible(true);
        hudModuleSelectorFrame.setVisible(false);
        hudModuleOverviewFrame.l$src$Z$193vdc5();
        HudModuleSelectorFrame.overviewVisible = true;
        hudModuleOverviewFrame.getModuleList().refreshFavorites();
    }

    HudModuleSelectorOpenOverviewClickHandler() {
    }

}

