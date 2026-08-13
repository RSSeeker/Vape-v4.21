package gg.vape.ui.click.frame.impl.hud;

import gg.vape.module.none.ClientSettings;
import gg.vape.module.render.hud.HudModule;
import gg.vape.module.render.hud.HudModuleGroup;
import gg.vape.ui.click.component.GuiClickListener;

class HudModuleConfigFrameToggleSelectedModuleClickHandler
implements GuiClickListener {
    private final HudModuleConfigFrame configFrame;

    @Override
    public void onPrimaryClick() {
        HudModule hudModule = this.configFrame.getSelectedModule();
        hudModule.setFavorite(!hudModule.isFavorite());
        if (ClientSettings.getFrame(HudModuleSelectorFrame.class).getSelectedGroup() == HudModuleGroup.FAVORITE) {
            ClientSettings.getFrame(HudModuleSelectorFrame.class).getModuleListPanel().refreshModules();
        }
    }

    HudModuleConfigFrameToggleSelectedModuleClickHandler(HudModuleConfigFrame hudModuleConfigFrame) {
        this.configFrame = hudModuleConfigFrame;
    }

}
