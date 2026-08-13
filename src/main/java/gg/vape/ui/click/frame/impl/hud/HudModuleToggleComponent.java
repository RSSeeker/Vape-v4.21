package gg.vape.ui.click.frame.impl.hud;

import gg.vape.module.render.hud.HudModule;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.MouseButton;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.frame.Frame;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.ScaledResolution;

public class HudModuleToggleComponent
extends GuiComponent {
    private final float iconScale;
    private final String iconKey;
    private boolean hovered;
    private final HudModule module;
    private boolean enabled;
    private Frame configFrame;

    @Override
    public double x() {
        return 26.0;
    }

    @Override
    public void F() {
        this.hovered = true;
    }

    @Override
    public void u() {
        if (this.hovered && !this.w$src$Z$e457mb()) {
            this.hovered = false;
        }
        this.syncEnabledState();
    }

    @Override
    public void I() {
    }

    @Override
    public void H() {
        GuiRenderPrimitives.d(this.G$src$D$1b2f02a() + 5.0, this.n(), this.A() - 3.0, this.L() - 3.0, this.enabled ? J.z() : (this.hovered ? HudModuleToggleComponent.J.l : HudModuleToggleComponent.J.m));
        GuiRenderPrimitives.F(this.iconKey, this.G$src$D$1b2f02a() + this.A() / 2.0 + 3.0, this.n() + this.L() / 2.0 - 2.0, (double)(8.0f * this.iconScale), 8.0f * this.iconScale, this.enabled || this.hovered ? HudModuleToggleComponent.J.f : HudModuleToggleComponent.J.W);
    }

    @Override
    public double C() {
        return 26.0;
    }

    private void toggleEnabled() {
        this.enabled = !this.enabled;
        this.module.setEnabled(this.enabled);
        if (this.configFrame != null) {
            this.applyConfigFrameState();
        }
    }

    private void syncEnabledState() {
        if (this.module.isEnabled() != this.enabled) {
            this.toggleEnabled();
        }
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
        if (guiMouseEvent.getAction().equals((Object)MouseButton.LEFT_CLICK)) {
            this.toggleEnabled();
        }
    }


    public HudModuleToggleComponent(HudModule hudModule) {
        this(hudModule, 1.0f);
    }

    public HudModuleToggleComponent setConfigFrame(Frame frame) {
        this.configFrame = frame;
        return this;
    }

    public HudModuleToggleComponent(HudModule hudModule, float f) {
        this.module = hudModule;
        this.iconKey = hudModule.getKey();
        this.enabled = hudModule.isEnabled();
        this.iconScale = f;
    }

    public void applyConfigFrameState() {
        if (this.configFrame == null) {
            return;
        }
        this.configFrame.setVisible(this.enabled);
        this.configFrame.c(true);
        this.configFrame.U();
        ScaledResolution scaledResolution = Minecraft.G();
        if (this.configFrame.n() > (double)scaledResolution.getScaledHeight() || this.configFrame.n() < 0.0) {
            this.configFrame.S((double)(scaledResolution.getScaledHeight() / 2));
        }
        if (this.configFrame.G$src$D$1b2f02a() > (double)scaledResolution.getScaledWidth() || this.configFrame.G$src$D$1b2f02a() < 0.0) {
            this.configFrame.K(scaledResolution.getScaledWidth() / 2);
        }
        if (this.configFrame instanceof HudModuleConfigFrameBase) {
            HudModuleConfigFrameBase hudModuleConfigFrameBase = (HudModuleConfigFrameBase)this.configFrame;
            hudModuleConfigFrameBase.closeAllHudSettings();
            hudModuleConfigFrameBase.getAnchoredSettingsFrame().setVisible(false);
        }
    }
}

