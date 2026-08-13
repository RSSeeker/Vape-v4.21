package gg.vape.module.utility.mlg;

import gg.vape.mapping.mappings.MMLGBlockWrapper;
import gg.vape.wrapper.Wrapper;

public class MLGBlockWrapper
extends Wrapper {
    public MLGBlockWrapper(Object handle) {
        super(handle);
    }

    public static Object getWaterBlock() {
        return MMLGBlockWrapper.getWater(MLGBlockWrapper.vapeInstance.getMappingsMapperCompat().Rj);
    }

    public static Object getLavaBlock() {
        return MMLGBlockWrapper.getLava(MLGBlockWrapper.vapeInstance.getMappingsMapperCompat().Rj);
    }
}
