package gg.vape.wrapper.impl;

public class ItemBlock
extends Item {
    public Block C() {
        return new Block(ItemBlock.vapeInstance.getMappingsMapperCompat().hb.getBlock(this.I));
    }

    public ItemBlock(Object object) {
        super(object);
    }
}
