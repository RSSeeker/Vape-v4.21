package gg.vape.friend.ui;

import com.google.gson.JsonObject;
import gg.vape.friend.ui.OnlineRadarPreviewComponent;
import gg.vape.friend.ui.OnlineRadarSettings;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.DropdownSelectComponent;
import gg.vape.ui.click.component.value.BooleanToggleComponent;
import gg.vape.ui.click.component.value.ColorValueEditorComponent;
import gg.vape.ui.click.component.value.NumberSliderComponent;
import gg.vape.ui.click.frame.impl.hud.HudModuleConfigFrameBase;
import gg.vape.ui.click.frame.impl.hud.HudSettingsFrameBase;
import gg.vape.ui.click.frame.impl.quickactions.QuickActionsFrame;
import gg.vape.unmap.ModeOption;

public class OnlineRadarSettingsFrame
extends HudSettingsFrameBase {
    private final ColorValueEditorComponent customColorEditor;
    private final ColorValueEditorComponent friendlyColorEditor;
    private final DropdownSelectComponent<ModeOption> dotStyleDropdown;
    private final DropdownSelectComponent<ModeOption> colorModeDropdown;
    private final NumberSliderComponent maxShownSlider;
    private final ColorValueEditorComponent enemyColorEditor;
    private final DropdownSelectComponent<ModeOption> radarStyleDropdown;
    private final NumberSliderComponent dotSizeSlider;
    private final DropdownSelectComponent<ModeOption> radarModeDropdown;
    private final OnlineRadarSettings settings = new OnlineRadarSettings();
    private final BooleanToggleComponent clampRadarToggle;
    private boolean radarSizeDragging;
    private final NumberSliderComponent radarScaleSlider;
    private final NumberSliderComponent radarSizeSlider;
    private final NumberSliderComponent maxDistanceSlider;
    private double previewRadarSize;
    private final BooleanToggleComponent showBackgroundToggle;
    private final BooleanToggleComponent showCrossToggle;

    public OnlineRadarSettings getSettings() {
        return this.settings;
    }

    @Override
    public void v() {
    }

    public OnlineRadarSettingsFrame() {
        super("newradar", "Radar");
        this.radarModeDropdown = new DropdownSelectComponent(this.settings.radarMode);
        this.colorModeDropdown = new DropdownSelectComponent(this.settings.colorMode);
        this.customColorEditor = new ColorValueEditorComponent(this.settings.customColor);
        this.friendlyColorEditor = new ColorValueEditorComponent(this.settings.friendlyColor);
        this.enemyColorEditor = new ColorValueEditorComponent(this.settings.enemyColor);
        this.dotStyleDropdown = new DropdownSelectComponent(this.settings.dotStyle);
        this.radarStyleDropdown = new DropdownSelectComponent(this.settings.radarStyle);
        this.dotSizeSlider = new NumberSliderComponent(this.settings.dotSize);
        this.radarSizeSlider = new NumberSliderComponent(this.settings.radarSize);
        this.radarScaleSlider = new NumberSliderComponent(this.settings.radarScale);
        this.showBackgroundToggle = new BooleanToggleComponent(this.settings.showBackground);
        this.maxDistanceSlider = new NumberSliderComponent(this.settings.maxDistance);
        this.maxShownSlider = new NumberSliderComponent(this.settings.maxShown);
        this.showCrossToggle = new BooleanToggleComponent(this.settings.showCross);
        this.clampRadarToggle = new BooleanToggleComponent(this.settings.clampRadar);
        this.previewRadarSize = (Double)this.settings.radarSize.getValue();
        this.addSettings(this.radarModeDropdown, this.colorModeDropdown, this.customColorEditor, this.friendlyColorEditor, this.enemyColorEditor, this.dotStyleDropdown,
                this.radarStyleDropdown, this.dotSizeSlider, this.radarSizeSlider, this.radarScaleSlider, this.showBackgroundToggle, this.maxDistanceSlider, this.maxShownSlider,
                this.showCrossToggle, this.clampRadarToggle);
        this.h(new OnlineRadarPreviewComponent(this), new Object[0]);
    }

    @Override
    public double A() {
        if (this.isPublicProfilePreview() && this.isTwoDimensionalMode()) {
            return this.radarSizeSlider.isDragging() ? this.previewRadarSize : (Double)this.settings.radarSize.getValue();
        }
        return super.A();
    }

    @Override
    public String getName() {
        return "Radar";
    }

    private boolean isTwoDimensionalMode() {
        return this.settings.radarMode.getValue() == this.settings.twoDimensionalRadarMode;
    }

    @Override
    public double L() {
        if (this.isPublicProfilePreview() && this.isTwoDimensionalMode()) {
            double radarSize = this.radarSizeSlider.isDragging() ? this.previewRadarSize : (Double)this.settings.radarSize.getValue();
            return radarSize + 2.0;
        }
        if (this.isPublicProfilePreview() && !this.isTwoDimensionalMode()) {
            boolean showingEditorPlaceholder = !ClientSettings.INSTANCE.inputEnabled && HudModuleConfigFrameBase.isHudEditorContext();
            if (showingEditorPlaceholder) {
                return Math.max(26, 32);
            }
        }
        return super.L();
    }


    @Override
    public void t(JsonObject jsonObject) {
        super.t(jsonObject);
        ClientSettings.getFrame(QuickActionsFrame.class).E$src$Lgg_vape_ui_click_frame_impl_quickactions_QuickA$1snij4t().setValue(this.V$src$Z$1xhop3l());
    }

    @Override
    public void Y() {
        if (this.isPublicProfilePreview() && this.isTwoDimensionalMode()) {
            if (this.radarSizeSlider.isDragging()) {
                if (!this.radarSizeDragging) {
                    this.radarSizeDragging = true;
                }
            } else {
                if (this.radarSizeDragging) {
                    this.radarSizeDragging = false;
                    this.H(true);
                }
                this.previewRadarSize = (Double)this.settings.radarSize.getValue();
            }
            return;
        }
        this.radarSizeDragging = false;
        this.previewRadarSize = (Double)this.settings.radarSize.getValue();
    }

    @Override
    protected void renderHudModeBorder() {
    }
}

