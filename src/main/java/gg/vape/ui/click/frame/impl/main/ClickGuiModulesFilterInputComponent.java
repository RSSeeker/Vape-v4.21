package gg.vape.ui.click.frame.impl.main;

import gg.vape.ui.click.component.LabeledTextInputComponent;
import gg.vape.ui.click.frame.impl.main.ClickGuiModulesPage;

public class ClickGuiModulesFilterInputComponent
extends LabeledTextInputComponent {
    final ClickGuiModulesPage J7;

    public ClickGuiModulesFilterInputComponent(ClickGuiModulesPage clickGuiModulesPage, String string) {
        super(string);
        this.J7 = clickGuiModulesPage;
    }

    @Override
    public void setText(String text) {
        super.setText(text);
        ClickGuiModulesPage.setLegitSearchQuery(this.J7, text);
        ClickGuiModulesPage.rebuildModuleCards(this.J7, ClickGuiModulesPage.getLegitContent(this.J7), true);
    }
}
