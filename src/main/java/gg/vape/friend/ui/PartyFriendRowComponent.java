package gg.vape.friend.ui;

import gg.vape.Vape;
import gg.vape.friend.FriendRequest;
import gg.vape.friend.FriendRequestService;
import gg.vape.friend.IncomingFriendRequest;
import gg.vape.friend.OnlineFriend;
import gg.vape.friend.OnlineFriendColorUtil;
import gg.vape.friend.PartyState;
import gg.vape.friend.ui.OnlineFriendAvatarComponent;
import gg.vape.friend.ui.OnlineFriendUiHelper;
import gg.vape.friend.ui.PartyFriendActionLabelComponent;
import gg.vape.friend.ui.PartyFriendCompactActionLabelComponent;
import gg.vape.friend.ui.PartyFriendFixedTextLabelComponent;
import gg.vape.friend.ui.PartyFriendNameLabelComponent;
import gg.vape.friend.ui.PartyFriendSecondaryActionLabelComponent;
import gg.vape.friend.ui.PartyFriendTertiaryActionLabelComponent;
import gg.vape.protocol.ZeusConnectionManager;
import gg.vape.protocol.packet.ClientGroupLeaderKickResponsePacket;
import gg.vape.protocol.packet.ClientGroupLeaderKickStatus;
import gg.vape.protocol.packet.ClientGroupLeaderPromoteResponsePacket;
import gg.vape.protocol.packet.ClientGroupLeaderPromoteStatus;
import gg.vape.protocol.packet.GroupUninviteResponsePacket;
import gg.vape.protocol.packet.GroupUninviteStatus;
import gg.vape.ui.click.component.FlowLayoutComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.component.TextLabelComponent;
import gg.vape.ui.click.component.TruncatedTextComponent;
import gg.vape.ui.click.component.gui.AnimatedCenteredTextLabelComponent;
import gg.vape.ui.notification.NotificationMessage;
import gg.vape.ui.notification.NotificationType;
import gg.vape.utils.render.GuiRenderPrimitives;

