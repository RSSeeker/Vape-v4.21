package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MTrajectoriesItemBridge
extends Mapping {
    private final MappingMethod isChargedMethod;

    public MTrajectoriesItemBridge() {
        super(MappedClasses.YA);
        this.isChargedMethod = this.registerStaticMethod("isCharged", true, Boolean.TYPE, new Class[]{MappedClasses.VK});
    }

    public boolean isCharged(Object itemStack) {
        return this.isChargedMethod.invokeBoolean(null, itemStack);
    }
}

