package gg.vape.module.none;

import com.google.gson.JsonObject;
import gg.vape.module.Mod;
import gg.vape.value.Value;

public class ConfigSettingsModule
extends Mod {
    public void loadMatchingValues(JsonObject jsonObject) {
        for (Value<?, ?> value : this.getAllValues()) {
            if (!value.matchesJsonId(jsonObject)) continue;
            value.loadJson(jsonObject);
        }
    }

    public ConfigSettingsModule(String name) {
        super(name);
    }

}

