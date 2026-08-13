package gg.vape.protocol.packet;

public enum ClientGroupLeaderPromoteStatus {
    SUCCESS,
    FAILED;

    private static final ClientGroupLeaderPromoteStatus[] F;

    static {
        String[] stringArray = new String[]{"FAILED", "SUCCESS"};


        F = new ClientGroupLeaderPromoteStatus[]{SUCCESS, FAILED};
    }

}

