package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MEntityRenderer;
import gg.vape.wrapper.impl.ForgeVersion;

public class MTessellator
extends Mapping {
    private MappingMethod e;
    private MappingMethod Z;
    private MappingMethod w;
    private MappingMethod D;
    private MappingField A;
    private MappingMethod N;
    private final MappingField i;
    private MappingMethod O;
    private MappingField u;

    private void u(Object object) {
        this.Z.invokeVoidNoArgs(object);
    }

    public static void z(MTessellator mTessellator, Object object) {
        mTessellator.c(object);
    }

    public void w(Object object, int n, int n2, int n3, int n4) {
        this.N.invokeVoid(object, n, n2, n3, n4);
    }

    public static Object a(MTessellator mTessellator) {
        return mTessellator.a();
    }

    private void O(Object object, double d, double d2, double d3, double d4, double d5) {
        this.O.invokeVoid(object, d, d2, d3, d4, d5);
    }

    private void C(Object object, int n) {
        this.D.invokeVoid(object, n);
    }

    private void Z(Object object, double d, double d2, double d3) {
        this.w.invokeVoid(object, d, d2, d3);
    }

    private Object Z(Object object) {
        return this.u.getObject(object);
    }

    public boolean K(Object object) {
        return this.A.getBoolean(object);
    }


    private Object a() {
        return this.i.getObject(null);
    }

    public static void v(MTessellator mTessellator, Object object, double d, double d2, double d3) {
        mTessellator.Z(object, d, d2, d3);
    }

    public static Object W(MTessellator mTessellator, Object object) {
        return mTessellator.Z(object);
    }

    public boolean E$src$Z$1p5wubj() {
        return this.A != null && !this.A.hasResolutionFailed();
    }

    public static void k(MTessellator mTessellator, Object object, int n) {
        mTessellator.C(object, n);
    }

    private void c(Object object) {
        this.e.invokeVoidNoArgs(object);
    }

    public static void Y(MTessellator mTessellator, Object object) {
        mTessellator.u(object);
    }

    public MTessellator() {
        this(MEntityRenderer.n());
    }

    private MTessellator(int n) {
        super(MappedClasses.ZX);
        int n2 = n;
        if (ForgeVersion.MC_1_12_2.d()) {
            Class clazz = MappedClasses.ZX;
            boolean bl = true;
            String string = "INSTANCE";
            MTessellator mTessellator = this;
            this.i = mTessellator.registerStaticField(string, bl, clazz);
        } else {
            Class clazz = MappedClasses.ZX;
            boolean bl = true;
            String string = "instance";
            MTessellator mTessellator = this;
            this.i = mTessellator.registerStaticField(string, bl, clazz);
        }
        if (ForgeVersion.MC_1_7_10.Y()) {
            if (ForgeVersion.MC_1_21_0.H().y()) {
                if (ForgeVersion.MC_1_16_5.d()) {
                    Class clazz = MappedClasses.lX;
                    boolean bl = true;
                    String string = "buffer";
                    MTessellator mTessellator = this;
                    this.u = mTessellator.J(string, bl, clazz);
                } else {
                    Class clazz = MappedClasses.lX;
                    boolean bl = true;
                    String string = ForgeVersion.c() >= 23 ? "buffer" : "worldRenderer";
                    MTessellator mTessellator = this;
                    this.u = mTessellator.J(string, bl, clazz);
                }
                Class[] classArray = new Class[]{};
                Class<Void> clazz = Void.TYPE;
                boolean bl = true;
                String string = "draw";
                MTessellator mTessellator = this;
                this.e = mTessellator.Y(string, bl, clazz, classArray);
            }
        } else {
            Class<Boolean> clazz = Boolean.TYPE;
            boolean bl = true;
            boolean bl2 = true;
            String string = "renderingChunk";
            MTessellator mTessellator = this;
            this.A = mTessellator.registerInstanceFieldWithSecondaryFlag(string, bl2, bl, clazz);
            Class[] classArray = new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE};
            Class<Void> clazz2 = Void.TYPE;
            boolean bl3 = true;
            String string2 = "setColorRGBA";
            MTessellator mTessellator2 = this;
            this.N = this.Y(string2, bl3, clazz2, classArray);
            Class[] classArray2 = new Class[]{};
            Class<Void> clazz3 = Void.TYPE;
            boolean bl4 = true;
            String string3 = "startDrawingQuads";
            MTessellator mTessellator3 = this;
            this.Z = this.Y(string3, bl4, clazz3, classArray2);
            Class[] classArray3 = new Class[]{Double.TYPE, Double.TYPE, Double.TYPE};
            Class<Void> clazz4 = Void.TYPE;
            boolean bl5 = true;
            String string4 = "addVertex";
            MTessellator mTessellator4 = this;
            this.w = this.Y(string4, bl5, clazz4, classArray3);
            Class[] classArray4 = new Class[]{};
            Class<Integer> clazz5 = Integer.TYPE;
            boolean bl6 = true;
            String string5 = "draw";
            MTessellator mTessellator5 = this;
            this.e = this.Y(string5, bl6, clazz5, classArray4);
            Class[] classArray5 = new Class[]{Integer.TYPE};
            Class<Void> clazz6 = Void.TYPE;
            boolean bl7 = true;
            String string6 = "startDrawing";
            MTessellator mTessellator6 = this;
            this.D = this.Y(string6, bl7, clazz6, classArray5);
            Class[] classArray6 = new Class[]{Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE};
            Class<Void> clazz7 = Void.TYPE;
            boolean bl8 = true;
            String string7 = "addVertexWithUV";
            MTessellator mTessellator7 = this;
            this.O = this.Y(string7, bl8, clazz7, classArray6);
        }
    }

    public static void E(MTessellator mTessellator, Object object, double d, double d2, double d3, double d4, double d5) {
        mTessellator.O(object, d, d2, d3, d4, d5);
    }
}

