package gg.vape.friend.ui;

import gg.vape.Vape;
import gg.vape.friend.PartyState;
import gg.vape.friend.ui.OnlineFriendAvatarStackComponent;
import gg.vape.friend.ui.OnlineFriendsFrame;
import gg.vape.friend.ui.PartyOverviewBackgroundPanel;
import gg.vape.friend.ui.PartyOverviewGroupOptionSyncMouseListener;
import gg.vape.friend.ui.PartyOverviewPanelActionClickHandler;
import gg.vape.friend.ui.PartyOverviewPanelPopupClickListener;
import gg.vape.friend.ui.PartyOverviewPanelPopupOutsideClickFilter;
import gg.vape.friend.ui.PartyOverviewPanelPopupOutsideClickListener;
import gg.vape.friend.ui.PartyPanel;
import gg.vape.module.none.ClientSettings;
import gg.vape.protocol.ZeusConnectionManager;
import gg.vape.protocol.packet.GroupDeleteResponsePacket;
import gg.vape.protocol.packet.GroupLeaveResponsePacket;
import gg.vape.protocol.packet.GroupOption;
import gg.vape.ui.click.component.ConfirmationDialogComponent;
import gg.vape.ui.click.component.FlowLayoutComponent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.IconButtonComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.SimpleTextLabelComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.component.SquareIconButtonComponent;
import gg.vape.ui.click.component.gui.TextButton;
import gg.vape.ui.click.component.value.BooleanStateAdapter;
import gg.vape.ui.click.component.value.BooleanToggleComponent;
import gg.vape.ui.click.frame.AnchoredPopupFrame;
import gg.vape.ui.click.frame.DimmedCenteredPopupFrame;
import gg.vape.ui.click.frame.PopupFrame;
import gg.vape.value.BooleanValue;
import gg.vape.value.Value;
import java.awt.Color;
import java.util.Map;

