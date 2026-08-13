package gg.vape.friend.ui;

import gg.vape.friend.OnlineFriend;
import gg.vape.friend.ui.PartyMemberRow;
import gg.vape.friend.ui.PartyMemberStatusComponent;

public class ChatMessagePartyMemberRow
extends PartyMemberRow {
    public ChatMessagePartyMemberRow(OnlineFriend onlineFriend, PartyMemberStatusComponent partyMemberStatusComponent) {
        super(onlineFriend, partyMemberStatusComponent, false);
        this.getAvatar().setVisible(false);
    }
}
