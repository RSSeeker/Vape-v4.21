package gg.vape.event.impl;

import gg.vape.Vape;
import gg.vape.config.ClientSettings;
import gg.vape.config.Profile;
import gg.vape.event.EventBus;
import gg.vape.event.EventListeners;
import gg.vape.event.impl.EventKeyInputBase;
import gg.vape.input.KeyboardInput;
import gg.vape.manager.client.OnlineConnectionManager;
import gg.vape.module.Mod;
import gg.vape.wrapper.impl.KeyBinding;
import gg.vape.wrapper.impl.Minecraft;

public class EventKeyPress
extends EventKeyInputBase {
    private static final EventListeners EVENT_LISTENERS = new EventListeners();

    public EventKeyPress(int key, boolean down) {
        super(key, down);
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    @Override
    public boolean fire() {
        if (!gg.vape.module.none.ClientSettings.INSTANCE.isInputEnabled() && KeyboardInput.isKeyDown(9) && KeyboardInput.isKeyDown(114)) {
            boolean timingEnabled = EventBus.timingEnabled = !EventBus.timingEnabled;
        }
        if (!gg.vape.module.none.ClientSettings.INSTANCE.isInputEnabled() && this.getKey() != 27) {
            boolean handled = gg.vape.module.none.ClientSettings.INSTANCE.handleSearchShortcut(this);
            return handled;
        }
        if (this.getKey() > 0 && this.isDown() && Minecraft.currentScreen().getObject() == null) {
            for (Profile profile : Vape.INSTANCE.getProfilesManager().getProfiles()) {
                if (!profile.activateIfMatched(this.getKey())) continue;
            }
        }
        for (Mod mod : Vape.INSTANCE.getModManager().collectMods()) {
            mod.u(this);
        }
        OnlineConnectionManager.INSTANCE.getSettings().handleKeyPress(this);
        return super.fire();
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    public boolean isKeybinding(KeyBinding keyBinding) {
        return this.getKey() == ClientSettings.getPlatformKeyCode(keyBinding);
    }

}

