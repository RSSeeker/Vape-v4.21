package gg.vape.friend.ui;

import gg.vape.Vape;
import gg.vape.friend.OnlineFriend;
import gg.vape.friend.PartyState;
import gg.vape.friend.ui.PartyFriendRowComponent;
import gg.vape.ui.click.component.FlowLayoutComponent;
import gg.vape.ui.click.component.IconButtonComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.SimpleTextLabelComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.component.SquareIconButtonComponent;
import java.awt.Color;

public class PartyPanel
extends PanelComponent {
    private final FlowLayoutComponent suggestedMembers;
    private final SimpleTextLabelComponent invitedLabel;
    private final PartyState partyState;
    private final SimpleTextLabelComponent suggestedLabel;
    private final IconButtonComponent closeButton;
    private final FlowLayoutComponent membersList;
    private final FlowLayoutComponent invitedList;

    public void refreshMembers() {
        this.membersList.t$src$V$zbu1jn();
        this.invitedList.t$src$V$zbu1jn();
        boolean localPlayerIsNotLeader = !this.partyState.getLeader().equals(Vape.INSTANCE.getOnlineManager().getLocalFriend());
        for (OnlineFriend friend : this.partyState.getMembers()) {
            if (this.partyState.getLeader() == friend) {
                this.membersList.addChildren(new PartyFriendRowComponent(friend, false, true));
                continue;
            }
            this.membersList.addChildren(new PartyFriendRowComponent(friend, false, localPlayerIsNotLeader));
        }
        this.invitedLabel.setVisible(!this.partyState.getInvitedUsers().isEmpty());
        for (OnlineFriend friend : this.partyState.getInvitedUsers()) {
            this.invitedList.addChildren(new PartyFriendRowComponent(friend, true, localPlayerIsNotLeader));
        }
    }


    public IconButtonComponent getCloseButton() {
        return this.closeButton;
    }

    @Override
    public void H() {
        super.H();
    }

    @Override
    public void u() {
        super.u();
        this.suggestedLabel.setVisible(false);
    }

    @Override
    public void c() {
        super.c();
        this.getFontRenderer(0.75).d("In party", this.G$src$D$1b2f02a() + 4.0, this.n() + 6.0, PartyPanel.J.Z);
    }

    public PartyPanel(PartyState partyState) {
        super(99.0, 150.0);
        this.closeButton = new SquareIconButtonComponent("newclose", 1.0, new Color(255, 255, 255, 0), PartyPanel.J.l, 8.0, 8.0);
        this.invitedLabel = new SimpleTextLabelComponent("Invited", 0.75, PartyPanel.J.Z);
        this.suggestedLabel = new SimpleTextLabelComponent("Suggested", 0.75, PartyPanel.J.Z);
        this.membersList = new FlowLayoutComponent(90.0);
        this.invitedList = new FlowLayoutComponent(90.0);
        this.suggestedMembers = new FlowLayoutComponent(90.0);
        this.partyState = partyState;
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        PanelComponent panelComponent = new PanelComponent(99.0, 15.0);
        panelComponent.addChildren(new SpacerComponent(87.0, 8.0), this.closeButton);
        this.addChildren(new SpacerComponent(1.0, 4.0), panelComponent, new SpacerComponent(1.0, 3.0), this.membersList, new SpacerComponent(1.0, 1.0), this.invitedLabel, new SpacerComponent(1.0, 3.0), this.invitedList, new SpacerComponent(1.0, 1.0), this.suggestedLabel, new SpacerComponent(1.0, 3.0), this.suggestedMembers);
        this.membersList.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        this.invitedList.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        this.t(150.0);
        this.refreshMembers();
        partyState.setPartyPanel(this);
    }
}

