package gg.vape.ui.click.frame.impl.main;

import gg.vape.ui.click.component.LabeledTextInputComponent;
import gg.vape.ui.click.frame.impl.main.ClickGuiMacrosController;

public class ClickGuiMacrosLabelInput
extends LabeledTextInputComponent {
    final ClickGuiMacrosController controller;

    @Override
    public void setText(String text) {
        super.setText(text);
        ClickGuiMacrosController.setSearchQuery(this.controller, text);
        this.controller.rebuildMacroCards();
    }

    public ClickGuiMacrosLabelInput(ClickGuiMacrosController clickGuiMacrosController, String string) {
        super(string);
        this.controller = clickGuiMacrosController;
    }
}
