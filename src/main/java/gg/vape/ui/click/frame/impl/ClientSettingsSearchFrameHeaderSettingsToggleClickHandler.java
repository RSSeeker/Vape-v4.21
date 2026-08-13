package gg.vape.ui.click.frame.impl;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.frame.FrameComponent;
import gg.vape.ui.click.frame.impl.ClientSettingsFrame;
import gg.vape.ui.click.frame.impl.ClientSettingsSearchFrame;
import gg.vape.ui.click.frame.impl.ClientSettingsSearchFrameHeader;

public class ClientSettingsSearchFrameHeaderSettingsToggleClickHandler
implements GuiClickListener {
    final ClientSettingsSearchFrameHeader x;

    @Override
    public void onPrimaryClick() {
        ClientSettingsFrame clientSettingsFrame = ClientSettings.getFrame(ClientSettingsFrame.class);
        ClientSettingsSearchFrame clientSettingsSearchFrame = ClientSettings.getFrame(ClientSettingsSearchFrame.class);
        if (clientSettingsFrame == null || clientSettingsSearchFrame == null) {
            return;
        }
        clientSettingsFrame.t(!clientSettingsFrame.V$src$Z$1xhop3l(), false);
        if (clientSettingsFrame.V$src$Z$1xhop3l()) {
            clientSettingsFrame.U();
        }
        clientSettingsFrame.K(clientSettingsSearchFrame.G$src$D$1b2f02a());
        clientSettingsFrame.S(clientSettingsSearchFrame.n());
        clientSettingsFrame.l$src$V$1mibm4x();
        ((FrameComponent)clientSettingsFrame).u();
    }


    public ClientSettingsSearchFrameHeaderSettingsToggleClickHandler(ClientSettingsSearchFrameHeader clientSettingsSearchFrameHeader) {
        this.x = clientSettingsSearchFrameHeader;
    }
}

