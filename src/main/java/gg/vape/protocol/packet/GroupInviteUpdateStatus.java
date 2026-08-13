package gg.vape.protocol.packet;

public enum GroupInviteUpdateStatus {
    ACCEPTED,
    SENT,
    DECLINED;

    private static final GroupInviteUpdateStatus[] k;

    static {
        String[] stringArray = new String[]{"DECLINED", "ACCEPTED", "SENT"};



        k = new GroupInviteUpdateStatus[]{ACCEPTED, SENT, DECLINED};
    }

}

