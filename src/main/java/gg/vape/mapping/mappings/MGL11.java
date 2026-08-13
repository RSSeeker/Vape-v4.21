package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MGlStateManagerTexGenState;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class MGL11
extends Mapping {
    private MappingMethod Q;
    private MappingMethod H;
    public MappingMethod S;
    private MappingMethod j;

    private void W(int n, int n2, int n3, FloatBuffer floatBuffer) {
        if (!GuiRenderPrimitives.V()) {
            this.S.invokeVoid(null, n, n3, floatBuffer);
        } else {
            this.S.invokeVoid(null, n, n2, n3, floatBuffer);
        }
    }

    public static void H(MGL11 mGL11, int n, int n2, int n3, FloatBuffer floatBuffer) {
        mGL11.W(n, n2, n3, floatBuffer);
    }

    private void m(int n, IntBuffer intBuffer) {
        this.j.invokeVoid(null, n, intBuffer);
    }

    public static void Y(MGL11 mGL11, int n, FloatBuffer floatBuffer) {
        mGL11.G(n, floatBuffer);
    }

    public static void X(MGL11 mGL11, int n, IntBuffer intBuffer) {
        mGL11.m(n, intBuffer);
    }

    public static void J(MGL11 mGL11, FloatBuffer floatBuffer) {
        mGL11.s(floatBuffer);
    }

    private void G(int n, FloatBuffer floatBuffer) {
        this.Q.invokeVoid(null, n, floatBuffer);
    }

    public MGL11() {
        this(MGlStateManagerTexGenState.F());
    }

    private MGL11(boolean bl) {
        super(MappedClasses.J);
        if (bl) {
            Class[] classArray = new Class[]{Integer.TYPE, FloatBuffer.class};
            Class<Void> clazz = Void.TYPE;
            boolean bl2 = false;
            String string = "glGetFloatv";
            MGL11 mGL11 = this;
            this.Q = mGL11.registerStaticMethod(string, bl2, clazz, classArray);
            return;
        }
        if (!GuiRenderPrimitives.V()) {
            Class[] classArray = new Class[]{Integer.TYPE, IntBuffer.class};
            Class<Void> clazz = Void.TYPE;
            boolean bl3 = false;
            String string = "glGetInteger";
            MGL11 mGL11 = this;
            this.j = mGL11.registerStaticMethod(string, bl3, clazz, classArray);
            Class[] classArray2 = new Class[]{Integer.TYPE, Integer.TYPE, FloatBuffer.class};
            Class<Void> clazz2 = Void.TYPE;
            boolean bl4 = false;
            String string2 = "glVertexPointer";
            MGL11 mGL112 = this;
            this.S = this.registerStaticMethod(string2, bl4, clazz2, classArray2);
            Class[] classArray3 = new Class[]{FloatBuffer.class};
            Class<Void> clazz3 = Void.TYPE;
            boolean bl5 = false;
            String string3 = "glMultMatrix";
            MGL11 mGL113 = this;
            this.H = this.registerStaticMethod(string3, bl5, clazz3, classArray3);
            Class[] classArray4 = new Class[]{Integer.TYPE, FloatBuffer.class};
            Class<Void> clazz4 = Void.TYPE;
            boolean bl6 = false;
            String string4 = "glGetFloat";
            MGL11 mGL114 = this;
            this.Q = this.registerStaticMethod(string4, bl6, clazz4, classArray4);
        } else {
            Class[] classArray = new Class[]{Integer.TYPE, IntBuffer.class};
            Class<Void> clazz = Void.TYPE;
            boolean bl7 = false;
            String string = "glGetIntegerv";
            MGL11 mGL11 = this;
            this.j = mGL11.registerStaticMethod(string, bl7, clazz, classArray);
            Class[] classArray5 = new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE, FloatBuffer.class};
            Class<Void> clazz5 = Void.TYPE;
            boolean bl8 = false;
            String string5 = "glVertexPointer";
            MGL11 mGL115 = this;
            this.S = this.registerStaticMethod(string5, bl8, clazz5, classArray5);
            Class[] classArray6 = new Class[]{FloatBuffer.class};
            Class<Void> clazz6 = Void.TYPE;
            boolean bl9 = false;
            String string6 = "glMultMatrixf";
            MGL11 mGL116 = this;
            this.H = this.registerStaticMethod(string6, bl9, clazz6, classArray6);
            Class[] classArray7 = new Class[]{Integer.TYPE, FloatBuffer.class};
            Class<Void> clazz7 = Void.TYPE;
            boolean bl10 = false;
            String string7 = "glGetFloatv";
            MGL11 mGL117 = this;
            this.Q = this.registerStaticMethod(string7, bl10, clazz7, classArray7);
        }
    }

    private void s(FloatBuffer floatBuffer) {
        this.H.invokeVoid(null, floatBuffer);
    }

}

