package gg.vape.protocol.event;

import gg.vape.protocol.ZeusClient;
import gg.vape.protocol.event.OnlineEvent;
import gg.vape.protocol.packet.GroupOption;

public class GroupOptionUpdatedEvent
extends OnlineEvent {
    private static String[] y;
    private final GroupOption c;
    private final Object h;

    public static String[] r() {
        return y;
    }

    public Object U() {
        return this.h;
    }

    public static void s(String[] stringArray) {
        y = stringArray;
    }

    public GroupOptionUpdatedEvent(ZeusClient oZ, GroupOption gk_02, Object object) {
        super(oZ);
        this.c = gk_02;
        this.h = object;
    }

    public GroupOption j() {
        return this.c;
    }

    static {
        if (GroupOptionUpdatedEvent.r() == null) {
            GroupOptionUpdatedEvent.s(new String[4]);
        }
    }
}

