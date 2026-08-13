package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.UserDisplayNameResponsePacket;
import gg.vape.protocol.packet.ZeusTrackedPacket;

public class UserDisplayNamePacket
extends ZeusTrackedPacket<UserDisplayNameResponsePacket> {
    private String displayName;
    private static boolean f;

    public static void Y(boolean bl) {
        f = bl;
    }

    public UserDisplayNamePacket() {
    }

    public String getDisplayName() {
        return this.displayName;
    }


    public static boolean r() {
        boolean bl = UserDisplayNamePacket.I();
        return !bl;
    }

    @Override
    public void T(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeString(this.displayName);
    }

    public static boolean I() {
        return f;
    }

    public UserDisplayNamePacket(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public void x(ZeusPacketBuffer zeusPacketBuffer) {
        this.displayName = zeusPacketBuffer.readString(16);
    }

    static {
        if (!UserDisplayNamePacket.r()) {
            UserDisplayNamePacket.Y(true);
        }
    }
}
