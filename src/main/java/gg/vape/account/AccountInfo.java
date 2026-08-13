package gg.vape.account;

import gg.vape.account.AccountEntitlements;
import gg.vape.account.AccountInfoResponse;
import java.util.Date;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AccountInfo {
    private final long userId;
    private final Date accountCreationDate;
    private final boolean profilesEnabled;
    private final AccountEntitlements entitlements;
    @Nullable
    private String username;


    public void setUsername(@NotNull String username) {
        this.username = username;
    }

    public boolean hasProfilesEnabled() {
        return this.profilesEnabled;
    }

    @Nullable
    public String getUsername() {
        return this.username;
    }

    public long getUserId() {
        return this.userId;
    }

    public AccountEntitlements getEntitlements() {
        return this.entitlements;
    }

    public Date getAccountCreationDate() {
        return this.accountCreationDate;
    }

    @Nullable
    @Contract(value="!null -> !null; null -> null")
    public static AccountInfo fromResponse(@Nullable AccountInfoResponse response) {
        if (response == null) {
            return null;
        }
        return new AccountInfo(response.getUserId(), response.getUsername(), response.getAccountCreationDate(),
                response.hasProfilesEnabled(), AccountEntitlements.fromResponse(response));
    }

    AccountInfo(long userId, @Nullable String username, Date accountCreationDate, boolean profilesEnabled,
                AccountEntitlements entitlements) {
        this.userId = userId;
        this.username = username;
        this.accountCreationDate = accountCreationDate;
        this.profilesEnabled = profilesEnabled;
        this.entitlements = entitlements;
    }
}

