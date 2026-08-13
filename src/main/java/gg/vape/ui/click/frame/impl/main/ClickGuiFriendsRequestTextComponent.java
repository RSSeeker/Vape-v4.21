package gg.vape.ui.click.frame.impl.main;

import gg.vape.friend.ExternalFriend;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.frame.impl.main.ClickGuiFriendsFriendRequestComponent;
import gg.vape.ui.click.frame.impl.main.ClickGuiFriendsRequestStatusComponent;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

final class ClickGuiFriendsRequestTextComponent
extends GuiComponent {
    private static final String SYNCED_ASSET = "synced@2x";
    final ClickGuiFriendsFriendRequestComponent owner;
    private String iconKey;
    private Color color;

    private ClickGuiFriendsRequestTextComponent(ClickGuiFriendsFriendRequestComponent clickGuiFriendsFriendRequestComponent) {
        this.owner = clickGuiFriendsFriendRequestComponent;
        this.color = ClickGuiFriendsRequestTextComponent.J.B;
        this.o(6.0);
        this.Y(6.0);
        this.setShowDisabledOverlay(false);
        this.setAcceptsMouseInput(false);
    }

    void updateStyle() {
        this.color = ClickGuiFriendsRequestTextComponent.J.B;
        this.iconKey = null;
        if (this.owner.getFriendEntry() instanceof ExternalFriend) {
            this.iconKey = SYNCED_ASSET;
            this.color = ClickGuiFriendsRequestTextComponent.J.T;
        }
    }

    @Override
    public double x() {
        return 6.0;
    }


    @Override
    public double C() {
        return 6.0;
    }

    @Override
    public void H() {
        if (this.owner.isBlatantMod()) {
            if (this.iconKey != null) {
                GuiRenderPrimitives.F(this.iconKey, this.G$src$D$1b2f02a() + 2.5, this.n() + 2.5, 5.0, 5.0, this.color);
            } else {
                GuiRenderPrimitives.V((float)this.G$src$D$1b2f02a(), (float)this.n(), 5.0, 0.5, this.color);
            }
        } else if (this.iconKey != null) {
            GuiRenderPrimitives.F(this.iconKey, this.G$src$D$1b2f02a() + 2.5, this.n() + 2.5, 5.0, 5.0, ClickGuiFriendsRequestTextComponent.J.W);
        } else {
            GuiRenderPrimitives.m((float)this.G$src$D$1b2f02a(), (float)this.n(), 5.0f, 1.0f, 0.5f, ClickGuiFriendsRequestTextComponent.J.W);
        }
    }

    ClickGuiFriendsRequestTextComponent(ClickGuiFriendsFriendRequestComponent clickGuiFriendsFriendRequestComponent, ClickGuiFriendsRequestStatusComponent clickGuiFriendsRequestStatusComponent) {
        this(clickGuiFriendsFriendRequestComponent);
    }

}

