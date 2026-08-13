package gg.vape.event.impl;

import gg.vape.config.PublicProfile;
import gg.vape.event.EventListeners;
import gg.vape.event.IEvent;

public class PublicProfileCreatedEvent
implements IEvent {
    private final PublicProfile profile;
    private static final EventListeners EVENT_LISTENERS = new EventListeners();

    public PublicProfile getProfile() {
        return this.profile;
    }

    public PublicProfileCreatedEvent(PublicProfile profile) {
        this.profile = profile;
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }
}
