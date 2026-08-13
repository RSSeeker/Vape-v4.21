package gg.vape.module.utility.inventory.cleaner;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gg.vape.config.ConfigJsonUtils;
import gg.vape.mapping.ItemMappingEntry;
import gg.vape.module.utility.inventory.cleaner.InventoryFilterCondition;
import gg.vape.module.utility.inventory.cleaner.InventoryFilterConditionType;
import gg.vape.module.utility.inventory.cleaner.ItemFilterSelection;
import gg.vape.module.utility.inventory.cleaner.MembershipMode;
import gg.vape.module.utility.inventory.cleaner.ui.ItemPickerSelection;
import gg.vape.wrapper.impl.ItemStack;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

public class MaterialFilterCondition
implements InventoryFilterCondition<MaterialFilterCondition> {
    private final List<ItemFilterSelection> selections = new ArrayList<ItemFilterSelection>();
    private MembershipMode membershipMode = MembershipMode.IS_IN;

    public MembershipMode getMembershipMode() {
        return this.membershipMode;
    }

    public void clear() {
        this.selections.clear();
    }

    @Override
    public boolean matches(ItemStack itemStack) {
        boolean isIn = this.membershipMode.equals(MembershipMode.IS_IN);
        for (ItemFilterSelection itemFilterSelection : this.selections) {
            if (!itemFilterSelection.matches(itemStack)) continue;
            return isIn;
        }
        return !isIn;
    }

    public MaterialFilterCondition(JsonObject jsonObject) {
        this.membershipMode = MembershipMode.fromName(jsonObject.get("operator").getAsString());
        JsonArray jsonArray = ConfigJsonUtils.getJsonArray(jsonObject, "items");
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.size(); ++i) {
                JsonElement jsonElement = jsonArray.get(i);
                ItemFilterSelection itemFilterSelection = new ItemFilterSelection(jsonElement);
                this.selections.add(itemFilterSelection);
            }
        }
    }

    public void removeSelection(ItemFilterSelection selection) {
        this.selections.remove(selection);
    }

    public MaterialFilterCondition() {
    }

    public void addSelection(ItemPickerSelection<String, ItemMappingEntry> pickerSelection) {
        ItemFilterSelection selection = new ItemFilterSelection();
        selection.setSelection(pickerSelection);
        this.addSelection(selection);
    }

    @Override
    public InventoryFilterConditionType getType() {
        return InventoryFilterConditionType.MATERIAL;
    }

    public MaterialFilterCondition(List<ItemFilterSelection> selections, MembershipMode membershipMode) {
        for (ItemFilterSelection selection : selections) {
            this.selections.add(selection.copy());
        }
        this.membershipMode = membershipMode;
    }

    public void setMembershipMode(MembershipMode membershipMode) {
        this.membershipMode = membershipMode;
    }


    public void addSelection(ItemFilterSelection selection) {
        this.selections.add(selection);
    }

    @Override
    public MaterialFilterCondition copy() {
        return new MaterialFilterCondition(this.selections, this.membershipMode);
    }

    public @UnmodifiableView List<ItemFilterSelection> getSelections() {
        return this.selections;
    }

    @Override
    public JsonObject toJson() {
        JsonObject jsonObject = InventoryFilterCondition.super.toJson();
        JsonArray jsonArray = new JsonArray();
        for (ItemFilterSelection itemFilterSelection : this.selections) {
            jsonArray.add(itemFilterSelection.toJson());
        }
        jsonObject.addProperty("operator", this.membershipMode.getName());
        if (jsonArray.size() > 0) {
            jsonObject.add("items", (JsonElement)jsonArray);
        }
        return jsonObject;
    }

    @Nullable
    public ItemFilterSelection findSelectionById(String id) {
        for (ItemFilterSelection selection : this.selections) {
            if (selection.getItemName() == null || !selection.getItemName().equalsIgnoreCase(id)) continue;
            return selection;
        }
        return null;
    }
}
