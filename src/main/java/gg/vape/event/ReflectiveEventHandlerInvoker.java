package gg.vape.event;

import gg.vape.event.EventHandlerInvoker;
import gg.vape.event.EventListener;
import gg.vape.event.IEvent;
import gg.vape.mapping.MappingMethod;
import java.lang.reflect.Method;

public class ReflectiveEventHandlerInvoker
implements EventHandlerInvoker {
    private final MappingMethod handlerMethod;
    private final EventListener listener;
    private final Class<? extends IEvent> eventType;
    private static String[] obfuscationState;

    public static String[] getObfuscationState() {
        return obfuscationState;
    }

    public static void setObfuscationState(String[] state) {
        obfuscationState = state;
    }

    static {
        if (ReflectiveEventHandlerInvoker.getObfuscationState() != null) {
            ReflectiveEventHandlerInvoker.setObfuscationState(new String[5]);
        }
    }

    @Override
    public <T extends IEvent> void invoke(T event) {
        this.handlerMethod.invokeVoid(this.listener, event);
    }

    public ReflectiveEventHandlerInvoker(EventListener listener, Class<? extends IEvent> eventType, Method method) {
        this.listener = listener;
        this.eventType = eventType;
        MappingMethod mappingMethod = new MappingMethod(null, listener.getClass(), method.getName(), false, false, false, Void.TYPE, eventType);
        this.handlerMethod = mappingMethod.register();
    }
}
