package gg.vape.ui.click.frame.impl.main;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.LabeledTextInputComponent;
import gg.vape.ui.click.frame.impl.main.ClickGuiFriendsPage;

public class ClickGuiFriendsNameInputListener
extends LabeledTextInputComponent {
    final ClickGuiFriendsPage page;

    @Override
    public void setText(String text) {
        super.setText(text);
        ClickGuiFriendsPage.setSearchQuery(this.page, text);
        ClientSettings.UI_EXECUTOR.execute(this::refreshResults);
    }

    private void refreshResults() {
        ClickGuiFriendsPage.refreshSearchResults(this.page);
    }

    public ClickGuiFriendsNameInputListener(ClickGuiFriendsPage clickGuiFriendsPage, String string) {
        super(string);
        this.page = clickGuiFriendsPage;
    }
}
