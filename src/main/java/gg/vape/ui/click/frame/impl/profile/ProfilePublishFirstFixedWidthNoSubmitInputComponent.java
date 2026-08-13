package gg.vape.ui.click.frame.impl.profile;

import gg.vape.ui.click.component.TextInputComponentBase;
import gg.vape.ui.click.frame.impl.profile.ProfilePublishEditorPanel;

public class ProfilePublishFirstFixedWidthNoSubmitInputComponent
extends TextInputComponentBase {
    private final double fixedWidth;

    public ProfilePublishFirstFixedWidthNoSubmitInputComponent(String placeholder, double fixedWidth) {
        super(placeholder);
        this.fixedWidth = fixedWidth;
    }

    @Override
    public void submit() {
    }

    @Override
    public double x() {
        return this.fixedWidth;
    }

    @Override
    public double C() {
        return 16.0;
    }
}
