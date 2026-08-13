package gg.vape.protocol.packet;

import gg.vape.friend.GroupUserModel;
import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.PartyMemberActionType;
import gg.vape.protocol.packet.ZeusSerializablePacket;

public class ServerPartyMemberUpdatePacket
implements ZeusSerializablePacket {
    private static String j;
    private GroupUserModel E;
    private PartyMemberActionType o;

    @Override
    public void o(ZeusPacketBuffer zeusPacketBuffer) {
        this.E.writeTo(zeusPacketBuffer);
        zeusPacketBuffer.writeEnum(this.o);
    }

    public static void x(String string) {
        j = string;
    }

    public GroupUserModel R() {
        return this.E;
    }

    public ServerPartyMemberUpdatePacket(GroupUserModel groupUserModel, PartyMemberActionType partyMemberActionType) {
        this.E = groupUserModel;
        this.o = partyMemberActionType;
    }

    public ServerPartyMemberUpdatePacket() {
    }

    public static String java_lang_String_o() {
        return j;
    }

    @Override
    public void S(ZeusPacketBuffer zeusPacketBuffer) {
        this.E = new GroupUserModel(zeusPacketBuffer);
        this.o = zeusPacketBuffer.readEnum(PartyMemberActionType.class);
    }

    public PartyMemberActionType a_EN_o() {
        return this.o;
    }

    static {
        if (ServerPartyMemberUpdatePacket.java_lang_String_o() == null) {
            ServerPartyMemberUpdatePacket.x("rgtxib");
        }
    }

    public /* synthetic */ PartyMemberActionType o() {
        return this.a_EN_o();
    }
}

