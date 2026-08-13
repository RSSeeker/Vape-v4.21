package gg.vape.wrapper.impl;

public class SPacketDestroyEntities
extends SPacketDestroyEntitiesBase {
    public int[] getEntityIds() {
        return SPacketDestroyEntities.vapeInstance.getMappingsMapperCompat().C7.getEntityIds(this.I);
    }

    public SPacketDestroyEntities(Object handle) {
        super(handle);
    }
}
