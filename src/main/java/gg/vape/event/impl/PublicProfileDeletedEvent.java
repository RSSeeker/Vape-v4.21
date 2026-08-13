package gg.vape.event.impl;

import gg.vape.config.PublicProfile;
import gg.vape.event.EventListeners;
import gg.vape.event.IEvent;

public class PublicProfileDeletedEvent
implements IEvent {
    private static final EventListeners EVENT_LISTENERS = new EventListeners();
    private static int obfuscationState;
    private final PublicProfile profile;


    public static void setObfuscationState(int state) {
        obfuscationState = state;
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    public PublicProfileDeletedEvent(PublicProfile profile) {
        this.profile = profile;
    }

    public static int getObfuscationState() {
        return obfuscationState;
    }

    public static int getObfuscationConstant() {
        int state = PublicProfileDeletedEvent.getObfuscationState();
        return 68;
    }

    public PublicProfile getProfile() {
        return this.profile;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    static {
        PublicProfileDeletedEvent.setObfuscationState(0);
    }
}

