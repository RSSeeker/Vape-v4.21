package gg.vape.friend.ui;

import gg.vape.friend.ui.PartyFriendRowComponent;
import gg.vape.ui.click.component.gui.AnimatedCenteredTextLabelComponent;
import java.awt.Color;

public class PartyFriendActionLabelComponent
extends AnimatedCenteredTextLabelComponent {
    private final PartyFriendRowComponent row;

    public PartyFriendActionLabelComponent(PartyFriendRowComponent partyFriendRowComponent, String string, Color color) {
        super(string, color);
        this.row = partyFriendRowComponent;
    }

    @Override
    public double x() {
        return this.getDefaultFontRenderer().N(this.getText()) + 3.0;
    }

    @Override
    public double C() {
        return 8.0;
    }
}
