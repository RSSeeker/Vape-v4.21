package gg.vape.ui.click.layout;

import func.skidline.RectData;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.layout.ComponentLayout;
import gg.vape.ui.click.layout.LayoutConstraints;
import java.util.ArrayList;
import java.util.List;

public class WrappingFlowLayout
extends ComponentLayout {
    private double I;
    private double r;

    @Override
    public void p() {
        this.i();
        double d = this.O().G$src$D$1b2f02a();
        double d2 = this.O().n();
        ArrayList<Double> arrayList = new ArrayList<Double>();
        double d3 = 0.0;
        double d4 = 0.0;
        List<GuiComponent> list = this.c();
        for (int i = 0; i < list.size(); ++i) {
            GuiComponent guiComponent = list.get(i);
            if (!guiComponent.V$src$Z$1xhop3l()) continue;
            LayoutConstraints layoutConstraints = this.Q().get(guiComponent);
            if (layoutConstraints.G$src$Z$1xi35hg()) {
                d = this.O().G$src$D$1b2f02a() + this.r - guiComponent.A();
            }
            guiComponent.K(d);
            guiComponent.S(d2);
            if (!arrayList.contains(d2)) {
                arrayList.add(d2);
            }
            if (!this.Q().containsKey(guiComponent)) {
                d += guiComponent.A();
                continue;
            }
            boolean bl = layoutConstraints.V();
            if (!layoutConstraints.h() && i < list.size() - 1) {
                GuiComponent guiComponent2 = list.get(i + 1);
                double d5 = d - this.O().G$src$D$1b2f02a();
                if (d5 + guiComponent.A() + guiComponent2.A() < this.I) {
                    bl = false;
                }
            }
            if (guiComponent.t$src$D$1x9zexg() > d3) {
                d3 = guiComponent.t$src$D$1x9zexg();
            }
            if (bl) {
                double d6 = d - this.O().G$src$D$1b2f02a();
                if (d6 > d4) {
                    d4 = d6;
                }
                d2 += d3;
                d = this.O().G$src$D$1b2f02a();
                d3 = 0.0;
                continue;
            }
            d += guiComponent.A();
        }
        for (GuiComponent guiComponent : this.c()) {
            this.m().put(guiComponent, new RectData(guiComponent.G$src$D$1b2f02a(), guiComponent.n(), guiComponent.A(), guiComponent.t$src$D$1x9zexg()));
            if (!guiComponent.V$src$Z$1xhop3l()) continue;
            double d7 = this.O().G$src$D$1b2f02a();
            double d8 = this.O().n();
            double d9 = guiComponent.G$src$D$1b2f02a() + guiComponent.A() - d7;
            double d10 = guiComponent.n() + guiComponent.t$src$D$1x9zexg() - d8;
            if (d9 > this.C()) {
                this.r(d9);
            }
            if (d10 > this.y()) {
                this.B(d10);
            }
            guiComponent.i$src$V$c9opdk();
        }
        this.r = this.O().A();
        if (this.C$src$Z$1jglhej()) {
            this.T();
        }
    }

    public void o(double d) {
        this.I = d;
    }

    public WrappingFlowLayout(GuiComponent guiComponent) {
        super(guiComponent);
        this.I = guiComponent.A();
    }

    public double B() {
        return this.I;
    }

}

