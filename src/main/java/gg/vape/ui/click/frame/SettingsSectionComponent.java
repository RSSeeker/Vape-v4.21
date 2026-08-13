package gg.vape.ui.click.frame;

import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.gui.InteractiveComponent;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ImageRenderer;
import java.awt.Color;

public class SettingsSectionComponent
extends InteractiveComponent {
    private Color b;
    private float Ah = 4.0f;
    private String I;
    private final GuiComponent[] Q;
    private static final String cb = "expandarrow";
    private double K;
    private boolean v;

    @Override
    public void I() {
    }

    @Override
    public void F() {
        this.v = true;
    }

    @Override
    public void u() {
        if (this.v && !this.w$src$Z$e457mb()) {
            this.v = false;
        }
    }

    @Override
    public void H() {
        float f = 6.88f;
        SmoothFontRenderer smoothFontRenderer = this.getFontRenderer(this.K);
        double d = smoothFontRenderer.d(this.I);
        double d2 = this.n() + this.L() / 2.0 - d / 2.0;
        double d3 = this.n() + this.L() / 2.0 - (double)(f / 2.0f);
        double d4 = this.n() + this.L() / 2.0 - (double)(this.Ah / 2.0f);
        Color color = SettingsSectionComponent.J.t;
        Color color2 = SettingsSectionComponent.J.Z;
        if (this.v) {
            color = SettingsSectionComponent.J.m;
            color2 = SettingsSectionComponent.J.A;
        }
        GuiRenderPrimitives.C(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), color);
        ImageRenderer.drawImage(this.b, (float)this.G$src$D$1b2f02a() + (float)this.A() - 5.0f - 5.0f, (float)d4, cb, this.Ah, this.Ah, false);
        smoothFontRenderer.d(this.I, this.G$src$D$1b2f02a() + 5.0, d2, color2);
    }

    public SettingsSectionComponent(String string, double d, GuiComponent ... guiComponentArray) {
        this.b = SettingsSectionComponent.J.W;
        this.I = string;
        this.K = d;
        this.Q = guiComponentArray;
    }

    @Override
    public double x() {
        return 110.0;
    }

    public String A$src$Ljava_lang_String_$9tmd4u() {
        return this.I;
    }

    public GuiComponent[] n$src$ALgg_vape_ui_click_component_GuiComponent_$ay9kg5() {
        return this.Q;
    }


    @Override
    public double C() {
        return 20.0;
    }
}

