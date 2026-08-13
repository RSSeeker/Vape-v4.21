package gg.vape.ui.click.component.value;

import func.skidline.RectData;
import gg.vape.input.MouseInput;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.MouseButton;
import gg.vape.ui.click.MousePosition;
import gg.vape.ui.click.animation.ColorAnimation;
import gg.vape.ui.click.animation.DoubleAnimation;
import gg.vape.ui.click.component.value.SliderComponentBase;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.RenderUtils;
import gg.vape.value.ColorValue;
import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ColorPaletteSliderComponent
extends SliderComponentBase {
    private double maximumPaletteValue;
    private boolean hovered;
    private Color labelColor;
    private boolean separatedSegments;
    private double labelWidth = 0.0;
    private double minimumPaletteValue = 0.0;
    private int previousSelectedIndex;
    private double step = 1.0;
    private ColorAnimation handleColorAnimation;
    private boolean forceCustomColor;
    private ColorValue colorValue;
    private boolean initialized;
    protected RectData handleBounds;
    private double valuePerPercent;
    private double fontScale = 0.75;
    protected int selectedIndex;
    private Color[] paletteColors;
    private DoubleAnimation handlePositionAnimation;
    protected DoubleAnimation handleHoverAnimation = new DoubleAnimation(0.15, 7.0, 8.0);

    public ColorValue getColorValue() {
        return this.colorValue;
    }

    public DoubleAnimation getHandlePositionAnimation() {
        return this.handlePositionAnimation;
    }

    public void animateSelection() {
        double paletteTrackWidth = this.A() - 10.0 - (double)this.paletteColors.length * 0.5;
        double segmentWidth = paletteTrackWidth / (double)this.paletteColors.length;
        double maximumIndex = this.getMaximumPaletteValue() - 1.0;
        double selectionRatio = (double)this.selectedIndex / maximumIndex;
        paletteTrackWidth = this.A() - 10.0 - (double)(this.separatedSegments ? 2 : 0);
        double targetHandleX = 5.0 + paletteTrackWidth * selectionRatio;
        double currentHandleX = this.handlePositionAnimation.getInterpolatedValue();
        this.handlePositionAnimation = new DoubleAnimation(0.15, currentHandleX, (targetHandleX += (selectionRatio - 0.5) * -1.0 * segmentWidth) + (this.separatedSegments ? 0.5 : 0.0));
        this.handlePositionAnimation.c();
        this.handleColorAnimation = new ColorAnimation(0.15, this.paletteColors[this.previousSelectedIndex], this.paletteColors[this.selectedIndex]);
        this.handleColorAnimation.c();
    }

    @Override
    public void F() {
        if (!this.hovered) {
            this.handleHoverAnimation.J();
        }
        this.hovered = true;
    }

    public double getMinimumPaletteValue() {
        return this.minimumPaletteValue;
    }

    public double getMaximumPaletteValue() {
        return this.maximumPaletteValue;
    }

    @Override
    public void H() {
        double paletteValue;
        this.updateDraggingValue();
        SmoothFontRenderer fontRenderer = this.getFontRenderer(this.fontScale);
        double labelHeight = fontRenderer.d(this.getLabel());
        this.labelWidth = fontRenderer.N(this.getLabel());
        double trackY = this.n() + 12.5 + labelHeight;
        double paletteTrackWidth = this.A() - 10.0 - (double)this.paletteColors.length * 0.5;
        double segmentX = 5.0;
        double segmentWidth = paletteTrackWidth / (double)this.paletteColors.length;
        double maximumIndex = this.getMaximumPaletteValue() - 1.0;
        fontRenderer.d(this.getLabel(), this.G$src$D$1b2f02a() + 5.0, this.n() + 5.0, this.labelColor);
        if (this.separatedSegments) {
            segmentWidth = (paletteTrackWidth - 2.0) / (double)this.paletteColors.length;
            for (paletteValue = 1.0; paletteValue < (double)(this.paletteColors.length + 1); paletteValue += 1.0) {
                if (paletteValue - 1.0 == (double)this.findPaletteIndexAtOffset(this.handlePositionAnimation.getInterpolatedValue())) {
                    GuiRenderPrimitives.C(this.G$src$D$1b2f02a() + segmentX - 1.0, trackY, segmentWidth + 1.0, 1.0, this.getDisabledOverlayColor());
                    segmentX += segmentWidth + 2.5;
                    continue;
                }
                GuiRenderPrimitives.C(this.G$src$D$1b2f02a() + segmentX, trackY, segmentWidth, 1.0, this.getColorForPaletteValue(paletteValue));
                segmentX += segmentWidth + 0.5;
            }
        } else {
            for (paletteValue = 1.0; paletteValue < (double)(this.paletteColors.length + 1); paletteValue += 1.0) {
                GuiRenderPrimitives.C(this.G$src$D$1b2f02a() + segmentX, trackY, segmentWidth, 1.0, this.getColorForPaletteValue(paletteValue));
                if (paletteValue == 1.0) {
                    GuiRenderPrimitives.V(this.G$src$D$1b2f02a() + segmentX - 0.5, trackY + 0.25, 0.5, 1.0, this.getColorForPaletteValue(paletteValue));
                } else if (paletteValue == (double)this.paletteColors.length) {
                    GuiRenderPrimitives.V(this.G$src$D$1b2f02a() + segmentX + segmentWidth, trackY + 0.25, 0.5, 1.0, this.getColorForPaletteValue(paletteValue));
                }
                segmentX += segmentWidth + 0.5;
            }
        }
        paletteValue = 0.5;
        if (!this.isCustomColor()) {
            paletteValue = this.colorValue != null ? (double)this.selectedIndex / maximumIndex : 0.0;
        }
        paletteTrackWidth = this.A() - 10.0 + (double)(this.separatedSegments ? 2 : 0);
        double handleCenterX = this.G$src$D$1b2f02a() + 5.0 + paletteTrackWidth * paletteValue;
        this.handleBounds = this.createHandleBounds(handleCenterX += (paletteValue - 0.5) * -1.0 * segmentWidth, trackY + 0.5, this.handleHoverAnimation.getEndValue() / 2.0);
        if (!this.initialized) {
            this.initialized = true;
            double initialHandleX = handleCenterX - this.G$src$D$1b2f02a();
            this.handlePositionAnimation = new DoubleAnimation(0.0, initialHandleX, initialHandleX);
            this.handleColorAnimation = new ColorAnimation(0.0, this.paletteColors[this.selectedIndex], this.paletteColors[this.selectedIndex]);
        }
        this.renderHandle();
    }

    @Override
    public void u() {
        if (this.hovered && !this.w$src$Z$e457mb()) {
            this.handleHoverAnimation.J();
            this.hovered = false;
        }
        this.synchronizeSelectionFromValue();
    }

    public void setSeparatedSegments(boolean separatedSegments) {
        this.separatedSegments = separatedSegments;
    }

    public void resetToMiddleColor() {
        this.colorValue.setRainbowEnabled(false);
        int middleIndex = Math.round(this.paletteColors.length / 2);
        this.colorValue.setColor(this.paletteColors[middleIndex]);
        this.selectedIndex = middleIndex;
        this.animateSelection();
        this.setForceCustomColor(false);
    }

    @Override
    public void g(GuiMouseEvent mouseEvent) {
        RectData interactionBounds = new RectData(this.G$src$D$1b2f02a(), this.handleBounds.W(), this.A(), this.handleBounds.R());
        if (interactionBounds.J(mouseEvent.getX(), mouseEvent.getY())) {
            this.setForceCustomColor(false);
            this.colorValue.setRainbowEnabled(false);
            this.dragStartMousePosition = RenderUtils.h();
            this.dragging = true;
        }
    }

    @Override
    public double x() {
        return 110.0;
    }

    protected void renderHandle() {
        if (this.isCustomColor()) {
            GuiRenderPrimitives.F("newcustomtheme", this.G$src$D$1b2f02a() + (double)this.handlePositionAnimation.getInterpolatedValue().floatValue() - 3.5, this.handleBounds.W() + this.handleBounds.R() / 2.0, this.handleBounds.e() - 2.0, this.handleBounds.R() - 2.0, Color.WHITE);
            GuiRenderPrimitives.F("toggledot", this.G$src$D$1b2f02a() + (double)this.handlePositionAnimation.getInterpolatedValue().floatValue(), this.handleBounds.W() + this.handleBounds.R() / 2.0, this.handleBounds.e() - 4.0, this.handleBounds.R() - 4.0, ColorPaletteSliderComponent.J.i);
            return;
        }
        GuiRenderPrimitives.F("newtheme", this.G$src$D$1b2f02a() + (double)this.handlePositionAnimation.getInterpolatedValue().floatValue() - 3.5, this.handleBounds.W() + this.handleBounds.R() / 2.0, this.handleBounds.e() - 2.0, this.handleBounds.R() - 2.0, this.handleColorAnimation.getInterpolatedColor());
    }

    public boolean isCustomColor() {
        if (this.forceCustomColor) {
            return true;
        }
        for (Color paletteColor : this.paletteColors) {
            if (!this.colorValue.matchesColor(paletteColor)) continue;
            return false;
        }
        this.selectedIndex = Math.round(this.paletteColors.length / 2);
        this.animateSelection();
        this.previousSelectedIndex = this.selectedIndex;
        return true;
    }

    public ColorPaletteSliderComponent(String label, ColorValue colorValue, Color[] paletteColors) {
        super(label);
        this.handleBounds = new RectData(0.0, 0.0, 0.0, 0.0);
        this.handlePositionAnimation = new DoubleAnimation(0.0, 0.0, 0.0);
        this.handleColorAnimation = new ColorAnimation(0.0, Color.BLACK, Color.BLACK);
        this.labelColor = ColorPaletteSliderComponent.J.Z;
        this.colorValue = colorValue;
        this.bindValue(colorValue);
        this.paletteColors = paletteColors;
        this.maximumPaletteValue = paletteColors.length;
        this.valuePerPercent = (this.maximumPaletteValue - this.minimumPaletteValue) / 100.0;
        this.synchronizeSelectionFromValue();
    }

    @Override
    public double C() {
        return 20.0;
    }

    public void synchronizeSelectionFromValue() {
        if (!this.isCustomColor()) {
            for (int paletteIndex = 0; paletteIndex < this.paletteColors.length; ++paletteIndex) {
                if (!this.colorValue.matchesColor(this.paletteColors[paletteIndex])) continue;
                this.selectedIndex = paletteIndex;
                if (this.selectedIndex != this.previousSelectedIndex) {
                    this.animateSelection();
                }
                this.previousSelectedIndex = this.selectedIndex;
            }
        }
    }

    public Color getColorForPaletteValue(double paletteValue) {
        double valuePerColor = (this.getMaximumPaletteValue() - this.getMinimumPaletteValue()) / (double)this.paletteColors.length;
        double minimumValue = this.getMinimumPaletteValue();
        Color selectedColor = Color.BLACK;
        boolean matchedSegment = false;
        for (int paletteIndex = 0; paletteIndex < this.paletteColors.length; ++paletteIndex) {
            double segmentStart = minimumValue + (double)paletteIndex * valuePerColor;
            int nextIndex = paletteIndex + 1;
            double segmentEnd = minimumValue + (double)nextIndex * valuePerColor;
            if (!(paletteValue > segmentStart) || !(paletteValue <= segmentEnd)) continue;
            selectedColor = this.paletteColors[paletteIndex];
            matchedSegment = true;
            break;
        }
        if (!matchedSegment) {
            selectedColor = paletteValue <= minimumValue ? this.paletteColors[0] : this.paletteColors[this.paletteColors.length - 1];
        }
        return selectedColor;
    }

    public void selectPaletteIndex(int paletteIndex) {
        Color selectedColor = this.paletteColors[paletteIndex];
        this.colorValue.setColor(selectedColor);
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        this.forceCustomColor = false;
        this.initialized = false;
    }

    public ColorAnimation getHandleColorAnimation() {
        return this.handleColorAnimation;
    }

    public double getLabelWidth() {
        return this.labelWidth;
    }

    public void setMaximumPaletteValue(double maximumPaletteValue) {
        this.maximumPaletteValue = maximumPaletteValue;
    }


    public void setStep(double step) {
        this.step = step;
    }

    @Override
    public void I() {
    }

    public int getSelectedIndex() {
        return this.selectedIndex;
    }

    private void updateDraggingValue() {
        if (this.dragging) {
            if (!MouseInput.isButtonDown(MouseButton.LEFT_CLICK.ordinal())) {
                this.dragging = false;
                return;
            }
            double trackWidth = this.A() - 10.0;
            MousePosition mousePosition = RenderUtils.h();
            double trackOffset = (double)mousePosition.O - this.G$src$D$1b2f02a() - 5.0 - this.handleBounds.e() / 2.0;
            double trackStart = this.G$src$D$1b2f02a() + 5.0 + this.handleBounds.e() / 2.0;
            double trackEnd = this.G$src$D$1b2f02a() + this.A() - 5.0 - this.handleBounds.e() / 2.0;
            double paletteValue = this.mapTrackOffsetToValue(this.minimumPaletteValue, this.maximumPaletteValue, trackStart, trackEnd, this.step, trackOffset);
            paletteValue = new BigDecimal("" + paletteValue).setScale(1, RoundingMode.HALF_UP).doubleValue();
            if (this.colorValue != null) {
                this.colorValue.setColor(this.getColorForPaletteValue(paletteValue + 0.0));
                this.synchronizeSelectionFromValue();
            }
        }
    }

    public RectData getHandleBounds() {
        return this.handleBounds;
    }

    public void setForceCustomColor(boolean forceCustomColor) {
        this.forceCustomColor = forceCustomColor;
    }

    public int findPaletteIndexAtOffset(double handleOffset) {
        double paletteTrackWidth = this.A() - 10.0 - (double)this.paletteColors.length * 0.5;
        double segmentStart = 5.0;
        double segmentWidth = paletteTrackWidth / (double)this.paletteColors.length;
        for (int paletteNumber = 1; paletteNumber < this.paletteColors.length + 1; ++paletteNumber) {
            if (handleOffset >= segmentStart && handleOffset <= segmentStart + segmentWidth + 0.5) {
                return paletteNumber - 1;
            }
            segmentStart += segmentWidth + 0.5;
        }
        return 0;
    }

    public Color[] getPaletteColors() {
        return this.paletteColors;
    }

    public double getStep() {
        return this.step;
    }

    public void setMinimumPaletteValue(double minimumPaletteValue) {
        this.minimumPaletteValue = minimumPaletteValue;
    }
}

