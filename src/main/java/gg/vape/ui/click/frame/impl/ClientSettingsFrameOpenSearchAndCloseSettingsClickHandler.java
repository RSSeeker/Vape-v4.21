package gg.vape.ui.click.frame.impl;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.frame.impl.ClientSettingsFrame;
import gg.vape.ui.click.frame.impl.ClientSettingsSearchFrame;

public class ClientSettingsFrameOpenSearchAndCloseSettingsClickHandler
implements GuiClickListener {
    final ClientSettingsFrame b;

    @Override
    public void onPrimaryClick() {
        ClientSettingsSearchFrame clientSettingsSearchFrame = ClientSettings.getFrame(ClientSettingsSearchFrame.class);
        clientSettingsSearchFrame.M(this.b.double_G(), this.b.double_n());
        clientSettingsSearchFrame.t(true, false);
        this.b.t(false, false);
    }

    public ClientSettingsFrameOpenSearchAndCloseSettingsClickHandler(ClientSettingsFrame clientSettingsFrame) {
        this.b = clientSettingsFrame;
    }
}
