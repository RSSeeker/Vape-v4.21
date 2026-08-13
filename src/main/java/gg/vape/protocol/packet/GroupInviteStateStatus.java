package gg.vape.protocol.packet;

public enum GroupInviteStateStatus {
    SUCCESSFULLY_ACCEPTED,
    SUCCESSFULLY_DECLINED,
    GROUP_FULL,
    FAILED;

    private static final GroupInviteStateStatus[] O;

    static {
        String[] stringArray = new String[]{"FAILED", "SUCCESSFULLY_ACCEPTED", "SUCCESSFULLY_DECLINED", "GROUP_FULL"};




        O = new GroupInviteStateStatus[]{SUCCESSFULLY_ACCEPTED, SUCCESSFULLY_DECLINED, GROUP_FULL, FAILED};
    }

}

