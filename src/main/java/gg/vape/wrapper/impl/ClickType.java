package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MClickType;
import gg.vape.wrapper.Wrapper;

public class ClickType
extends Wrapper {
    public static ClickType[] VALUES = new ClickType[]{ClickType.pickup(), ClickType.quickMove(), ClickType.swap(), ClickType.cloneStack(), ClickType.throwStack(), ClickType.quickCraft(), ClickType.pickupAll()};

    public static ClickType quickCraft() {
        return new ClickType(MClickType.getQuickCraft(ClickType.vapeInstance.getMappingsMapperCompat().clickType));
    }

    public ClickType(Object object) {
        super(object);
    }

    public static ClickType pickup() {
        return new ClickType(MClickType.getPickup(ClickType.vapeInstance.getMappingsMapperCompat().clickType));
    }

    public static ClickType cloneStack() {
        return new ClickType(MClickType.getClone(ClickType.vapeInstance.getMappingsMapperCompat().clickType));
    }

    public static ClickType quickMove() {
        return new ClickType(MClickType.getQuickMove(ClickType.vapeInstance.getMappingsMapperCompat().clickType));
    }

    public static ClickType pickupAll() {
        return new ClickType(MClickType.getPickupAll(ClickType.vapeInstance.getMappingsMapperCompat().clickType));
    }

    public static ClickType throwStack() {
        return new ClickType(MClickType.getThrow(ClickType.vapeInstance.getMappingsMapperCompat().clickType));
    }

    public static ClickType swap() {
        return new ClickType(MClickType.getSwap(ClickType.vapeInstance.getMappingsMapperCompat().clickType));
    }
}
