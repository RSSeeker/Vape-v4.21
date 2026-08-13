package gg.vape.utils.render.glu;

import org.lwjgl.opengl.GL11;

public class GluQuadric {
    public static final int DRAW_POINT = 100010;
    public static final int DRAW_LINE = 100011;
    public static final int DRAW_FILL = 100012;
    public static final int DRAW_SILHOUETTE = 100013;
    public static final int ORIENTATION_OUTSIDE = 100020;
    public static final int ORIENTATION_INSIDE = 100021;
    public static final int NORMAL_SMOOTH = 100000;
    public static final int NORMAL_FLAT = 100001;
    public static final int NORMAL_NONE = 100002;

    protected boolean textureCoordinatesEnabled;
    protected int drawStyle;
    protected int normalMode;
    protected int orientation;

    public int getDrawStyle() {
        return this.drawStyle;
    }

    protected void emitNormalizedNormal(float x, float y, float z) {
        float length = (float)Math.sqrt(x * x + y * y + z * z);
        if (length > 1.0E-5f) {
            x /= length;
            y /= length;
            z /= length;
        }
        GL11.glNormal3f((float)x, (float)y, (float)z);
    }

    public void setDrawStyle(int drawStyle) {
        this.drawStyle = drawStyle;
    }

    public GluQuadric() {
        this.drawStyle = DRAW_FILL;
        this.orientation = ORIENTATION_OUTSIDE;
        this.textureCoordinatesEnabled = false;
        this.normalMode = NORMAL_SMOOTH;
    }

    public void setNormalMode(int normalMode) {
        this.normalMode = normalMode;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public void setTextureCoordinatesEnabled(boolean enabled) {
        this.textureCoordinatesEnabled = enabled;
    }


    protected float cos(float angle) {
        return (float)Math.cos(angle);
    }


    public boolean isTextureCoordinatesEnabled() {
        return this.textureCoordinatesEnabled;
    }

    public int getOrientation() {
        return this.orientation;
    }

    public int getNormalMode() {
        return this.normalMode;
    }

    protected float sin(float angle) {
        return (float)Math.sin(angle);
    }

    protected void emitTextureCoordinate(float u, float v) {
        if (this.textureCoordinatesEnabled) {
            GL11.glTexCoord2f((float)u, (float)v);
        }
    }
}
