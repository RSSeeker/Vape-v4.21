package gg.vape.module.utility.inventory.cleaner;

import gg.vape.config.ClientSettings;
import gg.vape.module.utility.inventory.cleaner.CompositeInventoryItemMatcher;
import gg.vape.module.utility.inventory.cleaner.InventoryItemMatchContext;
import gg.vape.module.utility.inventory.cleaner.InventoryItemMatcher;
import gg.vape.module.utility.inventory.cleaner.InventoryItemMatcherBuilderFoundation;
import gg.vape.module.utility.inventory.cleaner.InventoryItemMatcherGroup;
import gg.vape.module.utility.inventory.cleaner.InventoryItemMatcherRegistry;
import gg.vape.utils.ItemStackScoreUtil;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemStack;
import java.util.Comparator;

public class ToolInventoryItemMatchers {
    public static final CompositeInventoryItemMatcher SHOVELS;
    public static final CompositeInventoryItemMatcher HOES;
    public static final CompositeInventoryItemMatcher AXES;
    public static final CompositeInventoryItemMatcher PICKAXES;
    public static final CompositeInventoryItemMatcher ANY_TOOL;

    private static boolean matchesShovel(ItemStack itemStack, Item item) {
        return ItemStackScoreUtil.g(item);
    }

    private static boolean matchesAxe(ItemStack itemStack, Item item) {
        return ItemStackScoreUtil.T(item);
    }

    static void initialize() {
        InventoryItemMatcherRegistry.register(ANY_TOOL);
        InventoryItemMatcherRegistry.register(AXES);
        InventoryItemMatcherRegistry.register(PICKAXES);
        InventoryItemMatcherRegistry.register(SHOVELS);
        InventoryItemMatcherRegistry.register(HOES);
    }

    static {
        String[] labels = new String[]{"Any type of pickaxe", "Any type of axe", "hoe-hover@2x", "Any tool", "axe-tool", "Pickaxe", "Any type of hoe", "Axe", "Shovel", "Any type of shovel", "axe-hover@2x", "any-tool", "Any type of tool", "pickaxe-hover@2x", "hoe", "shovel", "Hoe", "tools", "shovel-hover@2x", "pickaxe"};
        ANY_TOOL = ((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)InventoryItemMatcher.builder().composite().withId(labels[11])).withName(labels[3])).withDescription(labels[12])).withIconName(labels[17])).withGroup(InventoryItemMatcherGroup.HIDDEN)).withPredicate(ToolInventoryItemMatchers::matchesAnyTool).build();
        PICKAXES = ((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)InventoryItemMatcher.builder().composite().withId(labels[19])).withName(labels[5])).withDescription(labels[0])).withIconName(labels[13])).withGroup(InventoryItemMatcherGroup.TOOLS)).withPredicate(ToolInventoryItemMatchers::matchesPickaxe).withComparator(Comparator.comparingDouble(ToolInventoryItemMatchers::toolSortScore))).build();
        AXES = ((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)InventoryItemMatcher.builder().composite().withId(labels[4])).withName(labels[7])).withDescription(labels[1])).withIconName(labels[10])).withGroup(InventoryItemMatcherGroup.TOOLS)).withPredicate(ToolInventoryItemMatchers::matchesAxe).withComparator(PICKAXES.getComparator())).build();
        SHOVELS = ((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)InventoryItemMatcher.builder().composite().withId(labels[15])).withName(labels[8])).withDescription(labels[9])).withIconName(labels[18])).withGroup(InventoryItemMatcherGroup.TOOLS)).withPredicate(ToolInventoryItemMatchers::matchesShovel).withComparator(PICKAXES.getComparator())).build();
        HOES = ((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)InventoryItemMatcher.builder().composite().withId(labels[14])).withName(labels[16])).withDescription(labels[6])).withIconName(labels[2])).withGroup(InventoryItemMatcherGroup.TOOLS)).withPredicate(ToolInventoryItemMatchers::matchesHoe).build();
    }

    private static boolean matchesAnyTool(ItemStack itemStack, Item item) {
        return ItemStackScoreUtil.I(item);
    }

    private static double toolSortScore(InventoryItemMatchContext context) {
        return ClientSettings.getToolDamageScore(context.getItemStack());
    }

    private static boolean matchesPickaxe(ItemStack itemStack, Item item) {
        return ItemStackScoreUtil.m(item);
    }

    private static boolean matchesHoe(ItemStack itemStack, Item item) {
        return ItemStackScoreUtil.k(item);
    }
}

