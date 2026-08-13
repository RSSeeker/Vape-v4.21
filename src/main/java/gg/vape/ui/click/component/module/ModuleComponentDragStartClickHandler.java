package gg.vape.ui.click.component.module;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.MousePosition;
import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.component.module.ModuleComponent;
import gg.vape.utils.render.RenderUtils;

class ModuleComponentDragStartClickHandler
implements GuiClickListener {
    final ModuleComponent owner;

    ModuleComponentDragStartClickHandler(ModuleComponent owner) {
        this.owner = owner;
    }

    @Override
    public void onPrimaryClick() {
        MousePosition mousePosition = RenderUtils.h();
        this.owner.setLastDragMouseY(mousePosition.H);
        this.owner.setDragStartY(this.owner.double_n());
        this.owner.setDragging(true);
        ClientSettings.activeComponent = this.owner;
    }
}
