package gg.vape.runtime;

import gg.vape.runtime.NativeBridge;

public class NativeBridgeLifecycleAdapter {
    public void handlePrimaryClassLoaderPhase(ClassLoader classLoader) throws Exception {
    }

    void handleInternalLifecyclePhase() {
    }

    public void handleLifecyclePhaseOne() throws Exception {
    }

    public void handleLifecyclePhaseTwo() throws Exception {
    }

    public void handleLifecyclePhaseThree() throws Exception {
    }

    public void handleBridgeClassPhase(Class<? extends NativeBridge> bridgeClass) throws Exception {
    }

    public void handleLifecyclePhaseFour() throws Exception {
    }

    public void handleSecondaryClassLoaderPhase(ClassLoader classLoader) throws Exception {
    }

    public void handleLifecyclePhaseFive() throws Exception {
    }

    public void handleLifecyclePhaseSix() throws Exception {
    }
}
