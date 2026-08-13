package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MFoodStats;
import gg.vape.wrapper.Wrapper;

public class FoodStats
extends Wrapper {
    public int getFoodLevel() {
        return MFoodStats.getFoodLevel(FoodStats.vapeInstance.getMappingsMapperCompat().DJ, this.I);
    }

    public FoodStats(Object wrappedObject) {
        super(wrappedObject);
    }
}
