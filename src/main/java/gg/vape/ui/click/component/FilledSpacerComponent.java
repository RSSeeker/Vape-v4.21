package gg.vape.ui.click.component;

import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

public class FilledSpacerComponent
extends SpacerComponent {
    private Color fillColor;
    private double fillHeight;
    private double fillWidth;

    public FilledSpacerComponent(double width, double height, double fillWidth, double fillHeight, Color fillColor) {
        super(width, height);
        this.fillColor = fillColor;
        this.fillWidth = fillWidth;
        this.fillHeight = fillHeight;
    }

    public FilledSpacerComponent(double width, double height, Color fillColor) {
        super(width, height);
        this.fillColor = fillColor;
        this.fillWidth = width;
        this.fillHeight = height;
    }

    @Override
    public void c() {
        super.c();
        double fillX = this.G$src$D$1b2f02a() + (this.A() - this.fillWidth) / 2.0;
        double fillY = this.n() + (this.L() - this.fillHeight) / 2.0;
        GuiRenderPrimitives.C(fillX, fillY, this.fillWidth, this.fillHeight, this.fillColor);
    }
}
