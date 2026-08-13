package gg.vape.friend;

public enum OnlineFriendActivityType {
    AFK("AFK"),
    MOVING("Moving"),
    COMBAT("Combat"),
    BUILDING("Building"),
    DEAD("Dead"),
    NONE("None");

    private static final /* synthetic */ OnlineFriendActivityType[] VALUES_COPY;
    final String displayName;

    private OnlineFriendActivityType(String displayName) {
        this.displayName = displayName;
    }

    static {
        String[] declaredNames = new String[]{"None", "Building", "NONE", "AFK", "Dead", "DEAD", "BUILDING", "Combat", "MOVING", "COMBAT", "Moving", "AFK"};






        VALUES_COPY = new OnlineFriendActivityType[]{AFK, MOVING, COMBAT, BUILDING, DEAD, NONE};
    }

}
