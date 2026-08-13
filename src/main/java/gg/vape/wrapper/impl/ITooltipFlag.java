package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class ITooltipFlag
extends Wrapper {
    public static ITooltipFlag water() {
        return new ITooltipFlag(ITooltipFlag.vapeInstance.getMappingsMapperCompat().clipContextFluidMode.getWater());
    }

    public ITooltipFlag(Object handle) {
        super(handle);
    }
}
