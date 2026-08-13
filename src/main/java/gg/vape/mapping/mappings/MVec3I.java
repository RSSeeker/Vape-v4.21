package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;

public class MVec3I
extends Mapping {
    private final MappingField xField;
    private final MappingField yField;
    private final MappingField zField;
    private final MappingMethod constructor;

    public static MappingMethod getConstructor(MVec3I mapping) {
        return mapping.constructor;
    }

    public MVec3I() {
        super(MappedClasses.Vr);
        this.constructor = this.Y("<init>", false, Void.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE);
        this.xField = this.J("x", true, Integer.TYPE);
        this.yField = this.J("y", true, Integer.TYPE);
        this.zField = this.J("z", true, Integer.TYPE);
    }

    public int getX(Object instance) {
        return this.xField.getInt(instance);
    }

    public int getY(Object instance) {
        return this.yField.getInt(instance);
    }

    public int getZ(Object instance) {
        return this.zField.getInt(instance);
    }
}

