package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MWorld;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MIWorldNameable
extends Mapping {
    private final MappingMethod Z;
    private final MappingMethod C;
    private final MappingMethod c;

    public Object A(Object object) {
        return this.c.invokeObject(object, new Object[0]);
    }

    public boolean V(Object object) {
        return this.Z.invokeBoolean(object, new Object[0]);
    }

    public MIWorldNameable() {
        this(MWorld.E$src$Z$o9fqyv());
    }

    private MIWorldNameable(boolean bl) {
        super(MappedClasses.Yp);
        if (bl) {
            if (ForgeVersion.MC_1_16_5.d()) {
                Class[] classArray = new Class[]{};
                Class clazz = MappedClasses.Yr;
                boolean bl2 = true;
                String string = "getName";
                MIWorldNameable mIWorldNameable = this;
                this.C = mIWorldNameable.Y(string, bl2, clazz, classArray);
            } else {
                Class[] classArray = new Class[]{};
                Class<String> clazz = String.class;
                boolean bl3 = true;
                String string = "getName";
                MIWorldNameable mIWorldNameable = this;
                this.C = mIWorldNameable.Y(string, bl3, clazz, classArray);
            }
            Class[] classArray = new Class[]{};
            Class<Boolean> clazz = Boolean.TYPE;
            boolean bl4 = true;
            String string = "hasCustomName";
            MIWorldNameable mIWorldNameable = this;
            this.Z = mIWorldNameable.Y(string, bl4, clazz, classArray);
            Class[] classArray2 = new Class[]{};
            Class clazz2 = MappedClasses.Yr;
            boolean bl5 = true;
            String string2 = "getDisplayName";
            MIWorldNameable mIWorldNameable2 = this;
            this.c = this.Y(string2, bl5, clazz2, classArray2);
            if (GuiComponent.getLegacyComponentState() == null) {
                MWorld.g(false);
            }
            return;
        }
        Class[] classArray = new Class[]{};
        Class<String> clazz = String.class;
        boolean bl6 = true;
        String string = "getName";
        MIWorldNameable mIWorldNameable = this;
        this.C = mIWorldNameable.Y(string, bl6, clazz, classArray); 
        Class[] classArray3 = new Class[]{};
        Class<Boolean> clazz3 = Boolean.TYPE;
        boolean bl7 = true;
        String string3 = "hasCustomName";
        MIWorldNameable mIWorldNameable3 = this;
        this.Z = this.Y(string3, bl7, clazz3, classArray3);
        Class[] classArray4 = new Class[]{};
        Class clazz4 = MappedClasses.Yr;
        boolean bl8 = true;
        String string4 = "getDisplayName";
        MIWorldNameable mIWorldNameable4 = this;
        this.c = this.Y(string4, bl8, clazz4, classArray4);
        if (GuiComponent.getLegacyComponentState() == null) {
            MWorld.g(true);
        }
    }

    public String L(Object object) {
        return this.C.invokeObject(object, new Object[0]).toString();
    }

    public Object s(Object object) {
        return this.C.invokeObject(object, new Object[0]);
    }

}

