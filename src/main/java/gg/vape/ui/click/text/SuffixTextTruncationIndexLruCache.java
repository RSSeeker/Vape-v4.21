package gg.vape.ui.click.text;

import gg.vape.ui.click.text.SuffixTextTruncationIndexCache;
import java.util.LinkedHashMap;
import java.util.Map;

public class SuffixTextTruncationIndexLruCache
extends LinkedHashMap<Integer, Integer> {
    final SuffixTextTruncationIndexCache owner;

    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, Integer> entry) {
        return this.size() > 512;
    }

    public SuffixTextTruncationIndexLruCache(SuffixTextTruncationIndexCache owner, int initialCapacity, float loadFactor, boolean accessOrder) {
        super(initialCapacity, loadFactor, accessOrder);
        this.owner = owner;
    }

}
