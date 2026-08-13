package gg.vape.friend;

import gg.vape.friend.UserModel;
import gg.vape.protocol.ZeusPacketBuffer;
import java.util.UUID;
import org.jetbrains.annotations.Nullable;

public class GroupUserModel {
    @Nullable
    private final String minecraftServer;
    private final UUID minecraftUuid;
    private final UserModel user;
    private final String minecraftUsername;
    private final int groupRole;

    public long getUserId() {
        return this.user.getId();
    }

    public void writeTo(ZeusPacketBuffer buffer) {
        this.user.writeTo(buffer);
        buffer.writeUuid(this.minecraftUuid);
        buffer.writeString(this.minecraftUsername);
        ZeusPacketBuffer output = buffer;
        boolean hasMinecraftServer = this.minecraftServer != null;
        output.writeBoolean(hasMinecraftServer);
        if (this.minecraftServer != null) {
            buffer.writeString(this.minecraftServer);
        }
        buffer.writeInt(this.groupRole);
    }

    public String getMinecraftUsername() {
        return this.minecraftUsername;
    }


    public GroupUserModel(ZeusPacketBuffer buffer) {
        this.user = new UserModel(buffer);
        this.minecraftUuid = buffer.readUuid();
        this.minecraftUsername = buffer.readString(16);
        this.minecraftServer = buffer.readBoolean() ? buffer.readString(128) : null;
        this.groupRole = buffer.readInt();
    }

    @Nullable
    public String getMinecraftServer() {
        return this.minecraftServer;
    }

    public GroupUserModel(UserModel user, UUID minecraftUuid, String minecraftUsername, @Nullable String minecraftServer, int groupRole) {
        this.user = user;
        this.minecraftUuid = minecraftUuid;
        this.minecraftUsername = minecraftUsername;
        this.minecraftServer = minecraftServer;
        this.groupRole = groupRole;
    }

    public String getDisplayName() {
        return this.user.getDisplayName();
    }

    public UUID getMinecraftUuid() {
        return this.minecraftUuid;
    }

    public UserModel getUser() {
        return this.user;
    }

    public int getGroupRole() {
        return this.groupRole;
    }
}

