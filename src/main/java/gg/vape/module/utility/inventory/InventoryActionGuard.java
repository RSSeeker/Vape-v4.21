package gg.vape.module.utility.inventory;

import gg.vape.utils.RotationUtil;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RayTraceResult;
import gg.vape.wrapper.impl.World;

public class InventoryActionGuard {
    protected int remainingCooldownTicks;
    protected final int recentActionWindow = 10;
    protected World trackedWorld;
    protected boolean blocked;
    protected final int cooldownTicks;
    protected int stationaryTicks;
    protected double previousHealth;

    public void activateBlock() {
        this.blocked = true;
        this.remainingCooldownTicks = this.cooldownTicks;
    }

    public InventoryActionGuard(int cooldownTicks) {
        this.cooldownTicks = cooldownTicks;
    }

    public void reset() {
        this.blocked = false;
        this.previousHealth = -999.0;
        this.trackedWorld = Minecraft.theWorld();
        this.stationaryTicks = 0;
        this.remainingCooldownTicks = 0;
    }

    public boolean isBlocked() {
        return this.blocked;
    }

    public void update(EntityLivingBase entity) {
        double health = entity.w$src$F$15l9epb();
        World world = entity.getWorld();
        if (world.isNull() || this.trackedWorld != null && this.trackedWorld.isNotNull() && !world.equals(this.trackedWorld)) {
            this.reset();
            return;
        }
        if (this.blocked) {
            if (this.remainingCooldownTicks > 0) {
                --this.remainingCooldownTicks;
            } else {
                boolean stationary = RotationUtil.d(entity);
                if (stationary) {
                    ++this.stationaryTicks;
                    if (this.stationaryTicks >= 5) {
                        this.reset();
                        return;
                    }
                    if (!RotationUtil.H(entity) && !RotationUtil.F(entity)) {
                        this.reset();
                        return;
                    }
                    this.activateBlock();
                } else {
                    this.stationaryTicks = 0;
                    if (RotationUtil.D(entity, this.recentActionWindow) == 0) {
                        this.reset();
                        return;
                    }
                }
            }
        }
        boolean tookDamage = health < this.previousHealth || entity.V$src$I$fk0dv5() == 20;
        RayTraceResult rayTrace = Minecraft.p$src$Lgg_vape_wrapper_impl_RayTraceResult_$5rw6n0();
        boolean targetingEntity = rayTrace.isNotNull() && rayTrace.getEntity().isNotNull() && entity.Y$src$Z$154rldp();
        if (tookDamage || targetingEntity) {
            this.activateBlock();
        }
        this.trackedWorld = world;
        this.previousHealth = health;
    }

}

