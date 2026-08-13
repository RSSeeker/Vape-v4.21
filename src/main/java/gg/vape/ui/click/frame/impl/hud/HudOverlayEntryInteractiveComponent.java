package gg.vape.ui.click.frame.impl.hud;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.IconGlyphComponent;
import gg.vape.ui.click.component.gui.InteractiveComponent;
import gg.vape.ui.click.frame.Frame;
import gg.vape.unmap.ColorUtil;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;
import java.util.function.BooleanSupplier;
import org.jetbrains.annotations.Nullable;

public class HudOverlayEntryInteractiveComponent
extends InteractiveComponent {
    private static final Color INACTIVE_BACKGROUND = new Color(0, 0, 0, 63);
    private static final Color HOVER_OVERLAY = new Color(255, 255, 255, 10);
    private String tooltip;
    @Nullable
    private Runnable action;
    @Nullable
    private Class<? extends Frame> frameClass;
    @Nullable
    private BooleanSupplier selectedSupplier;
    private final IconGlyphComponent icon;


    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
        this.w(tooltip);
    }

    public void setSelectedSupplier(@Nullable BooleanSupplier selectedSupplier) {
        this.selectedSupplier = selectedSupplier;
    }

    public void setFrameClass(@Nullable Class<? extends Frame> frameClass) {
        this.frameClass = frameClass;
    }

    private static boolean isFrameEnabled(@Nullable Frame frame) {
        return frame != null
                && frame.V$src$Z$1xhop3l()
                && frame.y$src$Z$1f55jvh();
    }

    private boolean isSelected() {
        if (this.selectedSupplier != null) {
            return this.selectedSupplier.getAsBoolean();
        }
        if (this.frameClass == null) {
            return false;
        }
        Frame frame = ClientSettings.getFrame(this.frameClass);
        return isFrameEnabled(frame);
    }

    @Override
    public void H() {
        boolean selected = this.isSelected();
        boolean hovered = this.w$src$Z$e457mb();
        double d = this.G$src$D$1b2f02a();
        double d2 = this.n();
        double d3 = this.A();
        Color background = selected
                ? ClientSettings.INSTANCE.getAccentColor()
                : INACTIVE_BACKGROUND;
        if (selected) {
            GuiRenderPrimitives.I(d, d2 - 1.0, d3, d3,
                    ColorUtil.offsetRgb(background, 40.0), false, 4.0f, 1.0f,
                    8.0f, HudOverlayEntryInteractiveComponent.J.u);
            GuiRenderPrimitives.I(d, d2 + 1.0, d3, d3,
                    ColorUtil.offsetRgb(background, -15.0), false, 4.0f, 1.0f,
                    8.0f, HudOverlayEntryInteractiveComponent.J.u);
        }
        GuiRenderPrimitives.I(d, d2, d3, d3, background, false, 4.0f, 1.0f,
                8.0f, HudOverlayEntryInteractiveComponent.J.u);
        if (!selected && hovered) {
            GuiRenderPrimitives.I(d, d2, d3, d3, HOVER_OVERLAY, false, 4.0f,
                    1.0f, 8.0f, HudOverlayEntryInteractiveComponent.J.u);
        }
        this.icon.setColor(selected ? J.B() : (hovered ? J.f : J.W));
        this.icon.K(d + (d3 - 6.0) / 2.0);
        this.icon.S(d2 + (d3 - 6.0) / 2.0);
    }

    public HudOverlayEntryInteractiveComponent(String string, String string2) {
        this.icon = new IconGlyphComponent(string, 6.0f, 6.0f);
        this.icon.setSnapToPixels(true);
        this.icon.o(6.0);
        this.icon.Y(6.0);
        this.tooltip = string2;
        this.w(string2);
        this.o(22.0);
        this.Y(22.0);
        this.setShowDisabledOverlay(false);
        this.setPropagateMouseEvents(true);
        this.addChildren(this.icon);
        this.addClickListener(this::handleClick);
    }

    private void handleClick() {
        this.toggleFrameEnabled();
        if (this.action != null) {
            this.action.run();
        }
    }

    private void toggleFrameEnabled() {
        if (this.frameClass == null) {
            return;
        }
        Frame frame = ClientSettings.getFrame(this.frameClass);
        if (frame != null) {
            boolean enabled = !isFrameEnabled(frame);
            frame.setVisible(enabled);
            frame.c(enabled);
            frame.U();
            frame.l$src$V$1mibm4x();
        }
    }

    public void setAction(@Nullable Runnable action) {
        this.action = action;
    }

    public String getTooltip() {
        return this.tooltip;
    }
}

