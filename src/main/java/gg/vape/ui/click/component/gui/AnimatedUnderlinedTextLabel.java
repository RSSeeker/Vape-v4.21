package gg.vape.ui.click.component.gui;

import gg.vape.ui.click.animation.DoubleAnimation;
import gg.vape.ui.click.component.gui.UnderlinedTextLabel;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ImageRenderer;
import gg.vape.utils.render.OpenGlBackendHolder;
import java.awt.Color;

public class AnimatedUnderlinedTextLabel
extends UnderlinedTextLabel {
    private static final String ARROW_ICON_RESOURCE = "expandarrow";
    private final DoubleAnimation hoverAnimation = new DoubleAnimation(0.15, 0.0, 1.0);
    private float arrowSize = 4.0f;

    @Override
    public double C() {
        SmoothFontRenderer smoothFontRenderer = this.getFontRenderer(this.fontScale);
        return smoothFontRenderer.d(this.text);
    }

    public AnimatedUnderlinedTextLabel(String text, double fontScale, Color textColor, Color underlineColor) {
        super(text, fontScale, textColor, underlineColor);
    }


    @Override
    public void H() {
        SmoothFontRenderer fontRenderer = this.getFontRenderer(this.fontScale);
        double textHeight = fontRenderer.d(this.text);
        double width = this.A();
        double x = this.G$src$D$1b2f02a();
        double textY = this.n() + this.L() / 2.0 - textHeight / 2.0;
        Color renderedTextColor = this.w$src$Z$e457mb() ? this.getTextColor().brighter() : this.getTextColor();
        Color renderedUnderlineColor = this.w$src$Z$e457mb() ? this.getUnderlineColor().brighter() : this.getUnderlineColor();
        if (this.w$src$Z$e457mb()) {
            if (!this.hoverAnimation.I$src$Z$c48gtw()) {
                this.hoverAnimation.c();
            }
        } else if (this.hoverAnimation.I$src$Z$c48gtw()) {
            this.hoverAnimation.Z();
        }
        fontRenderer.d(this.text, x, textY, renderedTextColor);
        OpenGlBackendHolder.backend.pushMatrix();
        float renderScale = 2.0f;
        OpenGlBackendHolder.backend.scale(0.5f, 0.5f, 0.5f);
        int underlineOffset = 0;
        while ((double)underlineOffset < width) {
            GuiRenderPrimitives.V((int)((this.G$src$D$1b2f02a() + (double)underlineOffset + 1.0 * this.fontScale) * (double)renderScale), (int)((textY + textHeight + 2.0 * this.fontScale) * (double)renderScale), 1.0 * this.fontScale, 1.0 * this.fontScale, renderedUnderlineColor);
            ++underlineOffset;
        }
        OpenGlBackendHolder.backend.popMatrix();
        ImageRenderer.drawImage(renderedTextColor, (float)this.G$src$D$1b2f02a() + (float)this.A() - 5.0f + this.hoverAnimation.getInterpolatedValue().floatValue(), (float)textY + 2.0f, ARROW_ICON_RESOURCE, this.arrowSize, this.arrowSize, false);
    }

    @Override
    public double x() {
        return super.x();
    }
}

