package gg.vape.ui.click.frame.impl.main;

import gg.vape.ui.click.animation.ColorAnimation;
import gg.vape.ui.click.component.IconGlyphComponent;
import gg.vape.ui.click.component.SimpleTextLabelComponent;
import gg.vape.ui.click.component.TruncatedTextComponent;
import gg.vape.ui.click.component.gui.InteractiveComponent;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

public class ClickGuiFriendsFriendStatusComponent
extends InteractiveComponent {
    private static final double PREFERRED_WIDTH = 114.0;
    private static final Color AVATAR_LOWER_COLOR;
    private static final Color STATUS_DOT_BORDER_COLOR;
    private static final Color DEFAULT_PARTY_COLOR;
    private static final Color AVATAR_BACKGROUND_COLOR;
    private static final Color DEFAULT_STATUS_COLOR;
    private static final Color MENU_DOT_COLOR;
    private static final Color AVATAR_MIDDLE_COLOR;
    private Color statusColor;
    private Color partyIconColor;
    private boolean partyVisible;
    private double menuX;
    private final TruncatedTextComponent nameLabel;
    private double avatarX;
    private double menuY;
    private final ColorAnimation backgroundAnimation;
    private final SimpleTextLabelComponent statusLabel;
    private double avatarY;
    private final IconGlyphComponent partyIcon;

    private void updateLayout() {
        double d;
        double d2 = this.G$src$D$1b2f02a();
        double d3 = this.n();
        double d4 = this.A();
        double d5 = this.L();
        this.avatarX = d = d2 + 4.0;
        this.avatarY = d3 + (d5 - 10.0) / 2.0;
        double d6 = 6.0;
        if (this.partyVisible) {
            d6 += 12.0;
        }
        double d7 = d4 - 4.0 - d6 + d2;
        double d8 = this.avatarX + 10.0 + 4.0;
        double d9 = Math.max(0.0, d7 - d8);
        double d10 = 16.0;
        double d11 = d3 + (d5 - 16.0) / 2.0;
        this.nameLabel.K(d8);
        this.nameLabel.S(d11);
        this.nameLabel.o(d9);
        this.nameLabel.Y(8.0);
        this.nameLabel.setMaxWidth(d9);
        this.statusLabel.K(d8);
        this.statusLabel.S(d11 + 8.0 + 1.0);
        this.statusLabel.o(d9);
        this.statusLabel.Y(7.0);
        this.menuX = d7 + (this.partyVisible ? 12.0 : 0.0);
        this.menuY = d3 + (d5 - 6.0) / 2.0;
        if (this.partyVisible) {
            double d12 = d7;
            double d13 = d3 + (d5 - 6.0) / 2.0;
            this.partyIcon.K(d12);
            this.partyIcon.S(d13);
            this.partyIcon.o(6.0);
            this.partyIcon.Y(6.0);
        }
    }

    public void setStatusColor(Color color) {
        if (color != null) {
            this.statusColor = color;
        }
    }

    public void setPartyIconColor(Color color) {
        if (color != null) {
            this.partyIconColor = color;
            this.partyIcon.setColor(color);
        }
    }

    public void setPartyVisible(boolean bl) {
        this.partyVisible = bl;
        this.partyIcon.setVisible(bl);
        this.partyIcon.setColor(this.partyIconColor);
    }

    @Override
    public void H() {
        this.backgroundAnimation.u(this.w$src$Z$e457mb());
        this.updateLayout();
        GuiRenderPrimitives.B(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), this.backgroundAnimation.getInterpolatedColor(), 3.0f);
        this.renderAvatar();
        this.renderStatusDot();
        this.updatePartyIconVisibility();
        this.renderMenuDots();
    }

    private void renderAvatar() {
        GuiRenderPrimitives.V(this.avatarX, this.avatarY, 10.0, 0.75, AVATAR_BACKGROUND_COLOR);
        double d = 4.2;
        double d2 = this.avatarX + 2.5;
        double d3 = this.avatarY + 1.5;
        GuiRenderPrimitives.V(d2, d3, 4.2, 0.75, AVATAR_MIDDLE_COLOR);
        double d4 = 6.0;
        double d5 = this.avatarX + 2.0;
        double d6 = this.avatarY + 4.5;
        GuiRenderPrimitives.V(d5, d6, 6.0, 0.75, AVATAR_LOWER_COLOR);
    }

    public static double getPreferredWidth() {
        return PREFERRED_WIDTH;
    }

    private void renderStatusDot() {
        double d = 5.0;
        double d2 = this.avatarX + 10.0 - 5.0 + 2.5;
        double d3 = this.avatarY + 10.0 - 5.0 + 2.5;
        GuiRenderPrimitives.V(d2, d3, 5.0, 0.75, STATUS_DOT_BORDER_COLOR);
        GuiRenderPrimitives.V(d2 + 0.5, d3 + 0.5, 4.0, 0.75, this.statusColor);
    }


    private void renderMenuDots() {
        double d = this.menuX + 3.0 - 0.75;
        double d2 = this.menuY + 3.0 - 1.5 - 1.0;
        for (int i = 0; i < 3; ++i) {
            GuiRenderPrimitives.V(d, d2, 1.5, 0.75, MENU_DOT_COLOR);
            d2 += 2.0;
        }
    }

    public ClickGuiFriendsFriendStatusComponent(String string, String string2, boolean bl) {
        this.getClass();
        this.backgroundAnimation = new ColorAnimation(0.15, ClickGuiFriendsFriendStatusComponent.J.i, ClickGuiFriendsFriendStatusComponent.J.a);
        this.nameLabel = new TruncatedTextComponent("Player", "...", 0.0, 0.75, ClickGuiFriendsFriendStatusComponent.J.A, false);
        this.statusLabel = new SimpleTextLabelComponent("Offline", 0.625, ClickGuiFriendsFriendStatusComponent.J.C);
        this.partyIcon = new IconGlyphComponent("party@2x", 6.0f, 6.0f, DEFAULT_PARTY_COLOR);
        this.statusColor = DEFAULT_STATUS_COLOR;
        this.partyIconColor = DEFAULT_PARTY_COLOR;
        this.o(PREFERRED_WIDTH);
        this.Y(20.0);
        this.setShowDisabledOverlay(false);
        this.setPropagateMouseEvents(true);
        this.backgroundAnimation.O();
        this.nameLabel.setText(string);
        this.nameLabel.setTextColor(ClickGuiFriendsFriendStatusComponent.J.A);
        this.nameLabel.setMaxWidth(0.0);
        this.statusLabel.setText(string2);
        this.statusLabel.setTextColor(ClickGuiFriendsFriendStatusComponent.J.C);
        this.statusLabel.setOffsetX(0.0f);
        this.statusLabel.setOffsetY(0.0f);
        this.statusLabel.setExtraHeight(0);
        this.partyIcon.setVisible(false);
        this.partyIcon.setSnapToPixels(true);
        this.addChildren(this.nameLabel, this.statusLabel, this.partyIcon);
        this.setPartyVisible(bl);
    }

    public void setStatusText(String string) {
        this.statusLabel.setText(string);
    }

    private void updatePartyIconVisibility() {
        this.partyIcon.setVisible(this.partyVisible);
    }

    static {
        AVATAR_BACKGROUND_COLOR = new Color(54, 53, 54);
        AVATAR_MIDDLE_COLOR = new Color(124, 123, 124);
        AVATAR_LOWER_COLOR = new Color(89, 88, 89);
        STATUS_DOT_BORDER_COLOR = new Color(31, 30, 31);
        DEFAULT_STATUS_COLOR = new Color(98, 197, 84);
        MENU_DOT_COLOR = new Color(163, 163, 163);
        DEFAULT_PARTY_COLOR = new Color(98, 197, 84);
    }

    public void setPlayerName(String string) {
        this.nameLabel.setText(string);
    }
}

