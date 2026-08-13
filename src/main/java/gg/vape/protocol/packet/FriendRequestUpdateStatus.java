package gg.vape.protocol.packet;

public enum FriendRequestUpdateStatus {
    ACCEPTED,
    DECLINED,
    UNKNOWN;

    private static final FriendRequestUpdateStatus[] k;

    static {
        String[] stringArray = new String[]{"DECLINED", "ACCEPTED", "UNKNOWN"};



        k = new FriendRequestUpdateStatus[]{ACCEPTED, DECLINED, UNKNOWN};
    }

}

