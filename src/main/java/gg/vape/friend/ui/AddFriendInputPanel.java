package gg.vape.friend.ui;

import gg.vape.Vape;
import gg.vape.friend.Friend;
import gg.vape.friend.ui.AddFriendInputComponent;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.input.CompactTextInputComponent;
import gg.vape.ui.click.frame.FrameComponent;
import gg.vape.ui.click.layout.ComponentLayout;
import gg.vape.value.PlayerNameSuggestionProvider;

public class AddFriendInputPanel
extends FrameComponent {
    private static final String PLACEHOLDER = "Minecraft username";
    private final CompactTextInputComponent usernameInput = new AddFriendInputComponent(this, PLACEHOLDER);

    @Override
    public void Y() {
    }

    @Override
    public void c() {
        super.c();
    }


    static void submitInput(AddFriendInputPanel panel) {
        panel.addFriend();
    }

    @Override
    public void v() {
    }

    public AddFriendInputPanel() {
        ComponentLayout componentLayout = this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij();
        componentLayout.t(false);
        componentLayout.U(false);
        componentLayout.u(false);
        this.usernameInput.setSuggestionProvider(new PlayerNameSuggestionProvider());
        this.addChildren(this.usernameInput);
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    private void addFriend() {
        if (this.usernameInput.getText() == "") {
            return;
        }
        if (!this.usernameInput.hasNonBlankText()) {
            this.usernameInput.setText("");
            return;
        }
        String[] inputParts = this.usernameInput.getText().split(" ");
        String username = inputParts[0];
        String alias = inputParts.length > 1 ? inputParts[1] : inputParts[0];
        Vape.INSTANCE.getFriendManager().addFriend(new Friend(username, alias));
        this.usernameInput.setText("");
    }

    @Override
    public double x() {
        return 104.0;
    }

    @Override
    public double C() {
        return 22.0;
    }

    @Override
    public void V() {
    }
}
