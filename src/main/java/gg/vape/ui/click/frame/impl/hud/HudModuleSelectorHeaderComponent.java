package gg.vape.ui.click.frame.impl.hud;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.IconButtonComponent;
import gg.vape.ui.click.frame.FrameHeaderComponent;
import gg.vape.ui.click.frame.impl.hud.HudModuleSelectorFrame;
import gg.vape.ui.click.frame.impl.hud.HudModuleSelectorOpenConfigFrameClickHandler;
import gg.vape.ui.click.frame.impl.hud.HudModuleSelectorOpenOverviewClickHandler;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

public class HudModuleSelectorHeaderComponent
extends FrameHeaderComponent {
    private final float iconScale;
    private final IconButtonComponent collapseButton;
    private final IconButtonComponent settingsButton = new IconButtonComponent("newsettings");

    public HudModuleSelectorHeaderComponent(HudModuleSelectorFrame hudModuleSelectorFrame, float f) {
        super(hudModuleSelectorFrame);
        this.collapseButton = new IconButtonComponent("min");
        this.iconScale = f;
        this.settingsButton.addClickListener(new HudModuleSelectorOpenConfigFrameClickHandler(this));
        this.collapseButton.addClickListener(new HudModuleSelectorOpenOverviewClickHandler());
        this.addChildren(this.collapseButton, this.settingsButton);
    }

    public HudModuleSelectorHeaderComponent(HudModuleSelectorFrame hudModuleSelectorFrame) {
        this(hudModuleSelectorFrame, 1.0f);
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

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    IconButtonComponent getSettingsButton() {
        return this.settingsButton;
    }

    @Override
    public double A() {
        return this.w$src$Lgg_vape_ui_click_frame_Frame_$y4htd0().A();
    }

    @Override
    public void H() {
        float f = 8.0f * this.iconScale;
        GuiRenderPrimitives.h("legit_mode_icon", (int)(this.G$src$D$1b2f02a() + 5.0 + 8.0), (int)(this.n() + this.L() / 2.0), f, f, Color.white);
        this.collapseButton.K(this.G$src$D$1b2f02a() + this.w$src$Lgg_vape_ui_click_frame_Frame_$y4htd0().A() - 10.0 - 8.0);
        this.collapseButton.S(this.n());
        this.collapseButton.Y(this.L());
        this.settingsButton.K(this.G$src$D$1b2f02a() + this.w$src$Lgg_vape_ui_click_frame_Frame_$y4htd0().A() - 30.0 - 8.0);
        this.settingsButton.S(this.n());
        this.settingsButton.Y(this.L());
    }
}
