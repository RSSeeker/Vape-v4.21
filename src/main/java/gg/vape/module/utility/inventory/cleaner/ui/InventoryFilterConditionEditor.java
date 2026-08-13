package gg.vape.module.utility.inventory.cleaner.ui;

import gg.vape.module.none.ClientSettings;
import gg.vape.module.utility.inventory.cleaner.ComparisonOperator;
import gg.vape.module.utility.inventory.cleaner.DurabilityValueMode;
import gg.vape.module.utility.inventory.cleaner.EmptyInventoryFilterCondition;
import gg.vape.module.utility.inventory.cleaner.EnchantmentFilterCondition;
import gg.vape.module.utility.inventory.cleaner.EnchantmentFilterMode;
import gg.vape.module.utility.inventory.cleaner.InventoryFilterCondition;
import gg.vape.module.utility.inventory.cleaner.InventoryFilterConditionGroup;
import gg.vape.module.utility.inventory.cleaner.InventoryFilterConditionType;
import gg.vape.module.utility.inventory.cleaner.InventoryFilterRule;
import gg.vape.module.utility.inventory.cleaner.ItemDurabilityFilterCondition;
import gg.vape.module.utility.inventory.cleaner.ItemFilterSelection;
import gg.vape.module.utility.inventory.cleaner.MaterialFilterCondition;
import gg.vape.module.utility.inventory.cleaner.MembershipMode;
import gg.vape.module.utility.inventory.cleaner.NumericFilterCondition;
import gg.vape.module.utility.inventory.cleaner.PotionEffectFilterCondition;
import gg.vape.module.utility.inventory.cleaner.PotionEffectFilterMode;
import gg.vape.module.utility.inventory.cleaner.TextFilterCondition;
import gg.vape.module.utility.inventory.cleaner.TextMatchMode;
import gg.vape.module.utility.inventory.cleaner.ui.MaterialFilterSelectionList;
import gg.vape.module.utility.inventory.cleaner.ui.TextSuggestionInputComponent;
import gg.vape.module.utility.inventory.cleaner.ui.TextSuggestionRow;
import gg.vape.ui.click.component.DropdownSelectComponent;
import gg.vape.ui.click.component.GlyphIconComponent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.LabeledTextInputComponent;
import gg.vape.ui.click.component.OptionTextFormatter;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.component.TextInputComponentBase;
import gg.vape.ui.click.component.gui.InteractiveComponent;
import gg.vape.ui.click.component.gui.TextButton;
import gg.vape.ui.click.component.gui.TextLabel;
import gg.vape.ui.click.component.layout.PaddedComponent;
import gg.vape.ui.click.frame.FrameComponent;
import gg.vape.ui.click.frame.ScrollableFrameComponent;
import gg.vape.utils.StringUtils;
import gg.vape.wrapper.impl.Enchantment;
import gg.vape.wrapper.impl.PotionEntry;
import gg.vape.wrapper.impl.PotionRegistry;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.jetbrains.annotations.Nullable;