public class PartyDetailsPanel
extends PanelComponent {
    private static int obfuscationSeed;
    private boolean actionPending;
    private final IconButtonComponent closeButton;
    private AnchoredPopupFrame settingsPopup;
    private final IconButtonComponent settingsButton;
    private DimmedCenteredPopupFrame membersPopup;
    private final TextButton leaveOrDisbandButton;
    private FlowLayoutComponent settingsPanel;
    private final PartyPanel partyPanel;
    private PartyState partyState;

    public static AnchoredPopupFrame getSettingsPopup(PartyDetailsPanel panel) {
        return panel.settingsPopup;
    }

    private static void handleLeaveResponse(GroupLeaveResponsePacket response) {
    }

    public static int getReservedZero() {
        int reserved = PartyDetailsPanel.getObfuscationSeed();
        return 0;
    }

    public static void toggleSettingsPopup(PartyDetailsPanel panel) {
        panel.toggleSettingsPopup();
    }

    public static void openMembersPopup(PartyDetailsPanel panel) {
        panel.openMembersPopup();
    }

    public static void setObfuscationSeed(int seed) {
        obfuscationSeed = seed;
    }

    public static FlowLayoutComponent getSettingsPanel(PartyDetailsPanel panel) {
        return panel.settingsPanel;
    }

    private void closeMembersPopup() {
        if (this.membersPopup != null) {
            ClientSettings.removePopup(this.membersPopup);
            this.membersPopup = null;
        }
    }

    private void performLeaveOrDisband() {
        if (this.actionPending) {
            return;
        }
        this.actionPending = true;
        if (this.partyState != null) {
            if (this.partyState.getLeader().equals(Vape.INSTANCE.getOnlineManager().getLocalFriend())) {
                try {
                    ClientSettings.removePopup(this.settingsPopup);
                }
                catch (Exception exception) {
                    // empty catch block
                }
                ConfirmationDialogComponent confirmationDialog = new ConfirmationDialogComponent("Are you sure you want to disband the party?", "DISBAND", "disband confirm@2x");
                DimmedCenteredPopupFrame confirmationPopup = ClientSettings.createPopup(this.L$src$Lgg_vape_ui_click_frame_Frame_$1djx6sa(), confirmationDialog, DimmedCenteredPopupFrame.class);
                confirmationDialog.getConfirmButton().addClickListener(() -> this.confirmDisband(confirmationPopup));
                confirmationDialog.getCloseButton().addClickListener(() -> this.cancelDisband(confirmationPopup));
                confirmationPopup.addMouseListener(new PartyOverviewPanelPopupOutsideClickFilter(this, confirmationPopup));
            } else {
                ClientSettings.removePopup(this.settingsPopup);
                ZeusConnectionManager.T().u().u(PartyDetailsPanel::handleLeaveResponse, this::handleLeaveFailure);
            }
        }
    }

    static {
        PartyDetailsPanel.setObfuscationSeed(13);
    }

    private void toggleSettingsPopup() {
        if (this.settingsPopup == null) {
            OnlineFriendsFrame onlineFriendsFrame = ClientSettings.getFrame(OnlineFriendsFrame.class);
            this.settingsPopup = (AnchoredPopupFrame)onlineFriendsFrame.createPopup(this.settingsButton, this.settingsPanel, AnchoredPopupFrame.class);
            this.settingsPopup.addMouseListener(new PartyOverviewPanelPopupOutsideClickListener(this));
        } else {
            this.closeSettingsPopup();
        }
    }

    private void handleLeaveFailure() {
        this.actionPending = false;
    }

    public IconButtonComponent getCloseButton() {
        return this.closeButton;
    }

    private static Exception identityException(Exception exception) {
        return exception;
    }

    private void cancelDisband(PopupFrame popupFrame) {
        ClientSettings.removePopup(popupFrame);
        this.actionPending = false;
    }

    public static void closeSettingsPopup(PartyDetailsPanel panel) {
        panel.closeSettingsPopup();
    }

    private void confirmDisband(PopupFrame popupFrame) {
        ClientSettings.removePopup(popupFrame);
        ZeusConnectionManager.T().u().l(PartyDetailsPanel::handleDisbandResponse, this::handleDisbandFailure);
    }

    @Override
    public void c() {
        this.setShowDisabledOverlay(false);
        super.c();
    }

    private static void handleDisbandResponse(GroupDeleteResponsePacket response) {
    }

    private void closeSettingsPopup() {
        if (this.settingsPopup != null) {
            ClientSettings.removePopup(this.settingsPopup);
            this.settingsPopup = null;
        }
    }

    @Override
    public void u() {
        this.partyState = Vape.INSTANCE.getOnlineManager().getPartyManager().getCurrentParty();
        if (this.partyState == null) {
            this.closeMembersPopup();
            this.closeSettingsPopup();
            return;
        }
        boolean localPlayerIsLeader = this.partyState.getLeader().equals(Vape.INSTANCE.getOnlineManager().getLocalFriend());
        for (GuiComponent child : this.settingsPanel.f()) {
            if (!(child instanceof BooleanStateAdapter)) continue;
            ((BooleanStateAdapter)((Object)child)).setReadOnly(!localPlayerIsLeader);
        }
        super.u();
    }

    private void handleDisbandFailure() {
        this.actionPending = false;
    }

    public static int getObfuscationSeed() {
        return obfuscationSeed;
    }

    public PartyDetailsPanel(PartyState partyState) {
        super(92.0, 11.0);
        this.closeButton = new SquareIconButtonComponent("newclose", 1.0, new Color(0, 0, 0, 0), PartyDetailsPanel.J.l, 10.0, 10.0);
        this.settingsButton = new IconButtonComponent("more", 1.0, PartyDetailsPanel.J.f, Color.white, 8.0, 8.0);
        this.settingsPanel = new FlowLayoutComponent(80.0);
        this.actionPending = false;
        this.partyState = partyState;
        this.partyPanel = new PartyPanel(partyState);
        PanelComponent panelComponent = new PanelComponent(45.0, 8.0);
        panelComponent.addChildren(new SpacerComponent(2.0, 1.0));
        OnlineFriendAvatarStackComponent onlineFriendAvatarStackComponent = new OnlineFriendAvatarStackComponent(partyState.getMembers());
        panelComponent.addChildren(onlineFriendAvatarStackComponent);
        onlineFriendAvatarStackComponent.addMouseListener(new PartyOverviewPanelPopupClickListener(this));
        onlineFriendAvatarStackComponent.w("Party member list");
        this.settingsButton.w("Party settings");
        this.partyPanel.getCloseButton().addClickListener(this::closeMembersPopup);
        PanelComponent panelComponent2 = new PanelComponent(45.0, 8.0);
        panelComponent2.h(new SpacerComponent(panelComponent2.A() - this.settingsButton.A() - this.closeButton.A() - 2.0, 1.0), new Object[0]);
        panelComponent2.h(this.settingsButton, new Object[0]);
        panelComponent2.h(new SpacerComponent(2.0, 1.0), new Object[0]);
        panelComponent2.h(this.closeButton, new Object[0]);
        panelComponent2.setShowDisabledOverlay(false);
        panelComponent2.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().Q(true);
        panelComponent.setShowDisabledOverlay(false);
        this.addChildren(panelComponent, panelComponent2);
        this.settingsButton.addClickListener(new PartyOverviewPanelActionClickHandler(this));
        this.settingsPanel = new FlowLayoutComponent(99.0);
        this.settingsPanel.addChildren(new SpacerComponent(99.0, 3.0));
        SimpleTextLabelComponent simpleTextLabelComponent = new SimpleTextLabelComponent("Party Settings");
        simpleTextLabelComponent.setTextColor(PartyDetailsPanel.J.Z);
        this.settingsPanel.addChildren(simpleTextLabelComponent);
        for (Map.Entry<GroupOption, Value<?, ?>> optionEntry : partyState.getOptions().entrySet()) {
            GroupOption groupOption = optionEntry.getKey();
            Value<?, ?> value = optionEntry.getValue();
            if (!(value instanceof BooleanValue)) continue;
            BooleanValue booleanValue = (BooleanValue)value;
            BooleanToggleComponent booleanToggleComponent = new BooleanToggleComponent(booleanValue);
            booleanToggleComponent.addMouseListener(new PartyOverviewGroupOptionSyncMouseListener(this, booleanValue, groupOption, value));
            this.settingsPanel.addChildren(booleanToggleComponent);
        }
        this.settingsPanel.setDisabledOverlayColor(PartyDetailsPanel.J.y);
        this.leaveOrDisbandButton = new TextButton(partyState.getLeader().equals(Vape.INSTANCE.getOnlineManager().getLocalFriend()) ? "DISBAND" : "LEAVE", 0.9, PartyDetailsPanel.J.d, PartyDetailsPanel.J.c, 80.0, 10.0);
        this.leaveOrDisbandButton.addClickListener(this::performLeaveOrDisband);
        this.leaveOrDisbandButton.setDeriveTextColorFromBackground(false);
        this.settingsPanel.o(99.0);
        PartyOverviewBackgroundPanel partyOverviewBackgroundPanel = new PartyOverviewBackgroundPanel(this, this.settingsPanel.A(), 14.0);
        partyOverviewBackgroundPanel.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        partyOverviewBackgroundPanel.h(new SpacerComponent(99.0, 2.0), new Object[0]);
        PanelComponent panelComponent3 = new PanelComponent(99.0, 10.0);
        panelComponent3.addChildren(new SpacerComponent(this.settingsPanel.A() / 2.0 - this.leaveOrDisbandButton.A() / 2.0, 0.0), this.leaveOrDisbandButton, new SpacerComponent(this.settingsPanel.A() / 2.0 - this.leaveOrDisbandButton.A() / 2.0, 0.0));
        partyOverviewBackgroundPanel.addChildren(panelComponent3);
        partyOverviewBackgroundPanel.setDisabledOverlayColor(PartyDetailsPanel.J.i);
        this.settingsPanel.addChildren(partyOverviewBackgroundPanel);
        this.settingsPanel.h(new SpacerComponent(99.0, 6.0), new Object[0]);
    }

    private void openMembersPopup() {
        if (this.membersPopup == null) {
            OnlineFriendsFrame onlineFriendsFrame = ClientSettings.getFrame(OnlineFriendsFrame.class);
            this.membersPopup = (DimmedCenteredPopupFrame)onlineFriendsFrame.createPopup(onlineFriendsFrame.getPagePanel(), this.partyPanel, DimmedCenteredPopupFrame.class);
        }
    }
}
