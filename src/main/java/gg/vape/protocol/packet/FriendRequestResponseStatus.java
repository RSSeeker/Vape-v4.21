package gg.vape.protocol.packet;

public enum FriendRequestResponseStatus {
    SENT,
    SELF_REQUEST,
    ALREADY_FRIENDS,
    ALREADY_SENT,
    INVALID_USER,
    INVALID;

    private static final FriendRequestResponseStatus[] a;

    static {
        String[] stringArray = new String[]{"INVALID", "ALREADY_SENT", "SENT", "INVALID_USER", "ALREADY_FRIENDS", "SELF_REQUEST"};






        a = new FriendRequestResponseStatus[]{SENT, SELF_REQUEST, ALREADY_FRIENDS, ALREADY_SENT, INVALID_USER, INVALID};
    }

}

