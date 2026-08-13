package gg.vape.module.utility.inventory.cleaner;

import gg.vape.module.utility.inventory.cleaner.AbstractInventoryItemCategory;
import gg.vape.module.utility.inventory.cleaner.InventoryItemMatcher;
import gg.vape.module.utility.inventory.cleaner.ItemFilterSelection;
import gg.vape.module.utility.inventory.cleaner.MatcherBackedInventoryItemCategoryBuilder;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemStack;

public class MatcherBackedInventoryItemCategory
extends AbstractInventoryItemCategory {
    @Override
    public boolean isCompatible(ItemFilterSelection selection) {
        InventoryItemMatcher selectedMatcher = selection.getMatcher();
        ItemStack itemStack = selection.getItemStack();
        if (this.getMatchers().isEmpty()) {
            return true;
        }
        if (selectedMatcher != null && !this.getMatchers().contains(selectedMatcher)) {
            return false;
        }
        if (itemStack == null) {
            return selectedMatcher != null;
        }
        Item item = itemStack.getItem();
        return this.getMatchers().stream().anyMatch(matcher -> matcher.matches(itemStack, item));
    }


    public MatcherBackedInventoryItemCategory(MatcherBackedInventoryItemCategoryBuilder matcherBackedInventoryItemCategoryBuilder) {
        super(matcherBackedInventoryItemCategoryBuilder);
    }
}
