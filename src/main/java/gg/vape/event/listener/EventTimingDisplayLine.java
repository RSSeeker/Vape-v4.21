package gg.vape.event.listener;

public class EventTimingDisplayLine {
    private final String text;
    private final long totalMicros;

    public EventTimingDisplayLine(String text, long totalMicros) {
        this.text = text;
        this.totalMicros = totalMicros;
    }

    public long getTotalMicros() {
        return this.totalMicros;
    }

    public String getText() {
        return this.text;
    }
}
