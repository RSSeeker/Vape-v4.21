package gg.vape.ui.click.frame.impl.main;

import gg.vape.friend.OnlineFriend;
import gg.vape.friend.OnlineStatus;
import gg.vape.ui.click.animation.ColorAnimation;
import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.component.TruncatedTextComponent;
import gg.vape.ui.click.component.gui.InteractiveComponent;
import gg.vape.ui.click.frame.impl.main.ClickGuiFriendsOnlineIndicatorComponent;
import gg.vape.ui.click.frame.impl.main.ClickGuiFriendsPrimaryActionButton;
import gg.vape.ui.click.frame.impl.main.ClickGuiFriendsSecondaryActionButton;
import gg.vape.ui.click.frame.impl.main.ClickGuiFriendsStatusIconComponent;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ClickGuiFriendsFriendListComponent
extends InteractiveComponent {
    private static final Color PRIMARY_ACTION_HOVER_COLOR;
    private static final Color PRIMARY_ACTION_TEXT_COLOR;
    private static final Color INDICATOR_LINE_COLOR;
    private static final Color HOVER_BACKGROUND_COLOR;
    private static final Color NORMAL_BACKGROUND_COLOR;
    private static final Color PRIMARY_TEXT_COLOR;
    private static final Color HOVER_PRIMARY_TEXT_COLOR;
    private static final Color SECONDARY_TEXT_COLOR;
    private static final Color ACTIVITY_TEXT_COLOR;
    private final OnlineFriend friend;
    private final ClickGuiFriendsStatusIconComponent statusIcon;
    private boolean chatActionVisible;
    private final ClickGuiFriendsSecondaryActionButton chatButton;
    private final ColorAnimation backgroundAnimation;
    private final ClickGuiFriendsPrimaryActionButton acceptButton;
    private final TruncatedTextComponent nameLabel;
    private final ClickGuiFriendsSecondaryActionButton settingsButton;
    private final TruncatedTextComponent statusLabel;
    private final TruncatedTextComponent activityLabel;
    private boolean acceptMode;
    private final ClickGuiFriendsOnlineIndicatorComponent onlineIndicator;

    private String getStatusText(boolean bl) {
        boolean bl2;
        boolean bl3 = bl2 = this.friend.isVisible() && this.friend.getMinecraftUsername() != null && !this.friend.getMinecraftUsername().isEmpty();
        if (bl2) {
            return this.friend.getMinecraftUsername();
        }
        OnlineStatus onlineStatus = this.friend.getStatus();
        if (onlineStatus != null) {
            return onlineStatus.getDisplayName();
        }
        return "";
    }

    private String getDisplayName(boolean bl) {
        if (bl && this.friend.getDisplayName() != null) {
            return this.friend.getDisplayName();
        }
        String string = this.friend.getDisplayName();
        return string != null ? string : "";
    }

    public void setChatActionVisible(boolean bl) {
        this.chatActionVisible = bl;
    }

    private String getActivityText() {
        String string = this.friend.getMinecraftServer();
        if (string == null) {
            return "";
        }
        return string.trim();
    }

    static Color getPrimaryActionTextColor() {
        return PRIMARY_ACTION_TEXT_COLOR;
    }

    public OnlineFriend getFriend() {
        return this.friend;
    }

    @Override
    public void H() {
        double d;
        double d2;
        double d3;
        double d4;
        double d5;
        double d6;
        super.H();
        boolean bl = this.w$src$Z$e457mb();
        this.backgroundAnimation.u(bl);
        double d7 = this.G$src$D$1b2f02a();
        double d8 = this.n();
        double d9 = this.A();
        double d10 = this.L();
        GuiRenderPrimitives.B(d7, d8, d9, d10, this.backgroundAnimation.getInterpolatedColor(), 3.0f);
        double d11 = d7 + 6.0;
        double d12 = d8 + (d10 - 10.0) / 2.0;
        this.statusIcon.K(d11);
        this.statusIcon.S(d12);
        this.statusIcon.o(10.0);
        this.statusIcon.Y(10.0);
        this.statusIcon.setStatusDotSize(5.0);
        double d13 = d7 + d9 - 6.0 - 10.0;
        double d14 = d8 + (d10 - 10.0) / 2.0;
        this.settingsButton.K(d13);
        this.settingsButton.S(d14);
        this.settingsButton.o(10.0);
        this.settingsButton.Y(10.0);
        this.settingsButton.setVisible(true);
        double d15 = d13 - 4.0;
        this.chatButton.setVisible(false);
        if (!this.acceptMode && this.chatActionVisible) {
            d6 = d13 - 14.0 - 10.0;
            d5 = d8 + (d10 - 10.0) / 2.0;
            this.chatButton.K(d6);
            this.chatButton.S(d5);
            this.chatButton.o(10.0);
            this.chatButton.Y(10.0);
            this.chatButton.setVisible(true);
            d15 = d6 - 4.0;
        }
        if (this.acceptMode) {
            this.acceptButton.updateWidth();
            d6 = this.acceptButton.A();
            d5 = d7 + d9 - 20.0 - d6;
            d4 = d8 + (d10 - 10.0) / 2.0;
            this.acceptButton.K(d5);
            this.acceptButton.S(d4);
            this.acceptButton.Y(10.0);
            d15 = d5 - 4.0;
        }
        String string = this.getDisplayName(bl);
        String string2 = this.getStatusText(bl);
        String string3 = this.getActivityText();
        SmoothFontRenderer smoothFontRenderer = this.getFontRenderer(0.625);
        d4 = string3.isEmpty() ? 0.0 : smoothFontRenderer.N(string3) + 6.0 + 3.0;
        double d16 = d15;
        if (d4 > 0.0) {
            d16 = Math.max(78.0, d15 - d4);
            d3 = d8 + (d10 - 7.0) / 2.0;
            this.onlineIndicator.setVisible(true);
            this.onlineIndicator.K(d16);
            this.onlineIndicator.S(d3);
            this.onlineIndicator.o(6.0);
            this.onlineIndicator.Y(7.0);
            d2 = d16 + 6.0 + 3.0;
            d = Math.max(0.0, d15 - d2);
            this.activityLabel.setVisible(true);
            this.activityLabel.K(d2);
            this.activityLabel.S(d3);
            this.activityLabel.o(d);
            this.activityLabel.Y(7.0);
            this.activityLabel.setMaxWidth(d);
            this.activityLabel.setText(string3);
        } else {
            this.onlineIndicator.setVisible(false);
            this.activityLabel.setVisible(false);
            this.activityLabel.setText("");
            d16 = d15;
        }
        d3 = d4 > 0.0 ? d16 - 10.0 : d15;
        d2 = d7 + 20.0;
        d = Math.max(0.0, d3 - d2);
        double d17 = 16.0;
        double d18 = d8 + (d10 - 16.0) / 2.0;
        this.nameLabel.K(d2);
        this.nameLabel.S(d18);
        this.nameLabel.o(d);
        this.nameLabel.Y(8.0);
        this.nameLabel.setMaxWidth(d);
        this.nameLabel.setText(string);
        this.nameLabel.setTextColor(bl ? HOVER_PRIMARY_TEXT_COLOR : PRIMARY_TEXT_COLOR);
        this.statusLabel.K(d2);
        this.statusLabel.S(d18 + 8.0 + 1.0);
        this.statusLabel.o(d);
        this.statusLabel.Y(7.0);
        this.statusLabel.setMaxWidth(d);
        this.statusLabel.setText(string2);
        this.statusLabel.setVisible(!string2.isEmpty());
        this.chatButton.setNotificationDotVisible(this.friend.hasUnreadMessage());
        this.statusIcon.setHovered(bl);
    }


    static {
        NORMAL_BACKGROUND_COLOR = ClickGuiFriendsFriendListComponent.J.m;
        HOVER_BACKGROUND_COLOR = new Color(34, 33, 34);
        PRIMARY_TEXT_COLOR = ClickGuiFriendsFriendListComponent.J.A;
        HOVER_PRIMARY_TEXT_COLOR = ClickGuiFriendsFriendListComponent.J.f;
        SECONDARY_TEXT_COLOR = ClickGuiFriendsFriendListComponent.J.C;
        ACTIVITY_TEXT_COLOR = ClickGuiFriendsFriendListComponent.J.C;
        INDICATOR_LINE_COLOR = new Color(103, 101, 103);
        PRIMARY_ACTION_TEXT_COLOR = Color.WHITE;
        PRIMARY_ACTION_HOVER_COLOR = ClickGuiFriendsFriendListComponent.J.O;
    }

    static Color getPrimaryActionHoverColor() {
        return PRIMARY_ACTION_HOVER_COLOR;
    }

    public OnlineFriend getOnlineFriend() {
        return this.friend;
    }

    static Color getIndicatorLineColor() {
        return INDICATOR_LINE_COLOR;
    }

    static Color getPrimaryActionColor() {
        return PRIMARY_ACTION_TEXT_COLOR;
    }

    public void setSettingsClickListener(@Nullable GuiClickListener guiClickListener) {
        this.settingsButton.setClickListener(guiClickListener);
    }

    public ClickGuiFriendsFriendListComponent(@NotNull OnlineFriend onlineFriend) {
        this.getClass();
        this.backgroundAnimation = new ColorAnimation(0.15, NORMAL_BACKGROUND_COLOR, HOVER_BACKGROUND_COLOR);
        this.statusIcon = new ClickGuiFriendsStatusIconComponent(this, null);
        this.nameLabel = new TruncatedTextComponent("", "...", 0.0, 0.75, PRIMARY_TEXT_COLOR, false, false);
        this.statusLabel = new TruncatedTextComponent("", "...", 0.0, 0.625, SECONDARY_TEXT_COLOR, false, false);
        this.activityLabel = new TruncatedTextComponent("", "...", 0.0, 0.625, ACTIVITY_TEXT_COLOR, false, false);
        this.onlineIndicator = new ClickGuiFriendsOnlineIndicatorComponent(this, null);
        this.chatButton = new ClickGuiFriendsSecondaryActionButton(this, "chat@2x", null);
        this.settingsButton = new ClickGuiFriendsSecondaryActionButton(this, "settingdots", null);
        this.acceptButton = new ClickGuiFriendsPrimaryActionButton(this, "ACCEPT", null);
        this.chatActionVisible = true;
        this.acceptMode = false;
        this.friend = onlineFriend;
        this.setShowDisabledOverlay(false);
        this.Y(22.0);
        this.statusIcon.setShowDisabledOverlay(false);
        this.statusIcon.setAcceptsMouseInput(false);
        this.nameLabel.setShadowEnabled(false);
        this.statusLabel.setShadowEnabled(false);
        this.activityLabel.setShadowEnabled(false);
        this.onlineIndicator.setVisible(false);
        this.onlineIndicator.setAcceptsMouseInput(false);
        this.chatButton.setVisible(false);
        this.settingsButton.setVisible(true);
        this.acceptButton.setVisible(false);
        this.nameLabel.setAdditionalTooltipText("");
        this.statusLabel.setAdditionalTooltipText("");
        this.addChildren(this.statusIcon, this.nameLabel, this.statusLabel, this.onlineIndicator, this.activityLabel, this.chatButton, this.settingsButton, this.acceptButton);
    }

    public void setAcceptClickListener(@Nullable GuiClickListener guiClickListener) {
        this.acceptButton.setClickListener(guiClickListener);
    }

    public void setAcceptMode(boolean bl) {
        this.acceptMode = bl;
        this.acceptButton.setVisible(bl);
        if (bl) {
            this.chatButton.setVisible(false);
        }
    }

    public void setChatClickListener(@Nullable GuiClickListener guiClickListener) {
        this.chatButton.setClickListener(guiClickListener);
    }
}

