package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MPotionVersionRange;
import gg.vape.wrapper.Wrapper;
import java.util.Set;

public class PotionVersionRange
extends Wrapper {
    public Set entrySet() {
        return (Set)MPotionVersionRange.getEntrySet(PotionVersionRange.vapeInstance.getMappingsMapperCompat().itemEnchantments, this.I);
    }

    public static PotionVersionRange empty() {
        return new PotionVersionRange(MPotionVersionRange.getEmpty(PotionVersionRange.vapeInstance.getMappingsMapperCompat().itemEnchantments));
    }

    public PotionVersionRange(Object handle) {
        super(handle);
    }
}
