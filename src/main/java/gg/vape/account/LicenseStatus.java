package gg.vape.account;

import gg.vape.account.AccountCredentials;

public class LicenseStatus
implements AccountCredentials {
    private final String password;
    private static final String GENERATED_ACCOUNT_PASSWORD;
    private final String username;
    private final boolean generatedAccount;
    private static String sharedStatus;

    public static String getSharedStatus() {
        return sharedStatus;
    }

    public boolean isGeneratedAccount() {
        return this.generatedAccount;
    }

    public static void setSharedStatus(String status) {
        sharedStatus = status;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    static {
        LicenseStatus.setSharedStatus(null);
        GENERATED_ACCOUNT_PASSWORD = "password";
    }

    public LicenseStatus(String token) {
        this.username = token;
        this.password = GENERATED_ACCOUNT_PASSWORD;
        this.generatedAccount = true;
    }

    public LicenseStatus(String username, String password) {
        this.username = username;
        this.password = password;
        this.generatedAccount = false;
    }

    @Override
    public String getPassword() {
        return this.password;
    }
}
