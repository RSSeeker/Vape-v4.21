package gg.vape.ui.click.component.value;

import func.skidline.RectData;
import gg.vape.Vape;
import gg.vape.input.MouseInput;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.MouseButton;
import gg.vape.ui.click.animation.DoubleAnimation;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.gui.TextLabel;
import gg.vape.ui.click.component.value.ColorChannelType;
import gg.vape.ui.click.component.value.ColorPaletteRefreshClickListener;
import gg.vape.ui.click.component.value.ColorPaletteSliderComponent;
import gg.vape.ui.click.component.value.ColorValueEditorComponent;
import gg.vape.ui.click.component.value.SliderComponentBase;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.MutableColor;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.RenderUtils;
import gg.vape.value.ColorValue;
import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ColorChannelSliderComponent
extends SliderComponentBase {
    private DoubleAnimation handlePositionAnimation = new DoubleAnimation(0.0, 0.0, 0.0);
    private double maximum = 255.0;
    private TextLabel resetLabel;
    private Color labelColor;
    private double labelWidth = 0.0;
    private ColorValue colorValue;
    private final float valueScale;
    private RectData handleBounds;
    private long lastClickTimestamp;
    private boolean hovered;
    protected DoubleAnimation handleHoverAnimation = new DoubleAnimation(0.15, 7.0, 8.0);
    private double step;
    private double valuePerPercent;
    private double fontScale = 0.75;
    private GuiComponent fallbackColorComponent;
    private Double legacyDragValueSentinel;
    private ColorChannelType channelType;
    private static final String RESET_TEXT = "RESET";
    private double lastNormalizedValue = -1.0;
    private double minimum;

    public void setMinimum(double minimum) {
        this.minimum = minimum;
    }

    @Override
    public double x() {
        return 110.0;
    }

    private void handleColorValueChanged(ColorValue changedValue) {
        this.synchronizeHandlePosition();
    }

    private float[] createGradientStop(double trackOffset, double trackWidth) {
        float channelRatio = (float)(trackOffset / trackWidth);
        switch (this.channelType) {
            case BLOCK_CHILD: 
            case RAINBOW: {
                return new float[]{channelRatio, 1.0f, 1.0f, 1.0f};
            }
            case SATURATION: {
                return new float[]{this.colorValue.getHueRatio(), channelRatio, this.colorValue.getBrightnessRatio(), 1.0f};
            }
            case VIBRANCE: {
                return new float[]{this.colorValue.getHueRatio(), this.colorValue.getSaturationRatio(), channelRatio, 1.0f};
            }
            case OPACITY: {
                return new float[]{this.colorValue.getHueRatio(), this.colorValue.getSaturationRatio(), this.colorValue.getBrightnessRatio(), channelRatio};
            }
        }
        return new float[]{0.0f, 0.0f, 0.0f, 1.0f};
    }

    public void setStep(double step) {
        this.step = step;
    }

    @Override
    public void u() {
        if (this.hovered && !this.w$src$Z$e457mb()) {
            this.handleHoverAnimation.J();
            this.hovered = false;
        }
        if (this.colorValue.isRainbowEnabled()) {
            this.colorValue.advanceRainbowHue();
        }
    }

    private void updateDraggingValue() {
        if (this.dragging) {
            if (!MouseInput.isButtonDown(MouseButton.LEFT_CLICK.ordinal())) {
                this.dragging = false;
                return;
            }
            double trackWidth = this.A() - 10.0 - this.handleBounds.e();
            double trackOffset = (double)this.dragStartMousePosition.O - this.G$src$D$1b2f02a() + this.getMouseDeltaX() - 5.0 - this.handleBounds.e() / 2.0;
            double trackStart = this.G$src$D$1b2f02a() + 5.0 + this.handleBounds.e() / 2.0;
            double trackEnd = this.G$src$D$1b2f02a() + this.A() - 5.0 - this.handleBounds.e() / 2.0;
            double updatedValue = this.mapTrackOffsetToValue(this.minimum, this.maximum, trackStart, trackEnd, this.step, trackOffset);
            updatedValue = new BigDecimal("" + updatedValue).setScale(3, RoundingMode.HALF_UP).doubleValue();
            if (this.colorValue != null) {
                switch (this.channelType) {
                    case BLOCK_CHILD: 
                    case RAINBOW: {
                        if (((Double)this.colorValue.getHueValue().getValue()).equals(this.legacyDragValueSentinel)) {
                            return;
                        }
                        this.colorValue.getHueValue().setValue(updatedValue);
                        break;
                    }
                    case SATURATION: {
                        if (((Double)this.colorValue.getSaturationValue().getValue()).equals(this.legacyDragValueSentinel)) {
                            return;
                        }
                        this.colorValue.getSaturationValue().setValue(updatedValue);
                        break;
                    }
                    case VIBRANCE: {
                        if (((Double)this.colorValue.getHueValue().getValue()).equals(this.legacyDragValueSentinel)) {
                            return;
                        }
                        this.colorValue.getBrightnessValue().setValue(updatedValue);
                        break;
                    }
                    case OPACITY: {
                        if (((Double)this.colorValue.getAlphaValue().getValue()).equals(this.legacyDragValueSentinel)) {
                            return;
                        }
                        this.colorValue.getAlphaValue().setValue(updatedValue);
                    }
                }
            }
        }
    }

    public void setMaximum(double maximum) {
        this.maximum = maximum;
    }

    public double getLabelWidth() {
        return this.labelWidth;
    }

    public double getStep() {
        return this.step;
    }

    public Color getChannelColor(double channelValue, double maximumValue) {
        switch (this.channelType) {
            case BLOCK_CHILD: 
            case RAINBOW: {
                return new Color(Color.HSBtoRGB((float)(channelValue / maximumValue), 1.0f, 1.0f));
            }
            case SATURATION: {
                return new Color(Color.HSBtoRGB(this.colorValue.getHueRatio(), (float)(channelValue / maximumValue), this.colorValue.getBrightnessRatio()));
            }
            case VIBRANCE: {
                return new Color(Color.HSBtoRGB(this.colorValue.getHueRatio(), this.colorValue.getSaturationRatio(), (float)(channelValue / maximumValue)));
            }
            case OPACITY: {
                MutableColor mutableColor = new MutableColor(Color.HSBtoRGB(this.colorValue.getHueRatio(), this.colorValue.getSaturationRatio(), this.colorValue.getBrightnessRatio()));
                mutableColor.withAlpha((int)(255.0 * (channelValue / maximumValue)));
                return mutableColor;
            }
        }
        return new Color(0, 0, 0);
    }

    @Override
    public void H() {
        Object resolvedBackgroundColor;
        double rightTrackOffset;
        double rightTrackWidth;
        double handleTrackPadding;
        double resetLabelWidth;
        this.updateDraggingValue();
        if (!this.getDisabledOverlayColor().equals(Color.WHITE)) {
            this.onDisable();
        }
        double normalizedValue = 0.0;
        switch (this.channelType) {
            case BLOCK_CHILD: 
            case RAINBOW: {
                normalizedValue = (Double)this.colorValue.getHueValue().getValue() / this.maximum;
                break;
            }
            case SATURATION: {
                normalizedValue = (Double)this.colorValue.getSaturationValue().getValue() / this.maximum;
                break;
            }
            case VIBRANCE: {
                normalizedValue = (Double)this.colorValue.getBrightnessValue().getValue() / this.maximum;
                break;
            }
            case OPACITY: {
                normalizedValue = (Double)this.colorValue.getAlphaValue().getValue() / this.maximum;
            }
        }
        if ((normalizedValue *= (double)this.valueScale) != this.legacyDragValueSentinel) {
            this.synchronizeHandlePosition();
        }
        SmoothFontRenderer fontRenderer = this.getFontRenderer(this.fontScale);
        String displayLabel = this.channelType.equals((Object)ColorChannelType.RAINBOW) ? this.getLabel() : this.channelType.getDisplayName();
        double labelHeight = fontRenderer.d(displayLabel);
        this.labelWidth = fontRenderer.N(this.getLabel());
        double trackY = this.n() + 12.5 + labelHeight;
        double trackWidth = this.A() - 10.0;
        double handleCenterX = this.G$src$D$1b2f02a() + this.handlePositionAnimation.getInterpolatedValue();
        if (this.channelType.equals((Object)ColorChannelType.RAINBOW)) {
            fontRenderer.d(this.getLabel(), this.G$src$D$1b2f02a() + 5.0, this.n() + 5.0, this.labelColor);
        } else {
            fontRenderer.d(this.channelType.getDisplayName(), this.G$src$D$1b2f02a() + 5.0, this.n() + 5.0, this.labelColor);
        }
        if (this.resetLabel != null) {
            resetLabelWidth = fontRenderer.N(this.resetLabel.getText());
            this.resetLabel.K(this.G$src$D$1b2f02a() + this.A() - 5.0 - resetLabelWidth - 2.0);
            this.resetLabel.S(this.n() + 2.0);
            this.resetLabel.Y(10.0);
            this.resetLabel.o(resetLabelWidth);
            this.resetLabel.setFontScale(0.8);
        }
        this.handleBounds = this.createHandleBounds(handleCenterX, trackY + 0.5, this.handleHoverAnimation.getEndValue() / 2.0);
        resetLabelWidth = 5.0;
        double usableTrackWidth = this.A() - resetLabelWidth * 2.0;
        double trackStartX = this.G$src$D$1b2f02a() + resetLabelWidth;
        double leftTrackWidth = handleCenterX - trackStartX - (handleTrackPadding = this.handleHoverAnimation.getEndValue() / 2.0 + 0.5);
        if (leftTrackWidth >= 2.0) {
            float[] leftGradientStart = this.createGradientStop(0.0, usableTrackWidth);
            float[] leftGradientEnd = this.createGradientStop(leftTrackWidth, usableTrackWidth);
            GuiRenderPrimitives.F(trackStartX, trackY, leftTrackWidth, 2.0, leftGradientStart, leftGradientEnd);
        }
        if ((rightTrackWidth = usableTrackWidth - (rightTrackOffset = handleCenterX - trackStartX + handleTrackPadding)) >= 2.0) {
            float[] gradientStart = this.createGradientStop(rightTrackOffset, usableTrackWidth);
            float[] gradientEnd = this.createGradientStop(usableTrackWidth, usableTrackWidth);
            GuiRenderPrimitives.F(trackStartX + rightTrackOffset, trackY, rightTrackWidth, 2.0, gradientStart, gradientEnd);
        }
        resolvedBackgroundColor = this.getDisabledOverlayColor().equals(Color.WHITE) ? this.fallbackColorComponent.getDisabledOverlayColor() : this.getDisabledOverlayColor();
        double handleSize = this.handleHoverAnimation.getInterpolatedValue();
        GuiRenderPrimitives.Y((float)(this.handleBounds.o() + this.handleBounds.e() / 2.0 - handleSize / 2.0), (float)(this.handleBounds.W() + this.handleBounds.R() / 2.0 - handleSize / 2.0), (float)handleSize, (float)(0.8 / Vape.INSTANCE.getClientSettings().getGuiScaleFactor()), ColorChannelSliderComponent.J.A, 0.0, null);
    }

    public ColorChannelSliderComponent(ColorChannelType channelType, ColorValue colorValue) {
        this(channelType, colorValue, colorValue.getName(), 1.0);
        this.bindValue(colorValue);
        colorValue.addChangeListener(this::handleColorValueChanged);
        this.synchronizeHandlePosition();
    }

    @Override
    public void F() {
        if (!this.hovered) {
            this.handleHoverAnimation.J();
        }
        this.hovered = true;
    }

    public ColorChannelSliderComponent(ColorChannelType channelType, ColorValue colorValue, String label, double step) {
        super(label);
        this.handleBounds = new RectData(0.0, 0.0, 0.0, 0.0);
        this.labelColor = ColorChannelSliderComponent.J.Z;
        this.legacyDragValueSentinel = -1.0;
        this.colorValue = colorValue;
        this.channelType = channelType;
        this.step = step;
        this.valuePerPercent = (this.maximum - this.minimum) / 100.0;
        if (channelType.equals((Object)ColorChannelType.RAINBOW)) {
            // empty if block
        }
        this.valueScale = (float)(this.maximum / colorValue.getHueMaximum());
    }

    public double getMaximum() {
        return this.maximum;
    }

    @Override
    public double C() {
        return 25.0;
    }

    private void synchronizeHandlePosition() {
        double trackWidth = this.A() - 10.0;
        double normalizedValue = 0.0;
        switch (this.channelType) {
            case BLOCK_CHILD: 
            case RAINBOW: {
                normalizedValue = (Double)this.colorValue.getHueValue().getValue() / this.maximum;
                break;
            }
            case SATURATION: {
                normalizedValue = (Double)this.colorValue.getSaturationValue().getValue() / this.maximum;
                break;
            }
            case VIBRANCE: {
                normalizedValue = (Double)this.colorValue.getBrightnessValue().getValue() / this.maximum;
                break;
            }
            case OPACITY: {
                normalizedValue = (Double)this.colorValue.getAlphaValue().getValue() / this.maximum;
            }
        }
        double targetHandleX = (trackWidth - this.handleHoverAnimation.getEndValue()) * (normalizedValue *= (double)this.valueScale) + 5.0 + this.handleHoverAnimation.getEndValue() / 2.0;
        double currentHandleX = this.handlePositionAnimation.getInterpolatedValue();
        double animationDuration = 0.05;
        if (this.channelType == ColorChannelType.BLOCK_CHILD || this.channelType == ColorChannelType.RAINBOW) {
            if (this.colorValue.isRainbowEnabled() && normalizedValue == 0.0) {
                animationDuration = 0.0;
            }
        } else if (this.colorValue.isRainbowEnabled() && this.colorValue.isColorTransformEnabled()) {
            animationDuration = 1.0;
        }
        this.handlePositionAnimation = new DoubleAnimation(animationDuration, currentHandleX, targetHandleX);
        this.lastNormalizedValue = normalizedValue;
        this.handlePositionAnimation.c();
    }

    public void setFallbackColorComponent(GuiComponent fallbackColorComponent) {
        this.fallbackColorComponent = fallbackColorComponent;
    }

    public double getMinimum() {
        return this.minimum;
    }


    public ColorChannelSliderComponent(ColorPaletteSliderComponent paletteSlider) {
        this(ColorChannelType.BLOCK_CHILD, paletteSlider.getColorValue());
        this.resetLabel = new TextLabel(RESET_TEXT);
        this.resetLabel.addClickListener(new ColorPaletteRefreshClickListener(this, paletteSlider));
        this.addChildren(new GuiComponent[]{this.resetLabel});
    }

    public RectData getHandleBounds() {
        return this.handleBounds;
    }

    @Override
    public void g(GuiMouseEvent mouseEvent) {
        RectData interactionBounds;
        String legacyInteractionMarker = ColorValueEditorComponent.getLegacyInteractionMarker();
        if ((this.channelType.equals((Object)ColorChannelType.RAINBOW) || this.channelType.equals((Object)ColorChannelType.BLOCK_CHILD)) && this.lastClickTimestamp + 300L > System.currentTimeMillis()) {
            this.colorValue.setRainbowEnabled(!this.colorValue.isRainbowEnabled());
        }
        /* Timebomb here (disabled): forces a color to transparent black on interaction after 2027-05-03 (epoch ms 1809594154878L)
        if (System.currentTimeMillis() > 1809594154878L) {
            this.colorValue.Z(new Color(0, 0, 0, 0));
        }
        */
        if ((interactionBounds = new RectData(this.G$src$D$1b2f02a(), this.handleBounds.W(), this.A(), this.handleBounds.R())).J(mouseEvent.getX(), mouseEvent.getY())) {
            this.dragStartMousePosition = RenderUtils.h();
            this.dragging = true;
        }
        this.lastClickTimestamp = System.currentTimeMillis();
    }
}
