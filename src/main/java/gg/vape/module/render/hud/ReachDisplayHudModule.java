package gg.vape.module.render.hud;

import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPostAttack;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.render.hud.HudModule;
import gg.vape.module.render.hud.HudModuleGroup;
import gg.vape.rotation.RotationManager;
import gg.vape.ui.click.frame.impl.hud.ReachDisplayHudFrame;
import gg.vape.wrapper.impl.AxisAlignedBB;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RayTraceResult;
import gg.vape.wrapper.impl.Vec3;

public class ReachDisplayHudModule
extends HudModule {
    private float savedPitch;
    private long lastAttackTime;
    private float savedYaw;
    private float lastReach;

    public float getLastReach() {
        if (System.currentTimeMillis() - this.lastAttackTime >= 5000L) {
            this.lastReach = 0.0f;
            this.lastAttackTime = 0L;
        }
        return this.lastReach;
    }

    private void restorePlayerRotation() {
        RotationManager rotationManager = RotationManager.INSTANCE;
        if (rotationManager.hasAdaptiveController()) {
            EntityLivingBase entityLivingBase = Minecraft.F();
            entityLivingBase.H(this.savedYaw);
            entityLivingBase.C(this.savedPitch);
        }
    }

    @EventHandler
    public void onPostAttack(EventPostAttack event) {
        if (event.getTarget().isInstance(MappedClasses.zm) && !event.getTarget().isInstance(MappedClasses.FT) && Minecraft.p$src$Lgg_vape_wrapper_impl_RayTraceResult_$5rw6n0().isNotNull()) {
            Entity entity = Minecraft.p$src$Lgg_vape_wrapper_impl_RayTraceResult_$5rw6n0().getEntity();
            if (entity.isNull()) {
                return;
            }
            EntityLivingBase player = Minecraft.F();
            double reachDistance = Minecraft.playerController().N();
            this.applyManagedRotation();
            Vec3 eyePosition = player.O(1.0f);
            Vec3 lookDirection = player.J(1.0f);
            this.restorePlayerRotation();
            Vec3 rayEnd = eyePosition.addVector(
                    lookDirection.getX() * reachDistance,
                    lookDirection.getY() * reachDistance,
                    lookDirection.getZ() * reachDistance);
            float collisionBorder = entity.b();
            AxisAlignedBB targetBounds = entity.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl()
                    .expand(collisionBorder, collisionBorder, collisionBorder);
            RayTraceResult intercept = targetBounds.calculateIntercept(eyePosition, rayEnd);
            if (intercept.isNull()) {
                return;
            }
            double attackDistance = eyePosition.distanceTo(intercept.getHitVec());
            this.lastReach = (float)attackDistance;
            this.lastAttackTime = System.currentTimeMillis();
        }
    }

    public ReachDisplayHudModule() {
        super("Reach Display", HudModuleGroup.HUD, "reach_display", ReachDisplayHudFrame.class);
        this.setSuffix("Shows how far away your last attack was");
    }


    private void applyManagedRotation() {
        RotationManager rotationManager = RotationManager.INSTANCE;
        if (rotationManager.hasAdaptiveController()) {
            EntityLivingBase entityLivingBase = Minecraft.F();
            this.savedYaw = entityLivingBase.J();
            this.savedPitch = entityLivingBase.V();
            entityLivingBase.H(rotationManager.getManagedYaw());
            entityLivingBase.C(rotationManager.getManagedPitch());
        }
    }
}
