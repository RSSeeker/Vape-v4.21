package gg.vape.manager.client;

public enum OnlineDisconnectReason {
    UNKNOWN(true, true),
    LOGGED_IN_FROM_ANOTHER_LOCATION(false, true),
    BANNED(false, false),
    AUTH_FAILED(false, false);

    private final boolean allowsAutomaticReconnect;
    private static final OnlineDisconnectReason[] cachedValues;
    private final boolean reservedFlag;

    private OnlineDisconnectReason(boolean allowsAutomaticReconnect, boolean reservedFlag) {
        this.allowsAutomaticReconnect = allowsAutomaticReconnect;
        this.reservedFlag = reservedFlag;
    }

    public boolean allowsAutomaticReconnect() {
        return this.allowsAutomaticReconnect;
    }

    static {
        String[] obfuscationNames = new String[]{"LOGGED_IN_FROM_ANOTHER_LOCATION", "AUTH_FAILED", "BANNED", "UNKNOWN"};




        cachedValues = new OnlineDisconnectReason[]{UNKNOWN, LOGGED_IN_FROM_ANOTHER_LOCATION, BANNED, AUTH_FAILED};
    }

    public boolean isReservedFlagEnabled() {
        return this.reservedFlag;
    }

}
