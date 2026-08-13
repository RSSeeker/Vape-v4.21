package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class RayTraceContext
extends Wrapper {
    public static RayTraceContext b(Vec3 vec3, Vec3 vec32, RayTraceContext$FluidMode rayTraceContext$FluidMode, RayTraceContext$BlockMode rayTraceContext$BlockMode, Entity entity) {
        Object object = RayTraceContext.vapeInstance.getMappingsMapperCompat().Rs.create(vec3.getObject(), vec32.getObject(), rayTraceContext$FluidMode.getObject(), rayTraceContext$BlockMode.getObject(), entity.getObject());
        return new RayTraceContext(object);
    }

    public RayTraceContext(Object object) {
        super(object);
    }
}

