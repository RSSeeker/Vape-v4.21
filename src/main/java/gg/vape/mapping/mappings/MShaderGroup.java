package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.List;

public class MShaderGroup
extends Mapping {
    private MappingMethod K;
    private final MappingField Y;
    private static String[] k;
    private MappingMethod r;

    public MShaderGroup() {
        this(MShaderGroup.V());
    }

    private MShaderGroup(String[] stringArray) {
        super(MappedClasses.Fo);
        if (stringArray != null) {
            Class<List> clazz = List.class;
            boolean bl = true;
            String string = "listShaders";
            MShaderGroup mShaderGroup = this;
            this.Y = mShaderGroup.J(string, bl, clazz);
            if (ForgeVersion.MC_1_21_4.d()) {
                this.K = null;
            } else if (ForgeVersion.MC_1_20_6.d()) {
                Class[] classArray = new Class[]{MappedClasses.Dt, MappedClasses.Z5, MappedClasses.ll, MappedClasses.zC};
                Class<Void> clazz2 = Void.TYPE;
                boolean bl2 = false;
                String string2 = "<init>";
                MShaderGroup mShaderGroup2 = this;
                this.K = this.Y(string2, bl2, clazz2, classArray);
            } else {
                Class[] classArray = new Class[]{MappedClasses.Dt, MappedClasses.qY, MappedClasses.ll, MappedClasses.zC};
                Class<Void> clazz3 = Void.TYPE;
                boolean bl3 = false;
                String string3 = "<init>";
                MShaderGroup mShaderGroup3 = this;
                this.K = this.Y(string3, bl3, clazz3, classArray);
            }
            if (!ForgeVersion.MC_1_21_0.d()) {
                Class[] classArray = new Class[]{Integer.TYPE, Integer.TYPE};
                Class<Void> clazz4 = Void.TYPE;
                boolean bl4 = true;
                String string4 = "createBindFramebuffers";
                MShaderGroup mShaderGroup4 = this;
                this.r = this.Y(string4, bl4, clazz4, classArray);
            }
            if (GuiComponent.getLegacyComponentState() == null) {
                MShaderGroup.H(new String[2]);
            }
            return;
        }
        Class<List> clazz = List.class;
        boolean bl = true;
        String string = "listShaders";
        MShaderGroup mShaderGroup = this;
        this.Y = mShaderGroup.J(string, bl, clazz);
        if (ForgeVersion.MC_1_21_4.d()) {
            Class[] classArray = new Class[]{MappedClasses.Dt, MappedClasses.Z5, MappedClasses.ll, MappedClasses.zC};
            Class<Void> clazz5 = Void.TYPE;
            boolean bl5 = false;
            String string5 = "<init>";
            MShaderGroup mShaderGroup5 = this;
            this.K = this.Y(string5, bl5, clazz5, classArray);
        }
        Class[] classArray = new Class[]{MappedClasses.Dt, MappedClasses.qY, MappedClasses.ll, MappedClasses.zC};
        Class<Void> clazz6 = Void.TYPE;
        boolean bl6 = false;
        String string6 = "<init>";
        MShaderGroup mShaderGroup6 = this;
        this.K = this.Y(string6, bl6, clazz6, classArray);
        if (!ForgeVersion.MC_1_21_0.d()) {
            Class[] classArray2 = new Class[]{Integer.TYPE, Integer.TYPE};
            Class<Void> clazz7 = Void.TYPE;
            boolean bl7 = true;
            String string7 = "createBindFramebuffers";
            MShaderGroup mShaderGroup7 = this;
            this.r = this.Y(string7, bl7, clazz7, classArray2);
        }
        if (GuiComponent.getLegacyComponentState() == null) {
            MShaderGroup.H(new String[2]);
        }
    }

    public void G(Object object, int n, int n2) {
        this.r.invokeVoid(object, n, n2);
    }

    public static String[] V() {
        return k;
    }

    public Object k(Object object, Object object2, Object object3, Object object4) {
        if (this.K == null) {
            return null;
        }
        return this.K.newInstance(object, object2, object3, object4);
    }

    public static void H(String[] stringArray) {
        k = stringArray;
    }

    static {
        MShaderGroup.H(new String[3]);
    }


    public List h(Object object) {
        return (List)this.Y.getObject(object);
    }
}

