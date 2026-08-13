package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MCPacketUseEntityActionPacket
extends Mapping {
    private static final String LOCATION_FIELD_NAME = "location";
    private final MappingField locationField;

    public MCPacketUseEntityActionPacket() {
        super(MappedClasses.uL);
        this.locationField = this.J(LOCATION_FIELD_NAME, true, MappedClasses.qP);
    }

    public Object getLocation(Object packet) {
        return this.locationField.getObject(packet);
    }
}

