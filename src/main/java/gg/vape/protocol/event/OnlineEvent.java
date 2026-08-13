package gg.vape.protocol.event;

import gg.vape.protocol.ZeusClient;
import gg.vape.protocol.event.OnlineEventDispatcher;

public abstract class OnlineEvent {
    private static String[] A;
    private final ZeusClient W;

    public void u() {
        OnlineEventDispatcher.O.G(this);
    }

    public static void n(String[] stringArray) {
        A = stringArray;
    }

    public static String[] L() {
        return A;
    }

    public OnlineEvent(ZeusClient oZ) {
        this.W = oZ;
    }

    public ZeusClient e() {
        return this.W;
    }

    static {
        if (OnlineEvent.L() == null) {
            OnlineEvent.n(new String[2]);
        }
    }
}

