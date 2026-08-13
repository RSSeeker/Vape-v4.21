package gg.vape.ui.notification;

import func.skidline.RectData;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.animation.DoubleAnimation;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.gui.WrappedTextComponent;
import gg.vape.ui.click.frame.FrameComponent;
import gg.vape.ui.notification.NotificationMessage;
import gg.vape.utils.MutableColor;
import gg.vape.utils.TimerUtil;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.RenderUtils;
import java.awt.Color;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class NotificationToastOverlay
extends GuiComponent {
    TimerUtil I;
    private long b;
    WrappedTextComponent O;
    NotificationMessage Q;
    Queue<NotificationMessage> R = new ConcurrentLinkedQueue<NotificationMessage>();
    private boolean K;
    private final DoubleAnimation i;
    FrameComponent G;

    private void Y$src$V$1in99ri() {
        this.i.Z();
        this.K = true;
    }

    @Override
    public double C() {
        return 0.0;
    }

    public void k(NotificationMessage notificationMessage) {
        this.R();
        this.T(notificationMessage);
    }

    private void Z$src$V$1int2cv() {
        if (this.Q == null) {
            return;
        }
        this.i.setEndValue(Math.max(14.5, this.O.C() + 8.0));
        int n = (int)(255.0 * this.i.X());
        int n2 = this.i.l() ? n : (!this.i.n() ? n : (int)(255.0 * this.i.X() / 4.0));
        RectData rectData = this.getBounds();
        MutableColor mutableColor = new MutableColor(this.Q.t().getRed(), this.Q.t().getGreen(), this.Q.t().getBlue(), n);
        MutableColor mutableColor2 = new MutableColor(this.Q.Y().getRed(), this.Q.Y().getGreen(), this.Q.Y().getBlue(), n);
        double d = 6.0;
        GuiRenderPrimitives.d(rectData.o(), rectData.W(), rectData.e(), rectData.R(), (Color)mutableColor);
        GuiRenderPrimitives.P(rectData.o() - 0.5, rectData.W() - 0.5, rectData.e() + 1.0, rectData.R() + 1.0, mutableColor2, 2.0f, 1.0f, 1.0f);
        this.O.K(rectData.o() + d);
        this.O.S(Math.min(rectData.W() + 4.0, this.G.n() + this.G.L() - 6.0));
        MutableColor mutableColor3 = new MutableColor(this.Q.Y().darker().getRGB(), n2);
        this.O.setTextColor(mutableColor3);
        RenderUtils.m(rectData.o(), rectData.W(), rectData.e(), rectData.R());
        this.O.c();
        RenderUtils.T();
    }

    private void T(NotificationMessage notificationMessage) {
        this.Q = notificationMessage;
        this.O = new WrappedTextComponent(this.Q.S(), 0.8f);
        this.O.setWrapWidth(this.G.A() - 14.0);
        this.i.c();
    }

    @Override
    public double x() {
        return 0.0;
    }

    private boolean L$src$Z$1ig3y59() {
        if (this.k().l()) {
            return false;
        }
        return !this.R.isEmpty();
    }

    private boolean R$src$Z$1ijeppf() {
        return this.b == Long.MAX_VALUE || this.b + (long)this.Q.c() < System.currentTimeMillis();
    }

    public NotificationToastOverlay(FrameComponent frameComponent) {
        this.b = Long.MAX_VALUE;
        this.I = new TimerUtil();
        this.i = new DoubleAnimation(0.3, 0.0, 14.5);
        this.G = frameComponent;
        this.Y(0.0);
    }

    private void t(long l) {
        this.b = l;
    }

    @Override
    public void H() {
        this.Z$src$V$1int2cv();
    }

    @Override
    public void c() {
        this.Y(0.0);
        super.c();
    }

    @Override
    public void I() {
    }

    public int C(NotificationMessage notificationMessage) {
        this.R.add(notificationMessage);
        return this.R.size() - 1;
    }

    @Override
    public void u() {
        if (!this.I.hasTimeElapsed(0L)) {
            return;
        }
        if (this.K && !this.i.l()) {
            this.I.reset();
            this.Q = null;
            this.t(Long.MAX_VALUE);
            this.K = false;
            return;
        }
        if (this.Q == null) {
            if (!this.R.isEmpty()) {
                this.T(this.R.remove());
            }
            return;
        }
        if (this.w$src$J$1j3r3a0() == Long.MAX_VALUE) {
            if (this.k().N()) {
                this.s();
            }
            return;
        }
        if (!this.K && (this.R$src$Z$1ijeppf() || this.L$src$Z$1ig3y59())) {
            this.Y$src$V$1in99ri();
        }
    }

    public Queue<NotificationMessage> z() {
        return this.R;
    }


    public long w$src$J$1j3r3a0() {
        return this.b;
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    public void R() {
        this.Q = null;
        this.t(Long.MAX_VALUE);
        this.K = false;
    }

    @Override
    public void F() {
    }

    private void s() {
        this.b = System.currentTimeMillis();
    }

    @Override
    public RectData getBounds() {
        return new RectData(this.G.G$src$D$1b2f02a() + 2.5, this.G.n() + this.G.L() - this.i.getInterpolatedValue(), this.G.A() - 5.0, Math.max(Math.max(this.i.getInterpolatedValue() - 0.5, 4.0), 0.0));
    }

    public DoubleAnimation k() {
        return this.i;
    }
}

