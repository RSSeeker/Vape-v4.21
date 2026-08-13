package gg.vape.ui.click.component;

import gg.vape.ui.click.component.gui.InteractiveComponent;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ImageRenderer;
import java.awt.Color;

@Deprecated
public class IconButtonComponent
extends InteractiveComponent {
    private String iconResource;
    protected double iconScale;
    private static String[] legacyState;
    private Color overrideColor;
    private Color borderColor = null;
    private double imageDivisor = -1.0;

    public IconButtonComponent(String iconResource, double iconScale, double width, double height, Color color, Color hoverColor, Color borderColor) {
        this.iconResource = iconResource;
        this.iconScale = iconScale;
        this.o(width);
        this.Y(height);
        this.setNormalColor(color != null ? color : IconButtonComponent.J.W);
        this.setHoverColor(hoverColor != null ? hoverColor : IconButtonComponent.J.f);
        this.borderColor = borderColor;
    }

    public IconButtonComponent(String iconResource, double iconScale, Color borderColor) {
        this(iconResource, iconScale, 13.0, 13.0, null, null, borderColor);
    }

    public static void setLegacyState(String[] legacyState) {
        IconButtonComponent.legacyState = legacyState;
    }

    public void setOverrideColor(Color overrideColor) {
        this.overrideColor = overrideColor;
    }

    public double getIconScale() {
        return this.iconScale;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public static String[] getLegacyState() {
        return legacyState;
    }

    public IconButtonComponent(String iconResource, double iconScale, double width, double height) {
        this(iconResource, iconScale, width, height, null, null, null);
    }

    public void setIconResource(String iconResource) {
        this.iconResource = iconResource;
    }

    @Override
    public double x() {
        return 0.0;
    }

    static {
        if (IconButtonComponent.getLegacyState() != null) {
            IconButtonComponent.setLegacyState(new String[1]);
        }
    }


    public void setImageDivisor(double imageDivisor) {
        this.imageDivisor = imageDivisor;
    }

    public String getIconResource() {
        return this.iconResource;
    }

    @Override
    public double C() {
        return 0.0;
    }

    @Override
    public void H() {
        double imageHeight;
        double imageWidth;
        if (this.imageDivisor != -1.0) {
            imageWidth = (double)((float)ImageRenderer.getImageWidth(this.iconResource)) / this.imageDivisor;
            imageHeight = (double)((float)ImageRenderer.getImageHeight(this.iconResource)) / this.imageDivisor;
        } else {
            imageWidth = imageHeight = (double)(8.0f * (float)this.iconScale);
        }
        if (this.overrideColor != null) {
            GuiRenderPrimitives.F(this.iconResource, this.G$src$D$1b2f02a() + this.A() / 2.0, this.n() + this.L() / 2.0, imageWidth, imageHeight, this.overrideColor);
        } else {
            GuiRenderPrimitives.F(this.iconResource, this.G$src$D$1b2f02a() + this.A() / 2.0, this.n() + this.L() / 2.0, imageWidth, imageHeight, this.w$src$Z$e457mb() ? this.getHoverColor() : this.getNormalColor());
        }
        if (this.borderColor != null) {
            GuiRenderPrimitives.P(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), this.w$src$Z$e457mb() ? this.borderColor.brighter() : this.borderColor, 2.0f, 0.8f, 1.0f);
        }
    }

    public IconButtonComponent(String iconResource, double iconScale) {
        this(iconResource, iconScale, 13.0, 13.0, null, null, null);
    }

    public void setIconScale(double iconScale) {
        this.iconScale = iconScale;
    }

    public IconButtonComponent(String iconResource) {
        this(iconResource, 1.0, 13.0, 13.0, null, null, null);
    }

    public Color getOverrideColor() {
        return this.overrideColor;
    }

    public IconButtonComponent(String iconResource, double iconScale, Color color, Color hoverColor, double width, double height) {
        this(iconResource, iconScale, width, height, color, hoverColor, null);
    }
}

