package gg.vape.ui.click.frame.impl.profile;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.GuiMouseListener;
import gg.vape.ui.click.MouseClickButton;
import gg.vape.ui.click.frame.impl.profile.PublicProfileFilterTokenComponent;
import gg.vape.ui.click.frame.impl.profile.PublicProfileFilterTokenSelectorComponent;
import java.awt.Point;
import java.util.concurrent.atomic.AtomicBoolean;

public class PublicProfileFilterTokenSelectorClickHandler
implements GuiMouseListener {
    private final PublicProfileFilterTokenSelectorComponent selector;
    private final AtomicBoolean clickPending;

    private void removeLastToken(AtomicBoolean atomicBoolean) {
        try {
            this.selector.removeLastToken();
        }
        finally {
            atomicBoolean.set(false);
        }
    }


    @Override
    public void g(Point point, MouseClickButton mouseClickButton) {
        if (this.selector.isOverflowed()) {
            if (this.selector.getOverflowSummary().w$src$Z$e457mb()) {
                this.clickPending.set(true);
                ClientSettings.UI_EXECUTOR.execute(() -> this.removeLastToken(this.clickPending));
            }
            return;
        }
        for (PublicProfileFilterTokenComponent publicProfileFilterTokenComponent : this.selector.getTokens()) {
            if (!publicProfileFilterTokenComponent.w$src$Z$e457mb()) continue;
            if (this.clickPending.get()) {
                return;
            }
            this.clickPending.set(true);
            ClientSettings.UI_EXECUTOR.execute(() -> this.removeToken(publicProfileFilterTokenComponent, this.clickPending));
            return;
        }
    }

    public PublicProfileFilterTokenSelectorClickHandler(PublicProfileFilterTokenSelectorComponent publicProfileFilterTokenSelectorComponent, AtomicBoolean atomicBoolean) {
        this.selector = publicProfileFilterTokenSelectorComponent;
        this.clickPending = atomicBoolean;
    }

    private void removeToken(PublicProfileFilterTokenComponent token, AtomicBoolean atomicBoolean) {
        try {
            this.selector.removeToken(token);
        }
        finally {
            atomicBoolean.set(false);
        }
    }
}

