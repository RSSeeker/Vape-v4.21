package gg.vape.module.blatant;

import gg.vape.Vape;
import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventMotion;
import gg.vape.event.impl.EventMove;
import gg.vape.event.impl.EventPreMotion;
import gg.vape.event.impl.EventPreMove;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.value.NumberValue;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.PotionRegistry;

public class Fly
extends Mod {
    private final NumberValue verticalSpeed;
    private final NumberValue speed = NumberValue.create(this, "Speed", "#.#", "", 0.1, 0.5, 5.0, 0.1, "Speed for Normal fly mode.");

    @EventHandler
    public void onMotionUpdate(EventPreMotion eventPreMotion) {
        EntityPlayerSP player = Minecraft.thePlayer();
        EventMotion.setOnGround(false);
        player.U(false);
    }

    @Override
    public void onEnable() {
        Vape.INSTANCE.getClientSettings().onModuleChanged(this);
    }

    private void applyHorizontalMove(EventMove eventMove, double speed) {
        EntityPlayerSP player = Minecraft.thePlayer();
        double forwardInput = player.movementInput().D();
        double strafeInput = player.movementInput().T();
        float yaw = player.J();
        if (forwardInput == 0.0 && strafeInput == 0.0) {
            eventMove.setX(0.0).setZ(0.0);
            return;
        }
        if (forwardInput != 0.0) {
            if (strafeInput > 0.0) {
                yaw += forwardInput > 0.0 ? -45.0f : 45.0f;
            } else if (strafeInput < 0.0) {
                yaw += forwardInput > 0.0 ? 45.0f : -45.0f;
            }
            strafeInput = 0.0;
            if (forwardInput > 0.0) {
                forwardInput = 1.0;
            } else if (forwardInput < 0.0) {
                forwardInput = -1.0;
            }
        }
        eventMove.setX(forwardInput * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafeInput * speed * Math.sin(Math.toRadians(yaw + 90.0f)));
        eventMove.setZ(forwardInput * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafeInput * speed * Math.cos(Math.toRadians(yaw + 90.0f)));
    }
    private double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (Minecraft.thePlayer().i(PotionRegistry.U)) {
            int amplifier = Minecraft.thePlayer().b(PotionRegistry.U).L();
            baseSpeed *= 1.0 + 0.2 * (double)(amplifier + 1);
        }
        return baseSpeed;
    }

    public Fly() {
        super("Fly", 49630, Category.OTHER, "Makes you go zoom.");
        this.verticalSpeed = NumberValue.create(this, "Vertical Speed", "#.#", "", 0.1, 0.2, 5.0, 0.1, "Speed for Normal vertical fly mode.");
        this.setDefaultVisibility(false);
        this.addValue(this.speed, this.verticalSpeed);
    }

    @Override
    public boolean isBlatantMod() {
        return true;
    }

    @EventHandler
    public void onPreMove(EventPreMove eventPreMove) {
        EntityPlayerSP player = Minecraft.thePlayer();
        double verticalMotion = Minecraft.gameSettings().O().u() ? (Double)this.verticalSpeed.java_lang_Object_K() : (Minecraft.gameSettings().d$src$Lgg_vape_wrapper_impl_KeyBinding_$adn2z0().u() ? -((Double)this.verticalSpeed.java_lang_Object_K()).doubleValue() : 0.0);
        eventPreMove.setY(verticalMotion);
        player.k(verticalMotion);
        this.applyHorizontalMove(eventPreMove, Math.max((Double)this.speed.java_lang_Object_K(), this.getBaseMoveSpeed()));
    }
}

