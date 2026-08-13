package gg.vape.ui.click.frame.impl.profile;

import gg.vape.ui.click.component.TextInputComponentBase;
import gg.vape.ui.click.frame.impl.profile.PublicProfileOwnerDetailsPanel;

class PublicProfileOwnerFixedWidthNoSubmitInputComponent
extends TextInputComponentBase {
    private final double fixedWidth;

    PublicProfileOwnerFixedWidthNoSubmitInputComponent(String placeholder, double fixedWidth) {
        super(placeholder);
        this.fixedWidth = fixedWidth;
    }

    @Override
    public void submit() {
    }

    @Override
    public double C() {
        return 16.0;
    }

    @Override
    public double x() {
        return this.fixedWidth;
    }
}
