package gg.vape.ui.click.frame.impl.profile;

import gg.vape.Vape;
import gg.vape.config.Profile;
import gg.vape.ui.click.component.IconButtonComponent;
import gg.vape.ui.click.component.gui.TextLabel;
import gg.vape.ui.click.frame.ToggleableFrameHeaderComponent;
import gg.vape.ui.click.frame.impl.profile.ProfilesHeaderApplyPendingProfileClickHandler;
import gg.vape.ui.click.frame.impl.profile.ProfilesHeaderShowActiveRowsClickHandler;
import gg.vape.ui.click.frame.impl.profile.ProfilesHeaderShowAllRowsClickHandler;
import gg.vape.ui.click.frame.impl.profile.ProfilesSettingsFrame;
import gg.vape.ui.font.SmoothFontRenderer;
import java.awt.Color;

public class ProfilesSettingsHeaderComponent
extends ToggleableFrameHeaderComponent {
    private final IconButtonComponent editHiddenProfilesButton = new IconButtonComponent("newhide", 0.7);
    private final IconButtonComponent backButton;
    private final TextLabel doneButton = new TextLabel("Edit");
    private final ProfilesSettingsFrame profilesFrame;

    public ProfilesSettingsHeaderComponent(ProfilesSettingsFrame profilesFrame, String title, String iconKey, double width) {
        super(profilesFrame, title, iconKey, width);
        this.backButton = new IconButtonComponent("moduleback");
        this.profilesFrame = profilesFrame;
        this.editHiddenProfilesButton.w("Edit hidden profiles");
        this.editHiddenProfilesButton.addClickListener(new ProfilesHeaderShowAllRowsClickHandler(profilesFrame));
        this.backButton.addClickListener(new ProfilesHeaderApplyPendingProfileClickHandler(profilesFrame));
        this.doneButton.addClickListener(new ProfilesHeaderShowActiveRowsClickHandler(profilesFrame));
        this.addChildren(this.editHiddenProfilesButton, this.doneButton, this.backButton);
    }


    private static boolean isHiddenProfile(Profile profile) {
        return !profile.isVisible();
    }

    @Override
    public void H() {
        super.H();
        SmoothFontRenderer font = this.getFontRenderer(0.9);
        if (this.profilesFrame.getActivePopup() != null) {
            this.Y(false);
            this.L$src$Lgg_vape_ui_click_component_IconButtonComponent_$1i7gwfq().setVisible(false);
            this.editHiddenProfilesButton.setVisible(false);
            this.doneButton.setVisible(false);
            this.backButton.setOverrideColor(ProfilesSettingsHeaderComponent.J.A);
            this.backButton.setVisible(true);
            this.backButton.K(this.G$src$D$1b2f02a() + 4.0);
            this.backButton.S(this.n());
            this.backButton.Y(this.L());
            return;
        }
        this.Y(true);
        this.backButton.setVisible(false);
        if (this.profilesFrame.isShowingAllProfiles()) {
            this.L$src$Lgg_vape_ui_click_component_IconButtonComponent_$1i7gwfq().setVisible(false);
            this.profilesFrame.closePopupAndDiscardDraft();
            this.editHiddenProfilesButton.setVisible(false);
            this.doneButton.setVisible(true);
            this.doneButton.setLabelText("Done");
            this.doneButton.K(this.G$src$D$1b2f02a() + this.A() - 26.0 - font.N(this.doneButton.getText()) / 2.0);
            this.doneButton.S(this.n());
            this.doneButton.Y(this.L());
        } else {
            this.L$src$Lgg_vape_ui_click_component_IconButtonComponent_$1i7gwfq().setVisible(true);
            this.doneButton.setVisible(false);
            this.editHiddenProfilesButton.setVisible(true);
            this.editHiddenProfilesButton.setOverrideColor((Color)null);
            this.editHiddenProfilesButton.K(this.G$src$D$1b2f02a() + this.A() - 44.0);
            this.editHiddenProfilesButton.S(this.n());
            this.editHiddenProfilesButton.Y(this.L());
            int hiddenProfileCount = (int)Vape.INSTANCE.getProfilesManager().getProfiles().stream().filter(ProfilesSettingsHeaderComponent::isHiddenProfile).count();
            if (hiddenProfileCount != 0) {
                String countText = Integer.toString(hiddenProfileCount);
                font.d(countText, this.G$src$D$1b2f02a() + this.A() - 45.25 - font.N(countText) / 2.0, this.n() + this.L() / 2.0 - font.d(countText) / 2.0, ProfilesSettingsHeaderComponent.J.W);
            }
        }
    }
}

