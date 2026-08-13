package gg.vape.ui.click.frame.impl;

import gg.vape.Vape;
import gg.vape.module.Macro;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.AnimatedIconButtonComponent;
import gg.vape.ui.click.component.FadingTruncatedTextComponent;
import gg.vape.ui.click.component.IconButtonComponent;
import gg.vape.ui.click.component.gui.InteractiveComponent;
import gg.vape.ui.click.component.gui.TextButton;
import gg.vape.ui.click.component.input.BindableInputComponent;
import gg.vape.ui.click.component.value.BooleanToggleComponent;
import gg.vape.ui.click.component.value.RandomRangeSliderComponent;
import gg.vape.ui.click.frame.impl.FrameMacros;
import gg.vape.ui.click.frame.impl.FrameMacrosEditorRefreshFrameMouseListener;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

public class FrameMacrosEditor
extends InteractiveComponent {
    private RandomRangeSliderComponent Q;
    private Macro q1;
    private final TextButton q5;
    private BooleanToggleComponent qz;
    private final AnimatedIconButtonComponent K;
    private RandomRangeSliderComponent qR;
    private Macro q3;
    private BindableInputComponent qr;
    private final IconButtonComponent v;
    private FrameMacros qQ;
    private FadingTruncatedTextComponent I;
    private final TextButton b;
    private boolean qn;

    @Override
    public void H() {
        double d;
        SmoothFontRenderer smoothFontRenderer = this.getFontRenderer(0.9);
        double d2 = smoothFontRenderer.d(this.q1.getName());
        double d3 = this.n() + 9.0 - d2 / 2.0;
        double d4 = this.n() + 9.0;
        double d5 = this.G$src$D$1b2f02a() + 10.0;
        this.I.K(d5);
        this.I.S(d3);
        this.I.setMaxWidth(this.qn ? this.A() - 20.0 - 8.0 : 65.0 - this.qr.A());
        if (this.qn) {
            GuiRenderPrimitives.d(this.G$src$D$1b2f02a() + 5.0 - 0.5, this.n() + 0.5, this.A() - 10.0 + 1.0, this.L() - 1.0, FrameMacrosEditor.J.l);
        }
        GuiRenderPrimitives.d(this.G$src$D$1b2f02a() + 5.0, this.n() + 1.0, this.A() - 10.0, this.L() - 2.0, FrameMacrosEditor.J.m);
        if (this.qn) {
            d = this.G$src$D$1b2f02a() + 5.0;
            double d6 = this.n() + 20.0;
            double d7 = this.A() - 10.0;
            this.qR.K(d);
            this.qR.S(d6);
            this.qR.o(d7);
            this.qz.K(d);
            this.qz.S(d6 += this.qR.L());
            this.qz.o(d7 - 6.0);
            d6 += this.qz.L();
            if (this.qz.getBooleanValue().getEffectiveValue().booleanValue()) {
                this.Q.setVisible(true);
                this.Q.K(d);
                this.Q.S(d6);
                this.Q.o(d7);
                d6 += this.Q.L();
            } else {
                this.Q.setVisible(false);
                d6 += 5.0;
            }
            double d8 = (this.A() - 25.0) / 5.0;
            double d9 = this.G$src$D$1b2f02a() + 10.0;
            if (this.K != null) {
                this.K.K(d9);
                this.K.S(d6);
                this.K.Y(18.0);
                this.K.o(d8);
            }
            this.q5.K(d9 += d8 + 2.5);
            this.q5.S(d6);
            this.q5.Y(18.0);
            this.q5.o(d8 * 2.0);
            this.b.K(d9 += d8 * 2.0 + 2.5);
            this.b.S(d6);
            this.b.Y(18.0);
            this.b.o(d8 * 2.0);
        }
        d = this.G$src$D$1b2f02a() + this.A() - 15.0;
        if (this.v.V$src$Z$1xhop3l()) {
            this.v.setOverrideColor(this.qn ? FrameMacrosEditor.J.f : null);
            this.v.K(d);
            this.v.S(this.n());
            this.v.Y(18.0);
            d -= 5.0;
        }
        if (!this.qn) {
            this.qr.getBindLabel().setMaxWidth(20.0);
            this.qr.K(d -= this.qr.A());
            this.qr.S(this.n() + 4.0);
            GuiRenderPrimitives.F("newclock", d -= 9.0, d4, 8.0, 8.0, FrameMacrosEditor.J.K);
        }
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
        if (this.qQ.l$src$Lgg_vape_ui_click_frame_impl_FrameMacrosEditor_$1712c36() != null && this.qQ.l$src$Lgg_vape_ui_click_frame_impl_FrameMacrosEditor_$1712c36().equals(this) && !this.getBounds().J(guiMouseEvent.getX(), guiMouseEvent.getY())) {
            if (!this.v.V$src$Z$1xhop3l()) {
                this.qQ.X(null);
                return;
            }
            this.v.dispatchPrimaryClick();
            return;
        }
        super.g(guiMouseEvent);
    }

    @Override
    public void I() {
    }


    public Macro z() {
        return this.q1;
    }

    public FrameMacrosEditor(FrameMacros frameMacros, Macro macro) {
        this.b = new TextButton("Update", 0.8, FrameMacrosEditor.J.B).setTransparentBackgroundBorder(FrameMacrosEditor.J.l);
        this.q5 = new TextButton("Cancel", 0.8, FrameMacrosEditor.J.B).setTransparentBackgroundBorder(FrameMacrosEditor.J.l);
        this.K = new AnimatedIconButtonComponent("newtrash", 0.8, FrameMacrosEditor.J.d);
        this.v = new IconButtonComponent("settingdots");
        this.b.setDeriveTextColorFromBackground(false);
        this.q5.setDeriveTextColorFromBackground(false);
        this.qQ = frameMacros;
        this.q1 = macro;
        this.qr = new BindableInputComponent(macro);
        FrameMacrosEditor frameMacrosEditor = this;
        this.v.addClickListener(() -> {
            this.qn = !this.qn;
            if (this.qn) {
                frameMacros.X(frameMacrosEditor);
                this.q3 = Macro.create(macro.getName()).loadJson(macro.toJson());
                this.qz.setVisible(true);
                this.qR.setVisible(true);
                this.Q.setVisible(true);
                this.q5.setVisible(true);
                this.b.setVisible(true);
                this.K.setVisible(true);
                this.qr.setVisible(false);
            } else {
                frameMacros.X(null);
                this.qz.setVisible(false);
                this.qR.setVisible(false);
                this.Q.setVisible(false);
                this.q5.setVisible(false);
                this.b.setVisible(false);
                this.K.setVisible(false);
                this.qr.setVisible(true);
            }
            frameMacros.l$src$V$1mibm4x();
        });
        this.qR = new RandomRangeSliderComponent(macro.getDelay());
        this.Q = new RandomRangeSliderComponent(macro.getDoubleClickDelay());
        this.qz = new BooleanToggleComponent(macro.getDoubleClick());
        this.qz.addMouseListener(new FrameMacrosEditorRefreshFrameMouseListener(this, frameMacros));
        this.K.addClickListener(() -> {
            Vape.INSTANCE.getMacrosManager().removeMacro(macro);
            frameMacros.X(null);
        });
        this.q5.addClickListener(() -> {
            if (this.q3 != null) {
                macro.loadJson(this.q3.toJson());
            }
            this.v.dispatchPrimaryClick();
        });
        this.b.addClickListener(() -> {
            if (!this.v.V$src$Z$1xhop3l()) {
                frameMacros.X(null);
            } else {
                this.v.dispatchPrimaryClick();
            }
        });
        this.qz.setVisible(false);
        this.qz.setUseExplicitWidth(true);
        this.qz.setDisabledOverlayColor(FrameMacrosEditor.J.m);
        this.qR.setVisible(false);
        this.qR.setUseExplicitWidth(true);
        this.qR.setDisabledOverlayColor(FrameMacrosEditor.J.m);
        this.Q.setVisible(false);
        this.Q.setUseExplicitWidth(true);
        this.Q.setDisabledOverlayColor(FrameMacrosEditor.J.m);
        this.q5.setVisible(false);
        this.b.setVisible(false);
        this.K.setVisible(false);
        this.K.setDisabledOverlayColor(FrameMacrosEditor.J.m);
        this.I = new FadingTruncatedTextComponent(macro.getName(), 40.0, 0.9, FrameMacrosEditor.J.Z, FrameMacrosEditor.J.m, false, false);
        this.addChildren(this.I, this.v, this.qr, this.qz, this.qR, this.Q, this.q5, this.b, this.K);
    }

    @Override
    public double C() {
        return this.qn ? (this.qz.getBooleanValue().getEffectiveValue().booleanValue() ? 120.0 : 90.0) : 18.0;
    }

    private void lambda$setInitialMode$4() {
        this.setVisible(false);
        this.qQ.X(null);
    }

    private void lambda$setInitialMode$5() {
        this.q1 = Macro.create(this.q1.getName()).loadJson(this.q1.toJson());
        Vape.INSTANCE.getMacrosManager().addMacro(this.q1);
        this.setVisible(false);
        this.qQ.X(null);
    }

    @Override
    public double x() {
        return 110.0;
    }

    @Override
    public void F() {
    }

    @Override
    public void u() {
    }

    public void N$src$V$13y6z98() {
        this.v.dispatchPrimaryClick();
        this.v.setVisible(false);
        this.qr.setVisible(false);
        this.K.setVisible(false);
        this.q5.setLabelText("Cancel");
        this.q5.setBackgroundAnimationColors(new Color(0, 0, 0, 0), FrameMacrosEditor.J.d);
        this.b.setLabelText("Add");
        this.b.setBackgroundAnimationColors(new Color(0, 0, 0, 0), FrameMacrosEditor.J.B);
        this.q5.setClickListener(this::lambda$setInitialMode$4);
        this.b.setClickListener(this::lambda$setInitialMode$5);
    }
}
