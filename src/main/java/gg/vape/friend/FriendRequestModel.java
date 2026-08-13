package gg.vape.friend;

import gg.vape.friend.UserModel;
import gg.vape.protocol.ZeusPacketBuffer;

public class FriendRequestModel {
    private final UserModel requester;
    private final UserModel recipient;
    private final long id;

    public int hashCode() {
        return (int)(this.id ^ this.id >>> 32);
    }

    public void writeTo(ZeusPacketBuffer buffer) {
        buffer.writeLong(this.id);
        this.requester.writeTo(buffer);
        this.recipient.writeTo(buffer);
    }

    public FriendRequestModel(ZeusPacketBuffer buffer) {
        this.id = buffer.readLong();
        this.requester = new UserModel(buffer);
        this.recipient = new UserModel(buffer);
    }

    public UserModel getRecipient() {
        return this.recipient;
    }

    public UserModel getRequester() {
        return this.requester;
    }

    public FriendRequestModel(long id, UserModel requester, UserModel recipient) {
        this.id = id;
        this.requester = requester;
        this.recipient = recipient;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }
        FriendRequestModel friendRequestModel = (FriendRequestModel)other;
        return this.id == friendRequestModel.id;
    }


    public long getId() {
        return this.id;
    }
}

