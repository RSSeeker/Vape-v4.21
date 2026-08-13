package gg.vape.ui.click.frame.impl.profile;

import gg.vape.Vape;
import gg.vape.config.Profile;
import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.frame.impl.profile.ProfileListEntryComponent;

class ProfileListEntryOpenSettingsClickHandler
implements GuiClickListener {
    private final ProfileListEntryComponent entry;
    private final Profile profile;

    ProfileListEntryOpenSettingsClickHandler(ProfileListEntryComponent entry, Profile profile) {
        this.entry = entry;
        this.profile = profile;
    }

    @Override
    public void onPrimaryClick() {
        if (Vape.INSTANCE.getProfilesManager().getActiveProfile().equals(this.profile)) {
            this.profile.captureCurrentState();
        }
        this.entry.openSettings();
    }

}

