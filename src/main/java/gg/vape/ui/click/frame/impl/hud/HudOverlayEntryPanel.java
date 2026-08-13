package gg.vape.ui.click.frame.impl.hud;

import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.IconGlyphComponent;
import gg.vape.ui.click.component.SimpleTextLabelComponent;
import gg.vape.ui.click.frame.impl.hud.HudOverlayEntryInteractiveContainer;
import org.jetbrains.annotations.Nullable;

public final class HudOverlayEntryPanel
extends GuiComponent {
    private static final double LABEL_GAP = 4.0;
    private final HudOverlayEntryInteractiveContainer backControl;
    private final IconGlyphComponent icon;
    private static final double ICON_SIZE = 6.0;
    private final SimpleTextLabelComponent titleLabel;

    @Override
    public void H() {
        double d = this.G$src$D$1b2f02a();
        double d2 = this.n();
        double d3 = this.L();
        this.icon.K(d);
        this.icon.S(d2 + (d3 - ICON_SIZE) / 2.0);
        double labelX = this.icon.G$src$D$1b2f02a() + ICON_SIZE + LABEL_GAP;
        this.titleLabel.K(labelX);
        this.titleLabel.S(d2);
        this.titleLabel.o(Math.max(0.0, this.A() / 2.0));
        this.titleLabel.Y(d3);
        double backWidth = this.backControl.getPreferredWidth();
        this.backControl.Y(d3);
        this.backControl.o(backWidth);
        this.backControl.K(d + this.A() - backWidth);
        this.backControl.S(d2);
    }

    public HudOverlayEntryPanel(@Nullable Runnable runnable) {
        this.icon = new IconGlyphComponent("newoverlays_2x", 6.0f, 6.0f, HudOverlayEntryPanel.J.A);
        this.titleLabel = new SimpleTextLabelComponent("Members", 0.75, HudOverlayEntryPanel.J.A, true);
        this.setShowDisabledOverlay(false);
        this.icon.setSnapToPixels(true);
        this.icon.o(ICON_SIZE);
        this.icon.Y(ICON_SIZE);
        this.titleLabel.setOffsetX(0.0f);
        this.titleLabel.Y(18.0);
        this.titleLabel.setExtraHeight(0);
        this.backControl = new HudOverlayEntryInteractiveContainer(runnable);
        this.addChildren(this.icon, this.titleLabel, this.backControl);
    }

    public void setBackAction(@Nullable Runnable action) {
        this.backControl.setAction(action);
    }

    public void setTitle(String title) {
        this.titleLabel.setText(title);
    }
}
