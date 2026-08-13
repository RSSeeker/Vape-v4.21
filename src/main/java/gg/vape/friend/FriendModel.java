package gg.vape.friend;

import gg.vape.friend.UserModel;
import gg.vape.protocol.PresenceState;
import gg.vape.protocol.ZeusPacketBuffer;
import java.util.UUID;
import org.jetbrains.annotations.Nullable;

public class FriendModel {
    @Nullable
    private final String minecraftServer;
    private final UserModel user;
    private final PresenceState presenceState;
    private final String minecraftUsername;
    private final UUID minecraftUuid;
    private final boolean visible;

    public String getDisplayName() {
        return this.user.getDisplayName();
    }

    public UserModel getUser() {
        return this.user;
    }


    public FriendModel(ZeusPacketBuffer buffer) {
        this.user = new UserModel(buffer);
        this.minecraftUuid = buffer.readUuid();
        this.minecraftUsername = buffer.readString(16);
        this.visible = buffer.readBoolean();
        this.presenceState = buffer.readEnum(PresenceState.class);
        this.minecraftServer = buffer.readBoolean() ? buffer.readString(128) : null;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public UUID getMinecraftUuid() {
        return this.minecraftUuid;
    }

    public FriendModel(UserModel userModel) {
        this(userModel, PresenceState.OFFLINE, UUID.randomUUID(), "", false, null);
    }

    @Nullable
    public String getMinecraftServer() {
        return this.minecraftServer;
    }

    public long getUserId() {
        return this.user.getId();
    }

    public PresenceState getPresenceState() {
        return this.presenceState;
    }

    public String toString() {
        return "FriendModel{userModel=" + this.user + ", minecraftUuid=" + this.minecraftUuid + ", minecraftUsername='" + this.minecraftUsername + '\'' + ", state=" + (Object)((Object)this.presenceState) + ", minecraftServer='" + this.minecraftServer + '\'' + '}';
    }

    public void writeTo(ZeusPacketBuffer buffer) {
        this.user.writeTo(buffer);
        buffer.writeUuid(this.minecraftUuid);
        buffer.writeString(this.minecraftUsername);
        buffer.writeBoolean(this.visible);
        buffer.writeEnum(this.presenceState);
        buffer.writeBoolean(this.minecraftServer != null);
        if (this.minecraftServer != null) {
            buffer.writeString(this.minecraftServer);
        }
    }

    public String getMinecraftUsername() {
        return this.minecraftUsername;
    }

    public FriendModel(UserModel user, PresenceState presenceState, UUID minecraftUuid, String minecraftUsername, boolean visible, @Nullable String minecraftServer) {
        this.user = user;
        this.minecraftUuid = minecraftUuid;
        this.minecraftUsername = minecraftUsername;
        this.visible = visible;
        this.presenceState = presenceState;
        this.minecraftServer = minecraftServer;
    }
}

