package gg.vape.ui.click.frame.impl.profile;

import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.frame.impl.profile.ProfilesSettingsFrame;

public class ProfilesShowAllRowsClickHandler
implements GuiClickListener {
    private final ProfilesSettingsFrame profilesFrame;


    public ProfilesShowAllRowsClickHandler(ProfilesSettingsFrame profilesFrame) {
        this.profilesFrame = profilesFrame;
    }

    @Override
    public void onPrimaryClick() {
        this.profilesFrame.showAllProfileRows();
        this.profilesFrame.getDoneButton().setVisible(true);
        this.profilesFrame.getEditHiddenProfilesButton().setVisible(false);
    }
}

