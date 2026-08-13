package gg.vape.module.utility.inventory.cleaner;

import gg.vape.module.utility.inventory.cleaner.InventoryItemMatcher;
import gg.vape.wrapper.impl.ItemStack;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

public class InventoryItemCategoryBuilder<T extends InventoryItemCategoryBuilder<T>> {
    private Comparator<ItemStack> comparator;
    private final List<InventoryItemMatcher> matchers = new ArrayList<InventoryItemMatcher>();
    private String displayName;
    private String id;
    private String name;

    public T withName(@NotNull String name) {
        this.name = name;
        return (T)this;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public T withDisplayName(@NotNull String displayName) {
        this.displayName = displayName;
        return (T)this;
    }

    public T addMatcher(InventoryItemMatcher matcher) {
        this.matchers.add(matcher);
        return (T)this;
    }

    public Comparator<ItemStack> getComparator() {
        return this.comparator;
    }

    public String getName() {
        return this.name;
    }

    public T withComparator(@Nullable Comparator<ItemStack> comparator) {
        this.comparator = comparator;
        return (T)this;
    }

    public T withId(@NotNull String id) {
        this.id = id;
        return (T)this;
    }

    public String getId() {
        return this.id;
    }

    public @UnmodifiableView List<InventoryItemMatcher> getMatchers() {
        return this.matchers;
    }

    protected void validate() {
        Objects.requireNonNull(this.id, "id");
        Objects.requireNonNull(this.name, "name");
    }
}
