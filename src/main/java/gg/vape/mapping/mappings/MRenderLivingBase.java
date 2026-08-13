package gg.vape.mapping.mappings;

import gg.vape.Vape;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;
import java.nio.FloatBuffer;
import java.util.List;

public class MRenderLivingBase
extends Mapping {
    private MappingField g;
    public MappingMethod c;
    public MappingMethod O;
    private MappingField G;
    private MappingMethod Q;
    private MappingMethod h;
    public MappingMethod r;
    private MappingField f;
    private MappingField o;

    public static Object i(MRenderLivingBase mRenderLivingBase, Object object) {
        return mRenderLivingBase.I(object);
    }

    public static void O(MRenderLivingBase mRenderLivingBase, Object object, Object object2) {
        mRenderLivingBase.B(object, object2);
    }

    private Object I(Object object) {
        return this.G.getObject(object);
    }

    private void B(Object object, Object object2) {
        this.G.setObject(object, object2);
    }

    private Object d(Object object) {
        if (ForgeVersion.MC_1_16_5.d()) {
            return this.g.getObject(object);
        }
        if (ForgeVersion.MC_1_7_10.L()) {
            return this.o.getObject(object);
        }
        return this.Q.invokeObject(object, new Object[0]);
    }


    public static FloatBuffer y(MRenderLivingBase mRenderLivingBase, Object object) {
        return mRenderLivingBase.D(object);
    }

    public static Object x(MRenderLivingBase mRenderLivingBase, Object object) {
        return mRenderLivingBase.d(object);
    }

    public MRenderLivingBase() {
        this(MRenderManager.O());
    }

    private MRenderLivingBase(String[] stringArray) {
        super(MappedClasses.Fq);
        String[] stringArray2 = stringArray;
        if (ForgeVersion.MC_1_21_10.d()) {
            this.c = null;
            Class clazz = MappedClasses.V6;
            boolean bl = true;
            String string = "entityModel";
            MRenderLivingBase mRenderLivingBase = this;
            this.g = mRenderLivingBase.J(string, bl, clazz);
        } else if (ForgeVersion.MC_1_16_5.d()) {
            Class[] classArray = new Class[]{MappedClasses.zc, Float.TYPE, Float.TYPE, MappedClasses.DQ, MappedClasses.ZK, Integer.TYPE};
            Class<Void> clazz = Void.TYPE;
            boolean bl = true;
            String string = "render";
            Class clazz2 = MappedClasses.VQ;
            MRenderLivingBase mRenderLivingBase = this;
            this.c = mRenderLivingBase.registerInstanceMethodForOwner(clazz2, string, bl, clazz, classArray);
            Class clazz3 = MappedClasses.V6;
            boolean bl2 = true;
            String string2 = "entityModel";
            MRenderLivingBase mRenderLivingBase2 = this;
            this.g = this.J(string2, bl2, clazz3);
        } else if (ForgeVersion.MC_1_7_10.L()) {
            Class clazz = MappedClasses.M;
            boolean bl = true;
            String string = "mainModel";
            MRenderLivingBase mRenderLivingBase = this;
            this.o = mRenderLivingBase.J(string, bl, clazz);
            Class[] classArray = new Class[]{MappedClasses.zm, Double.TYPE, Double.TYPE, Double.TYPE};
            Class<Void> clazz4 = Void.TYPE;
            boolean bl3 = true;
            String string3 = "passSpecialRender";
            MRenderLivingBase mRenderLivingBase3 = this;
            this.r = this.Y(string3, bl3, clazz4, classArray);
        } else {
            if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
                Class<List> clazz = List.class;
                boolean bl = true;
                String string = "layerRenderers";
                MRenderLivingBase mRenderLivingBase = this;
                this.G = mRenderLivingBase.J(string, bl, clazz);
            } else {
                Class<List> clazz = List.class;
                boolean bl = Wrapper.isNativeAvailable;
                String string = "field_177097_h";
                MRenderLivingBase mRenderLivingBase = this;
                this.G = mRenderLivingBase.J(string, bl, clazz);
            }
            Class<FloatBuffer> clazz = FloatBuffer.class;
            boolean bl = true;
            String string = "brightnessBuffer";
            MRenderLivingBase mRenderLivingBase = this;
            this.f = mRenderLivingBase.J(string, bl, clazz);
            Class[] classArray = new Class[]{};
            Class clazz5 = MappedClasses.M;
            boolean bl4 = true;
            String string4 = "getMainModel";
            MRenderLivingBase mRenderLivingBase4 = this;
            this.Q = this.Y(string4, bl4, clazz5, classArray);
            Class[] classArray2 = new Class[]{MappedClasses.zm, Double.TYPE, Double.TYPE, Double.TYPE};
            Class<Void> clazz6 = Void.TYPE;
            boolean bl5 = true;
            String string5 = "renderName";
            MRenderLivingBase mRenderLivingBase5 = this;
            this.c = this.Y(string5, bl5, clazz6, classArray2);
            Class[] classArray3 = new Class[]{};
            Class<Void> clazz7 = Void.TYPE;
            boolean bl6 = true;
            String string6 = "unsetBrightness";
            MRenderLivingBase mRenderLivingBase6 = this;
            this.h = this.Y(string6, bl6, clazz7, classArray3);
            Class[] classArray4 = new Class[]{MappedClasses.zm, Float.TYPE, Boolean.TYPE};
            Class<Boolean> clazz8 = Boolean.TYPE;
            boolean bl7 = true;
            String string7 = "setBrightness";
            MRenderLivingBase mRenderLivingBase7 = this;
            this.O = this.Y(string7, bl7, clazz8, classArray4);
        }
    }

    boolean A(Object object, Object object2, float f, boolean bl) {
        return this.O.invokeBoolean(object, object2, Float.valueOf(f), bl);
    }

    private void t(Object object) {
        this.h.invokeVoidNoArgs(object);
    }

    private FloatBuffer D(Object object) {
        return (FloatBuffer)this.f.getObject(object);
    }
}

