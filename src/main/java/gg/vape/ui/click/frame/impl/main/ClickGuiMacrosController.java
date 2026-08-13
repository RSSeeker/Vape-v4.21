package gg.vape.ui.click.frame.impl.main;

import gg.vape.Vape;
import gg.vape.module.Macro;
import gg.vape.ui.click.animation.ColorAnimation;
import gg.vape.ui.click.component.GlyphIconComponent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.LabeledTextInputComponent;
import gg.vape.ui.click.component.MacroCardComponent;
import gg.vape.ui.click.component.MultilineTextBlockComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.component.gui.TextButton;
import gg.vape.ui.click.component.layout.PaddedComponent;
import gg.vape.ui.click.frame.FrameScrollbarPlacement;
import gg.vape.ui.click.frame.impl.main.ClickGuiContentPanel;
import gg.vape.ui.click.frame.impl.main.ClickGuiMacrosLabelInput;
import gg.vape.ui.click.frame.impl.main.ClickGuiMacrosSettingsPanel;
import gg.vape.ui.click.frame.impl.main.ClickGuiModulesPage;
import gg.vape.ui.click.frame.impl.main.ClickGuiOverlayPlacement;
import gg.vape.ui.click.frame.impl.main.ClickGuiOverlaySpec;
import gg.vape.ui.click.frame.impl.main.ClickGuiSidecarPanelBase;
import gg.vape.ui.theme.ThemeColors;
import java.awt.Color;
import org.jetbrains.annotations.Nullable;

public class ClickGuiMacrosController {
    private String searchQuery = "";
    private LabeledTextInputComponent searchInput;
    private final ClickGuiModulesPage modulesPage;
    private ClickGuiContentPanel macroContent;

    private void closeOverlayAndRefresh() {
        this.modulesPage.mainFrame.closeActiveOverlay();
        this.rebuildMacroCards();
    }

    //safe exec
    private static void openMacroDocumentation() {
        try {
            Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", "https://docs.vape.gg/features/misc/Macros"});
        }
        catch (Exception exception) {
            Vape.logThrowable(exception);
        }
    }

    public static String setSearchQuery(ClickGuiMacrosController controller, String query) {
        controller.searchQuery = query;
        return controller.searchQuery;
    }

    private void handleMacroCard(MacroCardComponent macroCardComponent) {
        this.updateSelectedCard(macroCardComponent);
    }

    private void configureCreateMacroSidecar(ClickGuiSidecarPanelBase clickGuiSidecarPanelBase) {
        clickGuiSidecarPanelBase.setDividerVisible(false);
        clickGuiSidecarPanelBase.getCloseButton().setVisible(false);
        clickGuiSidecarPanelBase.setBackAction(this::closeOverlayAndRefresh);
    }

    void openMacroSettings(Macro macro) {
        this.modulesPage.mainFrame.showOverlay(ClickGuiOverlaySpec.builder().title(macro.getName()).placement(ClickGuiOverlayPlacement.DOCKED_SHIFT).backdropEnabled(false).initializeContent(panel -> this.populateMacroSettings(macro, panel)).initializeSidecar(sidecar -> this.configureMacroSidecar(macro, sidecar)).build());
    }

    private void deleteMacro(Macro macro) {
        Vape.INSTANCE.getMacrosManager().removeMacro(macro);
        this.modulesPage.mainFrame.closeActiveOverlay();
        this.rebuildMacroCards();
    }

    public ClickGuiMacrosController(ClickGuiModulesPage clickGuiModulesPage) {
        this.modulesPage = clickGuiModulesPage;
    }

    private void populateMacroSettings(Macro macro, PanelComponent panelComponent) {
        ClickGuiMacrosSettingsPanel clickGuiMacrosSettingsPanel = new ClickGuiMacrosSettingsPanel(panelComponent.A(), panelComponent.L(), macro, false, this::closeOverlayAndRefresh, this::closeOverlayAndRefresh);
        panelComponent.h(clickGuiMacrosSettingsPanel, new Object[0]);
    }

