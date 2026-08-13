package gg.vape.unmap;

import gg.vape.config.ClientSettings;
import gg.vape.input.BindActivationMode;
import gg.vape.module.Mod;

public class ModBendable
extends Bendable {
    private static final String BIND_DISPLAY_FORMAT;
    private BindActivationMode activationMode = BindActivationMode.TOGGLE;
    private static String[] legacyStrings;
    private final Mod module;

    public static void setLegacyStrings(String[] strings) {
        legacyStrings = strings;
    }

    @Override
    public BindActivationMode getActivationMode() {
        return this.activationMode;
    }

    @Override
    public void onBindActivated() {
        this.module.onBindActivated();
    }

    public ModBendable(Mod module) {
        this.module = module;
    }

    @Override
    public boolean handleInput(int inputCode, boolean pressed) {
        if (this.getActivationMode() == BindActivationMode.TOGGLE) {
            return super.handleInput(inputCode, pressed);
        }
        if (!this.containsInput(inputCode)) {
            return false;
        }
        if (pressed) {
            if (this.areOtherInputsDown(inputCode)) {
                if (!this.module.isEnabled()) {
                    this.module.setEnabled(true);
                }
                return true;
            }
            return false;
        }
        if (this.module.isEnabled()) {
            this.module.setEnabled(false);
            return true;
        }
        return false;
    }

    @Override
    public boolean supportsActivationMode() {
        return true;
    }

    @Override
    public boolean isActive() {
        return this.module.k();
    }

    @Override
    public String getDisplayText() {
        return String.format(BIND_DISPLAY_FORMAT, ClientSettings.FORMAT_CODE, ClientSettings.FORMAT_CODE, this.getBindText(), ClientSettings.FORMAT_CODE, ClientSettings.FORMAT_CODE, this.module.getName());
    }

    static {
        ModBendable.setLegacyStrings(new String[1]);
        BIND_DISPLAY_FORMAT = " %s7[%sr%s%s7]%sr %s";
    }


    private boolean areOtherInputsDown(int triggeringInput) {
        for (int boundInput : this.getBoundInputs()) {
            if (boundInput == triggeringInput || ClientSettings.isInputDown(boundInput)) continue;
            return false;
        }
        return true;
    }

    public static String[] getLegacyStrings() {
        return legacyStrings;
    }

    @Override
    public void setActivationMode(BindActivationMode activationMode) {
        this.activationMode = activationMode == null ? BindActivationMode.TOGGLE : activationMode;
    }
}