public class PartyFriendRowComponent
extends PanelComponent {
    private boolean actionPending;
    protected final FlowLayoutComponent actionPanel;
    protected TextLabelComponent usernameLabel;
    private final AnimatedCenteredTextLabelComponent kickAction;
    protected final AnimatedCenteredTextLabelComponent revokeAction;
    private boolean reservedState;
    protected final OnlineFriend friend;
    private final AnimatedCenteredTextLabelComponent promoteAction;
    private final AnimatedCenteredTextLabelComponent addFriendAction;
    private final FlowLayoutComponent contentPanel = new FlowLayoutComponent(70.0);
    private final boolean limitedActions;
    private final boolean invitedUser;
    private static String obfuscationName;
    private final TruncatedTextComponent nameLabel;

    private void handlePromoteResponse(PartyState partyState, ClientGroupLeaderPromoteResponsePacket response) {
        if (response.M() == ClientGroupLeaderPromoteStatus.SUCCESS) {
            partyState.setLeader(this.friend);
        }
    }


    @Override
    public void H() {
        super.H();
    }

    public PartyFriendRowComponent(OnlineFriend friend, boolean invitedUser, boolean limitedActions) {
        super(99.0, 20.0);
        this.promoteAction = new PartyFriendCompactActionLabelComponent(this, "PROMOTE", PartyFriendRowComponent.J.l);
        this.kickAction = new PartyFriendSecondaryActionLabelComponent(this, "KICK", PartyFriendRowComponent.J.l);
        this.revokeAction = new PartyFriendActionLabelComponent(this, "REVOKE", PartyFriendRowComponent.J.l);
        this.addFriendAction = new PartyFriendTertiaryActionLabelComponent(this, "ADD", PartyFriendRowComponent.J.l);
        this.actionPanel = new FlowLayoutComponent(70.0);
        this.actionPending = false;
        this.friend = friend;
        this.invitedUser = invitedUser;
        this.limitedActions = limitedActions;
        this.nameLabel = new PartyFriendNameLabelComponent(this, friend.getDisplayName(), "...", 72.0, 0.75, PartyFriendRowComponent.J.A, false);
        this.usernameLabel = new PartyFriendFixedTextLabelComponent(this, friend.getMinecraftUsername(), 0.55, 0.75, 0.1, 72.0, false, false, PartyFriendRowComponent.J.h);
        this.contentPanel.h(this.nameLabel, new Object[0]);
        this.actionPanel.h(this.usernameLabel, new Object[0]);
        if (invitedUser) {
            this.actionPanel.addChildren(this.revokeAction);
        } else {
            this.actionPanel.addChildren(this.addFriendAction, this.promoteAction, new SpacerComponent(2.0, 1.0), this.kickAction);
        }
        this.contentPanel.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        this.contentPanel.addChildren(new SpacerComponent(0.0, 9.0));
        this.contentPanel.addChildren(this.actionPanel);
        this.addChildren(new SpacerComponent(4.0, 1.0), new OnlineFriendAvatarComponent(friend, 8.0, 8.0), new SpacerComponent(4.0, 1.0), this.contentPanel);
        this.promoteAction.addClickListener(this::promoteToLeader);
        this.kickAction.addClickListener(this::kickFromParty);
        this.revokeAction.addClickListener(this::revokeInvite);
        this.addFriendAction.addClickListener(this::addAsFriend);
    }

    public static String getName() {
        return obfuscationName;
    }

    private void handleRevokeResponse(PartyState partyState, GroupUninviteResponsePacket response) {
        if (response.getStatus() == GroupUninviteStatus.SUCCESS) {
            partyState.removeMember(this.friend);
        }
    }

    protected void renderRoleIndicator() {
        if (!this.invitedUser) {
            GuiRenderPrimitives.V(this.usernameLabel.G$src$D$1b2f02a(), this.usernameLabel.n() + 2.0, 2.0, 1.0, OnlineFriendColorUtil.getGroupRoleColor(this.friend.getGroupRole()));
        }
    }

    static {
        PartyFriendRowComponent.setObfuscationName("yJCYxb");
    }

    private void promoteToLeader() {
        if (this.actionPending) {
            return;
        }
        PartyState partyState = Vape.INSTANCE.getOnlineManager().getPartyManager().getCurrentParty();
        if (partyState == null) {
            return;
        }
        this.actionPending = true;
        ZeusConnectionManager.T().u().s(this.friend.getUser(), response -> this.handlePromoteResponse(partyState, response), this::handlePromoteFailure);
    }

    @Override
    public void c() {
        boolean alreadyFriendOrRequested = Vape.INSTANCE.getOnlineFriendManager().getFriends().contains(this.friend) || Vape.INSTANCE.getOnlineManager().getFriendRequestManager().hasOutgoingRequest(this.friend);
        boolean localUser = Vape.INSTANCE.getOnlineManager().getLocalFriend().equals(this.friend);
        boolean canAddFriend = !alreadyFriendOrRequested && !localUser;
        if (this.w$src$Z$e457mb()) {
            if (this.limitedActions) {
                this.addFriendAction.setVisible(canAddFriend);
                this.promoteAction.setVisible(false);
                this.kickAction.setVisible(false);
                this.revokeAction.setVisible(false);
            } else {
                this.addFriendAction.setVisible(canAddFriend);
                this.promoteAction.setVisible(true);
                this.kickAction.setVisible(true);
                this.revokeAction.setVisible(true);
            }
        } else {
            this.addFriendAction.setVisible(false);
            this.promoteAction.setVisible(false);
            this.kickAction.setVisible(false);
            this.revokeAction.setVisible(false);
        }
        this.usernameLabel.setVisible(!this.w$src$Z$e457mb() || this.limitedActions && !canAddFriend);
        this.l$src$V$1mibm4x();
        this.nameLabel.setText(this.friend.getDisplayName());
        if (this.invitedUser) {
            this.usernameLabel.setText(this.friend.getMinecraftUsername());
        } else {
            this.usernameLabel.setText("   " + this.friend.getMinecraftUsername());
        }
        super.c();
        this.renderRoleIndicator();
        this.promoteAction.setFontScale((double)0.65f);
        this.kickAction.setFontScale((double)0.65f);
        this.revokeAction.setFontScale((double)0.65f);
        this.addFriendAction.setFontScale((double)0.65f);
        this.promoteAction.setBorderAlpha(1.0f);
        this.kickAction.setBorderAlpha(1.0f);
        this.revokeAction.setBorderAlpha(1.0f);
        this.addFriendAction.setBorderAlpha(1.0f);
        this.contentPanel.setShowDisabledOverlay(false);
        this.setShowDisabledOverlay(false);
    }

    private void handlePromoteFailure() {
        this.actionPending = false;
    }

    private void handleKickFailure() {
        this.actionPending = false;
    }

    private void handleRevokeFailure() {
        this.actionPending = false;
    }

    public OnlineFriend getFriend() {
        return this.friend;
    }

    private void kickFromParty() {
        if (this.actionPending) {
            return;
        }
        PartyState partyState = Vape.INSTANCE.getOnlineManager().getPartyManager().getCurrentParty();
        if (partyState == null) {
            return;
        }
        this.actionPending = true;
        ZeusConnectionManager.T().u().c(this.friend.getUser(), response -> this.handleKickResponse(partyState, response), this::handleKickFailure);
    }

    private void handleKickResponse(PartyState partyState, ClientGroupLeaderKickResponsePacket response) {
        if (response.P() == ClientGroupLeaderKickStatus.SUCCESS) {
            partyState.removeMember(this.friend);
        }
    }

    private void addAsFriend() {
        if (this.actionPending) {
            return;
        }
        PartyState partyState = Vape.INSTANCE.getOnlineManager().getPartyManager().getCurrentParty();
        if (partyState == null) {
            return;
        }
        for (FriendRequest friendRequest : Vape.INSTANCE.getOnlineManager().getFriendRequestManager().getIncomingRequests()) {
            if (!friendRequest.getFriend().getDisplayName().equals(this.friend.getDisplayName())) continue;
            OnlineFriendUiHelper.showNotification(new NotificationMessage(NotificationType.SUCCESS, "Added " + this.friend.getDisplayName() + " as a friend"));
            Vape.INSTANCE.getOnlineManager().getFriendRequestManager().acceptIncomingRequest((IncomingFriendRequest)friendRequest);
            return;
        }
        this.actionPending = true;
        FriendRequestService.sendFriendRequest(this.friend.getDisplayName());
        this.actionPending = false;
    }

    public static void setObfuscationName(String name) {
        obfuscationName = name;
    }

    private void revokeInvite() {
        if (this.actionPending) {
            return;
        }
        PartyState partyState = Vape.INSTANCE.getOnlineManager().getPartyManager().getCurrentParty();
        if (partyState == null) {
            return;
        }
        this.actionPending = true;
        ZeusConnectionManager.T().u().V(this.friend.getUser(), response -> this.handleRevokeResponse(partyState, response), this::handleRevokeFailure);
    }
}

