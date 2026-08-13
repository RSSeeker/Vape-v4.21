package gg.vape.friend.ui;

import gg.vape.friend.OnlineFriend;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.utils.render.GlImageTexture;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.RemoteImageTextureManager;
import java.awt.Color;
import java.util.List;

public class OnlineFriendAvatarStackComponent
extends GuiComponent {
    private final List<OnlineFriend> friends;

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    @Override
    public void u() {
    }

    @Override
    public void H() {
        double friendCount = this.friends.size();
        double avatarSize = 8.0;
        double defaultSpacing = avatarSize + 1.0;
        double requiredWidth = friendCount * defaultSpacing;
        double remainingWidth = this.A() - requiredWidth;
        double spacing = defaultSpacing;
        double overlapAdjustment = remainingWidth / friendCount - avatarSize / 2.0 / friendCount;
        if (remainingWidth < 0.0) {
            spacing += overlapAdjustment;
        }
        float offsetX = 0.0f;
        int index = 0;
        while ((double)index < friendCount) {
            OnlineFriend friend = this.friends.get(index);
            GlImageTexture glImageTexture = RemoteImageTextureManager.getInstance().getTexture(friend.getMinecraftUsername(), 32);
            if (glImageTexture != null) {
                GuiRenderPrimitives.V((float)this.G$src$D$1b2f02a() + offsetX - 1.0f, (float)this.n() - 1.0f, (float)avatarSize + 2.0f, 1.0, OnlineFriendAvatarStackComponent.J.m);
                GuiRenderPrimitives.u((float)this.G$src$D$1b2f02a() + offsetX, (float)this.n(), (float)avatarSize, 1.0f, Color.WHITE, glImageTexture);
            }
            offsetX = (float)((double)offsetX + spacing);
            ++index;
        }
    }

    @Override
    public double x() {
        return 32.0;
    }

    @Override
    public double C() {
        return 8.0;
    }

    @Override
    public void I() {
    }


    @Override
    public void F() {
    }

    public OnlineFriendAvatarStackComponent(List<OnlineFriend> friends) {
        this.friends = friends;
    }
}

