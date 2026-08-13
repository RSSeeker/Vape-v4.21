package gg.vape.event.impl;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.mapping.MappedClasses;
import gg.vape.wrapper.impl.EntityLivingBase;
import org.jetbrains.annotations.Nullable;

public class EventLivingTravelBase
extends Event {
    private final Object entityHandle;
    @Nullable
    private EntityLivingBase entity;
    private static final EventListeners EVENT_LISTENERS = new EventListeners();

    @Override
    public boolean fire() {
        if (!MappedClasses.z5.isInstance(this.entityHandle)) {
            return false;
        }
        return super.fire();
    }

    public EntityLivingBase getEntity() {
        if (this.entity == null) {
            this.entity = new EntityLivingBase(this.entityHandle);
        }
        return this.entity;
    }


    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public EventLivingTravelBase(Object entityHandle) {
        this.entityHandle = entityHandle;
    }
}

