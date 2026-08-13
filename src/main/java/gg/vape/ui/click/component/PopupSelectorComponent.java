package gg.vape.ui.click.component;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.gui.InteractiveComponent;
import gg.vape.ui.click.frame.AnchoredPopupFrame;
import gg.vape.ui.click.frame.FrameComponent;
import org.jetbrains.annotations.Nullable;

public class PopupSelectorComponent
extends InteractiveComponent {
    protected final FrameComponent popupContent;
    @Nullable
    protected AnchoredPopupFrame popupFrame;
    private static String legacyMarker;

    static {
        if (PopupSelectorComponent.getLegacyMarker() != null) {
            PopupSelectorComponent.setLegacyMarker("ECQHRb");
        }
    }

    public FrameComponent getPopupContent() {
        return this.popupContent;
    }

    public static String getLegacyMarker() {
        return legacyMarker;
    }

    private void openPopup() {
        this.popupFrame = ClientSettings.createPopup(this, this.popupContent, AnchoredPopupFrame.class);
        this.popupFrame.t(true);
    }

    @Nullable
    public AnchoredPopupFrame getPopupFrame() {
        return this.popupFrame;
    }

    public PopupSelectorComponent(FrameComponent popupContent) {
        this.popupContent = popupContent;
        this.setClickListener(this::openPopup);
    }

    public static void setLegacyMarker(String marker) {
        legacyMarker = marker;
    }
}
