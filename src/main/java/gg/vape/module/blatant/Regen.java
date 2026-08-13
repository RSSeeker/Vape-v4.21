package gg.vape.module.blatant;

import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPrePlayerTick;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.value.NumberValue;
import gg.vape.wrapper.impl.C03PacketPlayer;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.Minecraft;

public class Regen
extends Mod {
    private static final long MOD_ID = 7642067290108205375L;
    private final NumberValue healthThreshold = NumberValue.create(this, "Health", "#.#", "", 0.5, 8.0, 10.0, 0.5, "Minimum health to regen at\nMay not bypass Anti-Cheats");

    @Override
    public boolean isBlatantMod() {
        return true;
    }

    @EventHandler
    public void onTick(EventPrePlayerTick eventPrePlayerTick) {
        EntityPlayerSP entityPlayerSP = Minecraft.thePlayer();
        if ((double)entityPlayerSP.w$src$F$15l9epb() < (Double)this.healthThreshold.getValue() * 2.0 && entityPlayerSP.Y$src$Lgg_vape_wrapper_impl_FoodStats_$fakh1z().getFoodLevel() > 16 && !entityPlayerSP.l$src$Z$1io4duf() && entityPlayerSP.u$src$Z$g120nz() && entityPlayerSP.b$src$Z$fqlxe4() && !Minecraft.gameSettings().O().u() && !entityPlayerSP.h$src$Z$ftwoya() && entityPlayerSP.l() % 5 == 1 && !entityPlayerSP.M$src$Z$ff28xj()) {
            C03PacketPlayer c03PacketPlayer = C03PacketPlayer.create(false);
            for (int i = 0; i < 40; ++i) {
                entityPlayerSP.sendQueue().addToSendQueue(c03PacketPlayer);
            }
        }
    }

    public Regen() {
        super("Regen", (int)MOD_ID, Category.OTHER, "Regenerates health quicker.");
    }

}

