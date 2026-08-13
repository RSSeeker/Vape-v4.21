package gg.vape.ui.click.component;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.utils.render.ImageRenderer;
import java.awt.Color;

public class CenteredGlyphComponent
extends GuiComponent {
    private String glyphResource;
    private float glyphHeight;
    private float glyphWidth;
    private Color color;

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void u() {
    }

    @Override
    public void I() {
    }

    public void setGlyphResource(String glyphResource) {
        this.glyphResource = glyphResource;
    }

    public String getGlyphResource() {
        return this.glyphResource;
    }

    public CenteredGlyphComponent(String glyphResource, float glyphWidth, float glyphHeight, Color color) {
        this.glyphResource = glyphResource;
        this.glyphWidth = glyphWidth;
        this.glyphHeight = glyphHeight;
        this.color = color;
    }

    @Override
    public double x() {
        return this.glyphWidth;
    }

    @Override
    public void g(GuiMouseEvent mouseEvent) {
    }

    public void setGlyphHeight(float glyphHeight) {
        this.glyphHeight = glyphHeight;
    }

    public void setGlyphWidth(float glyphWidth) {
        this.glyphWidth = glyphWidth;
    }

    public Color getColor() {
        return this.color;
    }

    @Override
    public void F() {
    }

    public float getGlyphWidth() {
        return this.glyphWidth;
    }

    public CenteredGlyphComponent(String glyphResource, float glyphWidth, float glyphHeight) {
        this(glyphResource, glyphWidth, glyphHeight, Color.white);
    }

    public float getGlyphHeight() {
        return this.glyphHeight;
    }

    @Override
    public double C() {
        return this.glyphHeight;
    }

    @Override
    public void H() {
        float centerOffsetX = this.getGlyphWidth() / 2.0f;
        float centerOffsetY = this.getGlyphHeight() / 2.0f;
        ImageRenderer.drawImage(this.color, (float)this.G$src$D$1b2f02a() + centerOffsetX, (float)this.n() + centerOffsetY, this.glyphResource, this.glyphWidth, this.glyphHeight, false);
    }
}
