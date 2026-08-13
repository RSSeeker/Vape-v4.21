package gg.vape.ui.click.frame;

import gg.vape.Vape;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.frame.AnchoredPopupContentActivityPanel;
import gg.vape.ui.click.frame.AnchoredPopupFrameCloseMouseListener;
import gg.vape.ui.click.frame.AnchoredPopupFrameOutsideCloseMouseListener;
import gg.vape.ui.click.frame.PopupFrame;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.wrapper.impl.Minecraft;
import java.awt.Color;
import org.jetbrains.annotations.Nullable;

public class AnchoredPopupFrame
extends PopupFrame {
    private boolean Tc = false;
    private GuiComponent Ti;
    private boolean Tl = false;
    private double TU;
    private boolean TC = true;
    private double TT = 0.0;
    @Nullable
    private Runnable Tb;
    private PanelComponent TL;
    private double Tu;

    @Override
    @Nullable
    public <T extends GuiComponent> T k(Class<T> clazz) {
        return (T)(clazz.isInstance(this.Ti) ? (GuiComponent)clazz.cast(this.Ti) : null);
    }

    @Override
    public boolean t() {
        return super.t() || this.Ti.t();
    }

    private void p() {
        int n = (int)(this.V$src$Lgg_vape_ui_click_component_GuiComponent_$jpbobc().G$src$D$1b2f02a() + this.V$src$Lgg_vape_ui_click_component_GuiComponent_$jpbobc().A() / 2.0 - this.X$src$Lgg_vape_ui_click_frame_Frame_$1aw5qf9().G$src$D$1b2f02a());
        int n2 = (int)(this.V$src$Lgg_vape_ui_click_component_GuiComponent_$jpbobc().n() + this.V$src$Lgg_vape_ui_click_component_GuiComponent_$jpbobc().L() / 2.0 - this.X$src$Lgg_vape_ui_click_frame_Frame_$1aw5qf9().n());
        int n3 = (int)(this.G$src$D$1b2f02a() + (double)n);
        int n4 = (int)(this.n() + (double)n2);
        int n5 = (int)((double)n3 + this.Ti.A());
        int n6 = (int)((double)n4 + this.Ti.L());
        double d = Vape.INSTANCE.getClientSettings().getGuiScaleFactor();
        int n7 = (int)((double)Minecraft.J() / 2.0 / d);
        int n8 = (int)((double)Minecraft.h() / 2.0 / d);
        int n9 = n7 - n5;
        int n10 = n8 - n6;
        if (n9 < 0) {
            n += n9;
        }
        if (n10 < 0) {
            n2 += n10;
        }
        String string = "offsetx " + n + ", offsety " + n2;
        this.TL.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().z(this.Ti, string);
    }

    public void m(double d) {
        this.Tc = true;
        this.TT = d;
    }

    @Nullable
    public Runnable M$src$Ljava_lang_Runnable_$1vl47b8() {
        return this.Tb;
    }

    public void z(@Nullable Runnable runnable) {
        this.Tb = runnable;
    }

    @Override
    public void c() {
        this.K(this.X$src$Lgg_vape_ui_click_frame_Frame_$1aw5qf9().G$src$D$1b2f02a());
        this.S(this.X$src$Lgg_vape_ui_click_frame_Frame_$1aw5qf9().n());
        if (this.TC) {
            this.d$src$V$5xual1();
            this.e();
            this.l$src$V$1mibm4x();
            super.c();
            this.h();
        } else {
            this.p();
            this.l$src$V$1mibm4x();
            super.c();
        }
    }

    @Override
    public boolean G(double d, double d2) {
        return super.G(d, d2) || this.Ti.t();
    }


    private void p(double d, double d2) {
        if (d > d2) {
            this.Tl = true;
            this.TU = this.V$src$Lgg_vape_ui_click_component_GuiComponent_$jpbobc().n() - 0.5;
            this.Tu = this.V$src$Lgg_vape_ui_click_component_GuiComponent_$jpbobc().n() - 0.5;
        } else {
            this.Tl = false;
        }
    }

    private int t$src$I$1dofbu2(boolean bl) {
        if (bl) {
            return (int)(this.V$src$Lgg_vape_ui_click_component_GuiComponent_$jpbobc().n() - this.Ti.L() - this.X$src$Lgg_vape_ui_click_frame_Frame_$1aw5qf9().n());
        }
        return (int)(this.TU - this.X$src$Lgg_vape_ui_click_frame_Frame_$1aw5qf9().n());
    }

    private void h() {
        double d = this.V$src$Lgg_vape_ui_click_component_GuiComponent_$jpbobc().G$src$D$1b2f02a() + this.V$src$Lgg_vape_ui_click_component_GuiComponent_$jpbobc().A() / 2.0;
        double d2 = d - 4.0;
        double d3 = this.Tu;
        double d4 = this.Tu + (double)(this.Tl ? 3 : -3);
        double d5 = d + 4.0;
        double d6 = d3;
        GuiRenderPrimitives.U(d2, d3, d, d4, d5, d6, this.Ti.getDisabledOverlayColor());
    }

    private void d$src$V$5xual1() {
        this.Tu = this.V$src$Lgg_vape_ui_click_component_GuiComponent_$jpbobc().n() + this.V$src$Lgg_vape_ui_click_component_GuiComponent_$jpbobc().L() + 4.0;
    }

    private void N$src$V$5lqtj3() {
        if (this.Tc) {
            double d = this.V$src$Lgg_vape_ui_click_component_GuiComponent_$jpbobc().n() + this.TT;
            this.TU = d - this.Ti.L();
            this.Tu = d;
            this.Tl = true;
        }
    }

    private void e() {
        this.TU = this.V$src$Lgg_vape_ui_click_component_GuiComponent_$jpbobc().n() + this.V$src$Lgg_vape_ui_click_component_GuiComponent_$jpbobc().L() + 4.0;
        double d = this.TU + this.Ti.L();
        double d2 = this.X$src$Lgg_vape_ui_click_frame_Frame_$1aw5qf9().n() + this.X$src$Lgg_vape_ui_click_frame_Frame_$1aw5qf9().L();
        this.p(d, d2);
        this.N$src$V$5lqtj3();
        boolean bl = this.Tl;
        if (this.Tc) {
            this.Tl = false;
        }
        int n = this.t$src$I$1dofbu2(this.Tl);
        this.Tl = bl;
        int n2 = (int)(this.V$src$Lgg_vape_ui_click_component_GuiComponent_$jpbobc().G$src$D$1b2f02a() + this.V$src$Lgg_vape_ui_click_component_GuiComponent_$jpbobc().A() / 2.0 - this.X$src$Lgg_vape_ui_click_frame_Frame_$1aw5qf9().G$src$D$1b2f02a() - this.Ti.A() / 2.0);
        String string = "offsetx " + n2 + ", offsety " + n;
        this.TL.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().z(this.Ti, string);
    }

    static Runnable b(AnchoredPopupFrame anchoredPopupFrame) {
        return anchoredPopupFrame.Tb;
    }

    public AnchoredPopupFrame(GuiComponent guiComponent, GuiComponent guiComponent2) {
        super(guiComponent, new AnchoredPopupContentActivityPanel(0.0, 0.0, guiComponent2));
        this.Ti = guiComponent2;
        this.TL = (PanelComponent)this.D$src$Lgg_vape_ui_click_component_GuiComponent_$srx612();
        this.TL.h(guiComponent2, new Object[0]);
        this.TL.setDisabledOverlayColor(new Color(0, 0, 0, 130));
        this.TL.setShowDisabledOverlay(true);
        this.TL.C$src$V$nadrmg();
        this.TL.setExplicitWidth(this.X$src$Lgg_vape_ui_click_frame_Frame_$1aw5qf9().A());
        this.TL.setExplicitHeight(this.X$src$Lgg_vape_ui_click_frame_Frame_$1aw5qf9().L() + (double)(this.X$src$Lgg_vape_ui_click_frame_Frame_$1aw5qf9().g$src$Z$iczr0v() ? 2 : 0));
        this.TL.addMouseListener(new AnchoredPopupFrameCloseMouseListener(this));
        this.addGlobalMouseListener(new AnchoredPopupFrameOutsideCloseMouseListener(this, guiComponent2));
    }

    public void O(boolean bl) {
        this.TC = bl;
    }
}

