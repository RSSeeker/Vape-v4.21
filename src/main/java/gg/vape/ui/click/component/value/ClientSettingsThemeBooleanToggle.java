package gg.vape.ui.click.component.value;

import gg.vape.config.ClientSettings;
import gg.vape.ui.click.component.value.BooleanToggleComponent;
import gg.vape.value.BooleanValue;
import java.awt.Color;

public final class ClientSettingsThemeBooleanToggle
extends BooleanToggleComponent {
    final ClientSettings clientSettings;

    public ClientSettingsThemeBooleanToggle(BooleanValue booleanValue, ClientSettings clientSettings) {
        super(booleanValue);
        this.clientSettings = clientSettings;
    }

    @Override
    public boolean V$src$Z$1xhop3l() {
        return this.clientSettings.healthPrediction.getEffectiveValue();
    }

    @Override
    public Color getDisabledOverlayColor() {
        return ClientSettingsThemeBooleanToggle.J.r;
    }
}
