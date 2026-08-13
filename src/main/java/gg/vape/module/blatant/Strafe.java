package gg.vape.module.blatant;

import gg.vape.Vape;
import gg.vape.config.ClientSettings;
import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPrePlayerTick;
import gg.vape.input.KeyBindingHelper;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.combat.AimAssist;
import gg.vape.utils.RotationUtil;
import gg.vape.value.NumberValue;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.GameSettings;
import gg.vape.wrapper.impl.KeyBinding;
import gg.vape.wrapper.impl.Minecraft;

public class Strafe
extends Mod {
    private final NumberValue selfMinAngle;
    private boolean sprintReset;
    private final NumberValue distance = NumberValue.create((Object)this, "Distance", "#.#", "", 0.1, 3.3, 6.0, 0.1);
    private AimAssist aimAssist;
    private final NumberValue targetMinAngle;
    private final NumberValue speed = NumberValue.create(this, "Speed", "#.#", "", 0.1, 0.5, 1.0);

    public Strafe() {
        super("Strafe", -256, Category.OTHER);
        this.targetMinAngle = NumberValue.create(this, "Target minimum angle", "#", "", 1.0, 120.0, 360.0);
        this.selfMinAngle = NumberValue.create(this, "Your minimum angle", "#", "", 1.0, 90.0, 360.0);
        this.addValue(this.distance, this.speed, this.targetMinAngle, this.selfMinAngle);
        this.v(10L, true);
    }

    private void initAimAssist() {
        this.aimAssist = Vape.INSTANCE.getModManager().getMod(AimAssist.class);
    }

    private void applyStrafe() {
        double targetDistance;
        if (Minecraft.a_pt_1_w().isNotNull()) {
            return;
        }
        EntityPlayerSP player = Minecraft.a_xH_J();
        GameSettings settings = Minecraft.a_w3_0_S();
        KeyBinding sprintKey = settings.s();
        boolean physicallyDown = ClientSettings.isPhysicalKeyDown(sprintKey);
        KeyBindingHelper.setPressedAndTick(Minecraft.a_w3_0_S().s(), physicallyDown);
        double distanceValue = (Double)this.distance.java_lang_Object_K();
        double speedValue = (Double)this.speed.java_lang_Object_K() / 5.0;
        speedValue *= 0.1;
        boolean hasTarget = this.aimAssist.boolean_r() && this.aimAssist.getCurrentTarget() != null;
        EntityLivingBase target = new EntityLivingBase(this.aimAssist.getCurrentTarget());
        if (target.isNull()) {
            return;
        }
        boolean targetAngle = RotationUtil.g(player, target, (Double)this.targetMinAngle.java_lang_Object_K() / 2.0);
        boolean selfAngle = RotationUtil.g(target, player, (Double)this.selfMinAngle.java_lang_Object_K() / 2.0);
        if (hasTarget && targetAngle && selfAngle && (targetDistance = (double)player.getDistanceToEntity(target)) < distanceValue && !player.boolean_h() && !player.boolean_r() && !player.boolean_S() && player.boolean_b() && !player.boolean_o()) {
            double targetX = target.double_z();
            double targetZ = target.double_h();
            if (player.double_z() - targetX > 0.5) {
                player.r(player.double_t() + speedValue);
            }
            if (player.double_z() - targetX < 0.5) {
                player.r(player.double_t() - speedValue);
            }
            if (player.double_h() - targetZ > 0.5) {
                player.i(player.double_T() + speedValue);
            }
            if (player.double_h() - targetZ < 0.5) {
                player.i(player.double_T() - speedValue);
            }
            this.sprintReset = true;
        }
    }

    @EventHandler
    public void onTick(EventPrePlayerTick event) {
        EntityPlayerSP player = Minecraft.a_xH_J();
        if (this.sprintReset) {
            player.R(false);
            this.sprintReset = false;
        }
    }

    @Override
    public void onFinishModuleInitialization() {
        this.initAimAssist();
    }

    @Override
    public void onScheduledAction() {
        this.applyStrafe();
    }
}

