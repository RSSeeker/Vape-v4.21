package gg.vape.friend.ui;

import gg.vape.Vape;
import gg.vape.friend.LocalOnlineFriend;
import gg.vape.friend.ui.UsernameCopyClickHandler;
import gg.vape.friend.ui.UsernameEditorCurrentNameLabel;
import gg.vape.friend.ui.UsernameEditorEditButtonClickHandler;
import gg.vape.friend.ui.UsernameEditorEditModeToggleClickHandler;
import gg.vape.friend.ui.UsernameEditorTextInputComponent;
import gg.vape.protocol.ZeusConnectionManager;
import gg.vape.protocol.packet.UserDisplayNameResponsePacket;
import gg.vape.protocol.packet.UserDisplayNameStatus;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.FlowLayoutComponent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.IconButtonComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.component.TextLabelComponent;
import gg.vape.ui.click.component.gui.TextLabel;
import gg.vape.ui.click.component.input.DebouncedTextInputComponent;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class UsernameEditorPanel
extends PanelComponent {
    private final PanelComponent editPanel;
    private final IconButtonComponent onlineStatusIcon = new IconButtonComponent("status online@2x", 1.4);
    private final IconButtonComponent copyButton;
    private final FlowLayoutComponent nameLayout = new FlowLayoutComponent(100.0);
    private final IconButtonComponent editButton;
    private static GuiComponent[] obfuscationComponents;
    private final AtomicBoolean requestPending;
    private final DebouncedTextInputComponent usernameInput;
    private final TextLabel cancelLabel;
    private final PanelComponent displayPanel;
    private final PanelComponent actionsPanel = new PanelComponent(20.0, 16.0);
    private final TextLabelComponent currentNameLabel;

    public static void toggleEditMode(UsernameEditorPanel panel) {
        panel.toggleEditMode();
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    private void toggleEditMode() {
        if (this.displayPanel.V$src$Z$1xhop3l()) {
            this.displayPanel.setVisible(false);
            this.editPanel.setVisible(true);
            LocalOnlineFriend localFriend = Vape.INSTANCE.getOnlineManager().getLocalFriend();
            String displayName = localFriend.getDisplayName();
            this.usernameInput.setText(displayName);
        } else {
            this.displayPanel.setVisible(true);
            this.editPanel.setVisible(false);
        }
    }

    public static void submitNameChange(AtomicBoolean requestPending, String newName, Consumer<String> successConsumer, Consumer<String> errorConsumer) {
        if (requestPending.get()) {
            return;
        }
        if (newName.isEmpty() || newName.equals(Vape.INSTANCE.getOnlineManager().getLocalFriend().getDisplayName())) {
            return;
        }
        requestPending.set(true);
        ZeusConnectionManager.T().u().U(newName, response -> UsernameEditorPanel.handleNameChangeResponse(successConsumer, errorConsumer, response), () -> UsernameEditorPanel.clearRequestPending(requestPending));
    }

    public static void setObfuscationComponents(GuiComponent[] components) {
        obfuscationComponents = components;
    }

    @Override
    public void H() {
        super.H();
    }


    @Override
    public void u() {
        super.u();
    }

    @Override
    public void Y() {
    }

    @Override
    public void v() {
    }

    @Override
    public void M() {
    }

    @Override
    public void c() {
        this.onlineStatusIcon.Y(17.0);
        this.onlineStatusIcon.setExplicitWidth(8.0);
        GuiRenderPrimitives.C(this.G$src$D$1b2f02a() - 3.0, this.n(), this.A() + 7.0, this.L(), new Color(255, 255, 255, 10));
        super.c();
        if (this.editPanel.V$src$Z$1xhop3l()) {
            GuiRenderPrimitives.a(this.usernameInput.G$src$D$1b2f02a() + 4.0, this.usernameInput.n() + 13.0, this.usernameInput.A() - 20.0, 1.0f, UsernameEditorPanel.J.y);
        } else {
            this.currentNameLabel.setText(Vape.INSTANCE.getOnlineManager().getLocalFriend().getDisplayName());
            this.currentNameLabel.setMaxWidth(this.w$src$Z$e457mb() ? this.A() - 36.0 : this.A() - 26.0);
        }
        this.editButton.setNormalColor(this.w$src$Z$e457mb() ? UsernameEditorPanel.J.W : UsernameEditorPanel.J.t);
    }

    private static void handleNameChangeResponse(Consumer<String> successConsumer, Consumer<String> errorConsumer, UserDisplayNameResponsePacket response) {
        if (response.getStatus() == UserDisplayNameStatus.SUCCESSFUL) {
            Vape.INSTANCE.getAccountInfo().setUsername(response.getDisplayName());
            successConsumer.accept(response.getDisplayName());
        } else if (response.getStatus() == UserDisplayNameStatus.COOLDOWN) {
            String cooldown = response.getUserIdOrCooldownEnd() / 1000L + "s";
            errorConsumer.accept("On cooldown for " + cooldown);
        } else if (response.getStatus() == UserDisplayNameStatus.USERNAME_VALIDATION_FAILED) {
            errorConsumer.accept("Invalid characters were used");
        } else if (response.getStatus() == UserDisplayNameStatus.BANNED) {
            errorConsumer.accept("You're banned from changing your username");
        } else if (response.getStatus() == UserDisplayNameStatus.USERNAME_TAKEN) {
            errorConsumer.accept("Username already taken");
        } else {
            errorConsumer.accept("Name change error");
        }
    }

    public static AtomicBoolean getRequestPending(UsernameEditorPanel panel) {
        return panel.requestPending;
    }

    static {
        UsernameEditorPanel.setObfuscationComponents(new GuiComponent[5]);
    }

    @Override
    public void V() {
    }

    public UsernameEditorPanel() {
        super(104.0, 16.0);
        this.editButton = new IconButtonComponent("newedit", 0.6, UsernameEditorPanel.J.W, UsernameEditorPanel.J.f, 10.0, 10.0);
        this.copyButton = new IconButtonComponent("newcopy", 0.6, UsernameEditorPanel.J.h);
        this.displayPanel = new PanelComponent(104.0, 16.0);
        this.editPanel = new PanelComponent(104.0, 16.0);
        this.requestPending = new AtomicBoolean();
        this.usernameInput = new UsernameEditorTextInputComponent(this, "Enter username", 10000L);
        this.cancelLabel = new TextLabel("Cancel", 0.8, false, UsernameEditorPanel.J.l);
        this.onlineStatusIcon.Y(16.0);
        this.onlineStatusIcon.setNormalColor(Color.WHITE);
        this.onlineStatusIcon.setHoverColor(Color.WHITE);
        this.currentNameLabel = new UsernameEditorCurrentNameLabel(this, "", 0.6, 0.8, 0.1, this.w$src$Z$e457mb() ? this.A() - 36.0 : this.A() - 26.0, true, false, UsernameEditorPanel.J.h, this);
        this.setShowDisabledOverlay(false);
        this.t(16.0);
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().Q(true);
        this.usernameInput.getActionButton().setIconResource("newnext");
        this.nameLayout.setShowDisabledOverlay(false);
        this.copyButton.setExplicitWidth(10.0);
        this.copyButton.setExplicitHeight(10.0);
        this.editButton.setExplicitWidth(10.0);
        this.editButton.setExplicitHeight(10.0);
        this.copyButton.setBorderColor(UsernameEditorPanel.J.y);
        this.actionsPanel.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().Q(true);
        this.actionsPanel.setShowDisabledOverlay(false);
        this.actionsPanel.setExplicitWidth(25.0);
        this.nameLayout.addChildren(new SpacerComponent(2.0, 1.0), this.onlineStatusIcon, this.currentNameLabel);
        this.actionsPanel.addChildren(this.editButton);
        this.actionsPanel.addChildren(new SpacerComponent(2.0, 1.0));
        this.actionsPanel.addChildren(this.copyButton);
        this.displayPanel.h(this.nameLayout, new Object[0]);
        this.displayPanel.h(this.actionsPanel, "alignright");
        this.displayPanel.setShowDisabledOverlay(false);
        this.editPanel.h(this.usernameInput, new Object[0]);
        this.editPanel.h(this.cancelLabel, new Object[0]);
        this.editPanel.setVisible(false);
        this.editPanel.setShowDisabledOverlay(false);
        this.usernameInput.setBackgroundVisible(false);
        this.usernameInput.setMaxLength(16);
        this.editButton.setBorderColor(UsernameEditorPanel.J.t);
        this.cancelLabel.setExplicitHeight(10.0);
        this.cancelLabel.setExplicitWidth(22.0);
        this.editPanel.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().Q(true);
        this.editButton.w("Edit display name");
        this.copyButton.w("Copy display name");
        this.h(this.displayPanel, new Object[0]);
        this.h(this.editPanel, new Object[0]);
        this.copyButton.addClickListener(new UsernameCopyClickHandler(this));
        this.editButton.addClickListener(new UsernameEditorEditButtonClickHandler(this));
        this.cancelLabel.addClickListener(new UsernameEditorEditModeToggleClickHandler(this));
    }

    public static GuiComponent[] getObfuscationComponents() {
        return obfuscationComponents;
    }

    private static void clearRequestPending(AtomicBoolean requestPending) {
        requestPending.set(false);
    }
}
