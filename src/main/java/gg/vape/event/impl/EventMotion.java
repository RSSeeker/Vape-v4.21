package gg.vape.event.impl;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.wrapper.impl.Entity;

public class EventMotion
extends Event {
    private static Entity savedRidingEntity;
    private static boolean onGround;
    public static Entity player;
    private static final EventListeners EVENT_LISTENERS;
    static boolean alwaysSend;
    private static float rotationYaw;
    private static float rotationPitch;

    public static float getRotationPitch() {
        return rotationPitch;
    }

    public static void setRotationYaw(float yaw) {
        rotationYaw = yaw;
    }

    static boolean storeOnGround(boolean value) {
        onGround = value;
        return onGround;
    }

    public static void setRotationPitch(float pitch) {
        rotationPitch = pitch;
    }

    static float storeRotationYaw(float yaw) {
        rotationYaw = yaw;
        return rotationYaw;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public static float getRotationYaw() {
        return rotationYaw;
    }

    static float storeRotationPitch(float pitch) {
        rotationPitch = pitch;
        return rotationPitch;
    }

    public static boolean shouldAlwaysSend() {
        return alwaysSend;
    }

    public static boolean isOnGround() {
        return onGround;
    }

    static Entity storeRidingEntity(Entity entity) {
        savedRidingEntity = entity;
        return savedRidingEntity;
    }

    public static void setOnGround(boolean value) {
        onGround = value;
    }

    EventMotion(Entity entity) {
        player = entity;
    }

    public static void setShouldAlwaysSend(boolean value) {
        alwaysSend = value;
    }

    @Override
    public boolean fire() {
        return super.fire();
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    static Entity getSavedRidingEntity() {
        return savedRidingEntity;
    }

    static {
        EVENT_LISTENERS = new EventListeners();
    }
}
