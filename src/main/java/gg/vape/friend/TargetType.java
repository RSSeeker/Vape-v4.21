package gg.vape.friend;

public enum TargetType {
    FRIEND,
    ENEMY;

    private static final TargetType[] VALUES_COPY;

    static {
        String[] declaredNames = new String[]{"ENEMY", "FRIEND"};


        VALUES_COPY = new TargetType[]{FRIEND, ENEMY};
    }

}
