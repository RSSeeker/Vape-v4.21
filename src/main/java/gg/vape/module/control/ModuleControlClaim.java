package gg.vape.module.control;

import gg.vape.module.Mod;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class ModuleControlClaim {
    private final AtomicBoolean claimed;
    private final Map<Mod, Integer> priorities = new HashMap<>();
    private final AtomicBoolean pendingOwnerSet = new AtomicBoolean();
    protected Mod owner;
    protected Mod pendingOwner;
    private final boolean priorityAware;

    public ModuleControlClaim() {
        this(false);
    }

    public ModuleControlClaim(boolean priorityAware) {
        this.claimed = new AtomicBoolean();
        this.priorityAware = priorityAware;
    }

    public void setPriority(Mod module, int priority) {
        this.priorities.put(module, priority);
    }

    protected boolean isCurrentOwner(Mod module) {
        return this.owner != null && this.owner.equals(module);
    }

    public void setClaimed(boolean claimed) {
        this.claimed.set(claimed);
    }

    private void setPendingOwner(Mod module) {
        this.pendingOwnerSet.set(true);
        this.pendingOwner = module;
    }

    public boolean hasPendingOwner() {
        return this.pendingOwnerSet.get();
    }

    protected boolean release(Mod module) {
        if (this.priorityAware) {
            if (this.isPendingOwner(module)) {
                this.clearPendingOwner();
                return true;
            }
            if (!this.isCurrentOwner(module)) {
                return false;
            }
        }
        this.owner = null;
        this.claimed.set(false);
        return true;
    }

    public void clearPendingOwner() {
        this.pendingOwnerSet.set(false);
        this.pendingOwner = null;
    }

    protected boolean hasHigherPriorityThanCurrent(Mod module) {
        int requestedPriority = this.priorities.getOrDefault(module, 0);
        int currentPriority = this.priorities.getOrDefault(this.owner, 0);
        return requestedPriority > currentPriority;
    }

    protected boolean tryClaim(Mod module) {
        if (this.priorityAware) {
            if (this.claimed.get() || this.hasPendingOwner() && !this.isPendingOwner(module)) {
                return false;
            }
            this.clearPendingOwner();
        }
        this.owner = module;
        this.claimed.set(true);
        return true;
    }

    public boolean isClaimed() {
        return this.claimed.get();
    }

    protected boolean isPendingOwner(Mod module) {
        return this.pendingOwner != null && this.pendingOwner.equals(module);
    }

    public void clearClaimed() {
        this.claimed.set(false);
    }

    public void markClaimed() {
        this.claimed.set(true);
    }

    public boolean tryAcquire(Mod module) {
        if (this.tryClaim(module)) {
            return true;
        }
        if (this.priorityAware && this.isClaimed() && !this.isCurrentOwner(module)) {
            if (this.hasPendingOwner() && !this.isPendingOwner(module)) {
                return false;
            }
            if (!this.hasHigherPriorityThanCurrent(module)) {
                return false;
            }
            this.setPendingOwner(module);
        }
        return false;
    }
}
