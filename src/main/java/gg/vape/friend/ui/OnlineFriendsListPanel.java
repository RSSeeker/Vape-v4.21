package gg.vape.friend.ui;

import gg.vape.friend.ui.CurrentPartyPanel;
import gg.vape.friend.ui.OnlineFriendEntriesPanel;
import gg.vape.friend.ui.PartyInviteCountBadge;
import gg.vape.friend.ui.PartyInvitesPanel;
import gg.vape.ui.click.component.FlowLayoutComponent;
import gg.vape.ui.click.component.PanelComponent;

public class OnlineFriendsListPanel
extends PanelComponent {
    private static final String WRAP_LAYOUT;
    private final PartyInviteCountBadge inviteCountBadge;
    private final PartyInvitesPanel invitesPanel;
    private final FlowLayoutComponent partyHeader;
    private final CurrentPartyPanel currentPartyPanel;
    private final OnlineFriendEntriesPanel friendEntriesPanel = new OnlineFriendEntriesPanel();
    private static int[] obfuscationValues;

    static {
        OnlineFriendsListPanel.setObfuscationValues(new int[2]);
        WRAP_LAYOUT = "wrap";
    }

    public PartyInvitesPanel getInvitesPanel() {
        return this.invitesPanel;
    }

    @Override
    public void c() {
        super.c();
        double entriesHeight = this.L() - this.partyHeader.L();
        this.friendEntriesPanel.t(entriesHeight);
    }

    public OnlineFriendEntriesPanel getFriendEntriesPanel() {
        return this.friendEntriesPanel;
    }

    public static void setObfuscationValues(int[] values) {
        obfuscationValues = values;
    }

    public OnlineFriendsListPanel() {
        super(104.0, 130.0);
        this.partyHeader = new FlowLayoutComponent(103.0);
        this.currentPartyPanel = new CurrentPartyPanel();
        this.inviteCountBadge = new PartyInviteCountBadge();
        this.invitesPanel = new PartyInvitesPanel();
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M(WRAP_LAYOUT);
        this.partyHeader.h(this.currentPartyPanel, new Object[0]);
        this.partyHeader.h(this.inviteCountBadge, new Object[0]);
        this.partyHeader.h(this.invitesPanel, new Object[0]);
        this.friendEntriesPanel.t(126.0);
        this.addChildren(this.partyHeader, this.friendEntriesPanel);
    }

    public CurrentPartyPanel getCurrentPartyPanel() {
        return this.currentPartyPanel;
    }

    public static int[] getObfuscationValues() {
        return obfuscationValues;
    }
}
