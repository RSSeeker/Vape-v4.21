package gg.vape.friend.ui;

import gg.vape.friend.ui.OnlineFriendCard;
import gg.vape.ui.click.GuiMouseListener;
import gg.vape.ui.click.MouseClickButton;
import java.awt.Point;

class OnlineFriendCardToggleDetailsMouseListener
implements GuiMouseListener {
    private final OnlineFriendCard card;

    @Override
    public void g(Point point, MouseClickButton mouseClickButton) {
        OnlineFriendCard.handleCardClick(this.card, mouseClickButton);
    }

    OnlineFriendCardToggleDetailsMouseListener(OnlineFriendCard onlineFriendCard) {
        this.card = onlineFriendCard;
    }
}
