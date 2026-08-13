package gg.vape.protocol.event;

import gg.vape.protocol.packet.PartyMemberActionType;
import java.util.Arrays;
import java.util.List;

public enum PartyMemberAction {
    ADD(PartyMemberActionType.ADD),
    REMOVE(PartyMemberActionType.REMOVE);

    private static final List<PartyMemberAction> M;
    private final PartyMemberActionType I;
    private static final PartyMemberAction[] p;

    public PartyMemberActionType c() {
        return this.I;
    }

    public static PartyMemberAction J(PartyMemberActionType partyMemberActionType) {
        for (PartyMemberAction partyMemberAction : M) {
            if (partyMemberAction.I != partyMemberActionType) continue;
            return partyMemberAction;
        }
        return null;
    }


    static {
        String[] stringArray = new String[]{"ADD", "REMOVE"};


        p = new PartyMemberAction[]{ADD, REMOVE};
        M = Arrays.asList(PartyMemberAction.values());
    }

    private PartyMemberAction(PartyMemberActionType partyMemberActionType) {
        this.I = partyMemberActionType;
    }

}

