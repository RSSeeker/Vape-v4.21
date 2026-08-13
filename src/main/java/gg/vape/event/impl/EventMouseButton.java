package gg.vape.event.impl;

import gg.vape.Vape;
import gg.vape.config.ClientSettings;
import gg.vape.config.Profile;
import gg.vape.event.EventListeners;
import gg.vape.manager.client.OnlineConnectionManager;
import gg.vape.module.Mod;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.KeyBinding;

public class EventMouseButton
extends EventKeyInputBase {
    public static int RIGHT_BUTTON;
    private static final EventListeners EVENT_LISTENERS;
    public static int LEFT_BUTTON;


    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    public EventMouseButton(int button, boolean down) {
        super(button, down);
    }

    public boolean isKeybinding(KeyBinding keyBinding) {
        int platformKeyCode = ClientSettings.getPlatformKeyCode(keyBinding);
        int mouseButtonCode = ForgeVersion.MC_1_16_5.d() ? platformKeyCode : 100 + platformKeyCode;
        return this.getKey() == mouseButtonCode;
    }

    static {
        LEFT_BUTTON = 0;
        RIGHT_BUTTON = 1;
        EVENT_LISTENERS = new EventListeners();
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public int getButton() {
        return super.getKey();
    }

    @Override
    public boolean fire() {
        int inputCode = -100 + this.getButton();
        if (this.getButtonState()) {
            for (Profile profile : Vape.INSTANCE.getProfilesManager().getProfiles()) {
                if (!profile.activateIfMatched(inputCode)) continue;
                return this.isCanceled();
            }
        }
        for (Mod mod : Vape.INSTANCE.getModManager().collectMods()) {
            if (mod.getBind().getBoundInputs().isEmpty()) continue;
            mod.getBind().handleInput(inputCode, this.getButtonState());
        }
        OnlineConnectionManager.INSTANCE.getSettings().handleMouseButton(this);
        return super.fire();
    }

    public boolean getButtonState() {
        return super.isDown();
    }
}

