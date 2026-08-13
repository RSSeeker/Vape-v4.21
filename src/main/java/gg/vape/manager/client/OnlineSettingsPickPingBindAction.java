package gg.vape.manager.client;

import gg.vape.friend.ping.PingManager;
import gg.vape.manager.client.OnlineSettings;
import gg.vape.unmap.Bendable;

class OnlineSettingsPickPingBindAction
extends Bendable {
    final OnlineSettings settings;

    OnlineSettingsPickPingBindAction(OnlineSettings onlineSettings) {
        this.settings = onlineSettings;
    }

    @Override
    public String getDisplayText() {
        return null;
    }

    @Override
    public void onBindActivated() {
        PingManager.INSTANCE.onEnable();
    }

    @Override
    public boolean isActive() {
        return false;
    }
}
