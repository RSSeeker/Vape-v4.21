package gg.vape.module.utility.inventory.cleaner;

import gg.vape.module.utility.inventory.cleaner.InventoryItemCategoryBuilder;
import gg.vape.module.utility.inventory.cleaner.MatcherBackedInventoryItemCategory;

public class MatcherBackedInventoryItemCategoryBuilder
extends InventoryItemCategoryBuilder<MatcherBackedInventoryItemCategoryBuilder> {
    public MatcherBackedInventoryItemCategory build() {
        return new MatcherBackedInventoryItemCategory(this);
    }
}

