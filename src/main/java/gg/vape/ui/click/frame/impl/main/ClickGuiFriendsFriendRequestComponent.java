package gg.vape.ui.click.frame.impl.main;

import gg.vape.friend.ExternalFriend;
import gg.vape.friend.Friend;
import gg.vape.friend.FriendEntry;
import gg.vape.friend.OnlineFriend;
import gg.vape.friend.OnlineStatus;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.MouseButton;
import gg.vape.ui.click.animation.ColorAnimation;
import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.component.TruncatedTextComponent;
import gg.vape.ui.click.component.gui.InteractiveComponent;
import gg.vape.ui.click.frame.impl.main.ClickGuiFriendsRequestActionComponent;
import gg.vape.ui.click.frame.impl.main.ClickGuiFriendsRequestRemoveComponent;
import gg.vape.ui.click.frame.impl.main.ClickGuiFriendsRequestTextComponent;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ClickGuiFriendsFriendRequestComponent
extends InteractiveComponent {
    private static final Color HOVER_TEXT_COLOR;
    private static final Color REMOVE_COLOR;
    private static final Color SETTINGS_HOVER_COLOR;
    private static final Color HOVER_BACKGROUND_COLOR;
    private static final Color SETTINGS_COLOR;
    private static final Color TEXT_COLOR;
    private static final Color NORMAL_BACKGROUND_COLOR;
    private static final Color REMOVE_HOVER_COLOR;
    private final FriendEntry friendEntry;
    private final TruncatedTextComponent nameLabel;
    private final ClickGuiFriendsRequestTextComponent statusIcon;
    private final ColorAnimation backgroundAnimation;
    private final ClickGuiFriendsRequestActionComponent removeButton;
    private final ClickGuiFriendsRequestRemoveComponent settingsButton;

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
        if (guiMouseEvent.getAction() == MouseButton.RIGHT_CLICK && !this.getClickCooldown().isCoolingDown()) {
            this.settingsButton.dispatchPrimaryClick();
            this.getClickCooldown().setActive(true);
            return;
        }
        if (guiMouseEvent.getAction() == MouseButton.LEFT_CLICK && !this.getClickCooldown().isCoolingDown()) {
            if (this.removeButton.V$src$Z$1xhop3l() && this.removeButton.i(guiMouseEvent.getX(), guiMouseEvent.getY())) {
                return;
            }
            if (this.settingsButton.V$src$Z$1xhop3l() && this.settingsButton.i(guiMouseEvent.getX(), guiMouseEvent.getY())) {
                return;
            }
            this.friendEntry.setTargeted(!this.friendEntry.isTargeted());
            this.getClickCooldown().setActive(true);
            return;
        }
        super.g(guiMouseEvent);
    }

    @Override
    public void H() {
        super.H();
        boolean bl = this.w$src$Z$e457mb();
        this.backgroundAnimation.u(bl);
        GuiRenderPrimitives.B(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), this.backgroundAnimation.getInterpolatedColor(), 3.0f);
        this.statusIcon.updateStyle();
        double d = this.G$src$D$1b2f02a();
        double d2 = this.n();
        double d3 = this.L();
        double d4 = this.A();
        this.statusIcon.K(d + 8.0);
        this.statusIcon.S(d2 + (d3 - 6.0) / 2.0);
        this.statusIcon.o(6.0);
        this.statusIcon.Y(6.0);
        double d5 = d + d4 - 8.0 - 10.0;
        double d6 = d2 + (d3 - 10.0) / 2.0;
        this.settingsButton.K(d5);
        this.settingsButton.S(d6);
        this.settingsButton.o(10.0);
        this.settingsButton.Y(10.0);
        this.settingsButton.setVisible(true);
        double d7 = d5 - 4.0 - 9.0;
        double d8 = d2 + (d3 - 9.0) / 2.0;
        this.removeButton.K(d7);
        this.removeButton.S(d8);
        this.removeButton.o(9.0);
        this.removeButton.Y(9.0);
        this.removeButton.setVisible(bl);
        double d9 = d + 20.0;
        double d10 = d7 - 6.0;
        double d11 = Math.max(0.0, d10 - d9);
        this.nameLabel.K(d9);
        this.nameLabel.S(d2);
        this.nameLabel.o(d11);
        this.nameLabel.Y(d3);
        this.nameLabel.setMaxWidth(d11);
        this.nameLabel.setText(this.getDisplayName(bl));
        this.nameLabel.setTextColor(bl ? HOVER_TEXT_COLOR : TEXT_COLOR);
        String string = this.getDetailsText(bl);
        this.nameLabel.setAdditionalTooltipText(string.isEmpty() ? "" : string);
    }

    static Color getRemoveColor() {
        return REMOVE_COLOR;
    }

    private boolean hasAlias() {
        if (this.friendEntry instanceof Friend) {
            Friend friend = (Friend)this.friendEntry;
            return !ClickGuiFriendsFriendRequestComponent.firstNonEmpty(friend.getAlias(), "").equalsIgnoreCase(friend.getName());
        }
        String string = ClickGuiFriendsFriendRequestComponent.firstNonEmpty(this.friendEntry.getDisplayName(), "");
        String string2 = ClickGuiFriendsFriendRequestComponent.firstNonEmpty(this.friendEntry.getName(), "");
        return !string.isEmpty() && !string.equalsIgnoreCase(string2);
    }

    public ClickGuiFriendsFriendRequestComponent(@NotNull FriendEntry friendEntry) {
        this.getClass();
        this.backgroundAnimation = new ColorAnimation(0.15, NORMAL_BACKGROUND_COLOR, HOVER_BACKGROUND_COLOR);
        this.setPropagateMouseEvents(true);
        this.friendEntry = friendEntry;
        this.Y(22.0);
        this.setShowDisabledOverlay(false);
        this.statusIcon = new ClickGuiFriendsRequestTextComponent(this, null);
        this.nameLabel = new TruncatedTextComponent(this.getDisplayName(false), "...", 0.0, 0.75, TEXT_COLOR, false);
        this.nameLabel.setShadowEnabled(false);
        this.nameLabel.setAdditionalTooltipText("");
        this.removeButton = new ClickGuiFriendsRequestActionComponent(this, null);
        this.removeButton.w("Remove friend");
        this.settingsButton = new ClickGuiFriendsRequestRemoveComponent(this, null);
        this.settingsButton.w("Friend settings");
        this.addChildren(this.statusIcon, this.nameLabel, this.settingsButton, this.removeButton);
    }

    private String getDetailsText(boolean bl) {
        FriendEntry friendEntry;
        Object object;
        if (bl) {
            return "";
        }
        if (this.friendEntry instanceof ExternalFriend && (object = ((ExternalFriend)(friendEntry = (ExternalFriend)this.friendEntry)).getOnlineFriend()) != null) {
            OnlineStatus onlineStatus;
            String string;
            StringBuilder stringBuilder = new StringBuilder();
            String string2 = ((OnlineFriend)object).getDisplayName();
            if (string2 != null && !string2.isEmpty()) {
                stringBuilder.append(string2);
            }
            if ((string = ((OnlineFriend)object).getMinecraftUsername()) != null && !string.isEmpty() && !string.equals(string2)) {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append('\n');
                }
                stringBuilder.append(string);
            }
            if ((onlineStatus = ((OnlineFriend)object).getStatus()) != null) {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append('\n');
                }
                stringBuilder.append(onlineStatus.getDisplayName());
            }
            return stringBuilder.toString();
        }
        if (this.friendEntry instanceof Friend) {
            friendEntry = (Friend)this.friendEntry;
            object = ((Friend)friendEntry).getAlias();
            String string = ((Friend)friendEntry).getName();
            if (object != null && !((String)object).equals(string)) {
                return "*" + (String)object + "\n" + string;
            }
        }
        return "";
    }

    public FriendEntry getFriendEntry() {
        return this.friendEntry;
    }

    static Color getSettingsHoverColor() {
        return SETTINGS_HOVER_COLOR;
    }


    static Color getSettingsColor() {
        return SETTINGS_COLOR;
    }

    public void setSettingsPrimaryClickListener(@NotNull GuiClickListener guiClickListener) {
        this.settingsButton.addClickListener(guiClickListener);
    }

    static Color getRemoveHoverColor() {
        return REMOVE_HOVER_COLOR;
    }

    static {
        NORMAL_BACKGROUND_COLOR = ClickGuiFriendsFriendRequestComponent.J.m;
        HOVER_BACKGROUND_COLOR = new Color(34, 33, 34);
        TEXT_COLOR = ClickGuiFriendsFriendRequestComponent.J.A;
        HOVER_TEXT_COLOR = ClickGuiFriendsFriendRequestComponent.J.f;
        REMOVE_COLOR = ClickGuiFriendsFriendRequestComponent.J.d;
        REMOVE_HOVER_COLOR = ClickGuiFriendsFriendRequestComponent.J.c;
        SETTINGS_COLOR = ClickGuiFriendsFriendRequestComponent.J.W;
        SETTINGS_HOVER_COLOR = ClickGuiFriendsFriendRequestComponent.J.f;
    }

    public boolean isBlatantMod() {
        return this.friendEntry.isTargeted();
    }

    private static String firstNonEmpty(@Nullable String string, @Nullable String string2) {
        if (string != null && !string.isEmpty()) {
            return string;
        }
        return string2 != null ? string2 : "";
    }

    private String getDisplayName(boolean bl) {
        Object object;
        Object object2;
        String string = this.friendEntry.getName();
        boolean bl2 = this.hasAlias();
        if (bl2) {
            Object object3;
            Object object4;
            if (bl) {
                return string;
            }
            if (this.friendEntry instanceof ExternalFriend && (object4 = ((ExternalFriend)(object3 = (ExternalFriend)this.friendEntry)).getOnlineFriend()) != null) {
                String string2 = ClickGuiFriendsFriendRequestComponent.firstNonEmpty(((OnlineFriend)object4).getDisplayName(), ((ExternalFriend)object3).getName());
                return "*" + string2;
            }
            if (this.friendEntry instanceof Friend) {
                object3 = (Friend)this.friendEntry;
                object4 = ClickGuiFriendsFriendRequestComponent.firstNonEmpty(((Friend)object3).getAlias(), ((Friend)object3).getName());
                return "*" + (String)object4;
            }
            object3 = ClickGuiFriendsFriendRequestComponent.firstNonEmpty(this.friendEntry.getDisplayName(), string);
            return "*" + (String)object3;
        }
        if (bl) {
            return string;
        }
        if (this.friendEntry instanceof ExternalFriend && (object2 = ((ExternalFriend)(object = (ExternalFriend)this.friendEntry)).getOnlineFriend()) != null) {
            String string3 = ClickGuiFriendsFriendRequestComponent.firstNonEmpty(((OnlineFriend)object2).getDisplayName(), ((ExternalFriend)object).getName());
            return string3;
        }
        if (this.friendEntry instanceof Friend) {
            object = (Friend)this.friendEntry;
            object2 = ClickGuiFriendsFriendRequestComponent.firstNonEmpty(((Friend)object).getAlias(), ((Friend)object).getName());
            return (String)object2;
        }
        object = ClickGuiFriendsFriendRequestComponent.firstNonEmpty(this.friendEntry.getDisplayName(), string);
        return (String)object;
    }

    public void setRemoveSecondaryClickListener(@Nullable GuiClickListener guiClickListener) {
        this.removeButton.setClickListener(guiClickListener);
    }

    public void setSettingsSecondaryClickListener(@Nullable GuiClickListener guiClickListener) {
        this.settingsButton.setClickListener(guiClickListener);
    }

    public void setRemovePrimaryClickListener(@NotNull GuiClickListener guiClickListener) {
        this.removeButton.addClickListener(guiClickListener);
    }

    static FriendEntry getFriendEntry(ClickGuiFriendsFriendRequestComponent component) {
        return component.friendEntry;
    }
}
