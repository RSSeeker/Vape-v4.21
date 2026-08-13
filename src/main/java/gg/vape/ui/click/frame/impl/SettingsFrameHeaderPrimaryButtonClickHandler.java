package gg.vape.ui.click.frame.impl;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.impl.SettingsFrameHeaderComponent;

class SettingsFrameHeaderPrimaryButtonClickHandler
implements GuiClickListener {
    final SettingsFrameHeaderComponent D;
    final Frame p;

    @Override
    public void onPrimaryClick() {
        if (SettingsFrameHeaderComponent.D(this.D) != null) {
            SettingsFrameHeaderComponent.D(this.D).onPrimaryClick();
        }
        ClientSettings.setFrameVisibility(this.p.getClass(), false);
    }

    SettingsFrameHeaderPrimaryButtonClickHandler(SettingsFrameHeaderComponent settingsFrameHeaderComponent, Frame frame) {
        this.D = settingsFrameHeaderComponent;
        this.p = frame;
    }

}

