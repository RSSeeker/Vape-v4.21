package gg.vape.utils.render;

import gg.vape.utils.datas.FloatPair;
import gg.vape.utils.render.RenderMatrix4f;
import gg.vape.utils.render.RenderVector3f;
import gg.vape.utils.render.RenderVector4f;

public class WorldToScreenProjector {

    public static FloatPair projectToScreen(RenderVector3f worldPosition, RenderMatrix4f modelMatrix, RenderMatrix4f viewMatrix, RenderMatrix4f projectionMatrix, int viewportWidth, int viewportHeight) {
        RenderMatrix4f modelViewMatrix = viewMatrix.multiply(modelMatrix);
        RenderMatrix4f clipMatrix = projectionMatrix.multiply(modelViewMatrix);
        RenderVector4f clipPosition = new RenderVector4f(worldPosition.x, worldPosition.y, worldPosition.z, 1.0f);
        clipMatrix.transform(clipPosition, clipPosition);
        if (clipPosition.w <= 0.01f) {
            return null;
        }
        float normalizedX = clipPosition.x / clipPosition.w;
        float normalizedY = clipPosition.y / clipPosition.w;
        float screenX = (normalizedX * 0.5f + 0.5f) * (float)viewportWidth;
        float screenY = (1.0f - (normalizedY * 0.5f + 0.5f)) * (float)viewportHeight;
        return new FloatPair(screenX, screenY);
    }
}

