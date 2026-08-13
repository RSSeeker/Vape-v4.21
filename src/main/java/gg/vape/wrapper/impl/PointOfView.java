package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class PointOfView
extends Wrapper {
    public static PointOfView[] values() {
        Object[] rawValues = PointOfView.vapeInstance.getMappingsMapperCompat().RZ.values();
        PointOfView[] values = new PointOfView[rawValues.length];
        for (int index = 0; index < rawValues.length; ++index) {
            values[index] = new PointOfView(rawValues[index]);
        }
        return values;
    }

    public PointOfView(Object wrappedObject) {
        super(wrappedObject);
    }

}

