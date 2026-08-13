package gg.vape.wrapper.impl;

public class EntityItem
extends Entity {
    public ItemStack getItemStack() {
        return new ItemStack(EntityItem.vapeInstance.getMappings().entityItem.getItem(this.I));
    }

    public EntityItem(Object entityItemHandle) {
        super(entityItemHandle);
    }
}
