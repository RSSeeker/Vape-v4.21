package gg.vape.module.render.freecam;

import gg.vape.Vape;
import gg.vape.event.EventListener;
import gg.vape.module.Mod;

public abstract class FreecamController<T extends Mod>
implements EventListener {
    protected static final Vape VAPE = Vape.INSTANCE;
    protected final T module;

    public FreecamController(T module) {
        this.module = module;
    }

    public T getModule() {
        return this.module;
    }

    public void onEnable() {
    }

    public void onDisable() {
    }
}

