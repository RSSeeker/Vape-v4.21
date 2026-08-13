package gg.vape.friend.ui;

import gg.vape.value.BooleanValue;
import gg.vape.value.NumberValue;

public class OnlinePlayerPreviewSettings {
    public final NumberValue fieldOfView;
    public final BooleanValue levelView;
    public final NumberValue scale = NumberValue.create(this, "Size", "#.#", "", 0.5, 1.0, 2.0);
    public final NumberValue framesPerSecond = NumberValue.create(this, "FPS", "#", "", 1.0, 30.0, 60.0);

    public OnlinePlayerPreviewSettings() {
        this.fieldOfView = NumberValue.create(this, "FOV", "#", "", 50.0, 90.0, 150.0);
        this.levelView = BooleanValue.create(this, "Level view", true);
    }
}
