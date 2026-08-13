package gg.vape.module.debug;

import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventRender3D;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.utils.render.ItemIconRenderer;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemStack;

public class RenderTestModule
extends Mod {
    private static final String MODULE_NAME;

    @EventHandler
    public void onRender3D(EventRender3D event) {
        ItemIconRenderer.renderItemStack(ItemStack.S(Item.T(1)), 10.0f, 1.0f, 16, 16);
        ItemIconRenderer.renderItemStack(ItemStack.S(Item.T(10)), 20.0f, 1.0f, 16, 16);
        ItemIconRenderer.renderItemStack(ItemStack.S(Item.T(11)), 30.0f, 1.0f, 16, 16);
    }

    static {
        MODULE_NAME = "Render Test Module";
    }

    public RenderTestModule() {
        super(MODULE_NAME, -1, Category.UTILITY);
    }

    @Override
    public void onEnable() {
    }
}

