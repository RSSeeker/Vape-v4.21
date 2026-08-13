package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

import java.util.function.Predicate;

public class EntityRayTraceBridge
extends Wrapper {
    public EntityRayTraceBridge(Object bridgeHandle) {
        super(bridgeHandle);
    }

    public RayTraceResult getClosestHit(Entity entity, float distance, Predicate<Object> predicate) {
        Object resultHandle = EntityRayTraceBridge.vapeInstance.getMappingsMapperCompat().entityRayTraceBridge
                .getClosestHit(this.I, entity.getObject(), distance, predicate);
        return new RayTraceResult(resultHandle);
    }
}
