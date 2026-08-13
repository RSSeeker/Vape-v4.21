package gg.vape.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gg.vape.config.ConfigJsonUtils;
import gg.vape.config.PublicProfileUser;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.Nullable;

public class PublicProfileSummary {
    private final long version;
    private final String name;
    @Nullable
    private final PublicProfileUser owner;
    private final List<String> tags;
    private final long likes;
    private final long dislikes;
    private final long profileId;
    @Nullable
    private String shareCode;

    public List<String> getTags() {
        return this.tags;
    }

    public String toString() {
        return "SimplePublicProfile{profileId=" + this.profileId + ", owner=" + this.owner + ", name='" + this.name + '\'' + ", version=" + this.version + ", likes=" + this.likes + ", dislikes=" + this.dislikes + ", tags=" + this.tags + '}';
    }

    @Nullable
    public PublicProfileUser getOwner() {
        return this.owner;
    }

    @Nullable
    public String getUppercaseShareCode() {
        if (this.shareCode == null) {
            return null;
        }
        return this.shareCode.toUpperCase();
    }

    public static PublicProfileSummary fromJson(JsonObject object) {
        ArrayList<String> tags = new ArrayList<String>();
        for (JsonElement tagElement : object.get("tags").getAsJsonArray()) {
            tags.add(tagElement.getAsString());
        }
        return new PublicProfileSummary(object.get("profileId").getAsLong(), PublicProfileUser.fromJson(object.get("owner")), object.get("name").getAsString(), object.get("version").getAsLong(), object.get("likes").getAsLong(), object.get("dislikes").getAsLong(), tags, ConfigJsonUtils.getString(object, "shareCode"));
    }

    public long getLikes() {
        return this.likes;
    }

    PublicProfileSummary(long profileId, @Nullable PublicProfileUser owner, String name, long version, long likes, long dislikes, List<String> tags, @Nullable String shareCode) {
        this.profileId = profileId;
        this.owner = owner;
        this.name = name;
        this.version = version;
        this.likes = likes;
        this.dislikes = dislikes;
        this.tags = tags;
        this.shareCode = shareCode;
    }


    public String getName() {
        return this.name;
    }

    public long getDislikes() {
        return this.dislikes;
    }

    public long getProfileId() {
        return this.profileId;
    }

    public long getVersion() {
        return this.version;
    }
}

