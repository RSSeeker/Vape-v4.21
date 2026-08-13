package gg.vape.ui.click.layout;

import func.skidline.RectData;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.layout.ComponentLayout;
import gg.vape.ui.click.layout.LayoutConstraints;
import java.util.ArrayList;
import java.util.List;

public class BottomUpFlowLayout
extends ComponentLayout {
    public BottomUpFlowLayout(GuiComponent guiComponent) {
        super(guiComponent);
    }


    @Override
    public void p() {
        this.i();
        double d = this.O().G$src$D$1b2f02a();
        double d2 = this.O().n() + this.O().L();
        ArrayList<Double> arrayList = new ArrayList<Double>();
        double d3 = 0.0;
        List<GuiComponent> list = this.c();
        for (int i = list.size() - 1; i >= 0; --i) {
            GuiComponent guiComponent = list.get(i);
            if (!guiComponent.V$src$Z$1xhop3l()) continue;
            LayoutConstraints layoutConstraints = this.Q().get(guiComponent);
            if (layoutConstraints.G$src$Z$1xi35hg()) {
                d = this.O().G$src$D$1b2f02a() + this.O().A() - guiComponent.A();
            }
            guiComponent.K(d);
            guiComponent.S(d2 - guiComponent.t$src$D$1x9zexg());
            if (!arrayList.contains(d2)) {
                arrayList.add(d2);
            }
            if (!this.Q().containsKey(guiComponent)) {
                d += guiComponent.A();
                continue;
            }
            boolean bl = layoutConstraints.V();
            if (guiComponent.L() > d3) {
                d3 = guiComponent.t$src$D$1x9zexg();
            }
            if (bl || layoutConstraints.G$src$Z$1xi35hg()) {
                d2 -= d3;
                d = this.O().G$src$D$1b2f02a();
                d3 = 0.0;
                continue;
            }
            d += guiComponent.A();
        }
        for (GuiComponent guiComponent : this.c()) {
            this.m().put(guiComponent, new RectData(guiComponent.G$src$D$1b2f02a(), guiComponent.n(), guiComponent.A(), guiComponent.L()));
            if (!guiComponent.V$src$Z$1xhop3l()) continue;
            double d4 = this.O().G$src$D$1b2f02a();
            double d5 = this.O().n() + this.O().L();
            double d6 = guiComponent.G$src$D$1b2f02a() + guiComponent.A() - d4;
            double d7 = d5 - guiComponent.n();
            if (d6 > this.C()) {
                this.r(d6);
            }
            if (d7 > this.y()) {
                this.B(d7);
            }
            guiComponent.i$src$V$c9opdk();
        }
    }
}

