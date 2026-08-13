package gg.vape.ui.click.frame.impl.main;

import com.google.gson.JsonObject;
import gg.vape.Vape;
import gg.vape.input.BindCaptureTask;
import gg.vape.module.Macro;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.component.gui.TextButton;
import gg.vape.ui.click.component.input.SmallTextInputComponent;
import gg.vape.ui.click.component.layout.PaddedComponent;
import gg.vape.ui.click.component.value.BooleanToggleComponent;
import gg.vape.ui.click.component.value.RandomRangeSliderComponent;
import gg.vape.ui.click.frame.impl.main.ClickGuiMacrosNameInput;
import gg.vape.ui.click.frame.impl.main.ClickGuiMacrosPreviewComponent;
import gg.vape.ui.click.frame.impl.main.ClickGuiMacrosPrimaryBindCaptureTask;
import gg.vape.ui.click.frame.impl.main.ClickGuiMacrosSecondaryBindCaptureTask;
import gg.vape.ui.click.frame.impl.main.ClickGuiMacrosSettingsControlPanel;
import gg.vape.ui.click.frame.impl.main.ClickGuiMacrosSettingsViewMode;
import gg.vape.ui.theme.ThemeColors;
import java.awt.Color;

public class ClickGuiMacrosSettingsPanel
extends PanelComponent {
    private static final Color BORDER_COLOR;
    private static final Color ENABLED_INPUT_COLOR;
    private static final Color DISABLED_INPUT_COLOR;
    private PaddedComponent actionButtonsWrapper;
    private String pendingName = "";
    private final Runnable cancelAction;
    private PaddedComponent controlPanelWrapper;
    private final Runnable saveAction;
    private final Macro macro;
    private final Macro originalMacro;
    private ClickGuiMacrosSettingsViewMode viewMode;
    private PanelComponent actionButtonsPanel;
    private final boolean creatingNew;
    private RandomRangeSliderComponent doubleClickDelaySlider;
    private SpacerComponent previewSpacer;
    private RandomRangeSliderComponent delaySlider;
    private final ThemeColors theme = ThemeColors.J;
    private SmallTextInputComponent nameInput;
    private PanelComponent settingsControlPanel;
    private PaddedComponent previewWrapper;
    private BooleanToggleComponent doubleClickToggle;
    private SpacerComponent actionButtonsSpacer;
    private BindCaptureTask bindCaptureTask;

    void updateViewVisibility() {
        boolean bl;
        boolean bl2;
        boolean bl3 = this.viewMode == ClickGuiMacrosSettingsViewMode.NAME_INPUT;
        boolean bl4 = bl2 = this.viewMode == ClickGuiMacrosSettingsViewMode.KEYBIND_INPUT;
        if (bl2) {
            boolean bl5;
            boolean bl6 = bl5 = this.viewMode == ClickGuiMacrosSettingsViewMode.FULL_SETTINGS;
            if (bl5) {
                if (this.creatingNew && !this.pendingName.isEmpty()) {
                    this.nameInput.setText(this.pendingName);
                }
                this.updateControlPanelHeight();
                this.nameInput.getActionButton().setVisible(bl3);
                this.previewSpacer.setVisible(bl5);
                this.previewWrapper.setVisible(bl5);
                this.delaySlider.setVisible(bl5);
                this.doubleClickToggle.setVisible(bl5);
                this.doubleClickDelaySlider.setVisible(this.macro.getDoubleClick().getEffectiveValue() != false);
                this.actionButtonsSpacer.setVisible(bl5);
                this.actionButtonsWrapper.setVisible(bl5);
                this.startSecondaryBindCapture();
                this.H(true);
                return;
            }
            if (this.creatingNew) {
                // empty if block
            }
            this.updateControlPanelHeight();
            this.nameInput.getActionButton().setVisible(bl3);
            this.previewSpacer.setVisible(bl5);
            this.previewWrapper.setVisible(bl5);
            this.delaySlider.setVisible(bl5);
            this.doubleClickToggle.setVisible(bl5);
            this.doubleClickDelaySlider.setVisible(false);
            this.actionButtonsSpacer.setVisible(bl5);
            this.actionButtonsWrapper.setVisible(bl5);
            this.startSecondaryBindCapture();
            this.H(true);
            return;
        }
        boolean bl7 = bl = this.viewMode == ClickGuiMacrosSettingsViewMode.FULL_SETTINGS;
        if (bl) {
            if (this.creatingNew && !this.pendingName.isEmpty()) {
                this.nameInput.setText(this.pendingName);
            }
            this.updateControlPanelHeight();
            this.nameInput.getActionButton().setVisible(bl3);
            this.previewSpacer.setVisible(bl);
            this.previewWrapper.setVisible(bl);
            this.delaySlider.setVisible(bl);
            this.doubleClickToggle.setVisible(bl);
            this.doubleClickDelaySlider.setVisible(this.macro.getDoubleClick().getEffectiveValue() != false);
            this.actionButtonsSpacer.setVisible(bl);
            this.actionButtonsWrapper.setVisible(bl);
            this.H(true);
            return;
        }
        if (this.creatingNew) {
            // empty if block
        }
        this.updateControlPanelHeight();
        this.nameInput.getActionButton().setVisible(bl3);
        this.previewSpacer.setVisible(bl);
        this.previewWrapper.setVisible(bl);
        this.delaySlider.setVisible(bl);
        this.doubleClickToggle.setVisible(bl);
        this.doubleClickDelaySlider.setVisible(false);
        this.actionButtonsSpacer.setVisible(bl);
        this.actionButtonsWrapper.setVisible(bl);
        this.H(true);
    }

    void submitName(String string) {
        this.pendingName = string;
        this.nameInput.clearFocus();
        this.viewMode = ClickGuiMacrosSettingsViewMode.KEYBIND_INPUT;
        this.updateViewVisibility();
    }

    static Color getBorderColor() {
        return BORDER_COLOR;
    }

    static {
        long[] lArray = new long[]{2959981560782464037L, 2380650494719630879L};
        ENABLED_INPUT_COLOR = new Color((int)lArray[0]);
        DISABLED_INPUT_COLOR = new Color((int)lArray[1]);
        BORDER_COLOR = new Color(255, 255, 255, 20);
    }

    public boolean isCreatingNew() {
        return this.creatingNew;
    }

    private void buildContent() {
        double d = this.A() - 12.0;
        this.buildControlPanel(d);
        this.previewSpacer = new SpacerComponent(0.0, 8.0);
        this.h(this.previewSpacer, new Object[0]);
        ClickGuiMacrosPreviewComponent clickGuiMacrosPreviewComponent = new ClickGuiMacrosPreviewComponent(this);
        clickGuiMacrosPreviewComponent.o(d);
        clickGuiMacrosPreviewComponent.Y(1.0);
        this.previewWrapper = new PaddedComponent(0.0, 8.0, 6.0, 0.0, clickGuiMacrosPreviewComponent);
        this.h(this.previewWrapper, new Object[0]);
        this.delaySlider = new RandomRangeSliderComponent(this.macro.getDelay());
        this.delaySlider.setShowDisabledOverlay(false);
        this.delaySlider.setExplicitWidth(this.A() - 1.0);
        this.delaySlider.setUseExplicitWidth(true);
        this.delaySlider.setDisabledOverlayColor(this.theme.m);
        this.h(this.delaySlider, "wrap");
        this.doubleClickToggle = new BooleanToggleComponent(this.macro.getDoubleClick());
        this.doubleClickToggle.setShowDisabledOverlay(false);
        this.doubleClickToggle.setExplicitWidth(this.A() - 1.0);
        this.doubleClickToggle.setUseExplicitWidth(true);
        this.doubleClickToggle.setDisabledOverlayColor(this.theme.m);
        this.h(this.doubleClickToggle, "wrap");
        this.doubleClickDelaySlider = new RandomRangeSliderComponent(this.macro.getDoubleClickDelay());
        this.doubleClickDelaySlider.setShowDisabledOverlay(false);
        this.doubleClickDelaySlider.setExplicitWidth(this.A() - 1.0);
        this.doubleClickDelaySlider.setUseExplicitWidth(true);
        this.doubleClickDelaySlider.setDisabledOverlayColor(this.theme.m);
        this.h(this.doubleClickDelaySlider, "wrap");
        this.actionButtonsSpacer = new SpacerComponent(0.0, 0.0);
        this.h(this.actionButtonsSpacer, new Object[0]);
        this.buildActionButtons();
    }


    private void saveChanges() {
        String string = this.nameInput.getText().trim();
        if (string.isEmpty()) {
            return;
        }
        JsonObject jsonObject = this.macro.toJson();
        jsonObject.remove("name");
        if (this.creatingNew) {
            Macro macro = Macro.create(string).loadJson(jsonObject);
            Vape.INSTANCE.getMacrosManager().addMacro(macro);
        } else {
            Macro macro = Macro.create(string).loadJson(jsonObject);
            Vape.INSTANCE.getMacrosManager().removeMacro(this.macro);
            Vape.INSTANCE.getMacrosManager().addMacro(macro);
        }
        if (this.saveAction != null) {
            this.saveAction.run();
        }
    }

    private void updateActionButtonSpacer() {
        if (this.actionButtonsSpacer == null || this.actionButtonsPanel == null) {
            return;
        }
        this.actionButtonsSpacer.Y(0.0);
        this.H(true);
        double d = this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().y();
        double d2 = this.L();
        double d3 = d2 - d;
        if (d3 < 0.0) {
            d3 = 0.0;
        }
        this.actionButtonsSpacer.Y(d3);
        this.H(true);
    }

    private void cancelChanges() {
        if (!this.creatingNew) {
            this.macro.loadJson(this.originalMacro.toJson());
        }
        if (this.cancelAction != null) {
            this.cancelAction.run();
        }
    }

    @Override
    public void u() {
        super.u();
        this.updateDoubleClickDelayVisibility();
        if (this.viewMode == ClickGuiMacrosSettingsViewMode.FULL_SETTINGS) {
            this.updateActionButtonSpacer();
        }
    }

    static Color getEnabledInputColor() {
        return ENABLED_INPUT_COLOR;
    }

    static Color getDisabledInputColor() {
        return DISABLED_INPUT_COLOR;
    }

    public Macro getMacro() {
        return this.macro;
    }

    void startPrimaryBindCapture() {
        if (this.bindCaptureTask != null && this.bindCaptureTask.isCapturing()) {
            return;
        }
        this.bindCaptureTask = new ClickGuiMacrosPrimaryBindCaptureTask(this, this.macro);
        this.bindCaptureTask.run();
    }

    public ClickGuiMacrosSettingsPanel(double d, double d2, Macro macro, boolean bl, Runnable runnable, Runnable runnable2) {
        super(d, d2);
        this.macro = macro;
        this.originalMacro = Macro.create(macro.getName()).loadJson(macro.toJson());
        this.creatingNew = bl;
        this.cancelAction = runnable;
        this.saveAction = runnable2;
        this.viewMode = bl ? ClickGuiMacrosSettingsViewMode.NAME_INPUT : ClickGuiMacrosSettingsViewMode.FULL_SETTINGS;
        this.setShowDisabledOverlay(false);
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        this.buildContent();
        this.updateViewVisibility();
    }

    public ClickGuiMacrosSettingsViewMode getViewMode() {
        return this.viewMode;
    }

    private void startSecondaryBindCapture() {
        if (this.bindCaptureTask != null && this.bindCaptureTask.isCapturing()) {
            return;
        }
        this.bindCaptureTask = new ClickGuiMacrosSecondaryBindCaptureTask(this, this.macro);
        this.bindCaptureTask.run();
    }

    private void updateDoubleClickDelayVisibility() {
        boolean bl;
        boolean bl2 = this.viewMode == ClickGuiMacrosSettingsViewMode.FULL_SETTINGS;
        boolean bl3 = bl = bl2 && this.macro.getDoubleClick().getEffectiveValue() != false;
        if (this.doubleClickDelaySlider.V$src$Z$1xhop3l() != bl) {
            this.doubleClickDelaySlider.setVisible(bl);
        }
    }

    public String getPendingName() {
        return this.pendingName;
    }

    void showFullSettings() {
        this.viewMode = ClickGuiMacrosSettingsViewMode.FULL_SETTINGS;
        this.updateViewVisibility();
    }

    private void updateControlPanelHeight() {
        float f = 30.0f;
        float f2 = 43.5f;
        boolean bl = this.viewMode == ClickGuiMacrosSettingsViewMode.KEYBIND_INPUT || this.viewMode == ClickGuiMacrosSettingsViewMode.FULL_SETTINGS;
        double d = bl ? 43.5 : 30.0;
        this.settingsControlPanel.Y(d);
    }

    void setBindCaptureTask(BindCaptureTask bindCaptureTask) {
        this.bindCaptureTask = bindCaptureTask;
    }

    private void buildActionButtons() {
        double d = this.A() - 12.0;
        this.actionButtonsPanel = new PanelComponent(d, 14.0);
        this.actionButtonsPanel.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("widthwrap");
        this.actionButtonsPanel.setShowDisabledOverlay(false);
        TextButton textButton = new TextButton("CANCEL", 0.625, this.theme.i, this.theme.i.brighter(), null, 2.0f, 1.0f, 35.5, 14.0);
        textButton.setUppercase(true);
        textButton.setUseAlternateFont(true);
        textButton.setDeriveTextColorFromBackground(false);
        textButton.setNormalTextColor(this.theme.A);
        textButton.addClickListener(this::cancelChanges);
        TextButton textButton2 = new TextButton(this.creatingNew ? "ADD" : "UPDATE", 0.625, this.theme.B, this.theme.B.brighter(), null, 2.0f, 1.0f, 27.5, 14.0);
        textButton2.setUppercase(true);
        textButton2.setDeriveTextColorFromBackground(false);
        textButton2.setNormalTextColor(this.theme.A);
        textButton2.addClickListener(this::saveChanges);
        double d2 = textButton.A() + 4.0 + textButton2.A();
        this.actionButtonsPanel.h(new SpacerComponent(this.actionButtonsPanel.A() - d2, 0.0), new Object[0]);
        this.actionButtonsPanel.h(new PaddedComponent(0.0, 0.0, 0.0, 4.0, textButton), new Object[0]);
        this.actionButtonsPanel.h(textButton2, new Object[0]);
        this.actionButtonsWrapper = new PaddedComponent(0.0, 6.0, 6.0, 0.0, this.actionButtonsPanel);
        this.h(this.actionButtonsWrapper, new Object[0]);
    }

    private void buildControlPanel(double d) {
        float f = 8.0f;
        float f2 = 4.0f;
        float f3 = 18.0f;
        float f4 = 30.0f;
        float f5 = 43.5f;
        float f6 = 0.5632f;
        float f7 = 8.0f;
        float f8 = 8.0f;
        float f9 = 3.0f;
        this.nameInput = new ClickGuiMacrosNameInput(this, "Type item name");
        this.nameInput.setBackgroundVisible(false);
        this.nameInput.setRightInset(6.0f);
        this.nameInput.setHorizontalInset(0.0);
        this.nameInput.setLeftInset(0.0f);
        this.nameInput.setUseExplicitHeight(true);
        this.nameInput.Y(18.0);
        this.nameInput.o(d);
        this.nameInput.setMaxLength(32);
        this.nameInput.setVerticalInset(0.0f);
        this.nameInput.getActionButton().setVisible(true);
        this.nameInput.getActionButton().setIconResource("newnext");
        if (!this.creatingNew) {
            this.nameInput.setText(this.macro.getName());
        }
        this.settingsControlPanel = new ClickGuiMacrosSettingsControlPanel(this, d, 43.5);
        this.settingsControlPanel.setShowDisabledOverlay(false);
        this.settingsControlPanel.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        this.settingsControlPanel.h(new SpacerComponent(d, 12.0), new Object[0]);
        this.settingsControlPanel.h(this.nameInput, new Object[0]);
        this.controlPanelWrapper = new PaddedComponent(6.0, 0.0, 6.0, 0.0, this.settingsControlPanel);
        this.h(this.controlPanelWrapper, new Object[0]);
    }

}
