package gg.vape.manager.client;

public enum OnlineAccountState {
    REGISTERED,
    BANNED,
    UNREGISTERED,
    REGISTRATION_OFFLINE,
    CONNECTING;

    private static final /* synthetic */ OnlineAccountState[] cachedValues;

    static {
        String[] obfuscationNames = new String[]{"REGISTRATION_OFFLINE", "CONNECTING", "BANNED", "REGISTERED", "UNREGISTERED"};





        cachedValues = new OnlineAccountState[]{REGISTERED, BANNED, UNREGISTERED, REGISTRATION_OFFLINE, CONNECTING};
    }
}
