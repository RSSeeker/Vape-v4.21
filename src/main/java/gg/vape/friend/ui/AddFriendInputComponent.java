package gg.vape.friend.ui;

import gg.vape.friend.ui.AddFriendInputPanel;
import gg.vape.ui.click.component.input.CompactTextInputComponent;

class AddFriendInputComponent
extends CompactTextInputComponent {
    private final AddFriendInputPanel panel;

    @Override
    public void submit() {
        super.submit();
        AddFriendInputPanel.submitInput(this.panel);
    }

    AddFriendInputComponent(AddFriendInputPanel panel, String placeholder) {
        super(placeholder);
        this.panel = panel;
    }
}
