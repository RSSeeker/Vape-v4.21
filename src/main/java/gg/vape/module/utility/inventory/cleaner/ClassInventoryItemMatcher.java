package gg.vape.module.utility.inventory.cleaner;

import gg.vape.module.utility.inventory.cleaner.AbstractInventoryItemMatcher;
import gg.vape.module.utility.inventory.cleaner.ClassInventoryItemMatcherBuilder;
import gg.vape.module.utility.inventory.cleaner.InventoryItemCategory;
import gg.vape.module.utility.inventory.cleaner.InventoryItemMatcherBuilderBase;
import gg.vape.module.utility.inventory.cleaner.InventoryMatcherListMode;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemStack;
import java.util.ArrayList;
import java.util.List;

public class ClassInventoryItemMatcher
extends AbstractInventoryItemMatcher {
    private final List<Class<?>> classes = new ArrayList();
    private InventoryItemCategory category;
    private final InventoryMatcherListMode listMode;

    @Override
    public InventoryItemCategory getCategory() {
        return this.category;
    }

    @Override
    public boolean matches(ItemStack itemStack, Item item) {
        if (this.listMode == InventoryMatcherListMode.WHITELIST) {
            return this.classes.stream().anyMatch(item::isInstance);
        }
        return this.classes.stream().noneMatch(item::isInstance);
    }

    public static ClassInventoryItemMatcherBuilder builderFrom(InventoryItemMatcherBuilderBase<?> inventoryItemMatcherBuilderBase) {
        return new ClassInventoryItemMatcherBuilder(inventoryItemMatcherBuilderBase, null);
    }

    public ClassInventoryItemMatcher(ClassInventoryItemMatcherBuilder classInventoryItemMatcherBuilder) {
        super(classInventoryItemMatcherBuilder);
        this.classes.addAll(classInventoryItemMatcherBuilder.getClasses());
        this.listMode = classInventoryItemMatcherBuilder.getListMode();
    }

    @Override
    public void setCategory(InventoryItemCategory category) {
        this.category = category;
    }

}

