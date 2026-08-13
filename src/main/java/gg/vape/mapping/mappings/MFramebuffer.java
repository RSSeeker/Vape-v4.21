package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MShaderGroup;
import gg.vape.wrapper.impl.ForgeVersion;

public class MFramebuffer
extends Mapping {
    private MappingField l;
    private final MappingMethod r;
    private final MappingMethod f;
    private final MappingMethod P;
    private final MappingMethod F;
    private final MappingMethod H;
    private final MappingMethod S;
    private final MappingMethod V;
    private final MappingMethod W;

    public static void D(MFramebuffer mFramebuffer, Object object) {
        mFramebuffer.x(object);
    }

    public static void bindFramebuffer(MFramebuffer mFramebuffer, Object object, boolean bl) {
        mFramebuffer.bindFramebuffer(object, bl);
    }

    public static void G(MFramebuffer mFramebuffer, Object object, int n, int n2) {
        mFramebuffer.E(object, n, n2);
    }

    public static void l(MFramebuffer mFramebuffer, Object object) {
        mFramebuffer.A(object);
    }

    public static void e(MFramebuffer mFramebuffer, Object object) {
        mFramebuffer.I(object);
    }

    private void bindFramebuffer(Object object, boolean bl) {
        if (this.r == null) {
            return;
        }
        this.r.invokeVoid(object, bl);
    }

    public static void m(MFramebuffer mFramebuffer, Object object) {
        mFramebuffer.Q(object);
    }


    private void Q(Object object) {
        if (this.P == null) {
            return;
        }
        this.P.invokeVoidNoArgs(object);
    }

    private void k(Object object, int n, int n2, boolean bl) {
        if (this.f == null) {
            return;
        }
        if (ForgeVersion.MC_1_21_0.d()) {
            this.f.invokeVoid(object, n, n2);
            return;
        }
        this.f.invokeVoid(object, n, n2, bl);
    }

    public static int L(MFramebuffer mFramebuffer, Object object) {
        return mFramebuffer.r(object);
    }

    public MFramebuffer() {
        this(MShaderGroup.V());
    }

    private MFramebuffer(String[] stringArray) {
        super(MappedClasses.ll);
        String[] stringArray2 = stringArray;
        if (ForgeVersion.MC_1_21_4.d()) {
            this.S = null;
            this.V = null;
            this.f = null;
            this.r = null;
            this.P = null;
            this.F = null;
            this.W = null;
            this.H = null;
        } else if (ForgeVersion.MC_1_16_5.d()) {
            if (ForgeVersion.MC_1_17.d()) {
                Class[] classArray = new Class[]{Integer.TYPE, Integer.TYPE};
                Class<Void> clazz = Void.TYPE;
                boolean bl = false;
                String string = "<init>";
                Class clazz2 = MappedClasses.zA;
                MFramebuffer mFramebuffer = this;
                this.S = mFramebuffer.registerInstanceMethodForOwner(clazz2, string, bl, clazz, classArray);
            } else {
                Class[] classArray = new Class[]{Integer.TYPE, Integer.TYPE, Boolean.TYPE, Boolean.TYPE};
                Class<Void> clazz = Void.TYPE;
                boolean bl = false;
                String string = "<init>";
                MFramebuffer mFramebuffer = this;
                this.S = mFramebuffer.Y(string, bl, clazz, classArray);
            }
            Class[] classArray = new Class[]{Integer.TYPE, Integer.TYPE};
            Class<Void> clazz = Void.TYPE;
            boolean bl = true;
            String string = "createBindFramebuffers";
            Class clazz3 = MappedClasses.Fo;
            MFramebuffer mFramebuffer = this;
            this.V = mFramebuffer.registerInstanceMethodForOwner(clazz3, string, bl, clazz, classArray);
            Class[] classArray2 = new Class[]{Integer.TYPE, Integer.TYPE, Boolean.TYPE};
            Class<Void> clazz4 = Void.TYPE;
            boolean bl2 = true;
            String string2 = "createBuffers";
            MFramebuffer mFramebuffer2 = this;
            this.f = this.Y(string2, bl2, clazz4, classArray2);
            Class[] classArray3 = new Class[]{Boolean.TYPE};
            Class<Void> clazz5 = Void.TYPE;
            boolean bl3 = true;
            String string3 = "bindFramebuffer";
            MFramebuffer mFramebuffer3 = this;
            this.r = this.Y(string3, bl3, clazz5, classArray3);
            Class[] classArray4 = new Class[]{};
            Class<Void> clazz6 = Void.TYPE;
            boolean bl4 = true;
            String string4 = "unbindFramebuffer";
            MFramebuffer mFramebuffer4 = this;
            this.P = this.Y(string4, bl4, clazz6, classArray4);
            Class[] classArray5 = new Class[]{};
            Class<Void> clazz7 = Void.TYPE;
            boolean bl5 = true;
            String string5 = "bindFramebufferTexture";
            MFramebuffer mFramebuffer5 = this;
            this.F = this.Y(string5, bl5, clazz7, classArray5);
            Class[] classArray6 = new Class[]{};
            Class<Void> clazz8 = Void.TYPE;
            boolean bl6 = true;
            String string6 = "unbindFramebufferTexture";
            MFramebuffer mFramebuffer6 = this;
            this.W = this.Y(string6, bl6, clazz8, classArray6);
            Class[] classArray7 = new Class[]{};
            Class<Void> clazz9 = Void.TYPE;
            boolean bl7 = true;
            String string7 = "deleteFramebuffer";
            MFramebuffer mFramebuffer7 = this;
            this.H = this.Y(string7, bl7, clazz9, classArray7);
        } else {
            Class[] classArray = new Class[]{Integer.TYPE, Integer.TYPE, Boolean.TYPE};
            Class<Void> clazz = Void.TYPE;
            boolean bl = false;
            String string = "<init>";
            MFramebuffer mFramebuffer = this;
            this.S = mFramebuffer.Y(string, bl, clazz, classArray);
            Class[] classArray8 = new Class[]{Integer.TYPE, Integer.TYPE};
            Class<Void> clazz10 = Void.TYPE;
            boolean bl8 = true;
            String string8 = "createBindFramebuffer";
            MFramebuffer mFramebuffer8 = this;
            this.V = this.Y(string8, bl8, clazz10, classArray8);
            Class[] classArray9 = new Class[]{Integer.TYPE, Integer.TYPE};
            Class<Void> clazz11 = Void.TYPE;
            boolean bl9 = true;
            String string9 = "createFramebuffer";
            MFramebuffer mFramebuffer9 = this;
            this.f = this.Y(string9, bl9, clazz11, classArray9);
            Class[] classArray10 = new Class[]{Boolean.TYPE};
            Class<Void> clazz12 = Void.TYPE;
            boolean bl10 = true;
            String string10 = "bindFramebuffer";
            MFramebuffer mFramebuffer10 = this;
            this.r = this.Y(string10, bl10, clazz12, classArray10);
            Class[] classArray11 = new Class[]{};
            Class<Void> clazz13 = Void.TYPE;
            boolean bl11 = true;
            String string11 = "unbindFramebuffer";
            MFramebuffer mFramebuffer11 = this;
            this.P = this.Y(string11, bl11, clazz13, classArray11);
            Class[] classArray12 = new Class[]{};
            Class<Void> clazz14 = Void.TYPE;
            boolean bl12 = true;
            String string12 = "bindFramebufferTexture";
            MFramebuffer mFramebuffer12 = this;
            this.F = this.Y(string12, bl12, clazz14, classArray12);
            Class[] classArray13 = new Class[]{};
            Class<Void> clazz15 = Void.TYPE;
            boolean bl13 = true;
            String string13 = "unbindFramebufferTexture";
            MFramebuffer mFramebuffer13 = this;
            this.W = this.Y(string13, bl13, clazz15, classArray13);
            Class[] classArray14 = new Class[]{};
            Class<Void> clazz16 = Void.TYPE;
            boolean bl14 = true;
            String string14 = "deleteFramebuffer";
            MFramebuffer mFramebuffer14 = this;
            this.H = this.Y(string14, bl14, clazz16, classArray14);
        }
        if (ForgeVersion.MC_1_21_6.v()) {
            Class<Integer> clazz = Integer.TYPE;
            boolean bl = true;
            String string = "depthBuffer";
            MFramebuffer mFramebuffer = this;
            this.l = mFramebuffer.J(string, bl, clazz); 
        }
    }

    private void E(Object object, int n, int n2) {
        if (this.f == null) {
            return;
        }
        this.f.invokeVoid(object, n, n2);
    }

    public static Object create(MFramebuffer mFramebuffer, int n, int n2, boolean bl) {
        return mFramebuffer.create(n, n2, bl);
    }

    private void x(Object object) {
        if (this.W == null) {
            return;
        }
        this.W.invokeVoidNoArgs(object);
    }

    private Object create(int n, int n2, boolean bl) {
        if (this.S == null) {
            return null;
        }
        if (ForgeVersion.MC_1_17.d()) {
            return this.S.newInstance(n, n2);
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            return this.S.newInstance(n, n2, bl, false);
        }
        return this.S.newInstance(n, n2, bl);
    }

    private int r(Object object) {
        return this.l.getInt(object);
    }

    private void A(Object object) {
        if (this.F == null) {
            return;
        }
        this.F.invokeVoidNoArgs(object);
    }

    private void f(Object object, int n, int n2) {
        if (this.V == null) {
            return;
        }
        this.V.invokeVoid(object, n, n2);
    }

    public static void w(MFramebuffer mFramebuffer, Object object, int n, int n2, boolean bl) {
        mFramebuffer.k(object, n, n2, bl);
    }

    public static void depthBuffer(MFramebuffer mFramebuffer, Object object, int n) {
        mFramebuffer.depthBuffer(object, n);
    }

    private void I(Object object) {
        if (this.H == null) {
            return;
        }
        this.H.invokeVoidNoArgs(object);
    }

    public static void f(MFramebuffer mFramebuffer, Object object, int n, int n2) {
        mFramebuffer.f(object, n, n2);
    }

    private void depthBuffer(Object object, int n) {
        this.l.setInt(object, n);
    }
}

