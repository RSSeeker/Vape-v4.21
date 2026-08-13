package gg.vape.module.render.hud;

import gg.vape.module.Mod;
import gg.vape.module.render.hud.HudModuleGroup;
import gg.vape.ui.click.frame.Frame;

public class HudModule
extends Mod {
    private final HudModuleGroup group;
    private final String key;
    private boolean showKeybindSetting;
    private final Class<? extends Frame> configFrameClass;

    public HudModule(String name, HudModuleGroup group, String key, Class<? extends Frame> configFrameClass) {
        this(name, group, key, configFrameClass, 0);
    }

    public Class<? extends Frame> getConfigFrameClass() {
        return this.configFrameClass;
    }

    @Override
    public void onBindActivated() {
    }

    public String getKey() {
        return this.key;
    }

    public HudModule(String name, HudModuleGroup group, String key, Class<? extends Frame> configFrameClass, int color) {
        super(name, color);
        this.group = group;
        this.key = key;
        this.configFrameClass = configFrameClass;
    }

    public boolean shouldShowKeybindSetting() {
        return this.showKeybindSetting;
    }

    public HudModule(String name, HudModuleGroup group, String key, int color) {
        this(name, group, key, null, color);
    }

    public void setShowKeybindSetting(boolean showKeybindSetting) {
        this.showKeybindSetting = showKeybindSetting;
    }

    public HudModule(String name, HudModuleGroup group, String key) {
        this(name, group, key, null);
    }

    public HudModuleGroup getGroup() {
        return this.group;
    }
}

