package gg.vape.manager.client;


public enum OnlineConnectionState {
    OFFLINE,
    OUTDATED_CLIENT,
    OUTDATED_SERVER,
    CONNECTING,
    ONLINE;

    private static final /* synthetic */ OnlineConnectionState[] cachedValues;
    private static String[] obfuscationState;

    public static void setObfuscationState(String[] state) {
        obfuscationState = state;
    }

    public boolean isOfflineState() {
        return this == OFFLINE || this == OUTDATED_CLIENT || this == OUTDATED_SERVER;
    }

    static {
        if (OnlineConnectionState.getObfuscationState() == null) {
            OnlineConnectionState.setObfuscationState(new String[3]);
        }
        String[] obfuscationNames = new String[]{"OFFLINE", "OUTDATED_CLIENT", "ONLINE", "OUTDATED_SERVER", "CONNECTING"};





        cachedValues = new OnlineConnectionState[]{OFFLINE, OUTDATED_CLIENT, OUTDATED_SERVER, CONNECTING, ONLINE};
    }


    public static String[] getObfuscationState() {
        return obfuscationState;
    }
}

