package gg.vape.ui.click.frame.impl.main;

import gg.vape.ui.click.animation.DoubleAnimation;
import gg.vape.ui.click.frame.impl.main.ClickGuiMainFrame;
import gg.vape.ui.click.frame.impl.main.ClickGuiOverlayBackdropComponent;
import gg.vape.ui.click.frame.impl.main.ClickGuiOverlayLayer;
import gg.vape.ui.click.frame.impl.main.ClickGuiOverlayNavigationPanel;
import gg.vape.ui.click.frame.impl.main.ClickGuiOverlayPlacement;
import gg.vape.ui.click.frame.impl.main.ClickGuiOverlayPointPredicate;
import gg.vape.ui.click.frame.impl.main.ClickGuiOverlaySpec;
import gg.vape.ui.click.frame.impl.main.ClickGuiOverlayTransitionMode;
import gg.vape.ui.click.frame.impl.main.ClickGuiSectionSwitchMap;
import java.util.Objects;
import org.jetbrains.annotations.Nullable;

final class ClickGuiOverlayController {
    private static final String PLACEMENT_ARGUMENT = "mode";
    private final ClickGuiOverlayLayer layer;
    final ClickGuiMainFrame frame;
    private final ClickGuiOverlayNavigationPanel navigationPanel;
    private final DoubleAnimation visibilityAnimation;
    private boolean closeCallbacksFired;
    private final ClickGuiOverlayBackdropComponent backdrop;
    private ClickGuiOverlayPlacement placement;
    private final DoubleAnimation backdropAnimation;
    private double width;
    private ClickGuiOverlaySpec activeSpec;
    private boolean open;

    private boolean isVisible(double progress) {
        return this.open || progress > 0.001;
    }

    private ClickGuiOverlayController(ClickGuiMainFrame clickGuiMainFrame, ClickGuiOverlayLayer clickGuiOverlayLayer, double d) {
        this.frame = clickGuiMainFrame;
        this.layer = clickGuiOverlayLayer;
        this.visibilityAnimation = new DoubleAnimation(0.15, 0.0, 1.0);
        this.visibilityAnimation.O();
        this.backdropAnimation = new DoubleAnimation(0.15, 0.0, 1.0);
        this.backdropAnimation.O();
        this.width = d;
        this.placement = clickGuiOverlayLayer == ClickGuiOverlayLayer.FRAME_OVERLAY ? ClickGuiOverlayPlacement.OVERLAY : ClickGuiOverlayPlacement.DOCKED;
        Runnable closeAction = this::closeAndRefreshLayout;
        this.navigationPanel = new ClickGuiOverlayNavigationPanel(clickGuiMainFrame, d, this::getVisibilityProgress, closeAction, null);
        this.navigationPanel.setSpecChangeListener(this::handleSpecChanged);
        this.navigationPanel.setVisible(false);
        this.backdrop = new ClickGuiOverlayBackdropComponent(clickGuiMainFrame, this::getBackdropOpacity, this::getPlacement, closeAction);
        this.backdrop.setVisible(false);
        this.backdrop.addGlobalMouseListener(new ClickGuiOverlayPointPredicate(this, clickGuiMainFrame));
    }

    boolean isOpen() {
        return this.open;
    }

    void show(ClickGuiOverlaySpec clickGuiOverlaySpec) {
        boolean bl;
        ClickGuiOverlayPlacement clickGuiOverlayPlacement = Objects.requireNonNull(clickGuiOverlaySpec.getPlacement(), PLACEMENT_ARGUMENT);
        double d = ClickGuiMainFrame.getOverlayWidth(this.frame, clickGuiOverlayPlacement);
        double d2 = this.getVisibilityProgress();
        boolean bl2 = this.isVisible(d2);
        this.activeSpec = clickGuiOverlaySpec;
        this.placement = clickGuiOverlayPlacement;
        this.width = d;
        this.navigationPanel.o(d);
        this.navigationPanel.showRoot(clickGuiOverlaySpec, bl2);
        boolean bl3 = this.open;
        if (bl3) {
            boolean bl4;
            this.open = true;
            boolean bl5 = clickGuiOverlaySpec.getTransitionMode() == ClickGuiOverlayTransitionMode.PUSH && this.navigationPanel.hasActiveSpec();
            boolean bl6 = bl4 = bl5 ? this.navigationPanel.isBackdropEnabled() : clickGuiOverlaySpec.isBackdropEnabled();
            if (bl4) {
                this.backdropAnimation.C();
            } else {
                this.backdropAnimation.O();
            }
            this.backdrop.setInteractive(bl4);
            this.navigationPanel.setVisible(true);
            this.backdrop.setVisible(bl4);
            return;
        }
        this.open = true;
        this.visibilityAnimation.J();
        boolean bl7 = clickGuiOverlaySpec.getTransitionMode() == ClickGuiOverlayTransitionMode.PUSH && this.navigationPanel.hasActiveSpec();
        boolean bl8 = bl = bl7 ? this.navigationPanel.isBackdropEnabled() : clickGuiOverlaySpec.isBackdropEnabled();
        if (bl) {
            this.backdropAnimation.J();
        } else {
            this.backdropAnimation.O();
        }
        this.backdrop.setInteractive(bl);
        this.navigationPanel.setVisible(true);
        this.backdrop.setVisible(bl);
    }

    boolean capturesInput() {
        return this.backdrop.V$src$Z$1xhop3l() && this.backdrop.isInteractive();
    }

