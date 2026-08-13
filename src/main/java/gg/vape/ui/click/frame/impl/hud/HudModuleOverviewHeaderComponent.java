package gg.vape.ui.click.frame.impl.hud;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.IconButtonComponent;
import gg.vape.ui.click.frame.FrameHeaderComponent;
import gg.vape.ui.click.frame.impl.hud.HudModuleOverviewFrame;
import gg.vape.ui.click.frame.impl.hud.HudModuleOverviewOpenSelectorClickHandler;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

public class HudModuleOverviewHeaderComponent
extends FrameHeaderComponent {
    private final float iconScale;
    private final IconButtonComponent expandButton = new IconButtonComponent("max");

    public HudModuleOverviewHeaderComponent(HudModuleOverviewFrame hudModuleOverviewFrame, float f) {
        super(hudModuleOverviewFrame);
        this.iconScale = f;
        this.expandButton.addClickListener(new HudModuleOverviewOpenSelectorClickHandler());
        this.addChildren(this.expandButton);
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    @Override
    public void H() {
        SmoothFontRenderer smoothFontRenderer = this.getFontRenderer(0.75);
        Color color = HudModuleOverviewHeaderComponent.J.Z;
        double d = smoothFontRenderer.d(this.w$src$Lgg_vape_ui_click_frame_Frame_$y4htd0().getName());
        double d2 = this.n() + this.L() / 2.0 - d / 2.0;
        float f = 8.0f * this.iconScale;
        smoothFontRenderer.d("Favorites", this.G$src$D$1b2f02a() + 10.0 + (double)f + 5.0, d2, color);
        GuiRenderPrimitives.F("legitmodeicon", this.G$src$D$1b2f02a() + 5.0 + 8.0, this.n() + this.L() / 2.0, (double)f, f, Color.white);
        this.expandButton.K(this.G$src$D$1b2f02a() + this.A() - 20.0 + 3.5);
        this.expandButton.S(this.n());
        this.expandButton.Y(this.L());
    }

    @Override
    public void u() {
    }

    @Override
    public void I() {
    }

    @Override
    public void F() {
    }

    public HudModuleOverviewHeaderComponent(HudModuleOverviewFrame hudModuleOverviewFrame) {
        this(hudModuleOverviewFrame, 1.0f);
    }

    @Override
    public double A() {
        return this.w$src$Lgg_vape_ui_click_frame_Frame_$y4htd0().A();
    }
}
