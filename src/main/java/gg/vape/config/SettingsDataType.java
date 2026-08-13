package gg.vape.config;

import gg.vape.config.GlobalSettingsPayload;
import gg.vape.config.OnlineSettingsPayload;
import gg.vape.config.SettingsPayload;
import gg.vape.config.SettingsScope;

public enum SettingsDataType {
    GLOBAL(SettingsScope.GLOBAL, GlobalSettingsPayload.class),
    ONLINE(SettingsScope.ONLINE, OnlineSettingsPayload.class);

    private final SettingsScope scope;
    private final Class<? extends SettingsPayload> payloadClass;
    private static final /* synthetic */ SettingsDataType[] ENUM_VALUES;

    static {
        String[] serializedNames = new String[]{"ONLINE", "GLOBAL"};


        ENUM_VALUES = new SettingsDataType[]{GLOBAL, ONLINE};
    }

    public Class<? extends SettingsPayload> getPayloadClass() {
        return this.payloadClass;
    }

    private SettingsDataType(SettingsScope scope, Class<? extends SettingsPayload> payloadClass) {
        this.scope = scope;
        this.payloadClass = payloadClass;
    }

    public SettingsScope getScope() {
        return this.scope;
    }

}
