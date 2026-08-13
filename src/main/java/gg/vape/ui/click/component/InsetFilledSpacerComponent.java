package gg.vape.ui.click.component;

import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

public class InsetFilledSpacerComponent
extends SpacerComponent {
    private final double lineHeight;
    private final double horizontalInset;
    private final Color color;

    @Override
    public void renderSpacer() {
        super.renderSpacer();
        GuiRenderPrimitives.C(this.double_G() + this.horizontalInset, this.double_n() + this.double_L() / 2.0 - this.lineHeight / 2.0, this.double_A() - this.horizontalInset * 2.0, this.lineHeight, this.color);
    }

    public InsetFilledSpacerComponent(double width, double height, double lineHeight, double horizontalInset, Color color) {
        super(width, height);
        this.lineHeight = lineHeight;
        this.horizontalInset = horizontalInset;
        this.color = color;
    }
}
