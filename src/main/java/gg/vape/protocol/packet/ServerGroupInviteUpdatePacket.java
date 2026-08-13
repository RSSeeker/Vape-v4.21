package gg.vape.protocol.packet;

import gg.vape.friend.UserModel;
import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.GroupInviteUpdateStatus;
import gg.vape.protocol.packet.ZeusSerializablePacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ServerGroupInviteUpdatePacket
implements ZeusSerializablePacket {
    @NotNull
    private UserModel x;
    @Nullable
    private UserModel u;
    @NotNull
    private GroupInviteUpdateStatus p;
    static final boolean K;

    public static ServerGroupInviteUpdatePacket L(UserModel oj_12) {
        return new ServerGroupInviteUpdatePacket(null, oj_12, GroupInviteUpdateStatus.DECLINED);
    }

    public static ServerGroupInviteUpdatePacket T(UserModel oj_12, UserModel oj_13) {
        return new ServerGroupInviteUpdatePacket(oj_12, oj_13, GroupInviteUpdateStatus.SENT);
    }

    @Nullable
    public UserModel k() {
        return this.u;
    }

    @NotNull
    public UserModel f() {
        return this.x;
    }

    private ServerGroupInviteUpdatePacket(@Nullable UserModel oj_12, @NotNull UserModel oj_13, @NotNull GroupInviteUpdateStatus p4) {
        this.u = oj_12;
        this.x = oj_13;
        this.p = p4;
    }

    @Override
    public void o(ZeusPacketBuffer gx_12) {
        gx_12.writeEnum(this.p);
        if (this.p == GroupInviteUpdateStatus.SENT) {
            if (!K && this.u == null) {
                throw new AssertionError();
            }
            this.u.writeTo(gx_12);
        }
        this.x.writeTo(gx_12);
    }

    public ServerGroupInviteUpdatePacket() {
    }

    @NotNull
    public GroupInviteUpdateStatus e() {
        return this.p;
    }


    @Override
    public void S(ZeusPacketBuffer gx_12) {
        this.p = gx_12.readEnum(GroupInviteUpdateStatus.class);
        if (this.p == GroupInviteUpdateStatus.SENT) {
            this.u = new UserModel(gx_12);
        }
        this.x = new UserModel(gx_12);
    }

    public static ServerGroupInviteUpdatePacket K(UserModel oj_12) {
        return new ServerGroupInviteUpdatePacket(null, oj_12, GroupInviteUpdateStatus.ACCEPTED);
    }

    static {
        boolean bl;
        K = bl = !ServerGroupInviteUpdatePacket.class.desiredAssertionStatus();
    }
}

