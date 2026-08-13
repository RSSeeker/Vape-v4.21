package gg.vape.ui.click.component;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

public class ColorDividerComponent
extends GuiComponent {
    private double dividerHeight;
    private static int[] legacyState;
    private Color color;
    double dividerWidth = 110.0;

    @Override
    public void I() {
        GuiRenderPrimitives.C(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), this.color);
    }

    public ColorDividerComponent(Color color) {
        this(color, 0.5);
    }

    @Override
    public void H() {
        GuiRenderPrimitives.C(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), this.color);
    }

    public ColorDividerComponent(Color color, double height, double width) {
        this.color = color;
        this.dividerHeight = height;
        this.dividerWidth = width;
    }

    @Override
    public double x() {
        return this.dividerWidth;
    }

    public static void setLegacyState(int[] legacyState) {
        ColorDividerComponent.legacyState = legacyState;
    }

    @Override
    public double L() {
        return this.C();
    }

    @Override
    public void g(GuiMouseEvent mouseEvent) {
    }

    static {
        if (ColorDividerComponent.getLegacyState() != null) {
            ColorDividerComponent.setLegacyState(new int[5]);
        }
    }

    @Override
    public void F() {
    }

    public static int[] getLegacyState() {
        return legacyState;
    }

    public ColorDividerComponent(Color color, double height) {
        this.color = color;
        this.dividerHeight = height;
    }

    @Override
    public void u() {
    }

    @Override
    public double C() {
        return this.dividerHeight;
    }
}
