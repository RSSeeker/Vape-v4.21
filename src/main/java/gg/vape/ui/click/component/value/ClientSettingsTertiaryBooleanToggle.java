package gg.vape.ui.click.component.value;

import gg.vape.config.PublicProfileSettings;
import gg.vape.ui.click.component.value.BooleanToggleComponent;
import gg.vape.value.BooleanValue;
import java.awt.Color;

public final class ClientSettingsTertiaryBooleanToggle
extends BooleanToggleComponent {
    final PublicProfileSettings publicProfileSettings;

    @Override
    public Color getDisabledOverlayColor() {
        return ClientSettingsTertiaryBooleanToggle.J.r;
    }

    public ClientSettingsTertiaryBooleanToggle(BooleanValue booleanValue, PublicProfileSettings publicProfileSettings) {
        super(booleanValue);
        this.publicProfileSettings = publicProfileSettings;
    }

    @Override
    public boolean V$src$Z$1xhop3l() {
        return this.publicProfileSettings.notifications.getEffectiveValue();
    }
}
