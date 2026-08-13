package gg.vape.module.utility.inventory.cleaner;

import gg.vape.module.utility.inventory.cleaner.InventoryItemMatchContext;
import gg.vape.module.utility.inventory.cleaner.InventoryItemMatcher;
import gg.vape.module.utility.inventory.cleaner.InventoryItemMatcherBuilderBase;
import gg.vape.module.utility.inventory.cleaner.InventoryItemMatcherGroup;
import java.util.Comparator;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractInventoryItemMatcher
implements InventoryItemMatcher {
    @Nullable
    private final Comparator<InventoryItemMatchContext> comparator;
    private final String id;
    private final InventoryItemMatcherGroup group;
    @Nullable
    private final String iconName;
    private final String name;
    @Nullable
    private final String description;

    @Override
    public InventoryItemMatcherGroup getGroup() {
        return this.group;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    public AbstractInventoryItemMatcher(InventoryItemMatcherBuilderBase<?> inventoryItemMatcherBuilderBase) {
        this.id = inventoryItemMatcherBuilderBase.getId();
        this.name = inventoryItemMatcherBuilderBase.getName();
        this.description = inventoryItemMatcherBuilderBase.getDescription();
        this.iconName = inventoryItemMatcherBuilderBase.getIconName();
        this.group = inventoryItemMatcherBuilderBase.getGroup();
        this.comparator = inventoryItemMatcherBuilderBase.getComparator();
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    @Nullable
    public String getIconName() {
        return this.iconName;
    }

    @Override
    @Nullable
    public Comparator<InventoryItemMatchContext> getComparator() {
        return this.comparator;
    }
}
