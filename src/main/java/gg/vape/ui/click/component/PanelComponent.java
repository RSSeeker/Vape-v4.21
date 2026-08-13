package gg.vape.ui.click.component;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.frame.FrameComponent;
import gg.vape.ui.click.layout.ComponentLayout;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;
import org.jetbrains.annotations.Nullable;

public class PanelComponent
extends FrameComponent {
    @Nullable
    private Color outlineColor;
    private float cornerRadius = 1.5f;
    private float borderWidth = 1.0f;
    private static boolean legacyInitialized;
    private boolean shadowEnabled = true;

    @Override
    public double C() {
        return 0.0;
    }

    public boolean isShadowEnabled() {
        return this.shadowEnabled;
    }

    @Override
    public void v() {
    }

    public PanelComponent(double width, double height) {
        this.o(width);
        this.Y(height);
        ComponentLayout componentLayout = this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij();
        componentLayout.t(false);
        componentLayout.M(false);
        componentLayout.U(false);
        componentLayout.I(false);
        componentLayout.u(false);
    }

    public void setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
    }

    public static boolean isLegacyInitialized() {
        return legacyInitialized;
    }

    @Override
    public double x() {
        return 0.0;
    }

    static {
        if (PanelComponent.needsLegacyInitialization()) {
            PanelComponent.setLegacyInitialized(true);
        }
    }

    public float getBorderWidth() {
        return this.borderWidth;
    }

    public static void setLegacyInitialized(boolean initialized) {
        legacyInitialized = initialized;
    }

    public float getCornerRadius() {
        return this.cornerRadius;
    }

    @Override
    public void g(GuiMouseEvent mouseEvent) {
    }

    @Nullable
    public Color getOutlineColor() {
        return this.outlineColor;
    }

    public void setCornerRadius(float cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    @Override
    public void Y() {
    }

    public static boolean needsLegacyInitialization() {
        return !PanelComponent.isLegacyInitialized();
    }

    public void setOutlineColor(@Nullable Color outlineColor) {
        this.outlineColor = outlineColor;
    }

    @Override
    public void V() {
    }

    @Override
    public void z(boolean useViewportHeight) {
        double renderX = this.G$src$D$1b2f02a();
        double renderY = this.n();
        double renderWidth = this.A();
        double renderHeight = useViewportHeight ? this.K : this.L();
        Color backgroundColor = this.getDisabledOverlayColor();
        if (this.shadowEnabled && this.u$src$I$ikouxa() > 0) {
            GuiRenderPrimitives.p(renderX, renderY, renderWidth, renderHeight, backgroundColor, this.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc() != null, this.cornerRadius, 1.0f, 8.0f, PanelComponent.J.u, this.u$src$I$ikouxa());
            if (this.outlineColor != null) {
                GuiRenderPrimitives.P(renderX, renderY, renderWidth, renderHeight, this.outlineColor, this.cornerRadius, 0.75f, 1.0f);
            }
        } else if (this.outlineColor != null) {
            GuiRenderPrimitives.q(renderX, renderY, renderWidth, renderHeight, this.borderWidth, backgroundColor, this.outlineColor);
        } else {
            GuiRenderPrimitives.C(renderX, renderY, renderWidth, renderHeight, backgroundColor);
        }
    }


    public void setShadowEnabled(boolean shadowEnabled) {
        this.shadowEnabled = shadowEnabled;
    }
}

