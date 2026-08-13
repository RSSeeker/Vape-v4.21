package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MRayTraceResult_Type;
import gg.vape.wrapper.Wrapper;

public class RayTraceResult_type
extends Wrapper {
    private static RayTraceResult_type entity;
    private static RayTraceResult_type block;
    private static RayTraceResult_type miss;

    public static RayTraceResult_type entity() {
        if (entity == null) {
            entity = new RayTraceResult_type(MRayTraceResult_Type.getEntity(RayTraceResult_type.vapeInstance.getMappingsMapperCompat().rayTraceResultType));
        }
        return entity;
    }

    public static RayTraceResult_type block() {
        if (block == null) {
            block = new RayTraceResult_type(MRayTraceResult_Type.getBlock(RayTraceResult_type.vapeInstance.getMappingsMapperCompat().rayTraceResultType));
        }
        return block;
    }

    public RayTraceResult_type(Object handle) {
        super(handle);
    }


    public static RayTraceResult_type miss() {
        if (miss == null) {
            miss = new RayTraceResult_type(RayTraceResult_type.vapeInstance.getMappingsMapperCompat().rayTraceResultType.getMiss());
        }
        return miss;
    }
}

