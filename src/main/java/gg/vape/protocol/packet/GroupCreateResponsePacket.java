package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.GroupCreatePacket;
import gg.vape.protocol.packet.GroupCreateStatus;
import gg.vape.protocol.packet.ZeusTrackedPacket;

public class GroupCreateResponsePacket
extends ZeusTrackedPacket<GroupCreatePacket> {
    private GroupCreateStatus status;
    private static String X;

    public GroupCreateStatus getStatus() {
        return this.status;
    }

    public GroupCreateResponsePacket(GroupCreatePacket groupCreatePacket, GroupCreateStatus status) {
        super(groupCreatePacket);
        this.status = status;
    }

    public static void m(String string) {
        X = string;
    }

    static {
        if (GroupCreateResponsePacket.Z() != null) {
            GroupCreateResponsePacket.m("koPAI");
        }
    }

    public static String Z() {
        return X;
    }

    @Override
    public void T(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeEnum(this.status);
    }

    @Override
    public void x(ZeusPacketBuffer zeusPacketBuffer) {
        this.status = zeusPacketBuffer.readEnum(GroupCreateStatus.class);
    }

    public GroupCreateResponsePacket() {
    }
}
