package gg.vape.ui.click.component;

import gg.vape.ui.click.animation.ColorAnimation;
import gg.vape.ui.click.animation.ThemeColorAnimation;
import gg.vape.ui.click.component.IconButtonComponent;
import gg.vape.unmap.ColorUtil;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;
import org.jetbrains.annotations.Nullable;

public class AnimatedIconButtonComponent
extends IconButtonComponent {
    private boolean selected;
    private ColorAnimation backgroundAnimation;
    private float borderRadius = 1.5f;
    private float borderAlpha = 1.0f;
    @Nullable
    private Color borderColor;
    private boolean dimOnHover;

    public void setAnimatedBorderColor(@Nullable Color borderColor) {
        this.borderColor = borderColor;
    }

    @Override
    public void H() {
        double alphaMultiplier = 1.0;
        if (this.dimOnHover && this.w$src$Z$e457mb()) {
            alphaMultiplier = this.selected ? 0.9 : 0.5;
        }
        if (this.w$src$Z$e457mb() || this.selected) {
            Color renderedBackgroundColor = this.backgroundAnimation.getInterpolatedColor();
            renderedBackgroundColor = ColorUtil.withAlpha(renderedBackgroundColor, (int)(alphaMultiplier * 255.0));
            GuiRenderPrimitives.d(this.G$src$D$1b2f02a() + 0.5, this.n() + 0.5, this.A() - 1.0, this.L() - 1.0, renderedBackgroundColor);
        } else {
            GuiRenderPrimitives.d(this.G$src$D$1b2f02a() + 0.5, this.n() + 0.5, this.A() - 1.0, this.L() - 1.0, this.getDisabledOverlayColor());
        }
        GuiRenderPrimitives.P(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), this.borderColor != null ? this.borderColor : AnimatedIconButtonComponent.J.l, this.borderRadius, this.borderAlpha, 1.0f);
        super.H();
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    public AnimatedIconButtonComponent(String iconResource, Color hoverBackgroundColor) {
        this(iconResource, 1.0, hoverBackgroundColor);
    }

    public float getBorderRadius() {
        return this.borderRadius;
    }

    public AnimatedIconButtonComponent(String iconResource) {
        super(iconResource);
        this.backgroundAnimation = new ThemeColorAnimation(0.15, this.getDisabledOverlayColor());
    }

    public void setBorderAlpha(float borderAlpha) {
        this.borderAlpha = borderAlpha;
    }

    public AnimatedIconButtonComponent(String iconResource, double iconScale, Color hoverBackgroundColor) {
        this(iconResource, iconScale, null, hoverBackgroundColor);
    }

    public ColorAnimation getBackgroundAnimation() {
        return this.backgroundAnimation;
    }

    public AnimatedIconButtonComponent(String iconResource, double iconScale, Color backgroundColor, Color hoverBackgroundColor) {
        super(iconResource, iconScale);
        this.backgroundAnimation = new ColorAnimation(0.15, backgroundColor == null ? this.getDisabledOverlayColor() : backgroundColor, hoverBackgroundColor);
    }

    public void setBorderRadius(float borderRadius) {
        this.borderRadius = borderRadius;
    }

    public void setDimOnHover(boolean dimOnHover) {
        this.dimOnHover = dimOnHover;
    }

    @Override
    public void setHovered(boolean hovered) {
        if (this.w$src$Z$e457mb() != hovered && !this.selected) {
            this.backgroundAnimation.J();
        }
        super.setHovered(hovered);
    }

    public float getBorderAlpha() {
        return this.borderAlpha;
    }

    @Nullable
    public Color getAnimatedBorderColor() {
        return this.borderColor;
    }
}

