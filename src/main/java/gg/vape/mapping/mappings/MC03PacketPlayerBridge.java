package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MC03PacketPlayerBridge
extends Mapping {
    private static final String CONSTRUCTOR_METHOD_NAME = "<init>";
    private final MappingMethod constructorMethod;

    public Object newInstance(boolean onGround, boolean horizontalCollision) {
        return this.constructorMethod.newInstance(onGround, horizontalCollision);
    }

    public MC03PacketPlayerBridge() {
        super(MappedClasses.Dl);
        this.constructorMethod = this.Y(CONSTRUCTOR_METHOD_NAME, false, Void.TYPE, new Class[]{Boolean.TYPE, Boolean.TYPE});
    }
}

