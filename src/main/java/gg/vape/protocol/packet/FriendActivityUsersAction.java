package gg.vape.protocol.packet;

public enum FriendActivityUsersAction {
    ADD,
    CHANGED_WORLD;

    private static final FriendActivityUsersAction[] o;

    static {
        String[] stringArray = new String[]{"CHANGED_WORLD", "ADD"};


        o = new FriendActivityUsersAction[]{ADD, CHANGED_WORLD};
    }

}

