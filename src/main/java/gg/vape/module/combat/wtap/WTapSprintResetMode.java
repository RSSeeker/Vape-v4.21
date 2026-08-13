package gg.vape.module.combat.wtap;

import gg.vape.config.ClientSettings;
import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventClickMouse;
import gg.vape.event.impl.EventPacketReceive;
import gg.vape.event.impl.EventPlayerUseItem;
import gg.vape.event.impl.EventPreTick;
import gg.vape.event.impl.EventRightClickMouse;
import gg.vape.event.impl.EventSendClickBlockToController;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Mod;
import gg.vape.module.SubModule;
import gg.vape.combat.AttackPacketTimingTracker;
import gg.vape.module.combat.HitSelect;
import gg.vape.rotation.RotationManager;
import gg.vape.unmap.ModeOption;
import gg.vape.value.ModeValue;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.Packet;
import gg.vape.wrapper.impl.RayTraceResult;
import gg.vape.wrapper.impl.SPacketEntityVelocity;

public class WTapSprintResetMode
extends SubModule<HitSelect> {
    private final ModeValue preference;
    private final ModeOption criticalHitsOption;
    private int velocityTicks = 0;
    private boolean pendingTimestampUpdate;
    private static final long VELOCITY_RESET_TICKS = 988881679777005575L;
    private final ModeOption kbReductionOption = new ModeOption("KB reduction");
    private long lastResetTime;
    private boolean cancelUse = true;

    public WTapSprintResetMode(Mod mod, String string) {
        super(mod, string);
        this.criticalHitsOption = new ModeOption("Critical hits");
        this.preference = ModeValue.create((Object)this, "Preference", this.kbReductionOption, this.kbReductionOption, this.criticalHitsOption);
        this.preference.setDescription("KB reduction: Favors knockback reduction\nCritical hits: Favors critical hit frequency");
        this.addValue(this.preference);
    }

    @EventHandler
    public void onPlayerUseItem(EventPlayerUseItem eventPlayerUseItem) {
        if (this.cancelUse) {
            eventPlayerUseItem.setCancelled(true);
        }
    }

    private boolean isMovingTowardTarget(Entity entity) {
        double deltaX = entity.z() - Minecraft.thePlayer().z();
        double deltaZ = entity.h() - Minecraft.thePlayer().h();
        return deltaX < 0.0 == Minecraft.thePlayer().t() < 0.0
                && deltaZ < 0.0 == Minecraft.thePlayer().T() < 0.0;
    }

    @EventHandler
    public void onRightClickMouse(EventRightClickMouse eventRightClickMouse) {
        if (this.cancelUse) {
            eventRightClickMouse.setCancelled(true);
        }
    }

    @EventHandler
    public void onPacketReceive(EventPacketReceive eventPacketReceive) {
        SPacketEntityVelocity velocityPacket;
        if (eventPacketReceive.getPacketInstance() == null) {
            return;
        }
        if (Minecraft.thePlayer().isNull()) {
            return;
        }
        Packet packet = eventPacketReceive.getPacket();
        boolean velocityReceived = false;
        if (packet.isInstance(MappedClasses.qe)) {
            velocityReceived = true;
        } else if (packet.isInstance(MappedClasses.YX)
                && (velocityPacket = new SPacketEntityVelocity(packet)).getEntityId() == Minecraft.thePlayer().S()) {
            velocityReceived = true;
        }
        if (velocityReceived) {
            this.velocityTicks = (int)VELOCITY_RESET_TICKS;
        }
    }

    private boolean prefersKnockbackReduction() {
        return this.preference.getValue() == this.kbReductionOption;
    }

    @EventHandler
    public void onClickMouse(EventClickMouse eventClickMouse) {
        RayTraceResult rayTrace;
        if (!((HitSelect)this.getParent()).shouldTrigger()) {
            return;
        }
        boolean airborneAfterVelocity = false;
        if (this.velocityTicks > 0) {
            --this.velocityTicks;
            if (eventClickMouse.getThePlayer().b$src$Z$fqlxe4()) {
                this.velocityTicks = 0;
            }
            if (this.prefersKnockbackReduction()) {
                return;
            }
            if (eventClickMouse.getThePlayer().q() > 0.0) {
                airborneAfterVelocity = true;
            } else if (!eventClickMouse.getThePlayer().b$src$Z$fqlxe4()) {
                return;
            }
        }
        if ((rayTrace = RotationManager.INSTANCE.getExtendedReachRayTrace()).isNotNull()
                && rayTrace.getEntity().isInstance(MappedClasses.zm)) {
            EntityLivingBase target = new EntityLivingBase(rayTrace.getEntity());
            if (!this.isMovingTowardTarget(target)) {
                return;
            }
            if (!airborneAfterVelocity) {
                AttackPacketTimingTracker timingTracker = AttackPacketTimingTracker.INSTANCE;
                int expectedHurtTime = timingTracker.getExpectedHurtTimeTicks();
                int upperHurtTime = expectedHurtTime + 1;
                if (target.c$src$I$15a9iwo() <= expectedHurtTime) {
                    if (System.currentTimeMillis() - this.lastResetTime >= timingTracker.getAverageHitDelay() * 2L) {
                        this.pendingTimestampUpdate = true;
                        return;
                    }
                }
                if (target.c$src$I$15a9iwo() > expectedHurtTime
                        && target.c$src$I$15a9iwo() <= upperHurtTime) {
                    return;
                }
            }
            eventClickMouse.setCancelled(true);
            this.cancelUse = true;
            ClientSettings.applyAttackEffects(target);
        }
    }

    @EventHandler
    public void onTick(EventPreTick eventPreTick) {
        if (this.pendingTimestampUpdate) {
            this.lastResetTime = System.currentTimeMillis();
            this.pendingTimestampUpdate = false;
        }
        this.cancelUse = false;
    }

    @EventHandler
    public void onSendClickBlockToController(EventSendClickBlockToController eventSendClickBlockToController) {
        if (this.cancelUse) {
            eventSendClickBlockToController.setCancelled(true);
        }
    }

}
