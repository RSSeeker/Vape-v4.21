package gg.vape.module.utility.inventory.cleaner;

import gg.vape.module.utility.inventory.cleaner.InventoryFilterCondition;
import gg.vape.module.utility.inventory.cleaner.InventoryFilterConditionGroup;
import java.util.ArrayList;
import java.util.List;

public class InventoryFilterConditionGroupBuilder {
    private final List<InventoryFilterCondition<?>> conditions = new ArrayList();

    public InventoryFilterConditionGroupBuilder addCondition(InventoryFilterCondition<?> condition) {
        this.conditions.add(condition);
        return this;
    }

    public InventoryFilterConditionGroup build() {
        InventoryFilterConditionGroup group = new InventoryFilterConditionGroup();
        InventoryFilterConditionGroup.mutableConditions(group).addAll(this.conditions);
        return group;
    }
}

