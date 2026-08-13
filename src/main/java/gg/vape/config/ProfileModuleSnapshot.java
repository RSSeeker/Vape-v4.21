package gg.vape.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gg.vape.input.BindActivationMode;
import gg.vape.input.BindSet;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.unmap.INamed;
import gg.vape.value.BindValue;
import gg.vape.value.Value;
import gg.vape.value.ValueSnapshot;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ProfileModuleSnapshot
implements INamed {
    private final Mod module;
    private final ValueSnapshot<BindValue, BindSet> bindSnapshot;
    private final List<ValueSnapshot<?, ?>> valueSnapshots;
    private final ProfileSnapshot profileSnapshot;
    private boolean enabled;
    private boolean visible;

    public int getSortPriority() {
        int priority = 0;
        if (this.hasBind()) {
            priority += 2;
        }
        if (this.isEnabled()) {
            ++priority;
        }
        if (this.hasBind() && this.isEnabled()) {
            ++priority;
        }
        return priority;
    }

    public void resetBind() {
        this.bindSnapshot.getValue().setBoundInputs(new ArrayList<Integer>());
        this.bindSnapshot.getValue().setActivationMode(BindActivationMode.TOGGLE);
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public List<ValueSnapshot<?, ?>> getValueSnapshots() {
        return this.valueSnapshots;
    }

    public Mod getModule() {
        return this.module;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public ProfileModuleSnapshot(ProfileSnapshot profileSnapshot, Mod module, JsonObject moduleJson) {
        JsonArray keybinds;
        this.profileSnapshot = profileSnapshot;
        this.module = module;
        this.valueSnapshots = new ArrayList<>();
        LinkedHashMap<String, JsonObject> valueJsonById = new LinkedHashMap<String, JsonObject>();
        if (moduleJson != null) {
            JsonArray valuesJson = moduleJson.getAsJsonArray("values");
            if (valuesJson != null) {
                for (JsonElement valueElement : valuesJson) {
                    if (valueElement.isJsonNull() || !valueElement.isJsonObject()) continue;
                    JsonObject valueJson = valueElement.getAsJsonObject();
                    String valueId = ConfigJsonUtils.getString(valueJson, "id");
                    if (valueId == null) continue;
                    valueJsonById.put(valueId, valueJson);
                }
            }
            if ((keybinds = ConfigJsonUtils.getJsonArray(moduleJson, "keybinds_2")) == null) {
                keybinds = new JsonArray();
            }
            Boolean visible = ConfigJsonUtils.getBoolean(moduleJson, "visible");
            this.visible = visible != null ? visible.booleanValue() : module.isDefaultVisible();
        } else {
            keybinds = new JsonArray();
            this.visible = module.isDefaultVisible();
        }
        this.bindSnapshot = new ValueSnapshot<>(new BindValue((Object)null, "", new BindSet(ConfigJsonUtils.parseInputCodes(keybinds, false), false, module.getBind().supportsActivationMode())));
        String bindMode = moduleJson == null ? null : ConfigJsonUtils.getString(moduleJson, "bind_mode");
        if (bindMode != null && this.bindSnapshot.getValue().supportsActivationMode()) {
            try {
                this.bindSnapshot.getValue().setActivationMode(BindActivationMode.valueOf(bindMode));
            }
            catch (IllegalArgumentException illegalArgumentException) {
                this.bindSnapshot.getValue().setActivationMode(BindActivationMode.TOGGLE);
            }
        }
        for (Value<?, ?> value : module.getAllValues()) {
            JsonObject valueJson = valueJsonById.get(value.getId());
            if (valueJson == null) {
                this.valueSnapshots.add(new ValueSnapshot<>(value));
                continue;
            }
            ValueSnapshot<?, ?> valueSnapshot = new ValueSnapshot<>(value);
            valueSnapshot.loadJson(valueJson);
            this.valueSnapshots.add(valueSnapshot);
        }
        if (this.profileSnapshot.getProfile() != null) {
            this.enabled = this.profileSnapshot.getProfile().getEnabledModules().contains(this.module);
        }
    }

    @Override
    public String getName() {
        return this.module.getName();
    }

    public boolean hasChanges() {
        if (this.module.getCategory() == Category.NONE) {
            return false;
        }
        for (ValueSnapshot<?, ?> valueSnapshot : this.getValueSnapshots()) {
            if (valueSnapshot.isDefault()) continue;
            return true;
        }
        if (this.hasBind()) {
            return true;
        }
        return this.isEnabled();
    }

    public List<ValueSnapshot<?, ?>> getValues(boolean includeDefaults) {
        ArrayList<ValueSnapshot<?, ?>> values = new ArrayList<>();
        for (ValueSnapshot<?, ?> valueSnapshot : this.getValueSnapshots()) {
            if (valueSnapshot.isDefault() && !includeDefaults) continue;
            values.add(valueSnapshot);
        }
        return values;
    }

    public boolean hasBind() {
        return this.bindSnapshot.getValue().hasValidBinding();
    }

    public JsonObject toJson() {
        JsonObject moduleJson = new JsonObject();
        moduleJson.addProperty("name", this.module.getName());
        if (this.module.getBind().usesOwnKeybindStorage()) {
            if (this.hasBind()) {
                moduleJson.add("keybinds_2", this.bindSnapshot.getValue().serializeBoundInputs());
            }
            if (this.bindSnapshot.getValue().supportsActivationMode() && this.bindSnapshot.getValue().getActivationMode() != BindActivationMode.TOGGLE) {
                moduleJson.addProperty("bind_mode", this.bindSnapshot.getValue().getActivationMode().name());
            }
        }
        JsonArray valuesJson = new JsonArray();
        for (ValueSnapshot<?, ?> valueSnapshot : this.valueSnapshots) {
            JsonObject valueJson;
            if (!((Value)valueSnapshot.getSourceValue()).isSerializable() || valueSnapshot.isDefault() || (valueJson = valueSnapshot.toJson()).entrySet().size() <= 1) continue;
            valuesJson.add(valueSnapshot.toJson());
        }
        if (valuesJson.size() != 0) {
            moduleJson.add("values", (JsonElement)valuesJson);
        }
        if (this.visible != this.module.isDefaultVisible()) {
            moduleJson.addProperty("visible", Boolean.valueOf(this.visible));
        }
        if (moduleJson.entrySet().size() == 1) {
            return null;
        }
        return moduleJson;
    }

    public String getBindDisplayText() {
        return this.bindSnapshot.getValue().getBindText();
    }

    public ValueSnapshot<BindValue, BindSet> getBindSnapshot() {
        return this.bindSnapshot;
    }
}
