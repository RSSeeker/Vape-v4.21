package gg.vape.ui.click.frame.impl.hud;

import gg.vape.Vape;
import gg.vape.module.render.hud.ReachDisplayHudModule;
import gg.vape.ui.click.frame.impl.hud.HudModuleConfigFrameBase;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.unmap.NumberFormat;

public class ReachDisplayHudFrame
extends HudModuleConfigFrameBase {
    private final NumberFormat distanceFormat = new NumberFormat("0.00");
    private final ReachDisplayHudModule reachDisplay = (ReachDisplayHudModule)this.getModule();

    @Override
    public String getName() {
        return "ReachDisplayFrame";
    }

    @Override
    public void renderHudContent() {
        SmoothFontRenderer smoothFontRenderer = Vape.INSTANCE.getFontManager().K(1.0, true);
        String distanceText = this.distanceFormat.format(this.reachDisplay.getLastReach());
        if (this.reachDisplay.getLastReach() == 0.0f) {
            distanceText = "0.00";
        }
        distanceText += " blocks";
        smoothFontRenderer.d(distanceText, this.G$src$D$1b2f02a() + 5.0,
                this.n() + 5.5, this.getEditorForegroundColor());
    }

    @Override
    public double A() {
        return 50.0;
    }

    @Override
    public double L() {
        return 20.0;
    }

    public ReachDisplayHudFrame() {
        super(ReachDisplayHudModule.class);
    }
}
