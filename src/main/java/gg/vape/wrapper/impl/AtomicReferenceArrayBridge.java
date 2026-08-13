package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class AtomicReferenceArrayBridge
extends Wrapper {
    public AtomicReferenceArray getChunks() {
        return (AtomicReferenceArray)AtomicReferenceArrayBridge.vapeInstance.getMappingsMapperCompat().H.getChunks(this.I);
    }

    public AtomicReferenceArrayBridge(Object wrappedObject) {
        super(wrappedObject);
    }
}
