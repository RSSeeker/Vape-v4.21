package gg.vape.ui.click.frame;

import func.skidline.RectData;
import gg.vape.ui.click.frame.Frame;
import gg.vape.utils.render.GuiRenderPrimitives;

public abstract class InsetFrameBase
extends Frame {
    @Override
    public void M() {
        this.No = new RectData(this.No.o() + 5.0, this.No.W(), this.No.e(), this.No.R());
        super.M();
    }

    @Override
    public void z(boolean bl) {
        GuiRenderPrimitives.d(this.G$src$D$1b2f02a() - 5.0, this.n(), this.A() + 10.0, bl ? this.K + 2.0 : this.L() + 2.0, this.getDisabledOverlayColor());
    }

    @Override
    public RectData getBounds() {
        if (this.NS) {
            return new RectData(this.G$src$D$1b2f02a() - 5.0, this.n(), this.A() + 10.0, this.d$src$D$ibccpu());
        }
        return new RectData(this.G$src$D$1b2f02a() - 5.0, this.n(), this.A() + 10.0, this.L());
    }

    @Override
    public void o(double d) {
        super.o(d - 10.0);
    }


    @Override
    public void K(double d) {
        super.K(d + 5.0);
    }
}

