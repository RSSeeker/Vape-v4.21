package gg.vape.module.utility.inventory.cleaner;

import gg.vape.module.utility.inventory.cleaner.AbstractInventoryItemCategory;
import gg.vape.module.utility.inventory.cleaner.ComparisonOperator;
import gg.vape.module.utility.inventory.cleaner.ItemFilterSelection;
import gg.vape.module.utility.inventory.cleaner.StackSizeInventoryItemCategoryBuilder;
import gg.vape.wrapper.impl.ItemStack;

public class StackSizeInventoryItemCategory
extends AbstractInventoryItemCategory {
    private final ComparisonOperator operator;
    private final int stackSize;


    @Override
    public boolean isCompatible(ItemFilterSelection cn_22) {
        ItemStack itemStack = cn_22.getItemStack();
        if (itemStack == null) {
            return false;
        }
        return this.operator.compare(itemStack.P(), this.stackSize);
    }

    StackSizeInventoryItemCategory(StackSizeInventoryItemCategoryBuilder stackSizeInventoryItemCategoryBuilder) {
        super(stackSizeInventoryItemCategoryBuilder);
        this.stackSize = stackSizeInventoryItemCategoryBuilder.getStackSize();
        this.operator = stackSizeInventoryItemCategoryBuilder.getOperator();
    }
}

