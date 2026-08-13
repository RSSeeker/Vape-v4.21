package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.impl.ForgeVersion;

public class MQuaternion
extends Mapping {
    private MappingField xField;
    private MappingField yField;
    private MappingField zField;
    private MappingField wField;
    private MappingMethod eulerAnglesConstructor;
    private MappingMethod componentsConstructor;

    public float getZ(Object instance) {
        return this.zField.getFloat(instance);
    }

    public float getX(Object instance) {
        return this.xField.getFloat(instance);
    }

    public MQuaternion() {
        this(BlockData.W());
    }

    private MQuaternion(String[] initializationGuard) {
        super(MappedClasses.qI);
        if (initializationGuard != null) {
            if (ForgeVersion.MC_1_20_6.v()) {
                this.eulerAnglesConstructor = this.Y("<init>", false, Void.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Boolean.TYPE);
            }
            this.componentsConstructor = this.Y("<init>", false, Void.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE);
            this.xField = this.J("x", true, Float.TYPE);
            this.yField = this.J("y", true, Float.TYPE);
            this.zField = this.J("z", true, Float.TYPE);
            this.wField = this.J("w", true, Float.TYPE);
        }
    }

    public float getW(Object instance) {
        return this.wField.getFloat(instance);
    }

    public Object newFromEulerAngles(float xAngle, float yAngle, float zAngle, boolean degrees) {
        return this.eulerAnglesConstructor.newInstance(Float.valueOf(xAngle), Float.valueOf(yAngle), Float.valueOf(zAngle), degrees);
    }

    public float getY(Object instance) {
        return this.yField.getFloat(instance);
    }

    public Object newFromComponents(float x, float y, float z, float w) {
        return this.componentsConstructor.newInstance(Float.valueOf(x), Float.valueOf(y), Float.valueOf(z), Float.valueOf(w));
    }
}

