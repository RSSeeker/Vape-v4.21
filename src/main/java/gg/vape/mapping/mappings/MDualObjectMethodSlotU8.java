package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MDualObjectMethodSlotU8
extends Mapping {
    private final MappingMethod W;
    private final MappingMethod h;

    public MDualObjectMethodSlotU8() {
        super(MappedClasses.u8);
        Class[] classArray = new Class[]{};
        Class clazz = MappedClasses.Vv;
        boolean bl = true;
        String string = "getBlockState";
        MDualObjectMethodSlotU8 mDualObjectMethodSlotU8 = this;
        this.W = this.Y(string, bl, clazz, classArray);
        Class[] classArray2 = new Class[]{};
        Class clazz2 = MappedClasses.lf;
        boolean bl2 = true;
        String string2 = "getPos";
        MDualObjectMethodSlotU8 mDualObjectMethodSlotU82 = this;
        this.h = this.Y(string2, bl2, clazz2, classArray2);
    }

    public Object y(Object object) {
        return this.h.invokeObject(object, new Object[0]);
    }

    public Object E(Object object) {
        return this.W.invokeObject(object, new Object[0]);
    }
}

