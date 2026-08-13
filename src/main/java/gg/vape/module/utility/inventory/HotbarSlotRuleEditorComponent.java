package gg.vape.module.utility.inventory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gg.vape.module.utility.inventory.HotbarSlotRule;
import gg.vape.module.utility.inventory.HotbarSlotRuleAddButton;
import gg.vape.module.utility.inventory.HotbarSlotRuleGroupComponent;
import gg.vape.module.utility.inventory.HotbarSlotRuleGroupSelectClickHandler;
import gg.vape.module.utility.inventory.HotbarSlotRuleValue;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiComponent;
import java.util.ArrayList;
import java.util.List;

public class HotbarSlotRuleEditorComponent
extends GuiComponent {
    private HotbarSlotRuleGroupComponent selectedGroup;
    private List<HotbarSlotRuleGroupComponent> groups;
    private boolean editing;
    private HotbarSlotRuleAddButton addGroupButton = new HotbarSlotRuleAddButton();
    private static String[] opaqueState;
    private HotbarSlotRuleValue ruleValue;

    public HotbarSlotRuleValue getRuleValue() {
        return this.ruleValue;
    }

    static List<HotbarSlotRuleGroupComponent> getGroupsInternal(HotbarSlotRuleEditorComponent hotbarSlotRuleEditorComponent) {
        return hotbarSlotRuleEditorComponent.groups;
    }

    public JsonObject serializeRules() {
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        for (HotbarSlotRuleGroupComponent hotbarSlotRuleGroupComponent : this.groups) {
            jsonArray.add((JsonElement)hotbarSlotRuleGroupComponent.toJson());
        }
        if (this.selectedGroup == null) {
            jsonObject.addProperty("selected", (Number)0);
        } else {
            jsonObject.addProperty("selected", (Number)this.groups.indexOf(this.selectedGroup));
        }
        jsonObject.add("panels", (JsonElement)jsonArray);
        return jsonObject;
    }

    public List<HotbarSlotRuleGroupComponent> getGroups() {
        return this.groups;
    }


    @Override
    public void u() {
    }

    public void loadJson(JsonObject jsonObject) {
        this.groups.clear();
        if (!jsonObject.has("panels")) {
            return;
        }
        JsonArray jsonArray = jsonObject.get("panels").getAsJsonArray();
        int n = jsonObject.get("selected").getAsInt();
        int n2 = jsonArray.size();
        for (int i = 0; i < n2; ++i) {
            HotbarSlotRuleGroupComponent hotbarSlotRuleGroupComponent = new HotbarSlotRuleGroupComponent(this, this.createEmptyRuleSlots());
            hotbarSlotRuleGroupComponent.loadJson(jsonArray.get(i).getAsJsonObject());
            this.groups.add(hotbarSlotRuleGroupComponent);
        }
        if (this.groups.size() > 0) {
            this.selectedGroup = this.getGroups().get(n);
        }
        this.rebuildChildren();
    }

    @Override
    public void I() {
    }

    public void rebuildChildren() {
        this.f().clear();
        this.addChildren(this.addGroupButton);
        for (HotbarSlotRuleGroupComponent hotbarSlotRuleGroupComponent : this.getGroups()) {
            HotbarSlotRuleGroupComponent hotbarSlotRuleGroupComponent2 = hotbarSlotRuleGroupComponent.addCloseListener(new HotbarSlotRuleGroupSelectClickHandler(this, hotbarSlotRuleGroupComponent));
            this.addChildren(hotbarSlotRuleGroupComponent2);
        }
        this.getParentFrameComponent().l$src$V$1mibm4x();
    }

    static {
        HotbarSlotRuleEditorComponent.v(new String[1]);
    }

    private List<HotbarSlotRule> createEmptyRuleSlots() {
        ArrayList<HotbarSlotRule> arrayList = new ArrayList<HotbarSlotRule>();
        for (int i = 0; i < 9; ++i) {
            HotbarSlotRule hotbarSlotRule = new HotbarSlotRule(0);
            arrayList.add(hotbarSlotRule);
        }
        return arrayList;
    }

    public HotbarSlotRuleEditorComponent(HotbarSlotRuleValue hotbarSlotRuleValue) {
        this.groups = new ArrayList<HotbarSlotRuleGroupComponent>();
        this.ruleValue = hotbarSlotRuleValue;
        hotbarSlotRuleValue.setEditor(this);
        HotbarSlotRuleEditorComponent hotbarSlotRuleEditorComponent = this;
        this.addGroupButton.addClickListener(() -> {
            HotbarSlotRuleGroupComponent groupComponent = new HotbarSlotRuleGroupComponent(hotbarSlotRuleEditorComponent, this.createEmptyRuleSlots());
            this.groups.add(groupComponent);
            if (this.selectedGroup == null) {
                this.selectedGroup = groupComponent;
            }
            this.rebuildChildren();
        });
        this.addChildren(this.addGroupButton);
    }

    static HotbarSlotRuleGroupComponent getSelectedGroupInternal(HotbarSlotRuleEditorComponent hotbarSlotRuleEditorComponent) {
        return hotbarSlotRuleEditorComponent.selectedGroup;
    }

    public void selectGroup(HotbarSlotRuleGroupComponent hotbarSlotRuleGroupComponent) {
        this.selectedGroup = hotbarSlotRuleGroupComponent;
    }

    @Override
    public double x() {
        return 110.0;
    }

    @Override
    public void H() {
        this.onDisable();
        this.addGroupButton.K(this.G$src$D$1b2f02a());
        this.addGroupButton.S(this.n());
        double d = this.addGroupButton.n() + this.addGroupButton.L();
        for (HotbarSlotRuleGroupComponent hotbarSlotRuleGroupComponent : this.getGroups()) {
            hotbarSlotRuleGroupComponent.K(this.G$src$D$1b2f02a());
            hotbarSlotRuleGroupComponent.S(d);
            d += hotbarSlotRuleGroupComponent.L();
        }
    }

    @Override
    public void F() {
    }

    public static String[] u$src$ALjava_lang_String_$1im86xh() {
        return opaqueState;
    }

    @Override
    public double C() {
        double d = this.addGroupButton.L();
        for (HotbarSlotRuleGroupComponent hotbarSlotRuleGroupComponent : this.getGroups()) {
            d += hotbarSlotRuleGroupComponent.L();
        }
        return d;
    }

    public static void v(String[] stringArray) {
        opaqueState = stringArray;
    }

    public void removeGroup(HotbarSlotRuleGroupComponent hotbarSlotRuleGroupComponent) {
        this.getGroups().remove(hotbarSlotRuleGroupComponent);
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    public HotbarSlotRuleGroupComponent getSelectedGroup() {
        return this.selectedGroup;
    }
}
