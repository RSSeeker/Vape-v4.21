package gg.vape.value;

import gg.vape.value.ListValue;
import java.util.ArrayList;
import java.util.Collection;

class ListValueMutableBackingList<C>
extends ArrayList<C> {
    final ListValue owner;

    ListValueMutableBackingList(ListValue listValue, Collection<? extends C> collection) {
        super(collection);
        this.owner = listValue;
    }

    @Override
    public boolean add(C c) {
        return super.add(c);
    }

    @Override
    public void clear() {
        super.clear();
    }

    @Override
    public boolean remove(Object object) {
        return super.remove(object);
    }
}
