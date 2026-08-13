package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.ScaledResolution;

public class MAbstractGui
extends Mapping {
    private MappingMethod V;
    private MappingMethod s;
    private MappingField p;
    private MappingMethod O;
    public MappingMethod r;
    private MappingField e;
    private MappingMethod x;
    private MappingMethod B;
    private MappingMethod m;

    public Object O(Object object) {
        return this.p.getObject(object);
    }

    public void J(Object object, int n, int n2, int n3, int n4) {
        this.B.invokeVoid(object, n, n2, n3, n4);
    }

    public Object B(Object object) {
        return this.e.getObject(object);
    }

    public static void x(MAbstractGui mAbstractGui, Object object, Object object2, int n, int n2, int n3, int n4, int n5, Object object3) {
        mAbstractGui.q(object, object2, n, n2, n3, n4, n5, object3);
    }


    private void q(Object object, Object object2, int n, int n2, int n3, int n4, int n5, Object object3) {
        this.V.invokeVoid(object, object2, n, n2, n3, n4, n5, object3);
    }

    public static Object y(MAbstractGui mAbstractGui, Object object, Object object2, Object object3, Object[] objectArray) {
        return mAbstractGui.z(object, object2, object3, objectArray);
    }

    private Object z(Object object, Object object2, Object object3, Object ... objectArray) {
        if (ForgeVersion.MC_1_21_11.d()) {
            return this.m.newInstance(object, object2, object3, (Integer)objectArray[0], (Integer)objectArray[1]);
        }
        return this.m.newInstance(object, object2, object3);
    }

    private void G(Object object, Object object2, int n, int n2, int n3, int n4, int n5, float f, float f2, float f3, float f4) {
        this.O.invokeVoid(object, object2, n, n2, n3, n4, n5, Float.valueOf(f), Float.valueOf(f2), Float.valueOf(f3), Float.valueOf(f4));
    }

    public void Q(Object object, Object object2, String string, int n, int n2, int n3, boolean bl) {
        this.x.invokeVoid(object, object2, string, n, n2, n3, bl);
    }

    public void j(Object object) {
        this.r.invokeVoidNoArgs(object);
    }

    public void J(Object object) {
        this.s.invokeVoidNoArgs(object);
    }

    public static void e(MAbstractGui mAbstractGui, Object object, Object object2, int n, int n2, int n3, int n4, int n5, float f, float f2, float f3, float f4) {
        mAbstractGui.G(object, object2, n, n2, n3, n4, n5, f, f2, f3, f4);
    }

    public MAbstractGui() {
        this(ScaledResolution.q());
    }

    private MAbstractGui(int n) {
        super(MappedClasses.m);
        int n2 = n;
        if (ForgeVersion.MC_1_20_6.d()) {
            if (ForgeVersion.MC_1_21_0.v()) {
                Class[] classArray = new Class[]{MappedClasses.zC, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE};
                Class<Void> clazz = Void.TYPE;
                boolean bl = true;
                String string = "innerBlit";
                MAbstractGui mAbstractGui = this;
                this.O = mAbstractGui.Y(string, bl, clazz, classArray);
                Class[] classArray2 = new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, MappedClasses.Db};
                Class<Void> clazz2 = Void.TYPE;
                boolean bl2 = true;
                String string2 = "blit";
                MAbstractGui mAbstractGui2 = this;
                this.V = this.Y(string2, bl2, clazz2, classArray2);
            }
            if (ForgeVersion.MC_1_21_6.d()) {
                Class[] classArray = new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE};
                Class<Void> clazz = Void.TYPE;
                boolean bl = true;
                String string = "enableScissor";
                MAbstractGui mAbstractGui = this;
                this.B = mAbstractGui.Y(string, bl, clazz, classArray);
                Class[] classArray3 = new Class[]{};
                Class<Void> clazz3 = Void.TYPE;
                boolean bl3 = true;
                String string3 = "disableScissor";
                MAbstractGui mAbstractGui3 = this;
                this.s = this.Y(string3, bl3, clazz3, classArray3);
                Class clazz4 = MappedClasses.i;
                boolean bl4 = true;
                String string4 = "guiRenderState";
                MAbstractGui mAbstractGui4 = this;
                this.p = this.J(string4, bl4, clazz4);
                if (ForgeVersion.MC_1_21_11.d()) {
                    Class[] classArray4 = new Class[]{MappedClasses.uP, MappedClasses.Dy, MappedClasses.i, Integer.TYPE, Integer.TYPE};
                    MAbstractGui mAbstractGui5 = this;
                    this.m = this.registerConstructor(classArray4);
                } else {
                    Class[] classArray5 = new Class[]{MappedClasses.uP, MappedClasses.Dy, MappedClasses.i};
                    MAbstractGui mAbstractGui6 = this;
                    this.m = this.registerConstructor(classArray5);
                }
                Class clazz5 = MappedClasses.Dy;
                boolean bl5 = true;
                String string5 = "pose";
                MAbstractGui mAbstractGui7 = this;
                this.e = this.J(string5, bl5, clazz5);
                Class[] classArray6 = new Class[]{MappedClasses.uQ, String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, Boolean.TYPE};
                Class<Void> clazz6 = Void.TYPE;
                boolean bl6 = true;
                String string6 = ForgeVersion.MC_26_1.d() ? "text" : "drawString";
                MAbstractGui mAbstractGui8 = this;
                this.x = this.Y(string6, bl6, clazz6, classArray6);
            } else {
                Class[] classArray = new Class[]{MappedClasses.uP, MappedClasses.DQ, MappedClasses.lp};
                Class<Void> clazz = Void.TYPE;
                boolean bl = false;
                String string = "<init>";
                MAbstractGui mAbstractGui = this;
                this.m = mAbstractGui.Y(string, bl, clazz, classArray);
                Class[] classArray7 = new Class[]{};
                Class<Void> clazz7 = Void.TYPE;
                boolean bl7 = true;
                String string7 = "flush";
                MAbstractGui mAbstractGui9 = this;
                this.r = this.Y(string7, bl7, clazz7, classArray7);
            }
        } else {
            Class[] classArray = new Class[]{MappedClasses.DQ, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, MappedClasses.Db};
            Class<Void> clazz = Void.TYPE;
            boolean bl = Wrapper.isNativeAvailable;
            String string = "func_238470_a_";
            MAbstractGui mAbstractGui = this;
            this.V = mAbstractGui.registerStaticMethod(string, bl, clazz, classArray);
            Class[] classArray8 = new Class[]{MappedClasses.qr, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE};
            Class<Void> clazz8 = Void.TYPE;
            boolean bl8 = true;
            String string8 = "innerBlit";
            MAbstractGui mAbstractGui10 = this;
            this.O = this.registerStaticMethod(string8, bl8, clazz8, classArray8);
        }
    }
}

