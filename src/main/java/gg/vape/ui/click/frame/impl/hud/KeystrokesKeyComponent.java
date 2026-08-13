package gg.vape.ui.click.frame.impl.hud;

import gg.vape.ui.click.animation.ColorAnimation;
import gg.vape.ui.click.frame.impl.hud.KeystrokesHudFrame;
import gg.vape.wrapper.impl.KeyBinding;
import java.awt.Color;

class KeystrokesKeyComponent {
    boolean released;
    public final ColorAnimation textColorAnimation;
    final KeyBinding keyBinding;
    public final ColorAnimation backgroundColorAnimation;
    boolean pressed;

    KeystrokesKeyComponent(KeystrokesHudFrame keystrokesHudFrame, KeyBinding keyBinding) {
        this.backgroundColorAnimation = new ColorAnimation(0.05,
                new Color(20, 20, 20, 180), new Color(255, 255, 255, 255));
        this.textColorAnimation = new ColorAnimation(0.05,
                new Color(255, 255, 255, 255), new Color(20, 20, 20, 255));
        this.released = true;
        this.keyBinding = keyBinding;
    }

    public ColorAnimation getBackgroundColorAnimation() {
        return this.backgroundColorAnimation;
    }

    public void press() {
        this.backgroundColorAnimation.J();
        this.textColorAnimation.J();
        this.released = false;
    }

    public ColorAnimation getTextColorAnimation() {
        return this.textColorAnimation;
    }

    public void release() {
        this.backgroundColorAnimation.Z();
        this.textColorAnimation.Z();
        this.released = true;
    }

    public boolean isReleased() {
        return this.released;
    }
}
