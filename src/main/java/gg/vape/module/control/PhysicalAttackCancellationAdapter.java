package gg.vape.module.control;

import gg.vape.event.impl.EventKeyInputBase;

public class PhysicalAttackCancellationAdapter
implements AttackCancellationAdapter {
    private final EventKeyInputBase keyInputEvent;

    @Override
    public void setCancelled(boolean cancelled) {
        this.keyInputEvent.setCancelled(cancelled);
    }

    public PhysicalAttackCancellationAdapter(EventKeyInputBase eventKeyInputBase) {
        this.keyInputEvent = eventKeyInputBase;
    }
}

