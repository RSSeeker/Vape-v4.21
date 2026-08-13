package gg.vape.friend.ui;

import gg.vape.friend.ui.PartyFriendRowComponent;
import gg.vape.ui.click.component.TextLabelComponent;
import java.awt.Color;

public class PartyFriendFixedTextLabelComponent
extends TextLabelComponent {
    private final PartyFriendRowComponent row;

    public PartyFriendFixedTextLabelComponent(PartyFriendRowComponent partyFriendRowComponent, String string, double d, double d2, double d3, double d4, boolean bl, boolean bl2, Color color) {
        super(string, d, d2, d3, d4, bl, bl2, color);
        this.row = partyFriendRowComponent;
    }

    @Override
    public double x() {
        return 68.0;
    }
}
