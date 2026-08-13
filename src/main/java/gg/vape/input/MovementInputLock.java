package gg.vape.input;

import gg.vape.module.control.ModuleControlClaim;

public class MovementInputLock
extends ModuleControlClaim {
    public void unlock() {
        this.clearClaimed();
    }

    public void lock() {
        this.markClaimed();
    }

    public boolean isLocked() {
        return this.isClaimed();
    }
}
