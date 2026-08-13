package gg.vape.ui.click.component.value;

import gg.vape.ui.click.animation.ColorAnimation;
import gg.vape.ui.click.component.FlowLayoutComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.SelectableTextRowComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.component.value.ListValueAddEntryInputComponent;
import gg.vape.ui.click.component.value.RemoveLimitValueEntryHandler;
import gg.vape.ui.click.component.value.RemoveOptionalLimitEntryHandler;
import gg.vape.unmap.ItemLimitData;
import gg.vape.value.LimitValue;
import gg.vape.value.ListValue;
import gg.vape.value.OptionalLimitEntry;
import gg.vape.value.OptionalLimitValue;
import java.awt.Color;
import java.util.List;

public class ListValueOptionsPanel
extends PanelComponent {
    private final FlowLayoutComponent entriesLayout;
    private final ListValue listValue;
    private final boolean blockedList;
    private final boolean sidecarStyle;
    private static final Color ENTRY_BACKGROUND = new Color(37, 36, 37);


    public ListValueOptionsPanel(ListValue listValue, boolean blockedList, double width, double height, boolean sidecarStyle) {
        super(width, height);
        this.listValue = listValue;
        this.blockedList = blockedList;
        this.sidecarStyle = sidecarStyle;
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        if (this.sidecarStyle) {
            this.setShowDisabledOverlay(false);
        }
        if (this.sidecarStyle) {
            this.addChildren(new SpacerComponent(0.0, 4.0));
        }
        ListValueAddEntryInputComponent listValueAddEntryInputComponent = new ListValueAddEntryInputComponent(this, blockedList, "Add entry...", listValue);
        listValueAddEntryInputComponent.setSuggestionProvider(listValue.getSuggestionProvider());
        listValueAddEntryInputComponent.setUseExplicitWidth(true);
        listValueAddEntryInputComponent.o(width);
        if (this.sidecarStyle) {
            listValueAddEntryInputComponent.setBorderThickness(0.75f);
            listValueAddEntryInputComponent.setCornerRadius(4.0f);
            listValueAddEntryInputComponent.setBorderAnimation(ColorAnimation.Y(ListValueOptionsPanel.J.k));
            listValueAddEntryInputComponent.setBackgroundColorOrNull(null);
            listValueAddEntryInputComponent.setTextColor(ListValueOptionsPanel.J.Z);
            listValueAddEntryInputComponent.setPlaceholderColor(ListValueOptionsPanel.J.h);
            listValueAddEntryInputComponent.getActionButton().o(10.0);
            listValueAddEntryInputComponent.getActionButton().Y(10.0);
            listValueAddEntryInputComponent.getActionButton().setIconWidth(6.0);
            listValueAddEntryInputComponent.getActionButton().setIconHeight(6.0);
            listValueAddEntryInputComponent.setVerticalInset(0.0f);
            listValueAddEntryInputComponent.setUseExplicitHeight(true);
            listValueAddEntryInputComponent.Y(16.0);
        }
        this.addChildren(listValueAddEntryInputComponent);
        if (this.sidecarStyle) {
            this.addChildren(new SpacerComponent(0.0, 5.0));
        }
        this.entriesLayout = new FlowLayoutComponent(width);
        this.entriesLayout.t(height - 25.0);
        if (this.sidecarStyle) {
            this.entriesLayout.setShowDisabledOverlay(false);
        }
        this.addChildren(this.entriesLayout);
    }

    public ListValueOptionsPanel(ListValue listValue, boolean bl, double d, double d2) {
        this(listValue, bl, d, d2, false);
    }

    public void refreshEntries() {
        block5: {
            block4: {
                this.entriesLayout.removeMarkedChildren();
                if (!(this.listValue instanceof OptionalLimitValue)) break block4;
                for (OptionalLimitEntry optionalLimitEntry : (List<OptionalLimitEntry>)((OptionalLimitValue)this.listValue).getValue()) {
                    SelectableTextRowComponent selectableTextRowComponent = new SelectableTextRowComponent(this.blockedList ? ListValueOptionsPanel.J.d : ListValueOptionsPanel.J.B, optionalLimitEntry.getValue()).setDeleteActionListener(new RemoveOptionalLimitEntryHandler(this, optionalLimitEntry)).setSelectionTarget(optionalLimitEntry);
                    selectableTextRowComponent.setUseExplicitWidth(true);
                    selectableTextRowComponent.o(this.A());
                    if (this.sidecarStyle) {
                        selectableTextRowComponent.setUseExplicitHeight(true);
                        selectableTextRowComponent.Y(20.0);
                        selectableTextRowComponent.setIndicatorSize(4);
                        selectableTextRowComponent.setIndicatorOffsetY(0.5f);
                        selectableTextRowComponent.setBackgroundColor(ENTRY_BACKGROUND);
                    }
                    this.entriesLayout.h(selectableTextRowComponent, new Object[0]);
                }
                break block5;
            }
            if (!(this.listValue instanceof LimitValue)) break block5;
            for (ItemLimitData itemLimitData : (List<ItemLimitData>)((LimitValue)this.listValue).getValue()) {
                String string = itemLimitData.getName() + (itemLimitData.getMetadata() < 0 ? "" : ":" + itemLimitData.getMetadata());
                SelectableTextRowComponent selectableTextRowComponent = new SelectableTextRowComponent(this.blockedList ? ListValueOptionsPanel.J.d : ListValueOptionsPanel.J.B, string).setDeleteActionListener(new RemoveLimitValueEntryHandler(this, itemLimitData)).setSelectionTarget(itemLimitData);
                selectableTextRowComponent.setUseExplicitWidth(true);
                selectableTextRowComponent.o(this.A());
                if (this.sidecarStyle) {
                    selectableTextRowComponent.setUseExplicitHeight(true);
                    selectableTextRowComponent.Y(20.0);
                    selectableTextRowComponent.setIndicatorSize(4);
                    selectableTextRowComponent.setIndicatorOffsetY(0.5f);
                    selectableTextRowComponent.setBackgroundColor(ENTRY_BACKGROUND);
                }
                this.entriesLayout.h(selectableTextRowComponent, new Object[0]);
            }
        }
    }

    public ListValueOptionsPanel(ListValue listValue, boolean bl) {
        this(listValue, bl, 110.0, 110.0, false);
    }

    public static ListValue getListValueCompat(ListValueOptionsPanel panel) {
        return panel.listValue;
    }
}
