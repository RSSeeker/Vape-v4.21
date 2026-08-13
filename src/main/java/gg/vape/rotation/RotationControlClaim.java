package gg.vape.rotation;

import gg.vape.module.Mod;
import gg.vape.module.control.ModuleControlClaim;
import gg.vape.rotation.AdaptiveRotationController;
import gg.vape.rotation.RotationManager;

public class RotationControlClaim
extends ModuleControlClaim {
    public boolean isClaimed() {
        return super.isClaimed();
    }

    public boolean isBlockedFor(Mod mod) {
        boolean blocked = false;
        if (this.isClaimed() && !this.isCurrentOwner(mod) && !this.hasHigherPriorityThanCurrent(mod)) {
            blocked = true;
        }
        if (this.hasPendingOwner() && !this.isPendingOwner(mod) && this.hasHigherPriorityThanCurrent(this.pendingOwner)) {
            blocked = true;
        }
        return blocked;
    }

    public RotationControlClaim() {
        super(true);
    }

    public boolean release(Mod mod) {
        return super.release(mod);
    }

    public boolean isOwnedBy(Mod mod) {
        return this.isClaimed() && this.isCurrentOwner(mod) && !this.hasPendingOwner();
    }

    public boolean acquire(Mod mod) {
        return this.acquire(mod, false);
    }


    public boolean acquire(Mod mod, boolean allowPreemption) {
        if (allowPreemption && this.isClaimed() && !this.isCurrentOwner(mod) && RotationManager.INSTANCE.hasAdaptiveController()) {
            AdaptiveRotationController controller = (AdaptiveRotationController)RotationManager.INSTANCE.getActiveController();
            if (this.hasHigherPriorityThanCurrent(mod) || controller.isRelativeMode()) {
                Mod previousOwner = this.owner;
                this.clearPendingOwner();
                this.release(previousOwner);
            }
        }
        return this.tryAcquire(mod);
    }
}
