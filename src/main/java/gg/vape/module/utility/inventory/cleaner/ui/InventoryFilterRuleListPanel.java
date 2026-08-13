package gg.vape.module.utility.inventory.cleaner.ui;

import gg.vape.module.none.ClientSettings;
import gg.vape.module.utility.inventory.cleaner.InventoryCleanerProfile;
import gg.vape.module.utility.inventory.cleaner.ItemInventoryFilterRule;
import gg.vape.module.utility.inventory.cleaner.ui.InventoryCleanerPopupFrame;
import gg.vape.module.utility.inventory.cleaner.ui.ItemInventoryFilterRuleRow;
import gg.vape.ui.click.component.FilledSpacerComponent;
import gg.vape.ui.click.component.GlyphIconComponent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.SimpleTextLabelComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.component.TwoButtonConfirmationPopupComponent;
import gg.vape.ui.click.component.gui.TextLabel;
import gg.vape.ui.click.component.layout.PaddedComponent;
import gg.vape.ui.click.frame.AnchoredPopupFrame;
import gg.vape.ui.click.frame.FrameComponent;
import gg.vape.ui.click.frame.ScrollableFrameComponent;
import java.awt.Color;
import java.util.ArrayList;

public class InventoryFilterRuleListPanel
extends ScrollableFrameComponent {
    private final SimpleTextLabelComponent titleLabel;
    private final InventoryCleanerProfile profile;
    private final InventoryCleanerPopupFrame popupFrame;
    private final SimpleTextLabelComponent countLabel = new SimpleTextLabelComponent("", 0.7);
    private final ScrollableFrameComponent ruleList;
    private final PanelComponent clearAllPanel;
    private final TextLabel clearAllLabel;
    private final GlyphIconComponent addIcon;

    public void C(ItemInventoryFilterRule itemInventoryFilterRule) {
        this.ruleList.h(this.createFilterRow(itemInventoryFilterRule), new Object[0]);
        this.updateClearAllVisibility();
    }

    public InventoryFilterRuleListPanel(InventoryCleanerPopupFrame inventoryCleanerPopupFrame, FrameComponent frameComponent, InventoryCleanerProfile inventoryCleanerProfile) {
        super(frameComponent.A() - 5.0, 20.0);
        this.titleLabel = new SimpleTextLabelComponent("", 0.7);
        this.addIcon = new GlyphIconComponent("newadd", 8.0, 8.0, 32.0, 32.0, InventoryFilterRuleListPanel.J.B, InventoryFilterRuleListPanel.J.O, InventoryFilterRuleListPanel.J.l);
        this.clearAllLabel = new TextLabel("Clear all", 0.8);
        this.popupFrame = inventoryCleanerPopupFrame;
        this.profile = inventoryCleanerProfile;
        this.ruleList = new ScrollableFrameComponent(this.A() - 8.0, 20.0);
        this.t(90.0);
        this.C$src$V$nadrmg();
        this.setShowDisabledOverlay(false);
        this.setDisabledOverlayColor(Color.MAGENTA);
        this.countLabel.setTextSupplier(() -> String.valueOf(inventoryCleanerProfile.getItemRules().size()));
        this.countLabel.setBold(true);
        this.countLabel.setTextColor(InventoryFilterRuleListPanel.J.A);
        this.countLabel.o(this.countLabel.getTextWidth());
        this.countLabel.Y(8.0);
        this.h(this.countLabel, "widthwrap");
        this.titleLabel.setTextSupplier(() -> "  INVENTORY FILTER" + (inventoryCleanerProfile.getItemRules().size() == 1 ? "" : "S"));
        this.titleLabel.setBold(true);
        this.titleLabel.o(58.0);
        this.titleLabel.Y(8.0);
        this.h(this.titleLabel, "widthwrap");
        this.clearAllPanel = new PanelComponent(50.0, 4.0);
        this.clearAllPanel.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("widthwrap");
        this.clearAllPanel.setShowDisabledOverlay(false);
        this.clearAllPanel.h(new SpacerComponent(3.0, 0.0), new Object[0]);
        this.clearAllPanel.h(new FilledSpacerComponent(1.0, 8.0, InventoryFilterRuleListPanel.J.y), new Object[0]);
        this.clearAllPanel.h(new SpacerComponent(3.0, 0.0), new Object[0]);
        this.clearAllLabel.setUppercase(false);
        this.clearAllLabel.setUnderlined(true);
        this.clearAllLabel.o(this.clearAllLabel.getTextWidth() * 0.75);
        this.clearAllLabel.Y(8.0);
        this.clearAllLabel.addClickListener(() -> this.onClearAll(inventoryCleanerProfile));
        this.clearAllPanel.h(this.clearAllLabel, new Object[0]);
        this.h(this.clearAllPanel, "wrap");
        this.h(new SpacerComponent(0.0, 0.0), "wrap");
        this.h(new SpacerComponent(0.0, 5.0), "widthwrap");
        this.ruleList.setShowDisabledOverlay(false);
        this.ruleList.setDisabledOverlayColor(Color.BLUE);
        this.ruleList.t(81.0);
        this.h(this.ruleList, new Object[0]);
        this.addIcon.setBackgroundAnimationColors(InventoryFilterRuleListPanel.J.r, InventoryFilterRuleListPanel.J.R);
        this.addIcon.setCornerRadius((float)(this.addIcon.A() / 2.0));
        this.addIcon.setOutlineAlpha(1.0f);
        this.addIcon.setCenterHorizontally(true);
        this.addIcon.setCenterVertically(true);
        this.addIcon.setRemovable(false);
        this.addIcon.clearClickListeners();
        this.addIcon.addClickListener(() -> this.onAddRule(inventoryCleanerProfile));
        PaddedComponent paddedComponent = new PaddedComponent(2.0, 1.0, 3.0, 2.0, this.addIcon);
        paddedComponent.setRemovable(false);
        this.ruleList.h(paddedComponent, new Object[0]);
        for (ItemInventoryFilterRule itemInventoryFilterRule : inventoryCleanerProfile.getItemRules()) {
            this.C(itemInventoryFilterRule);
        }
        this.updateClearAllVisibility();
    }

    private void onAddRule(InventoryCleanerProfile inventoryCleanerProfile) {
        ItemInventoryFilterRule itemInventoryFilterRule = new ItemInventoryFilterRule();
        this.popupFrame.chooseRuleItem(this.addIcon, inventoryCleanerProfile, itemInventoryFilterRule, () -> this.onRuleCreated(inventoryCleanerProfile, itemInventoryFilterRule));
    }


    private void updateClearAllVisibility() {
        this.clearAllPanel.setVisible(!this.profile.getItemRules().isEmpty());
    }

    private void onClearAllConfirmed(InventoryCleanerProfile inventoryCleanerProfile, AnchoredPopupFrame anchoredPopupFrame) {
        inventoryCleanerProfile.clearItemRules();
        this.ruleList.removeMarkedChildren();
        ClientSettings.removePopup(anchoredPopupFrame);
        this.updateClearAllVisibility();
    }

    private void onRuleCreated(InventoryCleanerProfile inventoryCleanerProfile, ItemInventoryFilterRule itemInventoryFilterRule) {
        inventoryCleanerProfile.addItemRule(itemInventoryFilterRule);
        this.C(itemInventoryFilterRule);
    }

    private void onRuleDeleted(ItemInventoryFilterRule itemInventoryFilterRule) {
        this.profile.removeItemRule(itemInventoryFilterRule);
        this.J(itemInventoryFilterRule);
    }

    private GuiComponent createFilterRow(ItemInventoryFilterRule itemInventoryFilterRule) {
        ItemInventoryFilterRuleRow itemInventoryFilterRuleRow = new ItemInventoryFilterRuleRow(this.profile, itemInventoryFilterRule);
        itemInventoryFilterRuleRow.setOnSelect(() -> this.onRuleEdit(itemInventoryFilterRule, itemInventoryFilterRuleRow));
        PaddedComponent paddedComponent = new PaddedComponent(0.0, 4.0, 1.0, 0.0, itemInventoryFilterRuleRow);
        itemInventoryFilterRuleRow.setOnDelete(() -> this.onRuleDeleted(itemInventoryFilterRule));
        return paddedComponent;
    }

    private void onRuleEdit(ItemInventoryFilterRule itemInventoryFilterRule, ItemInventoryFilterRuleRow itemInventoryFilterRuleRow) {
        if (itemInventoryFilterRule.getItemSelection().isEmpty()) {
            this.popupFrame.chooseRuleItem(itemInventoryFilterRuleRow, this.profile, itemInventoryFilterRule, null);
        } else {
            this.popupFrame.openRuleSettings(itemInventoryFilterRuleRow, this.profile, itemInventoryFilterRule, false);
        }
    }

    private void onClearAll(InventoryCleanerProfile inventoryCleanerProfile) {
        TwoButtonConfirmationPopupComponent twoButtonConfirmationPopupComponent = new TwoButtonConfirmationPopupComponent("Confirm clear all?", "YES");
        AnchoredPopupFrame anchoredPopupFrame = ClientSettings.createPopup(this.clearAllLabel, twoButtonConfirmationPopupComponent, AnchoredPopupFrame.class);
        anchoredPopupFrame.m(4.0);
        anchoredPopupFrame.r(false);
        anchoredPopupFrame.q(this.L$src$Lgg_vape_ui_click_frame_Frame_$1djx6sa(), anchoredPopupFrame);
        twoButtonConfirmationPopupComponent.getConfirmButton().addClickListener(() -> this.onClearAllConfirmed(inventoryCleanerProfile, anchoredPopupFrame));
        twoButtonConfirmationPopupComponent.getCancelButton().addClickListener(() -> InventoryFilterRuleListPanel.onClearAllCancelled(anchoredPopupFrame));
    }

    public void J(ItemInventoryFilterRule itemInventoryFilterRule) {
        ArrayList<GuiComponent> arrayList = new ArrayList<GuiComponent>();
        for (GuiComponent guiComponent : this.ruleList.f()) {
            PaddedComponent paddedComponent;
            ItemInventoryFilterRuleRow itemInventoryFilterRuleRow;
            if (!(guiComponent instanceof PaddedComponent) || (itemInventoryFilterRuleRow = (paddedComponent = (PaddedComponent)guiComponent).t(ItemInventoryFilterRuleRow.class)) == null || itemInventoryFilterRuleRow.getRule() != itemInventoryFilterRule) continue;
            arrayList.add(guiComponent);
        }
        for (GuiComponent guiComponent : arrayList) {
            this.ruleList.removeChild(guiComponent);
        }
        this.updateClearAllVisibility();
    }

    private static void onClearAllCancelled(AnchoredPopupFrame anchoredPopupFrame) {
        ClientSettings.removePopup(anchoredPopupFrame);
    }
}
