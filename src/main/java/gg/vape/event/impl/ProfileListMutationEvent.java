package gg.vape.event.impl;

import gg.vape.config.Profile;
import gg.vape.event.EventListeners;
import gg.vape.event.IEvent;
import gg.vape.event.impl.ProfileListMutationAction;

public class ProfileListMutationEvent
implements IEvent {
    private static final EventListeners EVENT_LISTENERS = new EventListeners();
    private final ProfileListMutationAction action;
    private final Profile profile;

    public ProfileListMutationEvent(Profile profile, ProfileListMutationAction action) {
        this.profile = profile;
        this.action = action;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    public Profile getProfile() {
        return this.profile;
    }

    public ProfileListMutationAction getAction() {
        return this.action;
    }
}
