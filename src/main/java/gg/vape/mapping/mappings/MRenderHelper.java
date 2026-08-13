package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MEntityRenderer;
import gg.vape.wrapper.impl.ForgeVersion;

public class MRenderHelper
extends Mapping {
    private MappingMethod Y;
    private MappingMethod E;
    private MappingMethod h;

    private void I() {
        this.h.invokeVoidNoArgs(null);
    }

    private void Z() {
        this.Y.invokeVoidNoArgs(null);
    }


    private void W() {
        this.E.invokeVoidNoArgs(null);
    }

    public static void L(MRenderHelper mRenderHelper) {
        mRenderHelper.I();
    }

    public static void Q(MRenderHelper mRenderHelper) {
        mRenderHelper.W();
    }

    public static void M(MRenderHelper mRenderHelper) {
        mRenderHelper.Z();
    }

    public MRenderHelper() {
        this(MEntityRenderer.X());
    }

    private MRenderHelper(int n) {
        super(MappedClasses.ql);
        int n2 = n;
        if (ForgeVersion.MC_1_17.v()) {
            Class[] classArray = new Class[]{};
            Class<Void> clazz = Void.TYPE;
            boolean bl = true;
            String string = "disableStandardItemLighting";
            MRenderHelper mRenderHelper = this;
            this.h = mRenderHelper.registerStaticMethod(string, bl, clazz, classArray);
            Class[] classArray2 = new Class[]{};
            Class<Void> clazz2 = Void.TYPE;
            boolean bl2 = true;
            String string2 = "enableStandardItemLighting";
            MRenderHelper mRenderHelper2 = this;
            this.E = this.registerStaticMethod(string2, bl2, clazz2, classArray2);
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            if (ForgeVersion.MC_1_17.d() && ForgeVersion.MC_1_21_6.v()) {
                Class[] classArray = new Class[]{};
                Class<Void> clazz = Void.TYPE;
                boolean bl = true;
                String string = "setupForFlatItems";
                MRenderHelper mRenderHelper = this;
                this.Y = mRenderHelper.registerStaticMethod(string, bl, clazz, classArray);
                Class[] classArray3 = new Class[]{};
                Class<Void> clazz3 = Void.TYPE;
                boolean bl3 = true;
                String string3 = "setupFor3DItems";
                MRenderHelper mRenderHelper3 = this;
                this.E = this.registerStaticMethod(string3, bl3, clazz3, classArray3);
            }
        } else {
            Class[] classArray = new Class[]{};
            Class<Void> clazz = Void.TYPE;
            boolean bl = true;
            String string = "enableGUIStandardItemLighting";
            MRenderHelper mRenderHelper = this;
            this.Y = mRenderHelper.registerStaticMethod(string, bl, clazz, classArray);
        }
    }
}

