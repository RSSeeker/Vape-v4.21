package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MDataComponents;
import gg.vape.wrapper.Wrapper;

public class DataComponents
extends Wrapper {
    public DataComponents(Object object) {
        super(object);
    }

    public static DataComponentType E() {
        return new DataComponentType(MDataComponents.E(DataComponents.vapeInstance.getMappingsMapperCompat().q5));
    }

    public static DataComponentType V() {
        return new DataComponentType(MDataComponents.m(DataComponents.vapeInstance.getMappingsMapperCompat().q5));
    }

    public static DataComponentType l() {
        return new DataComponentType(MDataComponents.T(DataComponents.vapeInstance.getMappingsMapperCompat().q5));
    }

    public static DataComponentType X() {
        return new DataComponentType(MDataComponents.e(DataComponents.vapeInstance.getMappingsMapperCompat().q5));
    }

    public static DataComponentType p() {
        return new DataComponentType(MDataComponents.L(DataComponents.vapeInstance.getMappingsMapperCompat().q5));
    }

    public static DataComponentType k() {
        return new DataComponentType(MDataComponents.F(DataComponents.vapeInstance.getMappingsMapperCompat().q5));
    }

    public static DataComponentType d() {
        return new DataComponentType(MDataComponents.a(DataComponents.vapeInstance.getMappingsMapperCompat().q5));
    }

    public static DataComponentType F() {
        return new DataComponentType(MDataComponents.y(DataComponents.vapeInstance.getMappingsMapperCompat().q5));
    }

    public static DataComponentType T() {
        return new DataComponentType(MDataComponents.M(DataComponents.vapeInstance.getMappingsMapperCompat().q5));
    }

    public static DataComponentType o() {
        return new DataComponentType(MDataComponents.h(DataComponents.vapeInstance.getMappingsMapperCompat().q5));
    }

    public static DataComponentType R() {
        return new DataComponentType(MDataComponents.O(DataComponents.vapeInstance.getMappingsMapperCompat().q5));
    }

    public static DataComponentType t() {
        return new DataComponentType(MDataComponents.I(DataComponents.vapeInstance.getMappingsMapperCompat().q5));
    }

    public static DataComponentType O() {
        return new DataComponentType(MDataComponents.z(DataComponents.vapeInstance.getMappingsMapperCompat().q5));
    }

    public static DataComponentType I() {
        return new DataComponentType(MDataComponents.b(DataComponents.vapeInstance.getMappingsMapperCompat().q5));
    }
}

