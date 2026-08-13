package gg.vape.event;

import gg.vape.event.EventHandlerInvoker;
import gg.vape.event.EventListener;
import gg.vape.event.GeneratedEventHandlerInvokerMarker;
import gg.vape.event.IEvent;
import gg.vape.mapping.access.GeneratedAccessorFactory;
import java.lang.reflect.Method;

public class GeneratedEventHandlerInvoker
implements EventHandlerInvoker {
    private final Class<? extends IEvent> eventType;
    private final GeneratedEventHandlerInvokerMarker generatedInvoker;
    private final EventListener listener;
    static final boolean ASSERTIONS_DISABLED = !GeneratedEventHandlerInvoker.class.desiredAssertionStatus();

    public GeneratedEventHandlerInvoker(EventListener listener, Class<? extends IEvent> eventType, Method method) throws InstantiationException, IllegalAccessException {
        this.listener = listener;
        this.eventType = eventType;
        Class<? extends GeneratedEventHandlerInvokerMarker> generatedInvokerClass = GeneratedAccessorFactory.N(method.getDeclaringClass(), method);
        if (!ASSERTIONS_DISABLED && generatedInvokerClass == null) {
            throw new AssertionError();
        }
        this.generatedInvoker = generatedInvokerClass.newInstance();
    }

    private static Exception identityException(Exception exception) {
        return exception;
    }

    @Override
    public <T extends IEvent> void invoke(T event) {
        this.generatedInvoker.invoke(this.listener, event);
    }
}
