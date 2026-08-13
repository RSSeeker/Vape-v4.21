package gg.vape.module.utility.inventory.cleaner;

import gg.vape.module.utility.inventory.cleaner.InventoryItemCategoryBuilder;
import gg.vape.module.utility.inventory.cleaner.MatcherBackedInventoryItemCategoryBuilder;
import gg.vape.module.utility.inventory.cleaner.StackSizeInventoryItemCategoryBuilder;

public class DefaultInventoryItemCategoryBuilder
extends InventoryItemCategoryBuilder {
    public StackSizeInventoryItemCategoryBuilder stackSize() {
        return new StackSizeInventoryItemCategoryBuilder();
    }

    public MatcherBackedInventoryItemCategoryBuilder matcherBacked() {
        return new MatcherBackedInventoryItemCategoryBuilder();
    }
}
