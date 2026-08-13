package gg.vape.wrapper.impl;

import java.util.stream.Stream;

public class EnchantmentRegistryLookup
extends ResourceKeyRegistryLookup {
    public Stream<EnchantmentHolder> listElements() {
        return EnchantmentRegistryLookup.vapeInstance.getMappingsMapperCompat().R2.listElements(this.getObject()).map(EnchantmentHolder::new);
    }

    public EnchantmentRegistryLookup(Object wrappedObject) {
        super(wrappedObject);
    }
}
