package gg.vape.event.impl;

public class DelayedPacketSendEntry {
    private boolean offsetApplied;
    private final EventPacketSend event;

    public DelayedPacketSendEntry(EventPacketSend event) {
        this.event = event;
    }

    public void setOffsetApplied(boolean offsetApplied) {
        this.offsetApplied = offsetApplied;
    }

    public EventPacketSend getEvent() {
        return this.event;
    }

    public boolean isOffsetApplied() {
        return this.offsetApplied;
    }
}

