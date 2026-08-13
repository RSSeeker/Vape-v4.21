package gg.vape.friend.ui;

import gg.vape.friend.ui.OnlineFriendUiHelper;
import gg.vape.friend.ui.PartyInviteCountBadge;
import gg.vape.ui.click.component.GuiClickListener;

class PartyInviteCountBadgeToggleInvitesClickHandler
implements GuiClickListener {
    private final PartyInviteCountBadge badge;

    @Override
    public void onPrimaryClick() {
        OnlineFriendUiHelper.findOnlineFriendsFrame(this.badge).getPartyInvitesPanel().toggleExpanded();
    }

    PartyInviteCountBadgeToggleInvitesClickHandler(PartyInviteCountBadge partyInviteCountBadge) {
        this.badge = partyInviteCountBadge;
    }
}
