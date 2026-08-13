package gg.vape.ui.click.frame.impl.profile;

import gg.vape.config.ClientSettings;
import gg.vape.config.Profile;
import gg.vape.module.Mod;
import gg.vape.ui.click.component.gui.TextButton;
import gg.vape.utils.StringUtils;
import java.awt.Color;

public class ProfileListEntryMetadataComponent
extends TextButton {
    private Profile profile;


    private String buildTooltip() {
        if (this.profile == null) {
            return "No profile selected";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Click to re-enable the saved states of modules\n");
        stringBuilder.append("Alternatively, right click the profile to do this\n\n");
        StringBuilder stringBuilder2 = new StringBuilder();
        String string = "";
        for (Mod mod : this.profile.getEnabledModules()) {
            string = string + mod.getName() + ", ";
            if (!(this.getDefaultFontRenderer().N(string) > 150.0)) continue;
            stringBuilder2.append(ClientSettings.FORMAT_CODE).append("f").append(string).append("\n");
            string = "";
        }
        if (!string.isEmpty()) {
            stringBuilder2.append(ClientSettings.FORMAT_CODE).append("f").append(string);
        }
        if (stringBuilder2.length() > 0) {
            stringBuilder.append("This will re-enable these modules:\n");
            stringBuilder.append(StringUtils.b(stringBuilder2.toString(), ", ", ""));
        } else {
            stringBuilder.append(ClientSettings.FORMAT_CODE).append("f").append("This profile has no modules saved");
        }
        return stringBuilder.toString();
    }

    private void enableSavedModules() {
        if (this.profile != null) {
            this.profile.applyEnabledModuleStates();
        }
    }

    @Override
    public void H() {
        this.w(this.buildTooltip());
        super.H();
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Profile getProfile() {
        return this.profile;
    }

    public ProfileListEntryMetadataComponent(Profile profile) {
        super("Enable modules", 0.8, ProfileListEntryMetadataComponent.J.B, ProfileListEntryMetadataComponent.J.O, ProfileListEntryMetadataComponent.J.l, 2.0f, 1.0f);
        this.profile = profile;
        this.setDeriveTextColorFromBackground(false);
        this.setBorderAndBackgroundColor(ProfileListEntryMetadataComponent.J.l, ProfileListEntryMetadataComponent.J.i);
        this.setBorderAlpha(1.0f);
        this.setTransparentBackgroundBorder(ProfileListEntryMetadataComponent.J.l);
        this.setDisabledOverlayColor(ProfileListEntryMetadataComponent.J.i);
        this.setIconResource("newload");
        this.setIconSize(7.0f);
        this.setUseAlternateFont(true);
        this.setFontScale(0.8);
        this.setHoverTextColor(Color.WHITE);
        this.setAnimateTextColor(true);
        this.addClickListener(this::enableSavedModules);
    }
}

