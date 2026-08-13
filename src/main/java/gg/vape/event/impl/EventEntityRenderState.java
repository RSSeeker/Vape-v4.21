package gg.vape.event.impl;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.RenderStateBridge;

public class EventEntityRenderState
extends Event {
    private final Object renderStateHandle;
    private Entity entity;
    private static final EventListeners EVENT_LISTENERS = new EventListeners();
    private RenderStateBridge renderState;
    private final Object entityHandle;


    public EventEntityRenderState(Object entityHandle, Object renderStateHandle) {
        this.entityHandle = entityHandle;
        this.renderStateHandle = renderStateHandle;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    public Entity getEntity() {
        if (this.entity == null) {
            this.entity = new Entity(this.entityHandle);
        }
        return this.entity;
    }

    public RenderStateBridge getEntityRenderState() {
        if (this.renderState == null) {
            this.renderState = new RenderStateBridge(this.renderStateHandle);
        }
        return this.renderState;
    }
}

