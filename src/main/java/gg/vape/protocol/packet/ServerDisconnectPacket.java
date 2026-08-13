package gg.vape.protocol.packet;

import gg.vape.manager.client.OnlineDisconnectReason;
import gg.vape.protocol.ZeusPacketBuffer;

public class ServerDisconnectPacket
implements ZeusSerializablePacket {
    private static int[] R;
    private OnlineDisconnectReason t;

    public static int[] i() {
        return R;
    }

    @Override
    public void o(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeEnum(this.t);
    }

    public ServerDisconnectPacket(OnlineDisconnectReason onlineDisconnectReason) {
        this.t = onlineDisconnectReason;
    }

    public OnlineDisconnectReason q() {
        return this.t;
    }

    public ServerDisconnectPacket() {
    }

    public static void U(int[] nArray) {
        R = nArray;
    }

    @Override
    public void S(ZeusPacketBuffer zeusPacketBuffer) {
        this.t = zeusPacketBuffer.readEnum(OnlineDisconnectReason.class);
    }

    static {
        if (ServerDisconnectPacket.i() != null) {
            ServerDisconnectPacket.U(new int[1]);
        }
    }
}

