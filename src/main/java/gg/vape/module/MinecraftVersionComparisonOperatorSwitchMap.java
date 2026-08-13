package gg.vape.module;

import gg.vape.module.MinecraftVersionComparisonOperator;

class MinecraftVersionComparisonOperatorSwitchMap {
    static final int[] operatorOrdinals = new int[MinecraftVersionComparisonOperator.values().length];

    MinecraftVersionComparisonOperatorSwitchMap() {
    }

    static {
        try {
            MinecraftVersionComparisonOperatorSwitchMap.operatorOrdinals[MinecraftVersionComparisonOperator.EQUALS.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            MinecraftVersionComparisonOperatorSwitchMap.operatorOrdinals[MinecraftVersionComparisonOperator.NOT_EQUAL.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            MinecraftVersionComparisonOperatorSwitchMap.operatorOrdinals[MinecraftVersionComparisonOperator.GREATER_THAN.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            MinecraftVersionComparisonOperatorSwitchMap.operatorOrdinals[MinecraftVersionComparisonOperator.GREATHER_THAN_OR_EQUAL.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            MinecraftVersionComparisonOperatorSwitchMap.operatorOrdinals[MinecraftVersionComparisonOperator.LESS_THAN.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            MinecraftVersionComparisonOperatorSwitchMap.operatorOrdinals[MinecraftVersionComparisonOperator.LESS_THAN_OR_EQUAL.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}

