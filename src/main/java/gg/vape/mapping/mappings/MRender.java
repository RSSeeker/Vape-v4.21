package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MRenderManager;
import gg.vape.wrapper.impl.ForgeVersion;

public class MRender
extends Mapping {
    private MappingMethod I;
    public MappingMethod p;
    public MappingMethod z;
    public MappingMethod w;

    private Object e(Object object, Object object2) {
        return this.w.invokeObject(object, object2);
    }

    public static Object s(MRender mRender, Object object, Object object2, float f) {
        return mRender.w(object, object2, f);
    }

    public static Object s(MRender mRender, Object object, Object object2) {
        return mRender.e(object, object2);
    }

    private void S(Object object, Object object2, double d, double d2, double d3, float f, float f2) {
        this.p.invokeVoid(object, object2, d, d2, d3, Float.valueOf(f), Float.valueOf(f2));
    }


    public static void E(MRender mRender, Object object, Object object2, double d, double d2, double d3, float f, float f2) {
        mRender.S(object, object2, d, d2, d3, f, f2);
    }

    public MRender() {
        this(MRenderManager.O());
    }

    private MRender(String[] stringArray) {
        super(MappedClasses.VQ);
        String[] stringArray2 = stringArray;
        if (ForgeVersion.MC_1_21_6.d()) {
            Class[] classArray = new Class[]{MappedClasses.zc, Float.TYPE};
            Class clazz = MappedClasses.qX;
            boolean bl = true;
            String string = "createRenderState";
            MRender mRender = this;
            this.I = mRender.Y(string, bl, clazz, classArray);
        }
        if (ForgeVersion.MC_1_21_10.d()) {
            Class[] classArray = new Class[]{MappedClasses.zc, MappedClasses.qX, Float.TYPE};
            Class<Void> clazz = Void.TYPE;
            boolean bl = true;
            String string = "extractRenderState";
            MRender mRender = this;
            this.z = mRender.Y(string, bl, clazz, classArray);
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            if (ForgeVersion.MC_1_21_10.v()) {
                Class[] classArray = new Class[]{MappedClasses.zc, Float.TYPE, Float.TYPE, MappedClasses.DQ, MappedClasses.ZK, Integer.TYPE};
                Class<Void> clazz = Void.TYPE;
                boolean bl = true;
                String string = "render";
                MRender mRender = this;
                this.p = mRender.Y(string, bl, clazz, classArray);
            }
        } else {
            Class[] classArray = new Class[]{MappedClasses.zc, Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE};
            Class<Void> clazz = Void.TYPE;
            boolean bl = true;
            String string = "doRender";
            MRender mRender = this;
            this.p = mRender.Y(string, bl, clazz, classArray);
        }
        if (ForgeVersion.MC_1_21_4.d()) {
            Class[] classArray = new Class[]{MappedClasses.qX};
            Class clazz = MappedClasses.zC;
            boolean bl = true;
            String string = "getTextureLocation";
            Class clazz2 = MappedClasses.Fq;
            MRender mRender = this;
            this.w = mRender.registerInstanceMethodForOwner(clazz2, string, bl, clazz, classArray);
        } else {
            Class[] classArray = new Class[]{MappedClasses.zc};
            Class clazz = MappedClasses.zC;
            boolean bl = true;
            String string = "getEntityTexture";
            MRender mRender = this;
            this.w = mRender.Y(string, bl, clazz, classArray);
        }
    }

    private Object w(Object object, Object object2, float f) {
        return this.I.invokeObject(object, object2, Float.valueOf(f));
    }
}

