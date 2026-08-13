package gg.vape.account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class LicenseStatusResponse {
    @Expose
    @SerializedName(value="skin")
    private String skin;
    @Expose
    @SerializedName(value="limit")
    private boolean limited;
    @Expose
    @SerializedName(value="info")
    private List<String> info;
    @Expose
    @SerializedName(value="token")
    private String token;
    @Expose
    @SerializedName(value="username")
    private String username;
    @Expose
    @SerializedName(value="password")
    private String password;

    public String getToken() {
        return this.token;
    }

    public String getSkin() {
        return this.skin;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean isLimited() {
        return this.limited;
    }

    public List<String> getInfo() {
        return this.info;
    }
}
