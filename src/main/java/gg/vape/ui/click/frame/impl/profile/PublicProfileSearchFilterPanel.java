package gg.vape.ui.click.frame.impl.profile;

import gg.vape.Vape;
import gg.vape.config.PublicProfileSortMode;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.component.TextInputComponentBase;
import gg.vape.ui.click.component.layout.PaddedComponent;
import gg.vape.ui.click.frame.impl.profile.DirtyTrackingPublicProfileFilterTokenSelectorComponent;
import gg.vape.ui.click.frame.impl.profile.PublicProfileFilterTokenClickListener;
import gg.vape.ui.click.frame.impl.profile.PublicProfileFilterTokenComponent;
import gg.vape.ui.click.frame.impl.profile.PublicProfileFilterTokenSelectorComponent;
import gg.vape.ui.click.frame.impl.profile.PublicProfileSelectedFilterPanel;
import gg.vape.ui.click.frame.impl.profile.PublicProfileSortModeButton;
import gg.vape.ui.click.frame.impl.profile.PublicProfilesFrame;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.value.FriendNameSuggestionProvider;
import gg.vape.value.ListValueSuggestionProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

public class PublicProfileSearchFilterPanel
extends PanelComponent {
    private final PublicProfileFilterTokenSelectorComponent tokenSelector;
    private PanelComponent sortButtonsPanel;
    private final Runnable searchCallback;
    private boolean expanded;
    private final PanelComponent suggestionsPanel;
    public static final double FILTER_WIDTH = 240.0;
    private final PanelComponent selectedFilterPanel;
    public static final double FRAME_WIDTH = 324.0;

    public TextInputComponentBase getSearchInput() {
        return this.tokenSelector.getInput();
    }

    private void clearAndCollapse() {
        this.setExpanded(false);
        this.tokenSelector.clearTokens();
    }

    void setExpanded(boolean expanded) {
        boolean changed = this.expanded != expanded;
        this.expanded = expanded;
        if (this.expanded) {
            this.sortButtonsPanel.setVisible(false);
            this.tokenSelector.getClearButton().setVisible(true);
            this.selectedFilterPanel.Y(44.0);
            this.selectedFilterPanel.setExplicitHeight(44.0);
            this.suggestionsPanel.setVisible(true);
        } else {
            this.sortButtonsPanel.setVisible(true);
            this.tokenSelector.getClearButton().setVisible(false);
            this.selectedFilterPanel.Y(26.0);
            this.selectedFilterPanel.setExplicitHeight(26.0);
            this.suggestionsPanel.setVisible(false);
        }
        this.tokenSelector.H(true);
        this.selectedFilterPanel.H(true);
        if (changed) {
            this.refreshSuggestions();
        }
    }

    private static void selectSortMode(PublicProfileSortMode publicProfileSortMode) {
        PublicProfilesFrame publicProfilesFrame = ClientSettings.getFrame(PublicProfilesFrame.class);
        if (publicProfilesFrame.Z$src$Lgg_vape_config_PublicProfileSortMode_$18pvsyy() != publicProfileSortMode) {
            publicProfilesFrame.l(publicProfileSortMode);
            publicProfilesFrame.P$src$Lgg_vape_ui_click_frame_impl_profile_PublicProfi$1ezbs2g().reload();
        }
    }

    private void executeSearch() {
        this.refreshSuggestions();
        this.searchCallback.run();
    }

    void renderFilterBackground() {
        if (this.expanded) {
            GuiRenderPrimitives.B(this.selectedFilterPanel.G$src$D$1b2f02a(), this.selectedFilterPanel.n(), this.selectedFilterPanel.A(), this.selectedFilterPanel.L(), PublicProfileSearchFilterPanel.J.l, 2.0f);
            GuiRenderPrimitives.u(this.selectedFilterPanel.G$src$D$1b2f02a() + 5.0, this.selectedFilterPanel.n() + this.tokenSelector.L(), this.selectedFilterPanel.G$src$D$1b2f02a() + this.selectedFilterPanel.A() - 5.0, this.selectedFilterPanel.n() + this.tokenSelector.L(), 0.75f, PublicProfileSearchFilterPanel.J.y);
            GuiRenderPrimitives.P(this.selectedFilterPanel.G$src$D$1b2f02a(), this.selectedFilterPanel.n(), this.selectedFilterPanel.A(), this.selectedFilterPanel.L(), PublicProfileSearchFilterPanel.J.y, 2.0f, 0.75f, 1.0f);
        } else {
            GuiRenderPrimitives.P(this.tokenSelector.G$src$D$1b2f02a(), this.tokenSelector.n(), this.tokenSelector.A(), this.tokenSelector.L(), PublicProfileSearchFilterPanel.J.y, 2.0f, 0.75f, 1.0f);
        }
    }

    public PublicProfileFilterTokenSelectorComponent getTokenSelector() {
        return this.tokenSelector;
    }

    private void registerInputListener() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        this.tokenSelector.getInput().getKeyTypedListeners().add(0, (character, keyCode) -> this.handleSearchInput(atomicBoolean, character, keyCode));
    }

    private void runDebouncedSearch(AtomicBoolean searchPending, String initialQuery) {
        while (searchPending.get()) {
            try {
                Thread.sleep(200L);
            }
            catch (InterruptedException interruptedException) {
                Vape.logThrowable(interruptedException);
            }
            String string2 = this.tokenSelector.getInput().getText().trim();
            if (string2.equalsIgnoreCase(initialQuery)) {
                if (!string2.isEmpty()) continue;
                searchPending.set(false);
                return;
            }
            try {
                this.searchCallback.run();
            }
            catch (Throwable throwable) {
                Vape.logThrowable(throwable);
            }
            searchPending.set(false);
            return;
        }
    }

    private void handleSearchInput(AtomicBoolean searchPending, char character, int keyCode) {
        ClientSettings.UI_EXECUTOR.execute(this::refreshSuggestions);
        if (!searchPending.get()) {
            searchPending.set(true);
            String string = this.tokenSelector.getInput().getText().trim();
            CompletableFuture.runAsync(() -> this.runDebouncedSearch(searchPending, string));
        }
    }

    private void refreshSuggestions() {
        ListValueSuggestionProvider listValueSuggestionProvider;
        boolean bl;
        String string = this.tokenSelector.getInput().getText().trim();
        boolean bl2 = bl = !string.isEmpty();
        List<String> arrayList = !bl ? new ArrayList<String>(Vape.INSTANCE.getPublicProfileManager().getTags()) : ((listValueSuggestionProvider = this.tokenSelector.getInput().getSuggestionProvider()) != null ? listValueSuggestionProvider.getSuggestions() : new ArrayList<String>());
        this.suggestionsPanel.removeMarkedChildren();
        double d = 0.0;
        for (String string2 : arrayList) {
            if (this.tokenSelector.containsToken(string2)) continue;
            PublicProfileFilterTokenComponent publicProfileFilterTokenComponent = new PublicProfileFilterTokenComponent(string2);
            PaddedComponent paddedComponent = new PaddedComponent(0.0, 0.0, 1.0, 1.0, publicProfileFilterTokenComponent);
            AtomicBoolean atomicBoolean = new AtomicBoolean(false);
            publicProfileFilterTokenComponent.addMouseListener(new PublicProfileFilterTokenClickListener(this, atomicBoolean, paddedComponent, publicProfileFilterTokenComponent));
            double d2 = paddedComponent.A();
            if (d + d2 >= this.suggestionsPanel.A()) break;
            d += d2;
            this.suggestionsPanel.h(paddedComponent, "widthwrap");
        }
    }

    PanelComponent getSuggestionsPanel() {
        return this.suggestionsPanel;
    }

    public PublicProfileSearchFilterPanel(double d, Runnable runnable) {
        super(d, 42.0);
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        this.searchCallback = runnable;
        this.buildSortButtons();
        Runnable selectorChanged = this::executeSearch;
        this.tokenSelector = new DirtyTrackingPublicProfileFilterTokenSelectorComponent(this, "Search Profile / Share Code", selectorChanged, d, 20.0, false, false);
        this.tokenSelector.getInput().setSuggestionProvider(new FriendNameSuggestionProvider());
        this.tokenSelector.getClearButton().setClickListener(this::clearAndCollapse);
        this.suggestionsPanel = new PanelComponent(d - 8.0, 16.0);
        this.suggestionsPanel.setShowDisabledOverlay(false);
        this.selectedFilterPanel = new PublicProfileSelectedFilterPanel(this, d, 26.0);
        this.selectedFilterPanel.setShowDisabledOverlay(false);
        this.selectedFilterPanel.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        this.selectedFilterPanel.h(this.tokenSelector, new Object[0]);
        this.selectedFilterPanel.h(new SpacerComponent(1.0, 4.0), new Object[0]);
        this.selectedFilterPanel.h(new PaddedComponent(3.0, 0.0, 4.0, 4.0, this.suggestionsPanel), new Object[0]);
        this.h(this.selectedFilterPanel, new Object[0]);
        this.h(this.sortButtonsPanel, new Object[0]);
        this.registerInputListener();
        this.setExpanded(false);
    }

    private void buildSortButtons() {
        this.sortButtonsPanel = new PanelComponent(this.A(), 20.0);
        this.sortButtonsPanel.setShowDisabledOverlay(false);
        this.sortButtonsPanel.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("widthwrap");
        for (PublicProfileSortMode publicProfileSortMode : PublicProfileSortMode.VALUES) {
            PublicProfileSortModeButton publicProfileSortModeButton = new PublicProfileSortModeButton(publicProfileSortMode.getDisplayName().toUpperCase(), 0.7, PublicProfileSearchFilterPanel.J.B, PublicProfileSearchFilterPanel.J.O, publicProfileSortMode);
            publicProfileSortModeButton.setCornerRadius(7.0f);
            publicProfileSortModeButton.setUseAlternateFont(true);
            publicProfileSortModeButton.setDeriveTextColorFromBackground(false);
            double d = publicProfileSortModeButton.getTextWidth();
            this.getClass();
            publicProfileSortModeButton.o(d + (double)(5.0f * 3.0f));
            publicProfileSortModeButton.Y(14.0);
            publicProfileSortModeButton.setClickListener(() -> PublicProfileSearchFilterPanel.selectSortMode(publicProfileSortMode));
            this.sortButtonsPanel.h(publicProfileSortModeButton, new Object[0]);
        }
    }
}
