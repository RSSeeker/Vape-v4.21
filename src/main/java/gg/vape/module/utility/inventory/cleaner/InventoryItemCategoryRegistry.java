package gg.vape.module.utility.inventory.cleaner;

import gg.vape.config.ClientSettings;
import gg.vape.module.utility.inventory.cleaner.BlockInventoryItemMatchers;
import gg.vape.module.utility.inventory.cleaner.ComparisonOperator;
import gg.vape.module.utility.inventory.cleaner.HiddenInventoryItemMatchers;
import gg.vape.module.utility.inventory.cleaner.InventoryItemCategory;
import gg.vape.module.utility.inventory.cleaner.ItemFilterSelection;
import gg.vape.module.utility.inventory.cleaner.MatcherBackedInventoryItemCategoryBuilder;
import gg.vape.module.utility.inventory.cleaner.StackSizeInventoryItemCategoryBuilder;
import gg.vape.module.utility.inventory.cleaner.ToolInventoryItemMatchers;
import gg.vape.module.utility.inventory.cleaner.WeaponInventoryItemMatchers;
import gg.vape.utils.BlockUtil;
import gg.vape.utils.ItemStackScoreUtil;
import gg.vape.wrapper.impl.ItemStack;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

public class InventoryItemCategoryRegistry {
    private static final Map<String, InventoryItemCategory> categoriesById = new LinkedHashMap<String, InventoryItemCategory>();
    public static final InventoryItemCategory FIRST_AVAILABLE = ((MatcherBackedInventoryItemCategoryBuilder)((MatcherBackedInventoryItemCategoryBuilder)((MatcherBackedInventoryItemCategoryBuilder)InventoryItemCategory.builder().matcherBacked().withId("any_type")).withName("First available")).withDisplayName("First accessible item found in inventory")).build();


    public static @UnmodifiableView List<InventoryItemCategory> getAll() {
        return new ArrayList<InventoryItemCategory>(categoriesById.values());
    }

    public static List<InventoryItemCategory> findCompatible(ItemFilterSelection itemFilterSelection) {
        ArrayList<InventoryItemCategory> arrayList = new ArrayList<InventoryItemCategory>();
        for (InventoryItemCategory inventoryItemCategory : categoriesById.values()) {
            if (!inventoryItemCategory.isCompatible(itemFilterSelection)) continue;
            arrayList.add(inventoryItemCategory);
        }
        return arrayList;
    }

    private static InventoryItemCategory register(InventoryItemCategory inventoryItemCategory) {
        categoriesById.put(inventoryItemCategory.getId(), inventoryItemCategory);
        return inventoryItemCategory;
    }

    @Nullable
    public static InventoryItemCategory getById(String id) {
        return categoriesById.get(id);
    }

