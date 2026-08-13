package gg.vape.friend.ui;

import gg.vape.Vape;
import gg.vape.friend.OnlineFriend;
import gg.vape.friend.OnlineStatus;
import gg.vape.friend.PartyInvite;
import gg.vape.friend.PartyState;
import gg.vape.friend.ui.OnlineFriendCard;
import gg.vape.friend.ui.OnlineFriendListEntryClosePopupClickHandler;
import gg.vape.friend.ui.OnlineFriendListEntryOpenActionsPopupClickHandler;
import gg.vape.friend.ui.OnlineFriendListEntryOpenPopupClickHandler;
import gg.vape.friend.ui.OnlineFriendUiHelper;
import gg.vape.module.none.ClientSettings;
import gg.vape.protocol.ZeusConnectionManager;
import gg.vape.protocol.packet.GroupCreateResponsePacket;
import gg.vape.protocol.packet.GroupCreateStatus;
import gg.vape.protocol.packet.GroupInviteResponsePacket;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.PopupFrame;
import gg.vape.ui.notification.NotificationMessage;
import gg.vape.ui.notification.NotificationType;

public class OnlineFriendListEntry
extends PanelComponent {
    private boolean inviteRequestPending;
    private PopupFrame cardPopup;
    private final OnlineFriend friend;
    private static int obfuscationSeed;
    private final OnlineFriendCard friendCard;
    private boolean popupOpen;

    public static int getReservedZero() {
        int reserved = OnlineFriendListEntry.getObfuscationSeed();
        return 0;
    }

    private void detachCardToPopup() {
        this.removeChild(this.friendCard);
        this.cardPopup = ClientSettings.createPopup(this, this.friendCard, PopupFrame.class);
        this.friendCard.setPopupMode(true);
        this.l$src$V$1mibm4x();
    }

    public static void closePopup(OnlineFriendListEntry entry) {
        entry.closePopup();
    }

    private void handleExistingPartyInviteResponse(OnlineFriend friend, GroupInviteResponsePacket response) {
        this.showInviteResult(friend, response);
    }

    private void handlePartyCreationFailure() {
        this.inviteRequestPending = false;
    }

    private void handleNewPartyInviteFailure() {
        this.inviteRequestPending = false;
    }

    @Override
    public void z(boolean bl) {
        super.z(bl);
    }

    private void addLocalPartyInvite() {
        Vape.INSTANCE.getOnlineManager().getPartyManager().addInvite(new PartyInvite(Vape.INSTANCE.getOnlineManager().getLocalFriend()));
    }


    private void handleExistingPartyInviteFailure() {
        this.inviteRequestPending = false;
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

    private void updatePopupLayout() {
        if (!this.popupOpen) {
            return;
        }
        Frame frame = this.L$src$Lgg_vape_ui_click_frame_Frame_$1djx6sa();
        this.cardPopup.K(this.G$src$D$1b2f02a());
        this.cardPopup.S(frame.n() + frame.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc().L());
        double messageListHeight = frame.L() - frame.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc().L() - 50.0;
        this.friendCard.getDetailsPanel().getChatPanel().getMessageListPanel().setExplicitHeight(messageListHeight);
        this.friendCard.getDetailsPanel().getChatPanel().getMessageListPanel().t(messageListHeight);
        this.friendCard.getDetailsPanel().getChatPanel().getMessageListPanel().l$src$V$1mibm4x();
        this.cardPopup.l$src$V$1mibm4x();
    }

    public static int getObfuscationSeed() {
        return obfuscationSeed;
    }

    private void handlePartyCreationResponse(OnlineFriend friend, GroupCreateResponsePacket response) {
        if (response.getStatus() == GroupCreateStatus.SUCCESS) {
            this.inviteRequestPending = true;
            ZeusConnectionManager.T().u().J(friend.getUser(), inviteResponse -> this.handleNewPartyInviteResponse(friend, inviteResponse), this::handleNewPartyInviteFailure);
        }
    }

    public static void openPopup(OnlineFriendListEntry entry) {
        entry.openPopup();
    }

    static {
        OnlineFriendListEntry.setObfuscationSeed(70);
    }

    private void handleNewPartyInviteResponse(OnlineFriend friend, GroupInviteResponsePacket response) {
        this.showInviteResult(friend, response);
    }

    private void closePopup() {
        ClientSettings.removePopup(this.cardPopup);
        this.addChildren(this.friendCard);
        this.friendCard.setPopupMode(false);
        this.popupOpen = false;
        this.cardPopup.l$src$V$1mibm4x();
        this.l$src$V$1mibm4x();
    }

    public OnlineFriendListEntry(OnlineFriend friend) {
        super(99.0, 24.0);
        this.friend = friend;
        this.friendCard = new OnlineFriendCard(friend);
        this.setShowDisabledOverlay(false);
        this.addChildren(this.friendCard);
        this.friendCard.getDetailsPanel().getActionPanel().getChatButton().addClickListener(new OnlineFriendListEntryOpenActionsPopupClickHandler(this));
        this.friendCard.getChatButton().addClickListener(new OnlineFriendListEntryOpenPopupClickHandler(this));
        this.friendCard.getInviteButton().addClickListener(() -> this.sendPartyInvite(friend));
        this.friendCard.getCloseButton().addClickListener(new OnlineFriendListEntryClosePopupClickHandler(this));
    }

    public OnlineFriend getFriend() {
        return this.friendCard.getFriend();
    }

    private void sendPartyInvite(OnlineFriend friend) {
        if (this.inviteRequestPending) {
            return;
        }
        if (friend.getStatus() == OnlineStatus.OFFLINE) {
            return;
        }
        this.inviteRequestPending = true;
        PartyState partyState = Vape.INSTANCE.getOnlineManager().getPartyManager().getCurrentParty();
        if (partyState != null) {
            if (!partyState.canInvite()) {
                this.inviteRequestPending = false;
                return;
            }
            if (partyState.getMembers().contains(friend)) {
                this.inviteRequestPending = false;
                OnlineFriendUiHelper.showNotification(new NotificationMessage(NotificationType.ERROR, "Cannot send party invite to a party member"));
                return;
            }
            if (partyState.getInvitedUsers().contains(friend)) {
                this.inviteRequestPending = false;
                OnlineFriendUiHelper.showNotification(new NotificationMessage(NotificationType.ERROR, "Cannot send party invite to an already invited person"));
                return;
            }
            ZeusConnectionManager.T().u().J(friend.getUser(), response -> this.handleExistingPartyInviteResponse(friend, response), this::handleExistingPartyInviteFailure);
            return;
        }
        ZeusConnectionManager.T().u().w(response -> this.handlePartyCreationResponse(friend, response), this::handlePartyCreationFailure);
    }

    @Override
    public void c() {
        super.c();
        this.updatePopupLayout();
    }

    private void openPopup() {
        this.detachCardToPopup();
        this.popupOpen = true;
    }

    public static void setObfuscationSeed(int seed) {
        obfuscationSeed = seed;
    }

    public OnlineFriendCard getFriendCard() {
        return this.friendCard;
    }

    @Override
    public double C() {
        return this.popupOpen ? super.C() : this.friendCard.L();
    }
}

