package gg.vape.ui.click.frame.impl.online;

import gg.vape.ui.click.component.value.BooleanToggleComponent;
import gg.vape.ui.click.frame.impl.online.OnlineAccountSettingsPageComponent;
import gg.vape.value.BooleanValue;

public class LinkedBooleanSettingsToggleComponent
extends BooleanToggleComponent {
    final BooleanValue[] linkedValues;
    final OnlineAccountSettingsPageComponent accountSettingsPage;

    public LinkedBooleanSettingsToggleComponent(OnlineAccountSettingsPageComponent accountSettingsPage, String label, double fontScale, BooleanValue booleanValue, BooleanValue[] linkedValues) {
        super(label, fontScale, booleanValue);
        this.accountSettingsPage = accountSettingsPage;
        this.linkedValues = linkedValues;
    }

    @Override
    public void toggleIfInteractive() {
        boolean wasOn = this.isOn();
        boolean toggledState = !wasOn;
        super.toggleIfInteractive();
        for (BooleanValue booleanValue : this.linkedValues) {
            booleanValue.setValue(!toggledState);
        }
    }


    @Override
    public void u() {
        super.u();
        int enabledLinkedValueCount = 0;
        for (BooleanValue booleanValue : this.linkedValues) {
            if (!booleanValue.getEffectiveValue().booleanValue()) continue;
            ++enabledLinkedValueCount;
        }
        if (enabledLinkedValueCount > 0 && this.isOn()) {
            this.setValue(false);
        } else if (enabledLinkedValueCount == 0 && !this.isOn()) {
            this.setValue(true);
        }
    }
}
