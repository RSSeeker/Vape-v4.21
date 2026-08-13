package gg.vape.protocol.packet;

import gg.vape.friend.FriendRequestModel;
import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.FriendRequestPacket;
import gg.vape.protocol.packet.FriendRequestResponseStatus;
import gg.vape.protocol.packet.ZeusTrackedPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FriendRequestResponsePacket
extends ZeusTrackedPacket<FriendRequestPacket> {
    @Nullable
    private FriendRequestModel request;
    private FriendRequestResponseStatus status;

    @Override
    public void T(ZeusPacketBuffer gx_12) {
        gx_12.writeEnum(this.status);
        if (this.request != null) {
            this.request.writeTo(gx_12);
        }
    }

    @Nullable
    public FriendRequestModel getRequest() {
        return this.request;
    }


    public FriendRequestResponsePacket() {
    }

    public FriendRequestResponsePacket(@Nullable FriendRequestPacket friendRequestPacket, FriendRequestResponseStatus status) {
        super(friendRequestPacket);
        this.status = status;
    }

    @Override
    public void x(ZeusPacketBuffer gx_12) {
        this.status = gx_12.readEnum(FriendRequestResponseStatus.class);
        if (this.status == FriendRequestResponseStatus.SENT) {
            this.request = new FriendRequestModel(gx_12);
        }
    }

    public FriendRequestResponseStatus getStatus() {
        return this.status;
    }

    public FriendRequestResponsePacket(@Nullable FriendRequestPacket friendRequestPacket, @NotNull FriendRequestModel request) {
        this(friendRequestPacket, FriendRequestResponseStatus.SENT);
        this.request = request;
    }
}
