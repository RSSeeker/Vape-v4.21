package gg.vape.ui.click.frame.impl.main;

import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.frame.impl.main.ClickGuiOverlayPlacement;
import gg.vape.ui.click.frame.impl.main.ClickGuiOverlaySpecBuilder;
import gg.vape.ui.click.frame.impl.main.ClickGuiOverlaySpecEntry;
import gg.vape.ui.click.frame.impl.main.ClickGuiOverlayTransitionMode;
import gg.vape.ui.click.frame.impl.main.ClickGuiSidecarPanelBase;
import java.util.function.Consumer;
import org.jetbrains.annotations.Nullable;

public final class ClickGuiOverlaySpec {
    @Nullable
    private final String sidecarIcon;
    private final String title;
    private final Consumer<PanelComponent> contentInitializer;
    @Nullable
    private final Double width;
    @Nullable
    private final Consumer<ClickGuiSidecarPanelBase> sidecarInitializer;
    @Nullable
    private final ClickGuiSidecarPanelBase sidecar;
    private final ClickGuiOverlayTransitionMode transitionMode;
    private final boolean backdropEnabled;
    private final ClickGuiOverlayPlacement placement;

    @Nullable
    public String getSidecarIcon() {
        return this.sidecarIcon;
    }

    @Nullable
    public Double getWidth() {
        return this.width;
    }

    public Consumer<PanelComponent> getContentInitializer() {
        return this.contentInitializer;
    }

    public ClickGuiOverlayPlacement getPlacement() {
        return this.placement;
    }

    @Nullable
    public ClickGuiSidecarPanelBase getSidecar() {
        return this.sidecar;
    }

    ClickGuiOverlaySpec(String string, ClickGuiSidecarPanelBase clickGuiSidecarPanelBase, String string2, Consumer consumer, Consumer consumer2, ClickGuiOverlayPlacement clickGuiOverlayPlacement, boolean bl, ClickGuiOverlayTransitionMode clickGuiOverlayTransitionMode, Double d, ClickGuiOverlaySpecEntry clickGuiOverlaySpecEntry) {
        this(string, clickGuiSidecarPanelBase, string2, consumer, consumer2, clickGuiOverlayPlacement, bl, clickGuiOverlayTransitionMode, d);
    }

    public String getTitle() {
        return this.title;
    }

    @Nullable
    public Consumer<ClickGuiSidecarPanelBase> getSidecarInitializer() {
        return this.sidecarInitializer;
    }

    public boolean isBackdropEnabled() {
        return this.backdropEnabled;
    }

    public ClickGuiOverlayTransitionMode getTransitionMode() {
        return this.transitionMode;
    }

    public static ClickGuiOverlaySpecBuilder builder() {
        return new ClickGuiOverlaySpecBuilder(null);
    }

    private ClickGuiOverlaySpec(String string, @Nullable ClickGuiSidecarPanelBase clickGuiSidecarPanelBase, @Nullable String string2, Consumer<PanelComponent> consumer, @Nullable Consumer<ClickGuiSidecarPanelBase> consumer2, ClickGuiOverlayPlacement clickGuiOverlayPlacement, boolean bl, ClickGuiOverlayTransitionMode clickGuiOverlayTransitionMode, @Nullable Double d) {
        this.title = string;
        this.sidecar = clickGuiSidecarPanelBase;
        this.sidecarIcon = string2;
        this.contentInitializer = consumer;
        this.sidecarInitializer = consumer2;
        this.placement = clickGuiOverlayPlacement;
        this.backdropEnabled = bl;
        this.transitionMode = clickGuiOverlayTransitionMode;
        this.width = d;
    }
}
