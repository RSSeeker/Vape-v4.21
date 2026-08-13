package gg.vape.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public class ProfileRemoteMetadata {
    private final long version;
    private final long publishedVersion;
    private final long publicProfileId;

    public JsonObject toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("publicProfileId", (Number)this.publicProfileId);
        object.addProperty("version", (Number)this.version);
        object.addProperty("publishedVersion", (Number)this.publishedVersion);
        return object;
    }

    @Nullable
    @Contract(value="!null -> !null; null -> null")
    public static ProfileRemoteMetadata fromJson(@Nullable JsonElement element) {
        if (element == null || element.isJsonNull()) {
            return null;
        }
        JsonObject object = element.getAsJsonObject();
        return new ProfileRemoteMetadata(object.get("publicProfileId").getAsLong(), object.get("version").getAsLong(), object.get("publishedVersion").getAsLong());
    }

    public long getPublicProfileId() {
        return this.publicProfileId;
    }

    public long getPublishedVersion() {
        return this.publishedVersion;
    }

    public String toString() {
        return "PrivateProfileMetadata{publicProfileId=" + this.publicProfileId + ", version=" + this.version + ", publishedVersion=" + this.publishedVersion + '}';
    }

    public long getVersion() {
        return this.version;
    }

    ProfileRemoteMetadata(long publicProfileId, long version, long publishedVersion) {
        this.publicProfileId = publicProfileId;
        this.version = version;
        this.publishedVersion = publishedVersion;
    }

    public boolean hasNewerPublishedVersion() {
        return this.version < this.publishedVersion;
    }

}

