package gg.vape.friend.ui;

import gg.vape.Vape;
import gg.vape.friend.OnlineFriend;
import gg.vape.friend.OnlineStatus;
import gg.vape.friend.PartyState;
import gg.vape.friend.ui.OnlineFriendUiHelper;
import gg.vape.friend.ui.PartyFriendRowComponent;
import gg.vape.friend.ui.PartyInviteActionLabelComponent;
import gg.vape.protocol.ZeusConnectionManager;
import gg.vape.protocol.packet.GroupCreateResponsePacket;
import gg.vape.protocol.packet.GroupCreateStatus;
import gg.vape.protocol.packet.GroupInviteResponsePacket;
import gg.vape.ui.click.component.gui.AnimatedCenteredTextLabelComponent;
import gg.vape.ui.notification.NotificationMessage;
import gg.vape.ui.notification.NotificationType;

public class PartyInviteFriendRowComponent
extends PartyFriendRowComponent {
    private boolean invitePending;
    private final AnimatedCenteredTextLabelComponent inviteAction;

    @Override
    protected void renderRoleIndicator() {
    }

    @Override
    public void c() {
        this.inviteAction.setVisible(this.w$src$Z$e457mb());
        super.c();
    }

    public PartyInviteFriendRowComponent(OnlineFriend onlineFriend) {
        super(onlineFriend, false, false);
        this.inviteAction = new PartyInviteActionLabelComponent(this, "INVITE", PartyInviteFriendRowComponent.J.l);
        this.invitePending = false;
        this.inviteAction.addClickListener(this::sendPartyInvite);
        this.actionPanel.removeMarkedChildren();
        this.actionPanel.h(this.usernameLabel, new Object[0]);
        this.inviteAction.setFontScale((double)0.65f);
        this.inviteAction.setBorderAlpha(1.0f);
        this.actionPanel.h(this.inviteAction, new Object[0]);
    }

    private void handleNewPartyInviteFailure() {
        this.invitePending = false;
    }


    private void sendPartyInvite() {
        if (this.invitePending) {
            return;
        }
        if (this.friend.getStatus() == OnlineStatus.OFFLINE) {
            return;
        }
        this.invitePending = true;
        PartyState partyState = Vape.INSTANCE.getOnlineManager().getPartyManager().getCurrentParty();
        if (partyState != null) {
            if (!partyState.canInvite()) {
                this.invitePending = false;
                return;
            }
            if (partyState.getMembers().contains(this.friend)) {
                this.invitePending = false;
                OnlineFriendUiHelper.showNotification(new NotificationMessage(NotificationType.ERROR, "Cannot send party invite to a party member"));
                return;
            }
            if (partyState.getInvitedUsers().contains(this.friend)) {
                this.invitePending = false;
                OnlineFriendUiHelper.showNotification(new NotificationMessage(NotificationType.ERROR, "Cannot send party invite to an already invited person"));
                return;
            }
            ZeusConnectionManager.T().u().J(this.friend.getUser(), this::handleExistingPartyInviteResponse, this::handleExistingPartyInviteFailure);
            return;
        }
        ZeusConnectionManager.T().u().w(this::handlePartyCreationResponse, this::handlePartyCreationFailure);
    }

    private void handlePartyCreationResponse(GroupCreateResponsePacket response) {
        if (response.getStatus() == GroupCreateStatus.SUCCESS) {
            this.invitePending = true;
            ZeusConnectionManager.T().u().J(this.friend.getUser(), this::handleNewPartyInviteResponse, this::handleNewPartyInviteFailure);
        }
    }

    private void showInviteResult(OnlineFriend friend, GroupInviteResponsePacket response) {
        switch (response.getStatus()) {
            case SUCCESS: {
                OnlineFriendUiHelper.showNotification(new NotificationMessage(NotificationType.SUCCESS, "Invited " + friend.getDisplayName() + " to party"));
                break;
            }
            case TOO_MANY_INVITES: {
                OnlineFriendUiHelper.showNotification(new NotificationMessage(NotificationType.ERROR, "Sent too many invites"));
                break;
            }
            case NOT_ONLINE: 
            case ALREADY_INVITED: 
            case FAILED: {
                OnlineFriendUiHelper.showNotification(new NotificationMessage(NotificationType.ERROR, "Error inviting " + friend.getDisplayName() + " to party"));
            }
        }
    }

    private void handleExistingPartyInviteResponse(GroupInviteResponsePacket response) {
        this.showInviteResult(this.friend, response);
    }

    private void handlePartyCreationFailure() {
        this.invitePending = false;
    }

    private void handleExistingPartyInviteFailure() {
        this.invitePending = false;
    }

    private void handleNewPartyInviteResponse(GroupInviteResponsePacket response) {
        this.showInviteResult(this.friend, response);
    }
}