    private ClickGuiOverlayPlacement getPlacement() {
        return this.placement;
    }

    ClickGuiOverlayNavigationPanel getNavigationPanel() {
        return this.navigationPanel;
    }

    boolean isVisible() {
        return this.isVisible(this.getVisibilityProgress());
    }

    ClickGuiOverlayController(ClickGuiMainFrame clickGuiMainFrame, ClickGuiOverlayLayer clickGuiOverlayLayer, double d, ClickGuiSectionSwitchMap clickGuiSectionSwitchMap) {
        this(clickGuiMainFrame, clickGuiOverlayLayer, d);
    }

    private void closeAndRefreshLayout() {
        this.close();
        ClickGuiMainFrame.refreshOverlayLayout(this.frame);
    }

    double getBackdropOpacity() {
        return Math.max(0.0, Math.min(1.0, this.backdropAnimation.getInterpolatedValue()));
    }

    boolean shiftsContent(double progress) {
        if (this.layer != ClickGuiOverlayLayer.CONTENT_OVERLAY) {
            return false;
        }
        return (this.open || progress > 0.001) && this.placement == ClickGuiOverlayPlacement.DOCKED_SHIFT;
    }

    ClickGuiOverlayBackdropComponent getBackdrop() {
        return this.backdrop;
    }

    double getWidth() {
        return this.width;
    }

    ClickGuiOverlaySpec getActiveSpec() {
        return this.activeSpec;
    }

    private void resetPlacement() {
        if (this.layer == ClickGuiOverlayLayer.FRAME_OVERLAY) {
            this.placement = ClickGuiOverlayPlacement.OVERLAY;
        } else {
            this.placement = ClickGuiOverlayPlacement.DOCKED;
        }
        this.width = ClickGuiMainFrame.getOverlayWidth(this.frame, this.placement);
    }

    void updateBounds() {
        this.backdrop.K(this.frame.G$src$D$1b2f02a());
        this.backdrop.S(this.frame.n());
        this.navigationPanel.K(this.frame.G$src$D$1b2f02a());
        this.navigationPanel.S(this.frame.n());
        this.navigationPanel.T$src$V$1wse0de();
    }

    double getVisibilityProgress() {
        return Math.max(0.0, Math.min(1.0, this.visibilityAnimation.getInterpolatedValue()));
    }

    void close() {
        boolean bl;
        boolean bl2 = this.open;
        double d = this.getVisibilityProgress();
        boolean bl3 = bl = this.activeSpec != null && (this.activeSpec.getTransitionMode() == ClickGuiOverlayTransitionMode.PUSH && this.navigationPanel.hasActiveSpec() ? this.navigationPanel.isBackdropEnabled() : this.activeSpec.isBackdropEnabled());
        if (!bl2 && d <= 0.0) {
            return;
        }
        this.open = false;
        if (bl2 || d > 0.0) {
            for (Runnable pendingCallback : ClickGuiMainFrame.getContentOverlayCallbacks(this.frame)) {
                try {
                    pendingCallback.run();
                }
                catch (Throwable throwable) {}
            }
        }
        if (bl2) {
            this.visibilityAnimation.J();
            if (bl) {
                this.backdropAnimation.J();
            } else {
                this.backdropAnimation.O();
            }
        }
        this.backdrop.setInteractive(false);
        if (this.activeSpec != null && this.getVisibilityProgress() <= 0.0 && !this.closeCallbacksFired) {
            this.closeCallbacksFired = true;
            for (Runnable pendingCallback : ClickGuiMainFrame.getFrameOverlayCallbacks(this.frame)) {
                try {
                    pendingCallback.run();
                }
                catch (Throwable throwable) {}
            }
        }
    }

    static ClickGuiOverlayBackdropComponent getBackdrop(ClickGuiOverlayController controller) {
        return controller.backdrop;
    }

    private void handleSpecChanged(@Nullable ClickGuiOverlaySpec clickGuiOverlaySpec) {
        this.activeSpec = clickGuiOverlaySpec;
        if (clickGuiOverlaySpec != null) {
            this.placement = this.navigationPanel.getPlacement();
            this.width = this.navigationPanel.A();
        }
    }

    void updateAnimationState() {
        double d = this.getVisibilityProgress();
        boolean bl = this.isVisible(d);
        this.navigationPanel.setVisible(bl);
        boolean bl2 = false;
        if (bl && this.activeSpec != null) {
            boolean bl3 = this.activeSpec.getTransitionMode() == ClickGuiOverlayTransitionMode.PUSH && this.navigationPanel.hasActiveSpec();
            bl2 = bl3 ? this.navigationPanel.isBackdropEnabled() : this.activeSpec.isBackdropEnabled();
        }
        double d2 = this.getBackdropOpacity();
        boolean bl4 = bl2 && d2 > 0.001;
        this.backdrop.setVisible(bl4);
        this.backdrop.setInteractive(bl2 && this.open);
        if (!bl && this.activeSpec != null) {
            this.navigationPanel.clearNavigation();
            this.activeSpec = null;
            this.resetPlacement();
            if (!this.closeCallbacksFired) {
                this.closeCallbacksFired = true;
                for (Runnable pendingCallback : ClickGuiMainFrame.getFrameOverlayCallbacks(this.frame)) {
                    try {
                        pendingCallback.run();
                    }
                    catch (Exception exception) {}
                }
            }
        } else if (bl) {
            this.closeCallbacksFired = false;
        }
    }

    boolean hasActiveSpec() {
        return this.activeSpec != null;
    }
}
