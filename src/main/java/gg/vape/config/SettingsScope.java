package gg.vape.config;

import gg.vape.config.GlobalSettingsPayload;
import gg.vape.config.SettingsPayload;
import java.util.Arrays;
import java.util.List;
import org.jetbrains.annotations.Nullable;

public enum SettingsScope {
    GLOBAL("global", GlobalSettingsPayload.class),
    ONLINE("online", null);

    private final String routeName;
    private static final /* synthetic */ SettingsScope[] ENUM_VALUES;
    @Nullable
    private final Class<? extends SettingsPayload> defaultPayloadClass;
    public static final List<SettingsScope> VALUES;
    private static int[] runtimeState;

    public String getRouteName() {
        return this.routeName;
    }

    @Nullable
    public static SettingsScope fromRouteName(String routeName) {
        for (SettingsScope scope : VALUES) {
            if (!scope.getRouteName().equalsIgnoreCase(routeName)) continue;
            return scope;
        }
        return null;
    }

    public static int[] getRuntimeState() {
        return runtimeState;
    }

    private SettingsScope(String routeName, Class<? extends SettingsPayload> defaultPayloadClass) {
        this.routeName = routeName;
        this.defaultPayloadClass = defaultPayloadClass;
    }


    @Nullable
    public Class<? extends SettingsPayload> getDefaultPayloadClass() {
        return this.defaultPayloadClass;
    }

    public static void setRuntimeState(int[] runtimeState) {
        SettingsScope.runtimeState = runtimeState;
    }

    static {
        if (SettingsScope.getRuntimeState() != null) {
            SettingsScope.setRuntimeState(new int[5]);
        }
        String[] serializedNames = new String[]{"ONLINE", "global", "online", "GLOBAL"};


        ENUM_VALUES = new SettingsScope[]{GLOBAL, ONLINE};
        VALUES = Arrays.asList(SettingsScope.values());
    }
}

