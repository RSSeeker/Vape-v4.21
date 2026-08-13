package gg.vape.manager.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gg.vape.Vape;
import gg.vape.config.BuiltinProfile;
import gg.vape.config.BuiltinProfileState;
import gg.vape.config.Minecraft121BuiltinProfile;
import gg.vape.config.Profile;
import gg.vape.config.ProfilesSyncPayloadBuilder;
import gg.vape.config.PublicProfile;
import gg.vape.event.EventBus;
import gg.vape.event.impl.ProfileChangeEvent;
import gg.vape.event.impl.ProfileListMutationAction;
import gg.vape.event.impl.ProfileListMutationEvent;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.none.ClientSettings;
import gg.vape.module.render.Search;
import gg.vape.ui.click.component.value.AbstractListValueComponent;
import gg.vape.ui.click.component.value.FloatingValueDropdownLayer;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.impl.ClientSettingsFrame;
import gg.vape.ui.click.frame.impl.profile.ProfilesSettingsFrame;
import gg.vape.value.Value;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.jetbrains.annotations.Nullable;

public class ProfilesManager {
    private final List<Profile> profiles = new ArrayList<Profile>();
    static final boolean assertionsDisabled = !ProfilesManager.class.desiredAssertionStatus();
    private Profile activeProfile;
    private final Set<UUID> deletedRemoteProfileIds = new LinkedHashSet<UUID>();

    public void resetAllSettings() {
        for (Mod object : Vape.INSTANCE.getModManager().getAllModules()) {
            if (object.getCategory() == Category.NONE && !(object instanceof Search) || object.isEnabled()) {
                // empty if block
            }
            if (object.getBind().usesOwnKeybindStorage()) {
                object.getBind().getBoundInputs().clear();
                if (object.getDefaultKeybind() != 0) {
                    object.getBind().getBoundInputs().add(object.getDefaultKeybind());
                }
            }
            object.setVisibility(object.isDefaultVisible());
            for (Value<?, ?> value : object.getAllValues()) {
                value.reset();
            }
        }
        ClientSettings.refreshModuleCategoryHeaders();
        for (Value value : Vape.INSTANCE.getValueManager().getValues()) {
            if (value.getDefaultValue() == null) continue;
            value.reset();
        }
        for (Frame frame : ClientSettings.getAllFrames()) {
            if (frame instanceof ClientSettingsFrame) {
                ((ClientSettingsFrame)frame).d$src$V$16knweo();
                continue;
            }
            if (!(frame instanceof FloatingValueDropdownLayer)) continue;
            ((AbstractListValueComponent)((FloatingValueDropdownLayer)frame).getSourceComponent()).setExpanded(false);
        }
    }

    public List<Profile> getProfiles() {
        return this.profiles;
    }

    public Profile getProfileByOnlineId(UUID onlineId) {
        for (Profile profile : this.profiles) {
            if (profile.getOnlineId() == null || !profile.getOnlineId().toString().equalsIgnoreCase(onlineId.toString())) continue;
            return profile;
        }
        return null;
    }

    public void removeProfile(Profile profile) {
        this.profiles.remove(profile);
        ClientSettings.getFrame(ProfilesSettingsFrame.class).removeProfile(profile);
        Vape.INSTANCE.saveAndStop();
        new ProfileListMutationEvent(profile, ProfileListMutationAction.REMOVE).fire();
        if (profile.getOnlineId() != null) {
            this.deletedRemoteProfileIds.add(profile.getOnlineId());
        }
    }

    public void addProfile(Profile profile, boolean prepend) {
        if (prepend) {
            this.profiles.add(0, profile);
            for (Profile profile2 : this.profiles) {
                profile2.setSortOrder(profile2.getCurrentSortIndex());
            }
        } else {
            this.profiles.add(profile);
        }
        ClientSettings.getFrame(ProfilesSettingsFrame.class).addProfile(profile);
        ProfilesSettingsFrame.refreshProfileList();
        Vape.INSTANCE.saveAndStop();
        new ProfileListMutationEvent(profile, ProfileListMutationAction.ADD).fire();
    }

    public JsonObject buildSingleProfileSyncPayload(Profile profile) {
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        jsonArray.add((JsonElement)profile.toJson(true));
        jsonObject.add("updatedProfiles", (JsonElement)jsonArray);
        jsonObject.add("deletedProfiles", (JsonElement)new JsonArray());
        return jsonObject;
    }

