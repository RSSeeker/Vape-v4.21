package gg.vape.ui.click.frame.impl.hud;

import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.frame.impl.hud.AnchoredHudModuleConfigFrame;

class HudModuleFrameOpenConfigClickHandler
implements GuiClickListener {
    private final AnchoredHudModuleConfigFrame configFrame;


    @Override
    public void onPrimaryClick() {
        this.configFrame.setVisible(false);
        for (GuiComponent guiComponent : this.configFrame.f()) {
            if (guiComponent == this.configFrame.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc()) continue;
            guiComponent.setVisible(false);
        }
    }

    HudModuleFrameOpenConfigClickHandler(AnchoredHudModuleConfigFrame anchoredHudModuleConfigFrame) {
        this.configFrame = anchoredHudModuleConfigFrame;
    }
}

