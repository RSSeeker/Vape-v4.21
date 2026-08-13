package gg.vape.protocol.packet;

public enum GroupCreateStatus {
    SUCCESS,
    ALREADY_IN_GROUP;

    private static final GroupCreateStatus[] L;

    static {
        String[] stringArray = new String[]{"SUCCESS", "ALREADY_IN_GROUP"};


        L = new GroupCreateStatus[]{SUCCESS, ALREADY_IN_GROUP};
    }

}

