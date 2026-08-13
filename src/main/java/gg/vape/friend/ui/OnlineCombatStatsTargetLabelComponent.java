package gg.vape.friend.ui;

import gg.vape.friend.ui.OnlineCombatStatsSettingsFrame;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.ImageRenderer;
import java.awt.Color;

public class OnlineCombatStatsTargetLabelComponent
extends GuiComponent {
    private final OnlineCombatStatsSettingsFrame statsFrame;

    private void renderTargetLabel() {
        SmoothFontRenderer fontRenderer = this.getFontRenderer(0.9);
        double textHeight = fontRenderer.d("Aim");
        double textY = this.n() + this.L() / 2.0 - textHeight / 2.0;
        double iconY = this.n() + this.L() / 2.0 - 3.0;
        Color color = this.statsFrame.isManagedByClickGui() ? this.statsFrame.applyDefaultEditorAlpha(OnlineCombatStatsTargetLabelComponent.J.Z) : OnlineCombatStatsTargetLabelComponent.J.Z;
        ImageRenderer.drawImage(color, (float)this.G$src$D$1b2f02a() + 5.0f, (float)iconY, "newaim", 6.0f, 6.0f, false);
        fontRenderer.d(this.statsFrame.getTargetLabel(), this.G$src$D$1b2f02a() + 8.0 + 10.0, textY, color);
    }

    @Override
    public void u() {
    }

    @Override
    public void I() {
        this.renderTargetLabel();
    }


    public OnlineCombatStatsTargetLabelComponent(OnlineCombatStatsSettingsFrame onlineCombatStatsSettingsFrame) {
        this.statsFrame = onlineCombatStatsSettingsFrame;
    }

    @Override
    public void H() {
        this.renderTargetLabel();
    }

    @Override
    public void F() {
    }

    @Override
    public double x() {
        return 110.0;
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    @Override
    public double C() {
        return 20.0;
    }
}

