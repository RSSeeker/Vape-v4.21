package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MRenderItem
extends Mapping {
    private MappingMethod r;
    private MappingMethod o;
    private MappingMethod s;
    private MappingField Q;
    private MappingMethod F;
    private MappingMethod H;
    private MappingMethod d;
    private MappingMethod k;
    private MappingMethod n;
    private MappingField v;
    private MappingMethod N;

    public static void x(MRenderItem mRenderItem, Object object, Object object2, Object object3, boolean bl, Object object4, Object object5, int n, int n2, Object object6) {
        mRenderItem.E(object, object2, object3, bl, object4, object5, n, n2, object6);
    }

    public static MappingMethod V(MRenderItem mRenderItem) {
        return mRenderItem.o;
    }

    public static void G(MRenderItem mRenderItem, Object object, float f) {
        mRenderItem.Z(object, f);
    }

    private float f(Object object) {
        return this.Q.getFloat(object);
    }

    private void i(Object object, Object object2, Object object3, Object object4, int n, int n2) {
        this.H.invokeVoid(object, object2, object3, object4, n, n2);
    }

    public static Object a(MRenderItem mRenderItem) {
        return mRenderItem.Q();
    }

    private void E(Object object, Object object2, Object object3, boolean bl, Object object4, Object object5, int n, int n2, Object object6) {
        this.N.invokeVoid(object, object2, object3, bl, object4, object5, n, n2, object6);
    }

    private Object F(Object object, Object object2, Object object3, Object object4, int n) {
        return this.k.invokeObject(object, object2, object3, object4, n);
    }

    private void C(Object object, Object object2, Object object3, int n, int n2, int[] nArray, Object object4, Object object5, Object object6) {
        this.F.invokeVoid(null, object, object2, object3, n, n2, nArray, object4, object5, object6);
    }

    public static void P(MRenderItem mRenderItem, Object object, Object object2, Object object3, Object object4, int n, int n2) {
        mRenderItem.Q(object, object2, object3, object4, n, n2);
    }

    private Object Q() {
        return this.o.invokeObject(null, new Object[0]);
    }

    public static Object A(MRenderItem mRenderItem, Object object, Object object2, Object object3, Object object4, int n) {
        return mRenderItem.F(object, object2, object3, object4, n);
    }

    public MRenderItem() {
        this(MRenderManager.O());
    }

    private MRenderItem(String[] stringArray) {
        super(MappedClasses.z8);
        String[] stringArray2 = stringArray;
        if (ForgeVersion.MC_1_7_10.L()) {
            Class[] classArray = new Class[]{};
            Class<Void> clazz = Void.TYPE;
            boolean bl = false;
            String string = "<init>";
            MRenderItem mRenderItem = this;
            this.n = mRenderItem.Y(string, bl, clazz, classArray);
            if (!Wrapper.vapeInstance.isForgeAbsent()) {
                Class[] classArray2 = new Class[]{};
                Class clazz2 = MappedClasses.z8;
                boolean bl2 = false;
                String string2 = "getInstance";
                MRenderItem mRenderItem2 = this;
                this.o = this.registerStaticMethod(string2, bl2, clazz2, classArray2);
            }
            Class[] classArray3 = new Class[]{MappedClasses.uQ, MappedClasses.Dt, MappedClasses.VK, Integer.TYPE, Integer.TYPE};
            Class<Void> clazz3 = Void.TYPE;
            boolean bl3 = true;
            String string3 = "renderItemIntoGUI";
            MRenderItem mRenderItem3 = this;
            this.H = this.Y(string3, bl3, clazz3, classArray3);
            Class[] classArray4 = new Class[]{MappedClasses.uQ, MappedClasses.Dt, MappedClasses.VK, Integer.TYPE, Integer.TYPE};
            Class<Void> clazz4 = Void.TYPE;
            boolean bl4 = true;
            String string4 = "renderItemOverlayIntoGUI";
            MRenderItem mRenderItem4 = this;
            this.r = this.Y(string4, bl4, clazz4, classArray4);
        } else if (ForgeVersion.MC_1_20_6.d()) {
            if (ForgeVersion.MC_26_1.d()) {
                Class[] classArray = new Class[]{MappedClasses.VK, Integer.TYPE, Integer.TYPE};
                Class<Void> clazz = Void.TYPE;
                boolean bl = true;
                String string = "item";
                Class clazz5 = MappedClasses.m;
                MRenderItem mRenderItem = this;
                this.d = mRenderItem.registerInstanceMethodForOwner(clazz5, string, bl, clazz, classArray);
                Class[] classArray5 = new Class[]{MappedClasses.uQ, MappedClasses.VK, Integer.TYPE, Integer.TYPE};
                Class<Void> clazz6 = Void.TYPE;
                boolean bl5 = true;
                String string5 = "itemDecorations";
                Class clazz7 = MappedClasses.m;
                MRenderItem mRenderItem5 = this;
                this.s = this.registerInstanceMethodForOwner(clazz7, string5, bl5, clazz6, classArray5);
            } else {
                Class[] classArray = new Class[]{MappedClasses.VK, Integer.TYPE, Integer.TYPE};
                Class<Void> clazz = Void.TYPE;
                boolean bl = true;
                String string = "renderFakeItem";
                Class clazz8 = MappedClasses.m;
                MRenderItem mRenderItem = this;
                this.d = mRenderItem.registerInstanceMethodForOwner(clazz8, string, bl, clazz, classArray);
                Class[] classArray6 = new Class[]{MappedClasses.uQ, MappedClasses.VK, Integer.TYPE, Integer.TYPE};
                Class<Void> clazz9 = Void.TYPE;
                boolean bl6 = true;
                String string6 = "renderItemDecorations";
                Class clazz10 = MappedClasses.m;
                MRenderItem mRenderItem6 = this;
                this.s = this.registerInstanceMethodForOwner(clazz10, string6, bl6, clazz9, classArray6);
            }
        } else {
            Class[] classArray = new Class[]{MappedClasses.VK, Integer.TYPE, Integer.TYPE};
            Class<Void> clazz = Void.TYPE;
            boolean bl = true;
            String string = "renderItemIntoGUI";
            MRenderItem mRenderItem = this;
            this.d = mRenderItem.Y(string, bl, clazz, classArray);
            Class[] classArray7 = new Class[]{MappedClasses.uQ, MappedClasses.VK, Integer.TYPE, Integer.TYPE};
            Class<Void> clazz11 = Void.TYPE;
            boolean bl7 = true;
            String string7 = "renderItemOverlays";
            MRenderItem mRenderItem7 = this;
            this.s = this.Y(string7, bl7, clazz11, classArray7);
        }
        if (ForgeVersion.MC_1_20_6.v()) {
            Class<Float> clazz = Float.TYPE;
            boolean bl = true;
            String string = "zLevel";
            MRenderItem mRenderItem = this;
            this.Q = mRenderItem.J(string, bl, clazz);
        }
    }

    public static void Y(MRenderItem mRenderItem, Object object, Object object2, Object object3, int n, int n2) {
        mRenderItem.r(object, object2, object3, n, n2);
    }

    private void Z(Object object, float f) {
        this.v.setFloat(object, f);
    }

    private float j(Object object) {
        return this.v.getFloat(object);
    }

    public static float x(MRenderItem mRenderItem, Object object) {
        return mRenderItem.j(object);
    }

    public void M(Object object, Object object2, int n, int n2) {
        this.d.invokeVoid(object, object2, n, n2);
    }

    public static MappingMethod F(MRenderItem mRenderItem) {
        return mRenderItem.n;
    }

    public static void z(MRenderItem mRenderItem, Object object, Object object2, Object object3, Object object4, int n, int n2) {
        mRenderItem.i(object, object2, object3, object4, n, n2);
    }

    private void r(Object object, Object object2, Object object3, int n, int n2) {
        this.s.invokeVoid(object, object2, object3, n, n2);
    }

    public static void l(MRenderItem mRenderItem, Object object, float f) {
        mRenderItem.r(object, f);
    }

    public static float I(MRenderItem mRenderItem, Object object) {
        return mRenderItem.f(object);
    }


    private void Q(Object object, Object object2, Object object3, Object object4, int n, int n2) {
        this.r.invokeVoid(object, object2, object3, object4, n, n2);
    }

    private void r(Object object, float f) {
        this.Q.setFloat(object, f);
    }
}

