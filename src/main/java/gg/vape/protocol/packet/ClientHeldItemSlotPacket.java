package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ZeusSerializablePacket;

public class ClientHeldItemSlotPacket
implements ZeusSerializablePacket {
    private int heldItemSlot;

    @Override
    public void o(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeInt(this.heldItemSlot);
    }

    public int getHeldItemSlot() {
        return this.heldItemSlot;
    }

    public ClientHeldItemSlotPacket(int heldItemSlot) {
        this.heldItemSlot = heldItemSlot;
    }

    @Override
    public void S(ZeusPacketBuffer zeusPacketBuffer) {
        this.heldItemSlot = zeusPacketBuffer.readInt();
    }

    public ClientHeldItemSlotPacket() {
    }
}