    public void loadJson(JsonObject jsonObject) {
        if (jsonObject == null) {
            return;
        }
        if (jsonObject.entrySet().isEmpty()) {
            Profile profile = this.initializeBuiltinProfiles();
            if (profile != null) {
                this.setActiveProfile(profile);
            }
            return;
        }
        for (Map.Entry entry : jsonObject.entrySet()) {
            JsonObject jsonObject2 = ((JsonElement)entry.getValue()).getAsJsonObject();
            Profile profile = new Profile("", "", true).loadJson(jsonObject2);
            this.addProfile(profile);
        }
        try {
            this.profiles.sort(ProfilesManager::compareProfileSortOrder);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        ProfilesSettingsFrame.refreshProfileList();
    }

    private static Throwable propagateThrowable(Throwable throwable) {
        return throwable;
    }

    private Profile initializeBuiltinProfiles() {
        ArrayList<BuiltinProfile> builtinProfiles = new ArrayList<BuiltinProfile>();
        builtinProfiles.add(new Minecraft121BuiltinProfile());
        builtinProfiles.add(new BuiltinProfileState());
        Profile selectedProfile = null;
        for (BuiltinProfile builtinProfile : builtinProfiles) {
            builtinProfile.applyPreset();
            this.addProfile(builtinProfile);
            if (selectedProfile != null || !builtinProfile.isApplicable()) continue;
            selectedProfile = builtinProfile;
        }
        if (selectedProfile == null && !this.profiles.isEmpty()) {
            selectedProfile = this.profiles.get(0);
        }
        return selectedProfile;
    }

    private static int compareProfileSortOrder(Profile first, Profile second) {
        if (first.getSortOrder() != null && second.getSortOrder() != null) {
            return first.getSortOrder().compareTo(second.getSortOrder());
        }
        if (first.getSortOrder() != null) {
            return 1;
        }
        return -1;
    }

    public Profile getProfileByName(String name) {
        for (Profile profile : this.profiles) {
            if (!profile.getName().equalsIgnoreCase(name)) continue;
            return profile;
        }
        return null;
    }

    public JsonObject toJson(boolean dirtyOnly) {
        if (dirtyOnly) {
            ArrayList<Profile> dirtyProfiles = new ArrayList<Profile>();
            for (Profile profile : this.getProfiles()) {
                if (!profile.isDirty()) continue;
                dirtyProfiles.add(profile);
            }
            ArrayList<UUID> deletedIds = new ArrayList<UUID>(this.deletedRemoteProfileIds);
            this.deletedRemoteProfileIds.clear();
            return ProfilesSyncPayloadBuilder.build(dirtyProfiles, (List<UUID>)deletedIds);
        }
        JsonObject result = new JsonObject();
        for (Profile profile : this.getProfiles()) {
            result.add(profile.getLocalId().toString(), (JsonElement)profile.toJson(dirtyOnly));
        }
        return result;
    }

    public void clearProfiles() {
        this.profiles.clear();
    }

    public void addProfile(Profile profile) {
        this.addProfile(profile, false);
    }

    public void updatePublicProfileLinks() {
        Collection<PublicProfile> collection = Vape.INSTANCE.getPublicProfileManager().getProfilesById().values();
        for (Profile profile : this.profiles) {
            profile.setPublicProfile(null);
            for (PublicProfile publicProfile : collection) {
                UUID uUID;
                if (publicProfile.getShareInfo() == null || (uUID = publicProfile.getShareInfo().getDerivedFrom()) == null || profile.getOnlineId() == null || !profile.getOnlineId().toString().equalsIgnoreCase(uUID.toString())) continue;
                profile.setPublicProfile(publicProfile);
            }
        }
    }

    public void setActiveProfile(Profile profile) {
        if (this.activeProfile != null && this.activeProfile.equals(profile)) {
            return;
        }
        Profile previousProfile = this.activeProfile;
        this.resetAllSettings();
        this.activeProfile = profile;
        this.activeProfile.apply();
        Vape.INSTANCE.saveAndStop();
        EventBus.getInstance().post(new ProfileChangeEvent(previousProfile, profile));
    }

    public void switchProfile(Profile profile) {
        if (this.getActiveProfile() != null) {
            this.getActiveProfile().captureCurrentState();
        }
        this.setActiveProfile(profile);
    }

    public void captureProfileState(Profile profile) {
        Profile currentProfile = this.getActiveProfile();
        if (profile.equals(currentProfile)) {
            profile.captureCurrentState();
        } else {
            this.setActiveProfile(profile);
            profile.captureCurrentState();
            this.setActiveProfile(currentProfile);
        }
    }

    public List<Profile> getRemoteProfiles() {
        ArrayList<Profile> remoteProfiles = new ArrayList<Profile>();
        for (Profile profile : this.profiles) {
            if (profile.getRemoteMetadata() == null) continue;
            remoteProfiles.add(profile);
        }
        return remoteProfiles;
    }

    public Profile getActiveProfile() {
        boolean activeProfileInvalid = this.activeProfile == null || !this.getProfiles().contains(this.activeProfile) && !this.activeProfile.isDraft();
        if (activeProfileInvalid) {
            if (!this.getProfiles().isEmpty()) {
                this.setActiveProfile(this.getProfiles().get(0));
            } else {
                Profile profile = this.initializeBuiltinProfiles();
                this.setActiveProfile(profile);
            }
        }
        return this.activeProfile;
    }

    @Nullable
    public Profile getProfileByPublicProfileId(long publicProfileId) {
        for (Profile profile : this.profiles) {
            if (profile.getRemoteMetadata() == null) continue;
            if (!assertionsDisabled && profile.getRemoteMetadata() == null) {
                throw new AssertionError();
            }
            if (profile.getRemoteMetadata().getPublicProfileId() != publicProfileId) continue;
            return profile;
        }
        return null;
    }

    public void sortProfiles() {
        this.profiles.sort(Profile::compareSortOrder);
    }

    public Profile getActiveProfileOrNull() {
        return this.activeProfile;
    }

    public Profile getProfileByLocalId(UUID localId) {
        for (Profile profile : this.profiles) {
            if (!profile.getLocalId().toString().equalsIgnoreCase(localId.toString())) continue;
            return profile;
        }
        return null;
    }
}
