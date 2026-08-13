package gg.vape.input;

import gg.vape.input.BindActivationMode;
import gg.vape.unmap.Bendable;
import gg.vape.unmap.BendableInputDispatcher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BindSet
extends Bendable {
    private final List<Integer> defaultKeys;
    private final boolean supportsActivationMode;
    private BindActivationMode activationMode = BindActivationMode.TOGGLE;

    public BindSet(List<Integer> keys, boolean registerForInput, boolean supportsActivationMode) {
        this.setBoundInputs(keys);
        if (registerForInput) {
            BendableInputDispatcher.register(this);
        }
        this.supportsActivationMode = supportsActivationMode;
        this.defaultKeys = new ArrayList<Integer>(this.getBoundInputs());
    }

    @Override
    public void setActivationMode(BindActivationMode activationMode) {
        this.activationMode = activationMode == null ? BindActivationMode.TOGGLE : activationMode;
    }

    public BindSet(List<Integer> keys, boolean registerForInput) {
        this(keys, registerForInput, false);
    }

    @Override
    public boolean supportsActivationMode() {
        return this.supportsActivationMode;
    }

    @Override
    public BindActivationMode getActivationMode() {
        return this.activationMode;
    }

    @Override
    public void onBindActivated() {
    }

    public BindSet() {
        this.setBoundInputs(new ArrayList<Integer>());
        BendableInputDispatcher.register(this);
        this.supportsActivationMode = false;
        this.defaultKeys = new ArrayList<Integer>(this.getBoundInputs());
    }


    @Override
    public String getDisplayText() {
        return this.getBindText();
    }

    @Override
    public boolean isActive() {
        return false;
    }

    public BindSet(int keyCode) {
        this.setBoundInputs(Arrays.asList(keyCode));
        BendableInputDispatcher.register(this);
        this.supportsActivationMode = false;
        this.defaultKeys = new ArrayList<Integer>(this.getBoundInputs());
    }

    public List<Integer> getDefaultKeys() {
        return this.defaultKeys;
    }
}

