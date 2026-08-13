package gg.vape.account;

import gg.vape.account.AccountInfoResponse;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public class AccountEntitlements {
    private boolean registered;
    private final boolean licensed;
    private final boolean banned;

    public boolean isLicensed() {
        return this.licensed;
    }

    public boolean isRegistered() {
        return this.registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public boolean isBanned() {
        return this.banned;
    }


    AccountEntitlements(boolean licensed, boolean registered, boolean banned) {
        this.licensed = licensed;
        this.registered = registered;
        this.banned = banned;
    }

    static AccountEntitlements fromResponse(AccountInfoResponse response) {
        return AccountEntitlements.fromNullableResponse(response);
    }

    @Nullable
    @Contract(value="!null -> !null; null -> null")
    private static AccountEntitlements fromNullableResponse(@Nullable AccountInfoResponse response) {
        if (response == null) {
            return null;
        }
        return new AccountEntitlements(response.isLicensed(), response.isRegistered(), response.isBanned());
    }
}

