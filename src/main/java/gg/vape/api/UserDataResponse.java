package gg.vape.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gg.vape.config.ConfigJsonUtils;
import gg.vape.config.PublicProfile;
import gg.vape.sync.RemoteProfileData;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public class UserDataResponse {
    private final Map<UUID, RemoteProfileData> profiles;
    @Nullable
    private final JsonArray friends;
    private final Map<Long, PublicProfile> publicProfiles;
    @Nullable
    private final JsonArray otherData;

    @Nullable
    public JsonArray getFriends() {
        return this.friends;
    }

    @Nullable
    @Contract(value="!null -> !null; null -> null")
    public static UserDataResponse fromJson(@Nullable JsonElement dataElement) {
        if (dataElement == null || dataElement.isJsonNull()) {
            return null;
        }
        JsonObject dataJson = dataElement.getAsJsonObject();
        LinkedHashMap<UUID, RemoteProfileData> profiles = new LinkedHashMap<UUID, RemoteProfileData>();
        LinkedHashMap<Long, PublicProfile> publicProfiles = new LinkedHashMap<Long, PublicProfile>();
        JsonObject profilesJson = ConfigJsonUtils.getJsonObject(dataJson, "profiles");
        if (profilesJson != null) {
            for (Map.Entry<String, JsonElement> entry : profilesJson.entrySet()) {
                RemoteProfileData remoteProfileData = RemoteProfileData.fromJson(entry.getValue());
                if (remoteProfileData == null) continue;
                profiles.put(UUID.fromString(entry.getKey()), remoteProfileData);
            }
        }
        JsonObject publicProfilesJson = ConfigJsonUtils.getJsonObject(dataJson, "publicProfiles");
        if (publicProfilesJson != null) {
            for (Map.Entry<String, JsonElement> entry : publicProfilesJson.entrySet()) {
                PublicProfile publicProfile = PublicProfile.fromJson(entry.getValue());
                if (publicProfile == null) continue;
                publicProfiles.put(publicProfile.getProfileId(), publicProfile);
            }
        }
        return new UserDataResponse(ConfigJsonUtils.getJsonArray(dataJson, "friends"), profiles, publicProfiles,
                ConfigJsonUtils.getJsonArray(dataJson, "otherData"));
    }

    public String toString() {
        return "FullPrivateDataResponse{friends=" + this.friends + ", profiles=" + this.profiles + ", publicProfiles=" + this.publicProfiles + ", otherData=" + this.otherData + '}';
    }

    @Nullable
    public JsonArray getOtherData() {
        return this.otherData;
    }

    public Map<UUID, RemoteProfileData> getProfiles() {
        return this.profiles;
    }


    public Map<Long, PublicProfile> getPublicProfiles() {
        return this.publicProfiles;
    }

    UserDataResponse(@Nullable JsonArray friends, Map<UUID, RemoteProfileData> profiles,
                     Map<Long, PublicProfile> publicProfiles, @Nullable JsonArray otherData) {
        this.friends = friends;
        this.profiles = profiles;
        this.publicProfiles = publicProfiles;
        this.otherData = otherData;
    }
}
