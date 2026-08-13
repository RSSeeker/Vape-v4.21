package gg.vape.ui.click.frame.impl.main;

import gg.vape.ui.click.frame.FrameHeaderComponent;
import gg.vape.ui.click.frame.impl.main.ClickGuiContentPanel;
import gg.vape.ui.click.frame.impl.main.ClickGuiMainFrame;
import gg.vape.ui.click.frame.impl.main.ClickGuiOverlayNavigationPanelBase;
import gg.vape.ui.click.frame.impl.main.ClickGuiOverlayNavigationPanelEntry;
import gg.vape.ui.click.frame.impl.main.ClickGuiOverlayPlacement;
import gg.vape.ui.click.frame.impl.main.ClickGuiOverlaySpec;
import gg.vape.ui.click.frame.impl.main.ClickGuiOverlayTransitionMode;
import gg.vape.ui.click.frame.impl.main.ClickGuiSidecarPanelBase;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.DoubleSupplier;
import org.jetbrains.annotations.Nullable;

public class ClickGuiOverlayNavigationPanel
extends ClickGuiContentPanel {
    private final Runnable closeAction;
    @Nullable
    private Consumer<ClickGuiOverlaySpec> specChangeListener;
    private ClickGuiOverlayPlacement placement;
    private final DoubleSupplier visibilitySupplier;
    private final ClickGuiContentPanel contentPanel;
    private final ClickGuiMainFrame frame;
    private final Deque<ClickGuiOverlaySpec> specStack = new ArrayDeque<ClickGuiOverlaySpec>();
    private ClickGuiSidecarPanelBase sidecar;

    public boolean hasBackStack() {
        return this.specStack.size() > 1;
    }

    public ClickGuiOverlayPlacement getPlacement() {
        return this.placement;
    }

    public void showRoot(ClickGuiOverlaySpec clickGuiOverlaySpec, boolean preservePushStack) {
        Double d;
        boolean bl2;
        Objects.requireNonNull(clickGuiOverlaySpec, "config");
        boolean bl3 = bl2 = preservePushStack && clickGuiOverlaySpec.getTransitionMode() == ClickGuiOverlayTransitionMode.PUSH && !this.specStack.isEmpty();
        if (!bl2) {
            this.specStack.clear();
        }
        this.specStack.push(clickGuiOverlaySpec);
        ClickGuiOverlayPlacement clickGuiOverlayPlacement = this.getRootPlacement();
        if (clickGuiOverlayPlacement != null) {
            this.placement = clickGuiOverlayPlacement;
        }
        if ((d = clickGuiOverlaySpec.getWidth()) != null) {
            this.o(d);
            this.contentPanel.o(d);
        }
        this.applySpec(clickGuiOverlaySpec);
    }

    private void updateBounds() {
        boolean bl;
        double d = Math.max(0.0, Math.min(1.0, this.visibilitySupplier.getAsDouble()));
        if (d <= 0.0) {
            return;
        }
        double d2 = d;
        if (this.placement == ClickGuiOverlayPlacement.DOCKED_SHIFT) {
            d2 = Math.min(1.0, d * 1.35);
        }
        double d3 = this.frame.G$src$D$1b2f02a();
        double d4 = this.frame.n();
        double d5 = this.frame.A();
        double d6 = this.frame.L();
        FrameHeaderComponent frameHeaderComponent = this.frame.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc();
        if (frameHeaderComponent == null) {
            frameHeaderComponent = this.frame.getHeader();
        }
        double d7 = 0.0;
        boolean bl2 = bl = this.placement == ClickGuiOverlayPlacement.DOCKED || this.placement == ClickGuiOverlayPlacement.DOCKED_SHIFT;
        if (bl && frameHeaderComponent != null) {
            d7 = frameHeaderComponent.L();
        }
        double d8 = d4 + d7;
        double d9 = Math.max(0.0, d6 - d7);
        double d10 = this.A();
        double d11 = d3 + d5;
        double d12 = d3 + d5 - d10;
        double d13 = d11 - d10 * d2;
        this.K(d13);
        this.S(d8);
        this.Y(d9);
        this.sidecar.K(d13);
        this.sidecar.S(d8);
        this.sidecar.o(d10);
        double d14 = d13;
        double d15 = d8 + this.sidecar.L();
        this.contentPanel.K(d14);
        this.contentPanel.S(d15);
        this.contentPanel.o(d10);
        this.updateContentBounds();
        this.contentPanel.l$src$V$1mibm4x();
    }

    private ClickGuiOverlayPlacement getRootPlacement() {
        ClickGuiOverlaySpec clickGuiOverlaySpec = this.specStack.peekLast();
        return clickGuiOverlaySpec != null ? clickGuiOverlaySpec.getPlacement() : null;
    }

    @Override
    public void c() {
        this.updateBounds();
        super.c();
    }

    private void updateContentBounds() {
        boolean bl;
        double d = this.frame.L();
        double d2 = this.frame.A();
        FrameHeaderComponent frameHeaderComponent = this.frame.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc();
        if (frameHeaderComponent == null) {
            frameHeaderComponent = this.frame.getHeader();
        }
        double d3 = 0.0;
        boolean bl2 = bl = this.placement == ClickGuiOverlayPlacement.DOCKED || this.placement == ClickGuiOverlayPlacement.DOCKED_SHIFT;
        if (bl && frameHeaderComponent != null) {
            d3 = frameHeaderComponent.L();
        }
        double d4 = Math.max(0.0, d - d3);
        double d5 = d4 - this.sidecar.L() - 0.5 - 0.1;
        this.contentPanel.o(this.A());
        this.contentPanel.Y(d5);
        this.contentPanel.t(d5);
    }

    public boolean isBackdropEnabled() {
        ClickGuiOverlaySpec clickGuiOverlaySpec = this.specStack.peekLast();
        return clickGuiOverlaySpec == null || clickGuiOverlaySpec.isBackdropEnabled();
    }

    public void clearNavigation() {
        this.specStack.clear();
        this.contentPanel.removeMarkedChildren();
        this.contentPanel.b(0.0);
        this.sidecar.setBackAction(null);
        if (!(this.sidecar instanceof ClickGuiOverlayNavigationPanelBase)) {
            this.sidecar.clearTrailingComponents();
            try {
                this.removeChild(this.sidecar);
            }
            catch (Exception exception) {
                // empty catch block
            }
            this.sidecar = new ClickGuiOverlayNavigationPanelEntry(this.closeAction);
            this.addChildren(this.sidecar);
        }
        this.notifySpecChanged(null);
    }

    public ClickGuiContentPanel getContentPanel() {
        return this.contentPanel;
    }

    private void applySpec(ClickGuiOverlaySpec clickGuiOverlaySpec) {
        Consumer<ClickGuiSidecarPanelBase> consumer;
        boolean bl;
        ClickGuiSidecarPanelBase clickGuiSidecarPanelBase = clickGuiOverlaySpec.getSidecar();
        boolean bl2 = bl = clickGuiSidecarPanelBase != null;
        if (bl && clickGuiSidecarPanelBase != this.sidecar) {
            try {
                this.removeChild(this.sidecar);
            }
            catch (Exception exception) {
                // empty catch block
            }
            this.sidecar = clickGuiSidecarPanelBase;
            this.sidecar.setCloseAction(this.closeAction);
            this.addChildren(this.sidecar);
        }
        Runnable runnable = this.specStack.size() > 1 ? this::pop : null;
        this.sidecar.setTitle(clickGuiOverlaySpec.getTitle());
        this.sidecar.setLeadingIconKey(clickGuiOverlaySpec.getSidecarIcon());
        this.sidecar.setBackAction(runnable);
        this.contentPanel.removeMarkedChildren();
        this.contentPanel.b(0.0);
        this.contentPanel.D(true);
        if (!bl && this.isNavigationSidecar(this.sidecar)) {
            this.sidecar.clearTrailingComponents();
            this.sidecar.setDividerVisible(true);
        }
        if ((consumer = clickGuiOverlaySpec.getSidecarInitializer()) != null) {
            consumer.accept(this.sidecar);
        }
        this.updateContentBounds();
        clickGuiOverlaySpec.getContentInitializer().accept(this.contentPanel);
        this.contentPanel.H(true);
        this.notifySpecChanged(clickGuiOverlaySpec);
    }

    public void push(ClickGuiOverlaySpec clickGuiOverlaySpec) {
        Objects.requireNonNull(clickGuiOverlaySpec, "config");
        this.specStack.push(clickGuiOverlaySpec);
        ClickGuiOverlayPlacement clickGuiOverlayPlacement = this.getRootPlacement();
        if (clickGuiOverlayPlacement != null) {
            this.placement = clickGuiOverlayPlacement;
        }
        this.applySpec(clickGuiOverlaySpec);
    }

    private void notifySpecChanged(@Nullable ClickGuiOverlaySpec clickGuiOverlaySpec) {
        Consumer<ClickGuiOverlaySpec> consumer = this.specChangeListener;
        if (consumer != null) {
            consumer.accept(clickGuiOverlaySpec);
        }
    }

    public boolean pop() {
        if (this.specStack.size() <= 1) {
            return false;
        }
        this.specStack.pop();
        ClickGuiOverlaySpec clickGuiOverlaySpec = this.specStack.peek();
        if (clickGuiOverlaySpec != null) {
            ClickGuiOverlayPlacement clickGuiOverlayPlacement = this.getRootPlacement();
            if (clickGuiOverlayPlacement != null) {
                this.placement = clickGuiOverlayPlacement;
            }
            this.applySpec(clickGuiOverlaySpec);
        } else {
            this.clearNavigation();
        }
        return true;
    }

    private boolean isNavigationSidecar(ClickGuiSidecarPanelBase clickGuiSidecarPanelBase) {
        return clickGuiSidecarPanelBase instanceof ClickGuiOverlayNavigationPanelBase;
    }

    @Override
    public void H() {
        boolean bl;
        double d = Math.max(0.0, Math.min(1.0, this.visibilitySupplier.getAsDouble()));
        if (d <= 0.0) {
            return;
        }
        double d2 = this.frame.n();
        double d3 = this.frame.L();
        FrameHeaderComponent frameHeaderComponent = this.frame.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc();
        if (frameHeaderComponent == null) {
            frameHeaderComponent = this.frame.getHeader();
        }
        double d4 = 0.0;
        boolean bl2 = bl = this.placement == ClickGuiOverlayPlacement.DOCKED || this.placement == ClickGuiOverlayPlacement.DOCKED_SHIFT;
        if (bl) {
            if (frameHeaderComponent != null) {
                d4 = frameHeaderComponent.L();
            }
            double d5 = d2 + d4;
            double d6 = Math.max(0.0, d3 - d4);
            double d7 = this.A();
            double d8 = this.G$src$D$1b2f02a();
            int n = 4;
            GuiRenderPrimitives.p(d8, d5, d7, d6, ClickGuiOverlayNavigationPanel.J.m, false, 2.0f, 1.0f, 0.0f, ClickGuiOverlayNavigationPanel.J.B, n);
            return;
        }
        double d9 = d2 + d4;
        double d10 = Math.max(0.0, d3 - d4);
        double d11 = this.A();
        double d12 = this.G$src$D$1b2f02a();
        int n = 4;
        GuiRenderPrimitives.p(d12, d9, d11, d10, ClickGuiOverlayNavigationPanel.J.m, false, 2.0f, 1.0f, 0.0f, ClickGuiOverlayNavigationPanel.J.B, n |= 2);
    }

    public boolean hasActiveSpec() {
        return !this.specStack.isEmpty();
    }

    public ClickGuiSidecarPanelBase getSidecar() {
        return this.sidecar;
    }

    public void setSpecChangeListener(@Nullable Consumer<ClickGuiOverlaySpec> consumer) {
        this.specChangeListener = consumer;
    }

    public ClickGuiOverlayNavigationPanel(ClickGuiMainFrame clickGuiMainFrame, double d, DoubleSupplier doubleSupplier, Runnable runnable, @Nullable Double d2) {
        super(d2 != null ? d2 : d, 0.0);
        this.placement = ClickGuiOverlayPlacement.OVERLAY;
        this.frame = clickGuiMainFrame;
        this.visibilitySupplier = doubleSupplier;
        this.closeAction = runnable;
        this.sidecar = new ClickGuiOverlayNavigationPanelEntry(runnable);
        double d3 = d2 != null ? d2 : d;
        this.contentPanel = new ClickGuiContentPanel(d3, 0.0);
        this.contentPanel.setParentFrameComponent(this);
        this.contentPanel.setLayoutMode("wrap");
        this.contentPanel.setShowDisabledOverlay(false);
        this.contentPanel.setShadowEnabled(false);
        this.contentPanel.D(true);
        this.setShowDisabledOverlay(false);
        this.o(d3);
        this.addChildren(this.sidecar, this.contentPanel);
        this.setVisible(false);
    }
}
