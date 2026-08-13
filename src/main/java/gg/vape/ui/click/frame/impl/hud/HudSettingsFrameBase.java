package gg.vape.ui.click.frame.impl.hud;

import func.skidline.RectData;
import gg.vape.Vape;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.AnimatedIconButtonComponent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.frame.CollapsibleFrame;
import gg.vape.ui.click.frame.SettingsFrameHeaderComponent;
import gg.vape.ui.click.frame.impl.hud.AnchoredHudModuleConfigFrame;
import gg.vape.ui.click.frame.impl.hud.HudModuleFrameBase;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.value.Value;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class HudSettingsFrameBase
extends HudModuleFrameBase
implements CollapsibleFrame {
    private static final String WRAP_LAYOUT = "wrap";
    private boolean collapsed = true;
    private final List<GuiComponent> settingComponents = new ArrayList<GuiComponent>();
    private boolean headerHiddenForHudMode;

    @Override
    public boolean q() {
        return this.collapsed;
    }

    @Override
    public double getMaximumY() {
        return super.getMaximumY() - 1.0;
    }

    private void updateInteractionState() {
        boolean interactive = this.isManagedByClickGui() && !this.isPublicProfilePreview();
        this.setUseExplicitWidth(interactive);
        this.setUseExplicitHeight(interactive);
    }

    public List<GuiComponent> getSettingComponents() {
        return this.settingComponents;
    }

    @Override
    protected void openAnchoredSettings() {
        AnchoredHudModuleConfigFrame<AnimatedIconButtonComponent> anchoredHudModuleConfigFrame = this.getAnchoredSettingsFrameInternal();
        this.restoreSettingComponents();
        anchoredHudModuleConfigFrame.removeMarkedChildren();
        for (GuiComponent guiComponent : this.getSettingComponents()) {
            if (guiComponent.getBoundValue() == null) continue;
            this.removeChild(guiComponent);
            guiComponent.setVisible(true);
            anchoredHudModuleConfigFrame.h(guiComponent, new Object[0]);
        }
        anchoredHudModuleConfigFrame.setVisible(true);
        anchoredHudModuleConfigFrame.t(170.0);
        anchoredHudModuleConfigFrame.H(true);
        if (!ClientSettings.INSTANCE.getActiveStack().Y().contains(anchoredHudModuleConfigFrame)) {
            ClientSettings.INSTANCE.getActiveStack().q(anchoredHudModuleConfigFrame);
        }
    }

    @Override
    public void w() {
        this.collapsed = !this.collapsed;
        this.updateSettingVisibility();
    }


    public void addSettings(GuiComponent ... guiComponentArray) {
        Collections.addAll(this.getSettingComponents(), guiComponentArray);
        this.addChildren(guiComponentArray);
        this.updateSettingVisibility();
    }

    @Override
    public double getComponentHeight() {
        double totalHeight = super.getComponentHeight();
        if (this.q()) {
            return totalHeight;
        }
        double excludedHeight = 0.0;
        for (GuiComponent guiComponent : this.f()) {
            if (!guiComponent.V$src$Z$1xhop3l()
                    || this.settingComponents.contains(guiComponent)
                    || guiComponent.equals(this.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc())) continue;
            excludedHeight += guiComponent.L();
        }
        return totalHeight - excludedHeight;
    }

    public AnchoredHudModuleConfigFrame<AnimatedIconButtonComponent> getAnchoredSettingsFrame() {
        return this.getAnchoredSettingsFrameInternal();
    }

    public void onPublicProfileContextChanged() {
    }

    @Override
    public RectData getBounds() {
        if (this.isManagedByClickGui()) {
            return this.getEditorBounds();
        }
        return super.getBounds();
    }

    private void hideHeaderForHudMode() {
        if (!this.headerHiddenForHudMode
                && this.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc() != null) {
            this.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc().setVisible(false);
            this.headerHiddenForHudMode = true;
            this.updateSettingVisibility();
        }
    }

    public HudSettingsFrameBase(String title, String frameId) {
        super(frameId);
        this.setDisabledOverlayColor(HudSettingsFrameBase.J.i);
        this.K(300.0);
        this.S(100.0);
        this.setVisible(false);
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M(false);
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M(WRAP_LAYOUT);
        this.Y(new SettingsFrameHeaderComponent(this, title, frameId));
        this.initializeEditorControls();
        this.getAnchoredSettingsFrameInternal().setTitle(this.getName());
    }

    public boolean isPublicProfilePreviewActive() {
        return this.isPublicProfilePreview();
    }

    protected boolean isPublicProfilePreview() {
        return this.isManagedByClickGui() && Vape.INSTANCE.getPublicProfileSettings().centralGuiStyle.isSelected();
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
        if (this.isManagedByClickGui()) {
            this.handleEditorMousePress(guiMouseEvent);
            return;
        }
        super.g(guiMouseEvent);
    }

    private void renderHudBackground() {
        this.updateEditorControls();
    }

    private void restoreHeaderOutsideHudMode() {
        if (this.headerHiddenForHudMode
                && this.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc() != null) {
            this.restoreSettingComponents();
            this.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc().setVisible(true);
            this.headerHiddenForHudMode = false;
            this.setHudEditorSelected(false);
            this.getCloseButton().setVisible(false);
            this.getSettingsButton().setVisible(false);
            if (this.getAnchoredSettingsFrameInternal().V$src$Z$1xhop3l()) {
                this.getAnchoredSettingsFrameInternal().setVisible(false);
            }
            this.updateSettingVisibility();
        }
    }

    @Override
    public double L() {
        if (this.isManagedByClickGui()) {
            return Math.max(26.0, super.L());
        }
        return this.q() ? this.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc().L() : super.L();
    }

    @Override
    public float getEditorOpacity() {
        if (!this.isManagedByClickGui()) {
            return 1.0f;
        }
        return super.getEditorOpacity();
    }

    protected void renderHudModeBorder() {
        if (this.isHudEditorSelected()) {
            return;
        }
        GuiRenderPrimitives.P(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), this.getEditorBackgroundColor(), 1.5f, 1.0f, 1.0f);
    }

    private void updateSettingVisibility() {
        for (GuiComponent guiComponent : this.getSettingComponents()) {
            boolean visible = !this.collapsed;
            if (this.isManagedByClickGui() && guiComponent.getBoundValue() != null) {
                visible = false;
            }
            guiComponent.setVisible(visible);
            guiComponent.setDisabledOverlayColor(HudSettingsFrameBase.J.r);
            Value value = guiComponent.getBoundValue();
            if (value == null || value.getParent() == null) continue;
            Color color = value.getOverrideColor() == null ? HudSettingsFrameBase.J.r.darker() : value.getOverrideColor();
            guiComponent.setDisabledOverlayColor(color);
        }
        this.l$src$V$1mibm4x();
    }

    @Override
    public Color applyEditorAlpha(Color color, int amount) {
        if (!this.isManagedByClickGui()) {
            return color;
        }
        return super.applyEditorAlpha(color, amount);
    }

    @Override
    public void H(boolean active) {
        this.updateInteractionState();
        super.H(active);
    }

    @Override
    public void dispatchMouseEvent(GuiMouseEvent guiMouseEvent) {
        if (this.isManagedByClickGui()) {
            this.dispatchEditorMouseEvent(guiMouseEvent);
            return;
        }
        super.dispatchMouseEvent(guiMouseEvent);
    }

    @Override
    protected void closeHudFrame() {
        this.restoreSettingComponents();
        this.setHudEditorSelected(false);
        this.setVisible(false);
    }

    @Override
    public boolean isShowDisabledOverlay() {
        if (this.isManagedByClickGui()) {
            return false;
        }
        return super.isShowDisabledOverlay();
    }

    @Override
    public double getMaximumX() {
        return super.getMaximumX() + 1.0;
    }

    @Override
    public void H() {
        this.updateInteractionState();
        if (this.isManagedByClickGui()) {
            this.hideHeaderForHudMode();
            this.renderHudModeBorder();
            this.renderHudBackground();
            this.renderFrontmostOverlayOutline();
            if (this.w$src$Z$e457mb() || this.isHudEditorSelected() || this.isFrontmostOverlay()) {
                this.renderEditorLabel();
            }
        } else {
            this.restoreHeaderOutsideHudMode();
        }
        super.H();
    }

    private void restoreSettingComponents() {
        AnchoredHudModuleConfigFrame<AnimatedIconButtonComponent> anchoredHudModuleConfigFrame = this.getAnchoredSettingsFrameInternal();
        for (GuiComponent guiComponent : this.getSettingComponents()) {
            if (guiComponent.getBoundValue() == null || guiComponent.getParentFrameComponent() != anchoredHudModuleConfigFrame) continue;
            anchoredHudModuleConfigFrame.removeChild(guiComponent);
            this.h(guiComponent, new Object[0]);
        }
        this.updateSettingVisibility();
    }

    @Override
    public void hideEditorControls() {
        this.restoreSettingComponents();
        super.hideEditorControls();
    }
}

