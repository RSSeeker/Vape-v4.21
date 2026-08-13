package gg.vape.module.combat.aimassist;

import gg.vape.Vape;

public class AimAssistRotationWorkerThread
extends Thread {
    private final AimAssistRotationSubModule rotationModule;

    public AimAssistRotationWorkerThread(AimAssistRotationSubModule rotationModule) {
        this.rotationModule = rotationModule;
    }

    @Override
    public void run() {
        while (!Vape.INSTANCE.isEnabled()) {
            try {
                Thread.sleep(1L);
                if (!this.rotationModule.isEnabled() || !this.rotationModule.isSelectedSubModule()) continue;
                AimAssistRotationSubModule.runWorkerTick(this.rotationModule);
            }
            catch (Exception ignored) {}
        }
    }
}

