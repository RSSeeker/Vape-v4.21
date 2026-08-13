package gg.vape.event;

import gg.vape.event.EventListener;
import gg.vape.event.GeneratedEventHandlerInvoker;
import gg.vape.event.IEvent;
import gg.vape.event.ReflectiveEventHandlerInvoker;
import java.lang.reflect.Method;

public interface EventHandlerInvoker {
    public <T extends IEvent> void invoke(T event);

    public static EventHandlerInvoker create(EventListener listener, Class<? extends IEvent> eventType, Method method) {
        try {
            return new GeneratedEventHandlerInvoker(listener, eventType, method);
        }
        catch (Throwable throwable) {
            return new ReflectiveEventHandlerInvoker(listener, eventType, method);
        }
    }
}
