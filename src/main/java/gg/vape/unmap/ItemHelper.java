package gg.vape.unmap;

import gg.vape.mapping.MappedClasses;
import gg.vape.unmap.ItemMatchRule;
import gg.vape.utils.BlockUtil;
import gg.vape.utils.ItemStackScoreUtil;
import gg.vape.wrapper.impl.DataComponentMap;
import gg.vape.wrapper.impl.DataComponents;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemStack;
import java.util.HashSet;
import java.util.function.Predicate;

public class ItemHelper {
    private final HashSet<ItemMatchRule> rules = new HashSet();

    private static boolean isBlockItem(Item item) {
        if (!item.isInstance(MappedClasses.Vw)) {
            return false;
        }
        if (ForgeVersion.MC_1_21_4.d()) {
            DataComponentMap dataComponentMap = item.g();
            boolean hasFoodComponent = dataComponentMap.V(DataComponents.d());
            return !hasFoodComponent;
        }
        return true;
    }

    private void registerItemPredicate(ItemMatchRule itemMatchRule, Predicate<Item> predicate) {
        itemMatchRule.setPredicate(predicate);
        this.rules.add(itemMatchRule);
    }

    private void registerCharacterPredicate(ItemMatchRule itemMatchRule, Predicate<Character> predicate) {
        itemMatchRule.setPredicate(predicate);
        this.rules.add(itemMatchRule);
    }

    public Predicate<Character> findCharacterRule(String alias) {
        for (ItemMatchRule itemMatchRule : this.rules) {
            for (String ruleAlias : itemMatchRule.getAliases()) {
                if (!ruleAlias.equalsIgnoreCase(alias)) continue;
                return itemMatchRule.getPredicate();
            }
        }
        return null;
    }

    public boolean matchesItem(String alias, ItemStack itemStack) {
        Item item = null;
        if (itemStack.isNotNull()) {
            item = itemStack.getItem();
        }
        for (ItemMatchRule itemMatchRule : this.rules) {
            for (String ruleAlias : itemMatchRule.getAliases()) {
                if (!ruleAlias.equalsIgnoreCase(alias)) continue;
                if (itemMatchRule.getAcceptedClasses() != null) {
                    if (itemMatchRule.getAcceptedClasses().length == 0) {
                        if (!itemStack.isNull()) continue;
                        return true;
                    }
                    for (Class acceptedClass : itemMatchRule.getAcceptedClasses()) {
                        if (item == null || !item.isInstance(acceptedClass)) continue;
                        return true;
                    }
                    continue;
                }
                if (itemMatchRule.getPredicate() == null || item == null || !itemMatchRule.getPredicate().test(item)) continue;
                return true;
            }
        }
        return false;
    }

    private void registerClassRule(ItemMatchRule itemMatchRule, Class ... classArray) {
        itemMatchRule.setAcceptedClasses(classArray);
        this.rules.add(itemMatchRule);
    }

    public ItemHelper() {
        Predicate<Item> blockPredicate = ItemHelper::isBlockItem;
        Predicate<Item> foodPredicate = ItemHelper::isFoodItem;
        this.registerItemPredicate(new ItemMatchRule(new String[]{"sword", "swords"}, null), ItemStackScoreUtil::h);
        this.registerClassRule(new ItemMatchRule(new String[]{"shovel", "shovels", "spade", "spades"}, null), MappedClasses.FM);
        this.registerClassRule(new ItemMatchRule(new String[]{"axe", "axes"}, null), MappedClasses.YP);
        this.registerItemPredicate(new ItemMatchRule(new String[]{"pickaxe", "pickaxes"}, null), ItemStackScoreUtil::m);
        this.registerItemPredicate(new ItemMatchRule(new String[]{"block", "blocks"}, null), blockPredicate);
        this.registerClassRule(new ItemMatchRule(new String[]{"fists", "none", "fist", "hand"}, null), new Class[0]);
        this.registerItemPredicate(new ItemMatchRule(new String[]{"food", "foods"}, null), foodPredicate);
        this.registerClassRule(new ItemMatchRule(new String[]{"potion", "potions"}, null), MappedClasses.Di);
        this.registerCharacterPredicate(new ItemMatchRule(new String[]{"bed", "beds"}, null), BlockUtil::v);
    }


    private static boolean isFoodItem(Item item) {
        if (ForgeVersion.MC_1_20_6.d()) {
            DataComponentMap dataComponentMap = item.g();
            return dataComponentMap.V(DataComponents.d());
        }
        return item.isInstance(MappedClasses.ITEM_FOOD);
    }
}

