package gg.vape.value;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gg.vape.utils.Base64Util;
import gg.vape.value.Value;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class StringMapValue
extends Value<Map<String, String>, StringMapValue> {
    private final String valuePlaceholder;
    private final String name;
    private final String keyPlaceholder;

    public StringMapValue copyDefinition() {
        return new StringMapValue(null, this.name, this.keyPlaceholder, this.valuePlaceholder);
    }

    @Override
    public StringMapValue copyValueDefinition() {
        return this.copyDefinition();
    }

    @Override
    public JsonObject toJson(boolean includeValue) {
        JsonObject jsonObject = super.toJson(includeValue);
        JsonObject encodedEntries = new JsonObject();
        for (Map.Entry<String, String> entry : this.getValue().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            String encodedKey = "b64:" + Base64Util.encodeUtf8Base64(key);
            String encodedValue = "b64:" + Base64Util.encodeUtf8Base64(value);
            encodedEntries.addProperty(encodedKey, encodedValue);
        }
        jsonObject.add("data", (JsonElement)encodedEntries);
        jsonObject.remove("value");
        return jsonObject;
    }

    public static StringMapValue create(Object owner, String name, String keyPlaceholder, String valuePlaceholder) {
        return new StringMapValue(owner, name, keyPlaceholder, valuePlaceholder);
    }

    public String getKeyPlaceholder() {
        return this.keyPlaceholder;
    }

    @Override
    public boolean loadJson(JsonObject jsonObject) {
        if (!jsonObject.has("data")) {
            return false;
        }
        JsonObject encodedEntries = (JsonObject)jsonObject.get("data");
        Set<Map.Entry<String, JsonElement>> entries = encodedEntries.entrySet();
        this.getValue().clear();
        for (Map.Entry<String, JsonElement> entry : entries) {
            String key = entry.getKey();
            String value = entry.getValue().getAsString();
            if (key.startsWith("b64:")) {
                key = Base64Util.decodeUtf8Base64(key.split(":")[1]);
                value = Base64Util.decodeUtf8Base64(value.split(":")[1]);
            }
            this.putEntry(key, value);
        }
        return true;
    }

    public void putEntry(String key, String value) {
        this.getValue().put(key, value);
    }

    @Override
    public void parse(String serializedValue) {
    }

    @Override
    public void reset() {
        if (this.isResettable()) {
            this.setValue(new LinkedHashMap());
        }
    }

    public String getValuePlaceholder() {
        return this.valuePlaceholder;
    }

    public void removeEntry(String key) {
        this.getValue().remove(key);
    }

    @Override
    public boolean isDefault() {
        Map<String, String> currentEntries = this.getValue();
        Map<String, String> defaultEntries = this.getDefaultValue();
        return currentEntries.size() == defaultEntries.size() && currentEntries.equals(defaultEntries);
    }

    public StringMapValue(Object owner, String name, String keyPlaceholder, String valuePlaceholder) {
        super(owner, name, new LinkedHashMap());
        this.setValue(new LinkedHashMap());
        this.name = name;
        this.keyPlaceholder = keyPlaceholder;
        this.valuePlaceholder = valuePlaceholder;
    }


    @Override
    public String getDisplayValue() {
        Map<String, String> entries = this.getValue();
        if (entries == null || entries.isEmpty()) {
            return "";
        }
        return entries.size() + " entries";
    }
}
