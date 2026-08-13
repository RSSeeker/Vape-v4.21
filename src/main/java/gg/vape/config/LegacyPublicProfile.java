package gg.vape.config;

import com.google.gson.JsonObject;
import gg.vape.config.Profile;
import org.jetbrains.annotations.Nullable;

public class LegacyPublicProfile
extends Profile {
    private boolean marked = false;
    private int useCount;

    @Override
    public JsonObject toJson(boolean includeLocalData) {
        JsonObject object = super.toJson(includeLocalData);
        return object;
    }

    @Override
    public Profile loadJson(JsonObject object) {
        super.loadJson(object);
        if (object.get("uses") != null) {
            this.useCount = object.get("uses").getAsInt();
        }
        return this;
    }

    public LegacyPublicProfile(String name, String clientVersion) {
        super(name, clientVersion);
    }


    @Override
    public int getUseCount() {
        return this.useCount;
    }

    public boolean isMarked() {
        return this.marked;
    }

    @Nullable
    public static String normalizeTag(String tag) {
        String normalizedTag = tag.trim();
        if (normalizedTag.isEmpty()) {
            return null;
        }
        return normalizedTag;
    }

    @Nullable
    public static String validateTag(@Nullable String tag) {
        if (tag == null) {
            return "You must input a valid tag";
        }
        if (tag.length() > 16) {
            return "Tags must be 16 characters or less";
        }
        return null;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }
}

