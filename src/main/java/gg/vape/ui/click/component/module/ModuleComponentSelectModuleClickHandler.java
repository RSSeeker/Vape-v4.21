package gg.vape.ui.click.component.module;

import gg.vape.Vape;
import gg.vape.module.Mod;
import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.component.module.ModuleComponent;
import gg.vape.ui.click.frame.impl.ModuleCategoryFrame;

class ModuleComponentSelectModuleClickHandler
implements GuiClickListener {
    final Mod module;
    final ModuleComponent owner;
    final ModuleCategoryFrame categoryFrame;

    ModuleComponentSelectModuleClickHandler(ModuleComponent moduleComponent, Mod mod, ModuleCategoryFrame moduleCategoryFrame) {
        this.owner = moduleComponent;
        this.module = mod;
        this.categoryFrame = moduleCategoryFrame;
    }

    @Override
    public void onPrimaryClick() {
        Vape.INSTANCE.getModuleProfileMetadataCodec().removeModule(this.module);
        this.categoryFrame.l$src$Z$193vdc5();
    }
}
