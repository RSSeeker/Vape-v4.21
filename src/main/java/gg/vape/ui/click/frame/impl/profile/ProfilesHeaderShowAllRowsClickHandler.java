package gg.vape.ui.click.frame.impl.profile;

import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.frame.impl.profile.ProfilesSettingsFrame;
import gg.vape.ui.click.frame.impl.profile.ProfilesSettingsHeaderComponent;

class ProfilesHeaderShowAllRowsClickHandler
implements GuiClickListener {
    private final ProfilesSettingsFrame profilesFrame;

    ProfilesHeaderShowAllRowsClickHandler(ProfilesSettingsFrame profilesFrame) {
        this.profilesFrame = profilesFrame;
    }

    @Override
    public void onPrimaryClick() {
        this.profilesFrame.showAllProfileRows();
    }

}

