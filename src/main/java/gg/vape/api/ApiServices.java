package gg.vape.api;

import gg.vape.account.AccountInfoResponse;
import gg.vape.api.ApiAccessTokenProvider;
import gg.vape.api.ApiHttpClient;
import gg.vape.api.ApiResponse;
import gg.vape.api.PublicProfileApi;
import gg.vape.api.SettingsApi;
import gg.vape.api.UserDataApi;
import gg.vape.ui.click.component.GuiComponent;
import java.util.concurrent.CompletableFuture;

public class ApiServices {
    private final String baseUrl;
    private final UserDataApi userDataApi;
    private final PublicProfileApi publicProfileApi;
    private static ApiServices instance;
    private static GuiComponent[] legacyComponentState;
    private final SettingsApi settingsApi;

    public PublicProfileApi getPublicProfileApi() {
        return this.publicProfileApi;
    }

    public CompletableFuture<ApiResponse<AccountInfoResponse>> getAccountInfo() {
        String accessToken = ApiAccessTokenProvider.getAccessToken();
        return CompletableFuture.supplyAsync(() -> this.requestAccountInfo(accessToken));
    }

    public static GuiComponent[] getLegacyComponentState() {
        return legacyComponentState;
    }

    private ApiResponse requestRegistration(String registrationCode) {
        ApiResponse response;
        String accessToken = ApiAccessTokenProvider.getAccessToken();
        try {
            StringBuilder url = new StringBuilder();
            this.getClass();
            response = ApiHttpClient.get(url.append(this.baseUrl).append("/api/v1/").append(accessToken)
                    .append("/register/").append(registrationCode).toString(), ApiResponse.class);
        }
        catch (Exception error) {
            throw new RuntimeException(error);
        }
        return response;
    }

    public static ApiServices getInstance() {
        return instance;
    }

    private ApiResponse requestAccountInfo(String accessToken) {
        try {
            StringBuilder url = new StringBuilder();
            this.getClass();
            return ApiHttpClient.getApiResponse(url.append(this.baseUrl).append("/api/v1/").append(accessToken)
                    .append("/authenticated").toString(), AccountInfoResponse::fromJson);
        }
        catch (Exception error) {
            throw new RuntimeException(error);
        }
    }

    public SettingsApi getSettingsApi() {
        return this.settingsApi;
    }

    public CompletableFuture<ApiResponse<Boolean>> registerOnlineAccount(String registrationCode) {
        return CompletableFuture.supplyAsync(() -> this.requestRegistration(registrationCode));
    }

    public ApiServices() {
        this.baseUrl = ApiServices.resolveBaseUrl();
        this.getClass();
        this.publicProfileApi = new PublicProfileApi(this.baseUrl);
        this.getClass();
        this.settingsApi = new SettingsApi(this.baseUrl);
        this.getClass();
        this.userDataApi = new UserDataApi(this.baseUrl);
    }

    private static String resolveBaseUrl() {
        String configured = System.getenv("VAPE_ONLINE_BASE_URL");
        // Original service: https://online.vape.gg
        return configured == null || configured.trim().isEmpty()
                ? "http://127.0.0.1:8080"
                : configured.replaceAll("/+$", "");
    }

    static {
        ApiServices.setLegacyComponentState(new GuiComponent[4]);
        instance = new ApiServices();
    }

    public static void setLegacyComponentState(GuiComponent[] state) {
        legacyComponentState = state;
    }

    public UserDataApi getUserDataApi() {
        return this.userDataApi;
    }
}
