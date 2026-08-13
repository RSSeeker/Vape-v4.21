package gg.vape.movement;

import gg.vape.config.ClientSettings;
import gg.vape.event.impl.EventTickBase;
import gg.vape.utils.SleepUtil;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.KeyBinding;
import gg.vape.wrapper.impl.Minecraft;
import java.util.ArrayList;

public abstract class PlayerMovementTask {
    public double remainingX;
    private boolean requireSupportedMovement;
    private static String[] controlFlowMarker;
    private boolean targetReached;
    private ArrayList<KeyBinding> excludedKeys = new ArrayList<KeyBinding>();
    private boolean ignoreZ;
    private boolean completed;
    public double remainingZ;
    private boolean restoreInputOnCompletion = true;
    private double completionTolerance = 0.2;
    private boolean ignoreX;
    private boolean sneakNearTarget;
    private boolean waitForGroundAfterArrival;

    public boolean shouldRestoreInputOnCompletion() {
        return this.restoreInputOnCompletion;
    }

    public void updateCompletion() {
        if (Minecraft.currentScreen().isNotNull()) {
            return;
        }
        EntityPlayerSP player = Minecraft.thePlayer();
        if (player.isNull()) {
            return;
        }
        boolean reachedTarget = this.hasReachedTarget();
        if (reachedTarget) {
            if (!this.targetReached) {
                this.targetReached = true;
            }
            this.setCompleted(true);
            return;
        }
        if (this.targetReached && (!this.waitForGroundAfterArrival || player.b$src$Z$fqlxe4())) {
            this.setCompleted(true);
        }
    }

    public static void setControlFlowMarker(String[] marker) {
        controlFlowMarker = marker;
    }

    public void setCompletionTolerance(double tolerance) {
        this.completionTolerance = tolerance;
    }

    public void setWaitForGroundAfterArrival(boolean waitForGround) {
        this.waitForGroundAfterArrival = waitForGround;
    }

    public boolean waitUntilComplete(long timeoutMillis) {
        int attempts = 0;
        while (!this.isCompleted()) {
            SleepUtil.sleep(10L);
            if ((long)(++attempts) <= timeoutMillis / 10L) continue;
            return true;
        }
        return false;
    }

    public void setExcludedKeys(ArrayList<KeyBinding> excludedKeys) {
        this.excludedKeys = excludedKeys;
    }

    static {
        if (PlayerMovementTask.getControlFlowMarker() != null) {
            PlayerMovementTask.setControlFlowMarker(new String[5]);
        }
    }

    public static String[] getControlFlowMarker() {
        return controlFlowMarker;
    }

    public double getRemainingZ() {
        return this.remainingZ;
    }


    public boolean shouldIgnoreX() {
        return this.ignoreX;
    }

    public abstract boolean hasReachedTarget();

    public void setRestoreInputOnCompletion(boolean restoreInput) {
        this.restoreInputOnCompletion = restoreInput;
    }

    public boolean shouldWaitForGroundAfterArrival() {
        return this.waitForGroundAfterArrival;
    }

    public double getRemainingX() {
        return this.remainingX;
    }

    public ArrayList<KeyBinding> getExcludedKeys() {
        return this.excludedKeys;
    }

    public boolean shouldRequireSupportedMovement() {
        return this.requireSupportedMovement;
    }

    public void setSneakNearTarget(boolean sneakNearTarget) {
        this.sneakNearTarget = sneakNearTarget;
    }

    public boolean isCompleted() {
        return this.completed;
    }

    public void applyMovementInput(EventTickBase event) {
        if (event.getThePlayer().isNull() || event.getWorld().isNull()) {
            return;
        }
        if (this.remainingX != 0.0 || this.remainingZ != 0.0) {
            KeyBinding sneakKey = Minecraft.gameSettings().d$src$Lgg_vape_wrapper_impl_KeyBinding_$adn2z0();
            if (this.shouldSneakNearTarget() && Math.abs(this.remainingX) < 2.0
                    && Math.abs(this.remainingZ) < 2.0) {
                MovementInputHelper.setKeyPressed(sneakKey, true);
            } else {
                boolean physicallyPressed = ClientSettings.isPhysicalKeyDown(sneakKey);
                if (physicallyPressed) {
                    MovementInputHelper.setKeyPressed(sneakKey, true);
                } else {
                    MovementInputHelper.setKeyPressed(sneakKey, false);
                }
            }
            MovementInputHelper.applyMovementToward(this.remainingX, this.remainingZ,
                    this.excludedKeys, this.requireSupportedMovement);
        }
    }

    public boolean shouldSneakNearTarget() {
        return this.sneakNearTarget;
    }

    public void setIgnoreZ(boolean ignoreZ) {
        this.ignoreZ = ignoreZ;
    }

    public boolean shouldIgnoreZ() {
        return this.ignoreZ;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public double getCompletionTolerance() {
        return this.completionTolerance;
    }

    public void setIgnoreX(boolean ignoreX) {
        this.ignoreX = ignoreX;
    }

    public void setRequireSupportedMovement(boolean requireSupportedMovement) {
        this.requireSupportedMovement = requireSupportedMovement;
    }
}

