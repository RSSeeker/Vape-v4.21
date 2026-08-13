package gg.vape.protocol.packet;

import gg.vape.friend.UserModel;
import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ChatToFriendResponsePacket;
import gg.vape.protocol.packet.ZeusTrackedPacket;

public class ChatToFriendPacket
extends ZeusTrackedPacket<ChatToFriendResponsePacket> {
    private long q;
    private static int[] l;
    private String Z;

    public long S() {
        return this.q;
    }

    public String Q() {
        return this.Z;
    }

    public static void W(int[] nArray) {
        l = nArray;
    }

    @Override
    public void x(ZeusPacketBuffer zeusPacketBuffer) {
        this.q = zeusPacketBuffer.readLong();
        this.Z = zeusPacketBuffer.readString(255);
    }

    @Override
    public void T(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeLong(this.q);
        zeusPacketBuffer.writeString(this.Z);
    }

    public ChatToFriendPacket(UserModel userModel, String string) {
        this.q = userModel.getId();
        this.Z = string;
    }

    public static int[] Y() {
        return l;
    }

    public ChatToFriendPacket() {
    }

    static {
        if (ChatToFriendPacket.Y() == null) {
            ChatToFriendPacket.W(new int[1]);
        }
    }
}

