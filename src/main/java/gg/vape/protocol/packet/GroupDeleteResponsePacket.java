package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.GroupDeletePacket;
import gg.vape.protocol.packet.GroupDeleteStatus;
import gg.vape.protocol.packet.ZeusTrackedPacket;

public class GroupDeleteResponsePacket
extends ZeusTrackedPacket<GroupDeletePacket> {
    private GroupDeleteStatus status;

    public GroupDeleteResponsePacket() {
    }

    public GroupDeleteResponsePacket(GroupDeletePacket request, GroupDeleteStatus status) {
        super(request);
        this.status = status;
    }

    @Override
    public void x(ZeusPacketBuffer gx_12) {
        this.status = gx_12.readEnum(GroupDeleteStatus.class);
    }

    @Override
    public void T(ZeusPacketBuffer gx_12) {
        gx_12.writeEnum(this.status);
    }

    public GroupDeleteStatus getStatus() {
        return this.status;
    }
}
