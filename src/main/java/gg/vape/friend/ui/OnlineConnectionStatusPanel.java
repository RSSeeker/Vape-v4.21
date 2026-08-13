package gg.vape.friend.ui;

import gg.vape.friend.ui.OnlineConnectionStatusPanelBody;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.layout.PaddedComponent;

public class OnlineConnectionStatusPanel
extends PanelComponent {
    public OnlineConnectionStatusPanel() {
        super(100.0, 130.0);
        OnlineConnectionStatusPanelBody statusBody = new OnlineConnectionStatusPanelBody();
        this.h(new PaddedComponent(this.double_A() / 2.0 - statusBody.double_A() / 2.0, this.double_L() / 2.0 - statusBody.double_L() / 2.0 - 15.0, statusBody), new Object[0]);
    }
}