    public static void initialize() {
        InventoryItemCategoryRegistry.register(FIRST_AVAILABLE);
        InventoryItemCategory highestAttackDamageCategory = InventoryItemCategoryRegistry.register(((MatcherBackedInventoryItemCategoryBuilder)((MatcherBackedInventoryItemCategoryBuilder)((MatcherBackedInventoryItemCategoryBuilder)((MatcherBackedInventoryItemCategoryBuilder)((MatcherBackedInventoryItemCategoryBuilder)((MatcherBackedInventoryItemCategoryBuilder)((MatcherBackedInventoryItemCategoryBuilder)InventoryItemCategory.builder().matcherBacked().withId("weapon_damage")).withName("Highest attack damage")).withDisplayName("Highest attack damage weapon")).withComparator(Comparator.comparingDouble(ItemStackScoreUtil::a))).addMatcher(WeaponInventoryItemMatchers.ANY_WEAPON)).addMatcher(WeaponInventoryItemMatchers.SWORDS)).addMatcher(WeaponInventoryItemMatchers.AXES)).build());
        InventoryItemCategory bestOverallWeaponCategory = InventoryItemCategoryRegistry.register(((MatcherBackedInventoryItemCategoryBuilder)((MatcherBackedInventoryItemCategoryBuilder)((MatcherBackedInventoryItemCategoryBuilder)((MatcherBackedInventoryItemCategoryBuilder)((MatcherBackedInventoryItemCategoryBuilder)((MatcherBackedInventoryItemCategoryBuilder)((MatcherBackedInventoryItemCategoryBuilder)InventoryItemCategory.builder().matcherBacked().withId("weapon_best")).withName("Best overall")).withDisplayName("Best overall weapon\nWeighed by damage and important enchants")).withComparator(Comparator.comparingDouble(ItemStackScoreUtil::k))).addMatcher(WeaponInventoryItemMatchers.ANY_WEAPON)).addMatcher(WeaponInventoryItemMatchers.SWORDS)).addMatcher(WeaponInventoryItemMatchers.AXES)).build());
        WeaponInventoryItemMatchers.ANY_WEAPON.setCategory(bestOverallWeaponCategory);
        WeaponInventoryItemMatchers.SWORDS.setCategory(bestOverallWeaponCategory);
        WeaponInventoryItemMatchers.AXES.setCategory(bestOverallWeaponCategory);
        InventoryItemCategory fastestMiningSpeedCategory = InventoryItemCategoryRegistry.register(((MatcherBackedInventoryItemCategoryBuilder)((MatcherBackedInventoryItemCategoryBuilder)((MatcherBackedInventoryItemCategoryBuilder)((MatcherBackedInventoryItemCategoryBuilder)((MatcherBackedInventoryItemCategoryBuilder)((MatcherBackedInventoryItemCategoryBuilder)((MatcherBackedInventoryItemCategoryBuilder)((MatcherBackedInventoryItemCategoryBuilder)InventoryItemCategory.builder().matcherBacked().withId("tool_speed")).withName("Fastest mining speed")).withComparator(Comparator.comparingDouble(ClientSettings::getToolDamageScore))).addMatcher(ToolInventoryItemMatchers.ANY_TOOL)).addMatcher(ToolInventoryItemMatchers.PICKAXES)).addMatcher(ToolInventoryItemMatchers.AXES)).addMatcher(ToolInventoryItemMatchers.SHOVELS)).addMatcher(ToolInventoryItemMatchers.HOES)).build());
        ToolInventoryItemMatchers.ANY_TOOL.setCategory(fastestMiningSpeedCategory);
        ToolInventoryItemMatchers.PICKAXES.setCategory(fastestMiningSpeedCategory);
        ToolInventoryItemMatchers.AXES.setCategory(fastestMiningSpeedCategory);
        ToolInventoryItemMatchers.SHOVELS.setCategory(fastestMiningSpeedCategory);
        ToolInventoryItemMatchers.HOES.setCategory(fastestMiningSpeedCategory);
        InventoryItemCategory hardestBlockCategory = InventoryItemCategoryRegistry.register(((MatcherBackedInventoryItemCategoryBuilder)((MatcherBackedInventoryItemCategoryBuilder)((MatcherBackedInventoryItemCategoryBuilder)((MatcherBackedInventoryItemCategoryBuilder)((MatcherBackedInventoryItemCategoryBuilder)InventoryItemCategory.builder().matcherBacked().withId("block_hardness")).withName("Hardest block")).withComparator(Comparator.comparingDouble(BlockUtil::O))).addMatcher(BlockInventoryItemMatchers.ANY_BLOCK)).addMatcher(BlockInventoryItemMatchers.BUILDING_BLOCKS)).build());
        InventoryItemCategory softestBlockCategory = InventoryItemCategoryRegistry.register(((MatcherBackedInventoryItemCategoryBuilder)((MatcherBackedInventoryItemCategoryBuilder)((MatcherBackedInventoryItemCategoryBuilder)((MatcherBackedInventoryItemCategoryBuilder)((MatcherBackedInventoryItemCategoryBuilder)InventoryItemCategory.builder().matcherBacked().withId("block_softness")).withName("Softest block")).withComparator(Comparator.comparingDouble(BlockUtil::O).reversed())).addMatcher(BlockInventoryItemMatchers.ANY_BLOCK)).addMatcher(BlockInventoryItemMatchers.BUILDING_BLOCKS)).build());
        InventoryItemCategoryRegistry.register(((StackSizeInventoryItemCategoryBuilder)((StackSizeInventoryItemCategoryBuilder)((StackSizeInventoryItemCategoryBuilder)InventoryItemCategory.builder().stackSize().withId("max_stack_size")).withName("Highest stack size")).withComparator(Comparator.comparingInt(ItemStack::t))).withStackSize(2).withOperator(ComparisonOperator.GREATER_THAN_OR_EQUAL).build());
        InventoryItemCategory highestBowDamageCategory = InventoryItemCategoryRegistry.register(((MatcherBackedInventoryItemCategoryBuilder)((MatcherBackedInventoryItemCategoryBuilder)((MatcherBackedInventoryItemCategoryBuilder)((MatcherBackedInventoryItemCategoryBuilder)((MatcherBackedInventoryItemCategoryBuilder)InventoryItemCategory.builder().matcherBacked().withId("bow_damage")).withName("Highest damage")).withDisplayName("Highest damage bow")).withComparator(Comparator.comparingDouble(ItemStackScoreUtil::f))).addMatcher(HiddenInventoryItemMatchers.ANY_BOW)).build());
        InventoryItemCategory bestOverallBowCategory = InventoryItemCategoryRegistry.register(((MatcherBackedInventoryItemCategoryBuilder)((MatcherBackedInventoryItemCategoryBuilder)((MatcherBackedInventoryItemCategoryBuilder)((MatcherBackedInventoryItemCategoryBuilder)((MatcherBackedInventoryItemCategoryBuilder)InventoryItemCategory.builder().matcherBacked().withId("bow_best")).withName("Best overall")).withDisplayName("Best overall bow\nWeighed by damage and important enchants")).withComparator(Comparator.comparingDouble(ItemStackScoreUtil::O))).addMatcher(HiddenInventoryItemMatchers.ANY_BOW)).build());
        HiddenInventoryItemMatchers.ANY_BOW.setCategory(bestOverallBowCategory);
    }
}

