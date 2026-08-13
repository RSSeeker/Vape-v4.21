package gg.vape.config;

import gg.vape.config.PublicProfileSettings;
import gg.vape.input.BindSet;

public class PublicProfileSettingsBindSet
extends BindSet {
    final PublicProfileSettings publicProfileSettings;

    @Override
    public void onBindActivated() {
    }

    public PublicProfileSettingsBindSet(PublicProfileSettings publicProfileSettings, int keyCode) {
        super(keyCode);
        this.publicProfileSettings = publicProfileSettings;
    }
}
