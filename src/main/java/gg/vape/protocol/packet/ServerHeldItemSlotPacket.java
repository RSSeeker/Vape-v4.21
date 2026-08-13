package gg.vape.protocol.packet;

import gg.vape.friend.UserModel;
import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ZeusSerializablePacket;

public class ServerHeldItemSlotPacket
implements ZeusSerializablePacket {
    private int heldItemSlot;
    private long userId;

    public long getUserId() {
        return this.userId;
    }

    public ServerHeldItemSlotPacket(UserModel userModel, int heldItemSlot) {
        this.userId = userModel.getId();
        this.heldItemSlot = heldItemSlot;
    }

    public int getHeldItemSlot() {
        return this.heldItemSlot;
    }

    public ServerHeldItemSlotPacket() {
    }

    @Override
    public void S(ZeusPacketBuffer zeusPacketBuffer) {
        this.userId = zeusPacketBuffer.readLong();
        this.heldItemSlot = zeusPacketBuffer.readInt();
    }

    @Override
    public void o(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeLong(this.userId);
        zeusPacketBuffer.writeInt(this.heldItemSlot);
    }
}
