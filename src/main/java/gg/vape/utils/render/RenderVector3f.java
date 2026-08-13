package gg.vape.utils.render;

public class RenderVector3f {
    public float y;
    public float z;
    public float x;

    public RenderVector3f cross(RenderVector3f other) {
        float crossX = this.y * other.z - this.z * other.y;
        float crossY = this.z * other.x - this.x * other.z;
        float crossZ = this.x * other.y - this.y * other.x;
        return new RenderVector3f(crossX, crossY, crossZ);
    }

    public RenderVector3f subtract(RenderVector3f other) {
        return new RenderVector3f(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    public RenderVector3f negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        return this;
    }

    public float dot(RenderVector3f other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    public RenderVector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String toString() {
        return "Vec3{x=" + this.x + ", y=" + this.y + ", z=" + this.z + '}';
    }

    public RenderVector3f normalize() {
        float length = (float)Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
        return new RenderVector3f(this.x / length, this.y / length, this.z / length);
    }
}
