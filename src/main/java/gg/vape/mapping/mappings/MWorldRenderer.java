package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MEntityRenderer;
import gg.vape.wrapper.impl.ForgeVersion;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class MWorldRenderer
extends Mapping {
    private MappingField n;
    public MappingField A;
    public MappingMethod b;
    private MappingField e;
    private MappingMethod T;
    private MappingMethod F;
    public MappingMethod j;
    private MappingMethod l;

    private int K(Object object, int n) {
        return this.j.invokeInt(object, n);
    }

    public static void X(MWorldRenderer mWorldRenderer, Object object, boolean bl) {
        mWorldRenderer.x(object, bl);
    }

    public static int G(MWorldRenderer mWorldRenderer, Object object, int n) {
        return mWorldRenderer.K(object, n);
    }

    public static Object t(MWorldRenderer mWorldRenderer, Object object) {
        return mWorldRenderer.x(object);
    }

    private Object e(Object object) {
        return this.A.getObject(object);
    }


    private void g(Object object, int n) {
        this.F.invokeVoid(object, n);
    }

    private Object x(Object object) {
        return this.e.getObject(object);
    }

    private void E(Object object, float f, float f2, float f3, int n) {
        this.b.invokeVoid(object, Float.valueOf(f), Float.valueOf(f2), Float.valueOf(f3), n);
    }

    public static Object v(MWorldRenderer mWorldRenderer, Object object) {
        return mWorldRenderer.e(object);
    }

    public MWorldRenderer() {
        this(MEntityRenderer.n());
    }

    private MWorldRenderer(int n) {
        super(MappedClasses.lX);
        if (n != 0) {
            if (ForgeVersion.MC_1_7_10.Y()) {
                // empty if block
            }
            if (ForgeVersion.MC_1_16_5.d()) {
                Class<ByteBuffer> clazz = ByteBuffer.class;
                boolean bl = true;
                String string = "byteBuffer";
                MWorldRenderer mWorldRenderer = this;
                this.e = mWorldRenderer.J(string, bl, clazz);
                Class<Boolean> clazz2 = Boolean.TYPE;
                boolean bl2 = true;
                String string2 = "isDrawing";
                MWorldRenderer mWorldRenderer2 = this;
                this.n = this.J(string2, bl2, clazz2);
            } else {
                Class<IntBuffer> clazz = IntBuffer.class;
                boolean bl = true;
                String string = "rawIntBuffer";
                MWorldRenderer mWorldRenderer = this;
                this.A = mWorldRenderer.J(string, bl, clazz);
                Class[] classArray = new Class[]{Float.TYPE, Float.TYPE, Float.TYPE, Integer.TYPE};
                Class<Void> clazz3 = Void.TYPE;
                boolean bl3 = true;
                String string3 = "putColorMultiplier";
                MWorldRenderer mWorldRenderer3 = this;
                this.b = this.Y(string3, bl3, clazz3, classArray);
                Class[] classArray2 = new Class[]{Integer.TYPE};
                Class<Integer> clazz4 = Integer.TYPE;
                boolean bl4 = true;
                String string4 = "getColorIndex";
                MWorldRenderer mWorldRenderer4 = this;
                this.j = this.Y(string4, bl4, clazz4, classArray2);
                Class[] classArray3 = new Class[]{Integer.TYPE};
                Class<Void> clazz5 = Void.TYPE;
                boolean bl5 = true;
                String string5 = "putColor4";
                MWorldRenderer mWorldRenderer5 = this;
                this.F = this.Y(string5, bl5, clazz5, classArray3);
            }
            return;
        }
        if (ForgeVersion.MC_1_7_10.Y()) {
            // empty if block
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            Class<ByteBuffer> clazz = ByteBuffer.class;
            boolean bl = true;
            String string = "byteBuffer";
            MWorldRenderer mWorldRenderer = this;
            this.e = mWorldRenderer.J(string, bl, clazz);
            Class<Boolean> clazz6 = Boolean.TYPE;
            boolean bl6 = true;
            String string6 = "isDrawing";
            MWorldRenderer mWorldRenderer6 = this;
            this.n = this.J(string6, bl6, clazz6);
        }
        Class<IntBuffer> clazz = IntBuffer.class;
        boolean bl = true;
        String string = "rawIntBuffer";
        MWorldRenderer mWorldRenderer = this;
        this.A = mWorldRenderer.J(string, bl, clazz);
        Class[] classArray = new Class[]{Float.TYPE, Float.TYPE, Float.TYPE, Integer.TYPE};
        Class<Void> clazz7 = Void.TYPE;
        boolean bl7 = true;
        String string7 = "putColorMultiplier";
        MWorldRenderer mWorldRenderer7 = this;
        this.b = this.Y(string7, bl7, clazz7, classArray);
        Class[] classArray4 = new Class[]{Integer.TYPE};
        Class<Integer> clazz8 = Integer.TYPE;
        boolean bl8 = true;
        String string8 = "getColorIndex";
        MWorldRenderer mWorldRenderer8 = this;
        this.j = this.Y(string8, bl8, clazz8, classArray4);
        Class[] classArray5 = new Class[]{Integer.TYPE};
        Class<Void> clazz9 = Void.TYPE;
        boolean bl9 = true;
        String string9 = "putColor4";
        MWorldRenderer mWorldRenderer9 = this;
        this.F = this.Y(string9, bl9, clazz9, classArray5);
    }

    private void x(Object object, boolean bl) {
        this.n.setBoolean(object, bl);
    }
}

