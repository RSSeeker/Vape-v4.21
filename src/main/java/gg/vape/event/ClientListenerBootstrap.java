package gg.vape.event;

import gg.vape.event.EventBus;
import gg.vape.event.EventListener;
import gg.vape.event.forge.ForgeClientChatReceivedEvent;
import gg.vape.event.listener.ClientListenerBootstrapEventListener;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class ClientListenerBootstrap {
    private final List<EventListener> listeners = Arrays.asList(new ForgeClientChatReceivedEvent(), new ClientListenerBootstrapEventListener());

    public void registerListeners() {
        for (EventListener listener : this.listeners) {
            EventBus.getInstance().registerListener(listener, new Predicate[0]);
        }
    }

    public void unregisterListeners() {
        for (EventListener listener : this.listeners) {
            EventBus.getInstance().unregisterListener(listener);
        }
    }
}
