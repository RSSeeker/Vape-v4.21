package gg.vape.ui.click.component.value;

import func.skidline.RectData;
import gg.vape.ui.click.MousePosition;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.utils.render.RenderUtils;

public abstract class SliderComponentBase
extends GuiComponent {
    protected MousePosition dragStartMousePosition;
    protected final double handlePadding = 2.0;
    private boolean legacyFlag;
    private String label;
    private static int[] legacyState;
    protected boolean dragging;

    public SliderComponentBase(String label) {
        this.label = label;
    }

    public double getMouseDeltaY() {
        return RenderUtils.h().H - this.dragStartMousePosition.H;
    }

    public double getMouseDeltaX() {
        return RenderUtils.h().O - this.dragStartMousePosition.O;
    }

    public String getLabel() {
        return this.label;
    }

    public double snapValueFromTrackOffset(double minimum, double maximum, double trackWidth, double step, double valuePerPercent, double trackOffset) {
        double percentage = trackOffset / trackWidth * 100.0;
        if (percentage <= 0.0) {
            return minimum;
        }
        if (percentage >= 100.0) {
            return maximum;
        }
        percentage = Math.min(percentage, 100.0);
        percentage = Math.max(percentage, 0.0);
        double rawValue = valuePerPercent * percentage + valuePerPercent;
        double stepRemainder = rawValue % step;
        double snappedValue = rawValue - stepRemainder;
        return snappedValue + minimum;
    }

    public static void setLegacyState(int[] state) {
        legacyState = state;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public static int[] getLegacyState() {
        return legacyState;
    }

    public boolean isDragging() {
        return this.dragging;
    }

    public double mapTrackOffsetToValue(double minimum, double maximum, double trackStart, double trackEnd, double step, double trackOffset) {
        double stepCount = (maximum - minimum) / step;
        double pixelsPerStep = (trackEnd - trackStart) / stepCount;
        double stepPosition = trackOffset / pixelsPerStep;
        double trackPosition = stepPosition * step;
        return Math.min(Math.max(trackPosition + minimum, minimum), maximum);
    }


    public RectData createHandleBounds(double centerX, double centerY, double radius) {
        return new RectData(centerX - radius, centerY - radius, radius * 2.0, radius * 2.0);
    }

    static {
        if (SliderComponentBase.getLegacyState() == null) {
            SliderComponentBase.setLegacyState(new int[2]);
        }
    }

    public /* synthetic */ double getMouseDeltaXCompat() {
        return this.getMouseDeltaX();
    }

    public /* synthetic */ String getLabelCompat() {
        return this.getLabel();
    }

    public /* synthetic */ boolean isDraggingCompat() {
        return this.isDragging();
    }
}

