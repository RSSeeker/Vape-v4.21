package gg.vape.module.utility.inventory;

import com.google.gson.JsonObject;
import gg.vape.module.utility.inventory.HotbarSlotRule;
import gg.vape.module.utility.inventory.HotbarSlotRuleEditorComponent;
import gg.vape.value.Value;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.Nullable;

public class HotbarSlotRuleValue
extends Value<List<HotbarSlotRule>, HotbarSlotRuleValue> {
    @Nullable
    private HotbarSlotRuleEditorComponent editor;

    public List<HotbarSlotRule> getRules() {
        if (this.editor == null || this.editor.getSelectedGroup() == null) {
            return new ArrayList<HotbarSlotRule>();
        }
        return this.editor.getSelectedGroup().getRules();
    }

    public HotbarSlotRuleValue createCopy() {
        return new HotbarSlotRuleValue(null, this.getId());
    }

    @Override
    public HotbarSlotRuleValue copyValueDefinition() {
        return this.createCopy();
    }

    @Override
    public void parse(String string) {
    }

    public void setEditor(HotbarSlotRuleEditorComponent editor) {
        this.editor = editor;
    }


    public HotbarSlotRuleValue(Object object, String string) {
        super(object, string, new ArrayList());
    }

    @Override
    public boolean loadJson(JsonObject jsonObject) {
        if (this.editor != null) {
            this.editor.loadJson(jsonObject);
        }
        return true;
    }

    @Override
    public String getDisplayValue() {
        List<HotbarSlotRule> list = this.getRules();
        if (list.isEmpty()) {
            return "None";
        }
        if (list.size() == 1) {
            return String.valueOf(list.get(0).getItemId());
        }
        return list.get(0).getItemId() + " +" + (list.size() - 1);
    }

    @Override
    public JsonObject toJson(boolean bl) {
        JsonObject jsonObject = this.editor != null ? this.editor.serializeRules() : new JsonObject();
        jsonObject.addProperty("id", this.getId());
        return jsonObject;
    }

    @Nullable
    public HotbarSlotRuleEditorComponent getEditor() {
        return this.editor;
    }

    public static HotbarSlotRuleValue create(Object owner, String id) {
        return new HotbarSlotRuleValue(owner, id);
    }
}
