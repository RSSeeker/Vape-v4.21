package gg.vape.wrapper.impl;

public class RegistryNamespaced
extends RegistrySimple {
    public Object getByValue(int id) {
        return RegistryNamespaced.vapeInstance.getMappingsMapperCompat().qc.getByValue(this.I, id);
    }

    public RegistryNamespaced(Object wrappedObject) {
        super(wrappedObject);
    }
}
