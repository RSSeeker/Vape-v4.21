package gg.vape.value;

import gg.vape.value.Value;

public interface ValueChangeListener<V extends Value<?, V>> {
    public void onValueChanged(V value);
}
