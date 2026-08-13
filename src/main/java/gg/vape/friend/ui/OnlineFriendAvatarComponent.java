package gg.vape.friend.ui;

import gg.vape.Vape;
import gg.vape.friend.OnlineFriend;
import gg.vape.friend.PartyState;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.utils.render.GlImageTexture;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ImageRenderer;
import gg.vape.utils.render.RemoteImageTextureManager;
import java.awt.Color;

public class OnlineFriendAvatarComponent
extends GuiComponent {
    private static final String LEADER_ICON_RESOURCE = "leader@2x";
    private final OnlineFriend friend;
    private final double width;
    private final double height;

    @Override
    public double C() {
        return this.height;
    }

    @Override
    public void F() {
    }

    @Override
    public double x() {
        return this.width;
    }

    @Override
    public void H() {
        GlImageTexture glImageTexture = RemoteImageTextureManager.getInstance().getTexture(this.friend.getMinecraftUsername(), 32);
        if (glImageTexture != null) {
            GuiRenderPrimitives.u((float)this.G$src$D$1b2f02a(), (float)this.n(), (float)this.A(), 1.0f, Color.WHITE, glImageTexture);
            PartyState partyState = Vape.INSTANCE.getOnlineManager().getPartyManager().getCurrentParty();
            if (partyState != null && partyState.getLeader().equals(this.friend)) {
                ImageRenderer.drawImage(Color.WHITE, (float)this.G$src$D$1b2f02a() + 1.5f, (float)this.n() - 4.5f, LEADER_ICON_RESOURCE, 3.0f, 3.0f, false);
            }
        }
    }

    public OnlineFriendAvatarComponent(OnlineFriend friend, double width, double height) {
        this.friend = friend;
        this.width = width;
        this.height = height;
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    @Override
    public void I() {
    }


    @Override
    public void u() {
    }
}

