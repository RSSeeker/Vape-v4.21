package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MVoxelShape
extends Mapping {
    private final MappingMethod isEmptyMethod;
    private final MappingMethod getBoundingBoxMethod;

    public MVoxelShape() {
        super(MappedClasses.la);
        this.getBoundingBoxMethod = this.Y("getBoundingBox", true, MappedClasses.uk, new Class[]{});
        this.isEmptyMethod = this.methodBuilder("isEmpty", Boolean.TYPE, new Class[]{}).buildMethod();
    }

    public boolean isEmpty(Object shapeHandle) {
        return this.isEmptyMethod.invokeBoolean(shapeHandle, new Object[0]);
    }

    public Object getBoundingBox(Object shapeHandle) {
        return this.getBoundingBoxMethod.invokeObject(shapeHandle, new Object[0]);
    }
}
