package gg.vape.ui.click.frame.impl.main;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.input.SmallTextInputComponent;
import gg.vape.ui.click.frame.impl.main.ClickGuiMacrosSettingsPanel;
import gg.vape.ui.click.frame.impl.main.ClickGuiMacrosSettingsViewMode;

class ClickGuiMacrosNameInput
extends SmallTextInputComponent {
    final ClickGuiMacrosSettingsPanel settingsPanel;

    @Override
    public float getTextVerticalOffset() {
        return 0.0f;
    }

    @Override
    public boolean isFocused() {
        return this.isNameInputMode() && super.isFocused();
    }

    @Override
    public void submit() {
        if (this.isNameInputMode() && this.hasNonBlankText()) {
            this.settingsPanel.submitName(this.getText().trim());
        }
    }

    ClickGuiMacrosNameInput(ClickGuiMacrosSettingsPanel clickGuiMacrosSettingsPanel, String string) {
        super(string);
        this.settingsPanel = clickGuiMacrosSettingsPanel;
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
        if (this.isNameInputMode()) {
            super.g(guiMouseEvent);
        }
    }

    private boolean isNameInputMode() {
        return this.settingsPanel.getViewMode() == ClickGuiMacrosSettingsViewMode.NAME_INPUT;
    }

    @Override
    public void requestFocus() {
        if (this.isNameInputMode()) {
            super.requestFocus();
        }
    }

}
