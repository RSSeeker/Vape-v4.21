package gg.vape.ui.click.component.value;

import func.skidline.RectData;
import gg.vape.Vape;
import gg.vape.input.MouseInput;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.MouseButton;
import gg.vape.ui.click.MousePosition;
import gg.vape.ui.click.animation.DoubleAnimation;
import gg.vape.ui.click.component.value.RandomRangeSliderInputHandle;
import gg.vape.ui.click.component.value.RangeEndpoint;
import gg.vape.ui.click.component.value.SliderComponentBase;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ImageRenderer;
import gg.vape.utils.render.RenderUtils;
import gg.vape.value.RandomValue;
import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class RandomRangeSliderComponent
extends SliderComponentBase {
    private int draggedEndpoint;
    private RandomValue randomValue;
    private final RandomRangeSliderInputHandle minimumInputHandle;
    private double initialMinimumValue;
    private double initialMaximumValue;
    private final double valuePerPercent;
    private final RandomRangeSliderInputHandle maximumInputHandle;
    private DoubleAnimation minimumHandlePositionAnimation = new DoubleAnimation(0.0, 0.0, 0.0);
    private double lastMaximumValue = -1.0;
    private RectData minimumHandleBounds;
    double wrappedLabelHeight;
    private DoubleAnimation maximumHandlePositionAnimation = new DoubleAnimation(0.0, 0.0, 0.0);
    private final Color labelColor;
    private double minimum;
    private double maximum;
    private RectData maximumHandleBounds;
    private final DoubleAnimation minimumHandleHoverAnimation = new DoubleAnimation(0.15, 8.0, 9.0);
    private final double fontScale = 0.75;
    private double lastMinimumValue = -1.0;
    private int hoveredEndpoint;
    private final double multilineLabelHeight;
    private final DoubleAnimation maximumHandleHoverAnimation = new DoubleAnimation(0.15, 8.0, 9.0);
    private double step;

    public double getInitialMaximumValue() {
        return this.initialMaximumValue;
    }

    @Override
    public void setExplicitWidth(double width) {
        if (this.A() == width) {
            return;
        }
        super.setExplicitWidth(width);
        this.updateHandleAnimations(true);
    }

    @Override
    public void g(GuiMouseEvent mouseEvent) {
        double handleSeparation = this.maximumHandleBounds.o() - this.minimumHandleBounds.o();
        double minimumInteractionWidth = this.minimumHandleBounds.o() - this.G$src$D$1b2f02a() + handleSeparation / 2.0;
        RectData minimumInteractionBounds = new RectData(this.G$src$D$1b2f02a(), this.minimumHandleBounds.W(), minimumInteractionWidth, this.minimumHandleBounds.R());
        RectData maximumInteractionBounds = new RectData(this.maximumHandleBounds.o() - handleSeparation / 2.0, this.maximumHandleBounds.W(), this.A() - minimumInteractionWidth, this.maximumHandleBounds.R());
        if (minimumInteractionBounds.J(mouseEvent.getX(), mouseEvent.getY())) {
            this.draggedEndpoint = 1;
        } else if (maximumInteractionBounds.J(mouseEvent.getX(), mouseEvent.getY())) {
            this.draggedEndpoint = 2;
        }
        if (this.draggedEndpoint != 0) {
            this.dragStartMousePosition = RenderUtils.h();
        }
    }

    public void setInitialMinimumValue(double initialMinimumValue) {
        this.initialMinimumValue = initialMinimumValue;
    }

    @Override
    public void u() {
        switch (this.hoveredEndpoint) {
            case 1: {
                MousePosition mousePosition = RenderUtils.h();
                if (this.minimumHandleBounds.Z(mousePosition)) break;
                this.hoveredEndpoint = 0;
                this.minimumHandleHoverAnimation.J();
                break;
            }
            case 2: {
                MousePosition mousePosition = RenderUtils.h();
                if (this.maximumHandleBounds.Z(mousePosition)) break;
                this.hoveredEndpoint = 0;
                this.maximumHandleHoverAnimation.J();
            }
        }
    }

    public RandomRangeSliderComponent(String label, double minimum, double maximum, double step) {
        this(label, minimum, maximum, step, 1.0, 1.0);
    }

    public double getStep() {
        return this.step;
    }

    private void updateHandleAnimations(boolean immediate) {
        float handleSize = (float)this.minimumHandleHoverAnimation.getEndValue();
        double trackWidth = this.A() - (this.getHorizontalInset() + 5.0);
        double minimumRatio = (this.randomValue.getMinimumValue() - this.minimum) / (this.maximum - this.minimum);
        double maximumRemainingRatio = 1.0 - (this.randomValue.getMaximumValue() - this.minimum) / (this.maximum - this.minimum);
        if (minimumRatio > 1.0) {
            minimumRatio = 1.0;
        } else if (minimumRatio < 0.0) {
            minimumRatio = 0.0;
        }
        if (maximumRemainingRatio > 1.0) {
            maximumRemainingRatio = 1.0;
        } else if (maximumRemainingRatio < 0.0) {
            maximumRemainingRatio = 0.0;
        }
        double selectedRangeRatio = 1.0 - (minimumRatio + maximumRemainingRatio);
        double minimumTargetX = minimumRatio * (trackWidth - (double)handleSize - 5.0) + this.getHorizontalInset() + (double)(handleSize / 2.0f);
        double maximumTargetX = minimumTargetX + 5.0 + selectedRangeRatio * (trackWidth - (double)handleSize - 5.0);
        double currentMinimumX = this.minimumHandlePositionAnimation.getInterpolatedValue();
        double currentMaximumX = this.maximumHandlePositionAnimation.getInterpolatedValue();
        this.minimumHandlePositionAnimation = new DoubleAnimation(0.05, currentMinimumX, minimumTargetX);
        this.minimumHandlePositionAnimation.c();
        this.maximumHandlePositionAnimation = new DoubleAnimation(0.05, currentMaximumX, maximumTargetX);
        this.maximumHandlePositionAnimation.c();
        if (immediate) {
            this.minimumHandlePositionAnimation.C();
            this.maximumHandlePositionAnimation.C();
        }
    }

    private void updateDraggingValue() {
        if (this.draggedEndpoint != 0) {
            if (!MouseInput.isButtonDown(MouseButton.LEFT_CLICK.ordinal())) {
                this.draggedEndpoint = 0;
                return;
            }
            double trackWidth = this.A() - (10.0 + this.getHorizontalInset()) - this.minimumHandleBounds.e() - this.maximumHandleBounds.e();
            double trackOffset = (double)this.dragStartMousePosition.O - this.G$src$D$1b2f02a() + this.getMouseDeltaX() - this.getHorizontalInset();
            if (this.draggedEndpoint == 2) {
                trackOffset -= 12.0;
            }
            if (this.draggedEndpoint == 1) {
                trackOffset -= 2.0;
            }
            double updatedValue = this.snapValueFromTrackOffset(this.minimum, this.maximum, trackWidth, this.step, this.valuePerPercent, trackOffset);
            double scaledStep = this.step;
            int decimalPlaces = 0;
            while (scaledStep % 1.0 != 0.0) {
                ++decimalPlaces;
                scaledStep *= 10.0;
            }
            updatedValue = new BigDecimal("" + updatedValue).setScale(decimalPlaces, RoundingMode.HALF_UP).doubleValue();
            if (updatedValue < this.minimum) {
                updatedValue = this.minimum;
            } else if (updatedValue > this.maximum) {
                updatedValue = this.maximum;
            }
            if (this.draggedEndpoint == 1) {
                if (updatedValue == this.lastMinimumValue) {
                    return;
                }
                this.randomValue.setClampedMinimumValue(updatedValue);
            } else {
                if (updatedValue == this.lastMaximumValue) {
                    return;
                }
                this.randomValue.setClampedMaximumValue(updatedValue);
            }
            this.lastMinimumValue = this.randomValue.getMinimumValue();
            this.lastMaximumValue = this.randomValue.getMaximumValue();
        }
    }

    public RandomRangeSliderComponent(RandomValue randomValue) {
        this(randomValue.getName(), randomValue.getAllowedMinimum(), randomValue.getAllowedMaximum(), randomValue.getIncrement(), randomValue.getMinimumValue(), randomValue.getMaximumValue());
        this.randomValue = randomValue;
        this.bindValue(randomValue);
        randomValue.addChangeListener(this::handleValueChanged);
        this.updateHandleAnimations(true);
    }

    public void setMaximum(double maximum) {
        this.maximum = maximum;
    }

    private List<String> getWrappedLabelLines() {
        SmoothFontRenderer fontRenderer = this.getFontRenderer(this.fontScale);
        String sanitizedLabel = Vape.INSTANCE.getFontSelector().W().s(this.getLabel());
        String[] labelWords = sanitizedLabel.split(" ");
        double availableWidth = this.minimumInputHandle.G$src$D$1b2f02a() - this.G$src$D$1b2f02a();
        ArrayList<String> lines = new ArrayList<String>();
        double currentLineWidth = 0.0;
        String currentLine = "";
        for (String word : labelWords) {
            double appendedLineWidth = currentLineWidth + fontRenderer.N(word + " ");
            if (appendedLineWidth > availableWidth) {
                currentLineWidth = 0.0;
                lines.add(currentLine);
                currentLine = word + " ";
                continue;
            }
            currentLineWidth = appendedLineWidth;
            currentLine = currentLine + word + " ";
        }
        lines.add(currentLine);
        return lines;
    }

    public void setStep(double step) {
        this.step = step;
    }


    private void handleValueChanged(RandomValue changedValue) {
        this.updateHandleAnimations(false);
    }

    public RandomRangeSliderComponent(String label, double minimum, double maximum, double step, double initialMinimumValue, double initialMaximumValue) {
        super(label);
        this.minimumHandleBounds = new RectData(0.0, 0.0, 0.0, 0.0);
        this.maximumHandleBounds = new RectData(0.0, 0.0, 0.0, 0.0);
        this.labelColor = RandomRangeSliderComponent.J.Z;
        this.minimum = minimum;
        this.maximum = maximum;
        this.step = step;
        this.valuePerPercent = (maximum - minimum) / 100.0;
        this.multilineLabelHeight = (double)(label.split("\n").length - 1) * this.getDefaultFontRenderer().d(label) + 5.0;
        if (initialMinimumValue == 1.0 && initialMaximumValue == 1.0) {
            this.initializeDefaultRange();
        } else {
            this.initialMinimumValue = initialMinimumValue;
            this.initialMaximumValue = initialMaximumValue;
        }
        this.minimumInputHandle = new RandomRangeSliderInputHandle(this, RangeEndpoint.MINIMUM);
        this.maximumInputHandle = new RandomRangeSliderInputHandle(this, RangeEndpoint.MAXIMUM);
        this.addChildren(this.minimumInputHandle, this.maximumInputHandle);
    }

    public RandomValue getRandomValue() {
        return this.randomValue;
    }

    @Override
    public double x() {
        return 110.0;
    }

    public void setMinimum(double minimum) {
        this.minimum = minimum;
    }

    @Override
    public double C() {
        return 20.0 + this.multilineLabelHeight + this.wrappedLabelHeight;
    }

    @Override
    public void H() {
        double handleGrowth;
        double drawableSegmentWidth;
        this.updateDraggingValue();
        this.onDisable();
        SmoothFontRenderer fontRenderer = this.getFontRenderer(this.fontScale);
        double labelHeight = fontRenderer.d(this.getLabel());
        double trackCenterY = this.n() + 12.5 + (double)((float)labelHeight);
        double inputY = this.n() + 5.0;
        this.renderLabel();
        double explicitLineBreakOffset = (double)(this.getLabel().split("\n").length - 1) * (fontRenderer.d(this.getLabel()) + 3.0);
        trackCenterY += explicitLineBreakOffset;
        this.maximumInputHandle.K(this.G$src$D$1b2f02a() + this.A() - 5.0 - this.maximumInputHandle.A());
        this.maximumInputHandle.S(inputY += explicitLineBreakOffset);
        ImageRenderer.drawResWithShadow(RandomRangeSliderComponent.J.K, (int)(this.G$src$D$1b2f02a() + this.A() - 5.0 - this.maximumInputHandle.A() - 8.0), (int)inputY, "newrangeindicator", 0.1f, false);
        this.minimumInputHandle.K(this.G$src$D$1b2f02a() + this.A() - 10.0 - this.minimumInputHandle.A() - this.maximumInputHandle.A() - 8.0);
        this.minimumInputHandle.S(inputY);
        this.minimumHandleBounds = this.createHandleBounds(this.G$src$D$1b2f02a() + this.minimumHandlePositionAnimation.getInterpolatedValue(), (trackCenterY += this.wrappedLabelHeight / 2.0) + 0.5, this.minimumHandleHoverAnimation.getInterpolatedValue() / 2.0);
        this.maximumHandleBounds = this.createHandleBounds(this.G$src$D$1b2f02a() + this.maximumHandlePositionAnimation.getInterpolatedValue(), trackCenterY + 0.5, this.maximumHandleHoverAnimation.getInterpolatedValue() / 2.0);
        this.minimumHandleBounds.A(this.minimumHandleBounds.e() / 2.0);
        this.maximumHandleBounds.A(this.maximumHandleBounds.e() / 2.0);
        this.maximumHandleBounds.M(this.maximumHandleBounds.o() + this.maximumHandleBounds.e());
        double trackBeforeMinimumWidth = this.minimumHandleBounds.o() - this.G$src$D$1b2f02a() - this.getHorizontalInset();
        double selectedTrackWidth = this.maximumHandleBounds.o() - this.minimumHandleBounds.o();
        double trackAfterMaximumWidth = this.G$src$D$1b2f02a() + this.A() - this.maximumHandleBounds.o() - 5.0;
        double trackY = trackCenterY + 0.5 - 1.0;
        if (trackBeforeMinimumWidth - 0.5 >= 2.0) {
            GuiRenderPrimitives.j(this.G$src$D$1b2f02a() + this.getHorizontalInset(), trackY, trackBeforeMinimumWidth - 0.5, 2.0, RandomRangeSliderComponent.J.l);
        }
        if (selectedTrackWidth > 0.0 && (drawableSegmentWidth = selectedTrackWidth - 0.5 - 5.0 - (handleGrowth = (this.minimumHandleHoverAnimation.getInterpolatedValue() - this.minimumHandleHoverAnimation.getStartValue()) / 2.0)) >= 2.0) {
            GuiRenderPrimitives.j(this.minimumHandleBounds.o() + 5.0 + handleGrowth, trackY, drawableSegmentWidth, 2.0, J.z());
        }
        if (trackAfterMaximumWidth > 0.0 && (drawableSegmentWidth = trackAfterMaximumWidth - 5.0 - (handleGrowth = (this.maximumHandleHoverAnimation.getInterpolatedValue() - this.maximumHandleHoverAnimation.getStartValue()) / 2.0)) >= 2.0) {
            GuiRenderPrimitives.j(this.maximumHandleBounds.o() + 6.0 + handleGrowth, trackY, drawableSegmentWidth, 2.0, RandomRangeSliderComponent.J.l);
        }
        GuiRenderPrimitives.F("rangemin", this.minimumHandleBounds.o() + this.minimumHandleBounds.e(), trackCenterY + 0.5, (double)this.minimumHandleHoverAnimation.getInterpolatedValue(), this.minimumHandleHoverAnimation.getInterpolatedValue(), J.z());
        GuiRenderPrimitives.F("rangemax", this.maximumHandleBounds.o() + this.maximumHandleBounds.e(), trackCenterY + 0.5, (double)this.maximumHandleHoverAnimation.getInterpolatedValue(), this.maximumHandleHoverAnimation.getInterpolatedValue(), J.z());
    }

    public double getMaximum() {
        return this.maximum;
    }

    @Override
    public void F() {
        MousePosition mousePosition = RenderUtils.h();
        if (this.minimumHandleBounds.Z(mousePosition) && this.hoveredEndpoint != 1) {
            if (this.hoveredEndpoint == 2) {
                this.maximumHandleHoverAnimation.J();
            }
            this.hoveredEndpoint = 1;
            this.minimumHandleHoverAnimation.J();
        } else if (this.maximumHandleBounds.Z(mousePosition) && this.hoveredEndpoint != 2) {
            if (this.hoveredEndpoint == 1) {
                this.minimumHandleHoverAnimation.J();
            }
            this.hoveredEndpoint = 2;
            this.maximumHandleHoverAnimation.J();
        }
    }

    private void initializeDefaultRange() {
        double maximumRemainder;
        double rangeMidpoint = (this.maximum + this.minimum) / 2.0;
        this.initialMinimumValue = rangeMidpoint - this.minimum;
        this.initialMaximumValue = rangeMidpoint + this.minimum;
        double minimumRemainder = this.initialMinimumValue % this.step;
        if (minimumRemainder != 0.0) {
            this.initialMinimumValue -= minimumRemainder;
        }
        if ((maximumRemainder = this.initialMaximumValue % this.step) != 0.0) {
            this.initialMaximumValue -= maximumRemainder;
        }
    }

    public double getInitialMinimumValue() {
        return this.initialMinimumValue;
    }

    public double getMinimum() {
        return this.minimum;
    }

    protected void renderLabel() {
        double accumulatedLabelHeight = -6.0;
        SmoothFontRenderer fontRenderer = this.getFontRenderer(this.fontScale);
        List<String> labelLines = this.getWrappedLabelLines();
        double lineY = this.n() + 5.0;
        for (String labelLine : labelLines) {
            double lineHeight = fontRenderer.d(labelLine);
            fontRenderer.d(labelLine, this.G$src$D$1b2f02a() + this.getHorizontalInset(), lineY, this.labelColor);
            lineY += lineHeight;
            accumulatedLabelHeight += lineHeight;
        }
        this.wrappedLabelHeight = accumulatedLabelHeight;
    }

    public void setInitialMaximumValue(double initialMaximumValue) {
        this.initialMaximumValue = initialMaximumValue;
    }

    @Override
    public void I() {
    }
}

