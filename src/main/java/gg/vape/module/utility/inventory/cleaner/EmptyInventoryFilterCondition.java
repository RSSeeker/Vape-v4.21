package gg.vape.module.utility.inventory.cleaner;

import com.google.gson.JsonObject;
import gg.vape.module.utility.inventory.cleaner.InventoryFilterCondition;
import gg.vape.module.utility.inventory.cleaner.InventoryFilterConditionType;
import gg.vape.wrapper.impl.ItemStack;

public class EmptyInventoryFilterCondition
implements InventoryFilterCondition<EmptyInventoryFilterCondition> {
    @Override
    public EmptyInventoryFilterCondition copy() {
        return new EmptyInventoryFilterCondition();
    }
    @Override
    public JsonObject toJson() {
        return null;
    }

    @Override
    public InventoryFilterConditionType getType() {
        return null;
    }

    @Override
    public boolean matches(ItemStack itemStack) {
        return false;
    }

}
