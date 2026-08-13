package gg.vape.config;

import gg.vape.Vape;
import gg.vape.config.ClientSettings;
import gg.vape.unmap.BindChangeListener;

class ClientSettingsBindChangeListener
implements BindChangeListener {
    final ClientSettings clientSettings;

    ClientSettingsBindChangeListener(ClientSettings clientSettings) {
        this.clientSettings = clientSettings;
    }

    @Override
    public void onBindChanged() {
        Vape.INSTANCE.getFriendManager().toggleCrosshairTarget();
    }
}
