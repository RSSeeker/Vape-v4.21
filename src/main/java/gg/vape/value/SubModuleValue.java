package gg.vape.value;

import gg.vape.module.Mod;
import gg.vape.module.SubModule;
import gg.vape.unmap.ModeOption;

public class SubModuleValue<T extends SubModule>
extends ModeOption {
    private final T instance;

    public SubModuleValue(String name, T instance) {
        super(name);
        this.instance = instance;
    }

    public SubModuleValue(T instance) {
        this(((Mod)instance).getName(), instance);
    }

    public T getInstance() {
        return this.instance;
    }
}
