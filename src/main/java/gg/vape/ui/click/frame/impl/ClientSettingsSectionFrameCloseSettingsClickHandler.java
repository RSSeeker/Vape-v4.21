package gg.vape.ui.click.frame.impl;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.frame.impl.ClientSettingsFrame;
import gg.vape.ui.click.frame.impl.ClientSettingsSectionFrame;

class ClientSettingsSectionFrameCloseSettingsClickHandler
implements GuiClickListener {
    final ClientSettingsSectionFrame J;

    ClientSettingsSectionFrameCloseSettingsClickHandler(ClientSettingsSectionFrame clientSettingsSectionFrame) {
        this.J = clientSettingsSectionFrame;
    }

    @Override
    public void onPrimaryClick() {
        ClientSettings.setFrameVisibility(ClientSettingsFrame.class, false);
        this.J.t(false, false);
    }
}
