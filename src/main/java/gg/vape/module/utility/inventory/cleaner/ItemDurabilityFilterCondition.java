package gg.vape.module.utility.inventory.cleaner;

import com.google.gson.JsonObject;
import gg.vape.module.utility.inventory.cleaner.ComparisonOperator;
import gg.vape.module.utility.inventory.cleaner.DurabilityValueMode;
import gg.vape.module.utility.inventory.cleaner.InventoryFilterConditionType;
import gg.vape.module.utility.inventory.cleaner.NumericFilterCondition;
import gg.vape.wrapper.impl.ItemStack;

public class ItemDurabilityFilterCondition
implements NumericFilterCondition<ItemDurabilityFilterCondition> {
    @Override
    public ItemDurabilityFilterCondition parseValue(String value) throws NumberFormatException {
        return this.parseDamage(value);
    }

    private int damage;
    private DurabilityValueMode valueMode;
    private ComparisonOperator operator = ComparisonOperator.EQUALS;

    public int getDamage() {
        return this.damage;
    }

    @Override
    public boolean matches(ItemStack itemStack) {
        if (itemStack.isNull()) {
            return false;
        }
        int maxDurability = itemStack.getItem().a();
        int durabilityValue = 0;
        switch (this.valueMode) {
            case PERCENTAGE: {
                durabilityValue = (int)((double)(maxDurability - itemStack.L()) / (double)maxDurability * 100.0);
                break;
            }
            case VALUE: {
                durabilityValue = maxDurability - itemStack.L();
            }
        }
        return this.operator.compare(durabilityValue, this.damage);
    }

    @Override
    public ComparisonOperator getOperator() {
        return this.operator;
    }

    public ItemDurabilityFilterCondition copy() {
        return new ItemDurabilityFilterCondition(this.damage, this.operator, this.valueMode);
    }

    public DurabilityValueMode getValueMode() {
        return this.valueMode;
    }

    public ItemDurabilityFilterCondition withOperator(ComparisonOperator operator) {
        this.operator = operator;
        return this;
    }

    @Override
    public JsonObject toJson() {
        JsonObject jsonObject = NumericFilterCondition.super.toJson();
        jsonObject.addProperty("durabilityMode", this.valueMode.getName());
        jsonObject.addProperty("damage", (Number)this.damage);
        return jsonObject;
    }

    public ItemDurabilityFilterCondition parseDamage(String value) throws NumberFormatException {
        this.damage = Integer.parseInt(value);
        return this;
    }

    public ItemDurabilityFilterCondition(JsonObject jsonObject) {
        this.valueMode = DurabilityValueMode.PERCENTAGE;
        this.damage = jsonObject.get("damage").getAsInt();
        this.operator = ComparisonOperator.fromName(jsonObject.get("operator").getAsString());
    }

    public ItemDurabilityFilterCondition(int damage, ComparisonOperator operator, DurabilityValueMode valueMode) {
        this.valueMode = DurabilityValueMode.PERCENTAGE;
        this.damage = damage;
        this.operator = operator;
        this.valueMode = valueMode;
    }

    public ItemDurabilityFilterCondition() {
        this.valueMode = DurabilityValueMode.PERCENTAGE;
    }

    public ItemDurabilityFilterCondition withDamage(int damage) {
        this.damage = damage;
        return this;
    }

    @Override
    public String getValueText() {
        return String.valueOf(this.damage);
    }

    public ItemDurabilityFilterCondition withValueMode(DurabilityValueMode valueMode) {
        this.valueMode = valueMode;
        return this;
    }

    @Override
    public InventoryFilterConditionType getType() {
        return InventoryFilterConditionType.ITEM_DURABILITY;
    }
}
