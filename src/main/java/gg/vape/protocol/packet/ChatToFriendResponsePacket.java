package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ChatToFriendPacket;
import gg.vape.protocol.packet.ChatToFriendStatus;
import gg.vape.protocol.packet.ZeusTrackedPacket;
import gg.vape.ui.click.component.GuiComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChatToFriendResponsePacket
extends ZeusTrackedPacket<ChatToFriendPacket> {
    private ChatToFriendStatus n;
    @Nullable
    private String N;
    private static GuiComponent[] o;
    private long l;

    public ChatToFriendResponsePacket(@Nullable ChatToFriendPacket chatToFriendPacket, @NotNull String string) {
        this(chatToFriendPacket, ChatToFriendStatus.SUCCESS);
        this.N = string;
        this.l = System.currentTimeMillis();
    }

    public ChatToFriendResponsePacket(@Nullable ChatToFriendPacket chatToFriendPacket, ChatToFriendStatus chatToFriendStatus) {
        super(chatToFriendPacket);
        this.n = chatToFriendStatus;
    }

    public static void T(GuiComponent[] guiComponentArray) {
        o = guiComponentArray;
    }

    @Override
    public void T(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeEnum(this.n);
        zeusPacketBuffer.writeString(this.N);
        zeusPacketBuffer.writeLong(this.l);
    }

    public static GuiComponent[] A() {
        return o;
    }

    public ChatToFriendStatus Z() {
        return this.n;
    }

    @Nullable
    public String X() {
        return this.N;
    }

    public ChatToFriendResponsePacket() {
    }

    public long y() {
        return this.l;
    }

    @Override
    public void x(ZeusPacketBuffer zeusPacketBuffer) {
        this.n = zeusPacketBuffer.readEnum(ChatToFriendStatus.class);
        this.N = zeusPacketBuffer.readString(255);
        this.l = zeusPacketBuffer.readLong();
    }

    static {
        if (ChatToFriendResponsePacket.A() != null) {
            ChatToFriendResponsePacket.T(new GuiComponent[1]);
        }
    }
}

