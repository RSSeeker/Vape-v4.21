package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MTickEventPhase;
import gg.vape.wrapper.Wrapper;

public class TickEventPhase
extends Wrapper {
    public static TickEventPhase q() {
        return new TickEventPhase(MTickEventPhase.B(TickEventPhase.vapeInstance.getMappingsMapperCompat().R8));
    }

    public TickEventPhase(Object object) {
        super(object);
    }

    public static TickEventPhase J() {
        return new TickEventPhase(MTickEventPhase.G(TickEventPhase.vapeInstance.getMappingsMapperCompat().R8));
    }
}

