package gg.vape.ui.click.frame.impl.profile;

import gg.vape.ui.click.component.gui.InteractiveComponent;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

public class ProfileCreateActionButtonComponent
extends InteractiveComponent {
    private double iconOffset;
    private boolean dynamicTextColor;
    private Color borderColor;
    private boolean uppercase;
    private Color iconColor;
    private boolean dynamicIconColor;
    private double iconScale;
    private String iconKey;
    private boolean useSmoothFont;
    private String label;
    private boolean hoverBackgroundVisible = true;
    private double textScale;
    private Color textColor;

    @Override
    public void H() {
        SmoothFontRenderer font = this.useSmoothFont
            ? this.getAlternateFontRenderer(this.textScale)
            : this.getFontRenderer(this.textScale);
        float iconSize = 8.0f * (float)this.iconScale;
        double contentX = this.G$src$D$1b2f02a() + iconSize / 2.0f + this.iconOffset + this.getHorizontalInset();
        boolean hovered = this.w$src$Z$e457mb();
        if (hovered && this.hoverBackgroundVisible) {
            GuiRenderPrimitives.d(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), J.z());
        }
        Color renderedIconColor = hovered ? J.B() : (this.dynamicIconColor ? J.z() : this.iconColor);
        if (!this.hoverBackgroundVisible) {
            renderedIconColor = hovered ? this.iconColor.brighter() : this.iconColor;
        }
        GuiRenderPrimitives.F(this.iconKey, contentX, this.n() + this.L() / 2.0, iconSize, iconSize, renderedIconColor);
        contentX += 3.3333333333333335 + iconSize / 2.0f;
        if (this.textColor != null) {
            Color renderedTextColor = hovered ? J.B() : (this.dynamicTextColor ? J.z() : this.textColor);
            if (!this.hoverBackgroundVisible) {
                renderedTextColor = hovered ? this.textColor.brighter() : this.textColor;
            }
            this.renderLabel(font, contentX, renderedTextColor);
        }
        if (this.borderColor != null) {
            GuiRenderPrimitives.P(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), hovered ? this.borderColor.brighter() : this.borderColor, 1.5f, 0.75f, 1.0f);
        }
    }

    public void setIconScale(double iconScale) {
        this.iconScale = iconScale;
    }


    @Override
    public double x() {
        return 0.0;
    }

    @Override
    public double C() {
        return 0.0;
    }

    public void setIconOffset(double iconOffset) {
        this.iconOffset = iconOffset;
    }

    @Override
    public void u() {
        if (this.A() <= 0.0) {
            SmoothFontRenderer font = this.useSmoothFont
                ? this.getAlternateFontRenderer(this.textScale)
                : this.getFontRenderer(this.textScale);
            String renderedLabel = this.uppercase ? this.label.toUpperCase() : this.label;
            this.o(8.0 * this.iconScale + font.N(renderedLabel) + 13.75);
        }
    }

    private void renderLabel(SmoothFontRenderer font, double x, Color color) {
        String renderedLabel = this.uppercase ? this.label.toUpperCase() : this.label;
        double y = this.n() + this.L() / 2.0 - font.d(renderedLabel) / 2.0;
        font.d(renderedLabel, x, y, color);
    }

    public ProfileCreateActionButtonComponent(String label, boolean uppercase, boolean useSmoothFont, double textScale, Color textColor, String iconKey, double iconScale, Color iconColor, Color borderColor) {
        this.label = label;
        this.uppercase = uppercase;
        this.useSmoothFont = useSmoothFont;
        this.textScale = textScale;
        this.textColor = textColor;
        if (textColor == null) {
            this.textColor = ProfileCreateActionButtonComponent.J.Z;
        }
        if (this.textColor.equals(J.z())) {
            this.dynamicTextColor = true;
        }
        this.iconKey = iconKey;
        this.iconScale = iconScale;
        this.iconColor = iconColor;
        if (iconColor == null) {
            this.iconColor = ProfileCreateActionButtonComponent.J.W;
        }
        if (this.iconColor.equals(J.z())) {
            this.dynamicIconColor = true;
        }
        this.borderColor = borderColor;
        this.Y(20.0);
    }

    public void setHoverBackgroundVisible(boolean visible) {
        this.hoverBackgroundVisible = visible;
    }

    public void setTextScale(double textScale) {
        this.textScale = textScale;
    }
}
