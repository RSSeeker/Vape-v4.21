package gg.vape.config;

import com.google.gson.annotations.SerializedName;
import gg.vape.config.SettingsPayload;

public class GlobalSettingsPayload
implements SettingsPayload {
    @SerializedName(value="firstRun")
    private Boolean firstRun;
    private static boolean initializationGuard;
    @SerializedName(value="cache")
    private Boolean cacheEnabled;

    @Override
    public void initializeDefaults() {
        if (this.firstRun == null) {
            this.firstRun = true;
        }
        if (this.cacheEnabled == null) {
            this.cacheEnabled = false;
        }
    }

    public void setCacheEnabled(Boolean cacheEnabled) {
        this.cacheEnabled = cacheEnabled;
    }

    public static void setInitializationGuard(boolean initializationGuard) {
        GlobalSettingsPayload.initializationGuard = initializationGuard;
    }

    public Boolean isCacheEnabled() {
        return this.cacheEnabled;
    }

    public static boolean isInitializationCheckActive() {
        boolean initialized = GlobalSettingsPayload.isInitializationGuardSet();
        return false;
    }

    public Boolean isFirstRun() {
        return this.firstRun;
    }

    public void setFirstRun(Boolean firstRun) {
        this.firstRun = firstRun;
    }


    public static boolean isInitializationGuardSet() {
        return initializationGuard;
    }

    static {
        if (!GlobalSettingsPayload.isInitializationGuardSet()) {
            GlobalSettingsPayload.setInitializationGuard(true);
        }
    }
}

