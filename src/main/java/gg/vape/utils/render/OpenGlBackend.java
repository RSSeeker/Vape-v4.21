package gg.vape.utils.render;

public interface OpenGlBackend {
    public void pushMatrix();

    public void translate(float x, float y, float z);

    public void rotate(double angle, double x, double y, double z);

    public void setColor(double red, float green, float blue);

    public void setScissor(int x, int y, int width, int height);

    public void loadIdentity();

    public float getFloatState(int parameter);

    public void popMatrix();

    public void setLineWidth(float width);

    public void setAlphaFunction(int function, float reference);

    public void scale(float x, float y, float z);

    public void rotate(float angle, float x, float y, float z);

    public void setColor(float red, float green, float blue);

    public void setNormal(float x, float y, float z);

    public void enableCapability(int capability);

    public int getIntegerState(int parameter);

    public void translate(double x, double y, double z);

    public void endPrimitive();

    public void beginPrimitive(int mode);

    public void disableCapabilityState(int capability);

    public boolean isCapabilityEnabled(int capability);

    public void addVertex(double x, double y, double z);

    public void setNormal(double x, double y, double z);

    public void scale(double x, double y, double z);

    public void setColor(double red, double green, double blue, double alpha);

    public void setColor(float red, float green, float blue, float alpha);

    public void setDepthMask(boolean enabled);

    default public /* synthetic */ float getFloat(int parameter) {
        return this.getFloatState(parameter);
    }

    default public /* synthetic */ void disableCapability(int capability) {
        this.disableCapabilityState(capability);
    }
}
