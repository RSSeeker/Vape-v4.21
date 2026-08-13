package gg.vape.module.utility.inventory.cleaner;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gg.vape.Vape;
import gg.vape.config.ConfigJsonUtils;
import gg.vape.module.utility.InventoryManager;

public class InventoryFilterPresetRegistry {
    private final InventoryFilterPresetStore slotRulePresets = new InventoryFilterPresetStore();
    private final InventoryFilterPresetStore itemRulePresets = new InventoryFilterPresetStore();

    public InventoryFilterPresetStore getSlotRulePresets() {
        return this.slotRulePresets;
    }


    public void loadJson(JsonObject jsonObject) {
        JsonArray jsonArray = ConfigJsonUtils.getJsonArray(jsonObject, "slotRules");
        if (jsonArray != null) {
            for (JsonElement element : jsonArray) {
                if (!element.isJsonObject()) continue;
                this.slotRulePresets.add(new SharedInventoryFilterPreset(element.getAsJsonObject()));
            }
        }
        JsonArray inventoryFilterRules = ConfigJsonUtils.getJsonArray(jsonObject, "inventoryFilterRules");
        if (inventoryFilterRules != null) {
            for (JsonElement jsonElement : inventoryFilterRules) {
                if (!jsonElement.isJsonObject()) continue;
                this.itemRulePresets.add(new SharedInventoryFilterPreset(jsonElement.getAsJsonObject()));
            }
        }
    }

    public InventoryFilterPresetStore getItemRulePresets() {
        return this.itemRulePresets;
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        for (SharedInventoryFilterPreset object : this.slotRulePresets.getAll()) {
            jsonArray.add((JsonElement)object.toJson());
        }
        JsonArray jsonArray2 = new JsonArray();
        for (SharedInventoryFilterPreset sharedInventoryFilterPreset : this.itemRulePresets.getAll()) {
            jsonArray2.add((JsonElement)sharedInventoryFilterPreset.toJson());
        }
        jsonObject.add("slotRules", (JsonElement)jsonArray);
        jsonObject.add("inventoryFilterRules", (JsonElement)jsonArray2);
        return jsonObject;
    }

    public void clearReferencesTo(SharedInventoryFilterPreset sharedInventoryFilterPreset) {
        InventoryManager inventoryManager = Vape.INSTANCE.getModManager().getMod(InventoryManager.class);
        for (InventoryCleanerProfile inventoryCleanerProfile : inventoryManager.getProfileValue().getProfiles()) {
            for (ItemInventoryFilterRule itemInventoryFilterRule : inventoryCleanerProfile.getItemRules()) {
                if (!sharedInventoryFilterPreset.equals(itemInventoryFilterRule.resolvePreset())) continue;
                itemInventoryFilterRule.clearPresetReference();
            }
            for (SlotInventoryFilterRule slotInventoryFilterRule : inventoryCleanerProfile.getSlotRules()) {
                if (!sharedInventoryFilterPreset.equals(slotInventoryFilterRule.resolvePreset())) continue;
                slotInventoryFilterRule.clearPresetReference();
            }
        }
    }
}
