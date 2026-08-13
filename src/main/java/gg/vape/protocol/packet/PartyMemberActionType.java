package gg.vape.protocol.packet;

public enum PartyMemberActionType {
    ADD,
    REMOVE;

    private static final PartyMemberActionType[] g;

    static {
        String[] stringArray = new String[]{"ADD", "REMOVE"};


        g = new PartyMemberActionType[]{ADD, REMOVE};
    }

}

