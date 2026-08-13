package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.BuiltInRegistries;

public class MBuiltInRegistries
extends Mapping {
    private MappingField j;
    private MappingField L;
    private MappingField n;
    private MappingField d;
    private MappingField G;

    public static Object g(MBuiltInRegistries mBuiltInRegistries) {
        return mBuiltInRegistries.n();
    }

    public static Object w(MBuiltInRegistries mBuiltInRegistries) {
        return mBuiltInRegistries.k();
    }

    public static Object M(MBuiltInRegistries mBuiltInRegistries) {
        return mBuiltInRegistries.o();
    }

    private Object k() {
        return this.j.getObject(null);
    }

    private Object o() {
        return this.G.getObject(null);
    }

    private Object p() {
        return this.L.getObject(null);
    }

    public MBuiltInRegistries() {
        this(BuiltInRegistries.f());
    }

    private MBuiltInRegistries(GuiComponent[] guiComponentArray) {
        super(MappedClasses.R);
        Class clazz = MappedClasses.Fk;
        boolean bl = true;
        String string = "MOB_EFFECT";
        MBuiltInRegistries mBuiltInRegistries = this;
        this.j = this.registerStaticField(string, bl, clazz);
        Class clazz2 = MappedClasses.Fk;
        boolean bl2 = true;
        String string2 = "POTION";
        MBuiltInRegistries mBuiltInRegistries2 = this;
        this.G = this.registerStaticField(string2, bl2, clazz2);
        Class clazz3 = MappedClasses.lz;
        boolean bl3 = true;
        String string3 = "ITEM";
        MBuiltInRegistries mBuiltInRegistries3 = this;
        this.L = this.registerStaticField(string3, bl3, clazz3);
        Class clazz4 = MappedClasses.lz;
        boolean bl4 = true;
        String string4 = "BLOCK";
        MBuiltInRegistries mBuiltInRegistries4 = this;
        this.d = this.registerStaticField(string4, bl4, clazz4);
        GuiComponent[] guiComponentArray2 = guiComponentArray;
        Class clazz5 = MappedClasses.Fk;
        boolean bl5 = true;
        String string5 = "PARTICLE_TYPE";
        MBuiltInRegistries mBuiltInRegistries5 = this;
        this.n = this.registerStaticField(string5, bl5, clazz5);
    }


    private Object T() {
        return this.d.getObject(null);
    }

    public static Object F(MBuiltInRegistries mBuiltInRegistries) {
        return mBuiltInRegistries.T();
    }

    private Object n() {
        return this.n.getObject(null);
    }

    public static Object v(MBuiltInRegistries mBuiltInRegistries) {
        return mBuiltInRegistries.p();
    }
}

