package gg.vape.ui.click.frame.impl.main;

import gg.vape.Vape;
import gg.vape.input.BindCaptureTask;
import gg.vape.ui.click.frame.impl.main.ClickGuiMacrosSettingsPanel;
import gg.vape.unmap.Bendable;

class ClickGuiMacrosSecondaryBindCaptureTask
extends BindCaptureTask {
    final ClickGuiMacrosSettingsPanel settingsPanel;

    ClickGuiMacrosSecondaryBindCaptureTask(ClickGuiMacrosSettingsPanel clickGuiMacrosSettingsPanel, Bendable bendable) {
        super(bendable);
        this.settingsPanel = clickGuiMacrosSettingsPanel;
    }


    private void completeCapture() {
        this.settingsPanel.setBindCaptureTask(null);
        if (this.settingsPanel.getMacro().hasValidBinding()) {
            this.settingsPanel.showFullSettings();
        } else {
            this.settingsPanel.updateViewVisibility();
        }
        Vape.INSTANCE.saveAndStop();
    }

    @Override
    public void onCaptureComplete() {
        this.completeCapture();
    }
}
