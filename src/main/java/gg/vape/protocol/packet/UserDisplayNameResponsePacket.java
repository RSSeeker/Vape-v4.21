package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.UserDisplayNamePacket;
import gg.vape.protocol.packet.UserDisplayNameStatus;
import gg.vape.protocol.packet.ZeusTrackedPacket;
import org.jetbrains.annotations.Nullable;

public class UserDisplayNameResponsePacket
extends ZeusTrackedPacket<UserDisplayNamePacket> {
    private long userIdOrCooldownEnd;
    private String displayName;
    private static String y;
    private UserDisplayNameStatus status;


    @Override
    public void T(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeEnum(this.status);
        if (this.status == UserDisplayNameStatus.SUCCESSFUL) {
            zeusPacketBuffer.writeString(this.displayName);
            zeusPacketBuffer.writeLong(this.userIdOrCooldownEnd);
        } else if (this.status == UserDisplayNameStatus.COOLDOWN) {
            zeusPacketBuffer.writeLong(this.userIdOrCooldownEnd);
        }
    }

    static {
        if (UserDisplayNameResponsePacket.q$src$Ljava_lang_String_$12vxeoi() == null) {
            UserDisplayNameResponsePacket.x("YnPS4b");
        }
    }

    public UserDisplayNameResponsePacket() {
    }

    public long getUserIdOrCooldownEnd() {
        return this.userIdOrCooldownEnd;
    }

    public UserDisplayNameResponsePacket(@Nullable UserDisplayNamePacket userDisplayNamePacket, UserDisplayNameStatus status) {
        super(userDisplayNamePacket);
        this.status = status;
    }

    public UserDisplayNameStatus getStatus() {
        return this.status;
    }

    public static void x(String string) {
        y = string;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public static String q$src$Ljava_lang_String_$12vxeoi() {
        return y;
    }

    @Override
    public void x(ZeusPacketBuffer zeusPacketBuffer) {
        this.status = zeusPacketBuffer.readEnum(UserDisplayNameStatus.class);
        if (this.status == UserDisplayNameStatus.SUCCESSFUL) {
            this.displayName = zeusPacketBuffer.readString(16);
            this.userIdOrCooldownEnd = zeusPacketBuffer.readLong();
        } else if (this.status == UserDisplayNameStatus.COOLDOWN) {
            this.userIdOrCooldownEnd = zeusPacketBuffer.readLong();
        }
    }

    public UserDisplayNameResponsePacket(@Nullable UserDisplayNamePacket userDisplayNamePacket, long cooldownEnd) {
        this(userDisplayNamePacket, UserDisplayNameStatus.COOLDOWN);
        this.userIdOrCooldownEnd = cooldownEnd;
    }

    public UserDisplayNameResponsePacket(@Nullable UserDisplayNamePacket userDisplayNamePacket, String displayName, long userId) {
        this(userDisplayNamePacket, UserDisplayNameStatus.SUCCESSFUL);
        this.displayName = displayName;
        this.userIdOrCooldownEnd = userId;
    }
}
