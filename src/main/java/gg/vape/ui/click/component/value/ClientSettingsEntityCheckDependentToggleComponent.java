package gg.vape.ui.click.component.value;

import gg.vape.config.ClientSettings;
import gg.vape.ui.click.component.value.BooleanToggleComponent;
import gg.vape.value.BooleanValue;
import java.awt.Color;

public final class ClientSettingsEntityCheckDependentToggleComponent
extends BooleanToggleComponent {
    final ClientSettings clientSettings;

    @Override
    public boolean V$src$Z$1xhop3l() {
        return this.clientSettings.healthPrediction.getEffectiveValue();
    }

    public ClientSettingsEntityCheckDependentToggleComponent(BooleanValue booleanValue, ClientSettings clientSettings) {
        super(booleanValue);
        this.clientSettings = clientSettings;
    }

    @Override
    public Color getDisabledOverlayColor() {
        return ClientSettingsEntityCheckDependentToggleComponent.J.r;
    }
}
