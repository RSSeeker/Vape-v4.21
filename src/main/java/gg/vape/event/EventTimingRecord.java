package gg.vape.event;

import gg.vape.event.IEvent;

public class EventTimingRecord {
    private final int count;
    private final long startNanos;
    private final Class<? extends IEvent> eventType;
    private final long durationNanos;

    public EventTimingRecord(Class<? extends IEvent> eventType, long startNanos, long durationNanos, int count) {
        this.eventType = eventType;
        this.startNanos = startNanos;
        this.durationNanos = durationNanos;
        this.count = count;
    }

    public int getCount() {
        return this.count;
    }

    public long getStartNanos() {
        return this.startNanos;
    }

    public Class<? extends IEvent> getEventType() {
        return this.eventType;
    }

    public long getDurationNanos() {
        return this.durationNanos;
    }
}
