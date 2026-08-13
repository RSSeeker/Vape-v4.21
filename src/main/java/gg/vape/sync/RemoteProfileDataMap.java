package gg.vape.sync;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public class RemoteProfileDataMap {
    private final Map<UUID, RemoteProfileData> profiles;
    private static final String TO_STRING_PREFIX = "PrivateProfilesResponse{profiles=";

    RemoteProfileDataMap(Map<UUID, RemoteProfileData> profiles) {
        this.profiles = profiles;
    }


    @Nullable
    @Contract(value="!null -> !null; null -> null")
    public static RemoteProfileDataMap fromJson(@Nullable JsonElement element) {
        if (element == null || element.isJsonNull()) {
            return null;
        }
        JsonObject json = element.getAsJsonObject();
        LinkedHashMap<UUID, RemoteProfileData> profiles = new LinkedHashMap<UUID, RemoteProfileData>();
        for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
            RemoteProfileData profile = RemoteProfileData.fromJson(entry.getValue());
            if (profile == null) continue;
            profiles.put(UUID.fromString(entry.getKey()), profile);
        }
        return new RemoteProfileDataMap(profiles);
    }

    public String toString() {
        return TO_STRING_PREFIX + this.profiles + '}';
    }

    public Map<UUID, RemoteProfileData> getProfiles() {
        return this.profiles;
    }
}

