package gg.vape.ui.click.frame.impl.main;

import gg.vape.friend.FriendEntry;
import gg.vape.friend.ui.OnlineFriendUiHelper;
import gg.vape.ui.click.component.value.BooleanToggleComponent;

public class ClickGuiFriendsModeToggleComponent
extends BooleanToggleComponent {
    final FriendEntry friendEntry;

    public ClickGuiFriendsModeToggleComponent(String label, double width, FriendEntry friendEntry) {
        super(label, width);
        this.friendEntry = friendEntry;
    }

    @Override
    public void synchronizeAnimationsImmediately() {
        if (this.friendEntry.isTargeted()) {
            this.getKnobPositionAnimation().C();
        } else {
            this.getKnobPositionAnimation().O();
        }
    }

    @Override
    public boolean isOn() {
        return this.friendEntry.isTargeted();
    }

    @Override
    public void toggle() {
        this.friendEntry.setTargeted(!this.friendEntry.isTargeted());
        this.synchronizeAnimationsImmediately();
        OnlineFriendUiHelper.refreshMinecraftFriends();
    }

}
