package gg.vape.ui.click.component;

import gg.vape.ui.click.component.TextInputComponentBase;

public class FixedSizeNoSubmitTextInputComponent
extends TextInputComponentBase {
    @Override
    public double x() {
        return 0.0;
    }

    @Override
    public double C() {
        return 0.0;
    }

    public FixedSizeNoSubmitTextInputComponent(String string) {
        super(string);
        this.o(100.0);
        this.Y(20.0);
    }

    @Override
    public void submit() {
    }

    @Override
    public void H() {
        super.H();
    }

    @Override
    public float getLeftInset() {
        return 0.0f;
    }
}
