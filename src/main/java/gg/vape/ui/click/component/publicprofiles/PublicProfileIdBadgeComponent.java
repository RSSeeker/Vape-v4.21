package gg.vape.ui.click.component.publicprofiles;

import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;

public class PublicProfileIdBadgeComponent
extends GuiComponent {
    private long count;
    private static final String MAX_DISPLAY_COUNT = "99";
    private double fontScale;

    @Override
    public double C() {
        return 7.0;
    }

    public long getCount() {
        return this.count;
    }

    public void setFontScale(double fontScale) {
        this.fontScale = fontScale;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public double x() {
        return 8.0;
    }

    public PublicProfileIdBadgeComponent(long l, double d) {
        this.count = l;
        this.fontScale = d;
    }

    @Override
    public void H() {
        if (this.count > 0L) {
            double d = 7.0;
            GuiRenderPrimitives.V(this.G$src$D$1b2f02a(), this.n(), d, 1.0, PublicProfileIdBadgeComponent.J.d);
            SmoothFontRenderer smoothFontRenderer = this.getFontRenderer(this.fontScale);
            String string = PublicProfileIdBadgeComponent.formatCount(this.count);
            double d2 = smoothFontRenderer.N(string);
            smoothFontRenderer.d(string, this.G$src$D$1b2f02a() + d / 2.0 - d2 / 2.0, this.n() + 1.5, PublicProfileIdBadgeComponent.J.A);
        }
    }

    public static String formatCount(long l) {
        if (l > 99L) {
            return MAX_DISPLAY_COUNT;
        }
        return String.valueOf(l);
    }


    public PublicProfileIdBadgeComponent(long l) {
        this(l, 0.6);
    }
}

