package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class FogType
extends Wrapper {
    public static FogType noneOrSky() {
        return new FogType(FogType.vapeInstance.getMappingsMapperCompat().fogType.getNoneOrSky());
    }

    public FogType(Object wrappedObject) {
        super(wrappedObject);
    }

    public static FogType terrainOrWorld() {
        return new FogType(FogType.vapeInstance.getMappingsMapperCompat().fogType.getTerrainOrWorld());
    }
}