    private void configureMacroSidecar(Macro macro, ClickGuiSidecarPanelBase clickGuiSidecarPanelBase) {
        clickGuiSidecarPanelBase.setDividerVisible(false);
        clickGuiSidecarPanelBase.getCloseButton().setVisible(false);
        clickGuiSidecarPanelBase.setBackAction(this::closeOverlayAndRefresh);
        GlyphIconComponent glyphIconComponent = new GlyphIconComponent("newtrash", 5.0, 5.0, 8.0, 8.0, ClickGuiModulesPage.J.h, ClickGuiModulesPage.J.d, null);
        glyphIconComponent.addClickListener(() -> this.deleteMacro(macro));
        clickGuiSidecarPanelBase.addTrailingComponent(glyphIconComponent);
    }

    private void populateCreateMacroSettings(Macro macro, PanelComponent panelComponent) {
        ClickGuiMacrosSettingsPanel clickGuiMacrosSettingsPanel = new ClickGuiMacrosSettingsPanel(panelComponent.A(), panelComponent.L(), macro, true, this::closeOverlayAndRefresh, this::closeOverlayAndRefresh);
        panelComponent.h(clickGuiMacrosSettingsPanel, new Object[0]);
    }

    void openCreateMacro() {
        Macro macro = Macro.create("New Macro");
        this.modulesPage.mainFrame.showOverlay(ClickGuiOverlaySpec.builder().title("Add new Macro").placement(ClickGuiOverlayPlacement.DOCKED_SHIFT).initializeContent(panel -> this.populateCreateMacroSettings(macro, panel)).initializeSidecar(this::configureCreateMacroSidecar).build());
    }

    public void selectMacroCard(MacroCardComponent macroCardComponent) {
        this.updateSelectedCard(macroCardComponent);
    }


    private void updateSelectedCard(@Nullable MacroCardComponent macroCardComponent) {
        if (this.macroContent == null) {
            return;
        }
        if (macroCardComponent != null) {
            if (macroCardComponent.isSelected()) {
                this.modulesPage.mainFrame.closeActiveOverlay();
                macroCardComponent.setSelected(false);
                macroCardComponent = null;
            } else {
                this.openMacroSettings(macroCardComponent.getMacro());
            }
        }
        for (GuiComponent guiComponent : this.macroContent.f()) {
            MacroCardComponent macroCardComponent2;
            if (!(guiComponent instanceof PaddedComponent) || (macroCardComponent2 = ((PaddedComponent)guiComponent).t(MacroCardComponent.class)) == null) continue;
            boolean selected = macroCardComponent2 == macroCardComponent;
            macroCardComponent2.setSelected(selected);
            macroCardComponent2.setDimmed(!selected && macroCardComponent != null);
        }
    }

