package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

import java.util.ArrayList;
import java.util.List;

public class ItemAttributeModifiersComponent
extends Wrapper {
    public List<Object> getRawModifiers() {
        return ItemAttributeModifiersComponent.vapeInstance.getMappingsMapperCompat().r.getModifiers(this.I);
    }

    public List<ItemAttributeModifiersComponent$Entry> getEntries() {
        ArrayList<ItemAttributeModifiersComponent$Entry> entries = new ArrayList<ItemAttributeModifiersComponent$Entry>();
        for (Object modifier : this.getRawModifiers()) {
            entries.add(new ItemAttributeModifiersComponent$Entry(modifier));
        }
        return entries;
    }

    public ItemAttributeModifiersComponent(Object wrappedObject) {
        super(wrappedObject);
    }
}
