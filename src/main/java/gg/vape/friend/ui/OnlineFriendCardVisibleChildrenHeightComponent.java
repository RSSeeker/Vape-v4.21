package gg.vape.friend.ui;

import gg.vape.friend.ui.OnlineFriendCard;
import gg.vape.ui.click.component.FlowLayoutComponent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.SpacerComponent;

class OnlineFriendCardVisibleChildrenHeightComponent
extends FlowLayoutComponent {
    final OnlineFriendCard onlineFriendCard;


    OnlineFriendCardVisibleChildrenHeightComponent(OnlineFriendCard onlineFriendCard, double width) {
        super(width);
        this.onlineFriendCard = onlineFriendCard;
    }

    @Override
    public double getVisibleChildrenWidth() {
        double totalWidth = 0.0;
        if (!OnlineFriendCard.getCompactActions(this.onlineFriendCard).V$src$Z$1xhop3l()) {
            return totalWidth;
        }
        for (GuiComponent child : OnlineFriendCard.getCompactActions(this.onlineFriendCard).f()) {
            if (child instanceof SpacerComponent || !child.V$src$Z$1xhop3l()) continue;
            totalWidth += child.A();
        }
        return totalWidth + 2.0;
    }
}
