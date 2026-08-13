package gg.vape.ui.click.component;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.utils.render.ImageRenderer;
import java.awt.Color;

public class IconGlyphComponent
extends GuiComponent {
    private static String[] legacyState;
    private final float iconHeight;
    private String iconResource;
    private boolean snapToPixels;
    private final float iconWidth;
    private Color color;

    public IconGlyphComponent(String iconResource, float iconWidth, float iconHeight) {
        this.iconResource = iconResource;
        this.iconWidth = iconWidth;
        this.iconHeight = iconHeight;
        this.color = Color.white;
    }

    @Override
    public void I() {
    }

    @Override
    public double x() {
        return this.iconWidth;
    }

    public IconGlyphComponent(String iconResource, float iconWidth, float iconHeight, Color color) {
        this.iconResource = iconResource;
        this.iconWidth = iconWidth;
        this.iconHeight = iconHeight;
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public String getIconResource() {
        return this.iconResource;
    }

    public static void setLegacyState(String[] legacyState) {
        IconGlyphComponent.legacyState = legacyState;
    }

    public static String[] getLegacyState() {
        return legacyState;
    }

    static {
        if (IconGlyphComponent.getLegacyState() != null) {
            IconGlyphComponent.setLegacyState(new String[3]);
        }
    }

    @Override
    public double C() {
        return this.iconHeight;
    }

    public void setIconResource(String iconResource) {
        this.iconResource = iconResource;
    }

    public boolean isSnapToPixels() {
        return this.snapToPixels;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void g(GuiMouseEvent mouseEvent) {
    }

    @Override
    public void H() {
        ImageRenderer.drawImage(this.color, this.snapToPixels ? (float)((int)this.G$src$D$1b2f02a()) : (float)this.G$src$D$1b2f02a(), this.snapToPixels ? (float)((int)this.n()) : (float)this.n(), this.iconResource, this.iconWidth, this.iconHeight, false);
    }


    public void setSnapToPixels(boolean snapToPixels) {
        this.snapToPixels = snapToPixels;
    }

    @Override
    public void F() {
    }

    @Override
    public void u() {
    }
}

