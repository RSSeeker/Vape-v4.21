package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.FriendRequestUpdateResponsePacket;
import gg.vape.protocol.packet.ZeusTrackedPacket;

public class FriendRequestUpdatePacket
extends ZeusTrackedPacket<FriendRequestUpdateResponsePacket> {
    private boolean accepted;
    private long userId;
    private static int R;

    public FriendRequestUpdatePacket() {
    }

    public long getUserId() {
        return this.userId;
    }


    @Override
    public void T(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeLong(this.userId);
        zeusPacketBuffer.writeBoolean(this.accepted);
    }

    public FriendRequestUpdatePacket(long userId, boolean accepted) {
        this();
        this.userId = userId;
        this.accepted = accepted;
    }

    public static int F() {
        int n = FriendRequestUpdatePacket.Q();
        return 0;
    }

    public static void e(int n) {
        R = n;
    }

    public boolean isAccepted() {
        return this.accepted;
    }

    public static int Q() {
        return R;
    }

    @Override
    public void x(ZeusPacketBuffer zeusPacketBuffer) {
        this.userId = zeusPacketBuffer.readLong();
        this.accepted = zeusPacketBuffer.readBoolean();
    }

    static {
        if (FriendRequestUpdatePacket.Q() == 0) {
            FriendRequestUpdatePacket.e(124);
        }
    }
}