class InventoryFilterConditionEditor
extends ScrollableFrameComponent {
    private final InventoryFilterRule filterRule;
    private InventoryFilterCondition<?> condition;
    private final InventoryFilterConditionGroup conditionGroup;
    private final Runnable onChanged;

    private void handleTextModeChanged(TextFilterCondition<?> textFilterCondition, DropdownSelectComponent dropdownSelectComponent) {
        textFilterCondition.withMatchMode((TextMatchMode)dropdownSelectComponent.getSelectedValue());
        this.rebuild();
    }

    private static void setPotionEntry(PotionEffectFilterCondition potionEffectFilterCondition, DropdownSelectComponent dropdownSelectComponent) {
        potionEffectFilterCondition.withPotionId(((PotionEntry)dropdownSelectComponent.getSelectedValue()).getLegacyId());
    }

    private void handlePotionModeChanged(PotionEffectFilterCondition potionEffectFilterCondition, DropdownSelectComponent dropdownSelectComponent) {
        potionEffectFilterCondition.withMode((PotionEffectFilterMode)dropdownSelectComponent.getSelectedValue());
        this.rebuild();
    }

    private static int comparePotionNames(PotionEntry firstPotion, PotionEntry secondPotion) {
        return firstPotion.getName().compareToIgnoreCase(secondPotion.getName());
    }

    private void handleTextInput(TextFilterCondition<?> textFilterCondition, TextSuggestionInputComponent textSuggestionInputComponent, char character, int keyCode) {
        if (textFilterCondition.getMatchMode().supportsMultipleValues()) {
            String enteredText;
            if (keyCode == 9 && !(enteredText = textSuggestionInputComponent.getInput().getText().trim()).isEmpty()) {
                textFilterCondition.addText(enteredText);
                textSuggestionInputComponent.getInput().setText("");
                this.rebuild();
            }
        } else {
            textFilterCondition.clearText();
            textFilterCondition.addText(textSuggestionInputComponent.getInput().getText());
        }
    }

    private void rebuild() {
        this.t$src$V$zbu1jn();
        this.h(new SpacerComponent(5.0, 0.0), new Object[0]);
        DropdownSelectComponent<InventoryFilterConditionType> dropdownSelectComponent = this.createDropdown("Select...", this.condition instanceof EmptyInventoryFilterCondition ? null : this.condition.getType(), OptionTextFormatter.namedValues(), Arrays.asList(InventoryFilterConditionType.values()));
        dropdownSelectComponent.addSelectionListener(() -> this.handleConditionTypeChanged(dropdownSelectComponent));
        this.h(dropdownSelectComponent, new Object[0]);
        if (this.condition instanceof EmptyInventoryFilterCondition) {
            DropdownSelectComponent<String> emptyDropdown = this.createDropdown("Select...", null, OptionTextFormatter.strings(), Collections.emptyList());
            emptyDropdown.setDisabled(true);
            this.h(emptyDropdown, new Object[0]);
            LabeledTextInputComponent emptyInput = new LabeledTextInputComponent("", false, true);
            emptyInput.setDisabled(true);
            emptyInput.getSearchIcon().setVisible(false);
            emptyInput.setShowDisabledOverlay(false);
            emptyInput.setBackgroundVisible(false);
            emptyInput.setHorizontalInset(0.0);
            emptyInput.setLeftInset(0.0f);
            emptyInput.setVerticalInset(0.0f);
            emptyInput.getActionButton().setVisible(false);
            emptyInput.setPlaceholderColor(InventoryFilterConditionEditor.J.h);
            emptyInput.o(this.A() - this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().C() - 17.5 - 20.0);
            emptyInput.Y(15.0);
            emptyInput.setNumericOnly(true);
            this.h(emptyInput, new Object[0]);
        } else {
            boolean requiresComparisonValue = true;
            if (this.condition instanceof EnchantmentFilterCondition) {
                EnchantmentFilterCondition enchantmentCondition = (EnchantmentFilterCondition)this.condition;
                DropdownSelectComponent<EnchantmentFilterMode> enchantmentModeDropdown = this.createDropdown("Select...", enchantmentCondition.getMode(), OptionTextFormatter.namedValues(), EnchantmentFilterMode.VALUES);
                enchantmentModeDropdown.addSelectionListener(() -> this.handleEnchantmentModeChanged(enchantmentCondition, enchantmentModeDropdown));
                enchantmentModeDropdown.setExplicitWidth(30.0);
                this.h(enchantmentModeDropdown, new Object[0]);
                List<String> enchantmentNames = new ArrayList<>();
                for (Enchantment enchantment : Enchantment.getEnchantments()) {
                    if (enchantment.isNull()) continue;
                    enchantmentNames.add(StringUtils.Q(enchantment.getTranslatedName(1)));
                }
                enchantmentNames.sort(String::compareToIgnoreCase);
                DropdownSelectComponent<String> enchantmentDropdown = this.createDropdown("Select enchantment...", enchantmentCondition.getEnchantment(), OptionTextFormatter.strings(), enchantmentNames);
                enchantmentDropdown.setExplicitWidth(enchantmentDropdown.getExplicitWidth() + 15.0);
                enchantmentDropdown.addSelectionListener(() -> InventoryFilterConditionEditor.setEnchantment(enchantmentCondition, enchantmentDropdown));
                this.h(enchantmentDropdown, new Object[0]);
                if (enchantmentCondition.getMode() == EnchantmentFilterMode.HAS) {
                    requiresComparisonValue = false;
                }
            }
            if (this.condition instanceof PotionEffectFilterCondition) {
                PotionEffectFilterCondition potionCondition = (PotionEffectFilterCondition)this.condition;
                DropdownSelectComponent<PotionEffectFilterMode> potionModeDropdown = this.createDropdown("Select...", potionCondition.getMode(), OptionTextFormatter.namedValues(), PotionEffectFilterMode.VALUES);
                potionModeDropdown.addSelectionListener(() -> this.handlePotionModeChanged(potionCondition, potionModeDropdown));
                potionModeDropdown.setExplicitWidth(30.0);
                this.h(potionModeDropdown, new Object[0]);
                List<PotionEntry> potionEntries = new ArrayList<PotionEntry>(PotionRegistry.O());
                potionEntries.sort(InventoryFilterConditionEditor::comparePotionNames);
                DropdownSelectComponent<PotionEntry> potionDropdown = this.createDropdown("Select potion...", potionCondition.getPotionEntry(), InventoryFilterConditionEditor::formatPotionName, potionEntries);
                potionDropdown.setExplicitWidth(potionDropdown.getExplicitWidth() + 15.0);
                potionDropdown.addSelectionListener(() -> InventoryFilterConditionEditor.setPotionEntry(potionCondition, potionDropdown));
                this.h(potionDropdown, new Object[0]);
                if (potionCondition.getMode() == PotionEffectFilterMode.HAS) {
                    requiresComparisonValue = false;
                }
            }
            if (this.condition instanceof ItemDurabilityFilterCondition) {
                ItemDurabilityFilterCondition durabilityCondition = (ItemDurabilityFilterCondition)this.condition;
                DropdownSelectComponent<DurabilityValueMode> durabilityDropdown = this.createDropdown("Select item...", durabilityCondition.getValueMode(), OptionTextFormatter.namedValues(), DurabilityValueMode.VALUES);
                durabilityDropdown.addSelectionListener(() -> this.handleDurabilityModeChanged(durabilityCondition, durabilityDropdown));
                this.h(durabilityDropdown, new Object[0]);
            }
            if (this.condition instanceof NumericFilterCondition && requiresComparisonValue) {
                NumericFilterCondition numericCondition = (NumericFilterCondition)this.condition;
                DropdownSelectComponent<ComparisonOperator> comparisonDropdown = this.createDropdown("Select item...", numericCondition.getOperator(), OptionTextFormatter.namedValues(), ComparisonOperator.VALUES);
                comparisonDropdown.setExplicitWidth(82.0);
                comparisonDropdown.addSelectionListener(() -> this.handleNumericOperatorChanged(numericCondition, comparisonDropdown));
                this.h(comparisonDropdown, new Object[0]);
                LabeledTextInputComponent numericInput = new LabeledTextInputComponent("", false, true);
                numericInput.getSearchIcon().setVisible(false);
                numericInput.setText(numericCondition.getValueText());
                numericInput.setShowDisabledOverlay(false);
                numericInput.setBackgroundVisible(false);
                numericInput.setHorizontalInset(0.0);
                numericInput.setLeftInset(0.0f);
                numericInput.setVerticalInset(0.0f);
                numericInput.getActionButton().setVisible(false);
                numericInput.setPlaceholderColor(InventoryFilterConditionEditor.J.h);
                numericInput.o(this.A() - this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().C() - 17.5 - 20.0);
                numericInput.Y(15.0);
                numericInput.setNumericOnly(true);
                numericInput.addKeyTypedListener((character, keyCode) -> InventoryFilterConditionEditor.parseNumericValue(numericCondition, numericInput, character, keyCode));
                this.h(new PaddedComponent(2.5, 0.0, 0.0, 0.0, numericInput), new Object[0]);
            } else if (this.condition instanceof TextFilterCondition) {
                TextFilterCondition<?> textCondition = (TextFilterCondition<?>)this.condition;
                DropdownSelectComponent<TextMatchMode> textModeDropdown = this.createDropdown("Select item...", textCondition.getMatchMode(), OptionTextFormatter.namedValues(), TextMatchMode.VALUES);
                textModeDropdown.setExplicitWidth(textModeDropdown.getExplicitWidth() + 25.0);
                textModeDropdown.addSelectionListener(() -> this.handleTextModeChanged(textCondition, textModeDropdown));
                this.h(textModeDropdown, new Object[0]);
                TextSuggestionInputComponent suggestionInput = new TextSuggestionInputComponent("", row -> InventoryFilterConditionEditor.removeTextValue(textCondition, row), this.A() - this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().C() - 17.5 - 20.0, 15.0, false, true);
                if (textCondition.getMatchMode().supportsMultipleValues()) {
                    suggestionInput.getInput().setText("");
                    for (String text : textCondition.getTexts()) {
                        suggestionInput.addRow(new TextSuggestionRow(text));
                    }
                } else {
                    suggestionInput.getInput().setText(textCondition.getTexts().isEmpty() ? "" : textCondition.getTexts().get(0));
                }
                suggestionInput.setShowDisabledOverlay(false);
                suggestionInput.getInput().setBackgroundVisible(false);
                suggestionInput.setHorizontalInset(0.0);
                suggestionInput.getInput().setLeftInset(0.0f);
                suggestionInput.getInput().setVerticalInset(0.0f);
                suggestionInput.getInput().getActionButton().setVisible(false);
                suggestionInput.getInput().setPlaceholderColor(InventoryFilterConditionEditor.J.h);
                suggestionInput.getInput().addKeyTypedListener((character, keyCode) -> this.handleTextInput(textCondition, suggestionInput, character, keyCode));
                this.h(new PaddedComponent(2.5, 0.0, 0.0, 0.0, suggestionInput), new Object[0]);
            } else if (this.condition instanceof MaterialFilterCondition) {
                MaterialFilterCondition materialCondition = (MaterialFilterCondition)this.condition;
                DropdownSelectComponent<MembershipMode> membershipDropdown = this.createDropdown("Select item...", materialCondition.getMembershipMode(), OptionTextFormatter.namedValues(), MembershipMode.VALUES);
                membershipDropdown.setExplicitWidth(30.0);
                membershipDropdown.addSelectionListener(() -> this.handleMembershipModeChanged(materialCondition, membershipDropdown));
                this.h(membershipDropdown, new Object[0]);
                MaterialFilterSelectionList selectionList = new MaterialFilterSelectionList(this.filterRule, materialCondition, this.A() - this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().C() - 36.0);
                for (ItemFilterSelection itemFilterSelection : materialCondition.getSelections()) {
                    selectionList.addSelectionRow(itemFilterSelection);
                }
                selectionList.setHorizontalInset(0.0);
                this.h(new PaddedComponent(2.5, 0.0, 0.0, 0.0, selectionList), new Object[0]);
                this.h(new SpacerComponent(this.A() - this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().C() - 17.5 - 20.0, 0.0), new Object[0]);
            } else {
                this.h(new SpacerComponent(this.A() - this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().C() - 17.5 - 20.0, 0.0), new Object[0]);
            }
        }
        TextButton andButton = new TextButton("AND", 0.65, InventoryFilterConditionEditor.J.B, InventoryFilterConditionEditor.J.O, 58.0, 9.0);
        andButton.setUseAlternateFont(true);
        andButton.setNormalTextColor(Color.WHITE);
        andButton.setDeriveTextColorFromBackground(false);
        andButton.setExplicitWidth(andButton.getTextWidth() + 10.0);
        andButton.setClickListener(this::addEmptyCondition);
        this.h(new PaddedComponent(5.0, 0.0, 5.0, 0.0, andButton), new Object[0]);
        PanelComponent closePanel = new PanelComponent(12.0, 12.0);
        GlyphIconComponent closeIcon = new GlyphIconComponent("newclose", 8.0, 8.0, 8.0, 8.0, InventoryFilterConditionEditor.J.A, InventoryFilterConditionEditor.J.A, null);
        closeIcon.setCenterHorizontally(true);
        closeIcon.setCenterVertically(true);
        closeIcon.setCornerRadius(4.0f);
        closeIcon.setBackgroundAnimationColors(InventoryFilterConditionEditor.J.R, InventoryFilterConditionEditor.J.c);
        closeIcon.addClickListener(this::scheduleConditionRemoval);
        closePanel.h(new PaddedComponent(6.0, 0.0, 3.0, 3.0, closeIcon), new Object[0]);
        closePanel.setShowDisabledOverlay(false);
        this.h(closePanel, new Object[0]);
    }

    private static String formatPotionName(PotionEntry potionEntry) {
        return potionEntry != null ? potionEntry.getName() : "";
    }

    private void handleConditionTypeChanged(DropdownSelectComponent dropdownSelectComponent) {
        InventoryFilterCondition inventoryFilterCondition = ((InventoryFilterConditionType)dropdownSelectComponent.getSelectedValue()).getFactory().get();
        this.conditionGroup.replaceCondition(this.condition, inventoryFilterCondition);
        this.condition = inventoryFilterCondition;
        this.rebuild();
    }

    private <T> DropdownSelectComponent<T> createDropdown(String placeholder, @Nullable T selectedValue, OptionTextFormatter<T> optionTextFormatter, List<T> options) {
        DropdownSelectComponent<T> dropdownSelectComponent = new DropdownSelectComponent<T>(placeholder, optionTextFormatter, options);
        dropdownSelectComponent.setEmptyText(placeholder);
        dropdownSelectComponent.setSelectedValue(selectedValue);
        dropdownSelectComponent.setShowLabelPrefix(false);
        dropdownSelectComponent.setExplicitHeight(this.L());
        this.getClass();
        dropdownSelectComponent.setExplicitWidth(60.0f + 5.0f * 2.0f);
        dropdownSelectComponent.setShowDisabledOverlay(false);
        dropdownSelectComponent.setHighlightedStyle(true);
        dropdownSelectComponent.setHorizontalInset(0.0);
        return dropdownSelectComponent;
    }

    private static void removeTextValue(TextFilterCondition<?> textFilterCondition, TextSuggestionRow textSuggestionRow) {
        textFilterCondition.removeText(textSuggestionRow.getText());
    }

    private static void setEnchantment(EnchantmentFilterCondition enchantmentFilterCondition, DropdownSelectComponent dropdownSelectComponent) {
        enchantmentFilterCondition.withEnchantment((String)dropdownSelectComponent.getSelectedValue());
    }

    private static void parseNumericValue(NumericFilterCondition numericFilterCondition, LabeledTextInputComponent input, char character, int keyCode) {
        try {
            numericFilterCondition.parseValue(input.getText());
        }
        catch (NumberFormatException numberFormatException) {
            // empty catch block
        }
    }

    private void handleDurabilityModeChanged(ItemDurabilityFilterCondition itemDurabilityFilterCondition, DropdownSelectComponent dropdownSelectComponent) {
        itemDurabilityFilterCondition.withValueMode((DurabilityValueMode)dropdownSelectComponent.getSelectedValue());
        this.rebuild();
    }

    private void removeCondition() {
        this.conditionGroup.removeCondition(this.condition);
        this.onChanged.run();
    }

    private void handleNumericOperatorChanged(NumericFilterCondition numericFilterCondition, DropdownSelectComponent dropdownSelectComponent) {
        numericFilterCondition.withOperator((ComparisonOperator)dropdownSelectComponent.getSelectedValue());
        this.rebuild();
    }

    public void setCondition(InventoryFilterCondition<?> condition) {
        this.condition = condition;
        this.rebuild();
    }

    private void addEmptyCondition() {
        EmptyInventoryFilterCondition emptyInventoryFilterCondition = new EmptyInventoryFilterCondition();
        this.conditionGroup.addCondition(emptyInventoryFilterCondition);
        this.onChanged.run();
    }

    private void handleEnchantmentModeChanged(EnchantmentFilterCondition enchantmentFilterCondition, DropdownSelectComponent dropdownSelectComponent) {
        enchantmentFilterCondition.withMode((EnchantmentFilterMode)dropdownSelectComponent.getSelectedValue());
        this.rebuild();
    }

    private void scheduleConditionRemoval() {
        ClientSettings.UI_EXECUTOR.execute(this::removeCondition);
    }

    public InventoryFilterConditionEditor(double width, InventoryFilterRule filterRule, InventoryFilterConditionGroup conditionGroup, InventoryFilterCondition<?> condition, Runnable onChanged) {
        super(width);
        this.v(width);
        this.h(15.0);
        this.filterRule = filterRule;
        this.conditionGroup = conditionGroup;
        this.condition = condition;
        this.onChanged = onChanged;
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("widthwrap");
        this.setShowDisabledOverlay(false);
        this.rebuild();
    }

    private void handleMembershipModeChanged(MaterialFilterCondition materialFilterCondition, DropdownSelectComponent dropdownSelectComponent) {
        materialFilterCondition.setMembershipMode((MembershipMode)dropdownSelectComponent.getSelectedValue());
        this.rebuild();
    }
}
