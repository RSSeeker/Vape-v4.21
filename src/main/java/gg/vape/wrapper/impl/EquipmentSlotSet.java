package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MEquipmentSlotSet;
import gg.vape.wrapper.Wrapper;

public class EquipmentSlotSet
extends Wrapper {
    public int d() {
        return MEquipmentSlotSet.e(EquipmentSlotSet.vapeInstance.getMappingsMapperCompat().Ro, this.I);
    }

    public static EquipmentSlotSet n() {
        return new EquipmentSlotSet(MEquipmentSlotSet.e(EquipmentSlotSet.vapeInstance.getMappingsMapperCompat().Ro));
    }

    public static EquipmentSlotSet T(int slot) {
        switch (slot) {
            case 0:
                return EquipmentSlotSet.k();
            case 1:
                return EquipmentSlotSet.q();
            case 2:
                return EquipmentSlotSet.o();
            case 3:
                return EquipmentSlotSet.n();
            case 4:
                return EquipmentSlotSet.j();
            case 5:
                return EquipmentSlotSet.U();
            default:
                return null;
        }
    }

    public static EquipmentSlotSet q() {
        return new EquipmentSlotSet(MEquipmentSlotSet.P(EquipmentSlotSet.vapeInstance.getMappingsMapperCompat().Ro));
    }

    public static EquipmentSlotSet U() {
        return new EquipmentSlotSet(MEquipmentSlotSet.T(EquipmentSlotSet.vapeInstance.getMappingsMapperCompat().Ro));
    }

    public static EquipmentSlotSet o() {
        return new EquipmentSlotSet(MEquipmentSlotSet.s(EquipmentSlotSet.vapeInstance.getMappingsMapperCompat().Ro));
    }


    public static EquipmentSlotSet k() {
        return new EquipmentSlotSet(MEquipmentSlotSet.r(EquipmentSlotSet.vapeInstance.getMappingsMapperCompat().Ro));
    }

    public MappedFieldSingletonWrapper y() {
        return new MappedFieldSingletonWrapper(MEquipmentSlotSet.a(EquipmentSlotSet.vapeInstance.getMappingsMapperCompat().Ro, this.I));
    }

    public static EquipmentSlotSet j() {
        return new EquipmentSlotSet(MEquipmentSlotSet.t(EquipmentSlotSet.vapeInstance.getMappingsMapperCompat().Ro));
    }

    public EquipmentSlotSet(Object object) {
        super(object);
    }
}
