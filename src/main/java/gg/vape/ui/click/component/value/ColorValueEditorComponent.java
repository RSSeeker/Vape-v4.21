package gg.vape.ui.click.component.value;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.MousePosition;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.IconButtonComponent;
import gg.vape.ui.click.component.value.ColorChannelSliderComponent;
import gg.vape.ui.click.component.value.ColorChannelType;
import gg.vape.ui.click.component.value.ColorPreviewSwatchComponent;
import gg.vape.ui.click.component.value.ColorValueEditorToggleExpandedClickHandler;
import gg.vape.ui.click.frame.FrameHeaderComponent;
import gg.vape.utils.render.ImageRenderer;
import gg.vape.utils.render.RenderUtils;
import gg.vape.value.ColorValue;
import java.awt.Color;

public class ColorValueEditorComponent
extends GuiComponent {
    private ColorChannelSliderComponent vibranceSlider;
    private boolean collapsed = true;
    private boolean suppressChildMouseDispatch;
    private ColorChannelSliderComponent rainbowSlider;
    private ColorChannelSliderComponent opacitySlider;
    private IconButtonComponent collapseButton = new IconButtonComponent("upcollapse", 0.3);
    private final ColorPreviewSwatchComponent previewSwatch;
    private ColorChannelSliderComponent saturationSlider;
    private static String legacyInteractionMarker;
    private ColorValue colorValue;

    @Override
    public void I() {
    }

    @Override
    public void H() {
        this.onDisable();
        this.rainbowSlider.K(this.G$src$D$1b2f02a());
        this.rainbowSlider.S(this.n());
        this.collapseButton.K(this.G$src$D$1b2f02a() + this.rainbowSlider.getLabelWidth() + 5.0);
        this.collapseButton.S(this.n() + 2.5 - 2.0);
        this.collapseButton.Y(this.rainbowSlider.L() / 2.0);
        this.collapseButton.o(10.0);
        this.collapseButton.setIconResource(this.collapsed ? "downexpand" : "upcollapse");
        ImageRenderer.drawImage(this.colorValue.getMutableColor(), (float)(this.G$src$D$1b2f02a() + this.A() - 5.0 - 6.0), (float)this.n() + 5.0f, "colorpreview", 6.0f, 6.0f, false);
        this.previewSwatch.K(this.G$src$D$1b2f02a() + this.A() - 10.0 - 5.0 - 6.0);
        this.previewSwatch.S(this.n() + 5.0);
        if (this.collapsed) {
            this.saturationSlider.setVisible(false);
            this.vibranceSlider.setVisible(false);
            this.opacitySlider.setVisible(false);
        } else {
            this.saturationSlider.setVisible(true);
            this.saturationSlider.K(this.G$src$D$1b2f02a());
            this.saturationSlider.S(this.n() + 20.0 + 5.0);
            this.vibranceSlider.setVisible(true);
            this.vibranceSlider.K(this.G$src$D$1b2f02a());
            this.vibranceSlider.S(this.n() + 40.0 + 10.0);
            this.opacitySlider.setVisible(true);
            this.opacitySlider.K(this.G$src$D$1b2f02a());
            this.opacitySlider.S(this.n() + 60.0 + 15.0);
        }
    }

    @Override
    public void setUseExplicitWidth(boolean bl) {
        super.setUseExplicitWidth(bl);
        this.rainbowSlider.setUseExplicitWidth(bl);
        this.saturationSlider.setUseExplicitWidth(bl);
        this.vibranceSlider.setUseExplicitWidth(bl);
        this.opacitySlider.setUseExplicitWidth(bl);
    }

    @Override
    public void F() {
        MousePosition mousePosition = RenderUtils.h();
    }

    public static String getLegacyInteractionMarker() {
        return legacyInteractionMarker;
    }

    public static void setLegacyInteractionMarker(String marker) {
        legacyInteractionMarker = marker;
    }

    public ColorValueEditorComponent(ColorValue colorValue) {
        this.colorValue = colorValue;
        this.bindValue(colorValue);
        this.rainbowSlider = new ColorChannelSliderComponent(ColorChannelType.RAINBOW, colorValue);
        this.rainbowSlider.setFallbackColorComponent(this);
        this.saturationSlider = new ColorChannelSliderComponent(ColorChannelType.SATURATION, colorValue);
        this.vibranceSlider = new ColorChannelSliderComponent(ColorChannelType.VIBRANCE, colorValue);
        this.opacitySlider = new ColorChannelSliderComponent(ColorChannelType.OPACITY, colorValue);
        this.previewSwatch = new ColorPreviewSwatchComponent(colorValue);
        this.rainbowSlider.setDisabledOverlayColor(Color.WHITE);
        this.saturationSlider.setDisabledOverlayColor(ColorValueEditorComponent.J.r);
        this.vibranceSlider.setDisabledOverlayColor(ColorValueEditorComponent.J.r);
        this.opacitySlider.setDisabledOverlayColor(ColorValueEditorComponent.J.r);
        this.saturationSlider.setToolTips(null);
        this.vibranceSlider.setToolTips(null);
        this.opacitySlider.setToolTips(null);
        this.collapseButton.addClickListener(new ColorValueEditorToggleExpandedClickHandler(this));
        this.addChildren(this.collapseButton, this.previewSwatch, this.rainbowSlider, this.saturationSlider, this.vibranceSlider, this.opacitySlider);
    }

    static {
        ColorValueEditorComponent.setLegacyInteractionMarker(null);
    }

    @Override
    public void u() {
    }

    @Override
    public void dispatchMouseEvent(GuiMouseEvent guiMouseEvent) {
        if (this.suppressChildMouseDispatch) {
            return;
        }
        for (GuiComponent guiComponent : this.f()) {
            if (!guiComponent.V$src$Z$1xhop3l() || !guiComponent.w$src$Z$e457mb()) continue;
            guiComponent.dispatchMouseEvent(guiMouseEvent);
            if (guiComponent instanceof FrameHeaderComponent) continue;
            return;
        }
    }

    @Override
    public void setExplicitWidth(double d) {
        super.setExplicitWidth(d);
        this.rainbowSlider.setExplicitWidth(d);
        this.saturationSlider.setExplicitWidth(d);
        this.vibranceSlider.setExplicitWidth(d);
        this.opacitySlider.setExplicitWidth(d);
    }

    @Override
    public void o(double d) {
        super.o(d);
        this.rainbowSlider.o(d);
        this.saturationSlider.o(d);
        this.vibranceSlider.o(d);
        this.opacitySlider.o(d);
    }

    @Override
    public double x() {
        return 110.0;
    }

    public static boolean setCollapsedCompat(ColorValueEditorComponent editor, boolean collapsed) {
        editor.collapsed = collapsed;
        return editor.collapsed;
    }

    public static boolean isCollapsedCompat(ColorValueEditorComponent editor) {
        return editor.collapsed;
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    @Override
    public double C() {
        return this.collapsed ? 25.0 : 104.0;
    }

}

