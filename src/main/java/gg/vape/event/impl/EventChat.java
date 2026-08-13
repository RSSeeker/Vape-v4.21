package gg.vape.event.impl;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.wrapper.impl.ITextComponent;

public class EventChat
extends Event {
    private ITextComponent message;
    private static final EventListeners EVENT_LISTENERS = new EventListeners();

    @Override
    public boolean fire() {
        return super.fire();
    }

    public void setMessage(ITextComponent message) {
        this.message = message;
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    public ITextComponent getMessage() {
        return this.message;
    }

    public EventChat(ITextComponent message) {
        this.message = message;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }
}
