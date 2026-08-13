package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.GroupInvitePacket;
import gg.vape.protocol.packet.GroupInviteStatus;
import gg.vape.protocol.packet.ZeusTrackedPacket;
import gg.vape.ui.click.component.GuiComponent;

public class GroupInviteResponsePacket
extends ZeusTrackedPacket<GroupInvitePacket> {
    private GroupInviteStatus status;
    private static GuiComponent[] S;

    @Override
    public void x(ZeusPacketBuffer zeusPacketBuffer) {
        this.status = zeusPacketBuffer.readEnum(GroupInviteStatus.class);
    }

    public GroupInviteStatus getStatus() {
        return this.status;
    }

    public GroupInviteResponsePacket() {
    }

    public static GuiComponent[] O() {
        return S;
    }

    @Override
    public void T(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeEnum(this.status);
    }

    public static void E(GuiComponent[] guiComponentArray) {
        S = guiComponentArray;
    }

    public GroupInviteResponsePacket(GroupInvitePacket groupInvitePacket, GroupInviteStatus status) {
        super(groupInvitePacket);
        this.status = status;
    }

    static {
        if (GroupInviteResponsePacket.O() != null) {
            GroupInviteResponsePacket.E(new GuiComponent[4]);
        }
    }
}
