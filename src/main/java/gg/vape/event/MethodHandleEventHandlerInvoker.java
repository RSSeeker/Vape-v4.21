package gg.vape.event;

import gg.vape.event.EventHandlerInvoker;
import gg.vape.event.EventListener;
import gg.vape.event.IEvent;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;

public class MethodHandleEventHandlerInvoker
implements EventHandlerInvoker {
    private final EventListener listener;
    private final MethodHandle methodHandle;
    private final Class<? extends IEvent> eventType;

    @Override
    public <T extends IEvent> void invoke(T event) {
        try {
            this.methodHandle.invoke(this.listener, event);
        }
        catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    public MethodHandleEventHandlerInvoker(EventListener listener, Class<? extends IEvent> eventType, Method method) {
        this.listener = listener;
        this.eventType = eventType;
        try {
            this.methodHandle = MethodHandles.lookup().unreflect(method);
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new RuntimeException(illegalAccessException);
        }
    }
}
