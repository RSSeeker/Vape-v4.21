package gg.vape.utils.render;

import gg.vape.wrapper.impl.PotionEffect;

public interface PotionEffectIconRenderBackend {
    public void render(float x, float y, int width, int height, float opacity);

    default public void renderQueued(float x, float y, int width, int height, float opacity, boolean worldSpace) {
        this.render(x, y, width, height, opacity);
    }

    public void capture(PotionEffect effect);

    public void dispose();
}
