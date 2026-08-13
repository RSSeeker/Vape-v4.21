package gg.vape.click;

import gg.vape.Vape;
import gg.vape.module.combat.ClickerMod;
import gg.vape.utils.SleepUtil;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClickerWorker
implements Runnable {
    private final AtomicBoolean active = new AtomicBoolean(false);
    private final Object activityMonitor = new Object();
    private boolean started;
    private final ClickerMod clickerModule;

    public ClickerWorker(ClickerMod clickerModule) {
        this.clickerModule = clickerModule;
    }

    public void startOrResume() {
        if (!this.started) {
            this.started = true;
            Thread workerThread = new Thread(this);
            workerThread.start();
        }
        this.resume();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void awaitActivityAndRunClickCycle() {
        ClickEngine clickEngine = this.clickerModule.getClickEngine();
        try {
            synchronized (this.activityMonitor) {
                if (!this.active.get()) {
                    try {
                        this.activityMonitor.wait();
                    }
                    catch (InterruptedException interruptedException) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
            if (clickEngine.getButton() == ClickButton.LEFT && this.clickerModule.isClickCycleBlocked()) {
                return;
            }
            this.clickerModule.runClickCycle();
        }
        catch (Exception exception) {
            Vape.logThrowable(exception);
        }
    }

    @Override
    public void run() {
        while (true) {
            SleepUtil.sleep(5L);
            this.awaitActivityAndRunClickCycle();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void resume() {
        synchronized (this.activityMonitor) {
            this.active.set(true);
            this.activityMonitor.notify();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void pause() {
        synchronized (this.activityMonitor) {
            this.active.set(false);
        }
    }
}
