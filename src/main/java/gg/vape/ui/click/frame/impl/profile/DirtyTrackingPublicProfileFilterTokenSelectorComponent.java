package gg.vape.ui.click.frame.impl.profile;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.frame.impl.profile.PublicProfileFilterTokenSelectorComponent;
import gg.vape.ui.click.frame.impl.profile.PublicProfileSearchFilterPanel;

class DirtyTrackingPublicProfileFilterTokenSelectorComponent
extends PublicProfileFilterTokenSelectorComponent {
    private final PublicProfileSearchFilterPanel searchPanel;

    DirtyTrackingPublicProfileFilterTokenSelectorComponent(PublicProfileSearchFilterPanel publicProfileSearchFilterPanel, String string, Runnable runnable, double d, double d2, boolean bl, boolean bl2) {
        super(string, runnable, d, d2, bl, bl2);
        this.searchPanel = publicProfileSearchFilterPanel;
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
        this.searchPanel.setExpanded(true);
        super.g(guiMouseEvent);
    }
}
