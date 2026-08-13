package gg.vape.value;

import gg.vape.value.ConditionalValue;
import gg.vape.value.ListValue;
import gg.vape.value.Value;
import gg.vape.value.ValueCondition;
import org.jetbrains.annotations.Nullable;

public class BooleanValue
extends ConditionalValue<Boolean, BooleanValue> {
    private static String[] legacyState;
    @Nullable
    private ListValue compactListValue = null;
    private final String displayName;

    public BooleanValue(Object owner, String name, boolean defaultValue) {
        this(owner, name, name, defaultValue);
    }

    @Override
    public void parse(String serializedValue) {
        if (serializedValue.isEmpty()) {
            return;
        }
        this.setValue(Boolean.parseBoolean(serializedValue));
    }

    public static void setBooleanLegacyState(String[] state) {
        legacyState = state;
    }

    @Override
    public ListValue getTerminalDependentValue() {
        return this.compactListValue;
    }

    @Override
    public boolean hasActiveDependentBranch() {
        return this.isEnabled();
    }

    public BooleanValue copyDefinition() {
        return BooleanValue.createFull(null, this.getId(), this.getDisplayName(), this.getEffectiveValue(), this.getDescription());
    }

    @Override
    public BooleanValue copyValueDefinition() {
        return this.copyDefinition();
    }

    public void setCompactListValue(ListValue listValue) {
        this.compactListValue = listValue;
    }

    public static BooleanValue createWithDisplayName(Object owner, String name, String displayName, boolean defaultValue) {
        return BooleanValue.createFull(owner, name, displayName, defaultValue, null);
    }

    @Override
    public boolean isDependentValueActive(Value dependentValue) {
        return this.isEnabled();
    }

    public BooleanValue(Object owner, String name, String displayName, boolean defaultValue) {
        super(owner, name, defaultValue);
        this.displayName = displayName;
    }

    public static BooleanValue create(Object owner, String name, boolean defaultValue, String description) {
        return BooleanValue.createFull(owner, name, name, defaultValue, description);
    }

    public Boolean getEffectiveValue() {
        if (this.isHidden()) {
            return false;
        }
        return (Boolean)super.getValue();
    }

    public boolean isEnabled() {
        return this.getEffectiveValue();
    }

    public static BooleanValue create(Object owner, String name, boolean defaultValue) {
        return BooleanValue.create(owner, name, defaultValue, null);
    }

    public ValueCondition getEnabledCondition() {
        return new ValueCondition().requireTrue(this);
    }

    public static BooleanValue createFull(Object owner, String name, String displayName, boolean defaultValue, String description) {
        BooleanValue booleanValue = new BooleanValue(owner, name, displayName, defaultValue);
        booleanValue.setDescription(description);
        return booleanValue;
    }

    public ValueCondition getDisabledCondition() {
        return new ValueCondition().requireFalse(this);
    }

    static {
        if (BooleanValue.getBooleanLegacyState() == null) {
            BooleanValue.setBooleanLegacyState(new String[3]);
        }
    }

    public boolean toggleIfValid() {
        boolean nextValue = !this.getEffectiveValue();
        if (this.isChangeValid(nextValue)) {
            this.setValue(nextValue);
            return true;
        }
        return false;
    }


    public void toggle() {
        this.setValue(!this.getEffectiveValue());
    }

    public static String[] getBooleanLegacyState() {
        return legacyState;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public Boolean getEffectiveValueCompat() {
        return this.getEffectiveValue();
    }
}
