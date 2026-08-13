package gg.vape.module.utility.inventory.cleaner;

import gg.vape.mapping.MappedClasses;
import gg.vape.module.utility.inventory.cleaner.ClassInventoryItemMatcher;
import gg.vape.module.utility.inventory.cleaner.ClassInventoryItemMatcherBuilder;
import gg.vape.module.utility.inventory.cleaner.InventoryItemMatchContext;
import gg.vape.module.utility.inventory.cleaner.InventoryItemMatcher;
import gg.vape.module.utility.inventory.cleaner.InventoryItemMatcherGroup;
import gg.vape.module.utility.inventory.cleaner.InventoryItemMatcherRegistry;
import gg.vape.module.utility.inventory.cleaner.InventoryMatcherListMode;
import gg.vape.module.utility.inventory.cleaner.StringInventoryItemMatcher;
import gg.vape.module.utility.inventory.cleaner.StringInventoryItemMatcherBuilder;
import gg.vape.module.utility.inventory.cleaner.StringMatchOperator;
import java.util.Map;

public class BlockInventoryItemMatchers {
    public static final ClassInventoryItemMatcher ANY_BLOCK;
    public static final StringInventoryItemMatcher BUILDING_BLOCKS;

    static {
        String[] labels = new String[]{"building-block-hover@2x", "_planks", "building-blocks", "any-block", "obsidian", "blocks-hover@2x", "stone", "_wool", "Any block", "Any type of building block", "Building blocks", "red_sandstone", "terracotta", "end_stone"};
        ANY_BLOCK = ((ClassInventoryItemMatcherBuilder)((ClassInventoryItemMatcherBuilder)((ClassInventoryItemMatcherBuilder)((ClassInventoryItemMatcherBuilder)InventoryItemMatcher.builder().classMatcher().withId(labels[3])).withName(labels[8])).withIconName(labels[5])).withGroup(InventoryItemMatcherGroup.BLOCKS)).addClass(MappedClasses.Vw).withListMode(InventoryMatcherListMode.WHITELIST).build();
        StringInventoryItemMatcherBuilder stringInventoryItemMatcherBuilder = ((StringInventoryItemMatcherBuilder)((StringInventoryItemMatcherBuilder)((StringInventoryItemMatcherBuilder)((StringInventoryItemMatcherBuilder)((StringInventoryItemMatcherBuilder)InventoryItemMatcher.builder().stringMatcher().withId(labels[2])).withName(labels[10])).withDescription(labels[9])).withIconName(labels[0])).withGroup(InventoryItemMatcherGroup.BLOCKS)).addPattern(labels[7], StringMatchOperator.ENDS).addPattern(labels[6], StringMatchOperator.EQUALS).addPattern(labels[1], StringMatchOperator.ENDS).addPattern(labels[11], StringMatchOperator.EQUALS).addPattern(labels[12], StringMatchOperator.ENDS).addPattern(labels[13], StringMatchOperator.EQUALS).addPattern(labels[4], StringMatchOperator.EQUALS);
        stringInventoryItemMatcherBuilder.withComparator((first, second) -> {
            String firstName = first.getMappingEntry().getResourceKey();
            String secondName = second.getMappingEntry().getResourceKey();
            int firstRank = -1;
            int secondRank = -1;
            int rank = stringInventoryItemMatcherBuilder.getOperatorsByPattern().size();
            for (Map.Entry<String, StringMatchOperator> entry : stringInventoryItemMatcherBuilder.getOperatorsByPattern().entrySet()) {
                String pattern = entry.getKey();
                StringMatchOperator operator = entry.getValue();
                if (operator.getPredicate().test(firstName, pattern)) {
                    firstRank = rank;
                }
                if (operator.getPredicate().test(secondName, pattern)) {
                    secondRank = rank;
                }
                --rank;
                if (firstRank != -1 && secondRank != -1) {
                    break;
                }
            }
            return Integer.compare(firstRank, secondRank);
        });
        BUILDING_BLOCKS = stringInventoryItemMatcherBuilder.build();
    }

    public static void initialize() {
        InventoryItemMatcherRegistry.register(ANY_BLOCK);
        InventoryItemMatcherRegistry.register(BUILDING_BLOCKS);
    }

}
