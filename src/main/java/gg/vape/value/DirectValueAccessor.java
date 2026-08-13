package gg.vape.value;

import gg.vape.value.Value;
import gg.vape.value.ValueAccessor;

public class DirectValueAccessor<K, T extends Value<K, T>>
extends ValueAccessor {
    public void setValue(Object value) {
        this.getSourceValue().setDirectValue(value);
    }

    public Object getValue() {
        return this.getSourceValue().getDirectValue();
    }

    public DirectValueAccessor(Value<K, T> value) {
        super(value);
    }

}
