package gg.vape.friend.ui;

import gg.vape.friend.FriendEntry;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.SelectableTextRowComponent;

public class FriendSettingsEntryRowComponent
extends SelectableTextRowComponent {
    private final FriendEntry friendEntry;

    @Override
    public boolean isSelected() {
        return this.friendEntry.isTargeted();
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
        this.friendEntry.setTargeted(!this.friendEntry.isTargeted());
    }

    @Override
    public void H() {
        String text = this.friendEntry.getName();
        if (!this.friendEntry.getDisplayName().equals(this.friendEntry.getName()) && !this.isHovered()) {
            text = "*" + this.friendEntry.getAlias();
        }
        this.setText(text);
        super.H();
    }


    public FriendSettingsEntryRowComponent(FriendEntry friendEntry) {
        super(FriendSettingsEntryRowComponent.J.B, friendEntry.getName());
        this.friendEntry = friendEntry;
    }

    public FriendEntry getFriendEntry() {
        return this.friendEntry;
    }
}

