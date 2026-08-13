package gg.vape.event;

import gg.vape.event.EventBus;
import gg.vape.event.IEvent;

public interface ICancelableEvent
extends IEvent {
    public boolean isCanceled();

    @Override
    default public boolean fire() {
        ICancelableEvent iCancelableEvent = EventBus.getInstance().post(this);
        return !iCancelableEvent.isCanceled();
    }


    public void setCancelled(boolean canceled);
}

