package gg.vape.wrapper.impl;

public class EntityRayTraceResult
extends RayTraceResult {
    public Entity getHitEntity() {
        return new Entity(EntityRayTraceResult.vapeInstance.getMappingsMapperCompat().entityRayTraceResult
                .getEntity(this.I));
    }

    public EntityRayTraceResult(Object rayTraceResultHandle) {
        super(rayTraceResultHandle);
    }

    public static EntityRayTraceResult create(Entity entity, Vec3 hitPosition) {
        Object resultHandle = EntityRayTraceResult.vapeInstance.getMappingsMapperCompat().entityRayTraceResult
                .create(entity.getObject(), hitPosition.getObject());
        return new EntityRayTraceResult(resultHandle);
    }
}
