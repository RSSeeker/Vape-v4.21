package gg.vape.manager.client;

import gg.vape.api.ApiResponse;
import gg.vape.api.ApiServices;
import gg.vape.config.GlobalSettingsPayload;
import gg.vape.config.SettingsDataType;
import gg.vape.value.BooleanValue;

public class GlobalSettingsController {
    private boolean firstRun = false;
    private GlobalSettingsPayload settings;
    private boolean loadFailed = false;
    private final BooleanValue cacheData = BooleanValue.create(null, "Cache data", false, "Caches data locally to improve load time (%appdata%/.vapeclient)");

    public boolean isFirstRun() {
        return this.firstRun;
    }

    public BooleanValue getCacheData() {
        return this.cacheData;
    }

    public void save() {
        this.settings.setCacheEnabled(this.cacheData.getEffectiveValue());
        this.settings.setFirstRun(false);
        try {
            ApiServices.getInstance().getSettingsApi().saveSettings(SettingsDataType.GLOBAL, this.settings);
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public void load() {
        try {
            ApiResponse apiResponse = ApiServices.getInstance().getSettingsApi().loadSettings(SettingsDataType.GLOBAL);
            this.loadFailed = false;
            if (apiResponse == null || !apiResponse.isSuccessful()) {
                this.settings = new GlobalSettingsPayload();
                this.settings.initializeDefaults();
            } else {
                this.settings = (GlobalSettingsPayload)apiResponse.getData();
            }
        }
        catch (Exception exception) {
            if (this.settings == null) {
                this.settings = new GlobalSettingsPayload();
            }
            this.settings.initializeDefaults();
            this.loadFailed = true;
        }
        this.firstRun = this.settings.isFirstRun();
        this.cacheData.setValue(this.settings.isCacheEnabled());
    }

    private static Exception propagateException(Exception exception) {
        return exception;
    }
}
