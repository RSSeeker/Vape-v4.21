package gg.vape.module.utility.inventory.cleaner;

import gg.vape.module.utility.inventory.cleaner.AbstractInventoryItemMatcher;
import gg.vape.module.utility.inventory.cleaner.InventoryItemMatcher;
import gg.vape.module.utility.inventory.cleaner.InventoryItemMatcherBuilder;
import gg.vape.module.utility.inventory.cleaner.InventoryItemMatcherBuilderBase;
import gg.vape.module.utility.inventory.cleaner.InventoryItemMatcherGroup;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemStack;

public class EmptySlotInventoryItemMatcher
extends AbstractInventoryItemMatcher {
    public static final EmptySlotInventoryItemMatcher EMPTY_SLOT = new EmptySlotInventoryItemMatcher();


    EmptySlotInventoryItemMatcher() {
        super((InventoryItemMatcherBuilderBase<?>)((InventoryItemMatcherBuilder)((InventoryItemMatcherBuilder)InventoryItemMatcher.builder().withName("Hand")).withDescription("No item")).withGroup(InventoryItemMatcherGroup.HIDDEN));
    }

    @Override
    public boolean matches(ItemStack itemStack, Item item) {
        return itemStack == null || itemStack.isNull() || item == null || item.isNull();
    }
}

