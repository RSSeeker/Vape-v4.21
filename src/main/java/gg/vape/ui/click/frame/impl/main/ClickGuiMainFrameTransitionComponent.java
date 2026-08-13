package gg.vape.ui.click.frame.impl.main;

import gg.vape.ui.click.animation.DoubleAnimation;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.frame.impl.main.ClickGuiMainFrame;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

public class ClickGuiMainFrameTransitionComponent
extends GuiComponent {
    private final ClickGuiMainFrame frame;
    private boolean active;
    private final DoubleAnimation animation;

    public boolean isActive() {
        return this.active;
    }

    public ClickGuiMainFrameTransitionComponent(ClickGuiMainFrame clickGuiMainFrame) {
        this.frame = clickGuiMainFrame;
        this.animation = new DoubleAnimation(0.15, 0.0, 1.0);
        this.animation.O();
        this.setVisible(false);
    }

    public void setActive(boolean active) {
        if (this.active != active) {
            this.active = active;
            this.animation.J();
            if (active) {
                this.animation.C();
                this.setAcceptsMouseInput(true);
            } else {
                this.animation.O();
                this.setAcceptsMouseInput(false);
            }
            this.setVisible(active);
            if (active) {
                this.K(this.frame.G$src$D$1b2f02a());
                this.S(this.frame.n());
                this.o(this.frame.A());
                this.Y(this.frame.L());
            }
        }
    }

    @Override
    public void H() {
        this.animation.u(this.active);
        double d = Math.max(0.0, Math.min(1.0, this.animation.getInterpolatedValue()));
        if (d <= 0.0) {
            return;
        }
        this.setAcceptsMouseInput(true);
        double d2 = this.frame.G$src$D$1b2f02a();
        double d3 = this.frame.n();
        double d4 = this.frame.A();
        double d5 = this.frame.L();
        int n = (int)(d * 140.0);
        Color color = new Color(0, 0, 0, Math.min(255, Math.max(0, n)));
        GuiRenderPrimitives.e(d2, d3, d4, d5, color, false, 2.0f, 1.0f);
    }

}

