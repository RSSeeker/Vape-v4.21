package gg.vape.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gg.vape.Vape;
import gg.vape.api.ApiHttpClient;
import gg.vape.config.ConfigJsonUtils;
import gg.vape.config.Profile;
import gg.vape.config.ProfileModuleSnapshot;
import gg.vape.config.ProfileModuleSnapshotOrderComparator;
import gg.vape.config.PublicProfile;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.SubModule;
import gg.vape.module.render.hud.HudModule;
import gg.vape.ui.click.frame.impl.profile.ProfileSnapshotGuiBuilder;
import gg.vape.utils.NameComparator;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ProfileSnapshot {
    private final List<ProfileModuleSnapshot> moduleSnapshots;
    private final ProfileSnapshotGuiBuilder guiBuilder;
    private Profile profile;

    public List<ProfileModuleSnapshot> getSortedModules(boolean includeDefaults) {
        List<ProfileModuleSnapshot> modules = this.getModules(includeDefaults);
        modules.sort(new ProfileModuleSnapshotOrderComparator());
        return modules;
    }

    public JsonObject serializeEnabledModules() {
        JsonObject enabledModules = new JsonObject();
        for (ProfileModuleSnapshot moduleSnapshot : this.getAllModules()) {
            if (moduleSnapshot.getModule() instanceof HudModule || !moduleSnapshot.isEnabled()) continue;
            enabledModules.addProperty(moduleSnapshot.getName(), Boolean.valueOf(moduleSnapshot.isEnabled()));
        }
        return enabledModules;
    }

    public ProfileSnapshotGuiBuilder getGuiBuilder() {
        return this.guiBuilder;
    }

    public void applyToProfile() {
        if (this.profile == null) {
            return;
        }
        JsonObject profileJson = this.profile.getData();
        profileJson.add("modules", this.serializeModules());
        profileJson.add("enabled", this.serializeEnabledModules());
        this.profile.updateData(profileJson);
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Profile getProfile() {
        return this.profile;
    }

    public JsonArray serializeModules() {
        JsonArray modulesJson = new JsonArray();
        for (ProfileModuleSnapshot moduleSnapshot : this.moduleSnapshots) {
            JsonObject moduleJson = moduleSnapshot.toJson();
            if (moduleJson == null) continue;
            modulesJson.add(moduleJson);
        }
        return modulesJson;
    }

    public List<ProfileModuleSnapshot> getModules(boolean includeDefaults) {
        ArrayList<ProfileModuleSnapshot> modules = new ArrayList<>();
        for (ProfileModuleSnapshot moduleSnapshot : this.getAllModules()) {
            if (moduleSnapshot.getModule() instanceof SubModule || moduleSnapshot.getModule().getCategory() == Category.NONE || !moduleSnapshot.hasChanges() && !includeDefaults) continue;
            modules.add(moduleSnapshot);
        }
        return modules;
    }

    public ProfileSnapshot(Profile profile, JsonArray modulesJson) {
        this.profile = profile;
        this.moduleSnapshots = new ArrayList<>();
        LinkedHashMap<String, JsonObject> moduleJsonByName = new LinkedHashMap<>();
        if (modulesJson != null) {
            for (JsonElement moduleElement : modulesJson) {
                if (moduleElement.isJsonNull() || !moduleElement.isJsonObject()) continue;
                JsonObject moduleJson = moduleElement.getAsJsonObject();
                String moduleName = ConfigJsonUtils.getString(moduleJson, "name");
                if (moduleName == null) continue;
                moduleJsonByName.put(moduleName, moduleJson);
            }
        }
        for (Mod module : Vape.INSTANCE.getModManager().getTopLevelModules()) {
            this.moduleSnapshots.add(new ProfileModuleSnapshot(this, module, moduleJsonByName.get(module.getName())));
        }
        this.moduleSnapshots.sort(new NameComparator());
        this.guiBuilder = new ProfileSnapshotGuiBuilder(this);
    }

    public static ProfileSnapshot createEditableCopy(PublicProfile publicProfile, Profile sourceProfile) {
        Profile editableProfile = new Profile(publicProfile.getName(), sourceProfile.getClientVersion());
        editableProfile.loadJson(sourceProfile.toJson(true));
        editableProfile.setPublishedData(sourceProfile.getPublishedData());
        editableProfile.setName(publicProfile.getName());
        return new ProfileSnapshot(editableProfile, sourceProfile.getData().getAsJsonArray("modules"));
    }

    public List<ProfileModuleSnapshot> getAllModules() {
        return this.moduleSnapshots;
    }

    public static ProfileSnapshot resolvePublicProfileSnapshot(PublicProfile publicProfile) {
        Object serializedModules = publicProfile.getData() != null
            ? publicProfile.getData().getOrDefault("modules", null)
            : null;
        assert publicProfile.getShareInfo() != null;
        Profile localProfile = publicProfile.getShareInfo().getDerivedFrom() != null
            ? Vape.INSTANCE.getProfilesManager().getProfileByOnlineId(publicProfile.getShareInfo().getDerivedFrom())
            : null;
        if (localProfile != null) {
            if (localProfile.equals(Vape.INSTANCE.getProfilesManager().getActiveProfile())) {
                localProfile.captureCurrentState();
            }
            return localProfile.createSnapshot(true);
        }
        Profile detachedProfile = new Profile(publicProfile.getName(), "4.21");
        JsonArray modulesJson = ApiHttpClient.GSON.fromJson(serializedModules != null ? ApiHttpClient.GSON.toJson(serializedModules) : "[]", JsonArray.class);
        JsonObject profileData = new JsonObject();
        JsonObject data = new JsonObject();
        data.add("modules", modulesJson);
        profileData.add("data", data);
        detachedProfile.loadJson(profileData);
        detachedProfile.setName(publicProfile.getName());
        return new ProfileSnapshot(detachedProfile, modulesJson);
    }
}

