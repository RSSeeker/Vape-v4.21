package gg.vape.module.render.entity;

import gg.vape.module.render.entity.RenderEntityContext;
import java.awt.Color;

public class RenderEntityContextEntry {
    private Color color;
    private final RenderEntityContext context;
    private double scale = 1.0;
    private boolean focused;

    public RenderEntityContextEntry(RenderEntityContext renderContext, Color color) {
        this.context = renderContext;
        this.color = color;
    }

    public double getScale() {
        return this.scale;
    }

    public boolean isFocused() {
        return this.focused;
    }

    public RenderEntityContext getContext() {
        return this.context;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    public Color getColor() {
        return this.color;
    }
}

