package gg.vape.ui.click.frame.impl.hud;

import func.skidline.RectData;
import gg.vape.Vape;
import gg.vape.input.KeyboardInput;
import gg.vape.module.MinecraftVersionConstraint;
import gg.vape.module.Mod;
import gg.vape.module.none.ClientSettings;
import gg.vape.module.render.hud.HudModule;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.MousePosition;
import gg.vape.ui.click.component.AnimatedIconButtonComponent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.value.ValueComponentFactory;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.impl.main.ClickGuiFrameManager;
import gg.vape.utils.render.BlurRegionRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.RenderUtils;
import gg.vape.value.BooleanValue;
import gg.vape.value.Value;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.ScaledResolution;
import java.awt.Color;

public abstract class HudModuleConfigFrameBase<T extends HudModule>
extends HudModuleFrameBase {
    private double dragOffsetY = -1.0;
    private double dragOffsetX = -1.0;
    private final BlurRegionRenderer backgroundBlur = new BlurRegionRenderer(0, 0);
    private final BooleanValue renderBackgroundSetting;
    private double snapReleaseMouseX = -1.0;
    public T module;
    private double snapReleaseMouseY = -1.0;

    @Override
    public void Y() {
        if (!this.IU || KeyboardInput.isKeyDown(160)) {
            this.snapReleaseMouseX = -1.0;
            this.snapReleaseMouseY = -1.0;
            this.dragOffsetX = -1.0;
            this.dragOffsetY = -1.0;
        }
        if (this.shouldRenderHudBackground()) {
            this.backgroundBlur.setDimensions((int)this.A() * 2, (int)this.L() * 2);
            this.backgroundBlur.renderBlur((int)this.G$src$D$1b2f02a(), (int)this.n(), 6.0f, 4.0f);
            GuiRenderPrimitives.d(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), this.getEditorBackgroundColor());
        }
        this.updateEditorControls();
        this.renderHudContent();
        this.renderFrontmostOverlayOutline();
        if ((this.w$src$Z$e457mb() || this.isHudEditorSelected() || this.isFrontmostOverlay()) && ClientSettings.INSTANCE != null && ClientSettings.INSTANCE.getActiveStack() instanceof ClickGuiFrameManager) {
            this.renderEditorLabel();
        }
    }

    @Override
    protected void closeHudFrame() {
        this.setHudEditorSelected(false);
        ((Mod)this.getModule()).setEnabled(false);
        this.setVisible(false);
    }

    private HudSnapCandidate[] findSnapCandidates(RectData rectData) {
        HudSnapCandidate hudSnapCandidate;
        double d;
        double d2;
        double d3;
        double d4;
        double d5;
        HudSnapCandidate hudSnapCandidate2 = null;
        HudSnapCandidate hudSnapCandidate3 = null;
        double d6 = 2.0;
        double d7 = 60.0;
        for (HudSnapEdge hudSnapEdge : HudSnapEdge.getXEdges()) {
            for (HudSnapEdge hudSnapEdge2 : HudSnapEdge.getXEdges()) {
                d5 = hudSnapEdge.getPosition(rectData) - hudSnapEdge2.getPosition(this.getBounds());
                if (!(Math.abs(d5) <= d6)) continue;
                d4 = HudSnapEdge.TOP.getPosition(rectData) - HudSnapEdge.TOP.getPosition(this.getBounds());
                if (d4 > 0.0) {
                    d3 = HudSnapEdge.BOTTOM.getPosition(this.getBounds());
                    d2 = HudSnapEdge.TOP.getPosition(rectData);
                } else {
                    d3 = HudSnapEdge.BOTTOM.getPosition(rectData);
                    d2 = HudSnapEdge.TOP.getPosition(this.getBounds());
                }
                d = hudSnapEdge.getPosition(rectData);
                hudSnapCandidate = new HudSnapCandidate(hudSnapEdge.getPosition(rectData), d3,
                        hudSnapEdge2.getPosition(this.getBounds()), d2, hudSnapEdge, hudSnapEdge2, d);
                if (!(hudSnapCandidate.getHeight() < d7)
                        || hudSnapCandidate2 != null
                        && !(hudSnapCandidate.getHeight() < hudSnapCandidate2.getHeight())) continue;
                hudSnapCandidate2 = hudSnapCandidate;
            }
        }
        for (HudSnapEdge hudSnapEdge : HudSnapEdge.getYEdges()) {
            for (HudSnapEdge hudSnapEdge2 : HudSnapEdge.getYEdges()) {
                d5 = hudSnapEdge.getPosition(rectData) - hudSnapEdge2.getPosition(this.getBounds());
                if (!(Math.abs(d5) <= d6)) continue;
                d4 = HudSnapEdge.LEFT.getPosition(rectData) - HudSnapEdge.LEFT.getPosition(this.getBounds());
                if (d4 > 0.0) {
                    d3 = HudSnapEdge.RIGHT.getPosition(this.getBounds());
                    d2 = HudSnapEdge.LEFT.getPosition(rectData);
                } else {
                    d3 = HudSnapEdge.RIGHT.getPosition(rectData);
                    d2 = HudSnapEdge.LEFT.getPosition(this.getBounds());
                }
                d = hudSnapEdge.getPosition(rectData);
                hudSnapCandidate = new HudSnapCandidate(d3, hudSnapEdge.getPosition(rectData), d2,
                        hudSnapEdge2.getPosition(this.getBounds()), hudSnapEdge, hudSnapEdge2, d);
                if (!(hudSnapCandidate.getWidth() < d7)
                        || hudSnapCandidate3 != null
                        && !(hudSnapCandidate.getWidth() < hudSnapCandidate3.getWidth())) continue;
                hudSnapCandidate3 = hudSnapCandidate;
            }
        }
        return new HudSnapCandidate[]{hudSnapCandidate2, hudSnapCandidate3};
    }

    @Override
    protected void openAnchoredSettings() {
        AnchoredHudModuleConfigFrame<AnimatedIconButtonComponent> anchoredHudModuleConfigFrame = this.getAnchoredSettingsFrameInternal();
        anchoredHudModuleConfigFrame.setHudModule((HudModule)this.getModule());
        anchoredHudModuleConfigFrame.removeMarkedChildren();
        for (Value<?, ?> value : anchoredHudModuleConfigFrame.getHudModule().getValues()) {
            GuiComponent guiComponent = ValueComponentFactory.createMainValueComponent(value);
            if (guiComponent == null) continue;
            if (value.getParent() != null) {
                guiComponent.setDisabledOverlayColor(HudModuleConfigFrameBase.J.r);
            } else {
                guiComponent.setDisabledOverlayColor(HudModuleConfigFrameBase.J.i);
            }
            anchoredHudModuleConfigFrame.h(guiComponent, new Object[0]);
        }
        anchoredHudModuleConfigFrame.setVisible(true);
        anchoredHudModuleConfigFrame.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc().setVisible(true);
        anchoredHudModuleConfigFrame.t(170.0);
        anchoredHudModuleConfigFrame.l$src$V$1mibm4x();
        if (!ClientSettings.INSTANCE.getActiveStack().Y().contains(anchoredHudModuleConfigFrame)) {
            ClientSettings.INSTANCE.getActiveStack().q(anchoredHudModuleConfigFrame);
        }
    }

    @Override
    protected void repositionAnchoredSettings() {
        this.getAnchoredSettingsFrameInternal().repositionToAnchor();
    }

    public void setBackgroundEnabled(boolean enabled) {
        this.renderBackgroundSetting.setValue(enabled);
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
        this.handleEditorMousePress(guiMouseEvent);
    }

    public HudModuleConfigFrameBase(Class<T> clazz) {
        super(clazz.getName());
        this.module = (T)Vape.INSTANCE.getModManager().getMod(clazz);
        this.setDisabledOverlayColor(HudModuleConfigFrameBase.J.i);
        this.K(30.0);
        this.S(10.0);
        this.N(true);
        this.setVisible(false);
        this.setDisabledOverlayColor(HudModuleConfigFrameBase.J.t);
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M(false);
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        this.renderBackgroundSetting = BooleanValue.create(this.module, "Render background", true);
        ((Mod)this.module).P(this.renderBackgroundSetting, new MinecraftVersionConstraint[0]);
        this.initializeEditorControls();
        ClientSettings.hudEditorStack.q(this.getAnchoredSettingsFrameInternal());
        this.addChildren(this.getCloseButton(), this.getSettingsButton());
    }

    public static boolean isHudEditorContext() {
        return ClientSettings.isHudEditorStack() || ClientSettings.INSTANCE.getActiveStack() == ClientSettings.clickGuiFrameManager && ClientSettings.clickGuiFrameManager.getOverlaySelector() != null && ClientSettings.clickGuiFrameManager.getOverlaySelector().V$src$Z$1xhop3l();
    }

    @Override
    public boolean l$src$Z$193vdc5() {
        return false;
    }

    private void applySnapping(RectData rectData) {
        double d;
        double d2;
        HudSnapCandidate[] hudSnapCandidateArray = this.findSnapCandidates(rectData);
        HudSnapCandidate hudSnapCandidate = hudSnapCandidateArray[0];
        HudSnapCandidate hudSnapCandidate2 = hudSnapCandidateArray[1];
        if (hudSnapCandidate == null && hudSnapCandidate2 == null) {
            return;
        }
        MousePosition mousePosition = RenderUtils.h();
        boolean bl = false;
        boolean bl2 = false;
        double d3 = this.G$src$D$1b2f02a();
        double d4 = this.n();
        double d5 = this.A() - 20.0;
        double d6 = 2.0;
        if (hudSnapCandidate != null && hudSnapCandidate.getWidth() <= d6) {
            d3 -= hudSnapCandidate.getWidth();
            bl = true;
            if (this.snapReleaseMouseX == -1.0) {
                this.snapReleaseMouseX = mousePosition.O;
            }
        }
        if (hudSnapCandidate2 != null && hudSnapCandidate2.getHeight() <= d6) {
            d4 -= hudSnapCandidate2.getHeight();
            bl2 = true;
            if (this.snapReleaseMouseY == -1.0) {
                this.snapReleaseMouseY = mousePosition.H;
            }
        }
        if (bl || bl2) {
            float f;
            if (this.snapReleaseMouseX != -1.0 && Math.abs(f = (float)((double)mousePosition.O - this.snapReleaseMouseX)) > 5.0f) {
                bl = false;
                d3 += (double)f;
                this.snapReleaseMouseX = -1.0;
            }
            if (this.snapReleaseMouseY != -1.0 && Math.abs(f = (float)((double)mousePosition.H - this.snapReleaseMouseY)) > 5.0f) {
                bl2 = false;
                d4 += (double)f;
                this.snapReleaseMouseY = -1.0;
            }
        }
        this.Y(d3, d4);
        if (!bl && (d2 = this.G$src$D$1b2f02a() + this.dragOffsetX) != (double)mousePosition.O) {
            if (this.G$src$D$1b2f02a() < 3.0) {
                this.dragOffsetX = (double)RenderUtils.h().O - this.G$src$D$1b2f02a();
            } else {
                d3 = Math.abs((double)mousePosition.O - this.dragOffsetX);
            }
        }
        if (!bl2 && (d = this.n() + this.dragOffsetY) != (double)mousePosition.H) {
            if (this.n() < 4.0) {
                this.dragOffsetY = (double)RenderUtils.h().H - this.n();
            } else {
                d4 = Math.abs((double)mousePosition.H - this.dragOffsetY);
            }
        }
        if (!bl || !bl2) {
            this.Y(d3, d4);
        }
        block4: for (HudSnapCandidate hudSnapCandidate3 : hudSnapCandidateArray) {
            if (hudSnapCandidate3 == null) continue;
            double d7 = hudSnapCandidate3.getBounds().get(HudSnapEdge.LEFT);
            double d8 = hudSnapCandidate3.getBounds().get(HudSnapEdge.TOP);
            double d9 = hudSnapCandidate3.getBounds().get(HudSnapEdge.RIGHT);
            double d10 = hudSnapCandidate3.getBounds().get(HudSnapEdge.BOTTOM);
            switch (hudSnapCandidate3.getTargetEdge()) {
                case RIGHT: 
                case LEFT: 
                case VERTICAL_CENTRE: {
                    if (Math.abs(hudSnapCandidate3.getHeight()) < 0.5) continue block4;
                    d7 = d9;
                }
                case BOTTOM: 
                case TOP: 
                case HORIZONTAL_CENTRE: {
                    if (Math.abs(hudSnapCandidate3.getWidth()) < 0.5) continue block4;
                    d8 = d10;
                }
                default: {
                    GuiRenderPrimitives.u(d7, d8, d9, d10, 1.5f, Color.GREEN);
                }
            }
        }
    }

    public boolean shouldRenderHudBackground() {
        return this.renderBackgroundSetting.getEffectiveValue() != false && this.L() > 0.0;
    }

    @Override
    public double C() {
        return 30.0;
    }

    public AnchoredHudModuleConfigFrame<AnimatedIconButtonComponent> getAnchoredSettingsFrame() {
        return this.getAnchoredSettingsFrameInternal();
    }

    @Override
    public RectData getBounds() {
        return this.getEditorBounds();
    }

    @Override
    public void dispatchMouseEvent(GuiMouseEvent guiMouseEvent) {
        super.dispatchMouseEvent(guiMouseEvent);
        this.g(guiMouseEvent);
    }

    private void updateSnapping() {
        if (!this.isHudEditorSelected()) {
            return;
        }
        if (this.dragOffsetX == -1.0 || this.dragOffsetY == -1.0) {
            this.dragOffsetX = (double)RenderUtils.h().O - this.G$src$D$1b2f02a();
            this.dragOffsetY = (double)RenderUtils.h().H - this.n();
        }
        for (Frame frame : ClientSettings.getAllFrames()) {
            if (!(frame instanceof HudModuleConfigFrameBase) || !frame.V$src$Z$1xhop3l() || frame.equals(this)) continue;
            RectData rectData = frame.getBounds();
            RectData rectData2 = new RectData(rectData.o() - 2.0, rectData.W() - 2.0, rectData.e() + 4.0, rectData.R() + 4.0);
            this.applySnapping(rectData2);
        }
        ScaledResolution scaledResolution = Minecraft.G();
        this.applySnapping(new RectData((double)scaledResolution.getScaledWidth() / 2.0, 0.0, 20.0, scaledResolution.getScaledHeight()));
    }

    public boolean shouldDrawBackground() {
        return this.renderBackgroundSetting.getEffectiveValue();
    }

    @Override
    public void v() {
        if (this.shouldRenderHudBackground()) {
            this.backgroundBlur.setDimensions((int)this.A() * 2, (int)this.L() * 2);
            this.backgroundBlur.renderBlur((int)this.G$src$D$1b2f02a(), (int)this.n(), 6.0f, 4.0f);
            GuiRenderPrimitives.d(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), this.getEditorBackgroundColor());
        }
        if (this.isHudEditorSelected()) {
            this.setHudEditorSelected(false);
        }
        this.renderHudContent();
    }

    public void setBackgroundSettingSuppressed(boolean suppressed) {
        this.renderBackgroundSetting.setHidden(!suppressed);
    }

    public static void closeAllHudSettings() {
        for (Frame frame : ClientSettings.getAllFrames()) {
            if (frame instanceof HudModuleConfigFrameBase) {
                HudModuleConfigFrameBase hudModuleConfigFrameBase = (HudModuleConfigFrameBase)frame;
                hudModuleConfigFrameBase.setHudEditorSelected(false);
            }
            if (!(frame instanceof HudSettingsFrameBase)) continue;
            ((HudSettingsFrameBase)frame).setHudEditorSelected(false);
        }
    }


    @Override
    public String getEditorLabel() {
        if (this.module != null && ((Mod)this.module).getName() != null && !((Mod)this.module).getName().isEmpty()) {
            return ((Mod)this.module).getName();
        }
        return super.getEditorLabel();
    }

    public abstract void renderHudContent();

    public T getModule() {
        return this.module;
    }

    @Override
    public double x() {
        return 50.0;
    }
}
