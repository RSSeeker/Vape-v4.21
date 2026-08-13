package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.GroupLeaveResponsePacket;
import gg.vape.protocol.packet.ZeusTrackedPacket;

public class GroupLeavePacket
extends ZeusTrackedPacket<GroupLeaveResponsePacket> {
    private static int[] z;

    @Override
    public void x(ZeusPacketBuffer gx_12) {
    }

    public static void b(int[] nArray) {
        z = nArray;
    }

    @Override
    public void T(ZeusPacketBuffer gx_12) {
    }

    public static int[] l() {
        return z;
    }

    static {
        if (GroupLeavePacket.l() != null) {
            GroupLeavePacket.b(new int[4]);
        }
    }
}

