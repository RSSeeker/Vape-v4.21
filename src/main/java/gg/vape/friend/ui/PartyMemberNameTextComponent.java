package gg.vape.friend.ui;

import gg.vape.friend.ui.PartyMemberRow;
import gg.vape.ui.click.component.TruncatedTextComponent;
import java.awt.Color;

public class PartyMemberNameTextComponent
extends TruncatedTextComponent {
    private final PartyMemberRow row;

    public PartyMemberNameTextComponent(PartyMemberRow partyMemberRow, String string, String string2, double d, double d2, Color color, boolean bl) {
        super(string, string2, d, d2, color, bl);
        this.row = partyMemberRow;
    }

    @Override
    public double C() {
        return 8.0;
    }
}
