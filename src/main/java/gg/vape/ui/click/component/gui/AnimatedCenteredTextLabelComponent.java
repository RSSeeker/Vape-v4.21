package gg.vape.ui.click.component.gui;

import gg.vape.ui.click.animation.ColorAnimation;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.gui.TextLabel;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

public class AnimatedCenteredTextLabelComponent
extends TextLabel {
    private float borderAlpha;
    private ColorAnimation borderAnimation;
    private Color textColor;

    @Override
    public double C() {
        return 0.0;
    }

    @Override
    public AnimatedCenteredTextLabelComponent setTextColor(Color textColor) {
        this.textColor = textColor;
        return this;
    }

    public AnimatedCenteredTextLabelComponent(String text, Color borderColor) {
        super(text);
        this.textColor = AnimatedCenteredTextLabelComponent.J.Z;
        this.borderAlpha = 1.0f;
        this.borderAnimation = new ColorAnimation(0.15, borderColor, borderColor.brighter());
    }

    @Override
    public void H() {
        GuiRenderPrimitives.P(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), this.borderAnimation.getInterpolatedColor(), 2.0f, this.borderAlpha, 1.0f);
        SmoothFontRenderer smoothFontRenderer = this.isUsingAlternateFont() ? this.getAlternateFontRenderer(this.fontScale) : this.getFontRenderer(this.fontScale);
        double d = smoothFontRenderer.d(this.text);
        double d2 = this.n() + this.L() / 2.0 - d / 2.0;
        smoothFontRenderer.W(this.text, this.G$src$D$1b2f02a() + this.A() / 2.0, d2, this.getTextColor());
    }

    public GuiComponent setBorderAnimationColors(Color normalColor, Color hoverColor) {
        this.borderAnimation = new ColorAnimation(0.15, normalColor, hoverColor);
        return super.setDisabledOverlayColor(normalColor);
    }


    @Override
    public double x() {
        return 0.0;
    }

    public void setBorderAlpha(float borderAlpha) {
        this.borderAlpha = borderAlpha;
    }

    @Override
    public Color getTextColor() {
        return this.textColor;
    }

    @Override
    public void setHovered(boolean hovered) {
        if (this.w$src$Z$e457mb() != hovered) {
            this.borderAnimation.J();
        }
        super.setHovered(hovered);
    }
}

