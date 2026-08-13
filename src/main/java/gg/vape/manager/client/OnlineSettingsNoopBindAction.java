package gg.vape.manager.client;

import gg.vape.manager.client.OnlineSettings;
import gg.vape.unmap.Bendable;

class OnlineSettingsNoopBindAction
extends Bendable {
    final OnlineSettings settings;

    OnlineSettingsNoopBindAction(OnlineSettings onlineSettings) {
        this.settings = onlineSettings;
    }

    @Override
    public void onBindActivated() {
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public String getDisplayText() {
        return null;
    }
}
