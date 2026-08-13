package gg.vape.value;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gg.vape.input.BindSet;
import gg.vape.value.Value;

public class BindValue
extends Value<BindSet, BindValue> {
    public BindValue(Object owner, String name, BindSet bindSet) {
        super(owner, name, bindSet);
    }


    public BindValue copyDefinition() {
        return new BindValue((Object)null, this.getId(), (BindSet)this.getValue());
    }

    @Override
    public BindValue copyValueDefinition() {
        return this.copyDefinition();
    }

    @Override
    public void parse(String serializedValue) {
    }

    @Override
    public JsonObject toJson(boolean includeValue) {
        JsonObject jsonObject = super.toJson(includeValue);
        jsonObject.add("binds", (JsonElement)((BindSet)this.getValue()).serializeBoundInputs());
        jsonObject.add("value", null);
        return jsonObject;
    }

    @Override
    public boolean isDefault() {
        for (Integer defaultKey : ((BindSet)this.getValue()).getDefaultKeys()) {
            if (((BindSet)this.getValue()).getBoundInputs().contains(defaultKey)) continue;
            return false;
        }
        return ((BindSet)this.getValue()).getBoundInputs().size() == ((BindSet)this.getValue()).getDefaultKeys().size();
    }

    @Override
    public boolean loadJson(JsonObject jsonObject) {
        JsonArray binds = jsonObject.getAsJsonArray("binds");
        ((BindSet)this.getValue()).loadBoundInputs(binds, false);
        return super.loadJson(jsonObject);
    }

    public static BindValue createWithKey(Object owner, String name, int keyCode) {
        return new BindValue(owner, name, new BindSet(keyCode));
    }

    public static BindValue createEmpty(Object owner, String name) {
        return new BindValue(owner, name, new BindSet());
    }
}
