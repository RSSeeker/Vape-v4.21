package gg.vape.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import gg.vape.config.Profile;
import java.util.List;
import java.util.UUID;
import org.jetbrains.annotations.Nullable;

public class ProfilesSyncPayloadBuilder {
    private static int runtimeState;

    public static void setRuntimeState(int runtimeState) {
        ProfilesSyncPayloadBuilder.runtimeState = runtimeState;
    }

    public static int getDefaultRuntimeState() {
        int currentState = ProfilesSyncPayloadBuilder.getRuntimeState();
        return 0;
    }


    static {
        ProfilesSyncPayloadBuilder.setRuntimeState(99);
    }

    public static JsonObject build(@Nullable List<Profile> updatedProfiles, @Nullable List<UUID> deletedProfileIds) {
        JsonObject payload = new JsonObject();
        JsonArray updatedProfilesJson = new JsonArray();
        if (updatedProfiles != null) {
            for (Profile profile : updatedProfiles) {
                updatedProfilesJson.add((JsonElement)profile.toJson(true));
            }
        }
        JsonArray deletedProfilesJson = new JsonArray();
        if (deletedProfileIds != null) {
            for (UUID profileId : deletedProfileIds) {
                deletedProfilesJson.add((JsonElement)new JsonPrimitive(profileId.toString()));
            }
        }
        payload.add("updatedProfiles", (JsonElement)updatedProfilesJson);
        payload.add("deletedProfiles", (JsonElement)deletedProfilesJson);
        return payload;
    }

    public static int getRuntimeState() {
        return runtimeState;
    }
}

