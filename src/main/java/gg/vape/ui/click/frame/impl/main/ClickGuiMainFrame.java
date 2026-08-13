package gg.vape.ui.click.frame.impl.main;

import func.skidline.RectData;
import gg.vape.module.Category;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.frame.OutlinedFrameBase;
import gg.vape.ui.click.frame.impl.main.ClickGuiFriendsPage;
import gg.vape.ui.click.frame.impl.main.ClickGuiMainFrameHeader;
import gg.vape.ui.click.frame.impl.main.ClickGuiMainFrameTransitionComponent;
import gg.vape.ui.click.frame.impl.main.ClickGuiModulesPage;
import gg.vape.ui.click.frame.impl.main.ClickGuiOverlayController;
import gg.vape.ui.click.frame.impl.main.ClickGuiOverlayLayer;
import gg.vape.ui.click.frame.impl.main.ClickGuiOverlayNavigationPanel;
import gg.vape.ui.click.frame.impl.main.ClickGuiOverlayPlacement;
import gg.vape.ui.click.frame.impl.main.ClickGuiOverlaySpec;
import gg.vape.ui.click.frame.impl.main.ClickGuiPageBase;
import gg.vape.ui.click.frame.impl.main.ClickGuiPanelBase;
import gg.vape.ui.click.frame.impl.main.ClickGuiProfilesPage;
import gg.vape.ui.click.frame.impl.main.ClickGuiSection;
import gg.vape.ui.click.frame.impl.main.ClickGuiSectionTabComponent;
import gg.vape.ui.click.frame.impl.main.ClickGuiSidecarPanelBase;
import gg.vape.ui.click.layout.ComponentLayout;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Objects;

