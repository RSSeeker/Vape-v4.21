package gg.vape.unmap;

import gg.vape.unmap.PropertyContainerBooleanKeyA;
import gg.vape.unmap.PropertyContainerBooleanKeyB;
import gg.vape.unmap.PropertyContainerBooleanKeyC;
import gg.vape.unmap.PropertyKey;
import java.util.HashMap;

public class PropertyContainer {
    public static PropertyKey<Boolean> LEGACY_FLAG;
    public static PropertyKey<Boolean> BETA_BADGE;
    private static String legacyLabel;
    public static PropertyKey<Boolean> NEW_BADGE;
    private HashMap<PropertyKey<?>, Object> properties = new HashMap();

    public <T> void putProperty(PropertyKey<T> propertyKey, T value) {
        this.properties.put(propertyKey, value);
    }

    public <T> T getProperty(PropertyKey<T> propertyKey) {
        if (!this.properties.containsKey(propertyKey)) {
            return propertyKey.getDefaultValue();
        }
        return (T)this.properties.get(propertyKey);
    }

    public static String getLabel() {
        return legacyLabel;
    }

    public static void setLabel(String label) {
        legacyLabel = label;
    }


    static {
        NEW_BADGE = new PropertyContainerBooleanKeyA();
        BETA_BADGE = new PropertyContainerBooleanKeyB();
        PropertyContainer.setLabel("m4RdLb");
        LEGACY_FLAG = new PropertyContainerBooleanKeyC();
    }
}
