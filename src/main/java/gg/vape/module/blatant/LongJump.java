package gg.vape.module.blatant;

import gg.vape.Vape;
import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPreMotion;
import gg.vape.event.impl.EventPreMove;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.value.BooleanValue;
import gg.vape.value.NumberValue;
import gg.vape.wrapper.impl.BlockPos;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.GameSettings;
import gg.vape.wrapper.impl.Material;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.PotionRegistry;

public class LongJump
extends Mod {
    private boolean jumpStarted;
    private static final long MODULE_ID = 3997974486500807233L;
    private double lastHorizontalDistance;
    private final BooleanValue autoDisable;
    private double moveSpeed;
    private boolean wasOnGround;
    private final NumberValue boost = NumberValue.create(this, "Boost", "#.#", "", 3.0, 4.1, 5.0);

    @Override
    public boolean isBlatantMod() {
        return true;
    }


    private double getBaseMoveSpeed(EntityPlayerSP player) {
        double baseSpeed = 0.28730000691562896;
        if (player.i(PotionRegistry.U) && player.b(PotionRegistry.U).k() > 5) {
            int amplifier = player.b(PotionRegistry.U).L();
            baseSpeed *= 1.0 + 0.15 * (double)(amplifier + 1);
        }
        return baseSpeed;
    }

    @EventHandler
    public void onMotionUpdate(EventPreMotion eventPreMotion) {
        EntityPlayerSP player = eventPreMotion.getThePlayer();
        this.lastHorizontalDistance = Math.hypot(player.z() - player.f(), player.h() - player.R());
        if (this.autoDisable.getEffectiveValue().booleanValue()) {
            if (!this.wasOnGround && player.u$src$Z$g120nz() && this.jumpStarted) {
                this.toggle();
            }
            if (!this.jumpStarted && this.hasMovementInput(player)) {
                this.jumpStarted = true;
            }
        }
    }

    private boolean hasMovementInput(EntityPlayerSP player) {
        GameSettings settings = Minecraft.gameSettings();
        return settings.Y().u() || settings.s().u() || settings.x$src$Lgg_vape_wrapper_impl_KeyBinding_$1cf7isg().u() || settings.g$src$Lgg_vape_wrapper_impl_KeyBinding_$qqn5n3().u() || player.movementInput().D() != 0.0f || player.movementInput().T() != 0.0f;
    }

    @EventHandler
    public void onPreMove(EventPreMove eventPreMove) {
        EntityPlayerSP player = eventPreMove.getThePlayer();
        if (player.F() == 0.0f && player.N$src$F$14ypudi() == 0.0f || eventPreMove.getWorld().isNull() || player.h$src$Z$ftwoya()) {
            this.moveSpeed = this.getBaseMoveSpeed(player);
            return;
        }
        boolean hasEnoughFood = player.Y$src$Lgg_vape_wrapper_impl_FoodStats_$fakh1z().getFoodLevel() >= 6;
        boolean onFastSurface = eventPreMove.getWorld().getBlockState(BlockPos.D(player.z(), player.N() - 0.1, player.h())).getBlock().c() == 0.98f;
        double baseSpeed = this.moveSpeed = this.getBaseMoveSpeed(player) * (player.d(Material.vine()) ? 0.5 : (player.P() ? 0.8 : (player.Q$src$Z$fh9faz() || player.h$src$Z$ftwoya() ? 0.54 : (onFastSurface ? 2.4 : (hasEnoughFood ? 1.0 : 0.765)))));
        if (player.b$src$Z$fqlxe4()) {
            if (this.wasOnGround) {
                this.moveSpeed = baseSpeed * (Double)this.boost.getValue();
                player.k((double)0.42f);
                eventPreMove.setY(0.42f);
            } else {
                this.moveSpeed = baseSpeed;
            }
        } else if (this.wasOnGround) {
            double airBaseSpeed = 0.3303950079529733;
            if (player.i(PotionRegistry.U) && player.b(PotionRegistry.U).k() > 5) {
                int amplifier = player.b(PotionRegistry.U).L();
                airBaseSpeed *= 1.0 + 0.15 * (double)(amplifier + 1);
            }
            this.moveSpeed = this.lastHorizontalDistance - 0.666 * (this.lastHorizontalDistance - airBaseSpeed);
            this.jumpStarted = true;
        } else {
            this.moveSpeed = this.lastHorizontalDistance - this.lastHorizontalDistance / 50.0;
        }
        this.wasOnGround = player.b$src$Z$fqlxe4();
        this.moveSpeed = Math.max(this.moveSpeed, baseSpeed);
        float movementYaw = this.getMovementYaw(player);
        eventPreMove.setX(-(Math.sin(movementYaw) * this.moveSpeed));
        eventPreMove.setZ(Math.cos(movementYaw) * this.moveSpeed);
    }

    public LongJump() {
        super("LongJump", (int)MODULE_ID, Category.OTHER, "Does not work on a advanced anti-cheat servers.");
        this.autoDisable = BooleanValue.create(this, "Toggle", true, "Toggles off after touching the ground.");
        this.addValue(this.boost, this.autoDisable);
        this.moveSpeed = 0.27999999999999997;
        this.wasOnGround = false;
        this.lastHorizontalDistance = 0.0;
    }

    @Override
    public void onEnable() {
        Vape.INSTANCE.getClientSettings().onModuleChanged(this);
        this.moveSpeed = 0.27999999999999997;
        this.wasOnGround = false;
        this.lastHorizontalDistance = 0.0;
        this.jumpStarted = false;
    }

    private float getMovementYaw(EntityPlayerSP player) {
        float yaw = player.J();
        if (player.F() < 0.0f) {
            yaw += 180.0f;
        }
        float forwardFactor = player.F() < 0.0f ? -0.5f : (player.F() > 0.0f ? 0.5f : 1.0f);
        if (player.N$src$F$14ypudi() > 0.0f) {
            yaw -= 90.0f * forwardFactor;
        }
        if (player.N$src$F$14ypudi() < 0.0f) {
            yaw += 90.0f * forwardFactor;
        }
        return yaw * (float)Math.PI / 180;
    }
}
