package gg.vape.ui.click.frame.impl.main;

import gg.vape.ui.click.animation.ColorAnimation;
import gg.vape.ui.click.component.gui.InteractiveComponent;
import gg.vape.ui.click.frame.impl.main.ClickGuiFriendsFriendRequestComponent;
import gg.vape.ui.click.frame.impl.main.ClickGuiFriendsRequestStatusComponent;
import gg.vape.utils.render.ImageRenderer;

final class ClickGuiFriendsRequestActionComponent
extends InteractiveComponent {
    private static final String TRASH_ICON = "newtrash";
    final ClickGuiFriendsFriendRequestComponent owner;
    private final ColorAnimation colorAnimation;

    ClickGuiFriendsRequestActionComponent(ClickGuiFriendsFriendRequestComponent clickGuiFriendsFriendRequestComponent, ClickGuiFriendsRequestStatusComponent clickGuiFriendsRequestStatusComponent) {
        this(clickGuiFriendsFriendRequestComponent);
    }

    @Override
    public double x() {
        return 9.0;
    }

    private ClickGuiFriendsRequestActionComponent(ClickGuiFriendsFriendRequestComponent clickGuiFriendsFriendRequestComponent) {
        this.owner = clickGuiFriendsFriendRequestComponent;
        this.colorAnimation = new ColorAnimation(0.15, ClickGuiFriendsFriendRequestComponent.getRemoveColor(), ClickGuiFriendsFriendRequestComponent.getRemoveHoverColor());
        this.o(9.0);
        this.Y(9.0);
        this.setShowDisabledOverlay(false);
    }

    @Override
    public double C() {
        return 9.0;
    }

    @Override
    public void H() {
        this.colorAnimation.u(this.w$src$Z$e457mb());
        double d = this.G$src$D$1b2f02a() + (this.A() - 4.5) / 2.0;
        double d2 = this.n() + (this.L() - 4.5) / 2.0;
        ImageRenderer.drawImage(this.colorAnimation.getInterpolatedColor(), (float)d, (float)d2, TRASH_ICON, 4.5f, 4.5f, false);
    }
}
