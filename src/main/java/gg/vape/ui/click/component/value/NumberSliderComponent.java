package gg.vape.ui.click.component.value;

import func.skidline.RectData;
import gg.vape.Vape;
import gg.vape.input.MouseInput;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.MouseButton;
import gg.vape.ui.click.animation.DoubleAnimation;
import gg.vape.ui.click.component.value.NumberSliderInputHandle;
import gg.vape.ui.click.component.value.SliderComponentBase;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.RenderUtils;
import gg.vape.value.NumberValue;
import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class NumberSliderComponent
extends SliderComponentBase {
    private Double legacyDragValueSentinel;
    private static final long FINE_STEP_DECIMAL_PLACES;
    protected DoubleAnimation handleHoverAnimation;
    private String unitSuffix;
    private Color labelColor;
    private DoubleAnimation handlePositionAnimation;
    private boolean hovered;
    private double fontScale;
    private double valuePerPercent;
    private double step;
    private NumberSliderInputHandle inputHandle;
    private double maximum;
    private RectData handleBounds;
    private int decimalPlaces;
    private NumberValue numberValue;
    private double minimum;

    public NumberValue getNumberValue() {
        return this.numberValue;
    }

    public void setStep(double step) {
        this.step = step;
    }

    @Override
    public void F() {
        if (!this.hovered) {
            this.handleHoverAnimation.J();
        }
        this.hovered = true;
    }

    static {
        FINE_STEP_DECIMAL_PLACES = 2501393815093379082L;
    }

    public NumberSliderComponent(String label, double minimum, double maximum, double step) {
        super(label);
        this.unitSuffix = "";
        this.fontScale = 0.75;
        this.decimalPlaces = 1;
        this.handleBounds = new RectData(0.0, 0.0, 0.0, 0.0);
        this.handlePositionAnimation = new DoubleAnimation(0.0, 0.0, 0.0);
        this.handleHoverAnimation = new DoubleAnimation(0.15, 7.0, 8.0);
        this.labelColor = NumberSliderComponent.J.Z;
        this.legacyDragValueSentinel = -1.0;
        this.minimum = minimum;
        this.maximum = maximum;
        this.step = step;
        this.valuePerPercent = (double)((int)(maximum * 100.0) - (int)(minimum * 100.0)) / 10000.0;
        this.inputHandle = new NumberSliderInputHandle(this);
        this.addChildren(this.inputHandle);
        if (step <= 0.01) {
            this.decimalPlaces = (int)FINE_STEP_DECIMAL_PLACES;
        }
    }

    @Override
    public void u() {
        if (this.hovered && !this.w$src$Z$e457mb()) {
            this.handleHoverAnimation.J();
            this.hovered = false;
        }
    }

    @Override
    public double C() {
        return 25.0;
    }

    public String getUnitSuffix() {
        return this.unitSuffix;
    }

    public DecimalFormat getDecimalFormat() {
        return this.numberValue.getInputFormat();
    }

    private double calculateHandleOffset(double trackWidth) {
        double normalizedValue;
        Double currentValue = this.numberValue != null ? (Double)this.numberValue.getValue() : null;
        double normalizedValueSnapshot = normalizedValue = currentValue != null ? (currentValue - this.minimum) / (this.maximum - this.minimum) : 0.0;
        if (normalizedValue > 1.0) {
            normalizedValue = 1.0;
        } else if (normalizedValue < 0.0) {
            normalizedValue = 0.0;
        }
        return (trackWidth - this.handleHoverAnimation.getEndValue()) * normalizedValue + this.getHorizontalInset() + this.handleHoverAnimation.getEndValue() / 2.0;
    }

    public double getMinimum() {
        return this.minimum;
    }


    @Override
    public void g(GuiMouseEvent mouseEvent) {
        RectData interactionBounds = new RectData(this.G$src$D$1b2f02a(), this.handleBounds.W(), this.A(), this.handleBounds.R());
        if (interactionBounds.J(mouseEvent.getX(), mouseEvent.getY())) {
            this.dragStartMousePosition = RenderUtils.h();
            this.dragging = true;
        }
    }

    public void setMaximum(double maximum) {
        this.maximum = maximum;
    }

    @Override
    public void setExplicitWidth(double width) {
        if (this.A() == width) {
            return;
        }
        super.setExplicitWidth(width);
        this.updateHandleAnimation(true);
    }

    private void handleValueChanged(NumberValue changedValue) {
        this.updateHandleAnimation(false);
    }

    @Override
    public double x() {
        return 110.0;
    }

    private void updateDraggingValue() {
        if (this.dragging) {
            if (!MouseInput.isButtonDown(MouseButton.LEFT_CLICK.ordinal())) {
                this.dragging = false;
                return;
            }
            double trackOffset = (double)this.dragStartMousePosition.O - this.G$src$D$1b2f02a() + this.getMouseDeltaX() - this.getHorizontalInset() - this.handleBounds.e() / 2.0;
            double trackStart = this.G$src$D$1b2f02a() + this.getHorizontalInset() + this.handleBounds.e() / 2.0;
            double trackEnd = this.G$src$D$1b2f02a() + this.A() - 5.0 - this.handleBounds.e() / 2.0;
            double updatedValue = this.mapTrackOffsetToValue(this.minimum, this.maximum, trackStart, trackEnd, this.step, trackOffset);
            updatedValue = new BigDecimal("" + updatedValue).setScale(this.decimalPlaces, RoundingMode.HALF_UP).doubleValue();
            if (this.numberValue != null) {
                if (((Double)this.numberValue.getValue()).equals(this.legacyDragValueSentinel)) {
                    return;
                }
                this.numberValue.setValue(updatedValue);
            }
        }
    }

    public double getStep() {
        return this.step;
    }

    public void setMinimum(double minimum) {
        this.minimum = minimum;
    }

    @Override
    public void H() {
        this.updateDraggingValue();
        this.onDisable();
        SmoothFontRenderer fontRenderer = this.getFontRenderer(this.fontScale);
        double labelHeight = fontRenderer.d(this.getLabel());
        double handleSize = this.handleHoverAnimation.getInterpolatedValue();
        double trackCenterY = this.n() + 12.5 + labelHeight;
        double handleCenterX = this.G$src$D$1b2f02a() + this.handlePositionAnimation.getInterpolatedValue();
        fontRenderer.d(this.getLabel(), this.G$src$D$1b2f02a() + this.getHorizontalInset(), this.n() + 5.0, this.labelColor);
        this.inputHandle.K(this.G$src$D$1b2f02a() + this.A() - 5.0 - this.inputHandle.A());
        this.inputHandle.S(this.n() + 5.0);
        this.handleBounds = this.createHandleBounds(handleCenterX, trackCenterY + 0.5, this.handleHoverAnimation.getEndValue() / 2.0);
        double filledTrackWidth = this.handleBounds.o() - this.G$src$D$1b2f02a() - this.getHorizontalInset();
        double remainingTrackWidth = this.G$src$D$1b2f02a() + this.A() - this.handleBounds.o() - 5.0;
        double trackY = trackCenterY + 0.5 - 1.0;
        if (filledTrackWidth - 0.5 >= 2.0) {
            GuiRenderPrimitives.j(this.G$src$D$1b2f02a() + this.getHorizontalInset(), trackY, filledTrackWidth - 0.5, 2.0, J.z());
        }
        if (remainingTrackWidth - 8.5 >= 2.0) {
            GuiRenderPrimitives.j(this.handleBounds.o() + 8.5, trackY, remainingTrackWidth - 8.5, 2.0, NumberSliderComponent.J.l);
        }
        GuiRenderPrimitives.Y((float)(handleCenterX - handleSize / 2.0), (float)(trackCenterY + 0.5 - handleSize / 2.0), (float)handleSize, (float)(0.8 / Vape.INSTANCE.getClientSettings().getGuiScaleFactor()), J.z(), 0.0, this.getDisabledOverlayColor());
    }

    public NumberSliderComponent(NumberValue numberValue) {
        this(numberValue.getName(), numberValue.getMinimum(), numberValue.getMaximum(), numberValue.getIncrement());
        this.unitSuffix = numberValue.getUnitSuffix();
        this.numberValue = numberValue;
        if (numberValue.getDescription() != null) {
            this.w(numberValue.getDescription());
        }
        this.bindValue(numberValue);
        numberValue.addChangeListener(this::handleValueChanged);
        this.updateHandleAnimation(true);
    }

    private void updateHandleAnimation(boolean immediate) {
        double trackWidth = this.A() - (this.getHorizontalInset() + 5.0);
        double targetHandleOffset = this.calculateHandleOffset(trackWidth);
        double currentHandleOffset = this.handlePositionAnimation.getInterpolatedValue();
        this.handlePositionAnimation = new DoubleAnimation(0.05, currentHandleOffset, targetHandleOffset);
        this.handlePositionAnimation.c();
        if (immediate) {
            this.handlePositionAnimation.C();
        }
    }

    public double getMaximum() {
        return this.maximum;
    }
}
