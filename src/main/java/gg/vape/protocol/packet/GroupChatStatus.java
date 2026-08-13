package gg.vape.protocol.packet;

public enum GroupChatStatus {
    SUCCESS,
    UNKNOWN;

    private static final GroupChatStatus[] I;

    static {
        String[] stringArray = new String[]{"SUCCESS", "UNKNOWN"};


        I = new GroupChatStatus[]{SUCCESS, UNKNOWN};
    }
}

