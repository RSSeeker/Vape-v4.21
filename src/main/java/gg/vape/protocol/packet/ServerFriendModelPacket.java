package gg.vape.protocol.packet;

import gg.vape.friend.FriendModel;
import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ZeusSerializablePacket;

public class ServerFriendModelPacket
implements ZeusSerializablePacket {
    private FriendModel friend;

    @Override
    public void o(ZeusPacketBuffer zeusPacketBuffer) {
        this.friend.writeTo(zeusPacketBuffer);
    }

    public FriendModel getFriend() {
        return this.friend;
    }

    public ServerFriendModelPacket(FriendModel friend) {
        this.friend = friend;
    }

    public ServerFriendModelPacket() {
    }

    @Override
    public void S(ZeusPacketBuffer zeusPacketBuffer) {
        this.friend = new FriendModel(zeusPacketBuffer);
    }
}
