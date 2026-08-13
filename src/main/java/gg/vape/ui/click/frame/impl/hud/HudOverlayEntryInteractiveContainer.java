package gg.vape.ui.click.frame.impl.hud;

import gg.vape.ui.click.component.IconGlyphComponent;
import gg.vape.ui.click.component.SimpleTextLabelComponent;
import gg.vape.ui.click.component.gui.InteractiveComponent;
import java.awt.Color;
import org.jetbrains.annotations.Nullable;

final class HudOverlayEntryInteractiveContainer
extends InteractiveComponent {
    private final IconGlyphComponent backIcon;
    @Nullable
    private Runnable action;
    private final SimpleTextLabelComponent label;
    private static final float ICON_SIZE = 5.0f;
    private static final double LABEL_GAP = 3.0;

    @Override
    public void H() {
        double d = this.L();
        double d2 = this.G$src$D$1b2f02a();
        double d3 = this.n();
        Color color = this.w$src$Z$e457mb() ? HudOverlayEntryInteractiveContainer.J.A : HudOverlayEntryInteractiveContainer.J.Z;
        this.backIcon.setColor(color);
        this.backIcon.K(d2);
        this.backIcon.S(d3 + (d - ICON_SIZE) / 2.0);
        this.label.setTextColor(color);
        this.label.K(this.backIcon.G$src$D$1b2f02a() + ICON_SIZE + LABEL_GAP);
        this.label.S(d3 - 0.5);
        this.label.o(this.label.getTextWidth());
        this.label.Y(d);
        this.o(this.getPreferredWidth());
    }

    double getPreferredWidth() {
        return ICON_SIZE + LABEL_GAP + this.label.getTextWidth();
    }


    private void runAction() {
        if (this.action != null) {
            this.action.run();
        }
    }

    HudOverlayEntryInteractiveContainer(@Nullable Runnable runnable) {
        this.backIcon = new IconGlyphComponent("back_arrow", ICON_SIZE, ICON_SIZE, HudOverlayEntryInteractiveContainer.J.W);
        this.label = new SimpleTextLabelComponent("Back", 0.75, HudOverlayEntryInteractiveContainer.J.A);
        this.action = runnable;
        this.setShowDisabledOverlay(false);
        this.setPropagateMouseEvents(true);
        this.w("Back");
        this.backIcon.setSnapToPixels(true);
        this.backIcon.o(ICON_SIZE);
        this.backIcon.Y(ICON_SIZE);
        this.label.setOffsetX(0.0f);
        this.label.setExtraHeight(0);
        this.label.setOffsetY(0.0f);
        this.addChildren(this.backIcon, this.label);
        this.addClickListener(this::runAction);
    }

    public void setAction(@Nullable Runnable action) {
        this.action = action;
    }
}

