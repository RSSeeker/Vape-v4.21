package gg.vape.ui.click.component;

import gg.vape.ui.click.animation.ColorAnimation;
import gg.vape.ui.click.component.gui.InteractiveComponent;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ImageRenderer;
import java.awt.Color;
import org.jetbrains.annotations.Nullable;

public class GlyphIconComponent
extends InteractiveComponent {
    private double offsetY;
    private double iconWidth;
    private double offsetX;
    private ColorAnimation backgroundAnimation = null;
    @Nullable
    private Color outlineColor;
    private float cornerRadius = 2.0f;
    private float outlineAlpha = 0.8f;
    private boolean centerHorizontally = false;
    private double iconHeight;
    private String iconResource;
    private boolean centerVertically = false;

    public boolean isCenterHorizontally() {
        return this.centerHorizontally;
    }

    public ColorAnimation getBackgroundAnimation() {
        return this.backgroundAnimation;
    }

    public String getIconResource() {
        return this.iconResource;
    }

    public void setOffsetX(double offsetX) {
        this.offsetX = offsetX;
    }

    public void setCenterVertically(boolean centerVertically) {
        this.centerVertically = centerVertically;
    }

    public boolean isCenterVertically() {
        return this.centerVertically;
    }


    public GlyphIconComponent(String iconResource, double iconWidth, double iconHeight, double width, double height, @Nullable Color color, @Nullable Color hoverColor, @Nullable Color outlineColor) {
        this.iconResource = iconResource;
        this.o(width);
        this.Y(height);
        this.iconWidth = iconWidth;
        this.iconHeight = iconHeight;
        this.setNormalColor(color != null ? color : GlyphIconComponent.J.W);
        this.setHoverColor(hoverColor != null ? hoverColor : GlyphIconComponent.J.f);
        this.outlineColor = outlineColor;
    }

    public void setBackgroundAnimation(ColorAnimation backgroundAnimation) {
        this.backgroundAnimation = backgroundAnimation;
    }

    @Override
    public double x() {
        return 0.0;
    }

    public void setIconResource(String iconResource) {
        this.iconResource = iconResource;
    }

    public void setBackgroundAnimationColors(Color color, Color hoverColor) {
        if (hoverColor == null) {
            this.backgroundAnimation = null;
            return;
        }
        this.getClass();
        this.backgroundAnimation = new ColorAnimation(0.15, color, hoverColor);
    }

    @Override
    public double C() {
        return 0.0;
    }

    public void setOutlineColor(@Nullable Color outlineColor) {
        this.outlineColor = outlineColor;
    }

    public double getOffsetY() {
        return this.offsetY;
    }

    public void setIconHeight(double iconHeight) {
        this.iconHeight = iconHeight;
    }

    public double getOffsetX() {
        return this.offsetX;
    }

    public float getCornerRadius() {
        return this.cornerRadius;
    }

    public void setCornerRadius(float cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    @Override
    public void H() {
        double iconX = this.G$src$D$1b2f02a();
        double iconY = this.n();
        if (this.centerHorizontally) {
            iconX += this.A() / 2.0 - this.iconWidth / 2.0;
        }
        iconX += this.offsetX;
        if (this.centerVertically) {
            iconY += this.L() / 2.0 - this.iconHeight / 2.0;
        }
        iconY += this.offsetY;
        if (this.backgroundAnimation != null) {
            this.backgroundAnimation.u(this.w$src$Z$e457mb());
            if ((double)this.cornerRadius == this.A() / 2.0 && (double)this.cornerRadius == this.L() / 2.0) {
                GuiRenderPrimitives.V(this.G$src$D$1b2f02a(), this.n(), this.cornerRadius * 2.0f, 1.0, this.backgroundAnimation.getInterpolatedColor());
            } else {
                GuiRenderPrimitives.e(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), this.backgroundAnimation.getInterpolatedColor(), false, this.cornerRadius, 1.0f);
            }
        }
        Color renderedColor = this.getNormalColor();
        if (this.getHoverColor() != null && this.w$src$Z$e457mb()) {
            renderedColor = this.getHoverColor();
        }
        ImageRenderer.drawImage(renderedColor, (int)iconX, (int)iconY, this.iconResource, (float)this.iconWidth, (float)this.iconHeight, false);
        if (this.outlineColor != null) {
            GuiRenderPrimitives.P(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), this.w$src$Z$e457mb() ? this.outlineColor.brighter() : this.outlineColor, this.cornerRadius, this.outlineAlpha, 1.0f);
        }
    }

    public void setOutlineAlpha(float outlineAlpha) {
        this.outlineAlpha = outlineAlpha;
    }

    public void setCenterHorizontally(boolean centerHorizontally) {
        this.centerHorizontally = centerHorizontally;
    }

    public double getIconHeight() {
        return this.iconHeight;
    }

    @Nullable
    public Color getOutlineColor() {
        return this.outlineColor;
    }

    public double getIconWidth() {
        return this.iconWidth;
    }

    public void setIconWidth(double iconWidth) {
        this.iconWidth = iconWidth;
    }

    public void setOffsetY(double offsetY) {
        this.offsetY = offsetY;
    }

    public float getOutlineAlpha() {
        return this.outlineAlpha;
    }
}

