package gg.vape.wrapper.impl;

public class ItemActionResult
extends EnumActionResult {
    public static ItemActionResult create(GlStateManagerTexGenCoord swingSource, ItemRenderContext itemContext) {
        return new ItemActionResult(ItemActionResult.vapeInstance.getMappingsMapperCompat().itemActionResult.create(swingSource.getObject(), itemContext.getObject()));
    }

    public boolean consumesAction() {
        return ItemActionResult.vapeInstance.getMappingsMapperCompat().itemActionResult.consumesAction(this.getObject());
    }

    public ItemActionResult withoutItem() {
        return new ItemActionResult(ItemActionResult.vapeInstance.getMappingsMapperCompat().itemActionResult.withoutItem(this.getObject()));
    }

    public ItemStack getHeldItemTransformedTo() {
        return new ItemStack(ItemActionResult.vapeInstance.getMappingsMapperCompat().itemActionResult.getHeldItemTransformedTo(this.getObject()));
    }

    public ItemActionResult heldItemTransformedTo(ItemStack itemStack) {
        return new ItemActionResult(ItemActionResult.vapeInstance.getMappingsMapperCompat().itemActionResult.heldItemTransformedTo(this.getObject(), itemStack.getObject()));
    }

    public boolean wasItemInteraction() {
        return ItemActionResult.vapeInstance.getMappingsMapperCompat().itemActionResult.wasItemInteraction(this.getObject());
    }

    public ItemActionResult(Object handle) {
        super(handle);
    }
}
