package gg.vape.event.impl;

import gg.vape.Vape;
import gg.vape.config.ClientSettings;
import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.render.hud.HudModule;
import gg.vape.wrapper.impl.GuiScreen;

public class EventGuiOpen
extends Event {
    private GuiScreen guiScreen;
    private static final EventListeners EVENT_LISTENERS = new EventListeners();

    @Override
    public boolean fire() {
        if (ClientSettings.pendingSanityReset && Vape.INSTANCE.getClientSettings().sanityCheck.getEffectiveValue().booleanValue()) {
            ClientSettings.pendingSanityReset = false;
            boolean modulesDisabled = false;
            if (this.guiScreen.isInstance(MappedClasses.u5) || this.guiScreen.isInstance(MappedClasses.D6) || this.guiScreen.isInstance(MappedClasses.F_)) {
                for (Mod mod : Vape.INSTANCE.getModManager().collectMods()) {
                    if (mod instanceof HudModule || mod.getCategory() == Category.NONE || !mod.isEnabled()) continue;
                    mod.setEnabled(false);
                    modulesDisabled = true;
                }
            }
            if (modulesDisabled) {
                Vape.INSTANCE.getNotificationManager().showInfo("Sanity Check", "All modules have been disabled!", 5000L);
            }
        }
        return super.fire();
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }


    public GuiScreen getGuiScreen() {
        return this.guiScreen;
    }

    public Object getGuiScreenObject() {
        return this.guiScreen.getObject();
    }

    public EventGuiOpen(Object guiScreenHandle) {
        this.guiScreen = new GuiScreen(guiScreenHandle);
    }

    public void setGuiScreen(GuiScreen guiScreen) {
        this.guiScreen = guiScreen;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }
}

