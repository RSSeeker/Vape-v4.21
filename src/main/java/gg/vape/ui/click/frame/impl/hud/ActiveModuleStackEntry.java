package gg.vape.ui.click.frame.impl.hud;

import gg.vape.module.Mod;
import gg.vape.module.ModuleDisplayInfo;

public class ActiveModuleStackEntry {
    public final Mod module;
    public final ModuleDisplayInfo displayInfo;

    public ActiveModuleStackEntry(Mod mod, ModuleDisplayInfo moduleDisplayInfo) {
        this.module = mod;
        this.displayInfo = moduleDisplayInfo;
    }
}
