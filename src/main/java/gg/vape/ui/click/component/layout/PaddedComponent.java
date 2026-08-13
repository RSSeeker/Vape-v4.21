package gg.vape.ui.click.component.layout;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.frame.FrameComponent;
import gg.vape.ui.click.layout.ComponentLayout;
import java.awt.Color;
import org.jetbrains.annotations.Nullable;

public class PaddedComponent
extends FrameComponent {
    private final double leftPadding;
    private static final String WRAP_LAYOUT = "wrap";
    private final double bottomPadding;
    private double topPadding;
    private final SpacerComponent leftSpacer;
    private final FrameComponent contentPanel = new PanelComponent(0.0, 0.0);
    private final double rightPadding;
    private final SpacerComponent topSpacer;
    private final GuiComponent content;

    public FrameComponent z$src$Lgg_vape_ui_click_frame_FrameComponent_$s47o9d() {
        return this.contentPanel;
    }

    @Override
    public double x() {
        return this.contentPanel.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().C() + this.rightPadding;
    }

    @Override
    public void C$src$V$nadrmg() {
        super.C$src$V$nadrmg();
        this.contentPanel.C$src$V$nadrmg();
    }

    public GuiComponent H$src$Lgg_vape_ui_click_component_GuiComponent_$kfnvup() {
        return this.content;
    }

    @Override
    public double C() {
        this.contentPanel.Y(this.contentPanel.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().y());
        this.contentPanel.o(this.contentPanel.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().C());
        return this.contentPanel.L() + this.topPadding + this.bottomPadding;
    }

    @Override
    public void v() {
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    public PaddedComponent(double d, double d2, double d3, double d4, GuiComponent guiComponent) {
        this.topPadding = d;
        this.bottomPadding = d2;
        this.leftPadding = d3;
        this.rightPadding = d4;
        this.content = guiComponent;
        ComponentLayout componentLayout = this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij();
        componentLayout.t(false);
        componentLayout.M(false);
        componentLayout.U(false);
        componentLayout.I(false);
        componentLayout.u(false);
        componentLayout.M(WRAP_LAYOUT);
        this.topSpacer = new SpacerComponent(0.0, d);
        this.leftSpacer = new SpacerComponent(d3, 0.0);
        this.contentPanel.addChildren(this.leftSpacer, guiComponent);
        this.addChildren(this.topSpacer, this.contentPanel);
        this.contentPanel.setShowDisabledOverlay(false);
        this.setShowDisabledOverlay(false);
    }

    public double o$src$D$1nnrfcl() {
        return this.rightPadding;
    }

    public double h() {
        return this.bottomPadding;
    }

    @Override
    public void Y() {
    }

    public void N(double d) {
        this.topPadding = d;
        this.topSpacer.Y(d);
        this.topSpacer.setShowDisabledOverlay(true);
        this.topSpacer.setDisabledOverlayColor(Color.magenta);
    }

    public double c$src$D$1nh5w89() {
        return this.leftPadding;
    }


    public PaddedComponent(double d, GuiComponent guiComponent) {
        this(d, d, guiComponent);
    }

    @Nullable
    public <T extends GuiComponent> T t(Class<T> clazz) {
        return (T)(clazz.isInstance(this.content) ? (GuiComponent)clazz.cast(this.content) : null);
    }

    @Override
    public void V() {
    }

    public PaddedComponent(double d, double d2, GuiComponent guiComponent) {
        this(d2, d2, d, d, guiComponent);
    }
}

