package gg.vape.event;

import gg.vape.event.EventHandlerInvoker;
import gg.vape.event.EventListenerRegistration;
import gg.vape.event.EventPriority;
import gg.vape.event.IEvent;

public class EventHandlerEntry {
    private final EventPriority priority;
    private final EventListenerRegistration registration;
    private final boolean skipCanceled;
    private final EventHandlerInvoker invoker;

    public EventHandlerInvoker getInvoker() {
        return this.invoker;
    }

    public EventListenerRegistration getRegistration() {
        return this.registration;
    }

    public boolean shouldSkipCanceled() {
        return this.skipCanceled;
    }

    public EventPriority getPriority() {
        return this.priority;
    }

    public EventHandlerEntry(EventListenerRegistration registration, EventPriority priority, boolean skipCanceled, EventHandlerInvoker invoker) {
        this.registration = registration;
        this.priority = priority;
        this.skipCanceled = skipCanceled;
        this.invoker = invoker;
    }

    public <T extends IEvent> T invoke(T event) {
        this.invoker.invoke(event);
        return event;
    }
}
