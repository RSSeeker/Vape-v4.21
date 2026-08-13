package gg.vape.friend.ui;

import gg.vape.friend.LocalOnlineFriend;
import gg.vape.friend.OnlineFriend;
import gg.vape.friend.ui.OnlineFriendAvatarComponent;
import gg.vape.friend.ui.PartyMemberNameTextComponent;
import gg.vape.friend.ui.PartyMemberStatusComponent;
import gg.vape.ui.click.component.FlowLayoutComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.component.TruncatedTextComponent;

public class PartyMemberRow
extends PanelComponent {
    private final FlowLayoutComponent contentPanel = new FlowLayoutComponent(100.0);
    private final TruncatedTextComponent nameLabel;
    private final OnlineFriendAvatarComponent avatar;
    private final PartyMemberStatusComponent statusComponent;
    private static int[] obfuscationValues;
    private final PanelComponent avatarPanel = new PanelComponent(18.0, 16.0);
    private final OnlineFriend friend;
    private final boolean localUser;

    public PartyMemberRow(OnlineFriend onlineFriend, PartyMemberStatusComponent partyMemberStatusComponent) {
        this(onlineFriend, partyMemberStatusComponent, true);
    }

    static {
        PartyMemberRow.setObfuscationValues(new int[5]);
    }

    @Override
    public void c() {
        super.c();
        this.avatarPanel.setExplicitWidth(this.avatar.V$src$Z$1xhop3l() ? 18.0 : 6.0);
    }

    @Override
    public double L() {
        return Math.max(this.contentPanel.L() + 3.0, this.avatarPanel.L());
    }

    public boolean isLocalUser() {
        return this.localUser;
    }

    @Override
    public double A() {
        return this.contentPanel.A() + this.avatarPanel.A();
    }

    public PartyMemberRow(OnlineFriend friend, PartyMemberStatusComponent statusComponent, boolean showName) {
        super(0.0, 0.0);
        this.setShowDisabledOverlay(false);
        this.statusComponent = statusComponent;
        this.localUser = friend instanceof LocalOnlineFriend;
        this.friend = friend;
        this.nameLabel = new PartyMemberNameTextComponent(this, friend.getDisplayName(), "...", 74.0, 0.75, PartyMemberRow.J.h, false);
        this.contentPanel.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        this.contentPanel.setShowDisabledOverlay(false);
        this.setExplicitWidth(statusComponent.A());
        this.setExplicitHeight(statusComponent.getExplicitHeight());
        this.avatar = new OnlineFriendAvatarComponent(friend, 8.0, 8.0);
        this.avatarPanel.setShowDisabledOverlay(false);
        if (this.localUser) {
            statusComponent.setDisabledOverlayColor(PartyMemberRow.J.B);
            statusComponent.setLocalUser(true);
            this.contentPanel.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("alignright, wrap");
            this.avatarPanel.addChildren(new SpacerComponent(3.0, 1.0), this.avatar);
            this.addChildren(this.contentPanel, this.avatarPanel);
        } else {
            statusComponent.setDisabledOverlayColor(PartyMemberRow.J.g);
            this.avatarPanel.addChildren(new SpacerComponent(6.0, 1.0), this.avatar);
            this.addChildren(this.avatarPanel, this.contentPanel);
        }
        if (showName) {
            this.contentPanel.addChildren(this.isLocalUser() ? new SpacerComponent(1.0, 8.0) : this.nameLabel);
        }
        this.contentPanel.h(statusComponent, new Object[0]);
        if (!statusComponent.showsAvatar()) {
            this.avatarPanel.setVisible(false);
        }
    }

    public OnlineFriendAvatarComponent getAvatar() {
        return this.avatar;
    }

    public PanelComponent getAvatarPanel() {
        return this.avatarPanel;
    }

    public static void setObfuscationValues(int[] values) {
        obfuscationValues = values;
    }

    public static int[] getObfuscationValues() {
        return obfuscationValues;
    }

}

