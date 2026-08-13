package gg.vape.friend.ui;

import gg.vape.friend.ui.FriendRequestRow;
import gg.vape.ui.click.component.TruncatedTextComponent;
import java.awt.Color;

public class FriendRequestNameTextComponent
extends TruncatedTextComponent {
    final FriendRequestRow owner;

    public FriendRequestNameTextComponent(FriendRequestRow friendRequestRow, String text, String ellipsis, double maxWidth, double scale, Color color, boolean centered) {
        super(text, ellipsis, maxWidth, scale, color, centered);
        this.owner = friendRequestRow;
    }

    @Override
    public double C() {
        return 16.0;
    }
}
