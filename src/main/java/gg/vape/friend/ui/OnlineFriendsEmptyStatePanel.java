package gg.vape.friend.ui;

import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.WrappingTextLabelComponent;

public class OnlineFriendsEmptyStatePanel
extends PanelComponent {
    public OnlineFriendsEmptyStatePanel() {
        super(104.0, 130.0);
        WrappingTextLabelComponent wrappingTextLabelComponent = new WrappingTextLabelComponent("No friends added", 0.9, OnlineFriendsEmptyStatePanel.J.Z);
        wrappingTextLabelComponent.o(98.0);
        WrappingTextLabelComponent wrappingTextLabelComponent2 = new WrappingTextLabelComponent("Click the friend requests button to get started", 0.8, OnlineFriendsEmptyStatePanel.J.h);
        wrappingTextLabelComponent2.o(98.0);
        this.h(wrappingTextLabelComponent, "offsetY 30");
        this.h(wrappingTextLabelComponent2, "offsetY 45");
    }

    @Override
    public void c() {
        super.c();
    }
}

