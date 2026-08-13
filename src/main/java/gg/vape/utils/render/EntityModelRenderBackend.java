package gg.vape.utils.render;

import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.ResourceLocation;
import java.awt.Color;

public interface EntityModelRenderBackend {
    public void captureEntity(EntityLivingBase entity);

    public void render(float x, float y, int width, int height, Color color, float cornerRadius);

    public void captureTexture(ResourceLocation texture);

    public void dispose();
}
