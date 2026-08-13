package gg.vape.utils.render;

public class TextureAtlasRegion {
    public final int width;
    public final int height;
    public float minU;
    public float maxV;
    public final int y;
    public final int x;
    public float maxU;
    public float minV;

    public TextureAtlasRegion(int x, int y, int width, int height, float minU, float minV, float maxU, float maxV) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.minU = minU;
        this.minV = minV;
        this.maxU = maxU;
        this.maxV = maxV;
    }
}
