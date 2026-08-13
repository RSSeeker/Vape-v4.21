package gg.vape.friend.ui;

import gg.vape.friend.ui.AddFriendInputPanel;
import gg.vape.friend.ui.FriendEntriesPanel;
import gg.vape.ui.click.component.PanelComponent;

public class FriendManagementPanel
extends PanelComponent {
    private final AddFriendInputPanel addFriendPanel = new AddFriendInputPanel();
    private static final String LAYOUT_MODE = "wrap, spanWidth";
    private final FriendEntriesPanel entriesPanel = new FriendEntriesPanel();

    public FriendEntriesPanel getEntriesPanel() {
        return this.entriesPanel;
    }

    public FriendManagementPanel() {
        super(104.0, 135.0);
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M(LAYOUT_MODE);
        this.addChildren(this.addFriendPanel, this.entriesPanel);
    }

    @Override
    public void c() {
        super.c();
    }
}
