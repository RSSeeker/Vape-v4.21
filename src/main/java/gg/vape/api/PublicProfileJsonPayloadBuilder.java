package gg.vape.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.List;
import java.util.UUID;
import org.jetbrains.annotations.Nullable;

public class PublicProfileJsonPayloadBuilder {
    public static JsonObject build(String name, String vapeVersion, String description, List<String> tags,
                                   boolean listed, boolean anonymous, boolean shareCodeFriendsOnly,
                                   @Nullable UUID derivedFrom, JsonObject profileData) {
        JsonArray tagsJson = new JsonArray();
        for (String tag : tags) {
            tagsJson.add((JsonElement)new JsonPrimitive(tag));
        }
        JsonObject payload = new JsonObject();
        payload.addProperty("name", name);
        payload.addProperty("vapeVersion", vapeVersion);
        payload.addProperty("description", description);
        payload.add("tags", (JsonElement)tagsJson);
        payload.addProperty("listed", Boolean.valueOf(listed));
        payload.addProperty("anonymous", Boolean.valueOf(anonymous));
        payload.addProperty("shareCodeFriendsOnly", Boolean.valueOf(shareCodeFriendsOnly));
        payload.addProperty("derivedFrom", derivedFrom != null ? derivedFrom.toString() : null);
        payload.add("profileData", (JsonElement)profileData);
        return payload;
    }

}

