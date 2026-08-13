package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ClientFriendActivityUsersAction;
import gg.vape.protocol.packet.ZeusSerializablePacket;

public class ClientFriendActivityUsersPacket
implements ZeusSerializablePacket {
    private static boolean i;
    private ClientFriendActivityUsersAction h;
    private long[] a;

    public long[] B() {
        return this.a;
    }

    public static boolean F() {
        boolean bl = ClientFriendActivityUsersPacket.c();
        return !bl;
    }

    private ClientFriendActivityUsersPacket(ClientFriendActivityUsersAction clientFriendActivityUsersAction, long[] lArray) {
        this(clientFriendActivityUsersAction);
        this.a = lArray;
    }

    @Override
    public void o(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeEnum(this.h);
        if (this.h == ClientFriendActivityUsersAction.ADD) {
            zeusPacketBuffer.writeVarInt(this.a.length);
            for (long l : this.a) {
                zeusPacketBuffer.writeLong(l);
            }
        }
    }

    public static void Q(boolean bl) {
        i = bl;
    }

    public ClientFriendActivityUsersAction y() {
        return this.h;
    }


    public static ClientFriendActivityUsersPacket X(long[] lArray) {
        return new ClientFriendActivityUsersPacket(ClientFriendActivityUsersAction.ADD, lArray);
    }

    @Override
    public void S(ZeusPacketBuffer zeusPacketBuffer) {
        this.h = zeusPacketBuffer.readEnum(ClientFriendActivityUsersAction.class);
        if (this.h == ClientFriendActivityUsersAction.ADD) {
            int n = zeusPacketBuffer.readVarInt();
            this.a = new long[n];
            for (int i = 0; i < n; ++i) {
                this.a[i] = zeusPacketBuffer.readLong();
            }
        }
    }

    private ClientFriendActivityUsersPacket(ClientFriendActivityUsersAction clientFriendActivityUsersAction) {
        this.h = clientFriendActivityUsersAction;
    }

    public static ClientFriendActivityUsersPacket u() {
        return new ClientFriendActivityUsersPacket(ClientFriendActivityUsersAction.CHANGED_WORLD);
    }

    public ClientFriendActivityUsersPacket() {
    }

    public static boolean c() {
        return i;
    }

    static {
        if (!ClientFriendActivityUsersPacket.F()) {
            ClientFriendActivityUsersPacket.Q(true);
        }
    }
}

