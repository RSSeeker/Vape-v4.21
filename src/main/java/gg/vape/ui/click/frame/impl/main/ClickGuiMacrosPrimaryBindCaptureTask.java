package gg.vape.ui.click.frame.impl.main;

import gg.vape.Vape;
import gg.vape.input.BindCaptureTask;
import gg.vape.ui.click.frame.impl.main.ClickGuiMacrosSettingsPanel;
import gg.vape.unmap.Bendable;

class ClickGuiMacrosPrimaryBindCaptureTask
extends BindCaptureTask {
    final ClickGuiMacrosSettingsPanel settingsPanel;

    private void completeCapture() {
        this.settingsPanel.setBindCaptureTask(null);
        Vape.INSTANCE.saveAndStop();
    }

    @Override
    public void onCaptureComplete() {
        this.completeCapture();
    }

    ClickGuiMacrosPrimaryBindCaptureTask(ClickGuiMacrosSettingsPanel clickGuiMacrosSettingsPanel, Bendable bendable) {
        super(bendable);
        this.settingsPanel = clickGuiMacrosSettingsPanel;
    }
}
