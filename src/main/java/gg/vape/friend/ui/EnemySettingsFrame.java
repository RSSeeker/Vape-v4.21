package gg.vape.friend.ui;

import gg.vape.Vape;
import gg.vape.friend.Enemy;
import gg.vape.friend.ui.EnemySettingsAddEnemyInputComponent;
import gg.vape.friend.ui.EnemySettingsEntryRow;
import gg.vape.friend.ui.EnemySettingsFrameToggleHeaderComponent;
import gg.vape.friend.ui.EnemySettingsRemoveEntryClickHandler;
import gg.vape.ui.click.component.ColorDividerComponent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.value.BooleanToggleComponent;
import gg.vape.ui.click.component.value.ColorValueEditorComponent;
import gg.vape.ui.click.frame.CollapsibleFrame;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.FrameHeaderComponent;

public class EnemySettingsFrame
extends Frame
implements CollapsibleFrame {
    private BooleanToggleComponent spoofAliasToggle;
    private BooleanToggleComponent recolorVisualsToggle;
    private ColorValueEditorComponent enemyColorEditor;
    private BooleanToggleComponent useAliasToggle;
    private ColorDividerComponent colorDivider;
    private BooleanToggleComponent useEnemiesToggle;
    private boolean expanded = true;

    static ColorValueEditorComponent getEnemyColorEditor(EnemySettingsFrame frame) {
        return frame.enemyColorEditor;
    }

    @Override
    public void v() {
    }

    @Override
    public boolean q() {
        return this.expanded;
    }

    public EnemySettingsFrame() {
        this.colorDivider = new ColorDividerComponent(EnemySettingsFrame.J.l);
        this.setDisabledOverlayColor(EnemySettingsFrame.J.i);
        this.K(300.0);
        this.S(100.0);
        this.setVisible(false);
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M(false);
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        this.Y(new EnemySettingsFrameToggleHeaderComponent(this, this, "newfriends", "Enemies"));
        this.enemyColorEditor = new ColorValueEditorComponent(Vape.INSTANCE.getEnemyManager().enemyColor);
        this.recolorVisualsToggle = new BooleanToggleComponent(Vape.INSTANCE.getFriendManager().recolorVisuals);
        this.useEnemiesToggle = new BooleanToggleComponent(Vape.INSTANCE.getEnemyManager().useEnemies);
        this.useAliasToggle = new BooleanToggleComponent(Vape.INSTANCE.getFriendManager().useAlias);
        this.spoofAliasToggle = new BooleanToggleComponent(Vape.INSTANCE.getFriendManager().spoofAlias);
        this.enemyColorEditor.setDisabledOverlayColor(EnemySettingsFrame.J.r);
        this.recolorVisualsToggle.setDisabledOverlayColor(EnemySettingsFrame.J.r);
        this.useEnemiesToggle.setDisabledOverlayColor(EnemySettingsFrame.J.r);
        this.useAliasToggle.setDisabledOverlayColor(EnemySettingsFrame.J.r);
        this.spoofAliasToggle.setDisabledOverlayColor(EnemySettingsFrame.J.r);
        this.enemyColorEditor.setVisible(false);
        this.recolorVisualsToggle.setVisible(false);
        this.useEnemiesToggle.setVisible(false);
        this.useAliasToggle.setVisible(false);
        this.spoofAliasToggle.setVisible(false);
        this.colorDivider.setVisible(false);
    }

    public void refreshEntries() {
        this.removeMarkedChildren();
        this.addChildren(this.enemyColorEditor, this.recolorVisualsToggle, this.useEnemiesToggle, this.useAliasToggle, this.spoofAliasToggle, this.colorDivider);
        this.h(new EnemySettingsAddEnemyInputComponent("Username / Alias"), new Object[0]);
        for (Enemy enemy : Vape.INSTANCE.getEnemyManager().getEnemies()) {
            this.h(new EnemySettingsEntryRow(enemy).setDeleteActionListener(new EnemySettingsRemoveEntryClickHandler(this, enemy)), new Object[0]);
        }
        this.l$src$V$1mibm4x();
    }

    static BooleanToggleComponent getUseEnemiesToggle(EnemySettingsFrame frame) {
        return frame.useEnemiesToggle;
    }

    @Override
    public void Y() {
    }

    @Override
    public void w() {
        this.expanded = !this.expanded;
        for (GuiComponent guiComponent : this.f()) {
            if (guiComponent instanceof FrameHeaderComponent) continue;
            guiComponent.setVisible(this.expanded);
        }
        this.l$src$V$1mibm4x();
    }

    static BooleanToggleComponent getRecolorVisualsToggle(EnemySettingsFrame frame) {
        return frame.recolorVisualsToggle;
    }

    static ColorDividerComponent getColorDivider(EnemySettingsFrame frame) {
        return frame.colorDivider;
    }


    static BooleanToggleComponent getSpoofAliasToggle(EnemySettingsFrame frame) {
        return frame.spoofAliasToggle;
    }

    static BooleanToggleComponent getUseAliasToggle(EnemySettingsFrame frame) {
        return frame.useAliasToggle;
    }

    @Override
    public String getName() {
        return "Enemies";
    }
}

