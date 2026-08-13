package gg.vape.ui.click.frame;

import gg.vape.ui.click.frame.FrameComponent;
import gg.vape.ui.click.layout.WrappingFlowLayout;
import org.jetbrains.annotations.Nullable;

public class ScrollableFrameComponent
extends FrameComponent {
    private static final String eb = "wrap, widthwrap";
    @Nullable
    private Double qE;
    @Nullable
    private Double qb;
    private final WrappingFlowLayout qP = new WrappingFlowLayout(this);

    @Nullable
    public Double d$src$Ljava_lang_Double_$1rarrr3() {
        return this.qE;
    }

    @Override
    public void V() {
    }


    public ScrollableFrameComponent v(@Nullable Double d) {
        this.qE = d;
        return this;
    }

    @Nullable
    public Double o$src$Ljava_lang_Double_$1bov8d0() {
        return this.qb;
    }

    @Override
    public double x() {
        double d = this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().C();
        Double d2 = this.qE;
        if (d2 != null) {
            return Math.min(this.u$src$D$ba5a9m(), Math.max(d2, d));
        }
        return Math.min(this.u$src$D$ba5a9m(), d);
    }

    public ScrollableFrameComponent h(@Nullable Double d) {
        this.qb = d;
        return this;
    }

    public ScrollableFrameComponent(double d) {
        this.qP.o(d);
        this.N(this.qP);
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M(eb);
    }

    public double u$src$D$ba5a9m() {
        return this.qP == null ? 0.0 : this.qP.B();
    }

    public ScrollableFrameComponent(double d, double d2) {
        this(d);
        this.qE = d;
        this.qb = d2;
    }

    private double R$src$D$aqwhhz() {
        double d = this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().y();
        Double d2 = this.qb;
        if (d2 != null) {
            return Math.max(d2, d);
        }
        return d;
    }

    @Override
    public double C() {
        double d = this.R$src$D$aqwhhz();
        return this.NS ? Math.min(d, this.d$src$D$ibccpu()) : d;
    }
}

