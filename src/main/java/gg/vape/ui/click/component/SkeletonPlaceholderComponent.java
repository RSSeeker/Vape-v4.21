package gg.vape.ui.click.component;

import gg.vape.ui.click.animation.ColorAnimation;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

public class SkeletonPlaceholderComponent
extends GuiComponent {
    private final ColorAnimation pulseAnimation;
    private static boolean legacyState;

    public static boolean isLegacyStateClear() {
        boolean legacyState = SkeletonPlaceholderComponent.getLegacyState();
        return !legacyState;
    }

    public static boolean getLegacyState() {
        return legacyState;
    }

    public SkeletonPlaceholderComponent(double width, double height) {
        this.getClass();
        this.pulseAnimation = new ColorAnimation(0.15 * 4.0, new Color(32, 32, 32, 32), new Color(128, 128, 128, 64));
        this.o(width);
        this.Y(height);
    }

    public static void setLegacyState(boolean legacyState) {
        SkeletonPlaceholderComponent.legacyState = legacyState;
    }


    @Override
    public void H() {
        if (!this.pulseAnimation.l()) {
            if (this.pulseAnimation.N()) {
                this.pulseAnimation.Z();
            } else {
                this.pulseAnimation.c();
            }
        }
        GuiRenderPrimitives.d(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), this.pulseAnimation.getInterpolatedColor());
    }

    static {
        if (!SkeletonPlaceholderComponent.isLegacyStateClear()) {
            SkeletonPlaceholderComponent.setLegacyState(true);
        }
    }
}

