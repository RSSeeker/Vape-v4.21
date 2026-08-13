package gg.vape.wrapper.impl;

import gg.vape.Vape;
import gg.vape.mapping.mappings.MMappedFieldSingletonWrapper;
import gg.vape.wrapper.Wrapper;

public class MappedFieldSingletonWrapper
extends Wrapper {
    private static MappedFieldSingletonWrapper humanoidArmorInstance;

    public MappedFieldSingletonWrapper(Object wrappedObject) {
        super(wrappedObject);
    }


    public static MappedFieldSingletonWrapper humanoidArmor() {
        if (humanoidArmorInstance == null) {
            humanoidArmorInstance = new MappedFieldSingletonWrapper(MMappedFieldSingletonWrapper.getHumanoidArmorField(Vape.INSTANCE.getMappings().CL).getObject(null));
        }
        return humanoidArmorInstance;
    }
}

