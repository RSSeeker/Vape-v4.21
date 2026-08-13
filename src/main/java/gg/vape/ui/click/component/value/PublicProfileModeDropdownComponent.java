package gg.vape.ui.click.component.value;

import gg.vape.Vape;
import gg.vape.ui.click.component.DropdownSelectComponent;
import gg.vape.ui.font.FontOption;
import gg.vape.value.ModeValue;

public final class PublicProfileModeDropdownComponent
extends DropdownSelectComponent<FontOption> {
    public PublicProfileModeDropdownComponent(ModeValue modeValue) {
        super(modeValue);
    }

    @Override
    public void onSelectionChanged() {
        Vape.INSTANCE.getFontSelector().N(this.getSelectedValue());
    }
}
