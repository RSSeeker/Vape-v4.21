package gg.vape.module.utility.inventory.cleaner.ui;

import gg.vape.Vape;
import gg.vape.friend.ui.OnlineRadarPreviewState;
import gg.vape.mapping.ItemMappingEntry;
import gg.vape.module.none.ClientSettings;
import gg.vape.module.utility.inventory.HotbarSlotRuleItemListFrame;
import gg.vape.module.utility.inventory.cleaner.HiddenInventoryItemMatchers;
import gg.vape.module.utility.inventory.cleaner.InventoryFilterRule;
import gg.vape.module.utility.inventory.cleaner.InventoryItemMatcher;
import gg.vape.module.utility.inventory.cleaner.InventoryItemMatcherGroup;
import gg.vape.module.utility.inventory.cleaner.InventoryItemMatcherRegistry;
import gg.vape.module.utility.inventory.cleaner.SlotInventoryFilterRule;
import gg.vape.module.utility.inventory.cleaner.ui.InventoryCleanerIconTextActionRow;
import gg.vape.module.utility.inventory.cleaner.ui.InventoryItemMatcherRowComponent;
import gg.vape.module.utility.inventory.cleaner.ui.InventoryItemPickerCategoryItemClickListener;
import gg.vape.module.utility.inventory.cleaner.ui.InventoryItemPickerSearchResultClickListener;
import gg.vape.module.utility.inventory.cleaner.ui.InventoryItemPreviewComponent;
import gg.vape.module.utility.inventory.cleaner.ui.InventoryItemStackSelectionRowComponent;
import gg.vape.module.utility.inventory.cleaner.ui.ItemPickerSelection;
import gg.vape.ui.click.component.GlyphIconComponent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.IconGlyphComponent;
import gg.vape.ui.click.component.LabeledTextInputComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.SimpleTextLabelComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.component.WrappingTextLabelComponent;
import gg.vape.ui.click.component.gui.InteractiveComponent;
import gg.vape.ui.click.component.layout.PaddedComponent;
import gg.vape.ui.click.frame.FrameComponent;
import gg.vape.ui.click.frame.ScrollableFrameComponent;
import gg.vape.utils.ItemStackScoreUtil;
import gg.vape.wrapper.impl.ItemStack;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class InventoryItemPickerPanel
extends PanelComponent {
    private final PanelComponent allItemsPanel;
    private final InventoryFilterRule filterRule;
    private boolean addSelectionAfterChoose = true;
    private final PanelComponent categoryPanel;
    private static final List<String> COMMON_ITEM_IDS;
    private final List<String> selectedItemIds;
    @Nullable
    private Consumer<ItemPickerSelection<String, ItemMappingEntry>> onExistingSelection;
    @Nullable
    private final InventoryItemMatcher filterMatcher;
    @NotNull
    private Consumer<ItemPickerSelection<String, ItemMappingEntry>> onSelect;
    private final boolean searchOnly;
    private final PanelComponent searchPanel;
    private final LabeledTextInputComponent searchInput = new LabeledTextInputComponent("Search items...", false, true);

    public static List<String> getSelectedItemIds(InventoryItemPickerPanel panel) {
        return panel.selectedItemIds;
    }

    @Nullable
    public Consumer<ItemPickerSelection<String, ItemMappingEntry>> getOnExistingSelection() {
        return this.onExistingSelection;
    }

    public void setOnSelect(@Nullable Consumer<ItemPickerSelection<String, ItemMappingEntry>> onSelect) {
        this.onSelect = onSelect;
    }

    private void dispatchSelection(ItemPickerSelection<String, ItemMappingEntry> selection) {
        if (this.selectedItemIds.contains(selection.getLeft() != null ? selection.getLeft() : selection.getRight().getResourceKey())) {
            Consumer<ItemPickerSelection<String, ItemMappingEntry>> existingSelectionHandler = this.onExistingSelection;
            if (existingSelectionHandler != null) {
                existingSelectionHandler.accept(selection);
            }
        } else {
            this.onSelect.accept(selection);
        }
    }

    private List<OnlineRadarPreviewState<ItemStack, ItemMappingEntry>> getSearchResults() {
        ItemStack resolvedStack;
        LinkedHashMap<ItemMappingEntry, ItemStack> matchingItems = new LinkedHashMap<ItemMappingEntry, ItemStack>();
        String query = this.searchInput.getText().toLowerCase();
        if (query.trim().isEmpty()) {
            for (ItemStack commonStack : HotbarSlotRuleItemListFrame.DEFAULT_ITEMS) {
                ItemMappingEntry mappingEntry = Vape.INSTANCE.getItemStackResolver().resolve(commonStack);
                if (mappingEntry == null || this.selectedItemIds.contains(mappingEntry.getResourceKey()) || (resolvedStack = mappingEntry.resolveItemStack()) == null || resolvedStack.isNull()) continue;
                matchingItems.put(mappingEntry, resolvedStack);
            }
        }
        for (ItemStack candidateStack : ItemStackScoreUtil.S()) {
            ItemMappingEntry mappingEntry;
            if (this.filterMatcher != null && !this.filterMatcher.matches(candidateStack) || (mappingEntry = Vape.INSTANCE.getItemStackResolver().resolve(candidateStack)) == null || (resolvedStack = mappingEntry.resolveItemStack()) == null || resolvedStack.isNull() || !mappingEntry.getModernId().contains(query) && !mappingEntry.getModernId().replace("_", " ").contains(query) && !resolvedStack.x().toLowerCase().contains(query) || this.selectedItemIds.contains(mappingEntry.getResourceKey())) continue;
            matchingItems.put(mappingEntry, resolvedStack);
        }
        ArrayList<OnlineRadarPreviewState<ItemStack, ItemMappingEntry>> results = new ArrayList<>();
        for (Map.Entry<ItemMappingEntry, ItemStack> entry : matchingItems.entrySet()) {
            results.add(OnlineRadarPreviewState.create(entry.getValue(), entry.getKey()));
        }
        results.sort((first, second) -> InventoryItemPickerPanel.compareSearchResults(query, first, second));
        return results;
    }

    public boolean shouldAddSelectionAfterChoose() {
        return this.addSelectionAfterChoose;
    }

    private void selectMatcher(InventoryItemMatcher matcher) {
        this.dispatchSelection(ItemPickerSelection.ofLeft(matcher.getId()));
    }

    private void openMatcherGroup(InventoryItemMatcherGroup matcherGroup) {
        this.showCategoryView(matcherGroup);
    }

    public void showSearchView() {
        this.allItemsPanel.setVisible(false);
        this.searchPanel.setVisible(true);
        this.categoryPanel.setVisible(false);
        this.searchPanel.t$src$V$zbu1jn();
        this.searchPanel.h(this.createHeader(this.A(), "Search", false, !this.searchOnly), new Object[0]);
        this.searchPanel.h(new SpacerComponent(9.0, 0.0), "widthwrap");
        this.searchPanel.h(this.searchInput, new Object[0]);
        this.searchPanel.h(new SpacerComponent(0.0, 5.0), new Object[0]);
        ScrollableFrameComponent selectedItemsPanel = new ScrollableFrameComponent(this.searchPanel.A() - 5.0, 0.0);
        selectedItemsPanel.t(36.0);
        selectedItemsPanel.setShowDisabledOverlay(false);
        ArrayList<String> selectedIds = new ArrayList<String>(this.selectedItemIds);
        SimpleTextLabelComponent selectedLabel = null;
        if (!selectedIds.isEmpty()) {
            selectedLabel = new SimpleTextLabelComponent("SELECTED", 0.8, InventoryItemPickerPanel.J.h, true);
            selectedLabel.o(this.allItemsPanel.A() - 10.0);
            selectedLabel.Y(8.0);
            selectedLabel.setExtraHeight(0);
            this.searchPanel.h(new PaddedComponent(0.0, 2.0, 5.0, 0.0, selectedLabel), new Object[0]);
        }
        BiFunction<ItemStack, ItemMappingEntry, GuiComponent> previewFactory = this::createSelectedItemPreview;
        for (String selectedId : selectedIds) {
            ItemStack selectedStack;
            ItemMappingEntry selectedEntry = Vape.INSTANCE.getItemStackResolver().findByName(selectedId);
            if (selectedEntry == null || (selectedStack = selectedEntry.resolveItemStack()) == null) continue;
            selectedItemsPanel.h(previewFactory.apply(selectedStack, selectedEntry), selectedItemsPanel.f().size() % 6 == 5 ? "wrap" : "widthwrap");
        }
        this.searchPanel.h(new PaddedComponent(5.0, 0.0, selectedItemsPanel), new Object[0]);
        selectedItemsPanel.H(true);
        SimpleTextLabelComponent searchResultsLabel = new SimpleTextLabelComponent("SEARCH RESULTS...", 0.8, InventoryItemPickerPanel.J.h, true);
        searchResultsLabel.o(this.allItemsPanel.A() - 10.0);
        searchResultsLabel.Y(8.0);
        this.searchPanel.h(new PaddedComponent(5.0, 0.0, 5.0, 0.0, searchResultsLabel), new Object[0]);
        PanelComponent searchResultsPanel = new PanelComponent(this.searchPanel.A(), 135.0 - selectedItemsPanel.C() - (selectedLabel != null ? selectedLabel.L() + 2.0 : 0.0));
        searchResultsPanel.t(searchResultsPanel.L());
        searchResultsPanel.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        searchResultsPanel.setShowDisabledOverlay(false);
        this.searchPanel.h(searchResultsPanel, new Object[0]);
        int resultCount = 0;
        int maxResults = 300;
        for (OnlineRadarPreviewState<ItemStack, ItemMappingEntry> result : this.getSearchResults()) {
            ItemStack resultStack = result.getKey();
            ItemMappingEntry resultEntry = result.getValue();
            InventoryItemStackSelectionRowComponent resultRow = new InventoryItemStackSelectionRowComponent(resultStack);
            resultRow.o(searchResultsPanel.A());
            resultRow.Y(16.0);
            PaddedComponent paddedRow = new PaddedComponent(1.0, 0.0, 0.0, 0.0, resultRow);
            searchResultsPanel.h(paddedRow, new Object[0]);
            resultRow.K(() -> this.scheduleItemSelection(resultEntry));
            if (++resultCount < maxResults) continue;
            break;
        }
    }

    private GuiComponent createSelectedItemPreview(ItemStack itemStack, ItemMappingEntry mappingEntry) {
        InventoryItemPreviewComponent preview = new InventoryItemPreviewComponent(itemStack, true);
        PaddedComponent paddedPreview = new PaddedComponent(1.0, 1.0, 2.0, 0.0, preview);
        preview.addMouseListener(new InventoryItemPickerSearchResultClickListener(this, mappingEntry));
        return paddedPreview;
    }

    public void removeSelectedItemId(String itemId) {
        this.selectedItemIds.remove(itemId);
    }

    public void setOnExistingSelection(@Nullable Consumer<ItemPickerSelection<String, ItemMappingEntry>> onExistingSelection) {
        this.onExistingSelection = onExistingSelection;
    }

    public void addSelectedItemId(String itemId) {
        this.selectedItemIds.add(itemId);
    }

    private void scheduleItemSelection(ItemMappingEntry mappingEntry) {
        ClientSettings.UI_EXECUTOR.execute(() -> this.selectMappingEntry(mappingEntry));
    }

    private static int compareSearchResults(String query, OnlineRadarPreviewState first, OnlineRadarPreviewState second) {
        String firstName = ((ItemStack)first.getKey()).x().toLowerCase();
        String secondName = ((ItemStack)second.getKey()).x().toLowerCase();
        if (firstName.equals(query) && !secondName.equals(query)) {
            return -1;
        }
        if (firstName.startsWith(query) && !secondName.startsWith(query)) {
            return -1;
        }
        return 0;
    }

    private void scheduleMatcherSelection(InventoryItemMatcher matcher) {
        ClientSettings.UI_EXECUTOR.execute(() -> this.selectMatcher(matcher));
    }

    static {
        COMMON_ITEM_IDS = Arrays.asList("minecraft:diamond_sword", "minecraft:diamond_pickaxe", "minecraft:diamond_axe", "minecraft:bow", "minecraft:cooked_beef", "minecraft:ender_pearl", "minecraft:snowball", "minecraft:egg", "minecraft:fishing_rod", "minecraft:enchanted_golden_apple", "minecraft:golden_apple", "minecraft:water_bucket");
    }

    public InventoryItemPickerPanel(InventoryFilterRule filterRule, boolean searchOnly, @Nullable InventoryItemMatcher filterMatcher, List<String> selectedItemIds, Consumer<ItemPickerSelection<String, ItemMappingEntry>> onSelect) {
        super(108.0, 215.0);
        this.selectedItemIds = new ArrayList<String>();
        this.filterRule = filterRule;
        this.searchOnly = searchOnly;
        this.filterMatcher = filterMatcher;
        this.selectedItemIds.addAll(selectedItemIds);
        this.onSelect = onSelect;
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        this.setDisabledOverlayColor(InventoryItemPickerPanel.J.H);
        double panelHeight = this.L();
        this.allItemsPanel = new PanelComponent(this.A(), panelHeight);
        this.searchPanel = new PanelComponent(this.A(), panelHeight);
        this.categoryPanel = new PanelComponent(this.A(), panelHeight);
        this.allItemsPanel.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        this.searchPanel.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        this.categoryPanel.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        this.allItemsPanel.setShowDisabledOverlay(false);
        this.searchPanel.setShowDisabledOverlay(false);
        this.categoryPanel.setShowDisabledOverlay(false);
        this.allItemsPanel.h(this.createHeader(this.A(), "All items", true, false), new Object[0]);
        this.allItemsPanel.h(new SpacerComponent(this.A(), 0.0), new Object[0]);
        this.allItemsPanel.h(new SpacerComponent(9.0, 0.0), "widthwrap");
        this.searchInput.setShowDisabledOverlay(false);
        this.searchInput.setBackgroundVisible(false);
        this.searchInput.setHorizontalInset(0.0);
        this.searchInput.setLeftInset(0.0f);
        this.searchInput.setVerticalInset(0.0f);
        this.searchInput.getActionButton().setVisible(false);
        this.searchInput.setPlaceholderColor(InventoryItemPickerPanel.J.h);
        this.searchInput.Y(14.0);
        this.searchInput.o(this.allItemsPanel.A() - 16.0);
        this.searchInput.addKeyTypedListener(this::handleSearchInput);
        this.allItemsPanel.h(this.searchInput, new Object[0]);
        this.allItemsPanel.h(new SpacerComponent(0.0, 5.0), new Object[0]);
        SimpleTextLabelComponent commonItemsLabel = new SimpleTextLabelComponent("COMMON ITEMS", 0.7, InventoryItemPickerPanel.J.h, true);
        commonItemsLabel.setExtraHeight(0);
        commonItemsLabel.o(this.allItemsPanel.A() - 10.0);
        this.allItemsPanel.h(new SpacerComponent(5.0, 0.0), "widthwrap");
        this.allItemsPanel.h(commonItemsLabel, new Object[0]);
        PanelComponent commonItemsPanel = new PanelComponent(this.allItemsPanel.A() - 10.0, 30.0);
        commonItemsPanel.setShowDisabledOverlay(false);
        PaddedComponent paddedCommonItems = new PaddedComponent(5.0, commonItemsPanel);
        paddedCommonItems.setShowDisabledOverlay(false);
        this.allItemsPanel.h(paddedCommonItems, new Object[0]);
        int commonItemCount = 0;
        for (String commonItemId : COMMON_ITEM_IDS) {
            ItemMappingEntry itemEntry = Vape.INSTANCE.getItemStackResolver().findByName(commonItemId);
            ItemStack itemStack;
            if (itemEntry == null || (itemStack = itemEntry.resolveItemStack()) == null || itemStack.isNull()) continue;
            InventoryItemPreviewComponent itemPreview = new InventoryItemPreviewComponent(itemStack, false);
            itemPreview.addMouseListener(new InventoryItemPickerCategoryItemClickListener(this, itemEntry));
            commonItemsPanel.h(new PaddedComponent(0.0, 2.0, 2.0, 0.0, itemPreview), commonItemCount == 5 ? "wrap" : "widthwrap");
            ++commonItemCount;
        }
        SimpleTextLabelComponent genericItemsLabel = new SimpleTextLabelComponent("GENERIC ITEMS", 0.7, InventoryItemPickerPanel.J.h, true);
        genericItemsLabel.setExtraHeight(4);
        genericItemsLabel.o(genericItemsLabel.getTextWidth() * 1.2);
        this.allItemsPanel.h(new SpacerComponent(5.0, 0.0), "widthwrap");
        this.allItemsPanel.h(genericItemsLabel, "widthwrap");
        IconGlyphComponent genericItemsInfo = new IconGlyphComponent("newinfo", 5.0f, 5.0f);
        genericItemsInfo.setSnapToPixels(true);
        genericItemsInfo.w("Generic Items are groups of items that share a common theme.");
        this.allItemsPanel.h(new PaddedComponent(1.5, genericItemsInfo), new Object[0]);
        PanelComponent matcherGroupsPanel = new PanelComponent(this.allItemsPanel.A(), 115.0);
        matcherGroupsPanel.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        matcherGroupsPanel.setShowDisabledOverlay(false);
        matcherGroupsPanel.t(matcherGroupsPanel.L());
        this.allItemsPanel.h(matcherGroupsPanel, new Object[0]);
        for (InventoryItemMatcherGroup matcherGroup : InventoryItemMatcherGroup.VALUES) {
            if (matcherGroup.getIconName() == null) continue;
            InventoryCleanerIconTextActionRow groupRow = new InventoryCleanerIconTextActionRow(matcherGroup.getName(), matcherGroup.getIconName(), () -> this.openMatcherGroup(matcherGroup));
            groupRow.w(matcherGroup.getDescription());
            groupRow.o(this.allItemsPanel.A());
            groupRow.Y(18.0);
            matcherGroupsPanel.h(new PaddedComponent(1.0, 0.0, 0.0, 0.0, groupRow), new Object[0]);
        }
        InventoryItemMatcher hiddenMatcher = HiddenInventoryItemMatchers.ANY_ITEM;
        InventoryCleanerIconTextActionRow anyItemRow = new InventoryCleanerIconTextActionRow(hiddenMatcher.getName(), hiddenMatcher.getIconName(), () -> this.scheduleMatcherSelection(hiddenMatcher));
        anyItemRow.o(this.allItemsPanel.A());
        anyItemRow.Y(18.0);
        anyItemRow.w(hiddenMatcher.getDescription());
        matcherGroupsPanel.h(new PaddedComponent(1.0, 0.0, 0.0, 0.0, anyItemRow), new Object[0]);
        this.allItemsPanel.o(108.0);
        this.h(this.allItemsPanel, new Object[0]);
        this.h(this.searchPanel, new Object[0]);
        this.h(this.categoryPanel, new Object[0]);
        if (this.searchOnly) {
            this.showSearchView();
        } else {
            this.showAllItemsView();
        }
    }

    private void handleSearchInput(char character, int keyCode) {
        ClientSettings.UI_EXECUTOR.execute(this::updateViewFromSearch);
    }

    public void showCategoryView(InventoryItemMatcherGroup matcherGroup) {
        this.allItemsPanel.setVisible(false);
        this.searchPanel.setVisible(false);
        this.categoryPanel.setVisible(true);
        this.categoryPanel.t$src$V$zbu1jn();
        PanelComponent header = this.createHeader(this.A(), matcherGroup.getName(), false, true);
        this.categoryPanel.h(header, new Object[0]);
        PanelComponent matcherList = new PanelComponent(this.searchPanel.A(), this.searchPanel.L() - header.L());
        matcherList.t(matcherList.L());
        matcherList.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        matcherList.setShowDisabledOverlay(false);
        this.categoryPanel.h(matcherList, new Object[0]);
        for (InventoryItemMatcher matcher : InventoryItemMatcherRegistry.getByGroup(matcherGroup)) {
            InventoryItemMatcherRowComponent matcherRow = new InventoryItemMatcherRowComponent(matcher, () -> this.scheduleMatcherSelection(matcher));
            matcherRow.o(matcherList.A());
            matcherRow.Y(18.0);
            matcherRow.w(matcher.getDescription());
            matcherList.h(new PaddedComponent(1.0, 0.0, 0.0, 0.0, matcherRow), new Object[0]);
        }
    }

    public void setAddSelectionAfterChoose(boolean addSelectionAfterChoose) {
        this.addSelectionAfterChoose = addSelectionAfterChoose;
    }


    @Nullable
    public Consumer<ItemPickerSelection<String, ItemMappingEntry>> getOnSelect() {
        return this.onSelect;
    }

    public static void select(InventoryItemPickerPanel panel, ItemPickerSelection selection) {
        panel.dispatchSelection(selection);
    }

    private void selectMappingEntry(ItemMappingEntry mappingEntry) {
        this.dispatchSelection(ItemPickerSelection.ofRight(mappingEntry));
        if (this.addSelectionAfterChoose) {
            this.selectedItemIds.add(mappingEntry.getResourceKey());
        }
        this.showSearchView();
    }

    private PanelComponent createHeader(double width, String title, boolean showSlot, boolean showBackButton) {
        PanelComponent header = new PanelComponent(width, 28.0);
        header.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        header.setShowDisabledOverlay(false);
        if (showSlot && this.filterRule instanceof SlotInventoryFilterRule) {
            SlotInventoryFilterRule slotRule = (SlotInventoryFilterRule)this.filterRule;
            PanelComponent slotBadge = new PanelComponent(10.0, 10.0);
            slotBadge.setShowDisabledOverlay(true);
            slotBadge.setDisabledOverlayColor(InventoryItemPickerPanel.J.y);
            slotBadge.S(5);
            header.h(slotBadge, new Object[0]);
            SimpleTextLabelComponent slotLabel = new SimpleTextLabelComponent(String.valueOf(slotRule.getSlot() + 1), 0.8, InventoryItemPickerPanel.J.A, true);
            slotLabel.setOffsetX(3.0f);
            slotBadge.h(slotLabel, new Object[0]);
            header.h(slotBadge, new Object[0]);
        } else {
            header.h(new SpacerComponent(0.0, 10.0), new Object[0]);
        }
        if (showBackButton) {
            GlyphIconComponent backButton = new GlyphIconComponent("back-hover@2x", 6.0, 6.0, 10.0, 10.0, InventoryItemPickerPanel.J.W, InventoryItemPickerPanel.J.f, null);
            backButton.setCenterVertically(true);
            backButton.setCenterHorizontally(true);
            backButton.addClickListener(this::showAllItemsView);
            header.h(new SpacerComponent(8.0, 0.0), "widthwrap");
            header.h(backButton, "widthwrap");
        }
        WrappingTextLabelComponent titleLabel = new WrappingTextLabelComponent(title, 1.0, InventoryItemPickerPanel.J.A);
        titleLabel.o(header.A() - (double)(showBackButton ? 36 : 0));
        titleLabel.setBold(true);
        titleLabel.setAcceptsMouseInput(false);
        header.h(titleLabel, new Object[0]);
        return header;
    }

    private void updateViewFromSearch() {
        if (this.searchInput.getText().trim().isEmpty() && !this.searchOnly) {
            this.showAllItemsView();
        } else {
            this.showSearchView();
        }
    }

    public void showAllItemsView() {
        this.allItemsPanel.setVisible(true);
        this.searchPanel.setVisible(false);
        this.categoryPanel.setVisible(false);
    }
}
