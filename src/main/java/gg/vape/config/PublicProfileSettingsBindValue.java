package gg.vape.config;

import com.google.gson.JsonObject;
import gg.vape.config.PublicProfileSettings;
import gg.vape.input.BindSet;
import gg.vape.value.BindValue;

public class PublicProfileSettingsBindValue
extends BindValue {
    final PublicProfileSettings settings;

    @Override
    public boolean loadJson(JsonObject object) {
        boolean loaded = super.loadJson(object);
        return loaded;
    }

    @Override
    public boolean isDefault() {
        return false;
    }

    public PublicProfileSettingsBindValue(PublicProfileSettings settings, Object owner, String name, BindSet bindSet) {
        super(owner, name, bindSet);
        this.settings = settings;
    }
}
