package gg.vape.utils.render;

import gg.vape.utils.render.PrimitiveTopology;

public class PrimitiveTopologySwitchMap {
    public static final int[] TOPOLOGY_SWITCH_MAP = new int[PrimitiveTopology.values().length];

    PrimitiveTopologySwitchMap() {
    }

    static {
        try {
            PrimitiveTopologySwitchMap.TOPOLOGY_SWITCH_MAP[PrimitiveTopology.LINES.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            PrimitiveTopologySwitchMap.TOPOLOGY_SWITCH_MAP[PrimitiveTopology.QUADS.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            PrimitiveTopologySwitchMap.TOPOLOGY_SWITCH_MAP[PrimitiveTopology.TRIANGLES.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            PrimitiveTopologySwitchMap.TOPOLOGY_SWITCH_MAP[PrimitiveTopology.LINES_LOOP.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
