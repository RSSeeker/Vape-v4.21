package gg.vape.ui.click.component.gui;

import gg.vape.ui.click.component.gui.TextLabel;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

public class UnderlinedTextLabel
extends TextLabel {
    private Color underlineColor;

    public UnderlinedTextLabel(String text, double fontScale, Color textColor, Color underlineColor) {
        super(text, fontScale);
        this.underlineColor = UnderlinedTextLabel.J.Z;
        this.setTextColor(textColor);
        this.underlineColor = underlineColor;
    }

    @Override
    public double C() {
        SmoothFontRenderer smoothFontRenderer = this.getFontRenderer(this.fontScale);
        return smoothFontRenderer.d(this.text);
    }


    @Override
    public void H() {
        SmoothFontRenderer smoothFontRenderer = this.getFontRenderer(this.fontScale);
        double d = smoothFontRenderer.d(this.text);
        double d2 = smoothFontRenderer.N(this.text) + 1.0;
        double d3 = this.G$src$D$1b2f02a() + this.A() / 2.0;
        double d4 = this.n() + this.L() / 2.0 - d / 2.0;
        Color color = this.w$src$Z$e457mb() ? this.getTextColor().brighter() : this.getTextColor();
        smoothFontRenderer.W(this.text, d3, d4, color);
        double d5 = d3 - d2 / 2.0 + 1.0;
        GuiRenderPrimitives.z(d5, d4 + d, d5 + d2, d4 + d, 1.0, 1.5, color);
    }

    @Override
    public double x() {
        SmoothFontRenderer smoothFontRenderer = this.getFontRenderer(this.fontScale);
        return smoothFontRenderer.N(this.text);
    }

    public UnderlinedTextLabel(String text, double fontScale) {
        super(text, fontScale);
        this.underlineColor = UnderlinedTextLabel.J.Z;
    }

    public Color getUnderlineColor() {
        return this.underlineColor;
    }

    public UnderlinedTextLabel(String text, double fontScale, Color color) {
        super(text, fontScale);
        this.underlineColor = UnderlinedTextLabel.J.Z;
        this.setTextColor(color);
        this.underlineColor = color;
    }

    public UnderlinedTextLabel(String text) {
        super(text);
        this.underlineColor = UnderlinedTextLabel.J.Z;
    }
}

