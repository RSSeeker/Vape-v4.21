package gg.vape.module.utility.invcleaner;

import gg.vape.utils.datas.ItemStackData;
import java.util.Comparator;

public class ItemDataComparator
implements Comparator<ItemStackData> {
    int targetSlot;

    @Override
    public int compare(ItemStackData firstItem, ItemStackData secondItem) {
        int firstSlot = firstItem.getSlot();
        int secondSlot = secondItem.getSlot();
        int firstDistance = firstSlot > this.targetSlot ? firstSlot - this.targetSlot : this.targetSlot - firstSlot;
        int secondDistance = secondSlot > this.targetSlot ? secondSlot - this.targetSlot : this.targetSlot - secondSlot;
        return firstDistance < secondDistance ? -1 : 0;
    }


    public ItemDataComparator(int targetSlot) {
        this.targetSlot = targetSlot;
    }
}

