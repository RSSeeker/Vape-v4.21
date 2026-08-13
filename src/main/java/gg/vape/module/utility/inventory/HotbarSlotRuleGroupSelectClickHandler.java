package gg.vape.module.utility.inventory;

import gg.vape.module.utility.inventory.HotbarSlotRuleEditorComponent;
import gg.vape.module.utility.inventory.HotbarSlotRuleGroupComponent;
import gg.vape.ui.click.component.GuiClickListener;

class HotbarSlotRuleGroupSelectClickHandler
implements GuiClickListener {
    final HotbarSlotRuleGroupComponent group;
    final HotbarSlotRuleEditorComponent editor;


    @Override
    public void onPrimaryClick() {
        if (this.group.equals(HotbarSlotRuleEditorComponent.getSelectedGroupInternal(this.editor)) && HotbarSlotRuleEditorComponent.getGroupsInternal(this.editor).size() > 0) {
            this.editor.selectGroup(HotbarSlotRuleEditorComponent.getGroupsInternal(this.editor).get(0));
        }
        this.editor.removeGroup(this.group);
        this.editor.rebuildChildren();
    }

    HotbarSlotRuleGroupSelectClickHandler(HotbarSlotRuleEditorComponent hotbarSlotRuleEditorComponent, HotbarSlotRuleGroupComponent hotbarSlotRuleGroupComponent) {
        this.editor = hotbarSlotRuleEditorComponent;
        this.group = hotbarSlotRuleGroupComponent;
    }
}

