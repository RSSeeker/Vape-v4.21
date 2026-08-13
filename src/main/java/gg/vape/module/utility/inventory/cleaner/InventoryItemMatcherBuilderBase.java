package gg.vape.module.utility.inventory.cleaner;

import gg.vape.module.utility.inventory.cleaner.InventoryItemMatchContext;
import gg.vape.module.utility.inventory.cleaner.InventoryItemMatcherGroup;
import java.util.Comparator;
import org.jetbrains.annotations.Nullable;

public class InventoryItemMatcherBuilderBase<T extends InventoryItemMatcherBuilderBase<T>> {
    @Nullable
    private Comparator<InventoryItemMatchContext> comparator;
    private String name;
    private InventoryItemMatcherGroup group;
    private String id;
    private String iconName;
    @Nullable
    private String description;

    public T withGroup(InventoryItemMatcherGroup group) {
        this.group = group;
        return (T)this;
    }

    public T withName(String name) {
        this.name = name;
        return (T)this;
    }

    @Nullable
    public Comparator<InventoryItemMatchContext> getComparator() {
        return this.comparator;
    }

    public InventoryItemMatcherGroup getGroup() {
        return this.group;
    }

    public String getName() {
        return this.name;
    }

    public String getIconName() {
        return this.iconName;
    }

    public String getDescription() {
        return this.description;
    }


    protected InventoryItemMatcherBuilderBase(InventoryItemMatcherBuilderBase<?> qq_12) {
        this.id = qq_12.getId();
        this.name = qq_12.getName();
        this.iconName = qq_12.getIconName();
        this.description = qq_12.getDescription();
        this.group = qq_12.getGroup();
        this.comparator = qq_12.getComparator();
    }

    public T withId(String id) {
        this.id = id;
        return (T)this;
    }

    public T withComparator(@Nullable Comparator<InventoryItemMatchContext> comparatorArg) {
        this.comparator = comparatorArg;
        return (T)this;
    }

    public T withIconName(String iconName) {
        this.iconName = iconName;
        return (T)this;
    }

    protected InventoryItemMatcherBuilderBase() {
    }

    public String getId() {
        return this.id;
    }

    public T withDescription(String description) {
        this.description = description;
        return (T)this;
    }

}

