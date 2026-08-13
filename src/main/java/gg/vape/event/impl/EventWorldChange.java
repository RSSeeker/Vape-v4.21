package gg.vape.event.impl;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.wrapper.impl.WorldClient;
import org.jetbrains.annotations.Nullable;

public class EventWorldChange
extends Event {
    private static final EventListeners EVENT_LISTENERS = new EventListeners();
    @Nullable
    private final WorldClient newWorld;
    @Nullable
    private final WorldClient previousWorld;

    @Nullable
    public WorldClient getNewWorld() {
        return this.newWorld;
    }

    @Nullable
    public WorldClient getPreviousWorld() {
        return this.previousWorld;
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public EventWorldChange(@Nullable WorldClient previousWorld, @Nullable WorldClient newWorld) {
        this.previousWorld = previousWorld;
        this.newWorld = newWorld;
    }
}
