package gg.vape.friend.ui;

import gg.vape.Vape;
import gg.vape.friend.PartyState;
import gg.vape.friend.ui.CurrentPartyLeaveDeleteClickHandler;
import gg.vape.friend.ui.CurrentPartyNameOpenDetailsMouseListener;
import gg.vape.friend.ui.CurrentPartyPanelOpenDetailsMouseListener;
import gg.vape.friend.ui.PartyDetailsAndChatPanel;
import gg.vape.friend.ui.PartyDetailsPopupCloseClickHandler;
import gg.vape.module.none.ClientSettings;
import gg.vape.protocol.ZeusConnectionManager;
import gg.vape.protocol.packet.GroupDeleteResponsePacket;
import gg.vape.protocol.packet.GroupLeaveResponsePacket;
import gg.vape.ui.click.MouseClickButton;
import gg.vape.ui.click.animation.ColorAnimation;
import gg.vape.ui.click.component.AnimatedPanelComponent;
import gg.vape.ui.click.component.ConfirmationDialogComponent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.component.TruncatedTextComponent;
import gg.vape.ui.click.component.gui.TextButton;
import gg.vape.ui.click.frame.DimmedCenteredPopupFrame;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.PopupFrame;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ImageRenderer;
import java.awt.Color;
import java.awt.Point;
import org.jetbrains.annotations.Nullable;

