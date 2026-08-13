package gg.vape.event.impl;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.ui.click.component.GuiComponent;

public class EventKeyInputBase
extends Event {
    private static final EventListeners EVENT_LISTENERS = new EventListeners();
    private static GuiComponent[] obfuscationState;
    private final boolean down;
    private final int key;

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    @Override
    public boolean fire() {
        return super.fire();
    }

    public static GuiComponent[] getKeyInputObfuscationState() {
        return obfuscationState;
    }

    public EventKeyInputBase(int key, boolean down) {
        this.key = key;
        this.down = down;
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    public boolean isDown() {
        return this.down;
    }

    public static void setKeyInputObfuscationState(GuiComponent[] state) {
        obfuscationState = state;
    }

    public int getKey() {
        return this.key;
    }

    static {
        EventKeyInputBase.setKeyInputObfuscationState(new GuiComponent[1]);
    }
}
