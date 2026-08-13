package gg.vape.ui.click.frame.impl.profile;

import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.frame.impl.profile.PublicProfileSearchFilterPanel;

class PublicProfileSelectedFilterPanel
extends PanelComponent {
    private final PublicProfileSearchFilterPanel searchPanel;

    @Override
    public void c() {
        this.searchPanel.renderFilterBackground();
        super.c();
    }

    PublicProfileSelectedFilterPanel(PublicProfileSearchFilterPanel publicProfileSearchFilterPanel, double d, double d2) {
        super(d, d2);
        this.searchPanel = publicProfileSearchFilterPanel;
    }
}
