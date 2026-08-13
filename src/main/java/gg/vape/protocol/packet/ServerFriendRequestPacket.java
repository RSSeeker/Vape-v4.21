package gg.vape.protocol.packet;

import gg.vape.friend.FriendRequestModel;
import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ZeusSerializablePacket;

public class ServerFriendRequestPacket
implements ZeusSerializablePacket {
    private FriendRequestModel request;

    public ServerFriendRequestPacket(FriendRequestModel request) {
        this.request = request;
    }

    @Override
    public void S(ZeusPacketBuffer zeusPacketBuffer) {
        this.request = new FriendRequestModel(zeusPacketBuffer);
    }

    public FriendRequestModel getRequest() {
        return this.request;
    }

    @Override
    public void o(ZeusPacketBuffer zeusPacketBuffer) {
        this.request.writeTo(zeusPacketBuffer);
    }

    public ServerFriendRequestPacket() {
    }
}
