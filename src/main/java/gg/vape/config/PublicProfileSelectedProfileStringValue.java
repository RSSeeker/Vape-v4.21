package gg.vape.config;

import gg.vape.Vape;
import gg.vape.config.Profile;
import gg.vape.config.PublicProfileSettings;
import gg.vape.utils.StringUtils;
import gg.vape.value.StringValue;
import java.util.UUID;

public class PublicProfileSelectedProfileStringValue
extends StringValue {
    final PublicProfileSettings settings;
    private static final String MISSING_ONLINE_UUID_SUFFIX = " has no online uuid";


    @Override
    public String getValue() {
        Profile activeProfile = Vape.INSTANCE.getProfilesManager().getActiveProfile();
        UUID onlineId = activeProfile.getOnlineId();
        if (onlineId == null) {
            Vape.debugLog(activeProfile.getName() + MISSING_ONLINE_UUID_SUFFIX);
            return "";
        }
        return onlineId.toString();
    }

    public PublicProfileSelectedProfileStringValue(PublicProfileSettings settings, Object owner, String name, String defaultValue) {
        super(owner, name, defaultValue);
        this.settings = settings;
    }

    @Override
    public void setValue(String profileIdentifier) {
        super.setValue(profileIdentifier);
        boolean isUuid = StringUtils.n(profileIdentifier);
        if (isUuid) {
            Profile profile = Vape.INSTANCE.getProfilesManager().getProfileByOnlineId(UUID.fromString(profileIdentifier));
            if (profile != null) {
                PublicProfileSettings.setSelectedProfile(this.settings, profile);
            }
        } else {
            Profile profile = Vape.INSTANCE.getProfilesManager().getProfileByName(profileIdentifier);
            if (profile != null) {
                PublicProfileSettings.setSelectedProfile(this.settings, profile);
            }
        }
    }
}
