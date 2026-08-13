package gg.vape.ui.click.component.gui;

import gg.vape.ui.click.animation.ColorAnimation;
import gg.vape.ui.click.component.gui.ActionButtonBase;
import gg.vape.ui.font.SmoothFontRenderer;
import java.awt.Color;

public class TextActionButton
extends ActionButtonBase {
    protected String text;
    protected boolean useAlternateFont;
    protected ColorAnimation textColorAnimation;
    protected double fontScale;

    public TextActionButton(String text, double fontScale, boolean useAlternateFont, double width, double height, Color backgroundColor, double borderThickness) {
        super(width, height, backgroundColor, borderThickness);
        this.textColorAnimation = new ColorAnimation(0.15, TextActionButton.J.W, TextActionButton.J.f);
        this.text = text;
        this.fontScale = fontScale;
        this.useAlternateFont = useAlternateFont;
    }

    @Override
    public void H() {
        super.H();
        SmoothFontRenderer fontRenderer = this.useAlternateFont ? this.getAlternateFontRenderer(this.fontScale) : this.getFontRenderer(this.fontScale);
        fontRenderer.d(this.text, this.G$src$D$1b2f02a() + this.A() / 2.0 - fontRenderer.N(this.text) / 2.0, this.n() + this.L() / 2.0 - fontRenderer.d(this.text) / 2.0, this.textColorAnimation.getInterpolatedColor());
    }

    public void setTextColorAnimation(ColorAnimation textColorAnimation) {
        this.textColorAnimation = textColorAnimation;
    }


    public ColorAnimation getTextColorAnimation() {
        return this.textColorAnimation;
    }

    public TextActionButton(String text, double fontScale, boolean useAlternateFont, double width, double height, Color backgroundColor) {
        super(width, height, backgroundColor);
        this.textColorAnimation = new ColorAnimation(0.15, TextActionButton.J.W, TextActionButton.J.f);
        this.text = text;
        this.fontScale = fontScale;
        this.useAlternateFont = useAlternateFont;
    }

    @Override
    public void setHovered(boolean hovered) {
        if (this.hovered != hovered) {
            this.textColorAnimation.J();
        }
        super.setHovered(hovered);
    }
}

