package gg.vape.event;

import gg.vape.ui.click.component.GuiComponent;
import java.util.concurrent.atomic.AtomicInteger;

public class EventListeners {
    private final AtomicInteger listenerCount = new AtomicInteger(0);
    private static GuiComponent[] obfuscationState;

    public static GuiComponent[] getObfuscationState() {
        return obfuscationState;
    }

    public void incrementListenerCount() {
        this.listenerCount.incrementAndGet();
    }

    public boolean hasListeners() {
        return this.listenerCount.get() > 0;
    }

    public static void setObfuscationState(GuiComponent[] state) {
        obfuscationState = state;
    }


    public void decrementListenerCount() {
        this.listenerCount.decrementAndGet();
    }

    static {
        if (EventListeners.getObfuscationState() != null) {
            EventListeners.setObfuscationState(new GuiComponent[2]);
        }
    }
}

