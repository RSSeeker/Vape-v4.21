package gg.vape.protocol.packet;

public enum GroupDeleteStatus {
    SUCCESS,
    NO_PERMISSION,
    NO_GROUP;

    private static final GroupDeleteStatus[] N;

    static {
        String[] stringArray = new String[]{"NO_GROUP", "SUCCESS", "NO_PERMISSION"};



        N = new GroupDeleteStatus[]{SUCCESS, NO_PERMISSION, NO_GROUP};
    }
}

