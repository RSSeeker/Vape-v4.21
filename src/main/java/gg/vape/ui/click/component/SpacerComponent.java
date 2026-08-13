package gg.vape.ui.click.component;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiComponent;

public class SpacerComponent
extends GuiComponent {
    private double spacerHeight;
    private double spacerWidth;
    private static int legacyState;

    @Override
    public void g(GuiMouseEvent mouseEvent) {
    }

    public static int getLegacyCompatibilityValue() {
        int legacyState = SpacerComponent.getLegacyState();
        if (legacyState == 0) {
            return 41;
        }
        return 0;
    }

    @Override
    public double C() {
        return this.spacerHeight;
    }

    @Override
    public void o(double width) {
        super.o(width);
    }

    static {
        if (SpacerComponent.getLegacyCompatibilityValue() != 0) {
            SpacerComponent.setLegacyState(56);
        }
    }

    @Override
    public void I() {
    }

    public static int getLegacyState() {
        return legacyState;
    }

    public SpacerComponent(double width, double height) {
        this.spacerWidth = width;
        this.spacerHeight = height;
        this.o(width);
        this.Y(height);
    }

    @Override
    public void u() {
    }

    @Override
    public double x() {
        return this.spacerWidth;
    }

    @Override
    public void H() {
    }


    public static void setLegacyState(int legacyState) {
        SpacerComponent.legacyState = legacyState;
    }

    @Override
    public void Y(double height) {
        super.Y(height);
        this.setExplicitHeight(height);
        this.spacerHeight = height;
    }

    @Override
    public void F() {
    }

    public /* synthetic */ void renderSpacer() {
        this.H();
    }
}

