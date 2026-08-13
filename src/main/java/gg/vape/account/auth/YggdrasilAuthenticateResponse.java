package gg.vape.account.auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import gg.vape.account.auth.YggdrasilAuthResponse;
import gg.vape.account.auth.YggdrasilProfile;
import java.util.List;

public class YggdrasilAuthenticateResponse
implements YggdrasilAuthResponse {
    @Expose
    @SerializedName(value="errorMessage")
    private String errorMessage;
    @Expose
    @SerializedName(value="selectedProfile")
    private YggdrasilProfile selectedProfile;
    @Expose
    @SerializedName(value="accessToken")
    private String accessToken;
    @Expose
    @SerializedName(value="clientToken")
    private String clientToken;
    private static int opaqueState;
    @Expose
    @SerializedName(value="availableProfiles")
    private List<YggdrasilProfile> availableProfiles;

    public YggdrasilProfile getSelectedProfile() {
        return this.selectedProfile;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public static int opaquePredicate() {
        int state = YggdrasilAuthenticateResponse.getOpaqueState();
        if (state == 0) {
            return 14;
        }
        return 0;
    }

    public static void setOpaqueState(int state) {
        opaqueState = state;
    }

    public List<YggdrasilProfile> getAvailableProfiles() {
        return this.availableProfiles;
    }


    public static int getOpaqueState() {
        return opaqueState;
    }

    public void clearErrorMessage() {
        this.errorMessage = null;
    }

    public String getClientToken() {
        return this.clientToken;
    }

    static {
        if (YggdrasilAuthenticateResponse.opaquePredicate() == 0) {
            YggdrasilAuthenticateResponse.setOpaqueState(1);
        }
    }
}

