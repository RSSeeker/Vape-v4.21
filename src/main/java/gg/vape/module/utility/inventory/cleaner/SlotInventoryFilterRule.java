package gg.vape.module.utility.inventory.cleaner;

import com.google.gson.JsonObject;
import gg.vape.Vape;
import gg.vape.module.utility.inventory.cleaner.AbstractInventoryFilterRule;
import gg.vape.module.utility.inventory.cleaner.InventoryFilterPreset;
import java.util.UUID;
import org.jetbrains.annotations.Nullable;

public class SlotInventoryFilterRule
extends AbstractInventoryFilterRule {
    private static int[] slotStateArray;
    private final int slot;

    public SlotInventoryFilterRule(JsonObject jsonObject) {
        super(jsonObject);
        this.slot = jsonObject.get("slot").getAsInt();
    }

    static {
        SlotInventoryFilterRule.setSlotStateArray(new int[5]);
    }


    public static void setSlotStateArray(int[] states) {
        slotStateArray = states;
    }

    @Override
    @Nullable
    public InventoryFilterPreset resolvePreset() {
        UUID sharedPresetId = this.getSharedPresetId();
        if (sharedPresetId != null) {
            return Vape.INSTANCE.getInventoryFilterPresetRegistry().getSlotRulePresets().getById(sharedPresetId);
        }
        return this.getInlinePreset();
    }

    public int getContainerSlot() {
        return 36 + this.getSlot();
    }

    @Override
    public JsonObject toJson(boolean embedSharedPreset) {
        JsonObject jsonObject = super.toJson(embedSharedPreset);
        jsonObject.addProperty("slot", (Number)this.slot);
        return jsonObject;
    }

    public SlotInventoryFilterRule(int slot) {
        this.slot = slot;
    }

    public static int[] getSlotStateArray() {
        return slotStateArray;
    }

    public int getSlot() {
        return this.slot;
    }
}

