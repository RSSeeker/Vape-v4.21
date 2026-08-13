package gg.vape.unmap;

import gg.vape.unmap.PropertyKey;

public final class PropertyContainerBooleanKeyC
extends PropertyKey<Boolean> {
    @Override
    public Boolean getDefaultValue() {
        return this.getFalseDefault();
    }

    Boolean getFalseDefault() {
        return false;
    }
}
