package gg.vape.module.utility.inventory.cleaner;

import com.google.gson.JsonObject;
import gg.vape.module.utility.inventory.cleaner.AbstractTextFilterCondition;
import gg.vape.module.utility.inventory.cleaner.InventoryFilterConditionType;
import gg.vape.module.utility.inventory.cleaner.TextMatchMode;
import gg.vape.wrapper.impl.ItemStack;
import java.util.List;

public class LoreFilterCondition
extends AbstractTextFilterCondition<LoreFilterCondition> {
    @Override
    public boolean matches(ItemStack itemStack) {
        if (itemStack.isNull()) {
            return false;
        }
        List<String> loreLines = itemStack.z();
        for (String loreLine : loreLines) {
            if (!this.getMatchMode().matchesAny(loreLine, this.getTexts())) continue;
            return true;
        }
        return false;
    }


    public LoreFilterCondition() {
    }

    @Override
    public InventoryFilterConditionType getType() {
        return InventoryFilterConditionType.LORE;
    }

    public LoreFilterCondition(JsonObject jsonObject) {
        super(jsonObject);
    }

    public LoreFilterCondition(List<String> texts, TextMatchMode matchMode) {
        super(texts, matchMode);
    }

    public LoreFilterCondition copy() {
        return new LoreFilterCondition(this.getTexts(), this.getMatchMode());
    }
}
