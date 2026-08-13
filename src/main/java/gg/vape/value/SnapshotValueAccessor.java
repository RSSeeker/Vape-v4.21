package gg.vape.value;

import gg.vape.value.Value;
import gg.vape.value.ValueAccessor;
import gg.vape.value.ValueSnapshot;

public class SnapshotValueAccessor<K, T extends Value<K, T>>
extends ValueAccessor {
    private final ValueSnapshot<T, K> snapshot;

    public Object getValue() {
        return this.snapshot.getValue();
    }

    public SnapshotValueAccessor(ValueSnapshot<T, K> valueSnapshot, Value<K, T> value) {
        super(value);
        this.snapshot = valueSnapshot;
    }

    public void setValue(Object value) {
        this.snapshot.setValue(value);
    }
}
