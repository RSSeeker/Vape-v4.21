package gg.vape.protocol.packet;

public enum ClientFriendActivityUsersAction {
    ADD,
    CHANGED_WORLD;

    private static final ClientFriendActivityUsersAction[] j;

    static {
        String[] stringArray = new String[]{"CHANGED_WORLD", "ADD"};


        j = new ClientFriendActivityUsersAction[]{ADD, CHANGED_WORLD};
    }

}

