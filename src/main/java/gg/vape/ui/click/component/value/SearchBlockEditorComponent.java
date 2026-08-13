package gg.vape.ui.click.component.value;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.SquareIconButtonComponent;
import gg.vape.ui.click.component.value.BooleanToggleComponent;
import gg.vape.ui.click.component.value.ColorChannelSliderComponent;
import gg.vape.ui.click.component.value.ColorChannelType;
import gg.vape.ui.click.component.value.SearchBlockEditorEnabledSyncMouseListener;
import gg.vape.ui.click.component.value.SearchBlockEditorMouseListener;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.ui.unmap.SearchBlock;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ImageRenderer;
import gg.vape.utils.render.RenderUtils;
import gg.vape.value.BooleanValue;
import gg.vape.value.ColorValue;
import java.awt.Color;

public class SearchBlockEditorComponent
extends GuiComponent {
    private static final float HORIZONTAL_PADDING = 5.0f;
    private static final double HEADER_HEIGHT = 18.0;
    private boolean hovered;
    private final BooleanToggleComponent tracersToggle;
    private boolean expanded;
    private final BooleanValue enabledValue = BooleanValue.create(null, "", false);
    private final SquareIconButtonComponent removeButton;
    private final ColorChannelSliderComponent vibranceSlider;
    private final ColorValue colorValue;
    private final ColorChannelSliderComponent rainbowSlider;
    private final SearchBlock searchBlock;
    private final BooleanValue tracersValue;
    private final ColorChannelSliderComponent saturationSlider;
    private final BooleanToggleComponent enabledToggle = new BooleanToggleComponent(this.enabledValue);

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
        if ((double)guiMouseEvent.getY() > this.n() + 20.0 - 2.0) {
            return;
        }
        this.expanded = !this.expanded;
        this.getParentFrameComponent().l$src$V$1mibm4x();
    }

    public boolean isEnabledToggleOn() {
        return this.enabledToggle.isOn();
    }

    @Override
    public void F() {
        this.hovered = true;
    }

    @Override
    public double C() {
        return (this.expanded ? 110.0 : HEADER_HEIGHT) + 3.0;
    }

    public SearchBlockEditorComponent setRemoveClickListener(GuiClickListener guiClickListener) {
        this.removeButton.addClickListener(guiClickListener);
        return this;
    }

    @Override
    public void u() {
        if (this.hovered && !this.w$src$Z$e457mb()) {
            this.hovered = false;
        }
    }

    public SearchBlockEditorComponent(SearchBlock searchBlock) {
        this.tracersValue = BooleanValue.create(null, "Tracers", false);
        this.tracersToggle = new BooleanToggleComponent(this.tracersValue);
        this.removeButton = new SquareIconButtonComponent("newclose");
        this.searchBlock = searchBlock;
        this.colorValue = ColorValue.create(null, searchBlock.d(), searchBlock.B());
        this.rainbowSlider = new ColorChannelSliderComponent(ColorChannelType.RAINBOW, this.colorValue, "Color", 1.0);
        this.rainbowSlider.setFallbackColorComponent(this);
        this.saturationSlider = new ColorChannelSliderComponent(ColorChannelType.SATURATION, this.colorValue, "", 1.0);
        this.vibranceSlider = new ColorChannelSliderComponent(ColorChannelType.VIBRANCE, this.colorValue, "", 1.0);
        this.rainbowSlider.setUseExplicitWidth(true);
        this.rainbowSlider.o(98.0);
        this.saturationSlider.setUseExplicitWidth(true);
        this.saturationSlider.o(98.0);
        this.vibranceSlider.setUseExplicitWidth(true);
        this.vibranceSlider.o(98.0);
        this.rainbowSlider.setDisabledOverlayColor(SearchBlockEditorComponent.J.m);
        this.saturationSlider.setDisabledOverlayColor(SearchBlockEditorComponent.J.m);
        this.vibranceSlider.setDisabledOverlayColor(SearchBlockEditorComponent.J.m);
        this.enabledValue.setValue(searchBlock.T());
        this.enabledToggle.synchronizeAnimationsImmediately();
        this.enabledToggle.addMouseListener(new SearchBlockEditorEnabledSyncMouseListener(this, searchBlock));
        this.tracersValue.setValue(searchBlock.W());
        this.tracersToggle.synchronizeAnimationsImmediately();
        this.tracersToggle.addMouseListener(new SearchBlockEditorMouseListener(this, searchBlock));
        searchBlock.c(this.colorValue.getMutableColor().getRGB());
        this.addChildren(this.enabledToggle, this.removeButton, this.tracersToggle, this.rainbowSlider, this.saturationSlider, this.vibranceSlider);
    }

    public static BooleanValue getTracersValueCompat(SearchBlockEditorComponent component) {
        return component.tracersValue;
    }

    @Override
    public void H() {
        SmoothFontRenderer fontRenderer = this.getFontRenderer(0.9);
        String blockName = this.searchBlock.d();
        double textHeight = fontRenderer.d(blockName);
        double contentHeight = this.L() - 3.0;
        Color textColor = SearchBlockEditorComponent.J.Z;
        double borderX = this.G$src$D$1b2f02a() + HORIZONTAL_PADDING - 0.5;
        double borderY = this.n() + 1.0 - 0.5;
        double borderWidth = this.A() - (double)(HORIZONTAL_PADDING * 2.0f) + 1.0;
        GuiRenderPrimitives.d(borderX, borderY, borderWidth, contentHeight - 2.0 + 1.0, this.searchBlock.B());
        double panelX = this.G$src$D$1b2f02a() + HORIZONTAL_PADDING;
        double panelY = this.n() + 1.0;
        double panelWidth = this.A() - (double)(HORIZONTAL_PADDING * 2.0f);
        GuiRenderPrimitives.d(panelX, panelY, panelWidth, contentHeight - 2.0, SearchBlockEditorComponent.J.m);
        float expandIconX = (float)this.G$src$D$1b2f02a() + HORIZONTAL_PADDING * 2.0f;
        float expandIconY = (float)(this.n() + (double)(HORIZONTAL_PADDING * 2.0f)) - 2.0f;
        ImageRenderer.drawImage(Color.WHITE, expandIconX, expandIconY, this.expanded ? "upcollapse" : "downexpand", 2.0f, 2.0f, false);
        double enabledToggleX = this.G$src$D$1b2f02a() + this.A() - 30.0;
        double textY = this.n() + HEADER_HEIGHT / 2.0 - textHeight / 2.0;
        RenderUtils.m(this.G$src$D$1b2f02a(), this.n(), enabledToggleX - this.G$src$D$1b2f02a(), HEADER_HEIGHT);
        double textX = this.G$src$D$1b2f02a() + (double)(HORIZONTAL_PADDING * 3.0f) + 8.0;
        fontRenderer.d(blockName, textX, textY, textColor);
        RenderUtils.T();
        double fadeRight = enabledToggleX;
        double fadeLeft = fadeRight - 10.0;
        double fadeTop = textY - 2.0;
        double fadeBottom = fadeTop + textHeight + 2.0;
        RenderUtils.X(new Color(SearchBlockEditorComponent.J.m.getRed(), SearchBlockEditorComponent.J.m.getGreen(), SearchBlockEditorComponent.J.m.getBlue(), 0), SearchBlockEditorComponent.J.m, fadeRight, fadeBottom, fadeRight, fadeTop, fadeLeft, fadeTop, fadeLeft, fadeBottom);
        double removeButtonX = this.G$src$D$1b2f02a() + this.A() - HORIZONTAL_PADDING * 1.5 - 8.0;
        this.removeButton.K(removeButtonX);
        this.removeButton.S(this.n());
        this.removeButton.Y(HEADER_HEIGHT);
        this.enabledToggle.setShowDisabledOverlay(false);
        this.enabledToggle.K(enabledToggleX);
        this.enabledToggle.S(this.n());
        this.enabledToggle.o(12.0);
        this.enabledToggle.Y(HEADER_HEIGHT);
        this.enabledToggle.setUseExplicitWidth(true);
        this.rainbowSlider.setVisible(this.expanded);
        this.saturationSlider.setVisible(this.expanded);
        this.vibranceSlider.setVisible(this.expanded);
        this.tracersToggle.setVisible(this.expanded);
        this.rainbowSlider.K(this.G$src$D$1b2f02a() + 6.0);
        this.saturationSlider.K(this.G$src$D$1b2f02a() + 6.0);
        this.vibranceSlider.K(this.G$src$D$1b2f02a() + 6.0);
        this.rainbowSlider.S(this.n() + 16.0);
        this.saturationSlider.S(this.n() + 40.0);
        this.vibranceSlider.S(this.n() + 65.0);
        this.tracersToggle.setShowDisabledOverlay(false);
        this.tracersToggle.K(this.G$src$D$1b2f02a() + 8.0);
        this.tracersToggle.S(90.0 + this.n());
        this.tracersToggle.o(this.A() - 16.0);
        this.tracersToggle.setUseExplicitWidth(true);
        this.tracersToggle.Y(12.0);
        this.searchBlock.c(this.colorValue.toRgb());
    }


    public static BooleanValue getEnabledValueCompat(SearchBlockEditorComponent component) {
        return component.enabledValue;
    }

    @Override
    public double x() {
        return 110.0;
    }

    public void setEnabledToggleState(boolean enabled) {
        this.enabledToggle.setValue(enabled);
    }

    @Override
    public void I() {
    }
}

