package gg.vape.ui.click.component.value;

import gg.vape.ui.click.component.TextInputComponentBase;
import gg.vape.ui.click.component.value.StringMapValueComponent;

class StringMapValueInputComponent
extends TextInputComponentBase {
    final StringMapValueComponent owner;

    @Override
    public double x() {
        return 110.0;
    }

    StringMapValueInputComponent(StringMapValueComponent owner, String placeholder) {
        super(placeholder);
        this.owner = owner;
    }

    @Override
    public void submit() {
    }

    @Override
    public double C() {
        return 18.0;
    }
}
