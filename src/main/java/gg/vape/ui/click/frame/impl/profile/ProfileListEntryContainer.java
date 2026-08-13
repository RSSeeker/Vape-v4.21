package gg.vape.ui.click.frame.impl.profile;

import gg.vape.config.Profile;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.frame.impl.profile.ProfileListEntryMetadataComponent;
import gg.vape.ui.click.frame.impl.profile.ProfileListEntryOpenButtonComponent;
import gg.vape.ui.click.frame.impl.profile.ProfilesSettingsFrame;

public class ProfileListEntryContainer
extends GuiComponent {
    private final ProfileListEntryMetadataComponent metadata;
    private final ProfileListEntryOpenButtonComponent openButton;

    public ProfileListEntryContainer(ProfilesSettingsFrame profilesSettingsFrame, Profile profile) {
        double columnWidth = (this.A() - 21.0) / 4.0;
        this.openButton = new ProfileListEntryOpenButtonComponent(profile, profilesSettingsFrame::closePopupAndDiscardDraft);
        this.openButton.o(columnWidth);
        this.openButton.Y(16.0);
        this.metadata = new ProfileListEntryMetadataComponent(profile);
        this.metadata.o(columnWidth * 3.0);
        this.metadata.Y(16.0);
        this.addChildren(this.metadata, this.openButton);
    }

    @Override
    public double C() {
        return 25.0;
    }

    @Override
    public double x() {
        return 110.0;
    }

    @Override
    public void H() {
        double contentX = this.G$src$D$1b2f02a() + 10.0;
        double contentY = this.n() + 5.0;
        this.metadata.K(contentX);
        this.metadata.S(contentY);
        this.openButton.K(contentX + 2.0 + this.metadata.A());
        this.openButton.S(contentY);
    }
}
