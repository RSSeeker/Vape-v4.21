package gg.vape.ui.click.frame.impl.profile;

import gg.vape.ui.click.component.TextInputComponentBase;
import gg.vape.ui.click.component.TruncatedTextComponent;
import gg.vape.ui.click.frame.impl.profile.ProfileListEntryComponent;
import gg.vape.ui.click.frame.impl.profile.ProfilesSettingsFrame;

class ProfileRenameInputComponent
extends TextInputComponentBase {
    private final TruncatedTextComponent nameDisplay;
    private final ProfileListEntryComponent entry;

    @Override
    public double x() {
        return 100.0;
    }


    ProfileRenameInputComponent(ProfileListEntryComponent entry, String profileName, TruncatedTextComponent nameDisplay) {
        super(profileName);
        this.entry = entry;
        this.nameDisplay = nameDisplay;
    }

    @Override
    public double C() {
        return this.V$src$Z$1xhop3l() ? 22.5 : 0.0;
    }

    @Override
    public void submit() {
        this.setVisible(false);
        String profileName = this.getText().trim();
        if (profileName.isEmpty()) {
            return;
        }
        this.entry.getProfile().setName(profileName);
        this.nameDisplay.setText(this.entry.getProfile().getName());
        ProfilesSettingsFrame.refreshProfileList();
    }
}
