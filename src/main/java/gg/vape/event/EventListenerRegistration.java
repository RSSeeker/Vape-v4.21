package gg.vape.event;

import gg.vape.event.EventHandler;
import gg.vape.event.EventHandlerEntry;
import gg.vape.event.EventHandlerInvoker;
import gg.vape.event.EventListener;
import gg.vape.event.EventPriority;
import gg.vape.event.ICancelableEvent;
import gg.vape.event.IEvent;
import gg.vape.event.impl.EventPreTick;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import org.jetbrains.annotations.UnmodifiableView;

public class EventListenerRegistration {
    private final Map<Class<? extends IEvent>, ArrayList<EventHandlerEntry>> handlersByEventType = new LinkedHashMap<Class<? extends IEvent>, ArrayList<EventHandlerEntry>>();
    private static String obfuscationState;
    private final Predicate<IEvent>[] filters;
    private final EventListener listener;

    public EventListenerRegistration(EventListener listener, Predicate<IEvent> ... filters) {
        this.listener = listener;
        this.filters = filters;
        this.discoverHandlers();
    }

    public EventListener getListener() {
        return this.listener;
    }

    public @UnmodifiableView Collection<Class<? extends IEvent>> getEventTypes() {
        return this.handlersByEventType.keySet();
    }

    public static String getObfuscationState() {
        return obfuscationState;
    }

    private static Exception identityException(Exception exception) {
        return exception;
    }

    static {
        if (EventListenerRegistration.getObfuscationState() != null) {
            EventListenerRegistration.setObfuscationState("HxeBW");
        }
    }

    private void discoverHandlers() {
        try {
            for (Method method : this.listener.getClass().getMethods()) {
                Class<?> parameterType;
                EventHandler annotation = method.getDeclaredAnnotation(EventHandler.class);
                if (annotation == null || method.getParameterCount() != 1 || !IEvent.class.isAssignableFrom(parameterType = method.getParameterTypes()[0])) continue;
                Class<? extends IEvent> eventType = parameterType.asSubclass(IEvent.class);
                EventHandlerInvoker invoker = EventHandlerInvoker.create(this.listener, eventType, method);
                EventHandlerEntry handler = new EventHandlerEntry(this, annotation.priority(), annotation.skipCanceled(), invoker);
                List<EventHandlerEntry> handlers = this.handlersByEventType.computeIfAbsent(eventType, EventListenerRegistration::createHandlerList);
                handlers.add(handler);
            }
            for (List<EventHandlerEntry> handlers : this.handlersByEventType.values()) {
                handlers.sort(Comparator.comparing(EventHandlerEntry::getPriority));
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void setObfuscationState(String state) {
        obfuscationState = state;
    }

    public boolean passesFilters(IEvent event) {
        for (Predicate<IEvent> filter : this.filters) {
            if (filter.test(event)) continue;
            return false;
        }
        return true;
    }

    public <T extends IEvent> T dispatch(T event, EventPriority priority) {
        boolean isPreTickEvent = event instanceof EventPreTick;
        ArrayList<EventHandlerEntry> handlers = this.handlersByEventType.get(event.getClass());
        if (handlers == null || handlers.isEmpty()) {
            return event;
        }
        ICancelableEvent cancelableEvent = event instanceof ICancelableEvent ? (ICancelableEvent)event : null;
        int handlerCount = handlers.size();
        for (int index = 0; index < handlerCount; ++index) {
            EventHandlerEntry handler = handlers.get(index);
            if (!handler.getPriority().equals(priority) || handler.shouldSkipCanceled() && cancelableEvent != null && cancelableEvent.isCanceled()) continue;
            handler.invoke(event);
        }
        return event;
    }

    private static ArrayList<EventHandlerEntry> createHandlerList(Class<? extends IEvent> eventType) {
        return new ArrayList<>();
    }
}
