package gg.vape.friend.ui;

import com.google.gson.JsonObject;
import gg.vape.friend.ui.OnlinePlayerPreviewComponent;
import gg.vape.friend.ui.OnlinePlayerPreviewDividerComponent;
import gg.vape.friend.ui.OnlinePlayerPreviewSettings;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.value.BooleanToggleComponent;
import gg.vape.ui.click.component.value.NumberSliderComponent;
import gg.vape.ui.click.frame.impl.hud.HudSettingsFrameBase;
import gg.vape.ui.click.frame.impl.quickactions.QuickActionsFrame;

public class OnlinePlayerPreviewSettingsFrame
extends HudSettingsFrameBase {
    private boolean scaleDragging;
    private static GuiComponent[] obfuscationComponents;
    private final NumberSliderComponent scaleSlider;
    private final OnlinePlayerPreviewSettings settings = new OnlinePlayerPreviewSettings();
    private final NumberSliderComponent framesPerSecondSlider;
    private final NumberSliderComponent fieldOfViewSlider;
    private final BooleanToggleComponent levelViewToggle;

    @Override
    public void t(JsonObject jsonObject) {
        super.t(jsonObject);
        ClientSettings.getFrame(QuickActionsFrame.class).N$src$Lgg_vape_ui_click_frame_impl_quickactions_QuickA$1smecqc().setValue(this.V$src$Z$1xhop3l());
    }

    @Override
    public void Y() {
        if (this.scaleSlider.isDragging()) {
            if (!this.scaleDragging) {
                this.scaleDragging = true;
            }
        } else if (this.scaleDragging) {
            this.scaleDragging = false;
            this.H(true);
        }
    }

    public OnlinePlayerPreviewSettings getSettings() {
        return this.settings;
    }

    @Override
    public String getName() {
        return "Rearview";
    }

    static {
        OnlinePlayerPreviewSettingsFrame.setObfuscationComponents(null);
    }

    public OnlinePlayerPreviewSettingsFrame() {
        super("newrearview", "Rearview");
        this.scaleSlider = new NumberSliderComponent(this.settings.scale);
        this.framesPerSecondSlider = new NumberSliderComponent(this.settings.framesPerSecond);
        this.fieldOfViewSlider = new NumberSliderComponent(this.settings.fieldOfView);
        this.levelViewToggle = new BooleanToggleComponent(this.settings.levelView);
        this.scaleSlider.setDisabledOverlayColor(OnlinePlayerPreviewSettingsFrame.J.r);
        this.framesPerSecondSlider.setDisabledOverlayColor(OnlinePlayerPreviewSettingsFrame.J.r);
        this.fieldOfViewSlider.setDisabledOverlayColor(OnlinePlayerPreviewSettingsFrame.J.r);
        this.levelViewToggle.setDisabledOverlayColor(OnlinePlayerPreviewSettingsFrame.J.r);
        this.addSettings(this.scaleSlider, this.framesPerSecondSlider, this.fieldOfViewSlider, this.levelViewToggle,
                new OnlinePlayerPreviewDividerComponent(this, OnlinePlayerPreviewSettingsFrame.J.l));
        this.h(new OnlinePlayerPreviewComponent(this), new Object[0]);
    }


    public static GuiComponent[] getObfuscationComponents() {
        return obfuscationComponents;
    }

    public static void setObfuscationComponents(GuiComponent[] components) {
        obfuscationComponents = components;
    }
}

