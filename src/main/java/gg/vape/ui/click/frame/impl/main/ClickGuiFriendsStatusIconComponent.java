package gg.vape.ui.click.frame.impl.main;

import gg.vape.friend.OnlineStatus;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.frame.impl.main.ClickGuiFriendsFriendListComponent;
import gg.vape.ui.click.frame.impl.main.ClickGuiFriendsRowActions;
import gg.vape.utils.render.GlImageTexture;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ImageRenderer;
import gg.vape.utils.render.RemoteImageTextureManager;
import java.awt.Color;
import org.jetbrains.annotations.Nullable;

final class ClickGuiFriendsStatusIconComponent
extends GuiComponent {
    private static final String OFFLINE_AVATAR_ASSET = "avatar offline@2x";
    private boolean emphasized;
    private double statusDotSize;
    final ClickGuiFriendsFriendListComponent owner;

    @Override
    public double x() {
        return 10.0;
    }

    @Nullable
    private GlImageTexture loadAvatarTexture() {
        if (!this.owner.getFriend().isVisible()) {
            return null;
        }
        String string = this.owner.getFriend().getMinecraftUsername();
        if (string == null || string.isEmpty()) {
            return null;
        }
        if (this.owner.getFriend().getStatus() == OnlineStatus.OFFLINE) {
            return null;
        }
        return RemoteImageTextureManager.getInstance().getTexture(string, 32);
    }

    @Override
    public void H() {
        double d = this.G$src$D$1b2f02a();
        double d2 = this.n();
        GlImageTexture glImageTexture = this.loadAvatarTexture();
        GuiRenderPrimitives.B(d, d2, 10.0, 10.0, ClickGuiFriendsStatusIconComponent.J.m, 5.0f);
        if (glImageTexture != null) {
            GuiRenderPrimitives.u((float)d, (float)d2, 10.0f, 0.8f, Color.WHITE, glImageTexture);
        } else {
            ImageRenderer.drawImage(Color.WHITE, (float)d, (float)d2, OFFLINE_AVATAR_ASSET, 10.0f, 10.0f, false);
        }
        double d3 = 10.0;
        Color color = this.emphasized ? ClickGuiFriendsStatusIconComponent.J.M : ClickGuiFriendsStatusIconComponent.J.E;
        GuiRenderPrimitives.m((float)d, (float)d2, 10.0f, 1.0f, 0.8f, color);
        OnlineStatus onlineStatus = this.owner.getFriend().getStatus();
        if (onlineStatus != null) {
            Color color2 = onlineStatus.getColor();
            double d4 = d + 10.0 - this.statusDotSize;
            double d5 = d2 + 10.0 - this.statusDotSize;
            GuiRenderPrimitives.V((float)(d4 - 1.0), (float)(d5 - 1.0), (float)(this.statusDotSize + 2.0), 0.8f, ClickGuiFriendsStatusIconComponent.J.m);
            GuiRenderPrimitives.V((float)d4, (float)d5, (float)this.statusDotSize, 0.8f, color2);
        }
    }

    @Override
    public void setHovered(boolean bl) {
        this.emphasized = bl;
    }


    ClickGuiFriendsStatusIconComponent(ClickGuiFriendsFriendListComponent clickGuiFriendsFriendListComponent, ClickGuiFriendsRowActions clickGuiFriendsRowActions) {
        this(clickGuiFriendsFriendListComponent);
    }

    @Override
    public double C() {
        return 10.0;
    }

    private ClickGuiFriendsStatusIconComponent(ClickGuiFriendsFriendListComponent clickGuiFriendsFriendListComponent) {
        this.owner = clickGuiFriendsFriendListComponent;
        this.statusDotSize = 5.0;
        this.setAcceptsMouseInput(false);
        this.setShowDisabledOverlay(false);
    }

    public void setStatusDotSize(double size) {
        this.statusDotSize = size;
    }
}

