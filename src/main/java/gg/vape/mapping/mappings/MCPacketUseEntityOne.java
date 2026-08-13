package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MCPacketUseEntityOne
extends Mapping {
    private final MappingMethod playerAbilitiesPacketConstructor;

    public MCPacketUseEntityOne() {
        super(MappedClasses.z1);
        this.playerAbilitiesPacketConstructor = this.Y("<init>", false, Void.TYPE, new Class[]{MappedClasses.q9});
    }

    public Object createPlayerAbilitiesPacket(Object playerCapabilities) {
        return this.playerAbilitiesPacketConstructor.newInstance(playerCapabilities);
    }
}

