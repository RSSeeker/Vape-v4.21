package gg.vape.protocol.packet;

public enum ChatToFriendStatus {
    SUCCESS,
    NOT_FRIENDS,
    OFFLINE;

    private static final ChatToFriendStatus[] f;

    static {
        String[] stringArray = new String[]{"SUCCESS", "OFFLINE", "NOT_FRIENDS"};



        f = new ChatToFriendStatus[]{SUCCESS, NOT_FRIENDS, OFFLINE};
    }

}

