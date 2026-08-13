package gg.vape.ui.click.frame.impl.profile;

import gg.vape.ui.click.component.LabeledTextInputComponent;
import gg.vape.ui.click.frame.impl.profile.ProfileSnapshotModuleListPanel;

class ProfileSnapshotModuleSearchInputComponent
extends LabeledTextInputComponent {
    private final ProfileSnapshotModuleListPanel moduleListPanel;

    @Override
    public void setText(String text) {
        super.setText(text);
        this.moduleListPanel.filterRows(this.getText());
    }


    ProfileSnapshotModuleSearchInputComponent(ProfileSnapshotModuleListPanel profileSnapshotModuleListPanel, String string, boolean bl, boolean bl2) {
        super(string, bl, bl2);
        this.moduleListPanel = profileSnapshotModuleListPanel;
    }
}
