package gg.vape.account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LicenseInfo {
    @Expose
    @SerializedName(value="username")
    private String username;
    @Expose
    @SerializedName(value="expires")
    private String expires;
    @Expose
    @SerializedName(value="licenseType")
    private String licenseType;
    @Expose
    @SerializedName(value="hasLicense")
    private boolean hasLicense;

    public final String getLicenseType() {
        return this.licenseType;
    }

    public final String getUsername() {
        return this.username;
    }

    public final boolean hasLicense() {
        return this.hasLicense;
    }

    public final String getExpires() {
        return this.expires;
    }
}
