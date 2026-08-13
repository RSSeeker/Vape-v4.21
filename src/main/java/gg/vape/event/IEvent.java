package gg.vape.event;

import gg.vape.event.EventBus;
import gg.vape.event.EventListeners;

public interface IEvent {
    public EventListeners getListeners();

    default public boolean fire() {
        EventBus.getInstance().post(this);
        return true;
    }
}

