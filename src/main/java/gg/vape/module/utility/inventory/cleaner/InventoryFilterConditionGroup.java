package gg.vape.module.utility.inventory.cleaner;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gg.vape.module.utility.inventory.cleaner.InventoryFilterCondition;
import gg.vape.module.utility.inventory.cleaner.InventoryFilterConditionGroupBuilder;
import gg.vape.wrapper.impl.ItemStack;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.UnmodifiableView;

public class InventoryFilterConditionGroup
implements Cloneable {
    private final List<InventoryFilterCondition<?>> conditions = new ArrayList();

    public void replaceCondition(InventoryFilterCondition<?> existingCondition, InventoryFilterCondition<?> replacement) {
        int index = this.conditions.indexOf(existingCondition);
        if (index != -1) {
            this.conditions.add(index, replacement);
        } else {
            this.conditions.add(replacement);
        }
        this.conditions.remove(existingCondition);
    }

    public @UnmodifiableView List<InventoryFilterCondition<?>> getConditions() {
        return this.conditions;
    }

    public void removeCondition(InventoryFilterCondition<?> condition) {
        this.conditions.remove(condition);
    }

    public InventoryFilterConditionGroup(JsonObject jsonObject) {
        JsonArray jsonArray = jsonObject.getAsJsonArray("conditions");
        for (int i = 0; i < jsonArray.size(); ++i) {
            JsonObject conditionJson = jsonArray.get(i).getAsJsonObject();
            this.conditions.add(InventoryFilterCondition.fromJson(conditionJson));
        }
    }

    InventoryFilterConditionGroup() {
    }

    static List<InventoryFilterCondition<?>> mutableConditions(InventoryFilterConditionGroup group) {
        return group.conditions;
    }


    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        jsonObject.add("conditions", (JsonElement)jsonArray);
        for (InventoryFilterCondition<?> condition : this.conditions) {
            JsonObject conditionJson = condition.toJson();
            if (conditionJson == null) continue;
            jsonArray.add((JsonElement)conditionJson);
        }
        return jsonObject;
    }

    public static InventoryFilterConditionGroupBuilder builder() {
        return new InventoryFilterConditionGroupBuilder();
    }

    public void addCondition(InventoryFilterCondition<?> condition) {
        this.conditions.add(condition);
    }

    public InventoryFilterConditionGroup copy() {
        InventoryFilterConditionGroupBuilder builder = InventoryFilterConditionGroup.builder();
        for (InventoryFilterCondition<?> condition : this.conditions) {
            builder.addCondition((InventoryFilterCondition<?>)condition.copy());
        }
        return builder.build();
    }

    public boolean matches(ItemStack itemStack) {
        for (InventoryFilterCondition<?> condition : this.conditions) {
            if (condition.matches(itemStack)) continue;
            return false;
        }
        return true;
    }
}

