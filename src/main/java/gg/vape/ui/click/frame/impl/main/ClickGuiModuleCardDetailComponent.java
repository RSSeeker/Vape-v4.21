package gg.vape.ui.click.frame.impl.main;

import gg.vape.Vape;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.frame.impl.main.ClickGuiModuleCardRenderState;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.StringUtils;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;
import java.util.Collections;
import java.util.List;

public class ClickGuiModuleCardDetailComponent
extends GuiComponent {
    private static final double o = 3.0;
    private Color Q;
    private static final double v = 1.0;
    private double O = 50.0;
    private float a;
    private List<ClickGuiModuleCardRenderState> i = Collections.emptyList();
    private static final double b = 2.5;
    private double K = 0.7;
    private static final String R = "...";

    public void O$src$V$wbyi9r(double d) {
        this.K = d;
    }

    @Override
    public double C() {
        return 0.0;
    }

    @Override
    public void H() {
        if (this.i.isEmpty()) {
            return;
        }
        SmoothFontRenderer smoothFontRenderer = this.getFontRenderer(this.K);
        double d = smoothFontRenderer.N("...");
        double d2 = smoothFontRenderer.d("A");
        double d3 = this.G$src$D$1b2f02a();
        double d4 = this.n();
        double d5 = this.L();
        double d6 = d4 + d5 / 2.0 - d2 / 2.0;
        double d7 = d3;
        double d8 = this.O;
        for (int i = 0; i < this.i.size(); ++i) {
            String string;
            String string2;
            double d9;
            ClickGuiModuleCardRenderState clickGuiModuleCardRenderState = this.i.get(i);
            if (clickGuiModuleCardRenderState.R()) {
                double d10 = 5.5;
                if (d10 <= d8) {
                    d9 = d7 + 1.5;
                    double d11 = d4 + d5 / 2.0 - 1.25;
                    Color color = this.m(clickGuiModuleCardRenderState.u());
                    GuiRenderPrimitives.V(d9, d11, 2.5, 1.0 / Vape.INSTANCE.getClientSettings().getGuiScaleFactor(), color);
                    d7 += d10;
                    d8 -= d10;
                    continue;
                }
                if (d <= d8) {
                    smoothFontRenderer.d("...", d7, d6, this.Q);
                }
                return;
            }
            if (!clickGuiModuleCardRenderState.n$src$Z$1c2q0zn() || (string2 = clickGuiModuleCardRenderState.n()).isEmpty() || (string = StringUtils.l(string2)).isEmpty()) continue;
            string2 = string;
            d9 = smoothFontRenderer.N(string2);
            if (d9 <= d8) {
                smoothFontRenderer.d(string2, d7, d6, this.Q);
                d7 += d9;
                d8 -= d9;
                continue;
            }
            String string3 = ClickGuiModuleCardDetailComponent.z(smoothFontRenderer, string2, d8, d);
            if (string3 != null) {
                smoothFontRenderer.d(string3 + "...", d7, d6, this.Q);
            } else if (d <= d8) {
                smoothFontRenderer.d("...", d7, d6, this.Q);
            }
            return;
        }
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    private static String z(SmoothFontRenderer smoothFontRenderer, String string, double d, double d2) {
        if (d2 > d) {
            return null;
        }
        double d3 = d - d2;
        int n = 0;
        int n2 = string.length();
        int n3 = -1;
        while (n <= n2) {
            double d4;
            int n4 = n + n2 >>> 1;
            double d5 = d4 = n4 == 0 ? 0.0 : smoothFontRenderer.N(string.substring(0, n4));
            if (d4 <= d3) {
                n3 = n4;
                n = n4 + 1;
                continue;
            }
            n2 = n4 - 1;
        }
        if (n3 <= 0) {
            return null;
        }
        return string.substring(0, n3);
    }

    public ClickGuiModuleCardDetailComponent() {
        this.Q = ClickGuiModuleCardDetailComponent.J.A;
        this.a = 1.0f;
    }

    @Override
    public double x() {
        return 0.0;
    }

    public boolean a$src$Z$86w47b() {
        if (this.i.isEmpty()) {
            return true;
        }
        for (ClickGuiModuleCardRenderState clickGuiModuleCardRenderState : this.i) {
            if (clickGuiModuleCardRenderState.R()) {
                return false;
            }
            if (!clickGuiModuleCardRenderState.n$src$Z$1c2q0zn() || clickGuiModuleCardRenderState.n().isEmpty()) continue;
            return false;
        }
        return true;
    }

    public void Y(List<ClickGuiModuleCardRenderState> list) {
        this.i = list != null ? list : Collections.emptyList();
    }


    public void n(Color color) {
        this.Q = color;
    }

    @Override
    public void F() {
    }

    public void J(double d) {
        this.O = d;
    }

    @Override
    public void I() {
    }

    @Override
    public void u() {
    }

    private Color m(Color color) {
        if (this.a >= 1.0f) {
            return color;
        }
        int n = Math.max(0, Math.round((float)color.getAlpha() * this.a));
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), n);
    }

    public List<ClickGuiModuleCardRenderState> l$src$Ljava_util_List_$1f3xl5l() {
        return this.i;
    }

    public String I$src$Ljava_lang_String_$q2yz37() {
        StringBuilder stringBuilder = new StringBuilder();
        for (ClickGuiModuleCardRenderState clickGuiModuleCardRenderState : this.i) {
            if (clickGuiModuleCardRenderState.n$src$Z$1c2q0zn()) {
                stringBuilder.append(clickGuiModuleCardRenderState.n());
                continue;
            }
            if (!clickGuiModuleCardRenderState.R()) continue;
            stringBuilder.append("\u25cf");
        }
        return stringBuilder.toString();
    }

    public void K(float f) {
        this.a = f;
    }
}

