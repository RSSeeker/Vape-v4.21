package gg.vape.ui.click.component.module;

import gg.vape.module.Mod;
import gg.vape.ui.click.component.GuiClickListener;

class ModuleComponentToggleModEnabledClickHandler
implements GuiClickListener {
    final Mod module;
    final ModuleComponent owner;


    @Override
    public void onPrimaryClick() {
        this.module.K(!this.module.isFavorite());
    }

    ModuleComponentToggleModEnabledClickHandler(ModuleComponent moduleComponent, Mod mod) {
        this.owner = moduleComponent;
        this.module = mod;
    }
}

