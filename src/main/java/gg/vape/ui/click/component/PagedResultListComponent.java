package gg.vape.ui.click.component;

import gg.vape.api.PagedResult;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GlyphIconComponent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.PanelComponent;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import org.jetbrains.annotations.Nullable;

public class PagedResultListComponent
extends PanelComponent {
    private final GlyphIconComponent scrollToTopButton;
    @Nullable
    private PagedResult<?> pageMetadata;
    private boolean contentUpdated;
    private int componentCount;
    private CompletableFuture<List<GuiComponent>> pendingLoad;
    @Nullable
    private Supplier<CompletableFuture<List<GuiComponent>>> pageLoader;
    @Nullable
    private Supplier<GuiComponent> placeholderSupplier;
    private int componentsPerRow = 1;
    private int loadThreshold = 1;
    private int placeholderCount;
    private List<GuiComponent> loadingPlaceholders = new ArrayList<GuiComponent>();
    private long nextPageIndex;
    private static final int DEFAULT_PLACEHOLDER_COUNT = (int)4641203030845292568L;
    private boolean lastPageReached = false;
    private long initialPageIndex;
    @Nullable
    private PanelComponent scrollContainer;

    @Override
    public void c() {
        super.c();
        PanelComponent effectiveScrollContainer = this.getEffectiveScrollContainer();
        this.scrollToTopButton.K(this.G$src$D$1b2f02a() + (this.A() - 18.0));
        this.scrollToTopButton.S(effectiveScrollContainer.n() + 4.0);
        this.scrollToTopButton.setVisible(effectiveScrollContainer.J$src$D$hx1pag() < -effectiveScrollContainer.L());
        if (this.scrollToTopButton.V$src$Z$1xhop3l()) {
            this.scrollToTopButton.c();
        }
    }

    public PagedResultListComponent(double width, double height) {
        this(width, height, 1);
    }

    @Nullable
    public Supplier<GuiComponent> getPlaceholderSupplier() {
        return this.placeholderSupplier;
    }

    @Override
    public double x() {
        if (this.scrollContainer != null) {
            return this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().C();
        }
        return super.x();
    }

    @Override
    public void removeMarkedChildren() {
        super.removeMarkedChildren();
        this.resetPagination(true);
    }

    @Nullable
    public PagedResult<?> getPageMetadata() {
        return this.pageMetadata;
    }

    public int getComponentsPerRow() {
        return this.componentsPerRow;
    }

    @Override
    public void h(GuiComponent component, Object ... constraints) {
        boolean wrapAfterComponent = this.componentsPerRow == 1 || this.componentCount > 0 && (this.componentCount + 1) % this.componentsPerRow == 0;
        super.h(component, wrapAfterComponent ? "wrap" : "");
        ++this.componentCount;
        if (this.scrollContainer != null) {
            this.setExplicitHeight(this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().y());
            this.t(this.getExplicitHeight());
        }
    }

    private void setLoadingPlaceholders(List<GuiComponent> placeholders) {
        this.loadingPlaceholders = placeholders;
    }

    public int getPlaceholderCount() {
        return this.placeholderCount;
    }

    @Nullable
    public Supplier<CompletableFuture<List<GuiComponent>>> getPageLoader() {
        return this.pageLoader;
    }

    public void setPageLoader(@Nullable Supplier<CompletableFuture<List<GuiComponent>>> pageLoader) {
        this.pageLoader = pageLoader;
    }

    public long getNextPageIndex() {
        return this.nextPageIndex;
    }

    public void reload() {
        this.resetPagination(false);
        this.loadNextPage();
    }


    private void cancelPendingLoad() {
        CompletableFuture<List<GuiComponent>> load = this.pendingLoad;
        if (load != null) {
            load.cancel(true);
            this.pendingLoad = null;
            this.replaceComponents(this.loadingPlaceholders, new ArrayList<GuiComponent>());
            this.loadingPlaceholders.clear();
        }
    }

    @Override
    public void F() {
        if (this.scrollToTopButton.V$src$Z$1xhop3l() && this.scrollToTopButton.t()) {
            this.scrollToTopButton.F();
        }
    }

    public void setLoadThreshold(int loadThreshold) {
        this.loadThreshold = loadThreshold;
    }

    @Override
    public double C() {
        if (this.scrollContainer != null) {
            return this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().y();
        }
        return super.C();
    }

    public void setComponentsPerRow(int componentsPerRow) {
        this.componentsPerRow = componentsPerRow;
    }

    @Nullable
    public PanelComponent getScrollContainer() {
        return this.scrollContainer;
    }

    private void handleLoadCompleted(AtomicReference<CompletableFuture<List<GuiComponent>>> expectedLoad, List<GuiComponent> loadedComponents, Throwable error) {
        if (error != null) {
            this.pendingLoad = null;
            return;
        }
        if (loadedComponents == null) {
            this.pendingLoad = null;
            return;
        }
        if (expectedLoad.get() != this.pendingLoad) {
            return;
        }
        this.replaceComponents(this.loadingPlaceholders, loadedComponents);
        this.loadingPlaceholders.clear();
        if (loadedComponents.isEmpty()) {
            this.lastPageReached = true;
        } else {
            ++this.nextPageIndex;
        }
        this.contentUpdated = true;
        this.pendingLoad = null;
    }

    public int getLoadThreshold() {
        return this.loadThreshold;
    }

    private List<GuiComponent> handleLoadFailure(Throwable error) {
        this.pendingLoad = null;
        return null;
    }

    @Override
    public void t$src$V$zbu1jn() {
        super.t$src$V$zbu1jn();
        this.resetPagination(true);
    }

    public void setInitialPageIndex(long initialPageIndex) {
        this.initialPageIndex = initialPageIndex;
    }

    @Override
    public void dispatchMouseEvent(GuiMouseEvent guiMouseEvent) {
        if (this.scrollToTopButton.V$src$Z$1xhop3l() && this.scrollToTopButton.t()) {
            this.scrollToTopButton.dispatchMouseEvent(guiMouseEvent);
            return;
        }
        super.dispatchMouseEvent(guiMouseEvent);
    }

    public void rebuildLayoutPreservingScroll() {
        List<GuiComponent> children = this.f();
        double scrollOffset = this.J$src$D$hx1pag();
        super.removeMarkedChildren();
        this.componentCount = 0;
        for (GuiComponent child : children) {
            this.h(child, new Object[0]);
        }
        this.W(scrollOffset);
    }

    public long getInitialPageIndex() {
        return this.initialPageIndex;
    }

    public void replaceComponents(List<GuiComponent> componentsToRemove, List<GuiComponent> componentsToAdd) {
        double scrollOffset = this.J$src$D$hx1pag();
        for (GuiComponent component : componentsToRemove) {
            this.removeChild(component);
        }
        for (GuiComponent component : componentsToAdd) {
            this.h(component, new Object[0]);
        }
        this.rebuildLayoutPreservingScroll();
        this.W(scrollOffset);
    }

    public PagedResultListComponent(double width, double height, int initialPageIndex) {
        super(width, height);
        this.placeholderCount = DEFAULT_PLACEHOLDER_COUNT;
        this.scrollToTopButton = new GlyphIconComponent("up_arrow", 8.0, 8.0, 15.0, 15.0, Color.WHITE, PagedResultListComponent.J.f, new Color(255, 255, 255, 64));
        this.initialPageIndex = initialPageIndex;
        this.nextPageIndex = initialPageIndex;
        this.scrollToTopButton.setIconWidth(6.0);
        this.scrollToTopButton.setIconHeight(6.0);
        this.scrollToTopButton.setNormalColor(PagedResultListComponent.J.W);
        this.scrollToTopButton.setBackgroundAnimationColors(PagedResultListComponent.J.m, PagedResultListComponent.J.m.brighter());
        this.scrollToTopButton.setOutlineColor(PagedResultListComponent.J.l);
        this.scrollToTopButton.setOutlineAlpha(0.75f);
        this.scrollToTopButton.setShowDisabledOverlay(true);
        this.scrollToTopButton.o(14.0);
        this.scrollToTopButton.Y(10.0);
        this.scrollToTopButton.setCornerRadius(5.0f);
        this.scrollToTopButton.setCenterHorizontally(true);
        this.scrollToTopButton.setCenterVertically(true);
        this.scrollToTopButton.addClickListener(this::scrollToTop);
    }

    private PanelComponent getEffectiveScrollContainer() {
        return this.scrollContainer != null ? this.scrollContainer : this;
    }

    public void setScrollContainer(@Nullable PanelComponent scrollContainer) {
        this.scrollContainer = scrollContainer;
    }

    public void setPlaceholderSupplier(@Nullable Supplier<GuiComponent> placeholderSupplier) {
        this.placeholderSupplier = placeholderSupplier;
    }

    private void resetPagination(boolean keepExistingChildren) {
        this.nextPageIndex = this.initialPageIndex;
        this.lastPageReached = this.pageMetadata != null && this.pageMetadata.isLastPage();
        this.contentUpdated = false;
        this.componentCount = 0;
        if (!keepExistingChildren) {
            this.removeMarkedChildren();
        }
        this.cancelPendingLoad();
    }

    public void setPlaceholderCount(int placeholderCount) {
        this.placeholderCount = placeholderCount;
    }

    private void scrollToTop() {
        PanelComponent effectiveScrollContainer = this.getEffectiveScrollContainer();
        effectiveScrollContainer.b(0.0);
    }

    @Override
    public void removeChild(GuiComponent guiComponent) {
        super.removeChild(guiComponent);
        --this.componentCount;
    }

    @Override
    public void u() {
        super.u();
        if (this.scrollToTopButton.V$src$Z$1xhop3l()) {
            this.scrollToTopButton.u();
        }
    }

    private void loadNextPage() {
        this.cancelPendingLoad();
        Supplier<CompletableFuture<List<GuiComponent>>> loader = this.pageLoader;
        if (loader == null) {
            return;
        }
        Supplier<GuiComponent> loadingPlaceholderSupplier = this.placeholderSupplier;
        if (loadingPlaceholderSupplier != null) {
            int placeholdersToAdd = this.placeholderCount;
            PagedResult<?> metadata = this.pageMetadata;
            if (metadata != null && this.nextPageIndex > metadata.getTotalPages()) {
                placeholdersToAdd = (int)metadata.getTotalElements() % this.placeholderCount;
            }
            ArrayList<GuiComponent> placeholders = new ArrayList<GuiComponent>();
            for (int index = 0; index < placeholdersToAdd; ++index) {
                placeholders.add(loadingPlaceholderSupplier.get());
            }
            this.addChildren(placeholders.toArray(new GuiComponent[0]));
            this.setLoadingPlaceholders(placeholders);
        }
        AtomicReference<CompletableFuture<List<GuiComponent>>> expectedLoad = new AtomicReference<CompletableFuture<List<GuiComponent>>>();
        this.pendingLoad = loader.get().whenCompleteAsync((components, error) -> this.handleLoadCompleted(expectedLoad, components, error), (Executor)ClientSettings.UI_EXECUTOR).exceptionally(this::handleLoadFailure);
        expectedLoad.set(this.pendingLoad);
    }

    @Override
    public void Y() {
        if (this.pendingLoad != null) {
            return;
        }
        PanelComponent effectiveScrollContainer = this.getEffectiveScrollContainer();
        if (this.contentUpdated) {
            this.contentUpdated = false;
            return;
        }
        if (effectiveScrollContainer.J$src$D$hx1pag() == 0.0) {
            return;
        }
        int childCount = this.f().size();
        int visibleChildCount = 0;
        double viewportBottom = effectiveScrollContainer.n() + effectiveScrollContainer.L();
        for (GuiComponent child : this.f()) {
            if (!(child.n() + child.L() / 2.0 <= viewportBottom)) break;
            ++visibleChildCount;
        }
        int remainingChildCount = childCount - visibleChildCount;
        if (remainingChildCount <= this.loadThreshold && !this.lastPageReached) {
            this.loadNextPage();
        }
    }

    public void setPageMetadata(@Nullable PagedResult<?> pageMetadata) {
        this.pageMetadata = pageMetadata;
        if (pageMetadata != null && pageMetadata.isLastPage()) {
            this.lastPageReached = true;
        }
    }
}
