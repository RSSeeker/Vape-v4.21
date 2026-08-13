package gg.vape.ui.click.component;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.animation.ColorAnimation;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.TextInputComponentBase;
import gg.vape.ui.click.component.gui.InteractiveComponent;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

public class AnimatedPanelComponent
extends PanelComponent {
    private boolean useStaticBorderColor;
    private float borderAlpha = 1.0f;
    private float borderRadius = 2.0f;
    private ColorAnimation borderAnimation;
    private Color overrideColor = null;

    private GuiComponent findHoveredInteractiveDescendant(GuiComponent component) {
        ArrayList<GuiComponent> descendants = this.X(new ArrayList<GuiComponent>(Arrays.asList(component)), 0);
        for (GuiComponent descendant : descendants) {
            if (!descendant.V$src$Z$1xhop3l() || !descendant.w$src$Z$e457mb() || !(descendant instanceof InteractiveComponent) && !(descendant instanceof TextInputComponentBase)) continue;
            return descendant;
        }
        return null;
    }

    public void setBorderRadius(float borderRadius) {
        this.borderRadius = borderRadius;
    }

    @Override
    public void J() {
        super.J();
    }

    public void setBorderAlpha(float borderAlpha) {
        this.borderAlpha = borderAlpha;
    }


    @Override
    public void z(boolean useAlternateHeight) {
        if (!this.isShowDisabledOverlay()) {
            return;
        }
        GuiRenderPrimitives.e(this.G$src$D$1b2f02a(), this.n(), this.A(), useAlternateHeight ? this.K : this.L(), this.useStaticBorderColor ? AnimatedPanelComponent.J.K : this.borderAnimation.getInterpolatedColor(), this.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc() != null, 2.0f, 1.0f);
    }

    public void toggleStaticBorderColor() {
        this.useStaticBorderColor = !this.useStaticBorderColor;
    }

    public float getBorderRadius() {
        return this.borderRadius;
    }

    public AnimatedPanelComponent(double width, double height) {
        super(width, height);
        this.setDisabledOverlayColor(AnimatedPanelComponent.J.m);
        this.borderAnimation = new ColorAnimation(0.15, this.getDisabledOverlayColor(), new Color(36, 35, 36));
    }

    @Override
    public void F() {
        if (!this.w$src$Z$e457mb()) {
            // empty if block
        }
        super.F();
    }

    public Color getOverrideColor() {
        return this.overrideColor;
    }

    public void setBorderAnimation(ColorAnimation borderAnimation) {
        this.borderAnimation = borderAnimation;
    }

    public ColorAnimation getBorderAnimation() {
        return this.borderAnimation;
    }

    public float getBorderAlpha() {
        return this.borderAlpha;
    }

    @Override
    public void u() {
        super.u();
    }

    public AnimatedPanelComponent(double width, double height, Color backgroundColor, Color hoverBorderColor) {
        super(width, height);
        this.setDisabledOverlayColor(backgroundColor);
        this.borderAnimation = new ColorAnimation(0.15, this.getDisabledOverlayColor(), hoverBorderColor);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    public void setOverrideColor(Color overrideColor) {
        this.overrideColor = overrideColor;
    }

    @Override
    public void dispatchMouseEvent(GuiMouseEvent mouseEvent) {
        super.dispatchMouseEvent(mouseEvent);
    }
}

