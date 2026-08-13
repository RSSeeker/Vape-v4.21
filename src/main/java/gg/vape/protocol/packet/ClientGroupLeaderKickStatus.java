package gg.vape.protocol.packet;

public enum ClientGroupLeaderKickStatus {
    SUCCESS,
    FAILED;

    private static final ClientGroupLeaderKickStatus[] D;

    static {
        String[] stringArray = new String[]{"SUCCESS", "FAILED"};


        D = new ClientGroupLeaderKickStatus[]{SUCCESS, FAILED};
    }
}

