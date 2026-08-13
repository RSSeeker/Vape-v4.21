package gg.vape.module.blatant;

import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPacketSend;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.unmap.ColorUtil;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.Minecraft;

public class PotionSaver
extends Mod {
    private boolean savingPotion;


    @Override
    public boolean isBlatantMod() {
        return true;
    }

    @EventHandler
    public void onPacketSend(EventPacketSend eventPacketSend) {
        EntityPlayerSP localPlayer = Minecraft.thePlayer();
        if (localPlayer.isNotNull() && !localPlayer.B$src$Ljava_util_Collection_$1uxz2f9().isEmpty() && !this.isMoving(localPlayer) && (localPlayer.b$src$Z$fqlxe4() || localPlayer.u$src$Z$g120nz())) {
            if (eventPacketSend.getPacket().isInstance(MappedClasses.qD)) {
                this.savingPotion = true;
                eventPacketSend.setCancelled(true);
            }
        } else {
            this.savingPotion = false;
        }
    }

    @Override
    public int getGuiColor() {
        return this.savingPotion ? -256 : ColorUtil.packGray(160);
    }

    private boolean isMoving(EntityPlayerSP player) {
        return player.t() != 0.0 || player.T() != 0.0;
    }

    public PotionSaver() {
        super("PotionSaver", -256, Category.OTHER, "Saves your potion effect(s) duration when standing still");
    }
}

