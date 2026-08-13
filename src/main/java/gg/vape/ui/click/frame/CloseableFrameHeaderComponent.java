package gg.vape.ui.click.frame;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.IconButtonComponent;
import gg.vape.ui.click.frame.CollapsibleFrame;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.FrameHeaderCloseToggleClickHandler;
import gg.vape.ui.click.frame.FrameHeaderComponent;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.ImageRenderer;
import java.awt.Color;

public class CloseableFrameHeaderComponent
extends FrameHeaderComponent {
    private String O;
    private static int[] Q;
    private double v;
    private String I;
    private boolean G = true;
    private boolean K;
    private IconButtonComponent R;

    public CloseableFrameHeaderComponent(Frame frame, String string, String string2) {
        this(frame, string, string2, 1.0);
    }

    @Override
    public void F() {
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    static boolean c(CloseableFrameHeaderComponent closeableFrameHeaderComponent, boolean bl) {
        closeableFrameHeaderComponent.K = bl;
        return closeableFrameHeaderComponent.K;
    }

    public void Y(boolean bl) {
        this.G = bl;
    }

    static {
        CloseableFrameHeaderComponent.o((int[])null);
    }

    public static void o(int[] nArray) {
        Q = nArray;
    }

    @Override
    public void H() {
        SmoothFontRenderer smoothFontRenderer = this.getFontRenderer(0.9);
        this.getClass();
        float f = 8.0f * (float)this.v * 0.9f;
        Color color = CloseableFrameHeaderComponent.J.A;
        double d = smoothFontRenderer.d(this.O);
        double d2 = this.n() + this.L() / 2.0 - d / 2.0;
        double d3 = this.n() + this.L() / 2.0 - (double)(f / 2.0f);
        double d4 = this.G$src$D$1b2f02a() + 6.0;
        double d5 = 32.0 * ((double)f / (ImageRenderer.getImageWidth(this.I) * this.v));
        smoothFontRenderer.d(this.O, d4 + d5 + 2.0, d2, color);
        if (this.G) {
            ImageRenderer.drawImage(color, (float)d4, (float)d3, this.I, f, f, false);
        }
        this.R.setOverrideColor(this.w$src$Lgg_vape_ui_click_frame_Frame_$y4htd0().y$src$Z$1f55jvh() ? CloseableFrameHeaderComponent.J.f : null);
        double d6 = this.G$src$D$1b2f02a() + this.A() - 7.5;
        this.getClass();
        this.R.K(d6 - 8.0);
        this.R.S(this.n());
        this.R.Y(this.L());
        this.R.setIconResource(this.K ? "downexpand" : "upcollapse");
    }

    public static int[] A$src$AI$cq4488() {
        return Q;
    }

    @Override
    public void u() {
    }

    @Override
    public void I() {
    }

    public CloseableFrameHeaderComponent(Frame frame, String string, String string2, double d) {
        super(frame);
        this.I = string;
        this.O = string2;
        this.v = d;
        this.K = ((CollapsibleFrame)((Object)frame)).q();
        this.R = new IconButtonComponent(this.K ? "downexpand" : "upcollapse", 0.3);
        this.R.addClickListener(new FrameHeaderCloseToggleClickHandler(this, frame));
        this.addChildren(this.R);
    }

}
