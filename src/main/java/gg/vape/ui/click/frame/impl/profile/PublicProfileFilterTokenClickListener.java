package gg.vape.ui.click.frame.impl.profile;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.GuiMouseListener;
import gg.vape.ui.click.MouseClickButton;
import gg.vape.ui.click.component.layout.PaddedComponent;
import gg.vape.ui.click.frame.impl.profile.PublicProfileFilterTokenComponent;
import gg.vape.ui.click.frame.impl.profile.PublicProfileSearchFilterPanel;
import java.awt.Point;
import java.util.concurrent.atomic.AtomicBoolean;

class PublicProfileFilterTokenClickListener
implements GuiMouseListener {
    private final PublicProfileFilterTokenComponent token;
    private final PaddedComponent suggestion;
    private final PublicProfileSearchFilterPanel searchPanel;
    private final AtomicBoolean clickPending;


    PublicProfileFilterTokenClickListener(PublicProfileSearchFilterPanel searchPanel, AtomicBoolean clickPending, PaddedComponent suggestion, PublicProfileFilterTokenComponent token) {
        this.searchPanel = searchPanel;
        this.clickPending = clickPending;
        this.suggestion = suggestion;
        this.token = token;
    }

    @Override
    public void g(Point point, MouseClickButton uA) {
        if (this.clickPending.get()) {
            return;
        }
        this.clickPending.set(true);
        ClientSettings.UI_EXECUTOR.execute(this::selectSuggestion);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void selectSuggestion() {
        try {
            this.searchPanel.getSuggestionsPanel().removeChild(this.suggestion);
            this.searchPanel.getTokenSelector().addToken(this.token);
        }
        finally {
            this.clickPending.set(false);
        }
    }
}

