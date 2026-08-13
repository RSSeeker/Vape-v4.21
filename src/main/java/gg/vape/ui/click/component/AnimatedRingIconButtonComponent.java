package gg.vape.ui.click.component;

import gg.vape.ui.click.animation.ColorAnimation;
import gg.vape.ui.click.component.IconButtonComponent;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

public class AnimatedRingIconButtonComponent
extends IconButtonComponent {
    private final ColorAnimation ringAnimation;

    @Override
    public void H() {
        super.H();
        float diameter = (float)this.A();
        GuiRenderPrimitives.m((float)(this.G$src$D$1b2f02a() + this.A() / 2.0 - (double)(diameter / 2.0f)), (float)(this.n() + this.L() / 2.0 - (double)(diameter / 2.0f)), diameter, 1.5f, 1.0f, this.ringAnimation.getInterpolatedColor());
    }

    @Override
    public void onEnable() {
        this.ringAnimation.J();
    }


    public AnimatedRingIconButtonComponent(String iconResource, Color ringColor, double iconScale, double width, double height) {
        super(iconResource, iconScale, width, height);
        this.ringAnimation = new ColorAnimation(0.1, ringColor, ringColor.brighter());
    }

    @Override
    public void F() {
        if (!this.w$src$Z$e457mb()) {
            this.ringAnimation.J();
        }
        super.F();
    }
}

