package gg.vape.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gg.vape.config.ConfigJsonUtils;
import java.util.UUID;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public class PublicProfileShareInfo {
    private final boolean uploadAnonymously;
    @Nullable
    private UUID derivedFrom;
    private final boolean shareCodeFriendsOnly;
    private final boolean listedPublicly;
    private String shareCode;
    private long unreadNotifications;

    public void setUnreadNotifications(long unreadNotifications) {
        this.unreadNotifications = unreadNotifications;
    }

    public String getUppercaseShareCode() {
        return this.shareCode.toUpperCase();
    }

    @Nullable
    public UUID getDerivedFrom() {
        return this.derivedFrom;
    }

    public boolean isShareCodeFriendsOnly() {
        return this.shareCodeFriendsOnly;
    }

    @Contract(value="!null -> !null; null -> null")
    public static PublicProfileShareInfo fromJson(@Nullable JsonElement element) {
        if (element == null || element.isJsonNull()) {
            return null;
        }
        JsonObject object = element.getAsJsonObject();
        String derivedFrom = ConfigJsonUtils.getString(object, "derivedFrom");
        return new PublicProfileShareInfo(derivedFrom != null ? UUID.fromString(object.get("derivedFrom").getAsString()) : null, object.get("unreadNotifications").getAsLong(), object.get("shareCode").getAsString(), object.get("listedPublicly").getAsBoolean(), object.get("shareCodeFriendsOnly").getAsBoolean(), object.get("uploadAnonymously").getAsBoolean());
    }

    public boolean isUploadAnonymously() {
        return this.uploadAnonymously;
    }

    public void setDerivedFrom(@Nullable UUID derivedFrom) {
        this.derivedFrom = derivedFrom;
    }

    public boolean isListedPublicly() {
        return this.listedPublicly;
    }

    PublicProfileShareInfo(@Nullable UUID derivedFrom, long unreadNotifications, String shareCode, boolean listedPublicly, boolean shareCodeFriendsOnly, boolean uploadAnonymously) {
        this.derivedFrom = derivedFrom;
        this.unreadNotifications = unreadNotifications;
        this.shareCode = shareCode;
        this.listedPublicly = listedPublicly;
        this.shareCodeFriendsOnly = shareCodeFriendsOnly;
        this.uploadAnonymously = uploadAnonymously;
    }


    public long getUnreadNotifications() {
        return this.unreadNotifications;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }
}

