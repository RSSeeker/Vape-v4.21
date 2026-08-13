package gg.vape.friend.ui;

import gg.vape.friend.ui.OnlineFriendListEntry;
import gg.vape.ui.click.component.GuiClickListener;

public class OnlineFriendListEntryOpenPopupClickHandler
implements GuiClickListener {
    private final OnlineFriendListEntry entry;

    @Override
    public void onPrimaryClick() {
        OnlineFriendListEntry.openPopup(this.entry);
    }

    public OnlineFriendListEntryOpenPopupClickHandler(OnlineFriendListEntry entry) {
        this.entry = entry;
    }
}
