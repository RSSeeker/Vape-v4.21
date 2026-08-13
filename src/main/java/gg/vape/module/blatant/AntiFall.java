package gg.vape.module.blatant;

import gg.vape.Vape;
import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPacketReceive;
import gg.vape.event.impl.EventPreMotion;
import gg.vape.manager.ModManager;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.render.Freecam;
import gg.vape.utils.TimerUtil;
import gg.vape.value.BooleanValue;
import gg.vape.value.NumberValue;
import gg.vape.wrapper.impl.Block;
import gg.vape.wrapper.impl.Blocks;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.MovementInput;
import gg.vape.wrapper.impl.World;

public class AntiFall
extends Mod {
    private final BooleanValue ignoreWhileSpeedEnabled = BooleanValue.create(this, "Speed Check", false, "Ignore falling when Speed is enabled.");
    private boolean lagbackPending;
    private final TimerUtil lagbackTimer;
    private final NumberValue fallDistance = NumberValue.createWithDescription(this, "Fall Dist", "#.#", "m", 0.1, 2.0, 5.0, "The amount of blocks to fall before attempting to lag back.");

    public AntiFall() {
        super("AntiFall", 16028225, Category.OTHER, "Helps you with your Parkinson's\nPrevents you from falling into the void.");
        this.lagbackTimer = new TimerUtil();
        this.addValue(this.ignoreWhileSpeedEnabled, this.fallDistance);
    }

    private boolean hasBlockBelow() {
        EntityPlayerSP localPlayer = Minecraft.a_xH_J();
        World world = localPlayer.gg_vape_wrapper_impl_World_Z();
        for (double y = localPlayer.double_N() - 1.0; y > 0.0; y -= 1.0) {
            Block block = world.getBlock(localPlayer.double_z(), y, localPlayer.double_h());
            if (block.isNull() || block.H().isInstance(Blocks.air().H().getObject().getClass())) continue;
            return true;
        }
        return false;
    }

    @EventHandler
    public void onPacketReceive(EventPacketReceive eventPacketReceive) {
        EntityPlayerSP localPlayer = Minecraft.a_xH_J();
        if (eventPacketReceive.getPacket().isNull() || localPlayer.isNull() || localPlayer.gg_vape_wrapper_impl_World_Z().isNull()) {
            return;
        }
        if (eventPacketReceive.getPacket().isInstance(MappedClasses.zw)) {
            localPlayer.U(0.0f);
            localPlayer.r(0.0);
            localPlayer.i(0.0);
            this.lagbackPending = false;
            this.lagbackTimer.reset();
        }
    }

    @Override
    public boolean isBlatantMod() {
        return true;
    }

    @EventHandler
    public void onMotionUpdate(EventPreMotion eventPreMotion) {
        EntityPlayerSP localPlayer = Minecraft.a_xH_J();
        World world = localPlayer.gg_vape_wrapper_impl_World_Z();
        ModManager modManager = Vape.INSTANCE.getModManager();
        if (localPlayer.isNull() || world.isNull() || localPlayer.boolean_M() || localPlayer.boolean_d() || localPlayer.a_xf_0_C().isCreativeMode() || localPlayer.a_xf_0_C().isFlying() || modManager.getState(Freecam.class) || modManager.getState(Fly.class) || this.ignoreWhileSpeedEnabled.getEffectiveValueCompat().booleanValue() && modManager.getState(Speed.class)) {
            return;
        }
        if (!this.lagbackPending && this.hasBlockBelow()) {
            return;
        }
        if (this.lagbackPending && this.lagbackTimer.hasTimeElapsed(250L) || localPlayer.boolean_u()) {
            this.lagbackPending = false;
            this.lagbackTimer.reset();
            return;
        }
        double triggerDistance = (Double)this.fallDistance.java_lang_Object_K();
        if ((double)localPlayer.float_M() >= triggerDistance && !modManager.getMod(Fly.class).boolean_r()) {
            Block blockBelow = world.getBlock(localPlayer.double_z(), localPlayer.double_N() - 1.0, localPlayer.double_h());
            boolean overAir = blockBelow.isNull() || blockBelow.H().isInstance(Blocks.air().H().getObject().getClass());
            if (overAir) {
                if (!this.lagbackPending) {
                    this.lagbackPending = true;
                    this.lagbackTimer.reset();
                } else {
                    MovementInput movementInput = localPlayer.a_jw_2_I();
                    movementInput.B(0.0f);
                    movementInput.M(0.0f);
                    localPlayer.r(0.0);
                    localPlayer.i(0.0);
                    localPlayer.B(localPlayer.double_z(), localPlayer.double_N() + (double)localPlayer.float_M(), localPlayer.double_h());
                    localPlayer.U(0.0f);
                }
            }
        }
    }
}
