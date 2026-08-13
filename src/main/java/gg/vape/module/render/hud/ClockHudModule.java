package gg.vape.module.render.hud;

import gg.vape.module.render.hud.HudModule;
import gg.vape.module.render.hud.HudModuleGroup;
import gg.vape.ui.click.frame.impl.hud.ClockHudFrame;
import gg.vape.unmap.ModeOption;
import gg.vape.value.BooleanValue;
import gg.vape.value.ModeValue;

public class ClockHudModule
extends HudModule {
    private final ModeOption digitalMode;
    public final ModeValue clockType;
    public final BooleanValue showDate;
    private final ModeOption analogMode;
    public final BooleanValue use24HourTime = BooleanValue.create(this, "24 Hour Time", false);

    public ClockHudModule() {
        super("Clock", HudModuleGroup.HUD, "clock_mod", ClockHudFrame.class);
        this.showDate = BooleanValue.create(this, "Show date", true);
        this.analogMode = new ModeOption("Analog");
        this.digitalMode = new ModeOption("Digital");
        this.clockType = ModeValue.create((Object)this, "Clock Type", this.analogMode, this.analogMode, this.digitalMode);
        this.setSuffix("Draws a clock with the current real-world time");
        this.clockType.addModeDependentValues(this.digitalMode, this.showDate);
        this.addValue(this.clockType, this.showDate, this.use24HourTime);
    }
}

