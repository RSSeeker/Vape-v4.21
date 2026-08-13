package gg.vape.friend.ui;

import gg.vape.friend.FriendEntry;
import gg.vape.ui.click.component.gui.TextButton;
import gg.vape.ui.click.component.input.TrailingActionTextInputComponent;
import gg.vape.ui.click.frame.impl.main.ClickGuiFriendsPage;
import java.util.List;

public class FriendAliasEditInputComponent
extends TrailingActionTextInputComponent {
    final String[] savedAlias;
    final ClickGuiFriendsPage friendsPage;
    final TextButton cancelButton;
    final TextButton saveButton;
    final FriendEntry friendEntry;

    public FriendAliasEditInputComponent(ClickGuiFriendsPage clickGuiFriendsPage, String string, List list, String[] stringArray, TextButton textButton, TextButton textButton2, FriendEntry friendEntry) {
        super(string, list);
        this.friendsPage = clickGuiFriendsPage;
        this.savedAlias = stringArray;
        this.saveButton = textButton;
        this.cancelButton = textButton2;
        this.friendEntry = friendEntry;
    }


    @Override
    public void submit() {
        super.submit();
        ClickGuiFriendsPage.updateFriendAlias(this.friendsPage, this.friendEntry, this.getText());
        this.savedAlias[0] = this.getText();
        this.saveButton.setVisible(false);
        this.cancelButton.setVisible(false);
    }

    @Override
    public void setText(String text) {
        super.setText(text);
        boolean changed = !text.equals(this.savedAlias[0]);
        this.saveButton.setVisible(changed);
        this.cancelButton.setVisible(changed);
    }
}
