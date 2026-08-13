package gg.vape.protocol.packet;

import gg.vape.friend.UserModel;
import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ZeusSerializablePacket;
import java.util.UUID;

public class FriendMinecraftProfileUpdatePacket
implements ZeusSerializablePacket {
    private String v;
    private long p;
    private UUID Q;

    @Override
    public void S(ZeusPacketBuffer zeusPacketBuffer) {
        this.p = zeusPacketBuffer.readLong();
        this.Q = zeusPacketBuffer.readUuid();
        this.v = zeusPacketBuffer.readString(16);
    }

    public FriendMinecraftProfileUpdatePacket() {
    }

    public String G() {
        return this.v;
    }

    public FriendMinecraftProfileUpdatePacket(UserModel userModel, UUID uUID, String string) {
        this.p = userModel.getId();
        this.Q = uUID;
        this.v = string;
    }

    public UUID l() {
        return this.Q;
    }

    public long H() {
        return this.p;
    }

    @Override
    public void o(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeLong(this.p);
        zeusPacketBuffer.writeUuid(this.Q);
        zeusPacketBuffer.writeString(this.v);
    }
}

