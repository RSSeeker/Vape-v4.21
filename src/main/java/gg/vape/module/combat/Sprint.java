package gg.vape.module.combat;

import gg.vape.Vape;
import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPreEntityUpdate;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.blatant.Scaffold;
import gg.vape.module.control.SharedModuleControlClaims;
import gg.vape.value.BooleanValue;
import gg.vape.wrapper.impl.KeyBinding;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.PotionRegistry;

public class Sprint
extends Mod {
    private final BooleanValue cancelInvis = BooleanValue.create(this, "Cancel Invis", false, "Does not sprint when you are invisible.\nUseful to prevent sprint particles.");
    private Scaffold scaffold;
    private static final long MODULE_ID = -2755412642150416500L;

    @Override
    public void onDisable() {
        KeyBinding.setKeyBindState(Minecraft.gameSettings().r(), false);
    }


    @EventHandler
    public void onPreEntityUpdate(EventPreEntityUpdate event) {
        if (this.scaffold == null) {
            this.scaffold = Vape.INSTANCE.getModManager().getMod(Scaffold.class);
        }
        if (!Minecraft.currentScreen().isNull()) {
            return;
        }
        if (SharedModuleControlClaims.movementInput.isLocked()) {
            return;
        }
        if (this.cancelInvis.getEffectiveValue() && Minecraft.thePlayer().i(PotionRegistry.R)
                && !Minecraft.thePlayer().C$src$Lgg_vape_wrapper_impl_ModelPlayer_$19uhx86().H()) {
            return;
        }
        KeyBinding sprintKey = Minecraft.gameSettings().r();
        boolean shouldStartSprinting = !this.scaffold.isActivelyScaffolding()
                && !Minecraft.thePlayer().B$src$Z$f90iek() && !Minecraft.thePlayer().r();
        if (!sprintKey.isKeyDown() && shouldStartSprinting) {
            KeyBinding.setKeyBindState(sprintKey, true);
        }
    }

    public Sprint() {
        super("Sprint", (int)MODULE_ID, Category.COMBAT, "Sets your sprinting to true.");
        this.addValue(this.cancelInvis);
    }
}

