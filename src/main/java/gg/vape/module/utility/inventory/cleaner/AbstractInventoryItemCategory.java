package gg.vape.module.utility.inventory.cleaner;

import gg.vape.module.utility.inventory.cleaner.InventoryItemCategory;
import gg.vape.module.utility.inventory.cleaner.InventoryItemCategoryBuilder;
import gg.vape.module.utility.inventory.cleaner.InventoryItemMatcher;
import gg.vape.wrapper.impl.ItemStack;
import java.util.Comparator;
import java.util.List;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

public abstract class AbstractInventoryItemCategory
implements InventoryItemCategory {
    private final String name;
    @Nullable
    private final Comparator<ItemStack> comparator;
    private final String displayName;
    private final List<InventoryItemMatcher> matchers;
    private final String id;
    @Override
    public @UnmodifiableView List<InventoryItemMatcher> getMatchers() {
        return this.matchers;
    }

    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    protected AbstractInventoryItemCategory(InventoryItemCategoryBuilder<?> builder) {
        this.id = builder.getId();
        this.name = builder.getName();
        this.displayName = builder.getDisplayName();
        this.comparator = builder.getComparator();
        this.matchers = builder.getMatchers();
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public Comparator<ItemStack> getComparator() {
        return this.comparator;
    }

    @Override
    public String getName() {
        return this.name;
    }

}

