package gg.vape.ui.click.component;

import gg.vape.ui.click.component.ToolTips;
import gg.vape.ui.font.SmoothFontRenderer;
import java.awt.Color;

class TooltipTextSegment {
    public boolean useAlternateFont;
    public String text;
    public double fontScale;
    public Color color;
    final ToolTips owner;

    public double getHeight() {
        SmoothFontRenderer fontRenderer = this.useAlternateFont ? this.owner.getAlternateFontRenderer(this.fontScale) : this.owner.getFontRenderer(this.fontScale);
        return fontRenderer.d(this.text);
    }

    public TooltipTextSegment(ToolTips owner, String text, double fontScale, Color color, boolean useAlternateFont) {
        this.owner = owner;
        this.text = text;
        this.fontScale = fontScale;
        this.color = color;
        this.useAlternateFont = useAlternateFont;
    }

    public double getWidth() {
        SmoothFontRenderer fontRenderer = this.useAlternateFont ? this.owner.getAlternateFontRenderer(this.fontScale) : this.owner.getFontRenderer(this.fontScale);
        return fontRenderer.N(this.text);
    }

    public void renderAt(double x, double y) {
        SmoothFontRenderer fontRenderer = this.useAlternateFont ? this.owner.getAlternateFontRenderer(this.fontScale) : this.owner.getFontRenderer(this.fontScale);
        fontRenderer.v(this.text, x, y, this.color);
    }

}

