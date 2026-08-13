package gg.vape.friend.ui;

import gg.vape.friend.ui.OnlineCombatStatsSettingsFrame;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.value.SliderComponentBase;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

public class OnlineCombatStatComparisonComponent
extends SliderComponentBase {
    private final OnlineCombatStatsSettingsFrame statsFrame;
    private int targetCount;
    private int localCount;

    private void renderComparison() {
        Color color;
        double d;
        double d2;
        String string;
        SmoothFontRenderer smoothFontRenderer;
        double d3;
        String string2;
        StringBuilder stringBuilder;
        SmoothFontRenderer smoothFontRenderer2 = this.getFontRenderer(0.85);
        int n = this.targetCount - this.localCount;
        int n2 = Math.abs(n);
        boolean bl = n < 0;
        boolean bl2 = n > 0;
        boolean bl3 = this.statsFrame.isManagedByClickGui();
        Color color2 = bl3 ? this.statsFrame.applyDefaultEditorAlpha(OnlineCombatStatComparisonComponent.J.Z) : OnlineCombatStatComparisonComponent.J.Z;
        Color color3 = bl3 ? this.statsFrame.applyDefaultEditorAlpha(OnlineCombatStatComparisonComponent.J.h) : OnlineCombatStatComparisonComponent.J.h;
        Color color4 = bl3 ? this.statsFrame.applyDefaultEditorAlpha(OnlineCombatStatComparisonComponent.J.l) : OnlineCombatStatComparisonComponent.J.l;
        Color color5 = bl3 ? this.statsFrame.applyDefaultEditorAlpha(OnlineCombatStatComparisonComponent.J.B) : OnlineCombatStatComparisonComponent.J.B;
        Color color6 = bl3 ? this.statsFrame.applyDefaultEditorAlpha(OnlineCombatStatComparisonComponent.J.d) : OnlineCombatStatComparisonComponent.J.d;
        StringBuilder stringBuilder2 = new StringBuilder();
        if (bl2) {
            stringBuilder = stringBuilder2;
            string2 = "+";
        } else {
            StringBuilder stringBuilder3 = stringBuilder2;
            if (bl) {
                stringBuilder = stringBuilder3;
                string2 = "-";
            } else {
                stringBuilder = stringBuilder3;
                string2 = "";
            }
        }
        String string3 = stringBuilder.append(string2).append(n2).toString();
        double d4 = smoothFontRenderer2.N(string3) + 5.0;
        if (d4 < 10.0) {
            d4 = 10.0;
        }
        GuiRenderPrimitives.d(this.G$src$D$1b2f02a() + 5.0, this.n() + 5.0, d4, 10.0, color4);
        double d5 = d3 = this.n() + 5.0 + 2.0;
        double d6 = this.G$src$D$1b2f02a() + 5.0 + d4 / 2.0;
        String string4 = string3;
        SmoothFontRenderer smoothFontRenderer3 = smoothFontRenderer2;
        if (bl2) {
            smoothFontRenderer = smoothFontRenderer3;
            string = string4;
            d2 = d6;
            d = d5;
            color = color5;
        } else {
            double d7 = d5;
            double d8 = d6;
            String string5 = string4;
            SmoothFontRenderer smoothFontRenderer4 = smoothFontRenderer3;
            if (bl) {
                smoothFontRenderer = smoothFontRenderer4;
                string = string5;
                d2 = d8;
                d = d7;
                color = color6;
            } else {
                smoothFontRenderer = smoothFontRenderer4;
                string = string5;
                d2 = d8;
                d = d7;
                color = color2;
            }
        }
        smoothFontRenderer.W(string, d2, d, color);
        smoothFontRenderer2.d(this.getLabel(), this.G$src$D$1b2f02a() + 10.0 + d4, d3, color2);
        String string6 = this.targetCount > 9 ? "" + this.targetCount : "0" + this.targetCount;
        String string7 = this.localCount > 9 ? "" + this.localCount : "0" + this.localCount;
        double d9 = smoothFontRenderer2.N(string6);
        double d10 = smoothFontRenderer2.N(string6);
        double d11 = smoothFontRenderer2.N("/");
        double d12 = this.G$src$D$1b2f02a() + this.A() - 5.0 - d10;
        smoothFontRenderer2.d(string7, d12, d3, color3);
        smoothFontRenderer2.d("/", d12 - 5.0 - d11, d3, color3);
        smoothFontRenderer2.d(string6, d12 - 10.0 - d11 - d9, d3, color2);
        double d13 = this.n() + 22.5;
        double d14 = 6.0;
        GuiRenderPrimitives.C(this.G$src$D$1b2f02a() + 5.0, d13 - 0.5, this.A() - 10.0, 2.0, color4);
        int n3 = this.targetCount + this.localCount;
        if (n3 == 0) {
            GuiRenderPrimitives.F("greenglowsquare", this.G$src$D$1b2f02a() + this.A() / 2.0, d13, 20.0, 20.0, Color.WHITE);
            return;
        }
        double d15 = (double)n2 / (double)n3;
        double d16 = this.A() / 2.0 - 5.0 - d14;
        double d17 = this.targetCount >= this.localCount ? this.G$src$D$1b2f02a() + 5.0 + d16 - d16 * d15 : this.G$src$D$1b2f02a() + 5.0 + d16 + d14 * 2.0 + d16 * d15;
        if (this.targetCount >= this.localCount) {
            GuiRenderPrimitives.C(d17, d13 - 0.5, this.G$src$D$1b2f02a() + this.A() / 2.0 - d17, 2.0, color5);
        } else {
            GuiRenderPrimitives.C(this.G$src$D$1b2f02a() + this.A() / 2.0, d13 - 0.5, d17 - this.G$src$D$1b2f02a() - this.A() / 2.0, 2.0, color6);
        }
        GuiRenderPrimitives.F(this.targetCount >= this.localCount ? "greenglowsquare" : "redglowsquare", this.G$src$D$1b2f02a() + this.A() / 2.0, d13, 20.0, 20.0, Color.WHITE);
        GuiRenderPrimitives.F(this.targetCount >= this.localCount ? "greenglowsquare" : "redglowsquare", d17, d13, 20.0, 20.0, Color.WHITE);
    }


    public OnlineCombatStatComparisonComponent(String string, OnlineCombatStatsSettingsFrame onlineCombatStatsSettingsFrame) {
        super(string);
        this.statsFrame = onlineCombatStatsSettingsFrame;
    }

    @Override
    public double C() {
        return 30.0;
    }

    public void setLocalCount(int count) {
        this.localCount = count;
    }

    @Override
    public void u() {
    }

    @Override
    public double x() {
        return 20.0;
    }

    @Override
    public void I() {
        this.renderComparison();
    }

    @Override
    public void H() {
        this.renderComparison();
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    @Override
    public void F() {
    }

    public void setTargetCount(int count) {
        this.targetCount = count;
    }
}