public class CurrentPartyPanel
extends AnimatedPanelComponent {
    private PartyDetailsAndChatPanel detailsPanel;
    private final TextButton leaveOrDisbandButton;
    private String partyName;
    private final ColorAnimation hoverAnimation;
    private final SpacerComponent iconSpacer;
    private static GuiComponent[] obfuscationComponents;
    private PartyState partyState;
    private PopupFrame detailsPopup;
    private boolean actionPending;
    private final TruncatedTextComponent partyNameLabel;
    private final PanelComponent actionPanel = new PanelComponent(23.0, 14.0);

    private void handleDisbandFailure() {
        this.actionPending = false;
    }

    private void handleLeaveFailure() {
        this.actionPending = false;
    }

    private static void handleLeaveResponse(GroupLeaveResponsePacket response) {
    }

    public static PartyDetailsAndChatPanel setDetailsPanel(CurrentPartyPanel panel, PartyDetailsAndChatPanel detailsPanel) {
        panel.detailsPanel = detailsPanel;
        return panel.detailsPanel;
    }

    public static void openDetails(CurrentPartyPanel panel, Point point, MouseClickButton mouseClickButton) {
        panel.openDetails(point, mouseClickButton);
    }

    public static PopupFrame getDetailsPopup(CurrentPartyPanel panel) {
        return panel.detailsPopup;
    }

    private void confirmDisband(PopupFrame popupFrame) {
        ClientSettings.removePopup(popupFrame);
        ZeusConnectionManager.T().u().l(CurrentPartyPanel::handleDisbandResponse, this::handleDisbandFailure);
    }

    @Override
    public void u() {
        if (Vape.INSTANCE.getOnlineManager() == null) {
            return;
        }
        this.hoverAnimation.u(this.w$src$Z$e457mb());
        this.partyState = Vape.INSTANCE.getOnlineManager().getPartyManager().getCurrentParty();
        this.setVisible(this.partyState != null);
        if (this.partyState != null) {
            if (this.partyState.getLeader().equals(Vape.INSTANCE.getOnlineManager().getLocalFriend())) {
                this.leaveOrDisbandButton.setLabelText("DISBAND");
                this.leaveOrDisbandButton.w("Disband party");
                this.leaveOrDisbandButton.setExplicitWidth(23.0);
                this.leaveOrDisbandButton.o(23.0);
                this.w("Disband party");
                this.partyName = "My party";
            } else {
                this.leaveOrDisbandButton.setLabelText("LEAVE");
                this.leaveOrDisbandButton.w("Leave party");
                this.w("Leave party");
                this.leaveOrDisbandButton.setExplicitWidth(18.0);
                this.leaveOrDisbandButton.o(18.0);
                this.partyName = this.partyState.getLeader().getDisplayName() + "'s party";
            }
            this.partyNameLabel.setExplicitWidth(this.A() - 18.0 - this.leaveOrDisbandButton.A() - 4.0);
            this.partyNameLabel.setMaxWidth(this.A() - 18.0 - this.leaveOrDisbandButton.A() - 6.0);
            this.partyNameLabel.setText(this.partyName);
        } else {
            this.w("Open party");
            if (this.detailsPopup != null) {
                ClientSettings.removePopup(this.detailsPopup);
                this.detailsPanel = null;
                this.detailsPopup = null;
            }
        }
    }


    public static PopupFrame setDetailsPopup(CurrentPartyPanel panel, PopupFrame popupFrame) {
        panel.detailsPopup = popupFrame;
        return panel.detailsPopup;
    }

    public static GuiComponent[] getObfuscationComponents() {
        return obfuscationComponents;
    }

    private void openDetails(Point point, MouseClickButton mouseClickButton) {
        this.detailsPanel = new PartyDetailsAndChatPanel(this.partyState);
        this.detailsPopup = ClientSettings.createPopup(this, this.detailsPanel, PopupFrame.class);
        this.detailsPanel.getCloseButton().addClickListener(new PartyDetailsPopupCloseClickHandler(this));
    }

    private static void handleDisbandResponse(GroupDeleteResponsePacket response) {
    }

    public static void setObfuscationComponents(GuiComponent[] components) {
        obfuscationComponents = components;
    }

    private void cancelDisband(PopupFrame popupFrame) {
        ClientSettings.removePopup(popupFrame);
        this.actionPending = false;
    }

    public CurrentPartyPanel() {
        super(100.0, 16.0);
        this.iconSpacer = new SpacerComponent(18.0, 16.0);
        this.leaveOrDisbandButton = new TextButton("LEAVE", 0.6, CurrentPartyPanel.J.d, CurrentPartyPanel.J.c, 18.0, 8.0);
        this.hoverAnimation = new ColorAnimation(0.15, new Color(150, 150, 150, 0), new Color(150, 150, 150, 20));
        this.actionPending = false;
        this.setShowDisabledOverlay(false);
        this.actionPanel.setShowDisabledOverlay(false);
        this.actionPanel.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().Q(true);
        this.partyNameLabel = new TruncatedTextComponent("", "...", 48.0, 0.8, CurrentPartyPanel.J.A, true);
        this.leaveOrDisbandButton.addClickListener(new CurrentPartyLeaveDeleteClickHandler(this));
        this.partyNameLabel.addMouseListener(new CurrentPartyNameOpenDetailsMouseListener(this));
        this.addMouseListener(new CurrentPartyPanelOpenDetailsMouseListener(this));
        this.w("Open party");
        this.leaveOrDisbandButton.setDeriveTextColorFromBackground(false);
        this.leaveOrDisbandButton.setNormalTextColor(Color.WHITE);
        this.actionPanel.addChildren(this.leaveOrDisbandButton);
        this.addChildren(this.iconSpacer, this.partyNameLabel, this.actionPanel);
    }

    @Override
    public void H() {
        if (this.detailsPopup != null) {
            Frame frame = this.L$src$Lgg_vape_ui_click_frame_Frame_$1djx6sa();
            this.detailsPopup.K(this.G$src$D$1b2f02a());
            this.detailsPopup.S(frame.n() + frame.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc().L());
            double chatHeight = frame.L() - frame.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc().L() - 45.0;
            this.detailsPanel.getChatPanel().getMessageListPanel().setExplicitHeight(chatHeight);
            this.detailsPanel.getChatPanel().getMessageListPanel().t(chatHeight);
            this.detailsPanel.getChatPanel().getMessageListPanel().l$src$V$1mibm4x();
            this.detailsPopup.l$src$V$1mibm4x();
        }
        this.partyNameLabel.S(this.n() + 5.0);
    }

    public static void performLeaveOrDisband(CurrentPartyPanel panel) {
        panel.performLeaveOrDisband();
    }

    @Override
    public void c() {
        if (this.partyState == null) {
            return;
        }
        GuiRenderPrimitives.d(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L() - 2.0, CurrentPartyPanel.J.m.brighter());
        GuiRenderPrimitives.d(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L() - 2.0, this.hoverAnimation.getInterpolatedColor());
        float iconX = (float)(this.G$src$D$1b2f02a() + 6.0);
        float iconY = (float)(this.n() + 4.0);
        ImageRenderer.drawImage(CurrentPartyPanel.J.B, iconX, iconY, "party1@2x", 7.0f, 6.3f, false);
        ImageRenderer.drawImage(CurrentPartyPanel.J.B, (float)(this.G$src$D$1b2f02a() + this.A() - 22.0), (float)this.n() - 0.5f, "join party texture@2x", 14.5f, 14.5f, false);
        super.c();
    }

    static {
        CurrentPartyPanel.setObfuscationComponents(null);
    }

    @Nullable
    public PartyDetailsAndChatPanel getDetailsPanel() {
        return this.detailsPanel;
    }

    private void performLeaveOrDisband() {
        if (this.actionPending) {
            return;
        }
        this.actionPending = true;
        if (this.partyState != null) {
            if (this.partyState.getLeader().equals(Vape.INSTANCE.getOnlineManager().getLocalFriend())) {
                ConfirmationDialogComponent confirmationDialog = new ConfirmationDialogComponent("Are you sure you want to disband the party?", "DISBAND", "disband confirm@2x");
                DimmedCenteredPopupFrame confirmationPopup = ClientSettings.createPopup(this.L$src$Lgg_vape_ui_click_frame_Frame_$1djx6sa(), confirmationDialog, DimmedCenteredPopupFrame.class);
                confirmationDialog.getConfirmButton().addClickListener(() -> this.confirmDisband(confirmationPopup));
                confirmationDialog.getCloseButton().addClickListener(() -> this.cancelDisband(confirmationPopup));
                confirmationPopup.q(this.L$src$Lgg_vape_ui_click_frame_Frame_$1djx6sa(), confirmationPopup);
            } else {
                ZeusConnectionManager.T().u().u(CurrentPartyPanel::handleLeaveResponse, this::handleLeaveFailure);
            }
        }
    }
}

