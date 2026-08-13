package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MSPacketHeldItemChange
extends Mapping {
    private final MappingMethod closeWindowPacketConstructor;

    public MSPacketHeldItemChange() {
        super(MappedClasses.l7);
        this.closeWindowPacketConstructor = this.Y("<init>", false, Void.TYPE, new Class[]{Integer.TYPE});
    }

    public Object createCloseWindowPacket(int windowId) {
        return this.closeWindowPacketConstructor.newInstance(windowId);
    }
}

