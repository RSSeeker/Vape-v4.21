package gg.vape.ui.click.frame.impl.hud;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.component.SimpleTextLabelComponent;
import gg.vape.ui.click.frame.impl.hud.HudModuleConfigFrame;
import gg.vape.ui.click.frame.impl.hud.HudModuleSelectorHeaderComponent;

class HudModuleSelectorOpenConfigFrameClickHandler
implements GuiClickListener {
    private final HudModuleSelectorHeaderComponent header;

    HudModuleSelectorOpenConfigFrameClickHandler(HudModuleSelectorHeaderComponent hudModuleSelectorHeaderComponent) {
        this.header = hudModuleSelectorHeaderComponent;
    }

    @Override
    public void onPrimaryClick() {
        HudModuleConfigFrame hudModuleConfigFrame = ClientSettings.getFrame(HudModuleConfigFrame.class);
        if (hudModuleConfigFrame == null) {
            return;
        }
        hudModuleConfigFrame.setTitle("Settings");
        hudModuleConfigFrame.removeMarkedChildren();
        hudModuleConfigFrame.h(new SimpleTextLabelComponent("No settings yet"), new Object[0]);
        this.header.getSettingsButton().setVisible(true);
        hudModuleConfigFrame.setVisible(true);
        hudModuleConfigFrame.U();
        hudModuleConfigFrame.t(hudModuleConfigFrame.L());
        hudModuleConfigFrame.beginOpening();
        hudModuleConfigFrame.l$src$V$1mibm4x();
    }

}

