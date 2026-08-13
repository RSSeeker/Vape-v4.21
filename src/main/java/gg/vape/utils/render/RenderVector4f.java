package gg.vape.utils.render;

public class RenderVector4f {
    public float y;
    public float x;
    public float w;
    public float z;

    public RenderVector4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public RenderVector4f(double x, double y, double z, double w) {
        this.x = (float)x;
        this.y = (float)y;
        this.z = (float)z;
        this.w = (float)w;
    }

    public String toString() {
        return "Vec4{x=" + this.x + ", y=" + this.y + ", z=" + this.z + ", w=" + this.w + '}';
    }
}
