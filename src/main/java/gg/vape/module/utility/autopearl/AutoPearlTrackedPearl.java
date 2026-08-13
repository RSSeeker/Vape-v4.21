package gg.vape.module.utility.autopearl;

import gg.vape.mapping.MappedClasses;
import gg.vape.module.utility.autopearl.AutoPearlRotationController;
import gg.vape.utils.BlockUtil;
import gg.vape.wrapper.impl.Block;
import gg.vape.wrapper.impl.EntityEnderPearl;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RayTraceResult;
import gg.vape.wrapper.impl.RayTraceResult_type;
import gg.vape.wrapper.impl.Vec3;
import gg.vape.wrapper.impl.WorldClient;
import org.jetbrains.annotations.Nullable;

public class AutoPearlTrackedPearl {
    private final EntityPlayer owner;
    private final EntityEnderPearl pearl;

    private AutoPearlTrackedPearl(EntityEnderPearl pearl, EntityPlayer owner) {
        this.pearl = pearl;
        this.owner = owner;
    }

    public EntityPlayer getOwner() {
        return this.owner;
    }

    public EntityEnderPearl getPearl() {
        return this.pearl;
    }


    public AutoPearlTrackedPearl(EntityEnderPearl pearl, EntityPlayer owner, AutoPearlRotationController ignoredController) {
        this(pearl, owner);
    }

    @Nullable
    public Vec3 predictLandingPosition() {
        WorldClient world = Minecraft.theWorld();
        if (world.isNull()) {
            return null;
        }
        double posX = this.pearl.z();
        double posY = this.pearl.N();
        double posZ = this.pearl.h();
        double motionX = this.pearl.t();
        double motionY = this.pearl.q();
        double motionZ = this.pearl.T();
        while (true) {
            Vec3 currentPosition = Vec3.create(posX, posY, posZ);
            Vec3 nextPosition = Vec3.create(posX + motionX, posY + motionY, posZ + motionZ);
            RayTraceResult rayTraceResult = world.K(currentPosition, nextPosition, false, this.pearl.isInstance(MappedClasses.F), false, this.pearl);
            posX += motionX;
            posY += motionY;
            posZ += motionZ;
            if (rayTraceResult.isNotNull() && !rayTraceResult.getTypeOfHit().equals(RayTraceResult_type.miss())) {
                boolean hitWater = false;
                Block block = rayTraceResult.Z$src$Lgg_vape_wrapper_impl_Block_$6x2c9a();
                if (block.isNotNull() && BlockUtil.p(block)) {
                    hitWater = true;
                }
                if (!hitWater) {
                    posX = rayTraceResult.getHitVec().getX();
                    posY = rayTraceResult.getHitVec().getY();
                    posZ = rayTraceResult.getHitVec().getZ();
                    return Vec3.create(posX, posY, posZ);
                }
            }
            if (posY < -128.0) break;
            boolean inWater = this.pearl.h$src$Z$ftwoya();
            double drag = inWater ? 0.8 : 0.99;
            motionX *= drag;
            motionY *= drag;
            motionZ *= drag;
            motionY -= 0.03;
        }
        return null;
    }
}

