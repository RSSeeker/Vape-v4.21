package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MRayTraceContext
extends Mapping {
    private static final String CONSTRUCTOR = "<init>";
    private final MappingMethod constructor;

    public MRayTraceContext() {
        super(MappedClasses.Fc);
        this.constructor = this.Y(CONSTRUCTOR, false, Void.TYPE, new Class[]{
                MappedClasses.qP, MappedClasses.qP, MappedClasses.DS, MappedClasses.Dm, MappedClasses.zc});
    }

    public Object create(Object startVec, Object endVec, Object fluidMode, Object blockMode, Object entity) {
        return this.constructor.newInstance(startVec, endVec, fluidMode, blockMode, entity);
    }
}
