package gg.vape.ui.click.frame.impl;

import gg.vape.event.EventBus;
import gg.vape.event.EventHandler;
import gg.vape.event.EventListener;
import gg.vape.event.impl.ProfileChangeEvent;
import gg.vape.module.Macro;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.frame.CloseableFrameHeaderComponent;
import gg.vape.ui.click.frame.CollapsibleFrame;
import gg.vape.ui.click.frame.FrameHeaderComponent;
import gg.vape.ui.click.frame.OutlinedFrameBase;
import gg.vape.ui.click.frame.impl.FrameMacrosAddMacroInputComponent;
import gg.vape.ui.click.frame.impl.FrameMacrosEditor;
import gg.vape.unmap.ColorUtil;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.util.function.Predicate;

public class FrameMacros
extends OutlinedFrameBase
implements CollapsibleFrame,
EventListener {
    public static FrameMacros jo;
    private boolean jm;
    private FrameMacrosEditor jg;

    @Override
    public void dispatchMouseEvent(GuiMouseEvent guiMouseEvent) {
        if (this.jg != null) {
            this.jg.dispatchMouseEvent(guiMouseEvent);
            return;
        }
        super.dispatchMouseEvent(guiMouseEvent);
    }

    private void o$src$V$ezev80() {
        for (GuiComponent guiComponent : this.f()) {
            if (guiComponent instanceof FrameHeaderComponent) continue;
            guiComponent.setVisible(this.jm);
        }
        this.l$src$V$1mibm4x();
    }


    public FrameMacros() {
        this.setDisabledOverlayColor(FrameMacros.J.i);
        this.K(300.0);
        this.S(100.0);
        this.jm = true;
        this.setVisible(false);
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M(false);
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        this.Y(new CloseableFrameHeaderComponent(this, "newmacros", "Macros"));
        this.addChildren(new FrameMacrosAddMacroInputComponent(this));
        jo = this;
        EventBus.getInstance().registerListener(this, new Predicate[0]);
    }

    @EventHandler
    public void U(ProfileChangeEvent profileChangeEvent) {
        this.jg = null;
    }

    public FrameMacrosEditor l$src$Lgg_vape_ui_click_frame_impl_FrameMacrosEditor_$1712c36() {
        return this.jg;
    }

    @Override
    public void w() {
        this.jm = !this.jm;
        this.o$src$V$ezev80();
    }

    public void v(Macro macro) {
        this.addChildren(new FrameMacrosEditor(this, macro));
    }

    @Override
    public void J() {
        if (this.jg != null) {
            if (this.jg.t()) {
                this.jg.J();
            }
            return;
        }
        super.J();
    }

    @Override
    public void c() {
        super.c();
        if (this.jg != null) {
            double d = this.jg.n() + this.jg.L() - (this.n() + this.d$src$D$ibccpu());
            if (d > 0.0) {
                this.b(this.J$src$D$hx1pag() - d);
            }
            GuiRenderPrimitives.C(this.G$src$D$1b2f02a(), this.n(), this.A(), Math.min(this.L(), this.d$src$D$ibccpu()), ColorUtil.withAlpha(this.getDisabledOverlayColor(), 100));
            this.jg.c();
        }
    }

    @Override
    public void v() {
    }

    @Override
    public void Y() {
    }

    public void X(FrameMacrosEditor frameMacrosEditor) {
        this.jg = frameMacrosEditor;
    }

    public void Z(Macro macro) {
        for (GuiComponent guiComponent : this.f()) {
            if (!(guiComponent instanceof FrameMacrosEditor) || !((FrameMacrosEditor)guiComponent).z().getName().equals(macro.getName())) continue;
            this.removeChild(guiComponent);
        }
    }

    @Override
    public String getName() {
        return "Macros";
    }

    @Override
    public boolean q() {
        return this.jm;
    }
}

