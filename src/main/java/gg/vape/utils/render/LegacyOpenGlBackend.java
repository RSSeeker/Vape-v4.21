package gg.vape.utils.render;

import gg.vape.utils.render.OpenGlBackend;
import org.lwjgl.opengl.GL11;

public class LegacyOpenGlBackend
implements OpenGlBackend {
    @Override
    public void rotate(float angle, float x, float y, float z) {
        GL11.glRotatef((float)angle, (float)x, (float)y, (float)z);
    }

    @Override
    public void setNormal(double x, double y, double z) {
        GL11.glNormal3d((double)x, (double)y, (double)z);
    }

    @Override
    public void endPrimitive() {
        GL11.glEnd();
    }

    @Override
    public void setAlphaFunction(int function, float reference) {
        GL11.glAlphaFunc((int)function, (float)reference);
    }

    @Override
    public void setDepthMask(boolean enabled) {
        GL11.glDepthMask((boolean)enabled);
    }

    @Override
    public void disableCapabilityState(int capability) {
        GL11.glDisable((int)capability);
    }

    @Override
    public void loadIdentity() {
        GL11.glLoadIdentity();
    }

    @Override
    public void enableCapability(int capability) {
        GL11.glEnable((int)capability);
    }

    @Override
    public void popMatrix() {
        GL11.glPopMatrix();
    }

    @Override
    public void addVertex(double x, double y, double z) {
        GL11.glVertex3d((double)x, (double)y, (double)z);
    }

    @Override
    public float getFloatState(int parameter) {
        return GL11.glGetFloat((int)parameter);
    }

    @Override
    public void translate(float x, float y, float z) {
        GL11.glTranslatef((float)x, (float)y, (float)z);
    }

    @Override
    public boolean isCapabilityEnabled(int capability) {
        return GL11.glIsEnabled((int)capability);
    }

    @Override
    public void setColor(float red, float green, float blue, float alpha) {
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
    }

    @Override
    public int getIntegerState(int parameter) {
        return GL11.glGetInteger((int)parameter);
    }

    @Override
    public void setColor(double red, float green, float blue) {
        GL11.glColor3d((double)red, (double)green, (double)blue);
    }

    @Override
    public void setColor(float red, float green, float blue) {
        GL11.glColor3f((float)red, (float)green, (float)blue);
    }

    @Override
    public void rotate(double angle, double x, double y, double z) {
        GL11.glRotated((double)angle, (double)x, (double)y, (double)z);
    }

    @Override
    public void setColor(double red, double green, double blue, double alpha) {
        GL11.glColor4d((double)red, (double)green, (double)blue, (double)alpha);
    }

    @Override
    public void scale(float x, float y, float z) {
        GL11.glScalef((float)x, (float)y, (float)z);
    }

    @Override
    public void scale(double x, double y, double z) {
        GL11.glScaled((double)x, (double)y, (double)z);
    }

    @Override
    public void setScissor(int x, int y, int width, int height) {
        GL11.glScissor((int)x, (int)y, (int)width, (int)height);
    }

    @Override
    public void setLineWidth(float width) {
        GL11.glLineWidth((float)width);
    }

    @Override
    public void pushMatrix() {
        GL11.glPushMatrix();
    }

    @Override
    public void setNormal(float x, float y, float z) {
        GL11.glNormal3f((float)x, (float)y, (float)z);
    }

    @Override
    public void translate(double x, double y, double z) {
        GL11.glTranslated((double)x, (double)y, (double)z);
    }

    @Override
    public void beginPrimitive(int mode) {
        GL11.glBegin((int)mode);
    }
}
