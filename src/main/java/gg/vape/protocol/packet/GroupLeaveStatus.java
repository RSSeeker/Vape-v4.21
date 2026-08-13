package gg.vape.protocol.packet;

public enum GroupLeaveStatus {
    SUCCESS,
    FAILED;

    private static final GroupLeaveStatus[] s;

    static {
        String[] stringArray = new String[]{"FAILED", "SUCCESS"};


        s = new GroupLeaveStatus[]{SUCCESS, FAILED};
    }

}

