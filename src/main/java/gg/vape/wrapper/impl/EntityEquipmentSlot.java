package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MEntityEquipmentSlot;
import gg.vape.wrapper.Wrapper;

public class EntityEquipmentSlot
extends Wrapper {
    public static EntityEquipmentSlot L() {
        return new EntityEquipmentSlot(MEntityEquipmentSlot.Q(EntityEquipmentSlot.vapeInstance.getMappingsMapperCompat().CY));
    }

    public boolean v() {
        return MEntityEquipmentSlot.S(EntityEquipmentSlot.vapeInstance.getMappingsMapperCompat().CY, this.I);
    }

    public EntityEquipmentSlot(Object object) {
        super(object);
    }

    public int W() {
        return MEntityEquipmentSlot.c(EntityEquipmentSlot.vapeInstance.getMappingsMapperCompat().CY, this.I);
    }
}

