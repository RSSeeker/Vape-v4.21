package gg.vape.module.blatant;

import gg.vape.Vape;
import gg.vape.event.EventHandler;
import gg.vape.event.EventPriority;
import gg.vape.event.impl.EventMove;
import gg.vape.event.impl.EventPreMotion;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.blatant.speed.BhopSpeed;
import gg.vape.module.blatant.speed.MineplexSpeed;
import gg.vape.unmap.ModeSelection;
import gg.vape.value.ModeValue;
import gg.vape.value.SubModuleValue;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.PotionRegistry;

public class Speed
extends Mod {
    private final SubModuleValue antiCheatBMode = new MineplexSpeed(this, "AntiCheat B").getSelectionValue();
    public double lastHorizontalDistance;
    private final ModeValue mode;
    private final SubModuleValue bhopMode = new BhopSpeed(this, "Bhop").getSelectionValue();
    public double moveSpeed;
    public int stage;

    public double defaultSpeed() {
        double baseSpeed = 0.28730000691562896;
        EntityPlayerSP player = Minecraft.thePlayer();
        if (player.i(PotionRegistry.U) && player.b(PotionRegistry.U).k() > 10) {
            int amplifier = player.b(PotionRegistry.U).L();
            baseSpeed *= 1.0 + 0.15 * (double)(amplifier + 1);
        }
        return baseSpeed;
    }

    @Override
    public boolean isBlatantMod() {
        return true;
    }

    public Speed() {
        super("Speed", 49630, Category.HIDDEN, "Increases your movement with various methods.");
        this.mode = ModeValue.create((Object)this, "Mode", "Speed mode to use.\nAntiCheat B works on various servers\nBhop - Bypasses Old NCP", (ModeSelection)this.bhopMode, this.antiCheatBMode, this.bhopMode);
        this.setDefaultVisibility(false);
        this.addValue(this.mode);
    }

    public void strafe(EventMove eventMove, double speed, EntityPlayerSP player) {
        double forwardInput = player.movementInput().D();
        double strafeInput = player.movementInput().T();
        float yaw = player.J();
        if (forwardInput == 0.0 && strafeInput == 0.0) {
            eventMove.setX(0.0);
            eventMove.setZ(0.0);
        } else if (forwardInput != 0.0) {
            if (strafeInput != 0.0) {
                if (strafeInput > 0.0) {
                    yaw += forwardInput > 0.0 ? -45.0f : 45.0f;
                    strafeInput = 0.0;
                } else {
                    yaw += forwardInput > 0.0 ? 45.0f : -45.0f;
                    strafeInput = 0.0;
                }
            }
            forwardInput = forwardInput > 0.0 ? 1.0 : -1.0;
        }
        double cosine = Math.cos(Math.toRadians(yaw + 90.0f));
        double sine = Math.sin(Math.toRadians(yaw + 90.0f));
        eventMove.setX(forwardInput * speed * cosine + strafeInput * speed * sine);
        eventMove.setZ(forwardInput * speed * sine - strafeInput * speed * cosine);
    }

    @EventHandler(priority=EventPriority.HIGH)
    public void onMotionUpdate(EventPreMotion eventPreMotion) {
        EntityPlayerSP player = Minecraft.thePlayer();
        double deltaX = player.z() - player.f();
        double deltaZ = player.h() - player.R();
        this.lastHorizontalDistance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
    }

    @Override
    public String getSimpleSuffix() {
        return this.mode.getDisplayValue();
    }

    @Override
    public void onEnable() {
        Vape.INSTANCE.getClientSettings().onModuleChanged(this);
        this.moveSpeed = this.defaultSpeed();
        this.lastHorizontalDistance = 0.0;
        this.stage = 2;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

}
