package gg.vape.event;

import gg.vape.event.EventListenerRegistration;

public class EventListenerTiming {
    private final long startNanos;
    private long durationNanos;
    private final EventListenerRegistration registration;

    public EventListenerRegistration getRegistration() {
        return this.registration;
    }

    public EventListenerTiming(EventListenerRegistration registration) {
        this.registration = registration;
        this.startNanos = System.nanoTime();
    }

    public void finish() {
        this.durationNanos = System.nanoTime() - this.startNanos;
    }

    public long getDurationNanos() {
        return this.durationNanos;
    }
}
