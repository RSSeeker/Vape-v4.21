package gg.vape.event;

import gg.vape.event.EventListenerTiming;
import gg.vape.event.IEvent;
import java.util.ArrayList;
import java.util.List;

public class EventDispatchTrace {
    private final List<EventListenerTiming> listenerTimings = new ArrayList<EventListenerTiming>();
    private final Class<? extends IEvent> eventType;
    private final long startNanos;
    private long endNanos;

    public EventDispatchTrace(Class<? extends IEvent> eventType) {
        this.eventType = eventType;
        this.startNanos = System.nanoTime();
    }

    public long getDurationNanos() {
        return this.endNanos - this.startNanos;
    }

    public List<EventListenerTiming> getListenerTimings() {
        return this.listenerTimings;
    }

    public long getStartNanos() {
        return this.startNanos;
    }

    public void finish() {
        this.endNanos = System.nanoTime();
    }

    public void addListenerTiming(EventListenerTiming eventListenerTiming) {
        this.listenerTimings.add(eventListenerTiming);
    }

    public Class<? extends IEvent> getEventType() {
        return this.eventType;
    }
}
