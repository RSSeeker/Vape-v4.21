package gg.vape.ui.click.component;

import gg.vape.Vape;
import gg.vape.ui.click.component.gui.InteractiveComponent;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.unmap.ColorUtil;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

public class DropdownSelectOptionComponent
extends InteractiveComponent {
    private Color textColor;
    private boolean showNewBadge;
    protected double fontScale;
    private boolean showBetaBadge;
    protected String label;
    private boolean useAlternateFont;

    public double getTextY() {
        SmoothFontRenderer fontRenderer = this.useAlternateFont ? this.getAlternateFontRenderer(this.fontScale) : this.getFontRenderer(this.fontScale);
        double textHeight = fontRenderer.d(this.label);
        return this.n() + this.L() / 2.0 - textHeight / 2.0;
    }

    public void setShowBetaBadge(boolean showBetaBadge) {
        this.showBetaBadge = showBetaBadge;
    }

    public boolean isShowNewBadge() {
        return this.showNewBadge;
    }

    public DropdownSelectOptionComponent(String label) {
        this(label, 0.9);
    }


    public void setShowNewBadge(boolean showNewBadge) {
        this.showNewBadge = showNewBadge;
    }

    @Override
    public void u() {
        if (this.A() < 0.0) {
            this.o(this.getTextWidth());
        }
    }

    public DropdownSelectOptionComponent setUseAlternateFont(boolean useAlternateFont) {
        this.useAlternateFont = useAlternateFont;
        return this;
    }

    public boolean isUseAlternateFont() {
        return this.useAlternateFont;
    }

    @Override
    public double x() {
        return 0.0;
    }

    public DropdownSelectOptionComponent(String label, double fontScale, double width, double height) {
        this.textColor = DropdownSelectOptionComponent.J.Z;
        this.label = label;
        this.fontScale = fontScale;
        this.o(width);
        this.Y(height);
    }

    public DropdownSelectOptionComponent(String label, double fontScale) {
        this.textColor = DropdownSelectOptionComponent.J.Z;
        this.label = label;
        this.fontScale = fontScale;
    }

    public Color getTextColor() {
        return this.textColor;
    }

    public double getTextHeight() {
        SmoothFontRenderer fontRenderer = this.useAlternateFont ? this.getAlternateFontRenderer(this.fontScale) : this.getFontRenderer(this.fontScale);
        return fontRenderer.d(this.label.toUpperCase());
    }

    public DropdownSelectOptionComponent setTextColor(Color textColor) {
        this.textColor = textColor;
        return this;
    }

    public void setFontScale(double fontScale) {
        this.fontScale = fontScale;
    }

    @Override
    public void H() {
        SmoothFontRenderer badgeFontRenderer;
        double badgeY;
        double badgeX;
        String displayText = this.label;
        SmoothFontRenderer labelFontRenderer = this.useAlternateFont ? this.getAlternateFontRenderer(this.fontScale) : this.getFontRenderer(this.fontScale);
        double textY = this.getTextY();
        if (this.w$src$Z$e457mb()) {
            GuiRenderPrimitives.C(this.G$src$D$1b2f02a() + 0.5, this.n(), this.A() - 0.5, this.L(), DropdownSelectOptionComponent.J.m);
        }
        Color resolvedTextColor = this.textColor != null ? (this.w$src$Z$e457mb() ? this.textColor.brighter() : this.textColor) : (this.w$src$Z$e457mb() ? DropdownSelectOptionComponent.J.A : DropdownSelectOptionComponent.J.Z);
        double x = this.G$src$D$1b2f02a();
        this.getClass();
        labelFontRenderer.d(displayText, x + 5.0, textY, resolvedTextColor);
        if (this.showNewBadge) {
            double textEndX = this.G$src$D$1b2f02a() + labelFontRenderer.N(displayText);
            this.getClass();
            badgeX = textEndX + (double)(5.0f * 2.0f);
            badgeY = textY + 0.5;
            GuiRenderPrimitives.d(badgeX, badgeY, 20.0, 7.0, J.z());
            badgeFontRenderer = Vape.INSTANCE.getFontManager().W(0.8, false);
            badgeFontRenderer.d("New!", badgeX + 3.0, badgeY + 0.5, ColorUtil.getContrastingGray(J.z(), 35, 255));
        }
        if (this.showBetaBadge) {
            double textEndX = this.G$src$D$1b2f02a() + labelFontRenderer.N(displayText);
            this.getClass();
            badgeX = textEndX + (double)(5.0f * 2.0f);
            if (this.showNewBadge) {
                badgeX += 24.0;
            }
            badgeY = textY + 0.5;
            badgeFontRenderer = Vape.INSTANCE.getFontManager().W(0.8, false);
            double badgeWidth = badgeFontRenderer.N("Beta") + 6.0;
            GuiRenderPrimitives.d(badgeX, badgeY, badgeWidth, 7.0, J.z());
            badgeFontRenderer.d("Beta", badgeX + 3.0, badgeY + 0.5, ColorUtil.getContrastingGray(J.z(), 35, 255));
        }
    }

    public String getLabel() {
        return this.label;
    }

    public boolean isShowBetaBadge() {
        return this.showBetaBadge;
    }

    @Override
    public double C() {
        return 0.0;
    }

    public DropdownSelectOptionComponent setLabel(String label) {
        this.label = label;
        this.o(this.getTextWidth());
        return this;
    }

    public double getTextWidth() {
        SmoothFontRenderer fontRenderer = this.useAlternateFont ? this.getAlternateFontRenderer(this.fontScale) : this.getFontRenderer(this.fontScale);
        return fontRenderer.N(this.label.toUpperCase());
    }

    @Override
    public void I() {
    }
}

