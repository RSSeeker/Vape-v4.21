package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MRenderManager;
import gg.vape.wrapper.Wrapper;

public class RenderManager
extends Wrapper {
    private static double interpolatedRenderPosX;
    private static double interpolatedRenderPosY;
    private static double interpolatedRenderPosZ;

    public double getRenderPosZ() {
        if (ForgeVersion.MC_1_16_5.d()) {
            return this.getActiveRenderInfo().o().getZ();
        }
        return MRenderManager.t(RenderManager.vapeInstance.getMappings().CA, this.I);
    }

    public static RenderManager getInstance() {
        return new RenderManager(MRenderManager.T(RenderManager.vapeInstance.getMappings().CA));
    }

    public Quaternion getCameraOrientation() {
        if (ForgeVersion.MC_1_21_10.d()) {
            return this.getActiveRenderInfo().G();
        }
        return new Quaternion(MRenderManager.q(RenderManager.vapeInstance.getMappings().CA, this.I));
    }

    public static double getInterpolatedRenderPosX() {
        return interpolatedRenderPosX;
    }

    public double getRenderPosY() {
        if (ForgeVersion.MC_1_16_5.d()) {
            return this.getActiveRenderInfo().o().getY();
        }
        return MRenderManager.N(RenderManager.vapeInstance.getMappings().CA, this.I);
    }

    public float getPlayerViewX() {
        if (ForgeVersion.MC_1_16_5.d()) {
            return this.getActiveRenderInfo().x();
        }
        return MRenderManager.o(RenderManager.vapeInstance.getMappings().CA, this.I);
    }

    public double getRenderPosX() {
        if (ForgeVersion.MC_1_16_5.d()) {
            return this.getActiveRenderInfo().o().getX();
        }
        return MRenderManager.M(RenderManager.vapeInstance.getMappings().CA, this.I);
    }

    public RenderManager(Object object) {
        super(object);
    }

    public float getPlayerViewY() {
        if (ForgeVersion.MC_1_16_5.d()) {
            return this.getActiveRenderInfo().Z();
        }
        return MRenderManager.r(RenderManager.vapeInstance.getMappings().CA, this.I);
    }

    public ActiveRenderInfo getActiveRenderInfo() {
        return new ActiveRenderInfo(MRenderManager.e(RenderManager.vapeInstance.getMappings().CA, this.I));
    }


    public static double getInterpolatedRenderPosY() {
        return interpolatedRenderPosY;
    }

    public Render getEntityRenderObject(Entity entity) {
        return new Render(MRenderManager.O(RenderManager.vapeInstance.getMappings().CA, this.I, entity.getObject()));
    }

    public static void updateInterpolatedRenderPosition(float partialTicks) {
        EntityLivingBase entityLivingBase = Minecraft.F();
        double previousX = entityLivingBase.M();
        double previousY = entityLivingBase.W();
        double previousZ = entityLivingBase.m$src$D$fwnne5();
        interpolatedRenderPosX = previousX + (entityLivingBase.z() - previousX) * (double)partialTicks;
        interpolatedRenderPosY = previousY + (entityLivingBase.N() - previousY) * (double)partialTicks;
        if (ForgeVersion.MC_1_14_4.d()) {
            interpolatedRenderPosY += (double)entityLivingBase.X();
            interpolatedRenderPosY += Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf().l().o().getY() - interpolatedRenderPosY;
        }
        interpolatedRenderPosZ = previousZ + (entityLivingBase.h() - previousZ) * (double)partialTicks;
    }

    public static double getInterpolatedRenderPosZ() {
        return interpolatedRenderPosZ;
    }
}

