package gg.vape.account;

import gg.vape.account.AccountCredentials;

public class MutableAccountCredentials
implements AccountCredentials {
    private String password;
    private String profileName;
    private String profileId;
    private String username;

    public String getProfileName() {
        return this.profileName;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public MutableAccountCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public String getProfileId() {
        return this.profileId;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }
}
