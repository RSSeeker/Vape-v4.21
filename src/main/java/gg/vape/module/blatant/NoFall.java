package gg.vape.module.blatant;

import gg.vape.Vape;
import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventMotion;
import gg.vape.event.impl.EventPostMotion;
import gg.vape.event.impl.EventPreMotion;
import gg.vape.input.KeyBindingHelper;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.unmap.ModeOption;
import gg.vape.unmap.ModeSelection;
import gg.vape.value.ModeValue;
import gg.vape.wrapper.impl.C03PacketPlayer;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.Minecraft;

public class NoFall
extends Mod {
    private final ModeValue mode;
    private static final long MODULE_ID = -4622334655389492929L;
    private final ModeOption antiCheatMode;
    private final ModeOption normalMode = new ModeOption("Normal");
    private float lastFallDistance;

    @EventHandler
    public void onMotionUpdate(EventPostMotion eventPostMotion) {
        EntityPlayerSP localPlayer = Minecraft.thePlayer();
        if (localPlayer.isNull() || localPlayer.getWorld().isNull() || localPlayer.M$src$Z$ff28xj() || localPlayer.C$src$Lgg_vape_wrapper_impl_ModelPlayer_$19uhx86().isCreativeMode() || localPlayer.C$src$Lgg_vape_wrapper_impl_ModelPlayer_$19uhx86().isFlying() || Vape.INSTANCE.getModManager().getState(Fly.class)) {
            return;
        }
        if (this.mode.getValue() == this.normalMode) {
            boolean shouldCancelFall = (double)localPlayer.M$src$F$ff28gb() > 2.224 && localPlayer.q() < 0.0;
            if (shouldCancelFall) {
                localPlayer.U(false);
            }
        }
    }

    @Override
    public boolean isBlatantMod() {
        return true;
    }


    public NoFall() {
        super("NoFall", (int)MODULE_ID, Category.OTHER, "Prevents taking fall damage.\nThis may not bypass AntiCheats.");
        this.antiCheatMode = new ModeOption("AntiCheat");
        this.mode = ModeValue.create((Object)this, "Mode", "NoFall method to prevent you from taking fall damage.\nNormal - Works on vanilla/some anti-cheats (Does not Bypass AntiCheat)\nAntiCheat - Works and bypasses on various anti-cheats", (ModeSelection)this.normalMode, this.normalMode, this.antiCheatMode);
        this.addValue(this.mode);
    }

    @Override
    public String getSimpleSuffix() {
        return this.mode.getDisplayValue();
    }

    @EventHandler
    public void onMotionUpdate(EventPreMotion eventPreMotion) {
        EntityPlayerSP localPlayer = Minecraft.thePlayer();
        if (localPlayer.isNull() || localPlayer.getWorld().isNull() || localPlayer.M$src$Z$ff28xj() || localPlayer.C$src$Lgg_vape_wrapper_impl_ModelPlayer_$19uhx86().isCreativeMode() || localPlayer.C$src$Lgg_vape_wrapper_impl_ModelPlayer_$19uhx86().isFlying() || Vape.INSTANCE.getModManager().getState(Fly.class)) {
            return;
        }
        if (this.mode.getValue() == this.normalMode) {
            boolean shouldCancelFall = (double)localPlayer.M$src$F$ff28gb() > 2.224 && localPlayer.q() < 0.0;
            if (shouldCancelFall) {
                EventMotion.setOnGround(true);
                localPlayer.L(1);
                if (Minecraft.gameSettings().O().isPressed()) {
                    KeyBindingHelper.setPressedAndTick(Minecraft.gameSettings().O(), false);
                }
            }
        }
        if (this.mode.getValue() == this.antiCheatMode) {
            if (this.lastFallDistance > localPlayer.M$src$F$ff28gb()) {
                this.lastFallDistance = 0.0f;
            }
            if ((double)localPlayer.M$src$F$ff28gb() > 2.124 && localPlayer.q() < 0.0 && localPlayer.M$src$F$ff28gb() >= 3.0f && localPlayer.M$src$F$ff28gb() - this.lastFallDistance > 3.0f) {
                this.lastFallDistance = localPlayer.M$src$F$ff28gb();
                localPlayer.sendQueue().addToSendQueue(C03PacketPlayer.create(true));
                localPlayer.sendQueue().addToSendQueue(C03PacketPlayer.create(false));
            }
        }
    }
}

