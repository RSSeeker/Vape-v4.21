package gg.vape.friend.ui;

import gg.vape.Vape;
import gg.vape.friend.Friend;
import gg.vape.ui.click.component.TextInputComponentBase;

public class FriendSettingsAddFriendInputComponent
extends TextInputComponentBase {
    @Override
    public double C() {
        return 20.0;
    }

    @Override
    public void submit() {
        if (!this.hasNonBlankText()) {
            this.setText("");
            return;
        }
        String[] stringArray = this.getText().split(" ");
        String string = stringArray[0];
        String string2 = stringArray.length > 1 ? stringArray[1] : stringArray[0];
        Vape.INSTANCE.getFriendManager().addFriend(new Friend(string, string2));
        this.setText("");
    }

    @Override
    public double getAvailableTextWidth() {
        return this.A() - 35.0;
    }

    public FriendSettingsAddFriendInputComponent(String string) {
        super(string);
        this.setShowDisabledOverlay(false);
        this.actionButtonColor = FriendSettingsAddFriendInputComponent.J.B;
    }


    @Override
    public double x() {
        return 110.0;
    }
}
