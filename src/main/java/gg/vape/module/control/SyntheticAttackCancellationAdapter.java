package gg.vape.module.control;

import gg.vape.event.impl.SyntheticAttackRequestEvent;

public class SyntheticAttackCancellationAdapter
implements AttackCancellationAdapter {
    private final SyntheticAttackRequestEvent attackRequestEvent;

    @Override
    public void setCancelled(boolean cancelled) {
        this.attackRequestEvent.setCancelled(cancelled);
    }

    public SyntheticAttackCancellationAdapter(SyntheticAttackRequestEvent attackRequestEvent) {
        this.attackRequestEvent = attackRequestEvent;
    }
}

