package gg.vape.ui.click.component.gui;

import gg.vape.ui.click.animation.ColorAnimation;
import gg.vape.ui.click.component.gui.InteractiveComponent;
import gg.vape.utils.MutableColor;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

public class ActionButtonBase
extends InteractiveComponent {
    protected Color backgroundColor;
    protected double borderThickness;
    protected ColorAnimation backgroundAnimation;

    @Override
    public void F() {
        if (!this.w$src$Z$e457mb()) {
            this.backgroundAnimation.J();
        }
        super.F();
    }

    @Override
    public double x() {
        return 0.0;
    }

    @Override
    public void I() {
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Color getBackgroundColor() {
        return this.backgroundColor;
    }

    @Override
    public void H() {
        GuiRenderPrimitives.P(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), this.backgroundAnimation.getInterpolatedColor(), 2.0f, (float)this.borderThickness, 1.0f);
        GuiRenderPrimitives.d(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), (Color)new MutableColor(this.backgroundColor).withAlpha((int)(255.0f * this.backgroundAnimation.s())));
    }

    public ColorAnimation getBackgroundAnimation() {
        return this.backgroundAnimation;
    }

    @Override
    public double C() {
        return 0.0;
    }

    public ActionButtonBase(double width, double height, Color backgroundColor, double borderThickness) {
        this.o(width);
        this.Y(height);
        this.backgroundColor = backgroundColor;
        this.backgroundAnimation = new ColorAnimation(0.15, new Color(45, 45, 45), backgroundColor);
        this.borderThickness = borderThickness;
    }

    @Override
    public void onEnable() {
        this.backgroundAnimation.J();
        super.onEnable();
    }


    public void setBackgroundAnimation(ColorAnimation backgroundAnimation) {
        this.backgroundAnimation = backgroundAnimation;
    }

    public ActionButtonBase(double width, double height, Color backgroundColor) {
        this(width, height, backgroundColor, 1.0);
    }

    public /* synthetic */ void renderButton() {
        this.H();
    }
}

