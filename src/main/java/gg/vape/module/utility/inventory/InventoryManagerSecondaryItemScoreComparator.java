package gg.vape.module.utility.inventory;

import gg.vape.config.ClientSettings;
import gg.vape.module.utility.AutoHotbar;
import gg.vape.wrapper.impl.Slot;
import java.util.Comparator;

public class InventoryManagerSecondaryItemScoreComparator
implements Comparator<Slot> {
    @Override
    public int compare(Slot first, Slot second) {
        return this.compareSecondaryScore(first, second);
    }
    final AutoHotbar autoHotbar;

    public InventoryManagerSecondaryItemScoreComparator(AutoHotbar autoHotbar) {
        this.autoHotbar = autoHotbar;
    }

    public int compareSecondaryScore(Slot first, Slot second) {
        return Double.compare(ClientSettings.getHiddenItemScore(first.getStack()), ClientSettings.getHiddenItemScore(second.getStack()));
    }
}
