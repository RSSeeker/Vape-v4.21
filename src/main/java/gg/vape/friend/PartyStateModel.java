package gg.vape.friend;

import gg.vape.friend.GroupUserModel;
import gg.vape.protocol.ZeusPacketBuffer;
import java.util.ArrayList;
import java.util.List;

public class PartyStateModel {
    private List<GroupUserModel> members = new ArrayList<GroupUserModel>();
    private GroupUserModel leader;
    private List<GroupUserModel> invitedUsers = new ArrayList<GroupUserModel>();

    public GroupUserModel getLeader() {
        return this.leader;
    }

    public void writeTo(ZeusPacketBuffer buffer) {
        this.leader.writeTo(buffer);
        buffer.writeVarInt(this.members.size());
        for (GroupUserModel member : this.members) {
            member.writeTo(buffer);
        }
        buffer.writeVarInt(this.invitedUsers.size());
        for (GroupUserModel invitedUser : this.invitedUsers) {
            invitedUser.writeTo(buffer);
        }
    }

    public PartyStateModel(ZeusPacketBuffer buffer) {
        int index;
        this.leader = new GroupUserModel(buffer);
        int memberCount = buffer.readVarInt();
        for (index = 0; index < memberCount; ++index) {
            this.members.add(new GroupUserModel(buffer));
        }
        int invitedUserCount = buffer.readVarInt();
        for (int invitedIndex = 0; invitedIndex < invitedUserCount; ++invitedIndex) {
            this.invitedUsers.add(new GroupUserModel(buffer));
        }
    }


    public List<GroupUserModel> getMembers() {
        return this.members;
    }

    public PartyStateModel(GroupUserModel leader, List<GroupUserModel> members, List<GroupUserModel> invitedUsers) {
        this.leader = leader;
        this.members = members;
        this.invitedUsers = invitedUsers;
    }

    public List<GroupUserModel> getInvitedUsers() {
        return this.invitedUsers;
    }
}

