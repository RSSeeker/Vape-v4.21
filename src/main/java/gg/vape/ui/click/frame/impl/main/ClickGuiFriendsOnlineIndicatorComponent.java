package gg.vape.ui.click.frame.impl.main;

import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.frame.impl.main.ClickGuiFriendsFriendListComponent;
import gg.vape.ui.click.frame.impl.main.ClickGuiFriendsRowActions;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

final class ClickGuiFriendsOnlineIndicatorComponent
extends GuiComponent {
    final ClickGuiFriendsFriendListComponent owner;

    @Override
    public double x() {
        return 6.0;
    }

    private ClickGuiFriendsOnlineIndicatorComponent(ClickGuiFriendsFriendListComponent clickGuiFriendsFriendListComponent) {
        this.owner = clickGuiFriendsFriendListComponent;
        this.setAcceptsMouseInput(false);
        this.setShowDisabledOverlay(false);
    }

    @Override
    public void H() {
        double d = this.G$src$D$1b2f02a();
        double d2 = this.n() + (this.L() - 6.0) / 2.0;
        Color color = new Color(37, 36, 37);
        GuiRenderPrimitives.B(d, d2, 6.0, 6.0, color, 1.5f);
        GuiRenderPrimitives.P(d, d2, 6.0, 6.0, ClickGuiFriendsOnlineIndicatorComponent.J.y, 1.5f, 0.8f, 1.0f);
        double d3 = d + 1.5;
        double d4 = 3.0;
        GuiRenderPrimitives.C(d3, d2 + 1.5, 3.0, 1.0, ClickGuiFriendsFriendListComponent.getIndicatorLineColor());
        GuiRenderPrimitives.C(d3, d2 + 3.0, 3.0, 1.0, ClickGuiFriendsFriendListComponent.getIndicatorLineColor());
        GuiRenderPrimitives.C(d3, d2 + 4.5, 3.0, 1.0, ClickGuiFriendsFriendListComponent.getIndicatorLineColor());
    }

    @Override
    public double C() {
        return 7.0;
    }

    ClickGuiFriendsOnlineIndicatorComponent(ClickGuiFriendsFriendListComponent clickGuiFriendsFriendListComponent, ClickGuiFriendsRowActions clickGuiFriendsRowActions) {
        this(clickGuiFriendsFriendListComponent);
    }
}
