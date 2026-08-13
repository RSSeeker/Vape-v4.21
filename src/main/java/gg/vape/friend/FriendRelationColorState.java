package gg.vape.friend;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GuiComponentContract;
import java.awt.Color;

public enum FriendRelationColorState {
    FRIEND(ClientSettings.INSTANCE.getAccentColor()),
    SYNCED(GuiComponentContract.J.T),
    ENEMY(GuiComponentContract.J.d);

    Color color;
    private static final /* synthetic */ FriendRelationColorState[] VALUES;

    private FriendRelationColorState(Color color) {
        this.color = color;
    }

    static {
        String[] relationNames = new String[]{"ENEMY", "FRIEND", "SYNCED"};



        VALUES = new FriendRelationColorState[]{FRIEND, SYNCED, ENEMY};
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
