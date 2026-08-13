package gg.vape.ui.click.frame.impl.profile;

import gg.vape.ui.click.animation.DoubleAnimation;
import java.awt.Color;

public class ProfileCardActionState {

    public static Color t(Color color, DoubleAnimation doubleAnimation, boolean bl) {
        if (color == null) {
            return null;
        }
        double d = Math.min(1.0, Math.max(0.0, doubleAnimation.getInterpolatedValue()));
        if (bl || d > 0.0) {
            float f = (float)(1.0 - 0.8 * d);
            int n = Math.max(0, Math.round((float)color.getAlpha() * f));
            return new Color(color.getRed(), color.getGreen(), color.getBlue(), n);
        }
        return color;
    }
}

