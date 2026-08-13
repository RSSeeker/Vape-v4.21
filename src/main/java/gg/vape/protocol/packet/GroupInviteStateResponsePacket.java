package gg.vape.protocol.packet;

import gg.vape.friend.PartyStateModel;
import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.GroupInviteStatePacket;
import gg.vape.protocol.packet.GroupInviteStateStatus;
import gg.vape.protocol.packet.ZeusTrackedPacket;
import org.jetbrains.annotations.Nullable;

public class GroupInviteStateResponsePacket
extends ZeusTrackedPacket<GroupInviteStatePacket> {
    private GroupInviteStateStatus status;
    private PartyStateModel partyState;

    public GroupInviteStateResponsePacket() {
    }

    public GroupInviteStateStatus getStatus() {
        return this.status;
    }

    public GroupInviteStateResponsePacket(GroupInviteStatePacket groupInviteStatePacket, GroupInviteStateStatus status) {
        super(groupInviteStatePacket);
        this.status = status;
    }

    @Override
    public void T(ZeusPacketBuffer gx_12) {
        gx_12.writeEnum(this.status);
        if (this.status == GroupInviteStateStatus.SUCCESSFULLY_ACCEPTED) {
            this.partyState.writeTo(gx_12);
        }
    }


    @Nullable
    public PartyStateModel getPartyState() {
        return this.partyState;
    }

    public GroupInviteStateResponsePacket(GroupInviteStatePacket groupInviteStatePacket, PartyStateModel partyStateModel) {
        this(groupInviteStatePacket, GroupInviteStateStatus.SUCCESSFULLY_ACCEPTED);
        this.partyState = partyStateModel;
    }

    @Override
    public void x(ZeusPacketBuffer gx_12) {
        this.status = gx_12.readEnum(GroupInviteStateStatus.class);
        if (this.status == GroupInviteStateStatus.SUCCESSFULLY_ACCEPTED) {
            this.partyState = new PartyStateModel(gx_12);
        }
    }
}
