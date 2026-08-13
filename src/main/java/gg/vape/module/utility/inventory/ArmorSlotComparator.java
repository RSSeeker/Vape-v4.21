package gg.vape.module.utility.inventory;

import gg.vape.module.utility.AutoHotbar;
import gg.vape.wrapper.impl.ItemSplashPotion;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.PotionEffect;
import gg.vape.wrapper.impl.Slot;
import java.util.Comparator;
import java.util.List;

public class ArmorSlotComparator
implements Comparator<Slot> {
    final AutoHotbar autoHotbar;

    public ArmorSlotComparator(AutoHotbar autoHotbar) {
        this.autoHotbar = autoHotbar;
    }

    @Override
    public int compare(Slot first, Slot second) {
        ItemStack firstStack = first.getStack();
        ItemStack secondStack = second.getStack();
        List<PotionEffect> firstEffects = new ItemSplashPotion(firstStack.getItem()).getPotionEffects(firstStack);
        List<PotionEffect> secondEffects = new ItemSplashPotion(secondStack.getItem()).getPotionEffects(secondStack);
        int result = 0;
        for (PotionEffect firstEffect : firstEffects) {
            int effectId = firstEffect.C();
            for (PotionEffect secondEffect : secondEffects) {
                int otherEffectId = secondEffect.C();
                if (effectId != otherEffectId) continue;
                result += Integer.compare(firstEffect.L(), secondEffect.L());
            }
        }
        return result;
    }
}

