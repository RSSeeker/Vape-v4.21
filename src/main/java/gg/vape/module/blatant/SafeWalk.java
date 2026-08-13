package gg.vape.module.blatant;

import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPostMove;
import gg.vape.event.impl.EventPreMove;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.value.BooleanValue;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.Minecraft;

public class SafeWalk
extends Mod {
    private boolean sneakCancelled;
    private static final long MODULE_ID = -5388886013077358019L;
    private final BooleanValue directionCheck = BooleanValue.create(this, "Direction Check", true, "Checks if you're walking forwards and it'll allow you to walk off the edge");


    @Override
    public boolean isBlatantMod() {
        return true;
    }

    @EventHandler
    public void onPostMove(EventPostMove eventPostMove) {
        EntityPlayerSP localPlayer = Minecraft.thePlayer();
        localPlayer.movementInput().setCancelled(this.sneakCancelled);
    }

    @EventHandler
    public void onPreMove(EventPreMove eventPreMove) {
        EntityPlayerSP localPlayer = Minecraft.thePlayer();
        this.sneakCancelled = localPlayer.movementInput().D$src$Z$v5d6e8();
        boolean shouldSafeWalk = true;
        if (this.directionCheck.getEffectiveValue().booleanValue() && !localPlayer.P() && localPlayer.F() > 0.0f && localPlayer.N$src$F$14ypudi() == 0.0f) {
            shouldSafeWalk = false;
        }
        if (shouldSafeWalk) {
            localPlayer.movementInput().setCancelled(true);
        }
    }

    public SafeWalk() {
        super("SafeWalk", (int)MODULE_ID, Category.WORLD, "Helps you from falling off the edge.");
        this.addValue(this.directionCheck);
    }
}

