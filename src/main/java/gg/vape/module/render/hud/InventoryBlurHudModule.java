package gg.vape.module.render.hud;

import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventGuiOpen;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.render.hud.HudModule;
import gg.vape.module.render.hud.HudModuleGroup;
import gg.vape.render.ShaderGroupRenderStateManager;

public class InventoryBlurHudModule
extends HudModule {
    public InventoryBlurHudModule() {
        super("Inventory Blur", HudModuleGroup.GAME, "inventory_blur");
        this.setSuffix("Blurs the background while in an inventory");
    }


    @EventHandler
    public void onGuiOpen(EventGuiOpen event) {
        if (event.getGuiScreen().isNull() || event.getGuiScreen().isInstance(MappedClasses.qo) || event.getGuiScreen().isInstance(MappedClasses.Fl) || MappedClasses.zL != null && event.getGuiScreen().isInstance(MappedClasses.zL)) {
            ShaderGroupRenderStateManager.getInstance().disable();
            return;
        }
        ShaderGroupRenderStateManager.getInstance().enable();
    }
}

