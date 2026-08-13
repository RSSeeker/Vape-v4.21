package gg.vape.ui.click.frame.impl.main;

import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.frame.impl.main.ClickGuiOverlayPlacement;
import gg.vape.ui.click.frame.impl.main.ClickGuiOverlaySpec;
import gg.vape.ui.click.frame.impl.main.ClickGuiOverlaySpecEntry;
import gg.vape.ui.click.frame.impl.main.ClickGuiOverlayTransitionMode;
import gg.vape.ui.click.frame.impl.main.ClickGuiSidecarPanelBase;
import java.util.Objects;
import java.util.function.Consumer;
import org.jetbrains.annotations.Nullable;

public final class ClickGuiOverlaySpecBuilder {
    private static final String MISSING_TITLE_MESSAGE = "Sidecar title must be provided";
    private Consumer<PanelComponent> contentInitializer;
    private Consumer<ClickGuiSidecarPanelBase> sidecarInitializer;
    private Boolean backdropEnabled;
    private ClickGuiOverlayTransitionMode transitionMode;
    private Double width;
    private ClickGuiOverlayPlacement placement = ClickGuiOverlayPlacement.OVERLAY;
    private String title;
    private String sidecarIcon;
    private ClickGuiSidecarPanelBase sidecar;

    public ClickGuiOverlaySpecBuilder transitionMode(ClickGuiOverlayTransitionMode transitionMode) {
        this.transitionMode = transitionMode;
        return this;
    }

    public ClickGuiOverlaySpecBuilder title(String title) {
        this.title = title;
        return this;
    }

    public ClickGuiOverlaySpecBuilder width(@Nullable Double width) {
        this.width = width;
        return this;
    }

    ClickGuiOverlaySpecBuilder(ClickGuiOverlaySpecEntry clickGuiOverlaySpecEntry) {
        this();
    }

    public ClickGuiOverlaySpecBuilder placement(ClickGuiOverlayPlacement placement) {
        this.placement = placement;
        return this;
    }

    public ClickGuiOverlaySpecBuilder backdropEnabled(boolean backdropEnabled) {
        this.backdropEnabled = backdropEnabled;
        return this;
    }

    public ClickGuiOverlaySpecBuilder sidecarIcon(@Nullable String sidecarIcon) {
        this.sidecarIcon = sidecarIcon;
        return this;
    }

    private ClickGuiOverlaySpecBuilder() {
        this.transitionMode = ClickGuiOverlayTransitionMode.REPLACE;
    }

    public ClickGuiOverlaySpecBuilder sidecar(@Nullable ClickGuiSidecarPanelBase sidecar) {
        this.sidecar = sidecar;
        return this;
    }


    public ClickGuiOverlaySpecBuilder initializeSidecar(Consumer<ClickGuiSidecarPanelBase> initializer) {
        this.sidecarInitializer = initializer;
        return this;
    }

    private static void initializeEmptyContent(PanelComponent panelComponent) {
    }

    public ClickGuiOverlaySpecBuilder initializeContent(Consumer<PanelComponent> initializer) {
        this.contentInitializer = initializer;
        return this;
    }

    public ClickGuiOverlaySpec build() {
        String title = Objects.requireNonNull(this.title, MISSING_TITLE_MESSAGE);
        Consumer<PanelComponent> initializer = this.contentInitializer != null ? this.contentInitializer : ClickGuiOverlaySpecBuilder::initializeEmptyContent;
        ClickGuiOverlayPlacement placement = this.placement != null ? this.placement : ClickGuiOverlayPlacement.OVERLAY;
        boolean backdropEnabled = this.backdropEnabled != null ? this.backdropEnabled : true;
        ClickGuiOverlayTransitionMode transitionMode = this.transitionMode != null ? this.transitionMode : ClickGuiOverlayTransitionMode.REPLACE;
        return new ClickGuiOverlaySpec(title, this.sidecar, this.sidecarIcon, initializer, this.sidecarInitializer, placement, backdropEnabled, transitionMode, this.width, null);
    }
}

