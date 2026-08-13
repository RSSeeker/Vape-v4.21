package gg.vape.module.combat;

import gg.vape.event.EventHandler;
import gg.vape.event.EventPriority;
import gg.vape.event.impl.EventPacketReceive;
import gg.vape.event.impl.EventPreTick;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.combat.AttackPacketTimingTracker;
import gg.vape.module.control.SecondaryActionControlClaim;
import gg.vape.module.control.SharedModuleControlClaims;
import gg.vape.utils.MathUtil;
import gg.vape.utils.RotationUtil;
import gg.vape.utils.network.PacketDispatchGuard;
import gg.vape.value.BooleanValue;
import gg.vape.value.NumberValue;
import gg.vape.value.RandomValue;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.NetHandlerPlayClientImpl;
import gg.vape.wrapper.impl.Packet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class KnockbackDelay
extends Mod {
    private static final long MODULE_ID = 6243151782707490976L;
    private long delayMillis;
    private long releaseTime;
    private final RandomValue airDelay;
    private final Queue<EventPacketReceive> heldPackets;
    private final NumberValue chance;
    private final RandomValue groundDelay;
    private long lastHitTime;
    private Entity target;
    private final PacketDispatchGuard dispatchGuard = PacketDispatchGuard.b;
    private final BooleanValue waterCheck;
    private int hitCount = 0;
    private SecondaryActionControlClaim controlClaim;

    private boolean rollChance() {
        int roll = MathUtil.randomExclusiveUpper(new Random(), 0, 100);
        return (double)roll >= 100.0 - (Double)this.chance.getValue();
    }

    public KnockbackDelay() {
        super("KnockbackDelay", (int)MODULE_ID, Category.UTILITY, "Delays incoming knockback packets");
        this.heldPackets = new LinkedList<EventPacketReceive>();
        this.chance = NumberValue.createWithDescription(this, "Chance", "#", "%", 0.0, 40.0, 100.0, "Chance of delaying knockback");
        this.airDelay = RandomValue.create(this, "Air delay", "#", "", 0.0, 50.0, 100.0, 500.0);
        this.groundDelay = RandomValue.create(this, "Ground delay", "#", "", 0.0, 200.0, 250.0, 500.0);
        this.waterCheck = BooleanValue.create(this, "Water check", false, "Won't delay knockback if in water");
        this.controlClaim = SharedModuleControlClaims.secondaryAction;
        this.addValue(this.chance, this.airDelay, this.groundDelay, this.waterCheck);
        this.chance.setMaximumFractionDigits(0);
        this.controlClaim.setPriority(this, 5);
    }

    @Override
    public String getDetailedSuffix() {
        if (!this.heldPackets.isEmpty()) {
            return "\u00a7cHolding";
        }
        return this.groundDelay.getDisplayValue() + "ms";
    }


    private boolean isInWater() {
        EntityPlayerSP player = Minecraft.thePlayer();
        if (player.isNull()) {
            return true;
        }
        return this.waterCheck.getEffectiveValue() && player.h$src$Z$ftwoya();
    }

    private boolean shouldDelay() {
        return !this.isInWater() && this.rollChance();
    }

    public EntityLivingBase findTargetInRange(double fov, double range) {
        EntityLivingBase target = RotationUtil.u(range, fov / 2.0);
        if (target == null) {
            return null;
        }
        if (RotationUtil.o(Minecraft.thePlayer(), target, range, 90.0, true)) {
            return target;
        }
        return null;
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onPacketReceive(EventPacketReceive event) {
        if (event.getWorld().isNotNull() && event.getThePlayer().isNotNull()) {
            Packet packet = event.getPacket();
            if (this.dispatchGuard.R(packet)) {
                return;
            }
            boolean isVelocity = this.containsPlayerVelocity(packet, event.getThePlayer());
            if (isVelocity) {
                if (this.target == null || !this.shouldDelay()) {
                    isVelocity = false;
                } else {
                    this.lastHitTime = System.currentTimeMillis();
                }
            }
            if (isVelocity && this.heldPackets.isEmpty()) {
                this.delayMillis = this.hitCount < 3 ? (long)this.airDelay.getRandomValue() : (long)this.groundDelay.getRandomValue();
                if (this.delayMillis > 0L) {
                    this.releaseTime = System.currentTimeMillis() + this.delayMillis;
                    this.heldPackets.add(event);
                    event.setCancelled(true);
                    this.controlClaim.markClaimed();
                }
            } else if (!this.heldPackets.isEmpty()) {
                this.heldPackets.add(event);
                event.setCancelled(true);
                this.controlClaim.markClaimed();
            }
            if (this.heldPackets.isEmpty()) {
                this.dispatchGuard.J(packet);
                this.controlClaim.clearClaimed();
            }
        }
    }

    private void flushHeldPackets() {
        if (this.heldPackets.isEmpty()) {
            return;
        }
        if (System.currentTimeMillis() >= this.releaseTime) {
            NetHandlerPlayClientImpl networkHandler = Minecraft.thePlayer().sendQueue();
            for (EventPacketReceive heldEvent : this.heldPackets) {
                this.dispatchGuard.l(heldEvent.getPacket(), networkHandler);
            }
            this.heldPackets.clear();
        }
    }

    private boolean containsPlayerVelocity(Packet packet, EntityPlayerSP player) {
        boolean[] found = new boolean[]{false};
        Packet.n(packet, resolvedPacket -> this.checkVelocityPacket(found, player, resolvedPacket));
        return found[0];
    }

    private void flushOnTick() {
        this.flushHeldPackets();
    }

    @EventHandler
    public void onTick(EventPreTick event) {
        if (Minecraft.thePlayer().isNull()) {
            return;
        }
        PacketDispatchGuard.B(this::flushOnTick);
        if (event.getWorld().isNotNull() && event.getThePlayer().isNotNull()) {
            this.target = this.findTargetInRange(90.0, 5.0);
            this.hitCount = event.getThePlayer().b$src$Z$fqlxe4() ? ++this.hitCount : 0;
        }
    }

    private void checkVelocityPacket(boolean[] found, EntityPlayerSP player, Packet packet) {
        Entity affectedEntity = AttackPacketTimingTracker.getVelocityPacketEntity(packet);
        if (!found[0] && affectedEntity != null && affectedEntity.equals(player)
                && System.currentTimeMillis() - this.lastHitTime > 475L) {
            found[0] = true;
        }
    }
}
