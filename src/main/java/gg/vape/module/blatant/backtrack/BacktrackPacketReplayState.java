package gg.vape.module.blatant.backtrack;

import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Packet;
import gg.vape.wrapper.impl.SPacketDestroyEntities;

public class BacktrackPacketReplayState
extends Packet {
    public BacktrackPacketReplayState(Object handle) {
        super(handle);
    }

    public int[] getDestroyedEntityIds() {
        if (ForgeVersion.MC_1_21_4.d()) {
            SPacketDestroyEntities destroyPacket = new SPacketDestroyEntities(BacktrackPacketReplayState.vapeInstance.getMappingsMapperCompat().Rt.getEntityIdsContainer(this.I));
            return destroyPacket.getEntityIds();
        }
        return BacktrackPacketReplayState.vapeInstance.getMappingsMapperCompat().Rt.getEntityIds(this.I);
    }
}
