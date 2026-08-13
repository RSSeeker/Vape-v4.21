package gg.vape.module.none;

import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPreTick;
import gg.vape.module.Category;
import gg.vape.module.Mod;

public class SelfDestruct
extends Mod {
    @EventHandler
    public void onTick(EventPreTick eventPreTick) {
    }

    public SelfDestruct() {
        super("SelfDestruct", 1, Category.NONE, "You can press LEFT CONTROL + HOME on your keyboard to self destruct at any time, including while in a portal, GUI, or menu");
    }

    @Override
    public void onEnable() {
    }
}
