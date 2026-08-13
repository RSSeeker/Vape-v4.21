package gg.vape.friend.ui;

import gg.vape.friend.ui.OnlineFriendListEntry;
import gg.vape.ui.click.component.GuiClickListener;

public class OnlineFriendListEntryOpenActionsPopupClickHandler
implements GuiClickListener {
    private final OnlineFriendListEntry entry;

    public OnlineFriendListEntryOpenActionsPopupClickHandler(OnlineFriendListEntry onlineFriendListEntry) {
        this.entry = onlineFriendListEntry;
    }

    @Override
    public void onPrimaryClick() {
        OnlineFriendListEntry.openPopup(this.entry);
    }
}
