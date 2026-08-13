package gg.vape.account;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gg.vape.api.ApiHttpClient;
import gg.vape.config.ConfigJsonUtils;
import java.text.ParseException;
import java.util.Date;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public class AccountInfoResponse {
    @Nullable
    private final String username;
    private final boolean banned;
    private final long userId;
    private final Date accountCreationDate;
    private final boolean registered;
    private final boolean licensed;
    private final boolean profilesEnabled;

    public boolean isLicensed() {
        return this.licensed;
    }

    public boolean hasProfilesEnabled() {
        return this.profilesEnabled;
    }

    AccountInfoResponse(long userId, @Nullable String username, @Nullable Date accountCreationDate,
                        boolean licensed, boolean registered, boolean profilesEnabled, boolean banned) {
        this.userId = userId;
        this.username = username;
        this.accountCreationDate = accountCreationDate;
        this.licensed = licensed;
        this.registered = registered;
        this.profilesEnabled = profilesEnabled;
        this.banned = banned;
    }

    @Nullable
    public String getUsername() {
        return this.username;
    }

    private static ParseException preserveParseException(ParseException parseException) {
        return parseException;
    }

    public boolean isRegistered() {
        return this.registered;
    }

    @Nullable
    @Contract(value="!null -> !null; null -> null")
    public static AccountInfoResponse fromJson(@Nullable JsonElement jsonElement) {
        if (jsonElement == null || jsonElement.isJsonNull()) {
            return null;
        }
        JsonObject responseJson = jsonElement.getAsJsonObject();
        try {
            return new AccountInfoResponse(responseJson.get("userId").getAsLong(), ConfigJsonUtils.getString(responseJson, "username"),
                    ApiHttpClient.parseApiDate(ConfigJsonUtils.getString(responseJson, "accountCreation")),
                    responseJson.get("licensed").getAsBoolean(), responseJson.get("registered").getAsBoolean(),
                    responseJson.get("profiles").getAsBoolean(), responseJson.get("banned").getAsBoolean());
        }
        catch (ParseException parseException) {
            throw new RuntimeException(parseException);
        }
    }

    public Date getAccountCreationDate() {
        return this.accountCreationDate;
    }

    public boolean isBanned() {
        return this.banned;
    }

    public long getUserId() {
        return this.userId;
    }
}
