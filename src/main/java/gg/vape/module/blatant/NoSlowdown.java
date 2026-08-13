package gg.vape.module.blatant;

import gg.vape.Vape;
import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPacketReceive;
import gg.vape.event.impl.EventPreMotion;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.combat.Sprint;
import gg.vape.unmap.ItemLimitData;
import gg.vape.value.BooleanValue;
import gg.vape.value.LimitValue;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.Packet;
import gg.vape.wrapper.impl.SPacketEntityVelocity;

public class NoSlowdown
extends Mod {
    private boolean pendingVelocity;
    private final BooleanValue limitItems = BooleanValue.create(this, "Limit Items", false, "Limits to whitelisted items only.");
    private final LimitValue whitelist = LimitValue.create(this, "noslowdown-whitelist", "Whitelisted", LimitValue.ALLOW_LIST_COLOR, new ItemLimitData("swords"));
    private static final long MOD_ID = -7214429765927550220L;

    @Override
    public boolean isBlatantMod() {
        return true;
    }

    public NoSlowdown() {
        super("NoSlowdown", (int)MOD_ID, Category.HIDDEN, "Prevents slowing down when\nblocking or using items.");
        this.limitItems.addDependentValues(this.whitelist);
        this.addValue(this.limitItems, this.whitelist);
    }

    @EventHandler
    public void onPacketReceive(EventPacketReceive eventPacketReceive) {
        Packet packet = eventPacketReceive.getPacket();
        if (packet.isInstance(MappedClasses.qe)) {
            this.pendingVelocity = true;
        }
        if (packet.isInstance(MappedClasses.YX)) {
            SPacketEntityVelocity velocityPacket = new SPacketEntityVelocity(packet);
            EntityPlayerSP localPlayer = Minecraft.thePlayer();
            if (localPlayer.isNotNull() && velocityPacket.getEntityId() == localPlayer.S()) {
                this.pendingVelocity = true;
            }
        }
    }

    @EventHandler
    public void onMotionUpdate(EventPreMotion eventPreMotion) {
        EntityPlayerSP localPlayer = Minecraft.thePlayer();
        if (this.pendingVelocity) {
            if (localPlayer.b$src$Z$fqlxe4()) {
                this.pendingVelocity = false;
            }
            return;
        }
        if (localPlayer.h$src$Z$ftwoya()) {
            return;
        }
        double forwardInput = localPlayer.movementInput().D();
        double strafeInput = localPlayer.movementInput().T();
        float yaw = localPlayer.J();
        if (!(!localPlayer.l$src$Z$1io4duf() || this.limitItems.getEffectiveValue().booleanValue() && !this.whitelist.matches(localPlayer.getHeldItemHand()) || Math.abs(strafeInput) != (double)0.2f && Math.abs(forwardInput) != (double)0.2f)) {
            if (Vape.INSTANCE.getModManager().getState(Sprint.class)) {
                localPlayer.R(true);
            }
            if (Math.abs(strafeInput) == (double)0.2f) {
                if (strafeInput > 0.0) {
                    localPlayer.movementInput().M(1.0f);
                } else if (strafeInput < 0.0) {
                    localPlayer.movementInput().M(-1.0f);
                }
            }
            if (Math.abs(forwardInput) == (double)0.2f) {
                if (forwardInput > 0.0) {
                    localPlayer.movementInput().B(1.0f);
                } else if (forwardInput < 0.0) {
                    localPlayer.movementInput().B(-1.0f);
                }
            }
            if (Math.abs(localPlayer.movementInput().T()) != 1.0f && forwardInput > 0.0) {
                forwardInput *= localPlayer.B$src$Z$f90iek() ? (double)1.3f : 1.0;
            }
            strafeInput *= forwardInput != 0.0 ? 0.5 : 0.85;
            localPlayer.r(forwardInput * Math.cos(Math.toRadians(yaw + 90.0f)) + strafeInput * Math.sin(Math.toRadians(yaw + 90.0f)));
            localPlayer.i(forwardInput * Math.sin(Math.toRadians(yaw + 90.0f)) - strafeInput * Math.cos(Math.toRadians(yaw + 90.0f)));
        }
    }

}

