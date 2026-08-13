package gg.vape.utils.render;

import gg.vape.wrapper.impl.ItemStack;

public interface ItemIconRenderBackend {
    public void dispose();

    default public void renderQueued(float x, float y, int width, int height, float opacity, boolean worldSpace) {
        this.render(x, y, width, height, opacity);
    }

    public void capture(ItemStack itemStack, float scale);

    public void render(float x, float y, int width, int height, float opacity);
}
