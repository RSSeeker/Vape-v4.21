package gg.vape.module.utility.inventory.cleaner;

import gg.vape.module.utility.inventory.cleaner.InventoryFilterConditionGroup;
import gg.vape.module.utility.inventory.cleaner.InventoryFilterConditionGroupBuilder;
import gg.vape.module.utility.inventory.cleaner.InventoryItemMatcher;
import gg.vape.module.utility.inventory.cleaner.InventoryItemMatcherPreset;
import java.util.ArrayList;
import java.util.List;

public class InventoryItemMatcherPresetBuilder {
    private String name;
    private final List<InventoryItemMatcher> matchers;
    private final List<InventoryFilterConditionGroup> conditionGroups = new ArrayList<InventoryFilterConditionGroup>();

    public InventoryItemMatcherPreset build() {
        return new InventoryItemMatcherPreset(this.name, this.conditionGroups, this.matchers);
    }

    public InventoryItemMatcherPresetBuilder() {
        this.matchers = new ArrayList<InventoryItemMatcher>();
    }

    public InventoryItemMatcherPresetBuilder addMatcher(InventoryItemMatcher matcher) {
        this.matchers.add(matcher);
        return this;
    }

    public InventoryItemMatcherPresetBuilder addConditionGroup(InventoryFilterConditionGroup conditionGroup) {
        this.conditionGroups.add(conditionGroup);
        return this;
    }

    public InventoryItemMatcherPresetBuilder name(String name) {
        this.name = name;
        return this;
    }

    public InventoryItemMatcherPresetBuilder addConditionGroup(InventoryFilterConditionGroupBuilder groupBuilder) {
        this.conditionGroups.add(groupBuilder.build());
        return this;
    }
}

