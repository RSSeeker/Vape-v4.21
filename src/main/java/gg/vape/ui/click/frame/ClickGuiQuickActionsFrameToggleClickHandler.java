package gg.vape.ui.click.frame;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.frame.ClickGuiQuickActionsComponent;
import gg.vape.ui.click.frame.impl.ClientSettingsSearchFrame;
import gg.vape.ui.click.frame.impl.quickactions.QuickActionsFrame;

class ClickGuiQuickActionsFrameToggleClickHandler
implements GuiClickListener {
    final ClickGuiQuickActionsComponent z;


    @Override
    public void onPrimaryClick() {
        QuickActionsFrame quickActionsFrame = ClientSettings.getFrame(QuickActionsFrame.class);
        ClientSettingsSearchFrame clientSettingsSearchFrame = ClientSettings.getFrame(ClientSettingsSearchFrame.class);
        if (quickActionsFrame == null || clientSettingsSearchFrame == null) {
            return;
        }
        quickActionsFrame.setVisible(!quickActionsFrame.V$src$Z$1xhop3l());
        if (quickActionsFrame.V$src$Z$1xhop3l()) {
            quickActionsFrame.U();
            quickActionsFrame.w(1);
        }
        quickActionsFrame.l$src$V$1mibm4x();
    }

    ClickGuiQuickActionsFrameToggleClickHandler(ClickGuiQuickActionsComponent clickGuiQuickActionsComponent) {
        this.z = clickGuiQuickActionsComponent;
    }
}

