package gg.vape.ui.click.component.input;

import gg.vape.Vape;
import gg.vape.input.BindCaptureTask;
import gg.vape.ui.click.component.input.BindableInputComponent;
import gg.vape.unmap.Bendable;

public class BindableInputComponentSavingBindCaptureTask
extends BindCaptureTask {
    final BindableInputComponent owner;

    public BindableInputComponentSavingBindCaptureTask(BindableInputComponent bindableInputComponent, Bendable bendable) {
        super(bendable);
        this.owner = bindableInputComponent;
    }

    public void saveSettings() {
        Vape.INSTANCE.saveAndStop();
    }

    @Override
    public void onCaptureComplete() {
        this.saveSettings();
    }
}
