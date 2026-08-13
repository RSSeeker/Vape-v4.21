package gg.vape.utils;

public class MutableFloatTriple {
    public float x;
    public float y;
    public float z;

    public void setZ(float z) {
        this.z = z;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public MutableFloatTriple(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getZ() {
        return this.z;
    }

    public void setY(float y) {
        this.y = y;
    }
}
