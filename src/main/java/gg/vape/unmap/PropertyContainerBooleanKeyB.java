package gg.vape.unmap;

import gg.vape.unmap.PropertyKey;

public final class PropertyContainerBooleanKeyB
extends PropertyKey<Boolean> {
    @Override
    public Boolean getDefaultValue() {
        return this.getFalseDefault();
    }

    Boolean getFalseDefault() {
        return false;
    }
}
