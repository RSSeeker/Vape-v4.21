package gg.vape.value;

import gg.vape.unmap.ItemLimitData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemLimitDataGroup {
    private boolean expanded = false;
    private final List<ItemLimitData> entries;

    public boolean isExpanded() {
        return this.expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public ItemLimitDataGroup(ItemLimitData ... entries) {
        this.expanded = entries.length > 4;
        this.entries = new ArrayList<ItemLimitData>(Arrays.asList(entries));
    }

    public ItemLimitDataGroup(List<ItemLimitData> entries) {
        this.expanded = entries.size() > 4;
        this.entries = entries;
    }

    public List<ItemLimitData> getEntries() {
        return this.entries;
    }
}
