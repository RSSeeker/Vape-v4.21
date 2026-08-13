package gg.vape.event.impl;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;

public class EventMove
extends Event {
    private double z;
    private static final EventListeners EVENT_LISTENERS = new EventListeners();
    private double y;
    private double x;

    public EventMove setY(double y) {
        this.y = y;
        return this;
    }

    public double getZ() {
        return this.z;
    }

    public EventMove setZ(double z) {
        this.z = z;
        return this;
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public double getX() {
        return this.x;
    }

    public EventMove(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public EventMove setX(double x) {
        this.x = x;
        return this;
    }

    public double getY() {
        return this.y;
    }

    @Override
    public boolean fire() {
        return super.fire();
    }
}
