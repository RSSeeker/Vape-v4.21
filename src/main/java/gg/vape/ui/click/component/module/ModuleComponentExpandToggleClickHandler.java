package gg.vape.ui.click.component.module;

import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.component.module.ModuleComponent;
import gg.vape.ui.click.frame.impl.ModuleCategoryFrame;

class ModuleComponentExpandToggleClickHandler
implements GuiClickListener {
    final ModuleCategoryFrame categoryFrame;
    final ModuleComponent owner;


    @Override
    public void onSecondaryClick() {
        this.onPrimaryClick();
    }

    @Override
    public void onPrimaryClick() {
        this.owner.setExpanded(!this.owner.isExpanded());
        if (this.owner.isExpanded()) {
            this.categoryFrame.G(this.owner.getModule());
            this.owner.expandValueComponents();
        } else {
            this.categoryFrame.G(null);
            this.owner.collapseValueComponents();
        }
        this.categoryFrame.l$src$V$1mibm4x();
    }

    ModuleComponentExpandToggleClickHandler(ModuleComponent moduleComponent, ModuleCategoryFrame moduleCategoryFrame) {
        this.owner = moduleComponent;
        this.categoryFrame = moduleCategoryFrame;
    }
}

