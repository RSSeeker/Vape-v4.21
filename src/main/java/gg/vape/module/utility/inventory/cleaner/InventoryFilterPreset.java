package gg.vape.module.utility.inventory.cleaner;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gg.vape.Vape;
import gg.vape.config.ConfigJsonUtils;
import gg.vape.module.utility.inventory.cleaner.AbstractInventoryFilterPreset;
import gg.vape.module.utility.inventory.cleaner.InventoryFilterConditionGroup;
import gg.vape.module.utility.inventory.cleaner.InventoryFilterRule;
import gg.vape.module.utility.inventory.cleaner.SharedInventoryFilterPreset;
import gg.vape.module.utility.inventory.cleaner.SlotInventoryFilterRule;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.jetbrains.annotations.Nullable;

public class InventoryFilterPreset
extends AbstractInventoryFilterPreset
implements Cloneable {
    protected UUID id;
    private final List<InventoryFilterConditionGroup> conditionGroups = new ArrayList<InventoryFilterConditionGroup>();

    public InventoryFilterPreset(boolean inventoryRule) {
        this(null, "");
        this.assignDefaultName(inventoryRule);
    }

    public UUID getId() {
        return this.id;
    }


    public InventoryFilterPreset(@Nullable UUID id, String name) {
        super(name);
        this.id = id != null ? id : UUID.randomUUID();
    }

    @Override
    public List<InventoryFilterConditionGroup> getConditionGroups() {
        return this.conditionGroups;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        for (InventoryFilterConditionGroup conditionGroup : this.conditionGroups) {
            jsonArray.add((JsonElement)conditionGroup.toJson());
        }
        jsonObject.addProperty("uuid", this.id.toString());
        jsonObject.addProperty("name", this.getName());
        jsonObject.add("conditions", (JsonElement)jsonArray);
        return jsonObject;
    }

    public InventoryFilterPreset(JsonObject jsonObject) {
        this(ConfigJsonUtils.getUuid(jsonObject, "uuid"), jsonObject.get("name").getAsString());
        JsonArray jsonArray = jsonObject.getAsJsonArray("conditions");
        for (JsonElement jsonElement : jsonArray) {
            this.addConditionGroup(new InventoryFilterConditionGroup(jsonElement.getAsJsonObject()));
        }
    }

    public SharedInventoryFilterPreset shareForRule(InventoryFilterRule rule) {
        SharedInventoryFilterPreset sharedPreset = new SharedInventoryFilterPreset(this);
        boolean slotRule = rule instanceof SlotInventoryFilterRule;
        rule.setPreset(sharedPreset);
        if (slotRule) {
            Vape.INSTANCE.getInventoryFilterPresetRegistry().getSlotRulePresets().replace(null, sharedPreset);
        } else {
            Vape.INSTANCE.getInventoryFilterPresetRegistry().getItemRulePresets().replace(null, sharedPreset);
        }
        return sharedPreset;
    }

    public void addConditionGroup(InventoryFilterConditionGroup conditionGroup) {
        this.conditionGroups.add(conditionGroup);
    }

    public InventoryFilterPreset copy() {
        InventoryFilterPreset copy = new InventoryFilterPreset(this.getId(), this.getName());
        for (InventoryFilterConditionGroup conditionGroup : this.conditionGroups) {
            copy.addConditionGroup(conditionGroup.copy());
        }
        return copy;
    }

    public InventoryFilterPreset(SharedInventoryFilterPreset sharedInventoryFilterPreset) {
        this(sharedInventoryFilterPreset.toJson());
        this.id = UUID.randomUUID();
    }

    public void assignDefaultName(boolean inventoryRule) {
        String prefix = (inventoryRule ? "Inventory Filter " : "Custom ") + "Rule #";
        int suffix = 1;
        for (InventoryFilterPreset preset : (!inventoryRule ? Vape.INSTANCE.getInventoryFilterPresetRegistry().getSlotRulePresets() : Vape.INSTANCE.getInventoryFilterPresetRegistry().getItemRulePresets()).getAll()) {
            if (!preset.getName().equalsIgnoreCase(prefix + suffix)) continue;
            ++suffix;
        }
        this.name = prefix + suffix;
    }

    public void removeConditionGroup(InventoryFilterConditionGroup conditionGroup) {
        this.conditionGroups.remove(conditionGroup);
    }
}

