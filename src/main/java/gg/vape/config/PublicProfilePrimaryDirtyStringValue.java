package gg.vape.config;

import gg.vape.config.PublicProfileSettings;
import gg.vape.value.StringValue;

public class PublicProfilePrimaryDirtyStringValue
extends StringValue {
    final PublicProfileSettings settings;
    boolean dirty;

    @Override
    public void notifyChangeListeners() {
        if (this.dirty) {
            return;
        }
        this.dirty = true;
    }

    public PublicProfilePrimaryDirtyStringValue(PublicProfileSettings settings, Object owner, String name, String defaultValue) {
        super(owner, name, defaultValue);
        this.settings = settings;
    }

}
