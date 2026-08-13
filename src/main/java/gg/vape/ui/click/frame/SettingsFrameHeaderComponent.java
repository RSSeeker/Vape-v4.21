package gg.vape.ui.click.frame;

import gg.vape.friend.ui.OnlineCombatStatsSettingsFrame;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.IconButtonComponent;
import gg.vape.ui.click.frame.CollapsibleFrame;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.FrameHeaderComponent;
import gg.vape.ui.click.frame.SettingsFrameHeaderCollapseClickListener;
import gg.vape.ui.click.frame.SettingsFrameHeaderToggleClickListener;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.ImageRenderer;
import java.awt.Color;

public class SettingsFrameHeaderComponent
extends FrameHeaderComponent {
    private String i;
    private IconButtonComponent I;
    private String R;
    private IconButtonComponent Q = new IconButtonComponent("settingdots");

    @Override
    public void u() {
    }

    @Override
    public void F() {
    }

    public IconButtonComponent x$src$Lgg_vape_ui_click_component_IconButtonComponent_$x1h5th() {
        return this.Q;
    }

    private void A$src$V$18zygv7() {
        SmoothFontRenderer smoothFontRenderer = this.getFontRenderer(0.9);
        Color color = SettingsFrameHeaderComponent.J.A;
        double d = smoothFontRenderer.d(this.i);
        float f = (float)ImageRenderer.getImageWidth(this.R) / 3.5f;
        float f2 = (float)ImageRenderer.getImageHeight(this.R) / 3.5f;
        double d2 = this.n() + this.L() / 2.0 - d / 2.0 + 1.0;
        double d3 = this.n() + this.L() / 2.0 - (double)(f2 / 2.0f) + 1.0;
        double d4 = this.G$src$D$1b2f02a() + 6.0;
        this.I.setImageDivisor(3.5);
        this.Q.setImageDivisor(3.2);
        smoothFontRenderer.d(this.i, d4 + (double)f + 4.0, d2, color);
        ImageRenderer.drawImage(color, (float)d4, (float)d3, this.R, f, f2, false);
        this.I.setOverrideColor(this.w$src$Lgg_vape_ui_click_frame_Frame_$y4htd0().y$src$Z$1f55jvh() ? SettingsFrameHeaderComponent.J.f : SettingsFrameHeaderComponent.J.W.darker());
        this.I.K(this.G$src$D$1b2f02a() + this.A() - 20.0);
        this.I.S(this.n() + 1.0);
        this.I.Y(this.L());
        this.I.o(10.0);
        this.Q.setOverrideColor(this.w$src$Lgg_vape_ui_click_frame_Frame_$y4htd0() instanceof CollapsibleFrame && !((CollapsibleFrame)((Object)this.w$src$Lgg_vape_ui_click_frame_Frame_$y4htd0())).q() ? SettingsFrameHeaderComponent.J.f : null);
        this.Q.K(this.G$src$D$1b2f02a() + this.A() - 12.0);
        this.Q.S(this.n() + 1.0);
        this.Q.Y(this.L());
    }

    @Override
    public void H() {
        this.A$src$V$18zygv7();
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    public SettingsFrameHeaderComponent(Frame frame, String string, String string2) {
        super(frame);
        this.I = new IconButtonComponent("newpin");
        this.R = string;
        this.i = string2;
        this.I.addClickListener(new SettingsFrameHeaderToggleClickListener(this));
        this.Q.addClickListener(new SettingsFrameHeaderCollapseClickListener(this, frame));
        this.addChildren(this.Q, this.I);
        this.I.setImageDivisor(3.5);
    }

    @Override
    public void I() {
        if (this.w$src$Lgg_vape_ui_click_frame_Frame_$y4htd0() instanceof CollapsibleFrame && !((CollapsibleFrame)((Object)this.w$src$Lgg_vape_ui_click_frame_Frame_$y4htd0())).q() && !(this.w$src$Lgg_vape_ui_click_frame_Frame_$y4htd0() instanceof OnlineCombatStatsSettingsFrame)) {
            ((CollapsibleFrame)((Object)this.w$src$Lgg_vape_ui_click_frame_Frame_$y4htd0())).w();
        }
    }

}

