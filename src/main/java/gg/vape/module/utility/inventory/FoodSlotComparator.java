package gg.vape.module.utility.inventory;

import gg.vape.module.utility.AutoHotbar;
import gg.vape.wrapper.impl.ItemFood;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.Slot;
import java.util.Comparator;

public class FoodSlotComparator
implements Comparator<Slot> {
    final AutoHotbar autoHotbar;

    @Override
    public int compare(Slot first, Slot second) {
        return this.compareNutritionScore(first, second);
    }

    public int compareNutritionScore(Slot first, Slot second) {
        ItemStack firstStack = first.getStack();
        ItemStack secondStack = second.getStack();
        ItemFood firstFood = new ItemFood(firstStack.getItem());
        ItemFood secondFood = new ItemFood(secondStack.getItem());
        float firstScore = (float)firstFood.getNutrition() * firstFood.getSaturation();
        float secondScore = (float)secondFood.getNutrition() * secondFood.getSaturation();
        return Float.compare(firstScore, secondScore);
    }

    public FoodSlotComparator(AutoHotbar autoHotbar) {
        this.autoHotbar = autoHotbar;
    }
}
