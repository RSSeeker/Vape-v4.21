package gg.vape.module.blatant.blockin;

import gg.vape.rotation.RotationAngles;
import gg.vape.utils.Vec3d;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.RayTraceResult;
import gg.vape.wrapper.impl.Vec3;

public class BlockInTargetRotationState {
    private Vec3d hitVector;
    private float pitch;
    private boolean sprinting;
    private BlockPlacementGraph placementGraph;
    private RayTraceResult rayTraceResult;
    private boolean onGround;
    private Vec3d targetVector;
    private double positionY;
    private boolean collidedHorizontally;
    private double positionX;
    private double positionZ;
    private float yaw;

    public BlockInTargetRotationState(EntityLivingBase entity) {
        this.positionX = entity.z();
        this.positionY = entity.N();
        this.positionZ = entity.h();
        this.yaw = entity.J();
        this.pitch = entity.V();
        this.onGround = entity.b$src$Z$fqlxe4();
        this.collidedHorizontally = entity.P();
        this.sprinting = entity.B$src$Z$f90iek();
    }

    public RotationAngles getRotation() {
        return new RotationAngles(this.yaw, this.pitch);
    }

    public boolean isCollidedHorizontally() {
        return this.collidedHorizontally;
    }

    public double getPositionX() {
        return this.positionX;
    }

    public boolean isSprinting() {
        return this.sprinting;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setHitVector(Vec3d hitVector) {
        this.hitVector = hitVector;
    }

    public void setRayTraceResult(RayTraceResult rayTraceResult) {
        this.rayTraceResult = rayTraceResult;
    }

    public float getPitch() {
        return this.pitch;
    }

    public Vec3d getHitVector() {
        return this.hitVector;
    }

    public Vec3d getTargetVector() {
        return this.targetVector;
    }

    public BlockPlacementGraph getPlacementGraph() {
        return this.placementGraph;
    }

    public boolean isOnGround() {
        return this.onGround;
    }

    public Vec3 getPositionVec3() {
        return Vec3.create(this.positionX, this.positionY, this.positionZ);
    }

    public double getPositionY() {
        return this.positionY;
    }

    public double getPositionZ() {
        return this.positionZ;
    }

    public void setPlacementGraph(BlockPlacementGraph placementGraph) {
        this.placementGraph = placementGraph;
    }

    public RayTraceResult getRayTraceResult() {
        return this.rayTraceResult;
    }

    public Vec3d getPosition() {
        return new Vec3d(this.positionX, this.positionY, this.positionZ);
    }

    public void setTargetVector(Vec3d targetVector) {
        this.targetVector = targetVector;
    }
}
