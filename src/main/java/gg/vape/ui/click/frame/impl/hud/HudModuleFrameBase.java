package gg.vape.ui.click.frame.impl.hud;

import com.google.gson.JsonObject;
import func.skidline.RectData;
import gg.vape.Vape;
import gg.vape.config.ConfigJsonUtils;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.MouseButton;
import gg.vape.ui.click.component.AnimatedIconButtonComponent;
import gg.vape.ui.click.component.DropdownSelectComponent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.impl.hud.AnchoredHudModuleConfigFrame;
import gg.vape.ui.click.frame.impl.hud.HudModuleConfigFrameBase;
import gg.vape.ui.click.frame.impl.hud.HudModuleFrameBaseCloseClickHandler;
import gg.vape.ui.click.frame.impl.hud.HudModuleFrameCloseClickHandler;
import gg.vape.ui.click.frame.impl.main.ClickGuiFrameManager;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.unmap.ColorUtil;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.RenderUtils;
import gg.vape.wrapper.impl.Minecraft;
import java.awt.Color;

public class HudModuleFrameBase
extends Frame {
    private boolean hudEditorSelected;
    private boolean frontmostOverlay;
    private AnchoredHudModuleConfigFrame<AnimatedIconButtonComponent> anchoredSettingsFrame;
    private AnimatedIconButtonComponent closeButton;
    private AnimatedIconButtonComponent settingsButton;

    @Override
    public void Y() {
    }

    public Color applyEditorAlpha(Color color, int opacityPercent) {
        int n2 = color.getAlpha();
        if (!HudModuleConfigFrameBase.isHudEditorContext() && !ClientSettings.INSTANCE.inputEnabled) {
            float f = this.isFrontmostOverlay() ? 0.75f : (float)opacityPercent / 100.0f;
            n2 = (int)((float)n2 * f);
        }
        return ColorUtil.withAlpha(color, n2);
    }

    public double getEditorHeight() {
        return Math.max(26.0, this.L());
    }

    protected void repositionAnchoredSettings() {
        double settingsY = this.settingsButton.n();
        if (this.settingsButton.G$src$D$1b2f02a() + this.settingsButton.A() + this.anchoredSettingsFrame.A() > (double)Minecraft.G().getScaledWidth()) {
            this.anchoredSettingsFrame.M(this.settingsButton.G$src$D$1b2f02a() - this.anchoredSettingsFrame.A() + 13.0, settingsY);
        } else {
            this.anchoredSettingsFrame.M(this.settingsButton.G$src$D$1b2f02a() + this.settingsButton.A() - 13.0, settingsY);
        }
    }

    public boolean isHudEditorSelected() {
        return this.hudEditorSelected;
    }

    public void hideEditorControls() {
        this.closeButton.setVisible(false);
        this.settingsButton.setVisible(false);
        if (this.anchoredSettingsFrame.V$src$Z$1xhop3l()) {
            this.anchoredSettingsFrame.setVisible(false);
        }
        for (GuiComponent guiComponent : this.anchoredSettingsFrame.f()) {
            if (!(guiComponent instanceof DropdownSelectComponent) || !((DropdownSelectComponent)guiComponent).isExpanded()) continue;
            guiComponent.setVisible(false);
        }
    }

    public double getMaximumY() {
        return Math.floor((double)Minecraft.h() / Vape.INSTANCE.getClientSettings().getGuiScaleFactor() / 2.0 - this.L() - 2.5);
    }

    public double getMaximumX() {
        return Math.floor((double)Minecraft.J() / Vape.INSTANCE.getClientSettings().getGuiScaleFactor() / 2.0 - this.A() - 2.5);
    }

    protected void handleEditorMousePress(GuiMouseEvent guiMouseEvent) {
        if (!HudModuleConfigFrameBase.isHudEditorContext()) {
            return;
        }
        if (guiMouseEvent.getAction().equals((Object)MouseButton.LEFT_CLICK)) {
            RectData rectData;
            if (!this.isHudEditorSelected()) {
                HudModuleConfigFrameBase.closeAllHudSettings();
                this.setHudEditorSelected(true);
            }
            this.Io = RenderUtils.h();
            double d = this.A();
            if (this.isHudEditorSelected() && this.closeButton.V$src$Z$1xhop3l()) {
                d += 2.0 + this.closeButton.A() + 2.0;
            }
            if ((rectData = new RectData(this.G$src$D$1b2f02a(), this.n(), d, this.getEditorHeight())).J(this.Io.O, this.Io.H)) {
                this.IU = true;
                this.N(false);
            }
        }
    }

    public boolean isFrontmostOverlay() {
        return this.frontmostOverlay;
    }

    public AnimatedIconButtonComponent getCloseButton() {
        return this.closeButton;
    }

    public AnimatedIconButtonComponent getSettingsButton() {
        return this.settingsButton;
    }

    protected void renderFrontmostOverlayOutline() {
        if (!this.isFrontmostOverlay() || this.isHudEditorSelected()) {
            return;
        }
        Color color = new Color(HudModuleFrameBase.J.O.getRed(), HudModuleFrameBase.J.O.getGreen(), HudModuleFrameBase.J.O.getBlue(), 150);
        GuiRenderPrimitives.P(this.G$src$D$1b2f02a() - 1.0, this.n() - 1.0, this.A() + 2.0, this.getEditorHeight() + 2.0, color, 1.5f, 1.0f, 1.0f);
    }

    @Override
    public void v() {
    }

    protected RectData getEditorBounds() {
        RectData rectData = super.getBounds();
        rectData.A(rectData.e() + 20.0);
        rectData.U(this.getEditorHeight());
        return rectData;
    }

    protected void renderEditorLabel() {
        SmoothFontRenderer smoothFontRenderer = this.getFontRenderer(0.875);
        String string = this.getEditorLabel();
        float f = (float)(this.n() - smoothFontRenderer.d(string) - 3.0);
        smoothFontRenderer.T(string, (float)this.G$src$D$1b2f02a(), f, Color.WHITE, new Color(0, 0, 0, 100));
    }

    public void renderEditorControls() {
        this.closeButton.setVisible(true);
        this.closeButton.K(this.G$src$D$1b2f02a() + this.A() + 2.0);
        this.closeButton.S(this.n() - 0.25);
        this.closeButton.Y(13.0);
        this.closeButton.o(13.0);
        this.settingsButton.setVisible(true);
        this.settingsButton.K(this.G$src$D$1b2f02a() + this.A() + 2.0);
        this.settingsButton.S(this.n() + this.closeButton.L() + 2.0);
        this.settingsButton.Y(13.0);
        this.settingsButton.o(13.0);
        if (this.closeButton.t()) {
            this.closeButton.J();
        } else if (this.closeButton.w$src$Z$e457mb()) {
            this.closeButton.deactivate();
        }
        if (this.settingsButton.t()) {
            this.settingsButton.J();
        } else if (this.settingsButton.w$src$Z$e457mb()) {
            this.settingsButton.deactivate();
        }
        this.closeButton.c();
        this.settingsButton.c();
        for (double d = this.G$src$D$1b2f02a() - 2.0; d < this.G$src$D$1b2f02a() + this.A() + 4.0 + this.closeButton.A(); d += 2.0) {
            GuiRenderPrimitives.a(d, this.n() - 3.5, 1.0, 1.0f, HudModuleFrameBase.J.O);
            GuiRenderPrimitives.a(d, this.n() + this.getEditorHeight() + 3.5, 1.0, 1.0f, HudModuleFrameBase.J.O);
        }
        for (double d = this.n() - 2.0; d < this.n() + this.getEditorHeight() + 4.0; d += 2.0) {
            GuiRenderPrimitives.d(this.G$src$D$1b2f02a() - 2.0, d, 1.0, 1.0f, HudModuleFrameBase.J.O);
            GuiRenderPrimitives.d(this.G$src$D$1b2f02a() + this.A() + 2.0 + this.closeButton.A() + 2.0, d, 1.0, 1.0f, HudModuleFrameBase.J.O);
        }
    }

    protected void updateEditorControls() {
        if (this.isHudEditorSelected()) {
            this.renderEditorControls();
            if (this.V$src$Z$1xhop3l() && this.anchoredSettingsFrame.V$src$Z$1xhop3l()) {
                this.repositionAnchoredSettings();
                this.anchoredSettingsFrame.U();
            }
        } else {
            this.hideEditorControls();
        }
    }

    public void setFrontmostOverlay(boolean frontmostOverlay) {
        this.frontmostOverlay = frontmostOverlay;
    }

    @Override
    public String getName() {
        return null;
    }

    protected void closeHudFrame() {
        this.setHudEditorSelected(false);
        this.setVisible(false);
    }

    @Override
    public void t(boolean bl, boolean bl2) {
        super.t(bl, bl2);
        if (!bl && this.anchoredSettingsFrame != null) {
            this.setHudEditorSelected(false);
            this.hideEditorControls();
        }
    }

    public boolean isManagedByClickGui() {
        return ClientSettings.INSTANCE != null && ClientSettings.INSTANCE.getActiveStack() instanceof ClickGuiFrameManager;
    }

    @Override
    public void t(JsonObject jsonObject) {
        Double d;
        super.t(jsonObject);
        Double d2 = ConfigJsonUtils.getDouble(jsonObject, "width");
        if (d2 != null) {
            this.o(d2);
        }
        if ((d = ConfigJsonUtils.getDouble(jsonObject, "height")) != null) {
            this.Y(d);
        }
    }

    public Color getEditorForegroundColor() {
        return this.applyDefaultEditorAlpha(Color.WHITE);
    }

    public double getEditorMargin() {
        return Math.floor(2.5);
    }

    @Override
    protected void T(double d, double d2) {
        super.T(d, d2);
    }

    public HudModuleFrameBase(String string) {
    }

    public Color getEditorBackgroundColor() {
        int n = 102;
        return this.applyDefaultEditorAlpha(new Color(HudModuleFrameBase.J.i.getRed(), HudModuleFrameBase.J.i.getGreen(), HudModuleFrameBase.J.i.getBlue(), n));
    }

    protected void initializeEditorControls() {
        int n = (int)Math.floor(76.5);
        this.closeButton = new AnimatedIconButtonComponent("newclose", HudModuleFrameBase.J.m);
        this.settingsButton = new AnimatedIconButtonComponent("settingdots", 0.7, HudModuleFrameBase.J.m);
        this.closeButton.setDisabledOverlayColor(new Color(HudModuleFrameBase.J.m.getRed(), HudModuleFrameBase.J.m.getGreen(), HudModuleFrameBase.J.m.getBlue(), n));
        this.settingsButton.setDisabledOverlayColor(new Color(HudModuleFrameBase.J.i.getRed(), HudModuleFrameBase.J.i.getGreen(), HudModuleFrameBase.J.i.getBlue(), n));
        this.closeButton.setVisible(false);
        this.settingsButton.setVisible(false);
        this.closeButton.addClickListener(new HudModuleFrameCloseClickHandler(this));
        this.settingsButton.addClickListener(new HudModuleFrameBaseCloseClickHandler(this));
        this.anchoredSettingsFrame = new AnchoredHudModuleConfigFrame<AnimatedIconButtonComponent>(this.settingsButton);
    }

    public Color applyDefaultEditorAlpha(Color color) {
        return this.applyEditorAlpha(color, 40);
    }

    protected void dispatchEditorMouseEvent(GuiMouseEvent guiMouseEvent) {
        if (this.isHudEditorSelected()) {
            if (this.closeButton.V$src$Z$1xhop3l() && this.closeButton.t()) {
                this.closeButton.dispatchMouseEvent(guiMouseEvent);
                return;
            }
            if (this.settingsButton.V$src$Z$1xhop3l() && this.settingsButton.t()) {
                this.settingsButton.dispatchMouseEvent(guiMouseEvent);
                return;
            }
        }
        super.dispatchMouseEvent(guiMouseEvent);
        this.g(guiMouseEvent);
    }

    public double getEditorSpacing() {
        return this.getEditorMargin();
    }

    public String getEditorLabel() {
        String string = this.getName();
        if (string != null && !string.isEmpty()) {
            return string;
        }
        return this.getClass().getSimpleName();
    }

    public void setHudEditorSelected(boolean selected) {
        this.hudEditorSelected = selected;
    }

    protected void openAnchoredSettings() {
    }

    public AnchoredHudModuleConfigFrame<AnimatedIconButtonComponent> getAnchoredSettingsFrameInternal() {
        return this.anchoredSettingsFrame;
    }

    public float getEditorOpacity() {
        if (!HudModuleConfigFrameBase.isHudEditorContext() && !ClientSettings.INSTANCE.inputEnabled) {
            return this.isFrontmostOverlay() ? 0.75f : 0.4f;
        }
        return 1.0f;
    }

    @Override
    public JsonObject Z() {
        JsonObject jsonObject = super.Z();
        jsonObject.addProperty("width", (Number)this.A());
        jsonObject.addProperty("height", (Number)this.L());
        return jsonObject;
    }
}

