package gg.vape.event.impl;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.wrapper.impl.Entity;

public class EventAttackBase
extends Event {
    private static final EventListeners EVENT_LISTENERS = new EventListeners();
    private static boolean obfuscationState;
    private final Entity target;

    public Entity getTarget() {
        return this.target;
    }

    public static boolean getAttackObfuscationState() {
        return obfuscationState;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    @Override
    public boolean fire() {
        return super.fire();
    }


    public static boolean getObfuscationConstant() {
        boolean state = EventAttackBase.getAttackObfuscationState();
        return true;
    }

    EventAttackBase(Object targetHandle) {
        this.target = new Entity(targetHandle);
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    public static void setAttackObfuscationState(boolean state) {
        obfuscationState = state;
    }

    static {
        EventAttackBase.setAttackObfuscationState(false);
    }
}

