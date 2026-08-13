package gg.vape.ui.click.component.value;

import gg.vape.ui.click.animation.DoubleAnimation;
import gg.vape.ui.click.component.TextInputComponentBase;
import gg.vape.unmap.ColorUtil;
import java.awt.Color;

public abstract class SliderInputHandle
extends TextInputComponentBase {
    private DoubleAnimation underlineAlphaAnimation = new DoubleAnimation(0.15, 0.0, 100.0);
    protected boolean editing;
    protected boolean hovered;
    private double legacyOffset = 0.0;

    @Override
    public double getAvailableTextWidth() {
        return 15.0;
    }

    @Override
    public double C() {
        return 6.0;
    }

    @Override
    public void u() {
        if (!this.w$src$Z$e457mb() && this.hovered) {
            this.hovered = false;
            this.underlineAlphaAnimation.J();
        }
        if (!this.isFocused() && this.editing) {
            this.editing = false;
            this.submit();
        }
        if (this.isFocused() && !this.editing) {
            this.editing = true;
            this.loadCurrentValueForEditing();
        }
    }

    public Color getUnderlineColor() {
        if (this.isFocused()) {
            return ColorUtil.withAlpha(SliderInputHandle.J.l, (int)this.underlineAlphaAnimation.getEndValue());
        }
        return ColorUtil.withAlpha(SliderInputHandle.J.l, this.underlineAlphaAnimation.getInterpolatedValue().intValue());
    }

    @Override
    public void F() {
        if (!this.hovered) {
            this.hovered = true;
            this.underlineAlphaAnimation.J();
        }
    }

    @Override
    public double x() {
        return this.getAvailableTextWidth();
    }

    public boolean isEditing() {
        return this.editing;
    }

    public SliderInputHandle() {
        super("");
    }

    public abstract void loadCurrentValueForEditing();

}
