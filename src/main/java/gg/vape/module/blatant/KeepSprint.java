package gg.vape.module.blatant;

import gg.vape.Vape;
import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPostAttack;
import gg.vape.event.impl.EventPreAttack;
import gg.vape.event.impl.EventPrePlayerTick;
import gg.vape.event.impl.EventSetSprinting;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.blatant.Scaffold;
import gg.vape.utils.MathUtil;
import gg.vape.utils.TimerUtil;
import gg.vape.value.BooleanValue;
import gg.vape.value.NumberValue;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.Minecraft;

public class KeepSprint
extends Mod {
    private final NumberValue retainFactor = new NumberValue(this, "Retain factor", 0.95, 0.6, 1.0, "#.##", "");
    private final TimerUtil sprintResetTimer;
    private double preAttackMotionX;
    private boolean cancelNextSprintEnable;
    private boolean sprintResetPending;
    private final BooleanValue resetSprint = new BooleanValue((Object)this, "Reset sprint", false);
    private Scaffold scaffold;
    private double preAttackMotionZ;
    private static final long MODULE_ID = -249991328817855756L;

    @EventHandler
    public void onTick(EventPrePlayerTick event) {
        if (this.sprintResetPending) {
            event.getThePlayer().R(false);
            this.cancelNextSprintEnable = true;
            this.sprintResetPending = false;
        }
    }


    @Override
    public String getDetailedSuffix() {
        return this.retainFactor.getDisplayValue();
    }

    @EventHandler
    public void onSetSprinting(EventSetSprinting event) {
        if (this.cancelNextSprintEnable && event.isNewStateSprinting()) {
            event.setCancelled(true);
            this.cancelNextSprintEnable = false;
        }
    }

    private boolean isMovingForward(EntityPlayerSP player) {
        float forwardInput = player.F();
        if (forwardInput > 0.0f) {
            float yaw = player.J();
            float forwardX = -MathUtil.sin(yaw * (float)Math.PI / 180.0f);
            float forwardZ = MathUtil.cos(yaw * (float)Math.PI / 180.0f);
            double forwardMotion = player.t() * forwardX + player.T() * forwardZ;
            return forwardMotion > 0.0;
        }
        return false;
    }

    @Override
    public boolean isBlatantMod() {
        return true;
    }

    @EventHandler
    public void onPreAttack(EventPreAttack event) {
        EntityPlayerSP player = Minecraft.thePlayer();
        if (this.scaffold == null) {
            this.scaffold = Vape.INSTANCE.getModManager().getMod(Scaffold.class);
        }
        if (this.scaffold.isActivelyScaffolding()) {
            return;
        }
        this.preAttackMotionX = player.t();
        this.preAttackMotionZ = player.T();
    }

    public KeepSprint() {
        super("KeepSprint", (int)MODULE_ID, Category.OTHER, "Prevents you from losing sprint when attacking");
        this.sprintResetTimer = new TimerUtil();
        this.addValue(this.retainFactor, this.resetSprint);
    }

    @EventHandler
    public void onPostAttack(EventPostAttack event) {
        EntityPlayerSP player = Minecraft.thePlayer();
        if (this.scaffold == null) {
            this.scaffold = Vape.INSTANCE.getModManager().getMod(Scaffold.class);
        }
        if (this.scaffold.isActivelyScaffolding()) {
            return;
        }
        if (event.getThePlayer().F() <= 0.0f) {
            return;
        }
        if (!player.b$src$Z$fqlxe4() && !this.isMovingForward(player)) {
            return;
        }
        double vanillaAttackSlowdown = 0.6;
        if (player.t() == this.preAttackMotionX * vanillaAttackSlowdown
                && player.T() == this.preAttackMotionZ * vanillaAttackSlowdown && !player.r()) {
            double retainedMotionScale = (Double)this.retainFactor.getValue();
            double restoredMotionX = player.t() / vanillaAttackSlowdown * retainedMotionScale;
            double restoredMotionZ = player.T() / vanillaAttackSlowdown * retainedMotionScale;
            player.r(restoredMotionX);
            player.i(restoredMotionZ);
            if (this.resetSprint.getEffectiveValue()) {
                if (this.sprintResetTimer.hasTimeElapsed(500L)) {
                    this.sprintResetPending = true;
                    this.sprintResetTimer.reset();
                }
            } else {
                player.R(true);
            }
        }
    }
}

