package gg.vape.ui.click.frame.impl;

import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.frame.impl.ClientSettingsFrame;
import gg.vape.ui.click.frame.impl.ClientSettingsSectionFrame;

public class ClientSettingsFrameActionClickHandler
implements GuiClickListener {
    final ClientSettingsFrame p;
    final String k;

    @Override
    public void onPrimaryClick() {
        ClientSettingsSectionFrame clientSettingsSectionFrame = ClientSettingsFrame.m(this.p, this.k);
        clientSettingsSectionFrame.addChildren((GuiComponent[])ClientSettingsFrame.t(this.p).get(this.k));
    }

    public ClientSettingsFrameActionClickHandler(ClientSettingsFrame sG2, String string) {
        this.p = sG2;
        this.k = string;
    }
}
