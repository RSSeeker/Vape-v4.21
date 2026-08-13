package gg.vape.ui.click.frame.impl.main;

import gg.vape.ui.click.component.LabeledTextInputComponent;
import gg.vape.ui.click.frame.impl.main.ClickGuiModulesPage;

public class ClickGuiModulesSearchInputComponent
extends LabeledTextInputComponent {
    final ClickGuiModulesPage tO;

    @Override
    public void setText(String text) {
        super.setText(text);
        ClickGuiModulesPage.setModuleSearchQuery(this.tO, text);
        ClickGuiModulesPage.rebuildModuleCards(this.tO, ClickGuiModulesPage.getModuleContent(this.tO), false);
    }

    public ClickGuiModulesSearchInputComponent(ClickGuiModulesPage clickGuiModulesPage, String string) {
        super(string);
        this.tO = clickGuiModulesPage;
    }
}
