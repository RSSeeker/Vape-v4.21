package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;
import java.util.List;

public class Ordering
extends Wrapper {
    public Ordering(Object wrappedObject) {
        super(wrappedObject);
    }

    public List sortedCopy(Iterable iterable) {
        return Ordering.vapeInstance.getMappingsMapperCompat().qm.sortedCopy(this.I, iterable);
    }
}
