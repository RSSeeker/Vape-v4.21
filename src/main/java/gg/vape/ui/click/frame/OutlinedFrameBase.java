package gg.vape.ui.click.frame;

import gg.vape.ui.click.frame.Frame;
import gg.vape.utils.render.GuiRenderPrimitives;

public abstract class OutlinedFrameBase
extends Frame {
    private static boolean l6;

    public static void u(boolean bl) {
        l6 = bl;
    }


    public static boolean D$src$Z$g1pigg() {
        return l6;
    }

    public static boolean S$src$Z$g9yfcv() {
        boolean bl = OutlinedFrameBase.D$src$Z$g1pigg();
        return false;
    }

    static {
        if (!OutlinedFrameBase.D$src$Z$g1pigg()) {
            OutlinedFrameBase.u(true);
        }
    }

    @Override
    public void H() {
        super.H();
    }

    @Override
    public void c() {
        super.c();
    }

    @Override
    public void z(boolean bl) {
        GuiRenderPrimitives.P(this.G$src$D$1b2f02a() - 0.5, this.n() - 0.5, this.A() + 1.0, bl ? this.K + 2.0 : this.getComponentHeight() + 2.0, OutlinedFrameBase.J.l, 2.1f, 1.0f, 1.0f);
        super.z(bl);
    }
}

