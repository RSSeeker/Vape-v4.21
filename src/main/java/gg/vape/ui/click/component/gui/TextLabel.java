package gg.vape.ui.click.component.gui;

import gg.vape.ui.click.component.gui.InteractiveComponent;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

public class TextLabel
extends InteractiveComponent {
    private boolean centered;
    private static String legacyState;
    protected String text;
    protected boolean uppercase = true;
    protected double fontScale;
    private boolean underlined;
    private boolean useAlternateFont;
    private Color borderColor;
    private Color textColor;

    @Override
    public double x() {
        return 0.0;
    }

    static {
        if (TextLabel.getLegacyState() != null) {
            TextLabel.setLegacyState("dpEwx");
        }
    }

    public double getUppercaseTextHeight() {
        SmoothFontRenderer fontRenderer = this.useAlternateFont ? this.getAlternateFontRenderer(this.fontScale) : this.getFontRenderer(this.fontScale);
        return fontRenderer.d(this.text.toUpperCase());
    }

    public boolean isUnderlined() {
        return this.underlined;
    }

    public TextLabel(String text) {
        this(text, 0.9);
    }

    public double getTextY() {
        SmoothFontRenderer fontRenderer = this.useAlternateFont ? this.getAlternateFontRenderer(this.fontScale) : this.getFontRenderer(this.fontScale);
        double textHeight = fontRenderer.d(this.text);
        return this.n() + this.L() / 2.0 - textHeight / 2.0;
    }

    public TextLabel setLabelText(String text) {
        this.text = text;
        this.o(this.getTextWidth());
        return this;
    }

    @Override
    public void I() {
    }

    public TextLabel setUseAlternateFont(boolean useAlternateFont) {
        this.useAlternateFont = useAlternateFont;
        return this;
    }

    public double getTextWidth() {
        SmoothFontRenderer fontRenderer = this.useAlternateFont ? this.getAlternateFontRenderer(this.fontScale) : this.getFontRenderer(this.fontScale);
        return fontRenderer.N(this.text.toUpperCase());
    }

    public TextLabel(String text, double fontScale, boolean uppercase) {
        this.textColor = TextLabel.J.Z;
        this.borderColor = null;
        this.text = text;
        this.fontScale = fontScale;
        this.uppercase = uppercase;
    }

    public TextLabel setTextColor(Color textColor) {
        this.textColor = textColor;
        return this;
    }

    public Color getTextColor() {
        return this.textColor;
    }

    @Override
    public void u() {
        if (this.A() < 0.0) {
            this.o(this.getTextWidth());
        }
    }

    public TextLabel(String text, double fontScale) {
        this.textColor = TextLabel.J.Z;
        this.borderColor = null;
        this.text = text;
        this.fontScale = fontScale;
    }

    public String getText() {
        return this.text;
    }

    public void setUnderlined(boolean underlined) {
        this.underlined = underlined;
    }

    public static String getLegacyState() {
        return legacyState;
    }

    public TextLabel setCentered(boolean centered) {
        this.centered = centered;
        return this;
    }

    public boolean isUsingAlternateFont() {
        return this.useAlternateFont;
    }

    public TextLabel(String text, double fontScale, boolean uppercase, Color borderColor) {
        this(text, fontScale, uppercase);
        this.borderColor = borderColor;
    }

    @Override
    public double C() {
        return 0.0;
    }

    public static void setLegacyState(String state) {
        legacyState = state;
    }

    public boolean isCentered() {
        return this.centered;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public void setUppercase(boolean uppercase) {
        this.uppercase = uppercase;
    }

    public TextLabel(String text, double fontScale, boolean uppercase, double width, double height) {
        this.textColor = TextLabel.J.Z;
        this.borderColor = null;
        this.text = text;
        this.fontScale = fontScale;
        this.uppercase = uppercase;
        this.o(width);
        this.Y(height);
    }

    public void setFontScale(double fontScale) {
        this.fontScale = fontScale;
    }


    @Override
    public void H() {
        String renderedText = this.uppercase ? this.text.toUpperCase() : this.text;
        SmoothFontRenderer fontRenderer = this.useAlternateFont ? this.getAlternateFontRenderer(this.fontScale) : this.getFontRenderer(this.fontScale);
        double textY = this.getTextY();
        Color renderedColor = this.textColor != null
                ? (this.w$src$Z$e457mb() ? this.textColor.brighter() : this.textColor)
                : (this.w$src$Z$e457mb() ? TextLabel.J.A : TextLabel.J.Z);
        if (this.borderColor != null) {
            fontRenderer.W(renderedText, this.G$src$D$1b2f02a() + this.A() / 2.0, textY, renderedColor);
            GuiRenderPrimitives.P(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), this.w$src$Z$e457mb() ? this.borderColor.brighter() : this.borderColor, 1.5f, 0.75f, 1.0f);
            return;
        }
        if (this.centered) {
            fontRenderer.W(renderedText, this.G$src$D$1b2f02a() + this.A() / 2.0, textY, renderedColor);
        } else {
            fontRenderer.d(renderedText, this.G$src$D$1b2f02a(), textY, renderedColor);
        }
        if (this.underlined) {
            GuiRenderPrimitives.L(this.G$src$D$1b2f02a(), textY + fontRenderer.d(renderedText), this.A(), TextLabel.J.Z);
        }
    }

    public boolean isUppercase() {
        return this.uppercase;
    }

    public Color getBorderColor() {
        return this.borderColor;
    }
}
