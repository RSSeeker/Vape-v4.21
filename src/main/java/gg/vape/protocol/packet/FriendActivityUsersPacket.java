package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.FriendActivityUsersAction;
import gg.vape.protocol.packet.ZeusSerializablePacket;
import gg.vape.ui.click.component.GuiComponent;

public class FriendActivityUsersPacket
implements ZeusSerializablePacket {
    private long[] e;
    private FriendActivityUsersAction B;
    private static GuiComponent[] j;

    public FriendActivityUsersAction G() {
        return this.B;
    }

    public static FriendActivityUsersPacket h(long[] lArray) {
        return new FriendActivityUsersPacket(FriendActivityUsersAction.CHANGED_WORLD, lArray);
    }


    public static GuiComponent[] L() {
        return j;
    }

    public FriendActivityUsersPacket() {
    }

    public static void x(GuiComponent[] guiComponentArray) {
        j = guiComponentArray;
    }

    @Override
    public void o(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeEnum(this.B);
        zeusPacketBuffer.writeVarInt(this.e.length);
        for (long l : this.e) {
            zeusPacketBuffer.writeLong(l);
        }
    }

    private FriendActivityUsersPacket(FriendActivityUsersAction friendActivityUsersAction, long[] lArray) {
        this.B = friendActivityUsersAction;
        this.e = lArray;
    }

    @Override
    public void S(ZeusPacketBuffer zeusPacketBuffer) {
        this.B = zeusPacketBuffer.readEnum(FriendActivityUsersAction.class);
        int n = zeusPacketBuffer.readVarInt();
        this.e = new long[n];
        for (int i = 0; i < n; ++i) {
            this.e[i] = zeusPacketBuffer.readLong();
        }
    }

    public static FriendActivityUsersPacket E(long[] lArray) {
        return new FriendActivityUsersPacket(FriendActivityUsersAction.ADD, lArray);
    }

    public long[] Y() {
        return this.e;
    }

    static {
        if (FriendActivityUsersPacket.L() != null) {
            FriendActivityUsersPacket.x(new GuiComponent[3]);
        }
    }
}

