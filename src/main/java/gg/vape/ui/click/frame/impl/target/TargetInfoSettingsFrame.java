package gg.vape.ui.click.frame.impl.target;

import gg.vape.event.EventListener;
import gg.vape.ui.click.component.value.BooleanToggleComponent;
import gg.vape.ui.click.frame.impl.hud.HudSettingsFrameBase;
import gg.vape.ui.click.frame.impl.target.TargetInfoPreviewComponent;
import gg.vape.ui.click.frame.impl.target.TargetInfoSettings;

public class TargetInfoSettingsFrame
extends HudSettingsFrameBase
implements EventListener {
    private final TargetInfoSettings settings = new TargetInfoSettings();
    private final BooleanToggleComponent hitsComparatorToggle;
    private final BooleanToggleComponent showHoveredToggle;
    private final TargetInfoPreviewComponent preview;
    private final BooleanToggleComponent damageComparatorToggle;
    private final BooleanToggleComponent comboCounterToggle;
    private final BooleanToggleComponent potsUsedComparatorToggle;

    public TargetInfoSettingsFrame() {
        super("newtargetinfo", "Target Info");
        this.showHoveredToggle = new BooleanToggleComponent(this.settings.showHovered);
        this.damageComparatorToggle = new BooleanToggleComponent(this.settings.damageComparator);
        this.comboCounterToggle = new BooleanToggleComponent(this.settings.comboCounter);
        this.hitsComparatorToggle = new BooleanToggleComponent(this.settings.hitsComparator);
        this.potsUsedComparatorToggle = new BooleanToggleComponent(this.settings.potsUsedComparator);
        this.addSettings(this.showHoveredToggle, this.damageComparatorToggle, this.comboCounterToggle, this.hitsComparatorToggle, this.potsUsedComparatorToggle);
        this.preview = new TargetInfoPreviewComponent(this);
        this.h(this.preview, new Object[0]);
    }

    public TargetInfoSettings getSettings() {
        return this.settings;
    }

    @Override
    public void Y() {
        this.o(100.0);
        this.H(true);
    }

    @Override
    protected void renderHudModeBorder() {
    }

    @Override
    public String getName() {
        return "Target Info";
    }
}
