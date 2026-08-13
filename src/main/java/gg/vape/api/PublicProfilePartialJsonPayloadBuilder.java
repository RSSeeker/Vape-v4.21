package gg.vape.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import gg.vape.api.ApiHttpClient;
import java.util.List;
import java.util.UUID;
import org.jetbrains.annotations.Nullable;

public class PublicProfilePartialJsonPayloadBuilder {
    public static JsonObject build(long profileId, @Nullable UUID derivedFrom, @Nullable String name,
                                   @Nullable String description, @Nullable List<String> tags,
                                   @Nullable Boolean listed, @Nullable Boolean anonymous,
                                   @Nullable Boolean shareCodeFriendsOnly, @Nullable JsonObject profileData) {
        JsonArray tagsJson = new JsonArray();
        if (tags != null) {
            for (String tag : tags) {
                tagsJson.add((JsonElement)new JsonPrimitive(tag));
            }
        }
        JsonObject payload = new JsonObject();
        payload.addProperty("profileId", (Number)profileId);
        payload.add("derivedFrom", ApiHttpClient.GSON.toJsonTree((Object)derivedFrom));
        payload.addProperty("name", name);
        payload.addProperty("vapeVersion", "4.21");
        payload.addProperty("description", description);
        payload.add("tags", (JsonElement)tagsJson);
        payload.addProperty("listed", listed);
        payload.addProperty("anonymous", anonymous);
        payload.addProperty("shareCodeFriendsOnly", shareCodeFriendsOnly);
        payload.add("profileData", (JsonElement)profileData);
        return payload;
    }
}
