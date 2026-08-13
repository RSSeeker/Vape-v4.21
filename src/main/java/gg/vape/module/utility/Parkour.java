package gg.vape.module.utility;

import gg.vape.Vape;
import gg.vape.config.ClientSettings;
import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPrePlayerTick;
import gg.vape.input.KeyBindingHelper;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.render.Freecam;
import gg.vape.wrapper.impl.AxisAlignedBB;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.KeyBinding;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.MovementInput;
import java.util.List;

public class Parkour
extends Mod {
    private boolean jumpPending;
    private boolean jumpKeyWasPressed;
    private static final long MODULE_ID = -6495356621742786881L;

    public Parkour() {
        super("Parkour", (int)MODULE_ID, Category.WORLD, "Jumps for you at the edge of blocks.");
    }


    @EventHandler
    public void onTick(EventPrePlayerTick event) {
        if (Vape.INSTANCE.getModManager().getState(Freecam.class)) {
            return;
        }
        KeyBinding keyBinding = Minecraft.gameSettings().O();
        EntityPlayerSP localPlayer = Minecraft.thePlayer();
        if (this.jumpPending) {
            if (!this.jumpKeyWasPressed) {
                KeyBindingHelper.updateKeyBinding(keyBinding, false, false);
            }
            this.jumpPending = false;
            this.jumpKeyWasPressed = false;
            return;
        }
        if (keyBinding.isKeyDown()) {
            return;
        }
        MovementInput movementInput = localPlayer.movementInput();
        boolean movingForward = movementInput.D() > 0.0f
                || ClientSettings.isPhysicalKeyDown(Minecraft.gameSettings().Y());
        if (movingForward && localPlayer.b$src$Z$fqlxe4()) {
            AxisAlignedBB boundingBox;
            if (ForgeVersion.MC_1_8_9.d()) {
                boundingBox = localPlayer.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl();
            } else {
                AxisAlignedBB currentBox = localPlayer.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl();
                boundingBox = currentBox.copy();
            }
            double distance = 0.0;
            double yaw = localPlayer.J();
            double yawOffset = 90.0;
            double offsetX = Math.cos(Math.toRadians(yaw + yawOffset)) * distance;
            double offsetZ = Math.sin(Math.toRadians(yaw + yawOffset)) * distance;
            double offsetY = -0.1;
            AxisAlignedBB offsetBox = boundingBox.k(offsetX, offsetY, offsetZ);
            List<?> nearCollisions = Minecraft.theWorld().i(localPlayer, offsetBox);
            distance = 1.0;
            offsetX = Math.cos(Math.toRadians(yaw + yawOffset)) * distance;
            offsetZ = Math.sin(Math.toRadians(yaw + yawOffset)) * distance;
            offsetY = -0.1;
            offsetBox = boundingBox.k(offsetX, offsetY, offsetZ);
            List<?> farCollisions = Minecraft.theWorld().i(localPlayer, offsetBox);
            int nearCount = nearCollisions.size();
            int farCount = farCollisions.size();
            if (nearCount == 0 && farCount == 0) {
                this.jumpKeyWasPressed = keyBinding.u();
                KeyBindingHelper.setPressedAndTick(keyBinding, true);
                this.jumpPending = true;
            }
        }
    }
}

