package gg.vape.ui.click.frame;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.frame.ClickGuiQuickActionsComponent;
import gg.vape.ui.click.frame.impl.VisibleModuleListFrame;

public class ClickGuiQuickActionsVisibleModulesClickHandler
implements GuiClickListener {
    final ClickGuiQuickActionsComponent v;

    @Override
    public void onPrimaryClick() {
        ClientSettings.showFrame(VisibleModuleListFrame.class);
    }

    public ClickGuiQuickActionsVisibleModulesClickHandler(ClickGuiQuickActionsComponent clickGuiQuickActionsComponent) {
        this.v = clickGuiQuickActionsComponent;
    }
}
