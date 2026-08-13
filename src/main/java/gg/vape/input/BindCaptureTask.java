package gg.vape.input;

import gg.vape.input.BindCaptureThread;
import gg.vape.unmap.Bendable;

public abstract class BindCaptureTask
implements Runnable {
    private BindCaptureThread captureThread;
    private Bendable bendable;

    @Override
    public void run() {
        if (this.captureThread == null) {
            this.captureThread = new BindCaptureThread(this, null);
            this.captureThread.start();
        }
    }

    public BindCaptureTask(Bendable bendable) {
        this.bendable = bendable;
    }

    public void setBendable(Bendable bendable) {
        this.bendable = bendable;
    }

    public boolean isCapturing() {
        return this.captureThread != null;
    }

    public final void finishCapture() {
        this.captureThread = null;
        this.onCaptureComplete();
    }

    Bendable getBendable() {
        return this.bendable;
    }

    public abstract void onCaptureComplete();
}