    public void renderMacroView() {
        double availableWidth = this.modulesPage.getAvailableContentWidth();
        this.searchInput = new ClickGuiMacrosLabelInput(this, "Search macros...");
        this.searchInput.setSearchIconTrailing(false);
        TextButton textButton = new TextButton("NEW MACRO", 0.625, ClickGuiModulesPage.J.z(), ClickGuiModulesPage.J.z().brighter(), null, 2.0f, 1.0f, 51.0, 16.0);
        textButton.setIconResource("newadd");
        textButton.setIconSize(6.0f);
        textButton.setUseAlternateFont(true);
        textButton.setUppercase(true);
        textButton.setDeriveTextColorFromBackground(false);
        textButton.setNormalTextColor(Color.WHITE);
        textButton.setUseThemeBackground(true);
        textButton.addClickListener(this::openCreateMacro);
        this.searchInput.o(Math.max(0.0, availableWidth - textButton.A() - 4.0));
        this.searchInput.setLeftInset(0.0f);
        this.searchInput.setHorizontalInset(0.0);
        this.searchInput.setRightInset(0.0f);
        this.searchInput.setVerticalInset(0.0f);
        this.searchInput.setUseExplicitHeight(true);
        this.searchInput.Y(16.0);
        this.searchInput.setBorderThickness(0.75f);
        this.searchInput.setCornerRadius(4.0f);
        this.searchInput.setBorderAnimation(ColorAnimation.Y(ThemeColors.J.s));
        this.searchInput.setBackgroundColorOrNull(null);
        this.modulesPage.getMainContainer().setResponsiveWidthEnabled(true);
        this.modulesPage.getMainContent().h(this.searchInput, "widthwrap");
        this.modulesPage.getMainContent().h(new PaddedComponent(0.0, 0.0, 4.0, 0.0, textButton), new Object[0]);
        this.modulesPage.getMainContent().h(new SpacerComponent(0.0, 5.0), new Object[0]);
        double contentHeight = this.modulesPage.getMainContent().L() - this.modulesPage.getMainContent().l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().y() - 1.0;
        if (contentHeight < 0.0) {
            contentHeight = 0.0;
        }
        this.macroContent = new ClickGuiContentPanel(availableWidth, contentHeight);
        this.macroContent.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        this.macroContent.setShowDisabledOverlay(false);
        this.macroContent.t(this.macroContent.L());
        this.macroContent.F(FrameScrollbarPlacement.OUTSIDE);
        this.macroContent.E(true);
        this.modulesPage.getMainContent().h(this.macroContent, new Object[0]);
        if (this.searchQuery != null && !this.searchQuery.isEmpty()) {
            this.searchInput.setText(this.searchQuery);
        } else {
            this.rebuildMacroCards();
        }
    }

    public void rebuildMacroCards() {
        if (this.macroContent == null) {
            return;
        }
        this.macroContent.removeMarkedChildren();
        String searchText = this.searchInput != null ? this.searchInput.getText() : "";
        String normalizedQuery = searchText == null ? "" : searchText.trim().toLowerCase();
        boolean hasSearchQuery = !normalizedQuery.isEmpty();
        if (hasSearchQuery) {
            for (Macro macro : Vape.INSTANCE.getMacrosManager().getMacros()) {
                if (!macro.getName().toLowerCase().contains(normalizedQuery)) continue;
                MacroCardComponent macroCardComponent = new MacroCardComponent(macro);
                macroCardComponent.o(this.macroContent.A());
                macroCardComponent.setSettingsAction(() -> this.handleMacroCard(macroCardComponent));
                macroCardComponent.setCardAction(() -> this.handleMacroCard(macroCardComponent));
                this.macroContent.h(new PaddedComponent(0.0, 3.0, 0.0, 0.0, macroCardComponent), new Object[0]);
            }
            if (this.macroContent.f().isEmpty()) {
                // empty if block
            }
            this.macroContent.H(true);
            return;
        }
        for (Macro macro : Vape.INSTANCE.getMacrosManager().getMacros()) {
            MacroCardComponent macroCardComponent = new MacroCardComponent(macro);
            macroCardComponent.o(this.macroContent.A());
            macroCardComponent.setSettingsAction(() -> this.handleMacroCard(macroCardComponent));
            macroCardComponent.setCardAction(() -> this.handleMacroCard(macroCardComponent));
            this.macroContent.h(new PaddedComponent(0.0, 3.0, 0.0, 0.0, macroCardComponent), new Object[0]);
        }
        if (this.macroContent.f().isEmpty()) {
            MultilineTextBlockComponent multilineTextBlockComponent = new MultilineTextBlockComponent("INFO", "Click NEW MACRO to add a macro.\n\nFor more info on macros, read the docs");
            multilineTextBlockComponent.setWidth(this.macroContent.A());
            multilineTextBlockComponent.setLink("read the docs", ClickGuiMacrosController::openMacroDocumentation);
            this.macroContent.h(new PaddedComponent(3.0, 3.0, 0.0, 0.0, multilineTextBlockComponent), new Object[0]);
        }
        this.macroContent.H(true);
    }

}
