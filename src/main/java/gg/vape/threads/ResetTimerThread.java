package gg.vape.threads;

import gg.vape.module.blatant.Step;
import gg.vape.wrapper.impl.Minecraft;

public class ResetTimerThread
extends Thread {
    final Step step;

    public ResetTimerThread(Step step) {
        this.step = step;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(100L);
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
        Minecraft.a_jo_2_T().setTimerSpeed(1.0f);
    }
}

