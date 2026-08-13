package gg.vape.ui.click.frame.impl.main;

import gg.vape.friend.ui.PlayerAvatarComponent;
import gg.vape.ui.click.animation.ColorAnimation;
import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.component.IconShape;
import gg.vape.ui.click.component.ShapeIconComponent;
import gg.vape.ui.click.component.TruncatedTextComponent;
import gg.vape.ui.click.component.gui.InteractiveComponent;
import gg.vape.ui.click.component.gui.TextButton;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.PlayerInfo;
import gg.vape.wrapper.impl.ResourceLocation;
import java.awt.Color;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ClickGuiFriendsFriendActionComponent
extends InteractiveComponent {
    private static final Color BADGE_COLOR;
    private static final Color BADGE_BACKGROUND_COLOR;
    private static final Color HOVER_TEXT_COLOR;
    private static final Color NORMAL_BACKGROUND_COLOR;
    private static final Color HOVER_BACKGROUND_COLOR;
    private static final Color TEXT_COLOR;
    private final String playerName;
    private GuiClickListener removeListener;
    private final PlayerAvatarComponent avatar;
    private final TruncatedTextComponent nameLabel;
    private final TextButton addButton;
    private final ShapeIconComponent badge;
    private final ColorAnimation backgroundAnimation;
    private final TextButton removeButton;
    private GuiClickListener addListener;
    private boolean removeMode;

    @Override
    public void H() {
        super.H();
        boolean bl = this.w$src$Z$e457mb();
        this.backgroundAnimation.u(bl);
        GuiRenderPrimitives.B(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), this.backgroundAnimation.getInterpolatedColor(), 3.0f);
        double d = this.G$src$D$1b2f02a();
        double d2 = this.n();
        double d3 = this.L();
        double d4 = this.A();
        this.avatar.K(d + 4.0);
        this.avatar.S(d2 + (d3 - 14.0) / 2.0);
        this.avatar.o(14.0);
        this.avatar.Y(14.0);
        double d5 = d + d4 - 6.0 - 35.0;
        double d6 = d2 + (d3 - 14.0) / 2.0;
        this.addButton.K(d5);
        this.addButton.S(d6);
        this.addButton.o(35.0);
        this.addButton.Y(14.0);
        double d7 = d + d4 - 6.0 - 50.0;
        double d8 = d2 + (d3 - 14.0) / 2.0;
        this.removeButton.K(d7);
        this.removeButton.S(d8);
        this.removeButton.o(50.0);
        this.removeButton.Y(14.0);
        double d9 = this.removeMode ? d7 - 6.0 : d5 - 6.0;
        double d10 = d + 22.0;
        double d11 = Math.max(0.0, d9 - d10);
        if (this.badge != null) {
            double d12 = d9 - d10;
            double d13 = this.badge.getRequiredWidth();
            double d14 = Math.max(0.0, d12 - d13 - 6.0);
            SmoothFontRenderer smoothFontRenderer = this.getFontRenderer(0.75);
            double d15 = smoothFontRenderer.N(this.playerName);
            double d16 = Math.min(d15, d14);
            this.badge.K(d10 + d16 + 6.0);
            this.badge.S(d2 + (d3 - 10.0) / 2.0);
            this.badge.o(d13);
            this.badge.Y(10.0);
            d11 = d14;
        }
        this.nameLabel.K(d10);
        this.nameLabel.S(d2);
        this.nameLabel.o(d11);
        this.nameLabel.Y(d3);
        this.nameLabel.setMaxWidth(d11);
        this.nameLabel.setTextColor(bl ? HOVER_TEXT_COLOR : TEXT_COLOR);
    }

    public void setRemoveSecondaryClickListener(@Nullable GuiClickListener guiClickListener) {
        this.removeListener = guiClickListener;
        this.removeButton.setClickListener(this::handleRemoveClick);
    }

    public PlayerAvatarComponent getAvatar() {
        return this.avatar;
    }

    private void handleAddClick() {
        if (this.addListener != null) {
            this.addListener.onPrimaryClick();
        }
        this.setRemoveMode(true);
    }

    public void setAddSecondaryClickListener(@Nullable GuiClickListener guiClickListener) {
        this.addListener = guiClickListener;
        this.addButton.setClickListener(this::handleAddClick);
    }

    private void handleRemoveClick() {
        if (this.removeListener != null) {
            this.removeListener.onPrimaryClick();
        }
        this.setRemoveMode(false);
    }

    public ClickGuiFriendsFriendActionComponent(@NotNull String string, @Nullable String string2) {
        this(string, string2, null);
    }

    public void setRemoveMode(boolean bl) {
        this.removeMode = bl;
        this.addButton.setVisible(!bl);
        this.removeButton.setVisible(bl);
    }

    public ClickGuiFriendsFriendActionComponent(@NotNull String string, @Nullable String string2, @Nullable EntityPlayer entityPlayer) {
        this(string, string2, entityPlayer, null);
    }

    public void setAddPrimaryClickListener(@NotNull GuiClickListener guiClickListener) {
        this.addListener = guiClickListener;
        this.addButton.addClickListener(this::handleAddClick);
    }

    public ClickGuiFriendsFriendActionComponent(@NotNull String string) {
        this(string, null, null);
    }

    public boolean isRemoveMode() {
        return this.removeMode;
    }

    public ClickGuiFriendsFriendActionComponent(@NotNull String string, @Nullable String string2, @Nullable EntityPlayer entityPlayer, @Nullable PlayerInfo playerInfo) {
        ResourceLocation resourceLocation;
        this.getClass();
        this.backgroundAnimation = new ColorAnimation(0.15, NORMAL_BACKGROUND_COLOR, HOVER_BACKGROUND_COLOR);
        this.removeMode = false;
        this.playerName = string;
        this.Y(22.0);
        this.setShowDisabledOverlay(false);
        this.avatar = entityPlayer != null && entityPlayer.isNotNull() ? PlayerAvatarComponent.fromEntityPlayer(entityPlayer, 14.0, 14.0) : (playerInfo != null ? ((resourceLocation = playerInfo.i()) != null && resourceLocation.isNotNull() ? PlayerAvatarComponent.fromTexture(resourceLocation, string, 14.0, 14.0) : new PlayerAvatarComponent(string, 14.0, 14.0)) : new PlayerAvatarComponent(string, 14.0, 14.0));
        this.nameLabel = new TruncatedTextComponent(string, "...", 0.0, 0.75, TEXT_COLOR, false);
        this.nameLabel.setShadowEnabled(false);
        this.nameLabel.setAdditionalTooltipText(string);
        this.addButton = new TextButton("ADD", 0.625, ClickGuiFriendsFriendActionComponent.J.B, ClickGuiFriendsFriendActionComponent.J.B.brighter(), null, 2.0f, 1.0f, 35.0, 14.0);
        this.addButton.setNormalTextColor(Color.WHITE);
        this.addButton.setDeriveTextColorFromBackground(false);
        this.addButton.setUppercase(true);
        this.addButton.setUseAlternateFont(true);
        this.addButton.w("Add friend");
        this.removeButton = new TextButton("REMOVE", 0.625, ClickGuiFriendsFriendActionComponent.J.d, ClickGuiFriendsFriendActionComponent.J.d.brighter(), null, 2.0f, 1.0f, 50.0, 14.0);
        this.removeButton.setNormalTextColor(Color.WHITE);
        this.removeButton.setDeriveTextColorFromBackground(false);
        this.removeButton.setUppercase(true);
        this.removeButton.setUseAlternateFont(true);
        this.removeButton.w("Remove friend");
        this.removeButton.setVisible(false);
        if (string2 != null) {
            this.badge = new ShapeIconComponent(IconShape.ROUNDED_RECT, string2, 10.0, 12.0, 4.0, 2.5f, BADGE_BACKGROUND_COLOR, BADGE_COLOR, 0.5);
            this.addChildren(this.avatar, this.nameLabel, this.addButton, this.removeButton, this.badge);
        } else {
            this.badge = null;
            this.addChildren(this.avatar, this.nameLabel, this.addButton, this.removeButton);
        }
    }


    public String getPlayerName() {
        return this.playerName;
    }

    public void setRemovePrimaryClickListener(@NotNull GuiClickListener guiClickListener) {
        this.removeListener = guiClickListener;
        this.removeButton.addClickListener(this::handleRemoveClick);
    }

    static {
        NORMAL_BACKGROUND_COLOR = ClickGuiFriendsFriendActionComponent.J.m;
        HOVER_BACKGROUND_COLOR = new Color(34, 33, 34);
        TEXT_COLOR = ClickGuiFriendsFriendActionComponent.J.A;
        HOVER_TEXT_COLOR = ClickGuiFriendsFriendActionComponent.J.f;
        BADGE_BACKGROUND_COLOR = new Color(98, 197, 84, 20);
        BADGE_COLOR = new Color(98, 197, 84);
    }
}

