package gg.vape.protocol.event;

import gg.vape.friend.GroupUserModel;
import gg.vape.protocol.ZeusClient;
import gg.vape.protocol.event.OnlineEvent;
import gg.vape.protocol.event.PartyMemberAction;
import gg.vape.protocol.packet.PartyMemberActionType;

public class PartyMemberUpdateEvent
extends OnlineEvent {
    private final GroupUserModel V;
    private final PartyMemberAction R;
    private static String[] o;

    public static String[] w() {
        return o;
    }

    public GroupUserModel S() {
        return this.V;
    }

    public PartyMemberAction q() {
        return this.R;
    }

    public static void h(String[] stringArray) {
        o = stringArray;
    }

    public PartyMemberUpdateEvent(ZeusClient oZ, GroupUserModel groupUserModel, PartyMemberActionType eN) {
        super(oZ);
        this.V = groupUserModel;
        this.R = PartyMemberAction.J(eN);
    }

    static {
        if (PartyMemberUpdateEvent.w() != null) {
            PartyMemberUpdateEvent.h(new String[4]);
        }
    }
}

