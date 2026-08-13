package gg.vape.ui.click.component.value;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.TextInputComponentBase;
import gg.vape.ui.click.component.value.StringMapValueComponent;

class StringMapKeyInputComponent
extends TextInputComponentBase {
    final StringMapValueComponent owner;

    @Override
    public void submit() {
        ClientSettings.activeComponent = StringMapValueComponent.getValueInputCompat(this.owner);
    }

    @Override
    public double x() {
        return 110.0;
    }

    @Override
    public double C() {
        return 18.0;
    }

    StringMapKeyInputComponent(StringMapValueComponent owner, String placeholder) {
        super(placeholder);
        this.owner = owner;
    }
}
