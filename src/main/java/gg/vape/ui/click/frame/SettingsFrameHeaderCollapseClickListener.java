package gg.vape.ui.click.frame;

import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.frame.CollapsibleFrame;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.SettingsFrameHeaderComponent;

class SettingsFrameHeaderCollapseClickListener
implements GuiClickListener {
    final SettingsFrameHeaderComponent C;
    final Frame V;


    @Override
    public void onPrimaryClick() {
        if (this.V instanceof CollapsibleFrame) {
            ((CollapsibleFrame)((Object)this.V)).w();
        }
    }

    SettingsFrameHeaderCollapseClickListener(SettingsFrameHeaderComponent settingsFrameHeaderComponent, Frame frame) {
        this.C = settingsFrameHeaderComponent;
        this.V = frame;
    }
}

