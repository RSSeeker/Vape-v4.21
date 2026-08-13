package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class EnumActionResult
extends Wrapper {
    public EnumActionResult(Object actionResultHandle) {
        super(actionResultHandle);
    }

    public static EnumActionResult pass() {
        return new EnumActionResult(EnumActionResult.vapeInstance.getMappingsMapperCompat().enumActionResult.getPass());
    }

    public static EnumActionResult fail() {
        return new EnumActionResult(EnumActionResult.vapeInstance.getMappingsMapperCompat().enumActionResult.getFail());
    }

    public static EnumActionResult success() {
        return new EnumActionResult(EnumActionResult.vapeInstance.getMappingsMapperCompat().enumActionResult.getSuccess());
    }
}
