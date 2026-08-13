package gg.vape.value;

import com.google.gson.JsonObject;
import gg.vape.value.Value;
import java.util.Arrays;
import java.util.Objects;

public class ValueSnapshot<T extends Value<R, ?>, R> {
    private final T sourceValue;
    private R value;

    public void setValue(Object object) {
        if (object instanceof Object[]) {
            this.value = (R)Arrays.copyOf((Object[])object, ((Object[])object).length);
            return;
        }
        this.value = (R)object;
    }

    public R getValue() {
        return this.value;
    }

    public boolean isDefault() {
        R defaultValue = this.sourceValue.getDefaultValue();
        if (defaultValue instanceof Object[]) {
            return Arrays.equals((Object[])defaultValue, (Object[])this.value);
        }
        if (defaultValue instanceof double[]) {
            return Arrays.equals((double[])defaultValue, (double[])this.value);
        }
        return Objects.equals(defaultValue, this.value);
    }

    public T getSourceValue() {
        return this.sourceValue;
    }

    public void loadJson(JsonObject jsonObject) {
        this.sourceValue.setPersistenceSuppressed(true);
        R originalValue = this.sourceValue.getValueCompat();
        this.sourceValue.reset();
        if (this.sourceValue.loadJson(jsonObject)) {
            this.value = this.sourceValue.getValueCompat();
        }
        this.sourceValue.setValue(originalValue);
        this.sourceValue.setPersistenceSuppressed(false);
    }

    public ValueSnapshot(T sourceValue) {
        this.sourceValue = sourceValue;
        this.value = sourceValue.getDefaultValue();
    }

    public JsonObject toJson() {
        R originalValue = this.sourceValue.getValueCompat();
        this.sourceValue.setValue(this.getValue());
        JsonObject jsonObject = this.sourceValue.toJson(false);
        this.sourceValue.setValue(originalValue);
        return jsonObject;
    }
}
