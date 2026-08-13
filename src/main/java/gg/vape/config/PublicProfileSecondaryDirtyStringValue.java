package gg.vape.config;

import gg.vape.config.PublicProfileSettings;
import gg.vape.value.StringValue;

public class PublicProfileSecondaryDirtyStringValue
extends StringValue {
    final PublicProfileSettings settings;
    boolean dirty;

    public PublicProfileSecondaryDirtyStringValue(PublicProfileSettings settings, Object owner, String name, String defaultValue) {
        super(owner, name, defaultValue);
        this.settings = settings;
    }


    @Override
    public void notifyChangeListeners() {
        if (this.dirty) {
            return;
        }
        this.dirty = true;
    }
}
