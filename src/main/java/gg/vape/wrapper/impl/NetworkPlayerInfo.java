package gg.vape.wrapper.impl;

public class NetworkPlayerInfo
extends Packet {
    public PositionMoveRotation getValues() {
        return new PositionMoveRotation(NetworkPlayerInfo.vapeInstance.getMappingsMapperCompat().RH.getValues(this.I));
    }

    public NetworkPlayerInfo(Object handle) {
        super(handle);
    }

    public int getEntityId() {
        return NetworkPlayerInfo.vapeInstance.getMappingsMapperCompat().RH.getEntityId(this.I);
    }
}
