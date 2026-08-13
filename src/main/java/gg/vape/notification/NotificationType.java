package gg.vape.notification;

public enum NotificationType {
    INFO("Info", -1),
    WARNING("Warning", -1277652),
    ALERT("Alert", -380360),
    FRIENDS_GENERAL("Info", -1, NotificationGroup.FRIENDS),
    FRIENDS_NEW_REQUEST("friend_request", -1, NotificationGroup.FRIENDS),
    FRIENDS_NEW_CHAT("chat", -1, NotificationGroup.FRIENDS),
    FRIENDS_ONLINE("online", -1, NotificationGroup.FRIENDS),
    FRIENDS_PARTY_GENERAL("group", -1, NotificationGroup.FRIENDS),
    FRIENDS_PARTY_INVITE("group", -1, NotificationGroup.FRIENDS);

    private final String displayName;
    private NotificationGroup group = NotificationGroup.NONE;
    private final String iconResource;
    private final int color;
    private static boolean controlFlowFlag;

    NotificationType(String displayName, int color) {
        this(displayName, color, NotificationGroup.NONE);
    }

    NotificationType(String displayName, int color, NotificationGroup group) {
        this.displayName = displayName;
        this.color = color;
        this.iconResource = "noti_" + displayName.toLowerCase();
        this.group = group;
    }

    public NotificationGroup getGroup() {
        return this.group;
    }

    public static boolean getControlFlowFlag() {
        return controlFlowFlag;
    }

    public static void setControlFlowFlag(boolean value) {
        controlFlowFlag = value;
    }

    public String getIconResource() {
        return this.iconResource;
    }

    public int getColor() {
        return this.color;
    }

    public static boolean initializeControlFlowFlag() {
        NotificationType.getControlFlowFlag();
        return true;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    static {
        NotificationType.setControlFlowFlag(false);
    }
}
