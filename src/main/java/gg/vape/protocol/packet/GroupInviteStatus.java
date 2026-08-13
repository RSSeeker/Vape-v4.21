package gg.vape.protocol.packet;

public enum GroupInviteStatus {
    SUCCESS,
    ALREADY_INVITED,
    TOO_MANY_INVITES,
    NOT_ONLINE,
    FAILED;

    private static final GroupInviteStatus[] O;

    static {
        String[] stringArray = new String[]{"NOT_ONLINE", "ALREADY_INVITED", "SUCCESS", "FAILED", "TOO_MANY_INVITES"};





        O = new GroupInviteStatus[]{SUCCESS, ALREADY_INVITED, TOO_MANY_INVITES, NOT_ONLINE, FAILED};
    }

}

