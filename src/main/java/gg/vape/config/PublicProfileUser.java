package gg.vape.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gg.vape.config.ConfigJsonUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public class PublicProfileUser {
    private final String username;
    private final long userId;
    private static int[] runtimeState;

    public static int[] getRuntimeState() {
        return runtimeState;
    }

    public static void setRuntimeState(int[] runtimeState) {
        PublicProfileUser.runtimeState = runtimeState;
    }

    public String getUsername() {
        return this.username;
    }

    static {
        PublicProfileUser.setRuntimeState(null);
    }

    @Nullable
    @Contract(value="null -> null")
    public static PublicProfileUser fromJson(@Nullable JsonElement element) {
        if (element == null || element.isJsonNull()) {
            return null;
        }
        JsonObject object = element.getAsJsonObject();
        PublicProfileUser user = new PublicProfileUser(object.get("userId").getAsLong(), ConfigJsonUtils.getString(object, "username"));
        return user.getUsername() != null ? user : null;
    }

    PublicProfileUser(long userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public String toString() {
        return "SimpleOnlineUser{userId=" + this.userId + ", username='" + this.username + '\'' + '}';
    }


    public long getUserId() {
        return this.userId;
    }
}

