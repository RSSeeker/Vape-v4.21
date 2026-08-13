package gg.vape.friend.ui;

import gg.vape.friend.ui.EnemySettingsFrame;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.ToggleableFrameHeaderComponent;

class EnemySettingsFrameToggleHeaderComponent
extends ToggleableFrameHeaderComponent {
    final EnemySettingsFrame owner;

    EnemySettingsFrameToggleHeaderComponent(EnemySettingsFrame enemySettingsFrame, Frame frame, String string, String string2) {
        super(frame, string, string2);
        this.owner = enemySettingsFrame;
    }

    @Override
    public void R() {
        EnemySettingsFrame.getEnemyColorEditor(this.owner).setVisible(this.I$src$Z$f74e2a());
        EnemySettingsFrame.getRecolorVisualsToggle(this.owner).setVisible(this.I$src$Z$f74e2a());
        EnemySettingsFrame.getUseEnemiesToggle(this.owner).setVisible(this.I$src$Z$f74e2a());
        EnemySettingsFrame.getUseAliasToggle(this.owner).setVisible(this.I$src$Z$f74e2a());
        EnemySettingsFrame.getSpoofAliasToggle(this.owner).setVisible(this.I$src$Z$f74e2a());
        EnemySettingsFrame.getColorDivider(this.owner).setVisible(this.I$src$Z$f74e2a());
        this.w$src$Lgg_vape_ui_click_frame_Frame_$y4htd0().l$src$V$1mibm4x();
    }
}
