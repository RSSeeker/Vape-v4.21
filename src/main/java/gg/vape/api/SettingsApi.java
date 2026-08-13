package gg.vape.api;

import gg.vape.api.ApiAccessTokenProvider;
import gg.vape.api.ApiHttpClient;
import gg.vape.api.ApiResponse;
import gg.vape.config.RefreshableSettingsPayload;
import gg.vape.config.SettingsDataType;
import gg.vape.config.SettingsPayload;

public class SettingsApi {
    private final String baseUrl;

    public <T> T saveSettings(SettingsDataType settingsDataType, SettingsPayload settingsPayload) throws Exception {
        String accessToken = ApiAccessTokenProvider.getAccessToken();
        if (settingsPayload instanceof RefreshableSettingsPayload) {
            ((RefreshableSettingsPayload)settingsPayload).refreshFromCurrentSettings();
        }
        return (T)ApiHttpClient.post(this.baseUrl + "/api/v1/" + accessToken + "/settings/save/" + settingsDataType.getScope().getRouteName(), settingsDataType.getPayloadClass(), settingsPayload);
    }

    public SettingsApi(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    private static Exception preserveException(Exception error) {
        return error;
    }

    public <T> ApiResponse<T> loadSettings(SettingsDataType settingsDataType) throws Exception {
        String accessToken = ApiAccessTokenProvider.getAccessToken();
        ApiResponse response = ApiHttpClient.get(this.baseUrl + "/api/v1/" + accessToken + "/settings/load/" + settingsDataType.getScope().getRouteName(), ApiResponse.class);
        if (response == null) {
            return null;
        }
        if (!response.isSuccessful()) {
            return ApiResponse.failure(response.getError());
        }
        return ApiResponse.success((T)ApiHttpClient.GSON.fromJson(ApiHttpClient.GSON.toJson(response.getData()), settingsDataType.getPayloadClass()));
    }
}
