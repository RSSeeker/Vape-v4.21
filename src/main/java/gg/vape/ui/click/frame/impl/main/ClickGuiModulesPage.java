package gg.vape.ui.click.frame.impl.main;

import gg.vape.Vape;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.ModuleDisplayScope;
import gg.vape.module.none.ClientSettings;
import gg.vape.module.render.Search;
import gg.vape.module.render.hud.HudModule;
import gg.vape.ui.click.animation.ColorAnimation;
import gg.vape.ui.click.component.FriendModuleInteractiveComponent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.LabeledTextInputComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.SimpleTextLabelComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.component.input.BindValueRowComponent;
import gg.vape.ui.click.component.layout.PaddedComponent;
import gg.vape.ui.click.component.value.SearchBlockListComponent;
import gg.vape.ui.click.component.value.ValueComponentFactory;
import gg.vape.ui.click.component.value.ValueComponentMode;
import gg.vape.ui.click.frame.FrameScrollbarPlacement;
import gg.vape.utils.StringUtils;
import gg.vape.value.BooleanValue;
import gg.vape.value.ListValue;
import gg.vape.value.Value;
import java.awt.Color;
import java.util.ArrayList;
import org.jetbrains.annotations.Nullable;

public class ClickGuiModulesPage
extends ClickGuiPageBase {
    final ClickGuiMainFrame mainFrame;
    private ClickGuiModuleViewMode viewMode;
    private final Runnable pageResetAction;
    private ClickGuiContentPanel moduleContent;
    private Category selectedCategory = Category.FAVORITES;
    private ClickGuiContentPanel legitContent;
    private String legitSearchQuery = "";
    private LabeledTextInputComponent moduleSearchInput;
    private LabeledTextInputComponent legitSearchInput;
    private String moduleSearchQuery = "";
    private final ClickGuiMacrosController macrosController;
    private boolean reorderingFavorites;

    private void lambda$filterModuleButtons$16(ClickGuiModuleCardComponent clickGuiModuleCardComponent) {
        this.selectModuleCard(clickGuiModuleCardComponent);
    }

    private void selectModuleCard(@Nullable ClickGuiModuleCardComponent clickGuiModuleCardComponent) {
        if (this.moduleContent == null) {
            return;
        }
        if (clickGuiModuleCardComponent != null) {
            if (clickGuiModuleCardComponent.isSelected()) {
                this.mainFrame.closeActiveOverlay();
                clickGuiModuleCardComponent.setSelected(false);
                clickGuiModuleCardComponent = null;
            } else {
                this.openModuleSettings(clickGuiModuleCardComponent.getModule(), clickGuiModuleCardComponent);
            }
        }
        for (GuiComponent guiComponent : this.moduleContent.f()) {
            ClickGuiModuleCardComponent clickGuiModuleCardComponent2;
            if (!(guiComponent instanceof PaddedComponent) || (clickGuiModuleCardComponent2 = ((PaddedComponent)guiComponent).t(ClickGuiModuleCardComponent.class)) == null) continue;
            boolean bl = clickGuiModuleCardComponent2 == clickGuiModuleCardComponent;
            clickGuiModuleCardComponent2.setSelected(bl);
            clickGuiModuleCardComponent2.setDimmed(!bl && clickGuiModuleCardComponent != null);
        }
    }

    @Override
    public void K() {
        super.K();
        if (this.pageResetAction != null) {
            this.mainFrame.removeContentOverlayCallback(this.pageResetAction);
        }
    }

    private void renderCurrentView() {
        this.getMainContent().removeMarkedChildren();
        switch (ClickGuiModuleViewModeSwitchMap.C[this.viewMode.ordinal()]) {
            case 1: {
                this.renderMacrosView();
                break;
            }
            case 2: {
                this.renderModuleView(true);
                break;
            }
            default: {
                this.renderModuleView(false);
            }
        }
        this.getMainContent().H(true);
    }

    private void lambda$openModuleSettings$22() {
        this.mainFrame.closeActiveOverlay();
        this.selectModuleCard(null);
    }

    private void openModuleSettings(Mod mod, ClickGuiModuleCardComponent clickGuiModuleCardComponent) {
        ClickGuiModulesSidecarPanel clickGuiModulesSidecarPanel = new ClickGuiModulesSidecarPanel(null);
        clickGuiModulesSidecarPanel.setLeadingIconKey("new" + mod.getCategory().getName().toLowerCase());
        clickGuiModulesSidecarPanel.setDividerVisible(false);
        clickGuiModulesSidecarPanel.setBackAction(this::lambda$openModuleSettings$22);
        clickGuiModulesSidecarPanel.setFavoriteVisible(true);
        clickGuiModulesSidecarPanel.setFavoriteHighlighted(mod.isFavorite());
        clickGuiModulesSidecarPanel.setFavoriteAction(() -> ClickGuiModulesPage.lambda$openModuleSettings$23(mod, clickGuiModulesSidecarPanel, clickGuiModuleCardComponent));
        clickGuiModulesSidecarPanel.setToggleVisible(true);
        clickGuiModulesSidecarPanel.setToggleAction(null);
        clickGuiModulesSidecarPanel.setToggleLabelSupplier(() -> ClickGuiModulesPage.lambda$openModuleSettings$24(mod));
        this.mainFrame.showOverlay(ClickGuiOverlaySpec.builder().title(mod.getName()).placement(ClickGuiOverlayPlacement.DOCKED_SHIFT).sidecar(clickGuiModulesSidecarPanel).initializeContent(arg_0 -> this.lambda$openModuleSettings$25(mod, arg_0)).backdropEnabled(false).build());
    }

    private static int lambda$filterModuleButtons$14(Mod mod, Mod mod2) {
        return Boolean.compare(mod2.isFavorite(), mod.isFavorite());
    }

    private static String lambda$openLegitModuleSettings$19(HudModule hudModule) {
        return hudModule.isEnabled() ? "ON" : "OFF";
    }

    public boolean clearActiveSearch() {
        switch (ClickGuiModuleViewModeSwitchMap.C[this.viewMode.ordinal()]) {
            case 2: {
                if (this.legitSearchInput == null) break;
                this.legitSearchInput.setText("");
                this.legitSearchInput.requestFocus();
                return true;
            }
            case 3: {
                if (this.moduleSearchInput == null) break;
                this.moduleSearchInput.setText("");
                this.moduleSearchInput.requestFocus();
                return true;
            }
        }
        return false;
    }

    private static String lambda$openModuleSettings$24(Mod mod) {
        return mod.isEnabled() ? "ON" : "OFF";
    }

    public static String setModuleSearchQuery(ClickGuiModulesPage page, String query) {
        page.moduleSearchQuery = query;
        return page.moduleSearchQuery;
    }

    private static void lambda$openModuleSettings$23(Mod mod, ClickGuiModulesSidecarPanel clickGuiModulesSidecarPanel, ClickGuiModuleCardComponent clickGuiModuleCardComponent) {
        mod.K(!mod.isFavorite());
        clickGuiModulesSidecarPanel.setFavoriteHighlighted(mod.isFavorite());
        clickGuiModuleCardComponent.setFavoriteHighlighted(!mod.isFavorite());
    }

    private void lambda$filterModuleButtons$15(ClickGuiLegitModuleCardComponent clickGuiLegitModuleCardComponent) {
        this.selectLegitModuleCard(clickGuiLegitModuleCardComponent);
    }

    private void lambda$new$0() {
        this.macrosController.selectMacroCard(null);
        this.macrosController.rebuildMacroCards();
        this.selectModuleCard(null);
        if (this.moduleContent != null) {
            this.rebuildModuleCards(this.moduleContent, false);
        }
        this.selectLegitModuleCard((ClickGuiLegitModuleCardComponent)null);
        if (this.legitContent != null) {
            this.rebuildModuleCards(this.legitContent, true);
        }
    }

    double getAvailableContentWidth() {
        return Math.max(0.0, this.getMainContent().A() - 0.0);
    }

    private void rebuildModuleCards(ClickGuiContentPanel clickGuiContentPanel, boolean bl) {
        boolean bl2;
        Object object2;
        if (clickGuiContentPanel == null) {
            return;
        }
        String string = null;
        if (bl) {
            for (GuiComponent guiComponent : clickGuiContentPanel.f()) {
                if (!(guiComponent instanceof ClickGuiLegitModuleCardComponent) || !((ClickGuiLegitModuleCardComponent)(object2 = (ClickGuiLegitModuleCardComponent)guiComponent)).isSelected()) continue;
                string = ((ClickGuiLegitModuleCardComponent)object2).getModule().getName();
                break;
            }
        } else {
            for (GuiComponent guiComponent : clickGuiContentPanel.f()) {
                if (!(guiComponent instanceof PaddedComponent) || (object2 = ((PaddedComponent)guiComponent).t(ClickGuiModuleCardComponent.class)) == null || !((ClickGuiModuleCardComponent)object2).isSelected()) continue;
                string = ((ClickGuiModuleCardComponent)object2).getModule().getName();
                break;
            }
        }
        double d = clickGuiContentPanel.J$src$D$hx1pag();
        clickGuiContentPanel.removeMarkedChildren();
        object2 = bl ? (this.legitSearchInput != null ? this.legitSearchInput.getText() : "") : (this.moduleSearchInput != null ? this.moduleSearchInput.getText() : "");
        String string2 = StringUtils.y(object2 == null ? "" : ((String)object2).trim());
        boolean bl3 = bl2 = !string2.isEmpty();
        if (bl2) {
            ArrayList<Mod> arrayList = new ArrayList<>();
            if (bl) {
                ArrayList<Mod> arrayList2 = Vape.INSTANCE.getModManager().getActiveModuleList();
                for (Mod object4 : arrayList2) {
                    HudModule hudModule;
                    if (!(object4 instanceof HudModule) || !StringUtils.y((hudModule = (HudModule)object4).getName()).contains(string2)) continue;
                    arrayList.add(hudModule);
                }
            } else {
                arrayList = new ArrayList<Mod>(Vape.INSTANCE.getModManager().collectMods());
                arrayList.removeIf(ClickGuiModulesPage::lambda$filterModuleButtons$11);
                arrayList.removeIf(ClickGuiModulesPage::lambda$filterModuleButtons$12);
                arrayList.removeIf(arg_0 -> ClickGuiModulesPage.lambda$filterModuleButtons$13(string2, arg_0));
                arrayList.sort(ClickGuiModulesPage::lambda$filterModuleButtons$14);
            }
            int n = 0;
            for (Mod mod : arrayList) {
                if (bl) {
                    ClickGuiLegitModuleCardComponent clickGuiLegitModuleCardComponent = new ClickGuiLegitModuleCardComponent((HudModule)mod);
                    clickGuiLegitModuleCardComponent.o(clickGuiContentPanel.A() / 3.0);
                    clickGuiLegitModuleCardComponent.setSettingsAction(() -> this.lambda$filterModuleButtons$15(clickGuiLegitModuleCardComponent));
                    clickGuiContentPanel.h(clickGuiLegitModuleCardComponent, n % 3 == 2 ? "wrap" : "widthwrap");
                    ++n;
                    continue;
                }
                ClickGuiModuleCardComponent clickGuiModuleCardComponent = new ClickGuiModuleCardComponent(mod);
                clickGuiModuleCardComponent.o(clickGuiContentPanel.A());
                clickGuiModuleCardComponent.setSettingsAction(() -> this.lambda$filterModuleButtons$16(clickGuiModuleCardComponent));
                clickGuiModuleCardComponent.setFavoriteControlVisible(bl2);
                clickGuiModuleCardComponent.setUnsafeBadgeEnabled(bl2);
                if (this.reorderingFavorites && this.selectedCategory == Category.FAVORITES) {
                    clickGuiModuleCardComponent.setReordering(true);
                    clickGuiModuleCardComponent.setReorderAction(this::lambda$filterModuleButtons$17);
                }
                clickGuiContentPanel.h(new PaddedComponent(0.0, 3.0, 0.0, 0.0, clickGuiModuleCardComponent), new Object[0]);
            }
            clickGuiContentPanel.H(true);
            boolean bl4 = false;
            if (string != null) {
                if (bl) {
                    ClickGuiLegitModuleCardComponent clickGuiLegitModuleCardComponent;
                    for (GuiComponent guiComponent : clickGuiContentPanel.f()) {
                        if (!(guiComponent instanceof ClickGuiLegitModuleCardComponent) || !(clickGuiLegitModuleCardComponent = (ClickGuiLegitModuleCardComponent)guiComponent).getModule().getName().equals(string)) continue;
                        clickGuiLegitModuleCardComponent.setSelected(true);
                        bl4 = true;
                        break;
                    }
                    if (bl4) {
                        for (GuiComponent guiComponent : clickGuiContentPanel.f()) {
                            if (!(guiComponent instanceof ClickGuiLegitModuleCardComponent)) continue;
                            clickGuiLegitModuleCardComponent = (ClickGuiLegitModuleCardComponent)guiComponent;
                            clickGuiLegitModuleCardComponent.setDimmed(!clickGuiLegitModuleCardComponent.isSelected());
                        }
                    } else {
                        this.mainFrame.closeActiveOverlay();
                    }
                } else {
                    ClickGuiModuleCardComponent clickGuiModuleCardComponent;
                    for (GuiComponent guiComponent : clickGuiContentPanel.f()) {
                        if (!(guiComponent instanceof PaddedComponent) || (clickGuiModuleCardComponent = ((PaddedComponent)guiComponent).t(ClickGuiModuleCardComponent.class)) == null || !clickGuiModuleCardComponent.getModule().getName().equals(string)) continue;
                        clickGuiModuleCardComponent.setSelected(true);
                        bl4 = true;
                        break;
                    }
                    if (bl4) {
                        for (GuiComponent guiComponent : clickGuiContentPanel.f()) {
                            if (!(guiComponent instanceof PaddedComponent) || (clickGuiModuleCardComponent = ((PaddedComponent)guiComponent).t(ClickGuiModuleCardComponent.class)) == null) continue;
                            clickGuiModuleCardComponent.setDimmed(!clickGuiModuleCardComponent.isSelected());
                        }
                    } else {
                        this.mainFrame.closeActiveOverlay();
                    }
                }
            }
            clickGuiContentPanel.b(d);
            return;
        }
        ArrayList<Mod> arrayList = new ArrayList<>();
        if (bl) {
            ArrayList<Mod> arrayList3 = Vape.INSTANCE.getModManager().getActiveModuleList();
            for (Mod mod : arrayList3) {
                if (!(mod instanceof HudModule)) continue;
                HudModule hudModule = (HudModule)mod;
                arrayList.add(hudModule);
            }
        } else if (this.selectedCategory == Category.FAVORITES) {
            arrayList = new ArrayList<Mod>(Vape.INSTANCE.getModuleProfileMetadataCodec().getSelectedModules());
        } else {
            arrayList = new ArrayList<Mod>(Vape.INSTANCE.getModManager().collectMods());
            arrayList.removeIf(ClickGuiModulesPage::lambda$filterModuleButtons$11);
            arrayList.removeIf(ClickGuiModulesPage::lambda$filterModuleButtons$12);
        }
        int n = 0;
        for (Mod mod : arrayList) {
            if (!bl && (this.selectedCategory != Category.FAVORITES ? mod.getCategory() != this.selectedCategory : !mod.isFavorite())) continue;
            if (bl) {
                ClickGuiLegitModuleCardComponent clickGuiLegitModuleCardComponent = new ClickGuiLegitModuleCardComponent((HudModule)mod);
                clickGuiLegitModuleCardComponent.o(clickGuiContentPanel.A() / 3.0);
                clickGuiLegitModuleCardComponent.setSettingsAction(() -> this.lambda$filterModuleButtons$15(clickGuiLegitModuleCardComponent));
                clickGuiContentPanel.h(clickGuiLegitModuleCardComponent, n % 3 == 2 ? "wrap" : "widthwrap");
                ++n;
                continue;
            }
            ClickGuiModuleCardComponent clickGuiModuleCardComponent = new ClickGuiModuleCardComponent(mod);
            clickGuiModuleCardComponent.o(clickGuiContentPanel.A());
            clickGuiModuleCardComponent.setSettingsAction(() -> this.lambda$filterModuleButtons$16(clickGuiModuleCardComponent));
            clickGuiModuleCardComponent.setFavoriteControlVisible(bl2);
            clickGuiModuleCardComponent.setUnsafeBadgeEnabled(bl2);
            if (this.reorderingFavorites && this.selectedCategory == Category.FAVORITES) {
                clickGuiModuleCardComponent.setReordering(true);
                clickGuiModuleCardComponent.setReorderAction(this::lambda$filterModuleButtons$17);
            }
            clickGuiContentPanel.h(new PaddedComponent(0.0, 3.0, 0.0, 0.0, clickGuiModuleCardComponent), new Object[0]);
        }
        clickGuiContentPanel.H(true);
        boolean bl5 = false;
        if (string != null) {
            if (bl) {
                ClickGuiLegitModuleCardComponent clickGuiLegitModuleCardComponent;
                for (GuiComponent guiComponent : clickGuiContentPanel.f()) {
                    if (!(guiComponent instanceof ClickGuiLegitModuleCardComponent) || !(clickGuiLegitModuleCardComponent = (ClickGuiLegitModuleCardComponent)guiComponent).getModule().getName().equals(string)) continue;
                    clickGuiLegitModuleCardComponent.setSelected(true);
                    bl5 = true;
                    break;
                }
                if (bl5) {
                    for (GuiComponent guiComponent : clickGuiContentPanel.f()) {
                        if (!(guiComponent instanceof ClickGuiLegitModuleCardComponent)) continue;
                        clickGuiLegitModuleCardComponent = (ClickGuiLegitModuleCardComponent)guiComponent;
                        clickGuiLegitModuleCardComponent.setDimmed(!clickGuiLegitModuleCardComponent.isSelected());
                    }
                } else {
                    this.mainFrame.closeActiveOverlay();
                }
            } else {
                ClickGuiModuleCardComponent clickGuiModuleCardComponent;
                for (GuiComponent guiComponent : clickGuiContentPanel.f()) {
                    if (!(guiComponent instanceof PaddedComponent) || (clickGuiModuleCardComponent = ((PaddedComponent)guiComponent).t(ClickGuiModuleCardComponent.class)) == null || !clickGuiModuleCardComponent.getModule().getName().equals(string)) continue;
                    clickGuiModuleCardComponent.setSelected(true);
                    bl5 = true;
                    break;
                }
                if (bl5) {
                    for (GuiComponent guiComponent : clickGuiContentPanel.f()) {
                        if (!(guiComponent instanceof PaddedComponent) || (clickGuiModuleCardComponent = ((PaddedComponent)guiComponent).t(ClickGuiModuleCardComponent.class)) == null) continue;
                        clickGuiModuleCardComponent.setDimmed(!clickGuiModuleCardComponent.isSelected());
                    }
                } else {
                    this.mainFrame.closeActiveOverlay();
                }
            }
        }
        clickGuiContentPanel.b(d);
    }

    private static boolean lambda$filterModuleButtons$12(Mod mod) {
        return mod.getModuleDisplayScope() == ModuleDisplayScope.FRAMES_ONLY;
    }

    private static void lambda$openLegitModuleSettings$20(ClickGuiSidecarPanelBase clickGuiSidecarPanelBase) {
        clickGuiSidecarPanelBase.setDividerVisible(false);
    }

    private void lambda$openLegitModuleSettings$21(HudModule hudModule, PanelComponent panelComponent) {
        this.populateSettings(hudModule, panelComponent);
    }

    @Override
    public void Z$src$V$15w0jcm() {
        super.Z$src$V$15w0jcm();
        if (this.pageResetAction != null) {
            this.mainFrame.removeContentOverlayCallback(this.pageResetAction);
            this.mainFrame.addContentOverlayCallback(this.pageResetAction);
        }
    }

    public double B$src$D$yaoqbo() {
        GuiComponent guiComponent = this.moduleContent;
        if (guiComponent == null || !guiComponent.V$src$Z$1xhop3l()) {
            guiComponent = this.moduleSearchInput;
        }
        if (guiComponent == null) {
            return Double.MAX_VALUE;
        }
        return Math.max(0.0, guiComponent.G$src$D$1b2f02a() - this.mainFrame.G$src$D$1b2f02a());
    }

    private static Integer lambda$renderCategoryButtons$3(Category category) {
        return Vape.INSTANCE.getModManager().countEnabledModules(category);
    }

    private void lambda$filterModuleButtons$17() {
        this.rebuildModuleCards(this.moduleContent, false);
    }

    private void lambda$renderModuleContent$10(LabeledTextInputComponent labeledTextInputComponent) {
        this.reorderingFavorites = false;
        labeledTextInputComponent.setEditingFavorites(false);
        labeledTextInputComponent.setBorderAnimation(ColorAnimation.Y(ClickGuiModulesPage.J.s));
        this.rebuildModuleCards(this.moduleContent, false);
    }

    public static ClickGuiContentPanel getModuleContent(ClickGuiModulesPage page) {
        return page.moduleContent;
    }

    private static boolean lambda$filterModuleButtons$13(String string, Mod mod) {
        String string2 = StringUtils.y(mod.getName());
        if (mod.getCategory() == Category.OTHER) {
            return !string2.equals(string);
        }
        return !string2.contains(string);
    }

    private void renderMacrosView() {
        this.macrosController.renderMacroView();
    }

    private void lambda$openModuleSettings$25(Mod mod, PanelComponent panelComponent) {
        this.populateSettings(mod, panelComponent);
    }

    private Boolean lambda$renderCategoryButtons$5() {
        return this.viewMode == ClickGuiModuleViewMode.MACROS;
    }

    private Boolean lambda$renderCategoryButtons$1(Category category) {
        return this.viewMode == ClickGuiModuleViewMode.MODULE_CATEGORY && this.selectedCategory == category;
    }


    private void renderModuleView(boolean bl) {
        ClickGuiContentPanel clickGuiContentPanel;
        LabeledTextInputComponent labeledTextInputComponent = bl ? (this.legitSearchInput = new ClickGuiModulesFilterInputComponent(this, "Search legit modules...")) : (this.moduleSearchInput = new ClickGuiModulesSearchInputComponent(this, "Search modules..."));
        labeledTextInputComponent.setBorderThickness(0.75f);
        labeledTextInputComponent.setCornerRadius(4.0f);
        labeledTextInputComponent.setBorderAnimation(ColorAnimation.Y(ClickGuiModulesPage.J.s));
        labeledTextInputComponent.setBackgroundColorOrNull(null);
        labeledTextInputComponent.setSearchIconTrailing(false);
        labeledTextInputComponent.o(bl ? this.getMainContent().A() : this.getAvailableContentWidth());
        labeledTextInputComponent.setLeftInset(0.0f);
        labeledTextInputComponent.setHorizontalInset(0.0);
        labeledTextInputComponent.setRightInset(0.0f);
        labeledTextInputComponent.setVerticalInset(0.0f);
        labeledTextInputComponent.setUseExplicitHeight(true);
        labeledTextInputComponent.Y(16.0);
        if (!bl && this.selectedCategory == Category.FAVORITES) {
            labeledTextInputComponent.setShowEditButton(true);
            labeledTextInputComponent.getEditButton().addClickListener(() -> this.lambda$renderModuleContent$9(labeledTextInputComponent));
            labeledTextInputComponent.getDoneLabel().addClickListener(() -> this.lambda$renderModuleContent$10(labeledTextInputComponent));
            if (this.reorderingFavorites) {
                labeledTextInputComponent.setEditingFavorites(true);
                labeledTextInputComponent.setBorderAnimation(ColorAnimation.Y(ClickGuiModulesPage.J.I));
                labeledTextInputComponent.setText("");
            }
        }
        this.getMainContent().h(labeledTextInputComponent, new Object[0]);
        this.getMainContent().h(new SpacerComponent(0.0, 6.0), new Object[0]);
        if (bl) {
            this.legitContent = new ClickGuiContentPanel(this.getMainContent().A(), this.getMainContent().L() - this.getMainContent().l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().y() - 1.0);
            this.legitContent.setShowDisabledOverlay(false);
            this.legitContent.t(this.legitContent.L());
            this.legitContent.F(FrameScrollbarPlacement.OUTSIDE);
            this.legitContent.setResponsiveWidthEnabled(true);
            clickGuiContentPanel = this.legitContent;
        } else {
            this.moduleContent = new ClickGuiContentPanel(this.getAvailableContentWidth(), this.getMainContent().L() - this.getMainContent().l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().y() - 1.0);
            this.moduleContent.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
            this.moduleContent.setShowDisabledOverlay(false);
            this.moduleContent.t(this.moduleContent.L());
            this.moduleContent.F(FrameScrollbarPlacement.OUTSIDE);
            clickGuiContentPanel = this.moduleContent;
        }
        this.getMainContent().h(clickGuiContentPanel, new Object[0]);
        clickGuiContentPanel.E(true);
        if (bl) {
            if (this.legitSearchQuery != null && !this.legitSearchQuery.isEmpty()) {
                this.legitSearchInput.setText(this.legitSearchQuery);
            } else {
                this.rebuildModuleCards(clickGuiContentPanel, true);
            }
        } else if (this.moduleSearchQuery != null && !this.moduleSearchQuery.isEmpty()) {
            this.moduleSearchInput.setText(this.moduleSearchQuery);
        } else {
            this.rebuildModuleCards(clickGuiContentPanel, false);
        }
    }

    private static boolean lambda$filterModuleButtons$11(Mod mod) {
        return mod.getCategory() == Category.NONE;
    }

    private void lambda$renderCategoryButtons$4(Category category) {
        if (this.viewMode != ClickGuiModuleViewMode.MODULE_CATEGORY || this.selectedCategory != category) {
            this.viewMode = ClickGuiModuleViewMode.MODULE_CATEGORY;
            this.selectedCategory = category;
            this.moduleSearchQuery = "";
            this.reorderingFavorites = false;
            this.renderCurrentView();
        }
    }

    public static ClickGuiContentPanel getLegitContent(ClickGuiModulesPage page) {
        return page.legitContent;
    }

    private static Integer lambda$renderCategoryButtons$2() {
        return Vape.INSTANCE.getModuleProfileMetadataCodec().getVisibleModuleCount();
    }

    private void renderNavigation() {
        this.getSidebarContent().removeMarkedChildren();
        ArrayList<FriendModuleInteractiveComponent> arrayList = new ArrayList<>();
        for (Category object2 : Category.values()) {
            if (object2 == Category.NONE || object2 == Category.OTHER) continue;
            FriendModuleInteractiveComponent friendModuleInteractiveComponent = new FriendModuleInteractiveComponent(object2.getName(), object2.getIconKey(), () -> this.lambda$renderCategoryButtons$1(object2), ClientSettings.INSTANCE.showEnabledCount.getEffectiveValue().booleanValue() ? (object2 == Category.FAVORITES ? ClickGuiModulesPage::lambda$renderCategoryButtons$2 : () -> ClickGuiModulesPage.lambda$renderCategoryButtons$3(object2)) : null, "expandarrow");
            friendModuleInteractiveComponent.setSelectedBadgeColor(Color.WHITE);
            friendModuleInteractiveComponent.setBadgeColor(ClickGuiModulesPage.J.h);
            if (object2 == Category.FAVORITES) {
                friendModuleInteractiveComponent.getCountBadge().setBackgroundColor(new Color(255, 255, 255, 7));
                friendModuleInteractiveComponent.setSelectedBadgeColor(Color.WHITE);
                friendModuleInteractiveComponent.setBadgeColor(ClickGuiModulesPage.J.h);
                friendModuleInteractiveComponent.getHoverBackgroundAnimation().setStartColor(ClickGuiModulesPage.J.t);
                friendModuleInteractiveComponent.getHoverBackgroundAnimation().setEndColor(ClickGuiModulesPage.J.t);
                friendModuleInteractiveComponent.setNormalPrimaryColor(ClickGuiModulesPage.J.I);
                friendModuleInteractiveComponent.setSelectedPrimaryColor(ClickGuiModulesPage.J.I);
                friendModuleInteractiveComponent.setHoverPrimaryColor(ClickGuiModulesPage.J.Y);
            } else {
                arrayList.add(friendModuleInteractiveComponent);
            }
            friendModuleInteractiveComponent.addClickListener(() -> this.lambda$renderCategoryButtons$4(object2));
            this.getSidebarContent().h(new PaddedComponent(0.0, object2 == Category.FAVORITES ? 3.0 : 2.0, 0.0, 0.0, friendModuleInteractiveComponent), new Object[0]);
        }
        FriendModuleInteractiveComponent friendModuleInteractiveComponent = new FriendModuleInteractiveComponent("Macros", "newmacros", this::lambda$renderCategoryButtons$5, null, "expandarrow");
        arrayList.add(friendModuleInteractiveComponent);
        friendModuleInteractiveComponent.addClickListener(this::lambda$renderCategoryButtons$6);
        this.getSidebarContent().h(new PaddedComponent(0.0, 2.0, 0.0, 0.0, friendModuleInteractiveComponent), new Object[0]);
        FriendModuleInteractiveComponent friendModuleInteractiveComponent2 = new FriendModuleInteractiveComponent("Legit", "legit_primary", this::lambda$renderCategoryButtons$7, null, "expandarrow");
        arrayList.add(friendModuleInteractiveComponent2);
        friendModuleInteractiveComponent2.addClickListener(this::lambda$renderCategoryButtons$8);
        this.getSidebarContent().h(new PaddedComponent(0.0, 2.0, 0.0, 0.0, friendModuleInteractiveComponent2), new Object[0]);
        for (FriendModuleInteractiveComponent friendModuleInteractiveComponent3 : arrayList) {
            friendModuleInteractiveComponent3.getCountBadge().setBackgroundColor(new Color(255, 255, 255, 7));
            friendModuleInteractiveComponent3.setSelectedBadgeColor(Color.WHITE);
            friendModuleInteractiveComponent3.setBadgeColor(ClickGuiModulesPage.J.h);
            friendModuleInteractiveComponent3.getHoverBackgroundAnimation().setStartColor(ClickGuiModulesPage.J.t);
            friendModuleInteractiveComponent3.getHoverBackgroundAnimation().setEndColor(ClickGuiModulesPage.J.t);
        }
        this.getSidebarContent().H(true);
    }

    public static String setLegitSearchQuery(ClickGuiModulesPage page, String query) {
        page.legitSearchQuery = query;
        return page.legitSearchQuery;
    }

    private void lambda$renderModuleContent$9(LabeledTextInputComponent labeledTextInputComponent) {
        this.reorderingFavorites = true;
        labeledTextInputComponent.setEditingFavorites(true);
        labeledTextInputComponent.setBorderAnimation(ColorAnimation.Y(ClickGuiModulesPage.J.I));
        labeledTextInputComponent.setText("");
        this.rebuildModuleCards(this.moduleContent, false);
    }

    private void openLegitModuleSettings(HudModule hudModule, ClickGuiLegitModuleCardComponent clickGuiLegitModuleCardComponent) {
        ClickGuiModulesSidecarPanel clickGuiModulesSidecarPanel = new ClickGuiModulesSidecarPanel(null);
        clickGuiModulesSidecarPanel.setDividerVisible(false);
        clickGuiModulesSidecarPanel.setBackAction(this::lambda$openLegitModuleSettings$18);
        clickGuiModulesSidecarPanel.setFavoriteVisible(false);
        clickGuiModulesSidecarPanel.setToggleVisible(true);
        clickGuiModulesSidecarPanel.setToggleAction(null);
        clickGuiModulesSidecarPanel.setToggleLabelSupplier(() -> ClickGuiModulesPage.lambda$openLegitModuleSettings$19(hudModule));
        this.mainFrame.showOverlay(ClickGuiOverlaySpec.builder().title(hudModule.getName()).placement(ClickGuiOverlayPlacement.DOCKED_SHIFT).sidecar(clickGuiModulesSidecarPanel).initializeSidecar(ClickGuiModulesPage::lambda$openLegitModuleSettings$20).initializeContent(arg_0 -> this.lambda$openLegitModuleSettings$21(hudModule, arg_0)).backdropEnabled(false).build());
    }

    public ClickGuiModulesPage(ClickGuiMainFrame clickGuiMainFrame, double d, double d2, double d3) {
        super(d, d2, d3, 0.0, "Modules");
        this.viewMode = ClickGuiModuleViewMode.MODULE_CATEGORY;
        this.mainFrame = clickGuiMainFrame;
        this.macrosController = new ClickGuiMacrosController(this);
        this.pageResetAction = this::lambda$new$0;
        this.mainFrame.addContentOverlayCallback(this.pageResetAction);
        this.renderNavigation();
        this.renderCurrentView();
    }

    private void populateSettings(Mod mod, PanelComponent panelComponent) {
        panelComponent.removeMarkedChildren();
        boolean bl = false;
        for (Value<?, ?> value : mod.getValues()) {
            GuiComponent guiComponent;
            if (value == null || (guiComponent = ValueComponentFactory.createValueComponent(value, false, ValueComponentMode.STANDALONE)) == null) continue;
            if (value.getParent() != null) {
                BooleanValue booleanValue = value.getParent() instanceof BooleanValue ? (BooleanValue)value.getParent() : null;
                boolean bl2 = value instanceof ListValue && booleanValue != null && booleanValue.getDependentValues().size() == 1;
                guiComponent.setShowDisabledOverlay(!bl2);
                if (!bl2) {
                    guiComponent.setDisabledOverlayColor(ClickGuiModulesPage.J.r);
                }
            } else {
                guiComponent.setShowDisabledOverlay(false);
            }
            guiComponent.setExplicitWidth(panelComponent.A() - 1.0);
            panelComponent.h(guiComponent, "wrap");
            bl = true;
        }
        if (mod instanceof Search) {
            SearchBlockListComponent searchBlockList = new SearchBlockListComponent(ValueComponentMode.STANDALONE);
            searchBlockList.setShowDisabledOverlay(false);
            searchBlockList.setExplicitWidth(panelComponent.A() - 1.0);
            panelComponent.h(searchBlockList, "wrap");
            bl = true;
        }
        if (mod instanceof HudModule && ((HudModule)mod).shouldShowKeybindSetting()) {
            BindValueRowComponent bindValueRow = new BindValueRowComponent("Keybind", mod.getBind());
            bindValueRow.setShowDisabledOverlay(false);
            bindValueRow.setHorizontalInset(0.0);
            bindValueRow.setExplicitWidth(panelComponent.A() - 1.0);
            panelComponent.h(bindValueRow, "wrap");
            bl = true;
        }
        if (!bl) {
            double d;
            SimpleTextLabelComponent noSettingsLabel = new SimpleTextLabelComponent("No settings");
            noSettingsLabel.setTextColor(ClickGuiModulesPage.J.h);
            noSettingsLabel.setOffsetX(0.0f);
            double d2 = panelComponent.A();
            if (d2 <= 0.0 && (d = panelComponent.getExplicitWidth()) > 0.0) {
                d2 = d;
            }
            if (d2 > 0.0) {
                noSettingsLabel.o(d2);
            }
            panelComponent.h(new PaddedComponent(0.0, 4.0, 0.0, 0.0, noSettingsLabel), new Object[0]);
        }
        panelComponent.H(true);
    }

    public static void rebuildModuleCards(ClickGuiModulesPage page, ClickGuiContentPanel content, boolean legitView) {
        page.rebuildModuleCards(content, legitView);
    }

    private void lambda$openLegitModuleSettings$18() {
        this.mainFrame.closeActiveOverlay();
        this.selectLegitModuleCard((ClickGuiLegitModuleCardComponent)null);
    }

    private void selectLegitModuleCard(@Nullable ClickGuiLegitModuleCardComponent clickGuiLegitModuleCardComponent) {
        if (this.legitContent == null) {
            return;
        }
        if (clickGuiLegitModuleCardComponent != null) {
            if (clickGuiLegitModuleCardComponent.isSelected()) {
                this.mainFrame.closeActiveOverlay();
                clickGuiLegitModuleCardComponent.setSelected(false);
                clickGuiLegitModuleCardComponent = null;
            } else {
                this.openLegitModuleSettings(clickGuiLegitModuleCardComponent.getModule(), clickGuiLegitModuleCardComponent);
            }
        }
        for (GuiComponent guiComponent : this.legitContent.f()) {
            if (!(guiComponent instanceof ClickGuiLegitModuleCardComponent)) continue;
            ClickGuiLegitModuleCardComponent clickGuiLegitModuleCardComponent2 = (ClickGuiLegitModuleCardComponent)guiComponent;
            boolean bl = clickGuiLegitModuleCardComponent2 == clickGuiLegitModuleCardComponent;
            clickGuiLegitModuleCardComponent2.setSelected(bl);
            clickGuiLegitModuleCardComponent2.setDimmed(!bl && clickGuiLegitModuleCardComponent != null);
        }
    }

    private Boolean lambda$renderCategoryButtons$7() {
        return this.viewMode == ClickGuiModuleViewMode.LEGIT;
    }

    private void lambda$renderCategoryButtons$8() {
        if (this.viewMode != ClickGuiModuleViewMode.LEGIT) {
            this.viewMode = ClickGuiModuleViewMode.LEGIT;
            this.renderCurrentView();
        }
    }

    private void lambda$renderCategoryButtons$6() {
        if (this.viewMode != ClickGuiModuleViewMode.MACROS) {
            this.viewMode = ClickGuiModuleViewMode.MACROS;
            this.renderCurrentView();
        }
    }
}
