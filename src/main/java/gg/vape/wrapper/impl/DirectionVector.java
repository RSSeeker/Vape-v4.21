package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MDirectionVector;
import gg.vape.wrapper.Wrapper;

public class DirectionVector
extends Wrapper {
    public DirectionVector(Object wrappedObject) {
        super(wrappedObject);
    }

    public static DirectionVector positive() {
        return new DirectionVector(MDirectionVector.getPositive(DirectionVector.vapeInstance.getMappings().qx));
    }
}
