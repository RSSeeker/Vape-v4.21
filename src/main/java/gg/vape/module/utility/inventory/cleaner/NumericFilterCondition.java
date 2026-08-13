package gg.vape.module.utility.inventory.cleaner;

import com.google.gson.JsonObject;
import gg.vape.module.utility.inventory.cleaner.ComparisonOperator;
import gg.vape.module.utility.inventory.cleaner.InventoryFilterCondition;

public interface NumericFilterCondition<T extends InventoryFilterCondition<T>>
extends InventoryFilterCondition<T> {
    public static final String OPERATOR_KEY = "operator";

    public T withOperator(ComparisonOperator operator);

    public ComparisonOperator getOperator();

    public T parseValue(String value) throws NumberFormatException;

    public String getValueText();

    @Override
    default public JsonObject toJson() {
        JsonObject jsonObject = InventoryFilterCondition.super.toJson();
        jsonObject.addProperty(OPERATOR_KEY, this.getOperator().getName());
        return jsonObject;
    }
}