public class ClickGuiMainFrame
extends OutlinedFrameBase {
    private static final double FRAME_WIDTH = 400.0;
    private static final double FRAME_HEIGHT = 240.0;
    private static final double HEADER_HEIGHT = 40.0;
    private static final double FRAME_OVERLAY_WIDTH = 136.0;
    private static final double CONTENT_OVERLAY_WIDTH = 120.0;
    private final List<Runnable> contentOverlayCallbacks;
    private final List<ClickGuiSectionTabComponent> sectionTabs = new ArrayList<ClickGuiSectionTabComponent>();
    private final ClickGuiMainFrameTransitionComponent transitionOverlay;
    private double lastContentInset = -1.0;
    private final PanelComponent contentPanel;
    private final EnumMap<ClickGuiSection, ClickGuiPanelBase> pages;
    private ClickGuiPanelBase activePage;
    private final ClickGuiOverlayController frameOverlayController;
    private final ClickGuiMainFrameHeader header;
    private ClickGuiSection activeSection;
    private final ClickGuiOverlayController contentOverlayController;
    private final List<Runnable> frameOverlayCallbacks;

    public ClickGuiOverlaySpec getActiveOverlaySpec() {
        if (this.frameOverlayController.hasActiveSpec()) {
            return this.frameOverlayController.getActiveSpec();
        }
        if (this.contentOverlayController.hasActiveSpec()) {
            return this.contentOverlayController.getActiveSpec();
        }
        return null;
    }

    @Override
    public void J() {
        boolean bl = this.frameOverlayController.capturesInput();
        boolean bl2 = this.contentOverlayController.capturesInput();
        if (!bl && !bl2) {
            super.J();
            return;
        }
        this.F();
        this.setHovered(true);
        for (GuiComponent guiComponent : this.f()) {
            if (!guiComponent.V$src$Z$1xhop3l() || !guiComponent.t()) continue;
            if (bl) {
                if (guiComponent != this.frameOverlayController.getNavigationPanel() && guiComponent != this.frameOverlayController.getBackdrop()) continue;
                guiComponent.J();
                continue;
            }
            if (bl2) {
                if (guiComponent != this.header && guiComponent != this.contentOverlayController.getNavigationPanel() && guiComponent != this.contentOverlayController.getBackdrop() && guiComponent != this.frameOverlayController.getNavigationPanel() && guiComponent != this.frameOverlayController.getBackdrop()) continue;
                guiComponent.J();
                continue;
            }
            guiComponent.J();
        }
    }

    public void removeFrameOverlayCallback(Runnable runnable) {
        if (runnable != null) {
            this.frameOverlayCallbacks.remove(runnable);
        }
    }

    public ClickGuiOverlayNavigationPanel getActiveOverlayNavigation() {
        return this.getActiveOverlayController().getNavigationPanel();
    }

    public void addContentOverlayCallback(Runnable runnable) {
        if (runnable != null) {
            this.contentOverlayCallbacks.add(runnable);
        }
    }

    public boolean clearActiveSearch() {
        if (this.transitionOverlay.isActive()) {
            return false;
        }
        if (this.activeSection == ClickGuiSection.MODULES && this.activePage instanceof ClickGuiModulesPage) {
            return ((ClickGuiModulesPage)this.activePage).clearActiveSearch();
        }
        return false;
    }

    private void showSection(ClickGuiSection clickGuiSection) {
        ClickGuiPanelBase clickGuiPanelBase = this.pages.computeIfAbsent(clickGuiSection, this::createPage);
        this.closeActiveOverlay();
        if (clickGuiPanelBase == null) {
            return;
        }
        if (this.activePage == clickGuiPanelBase) {
            clickGuiPanelBase.Z$src$V$15w0jcm();
            clickGuiPanelBase.H(true);
            return;
        }
        if (this.activePage != null) {
            this.activePage.K();
            this.contentPanel.removeChild(this.activePage);
        }
        this.activePage = clickGuiPanelBase;
        this.contentPanel.h(clickGuiPanelBase, new Object[0]);
        clickGuiPanelBase.Z$src$V$15w0jcm();
    }

    private void handleSectionTabClick(ClickGuiSection clickGuiSection) {
        this.selectSection(clickGuiSection);
    }

    public ClickGuiMainFrameTransitionComponent getTransitionOverlay() {
        return this.transitionOverlay;
    }

    private double getOverlayWidth(ClickGuiOverlayPlacement clickGuiOverlayPlacement) {
        if (clickGuiOverlayPlacement == ClickGuiOverlayPlacement.OVERLAY) {
            return FRAME_OVERLAY_WIDTH;
        }
        return CONTENT_OVERLAY_WIDTH;
    }

    private void selectSection(ClickGuiSection clickGuiSection) {
        this.activeSection = clickGuiSection;
        this.updateSelectedTab();
        this.showSection(clickGuiSection);
    }

    @Override
    public double A() {
        return FRAME_WIDTH;
    }

    public boolean isOverlayTransitioning() {
        return this.frameOverlayController.isOpen() || this.contentOverlayController.isOpen();
    }

    @Override
    public void H() {
        this.updateContentLayout();
        GuiRenderPrimitives.e(this.G$src$D$1b2f02a(), this.n(), FRAME_WIDTH, FRAME_HEIGHT, this.getDisabledOverlayColor(), false, 2.0f, 1.0f);
        super.H();
    }

    public double getHeaderHeight() {
        return HEADER_HEIGHT;
    }

    @Override
    public void u() {
        this.h(new RectData(this.G$src$D$1b2f02a(), this.n(), FRAME_WIDTH, FRAME_HEIGHT));
        this.contentOverlayController.updateBounds();
        this.frameOverlayController.updateBounds();
        super.u();
        this.contentOverlayController.updateAnimationState();
        this.frameOverlayController.updateAnimationState();
    }

    @Override
    public String getName() {
        return "Standalone GUI";
    }

    public boolean h() {
        return false;
    }

    static void refreshOverlayLayout(ClickGuiMainFrame clickGuiMainFrame) {
        clickGuiMainFrame.updateContentLayout();
    }

    public void closeActiveOverlay() {
        if (this.frameOverlayController.isVisible()) {
            this.frameOverlayController.close();
        } else {
            this.contentOverlayController.close();
        }
        this.updateContentLayout();
    }

    private void updateSelectedTab() {
        for (ClickGuiSectionTabComponent clickGuiSectionTabComponent : this.sectionTabs) {
            clickGuiSectionTabComponent.setSelected(clickGuiSectionTabComponent.getSection() == this.activeSection);
        }
    }

    private void initializeTabs() {
        ClickGuiMainFrameHeader clickGuiMainFrameHeader = this.header;
        for (ClickGuiSection clickGuiSection : ClickGuiSection.values()) {
            ClickGuiSectionTabComponent clickGuiSectionTabComponent = new ClickGuiSectionTabComponent(clickGuiSection);
            clickGuiSectionTabComponent.Y(20.0);
            clickGuiSectionTabComponent.addClickListener(() -> this.handleSectionTabClick(clickGuiSection));
            this.sectionTabs.add(clickGuiSectionTabComponent);
            clickGuiMainFrameHeader.addSectionTab(clickGuiSectionTabComponent);
        }
    }

    public ClickGuiMainFrame() {
        this.contentOverlayCallbacks = new ArrayList<Runnable>();
        this.frameOverlayCallbacks = new ArrayList<Runnable>();
        this.pages = new EnumMap(ClickGuiSection.class);
        this.activeSection = ClickGuiSection.MODULES;
        this.o(FRAME_WIDTH);
        this.Y(FRAME_HEIGHT);
        this.setDisabledOverlayColor(ClickGuiMainFrame.J.i);
        this.setShowDisabledOverlay(false);
        this.g(true);
        this.L(false, true);
        this.Y(false);
        ComponentLayout componentLayout = this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij();
        componentLayout.t(false);
        componentLayout.M(false);
        componentLayout.U(false);
        componentLayout.I(false);
        componentLayout.u(false);
        this.header = new ClickGuiMainFrameHeader(this);
        this.h(this.header, "wrap");
        this.contentPanel = new PanelComponent(FRAME_WIDTH, FRAME_HEIGHT - HEADER_HEIGHT);
        this.contentPanel.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("widthwrap");
        this.contentPanel.setShowDisabledOverlay(false);
        this.contentPanel.setShadowEnabled(false);
        this.h(this.contentPanel, new Object[0]);
        this.frameOverlayController = new ClickGuiOverlayController(this, ClickGuiOverlayLayer.FRAME_OVERLAY, FRAME_OVERLAY_WIDTH, null);
        this.contentOverlayController = new ClickGuiOverlayController(this, ClickGuiOverlayLayer.CONTENT_OVERLAY, CONTENT_OVERLAY_WIDTH, null);
        this.transitionOverlay = new ClickGuiMainFrameTransitionComponent(this);
        this.addChildren(this.contentOverlayController.getBackdrop(), this.contentOverlayController.getNavigationPanel(), this.frameOverlayController.getBackdrop(), this.frameOverlayController.getNavigationPanel(), this.transitionOverlay);
        this.initializeTabs();
        this.selectSection(this.activeSection);
    }

    static List<Runnable> getContentOverlayCallbacks(ClickGuiMainFrame clickGuiMainFrame) {
        return clickGuiMainFrame.contentOverlayCallbacks;
    }

    public Category s$src$Lgg_vape_module_Category_$154whg1() {
        return Category.NONE;
    }


    private ClickGuiOverlayController getActiveOverlayController() {
        if (this.frameOverlayController.hasActiveSpec()) {
            return this.frameOverlayController;
        }
        return this.contentOverlayController;
    }

    static double getOverlayWidth(ClickGuiMainFrame clickGuiMainFrame, ClickGuiOverlayPlacement clickGuiOverlayPlacement) {
        return clickGuiMainFrame.getOverlayWidth(clickGuiOverlayPlacement);
    }

    public void removeContentOverlayCallback(Runnable runnable) {
        if (runnable != null) {
            this.contentOverlayCallbacks.remove(runnable);
        }
    }

    @Override
    public boolean d$src$Z$1lx9d06() {
        return false;
    }

    public ClickGuiOverlayPlacement getActiveOverlayPlacement() {
        ClickGuiOverlaySpec clickGuiOverlaySpec = this.getActiveOverlaySpec();
        if (clickGuiOverlaySpec != null) {
            return clickGuiOverlaySpec.getPlacement();
        }
        return ClickGuiOverlayPlacement.OVERLAY;
    }

    private void updateContentLayout() {
        double d = this.contentOverlayController.getVisibilityProgress();
        boolean bl = this.contentOverlayController.shiftsContent(d);
        if (bl) {
            double d2;
            double d3;
            double d4 = Math.min(1.0, d * 1.35);
            double d5 = 0.0;
            d5 = (this.contentOverlayController.getWidth() - 6.0) * d4;
            if (this.activePage instanceof ClickGuiModulesPage) {
                d3 = Math.max(0.0, this.G$src$D$1b2f02a() - this.contentPanel.G$src$D$1b2f02a());
                d2 = Math.max(0.0, ((ClickGuiModulesPage)this.activePage).B$src$D$yaoqbo() - 0.0 + d3);
                d5 = Math.min(d5, d2);
            }
            d3 = this.G$src$D$1b2f02a() - d5;
            this.contentPanel.K(d3);
            this.contentPanel.o(FRAME_WIDTH);
            this.contentPanel.h(null);
            this.contentPanel.H(true);
            d2 = 10.0 * d4;
            if (this.activePage != null && d2 != this.lastContentInset) {
                this.lastContentInset = d2;
                this.activePage.K(FRAME_WIDTH, FRAME_HEIGHT, d2);
            }
            return;
        }
        double d6 = d;
        double d7 = 0.0;
        double d8 = this.G$src$D$1b2f02a() - d7;
        this.contentPanel.K(d8);
        this.contentPanel.o(FRAME_WIDTH);
        this.contentPanel.h(null);
        this.contentPanel.H(true);
        double d9 = 0.0;
        if (this.activePage != null && d9 != this.lastContentInset) {
            this.lastContentInset = d9;
            this.activePage.K(FRAME_WIDTH, FRAME_HEIGHT, d9);
        }
    }

    static List<Runnable> getFrameOverlayCallbacks(ClickGuiMainFrame clickGuiMainFrame) {
        return clickGuiMainFrame.frameOverlayCallbacks;
    }

    @Override
    public double L() {
        return FRAME_HEIGHT;
    }

    public ClickGuiMainFrameHeader getHeader() {
        return this.header;
    }

    public void addFrameOverlayCallback(Runnable runnable) {
        if (runnable != null) {
            this.frameOverlayCallbacks.add(runnable);
        }
    }

    private ClickGuiOverlayController getOverlayController(ClickGuiOverlayPlacement clickGuiOverlayPlacement) {
        if (clickGuiOverlayPlacement == ClickGuiOverlayPlacement.OVERLAY) {
            return this.frameOverlayController;
        }
        return this.contentOverlayController;
    }

    public void showOverlay(ClickGuiOverlaySpec clickGuiOverlaySpec) {
        Objects.requireNonNull(clickGuiOverlaySpec, "config");
        ClickGuiOverlayController clickGuiOverlayController = this.getOverlayController(clickGuiOverlaySpec.getPlacement());
        clickGuiOverlayController.show(clickGuiOverlaySpec);
        this.updateContentLayout();
    }

    public ClickGuiSidecarPanelBase getActiveSidecar() {
        return this.getActiveOverlayController().getNavigationPanel().getSidecar();
    }

    private ClickGuiPanelBase createPage(ClickGuiSection clickGuiSection) {
        ClickGuiPageBase clickGuiPageBase;
        switch (clickGuiSection) {
            case MODULES: {
                clickGuiPageBase = new ClickGuiModulesPage(this, this.contentPanel.A(), this.contentPanel.L(), 104.0);
                break;
            }
            case FRIENDS: {
                clickGuiPageBase = new ClickGuiFriendsPage(this, this.contentPanel.A(), this.contentPanel.L(), 104.0);
                break;
            }
            case PROFILES: {
                clickGuiPageBase = new ClickGuiProfilesPage(this, this.contentPanel.A(), this.contentPanel.L(), 186.0);
                break;
            }
            default: {
                return null;
            }
        }
        return clickGuiPageBase;
    }
}

