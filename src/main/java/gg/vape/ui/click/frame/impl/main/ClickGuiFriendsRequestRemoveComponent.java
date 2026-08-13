package gg.vape.ui.click.frame.impl.main;

import gg.vape.ui.click.animation.ColorAnimation;
import gg.vape.ui.click.component.gui.InteractiveComponent;
import gg.vape.ui.click.frame.impl.main.ClickGuiFriendsFriendRequestComponent;
import gg.vape.ui.click.frame.impl.main.ClickGuiFriendsRequestStatusComponent;
import gg.vape.utils.render.ImageRenderer;

final class ClickGuiFriendsRequestRemoveComponent
extends InteractiveComponent {
    private static final String SETTINGS_ICON = "newsettings";
    final ClickGuiFriendsFriendRequestComponent owner;
    private final ColorAnimation colorAnimation;

    private ClickGuiFriendsRequestRemoveComponent(ClickGuiFriendsFriendRequestComponent clickGuiFriendsFriendRequestComponent) {
        this.owner = clickGuiFriendsFriendRequestComponent;
        this.colorAnimation = new ColorAnimation(0.15, ClickGuiFriendsFriendRequestComponent.getSettingsColor(), ClickGuiFriendsFriendRequestComponent.getSettingsHoverColor());
        this.o(10.0);
        this.Y(10.0);
        this.setShowDisabledOverlay(false);
    }

    ClickGuiFriendsRequestRemoveComponent(ClickGuiFriendsFriendRequestComponent clickGuiFriendsFriendRequestComponent, ClickGuiFriendsRequestStatusComponent clickGuiFriendsRequestStatusComponent) {
        this(clickGuiFriendsFriendRequestComponent);
    }

    @Override
    public double C() {
        return 10.0;
    }

    @Override
    public double x() {
        return 10.0;
    }

    @Override
    public void H() {
        this.colorAnimation.u(this.w$src$Z$e457mb());
        double d = this.G$src$D$1b2f02a() + (this.A() - 5.0) / 2.0;
        double d2 = this.n() + (this.L() - 5.0) / 2.0;
        ImageRenderer.drawImage(this.colorAnimation.getInterpolatedColor(), (float)d, (float)d2, SETTINGS_ICON, 5.0f, 5.0f, false);
    }
}
