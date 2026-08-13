package gg.vape.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public class DualJsonArrayPayload {
    private final JsonArray friends;
    private final JsonArray otherData;

    public JsonArray getOtherData() {
        return this.otherData;
    }

    public JsonArray getFriends() {
        return this.friends;
    }


    DualJsonArrayPayload(JsonArray friends, JsonArray otherData) {
        this.friends = friends;
        this.otherData = otherData;
    }

    public String toString() {
        return "PrivateUserDataResponse{friends=" + this.friends + ", otherData=" + this.otherData + '}';
    }

    @Nullable
    @Contract(value="!null -> !null; null -> null")
    public static DualJsonArrayPayload fromJson(@Nullable JsonElement element) {
        if (element == null || element.isJsonNull()) {
            return null;
        }
        JsonObject object = element.getAsJsonObject();
        return new DualJsonArrayPayload(object.get("friends").getAsJsonArray(), object.get("otherData").getAsJsonArray());
    }
}

