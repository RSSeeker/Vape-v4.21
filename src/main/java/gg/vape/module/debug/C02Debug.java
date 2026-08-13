package gg.vape.module.debug;

import gg.vape.utils.Vec3d;
import gg.vape.wrapper.impl.Entity;

public class C02Debug {
    private int entityId;
    private String action;
    private Vec3d hitVec;
    private String entityName;
    private final int packetId;

    public C02Debug(int packetId, Entity entity, String action) {
        this.packetId = packetId;
        this.entityName = entity.getName();
        this.entityId = entity.S();
        this.action = action;
    }

    @Override
    public String toString() {
        String message = String.format("[C02 | %d], Entity: %s (ID: %d), Action: %s", this.packetId, this.entityName, this.entityId, this.action);
        if (this.hitVec != null) {
            message = message + ", HitVec: [" + this.hitVec.getX() + " " + this.hitVec.getY() + " " + this.hitVec.getZ() + "]";
        }
        return message;
    }

    public void setHitVector(Vec3d hitVector) {
        this.hitVec = hitVector;
    }
}

