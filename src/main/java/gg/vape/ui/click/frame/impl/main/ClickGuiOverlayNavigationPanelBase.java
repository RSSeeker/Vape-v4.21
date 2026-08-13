package gg.vape.ui.click.frame.impl.main;

import gg.vape.ui.click.frame.impl.main.ClickGuiSidecarPanelBase;
import org.jetbrains.annotations.Nullable;

public class ClickGuiOverlayNavigationPanelBase
extends ClickGuiSidecarPanelBase {
    public ClickGuiOverlayNavigationPanelBase(@Nullable String title, @Nullable String iconKey, @Nullable Runnable closeAction) {
        this.Y(20.0);
        if (title != null) {
            this.setTitle(title);
        }
        if (iconKey != null && !iconKey.isEmpty()) {
            this.setLeadingIconKey(iconKey);
        }
        this.getLeadingIcon().setHoverColor(null);
        this.setCloseAction(closeAction);
    }

}

