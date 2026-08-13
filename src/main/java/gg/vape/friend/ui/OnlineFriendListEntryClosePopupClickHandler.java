package gg.vape.friend.ui;

import gg.vape.friend.ui.OnlineFriendListEntry;
import gg.vape.ui.click.component.GuiClickListener;

public class OnlineFriendListEntryClosePopupClickHandler
implements GuiClickListener {
    private final OnlineFriendListEntry entry;

    @Override
    public void onPrimaryClick() {
        OnlineFriendListEntry.closePopup(this.entry);
    }

    public OnlineFriendListEntryClosePopupClickHandler(OnlineFriendListEntry entry) {
        this.entry = entry;
    }
}
