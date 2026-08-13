package gg.vape.ui.click.component;

import gg.vape.ui.click.animation.ColorAnimation;
import gg.vape.ui.click.component.IconButtonComponent;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

public class SquareIconButtonComponent
extends IconButtonComponent {
    private final ColorAnimation backgroundColorAnimation;

    @Override
    public void F() {
        if (!this.w$src$Z$e457mb()) {
            this.backgroundColorAnimation.J();
        }
        super.F();
    }

    public SquareIconButtonComponent(String iconResource, double iconScale, Color backgroundColor, Color hoverBackgroundColor, double width, double height) {
        super(iconResource, iconScale, width, height);
        this.backgroundColorAnimation = new ColorAnimation(0.1, backgroundColor, hoverBackgroundColor);
    }


    public SquareIconButtonComponent(String iconResource, double iconScale) {
        this(iconResource, iconScale, new Color(0, 0, 0, 0), SquareIconButtonComponent.J.h, 8.0, 8.0);
    }

    @Override
    public void H() {
        float backgroundSize = (float)(this.A() * (double)((float)this.iconScale));
        GuiRenderPrimitives.V(this.G$src$D$1b2f02a() + this.A() / 2.0 - (double)(backgroundSize / 2.0f), this.n() + this.L() / 2.0 - (double)(backgroundSize / 2.0f), backgroundSize, 1.0, this.backgroundColorAnimation.getInterpolatedColor());
        super.H();
    }

    public SquareIconButtonComponent(String iconResource) {
        this(iconResource, 1.0);
    }

    @Override
    public void onEnable() {
        this.backgroundColorAnimation.J();
        super.onEnable();
    }
}

