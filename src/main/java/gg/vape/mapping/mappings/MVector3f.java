package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.impl.ForgeVersion;

public class MVector3f
extends Mapping {
    private final MappingMethod constructor;
    private MappingField xField;
    private MappingField yField;
    private MappingField zField;
    private MappingMethod rotationDegreesMethod;

    public float getZ(Object instance) {
        return this.zField.getFloat(instance);
    }

    public Object rotationDegrees(Object instance, float degrees) {
        return this.rotationDegreesMethod.invokeObject(instance, Float.valueOf(degrees));
    }

    public Object newInstance(float x, float y, float z) {
        return this.constructor.newInstance(Float.valueOf(x), Float.valueOf(y), Float.valueOf(z));
    }

    public MVector3f() {
        super(MappedClasses.qb);
        this.constructor = this.Y("<init>", false, Void.TYPE, Float.TYPE, Float.TYPE, Float.TYPE);
        if (BlockData.W() != null) {
            if (ForgeVersion.MC_1_20_6.v()) {
                this.rotationDegreesMethod = this.Y("rotationDegrees", true, MappedClasses.qI, Float.TYPE);
            }
            this.xField = this.J("x", true, Float.TYPE);
            this.yField = this.J("y", true, Float.TYPE);
            this.zField = this.J("z", true, Float.TYPE);
        }
    }

    public float getY(Object instance) {
        return this.yField.getFloat(instance);
    }

    public float getX(Object instance) {
        return this.xField.getFloat(instance);
    }
}

