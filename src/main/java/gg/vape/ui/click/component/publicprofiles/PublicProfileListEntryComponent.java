package gg.vape.ui.click.component.publicprofiles;

import gg.vape.config.PublicProfile;
import gg.vape.ui.click.component.TruncatedTextComponent;
import gg.vape.ui.click.component.gui.InteractiveComponent;
import gg.vape.ui.click.component.publicprofiles.PublicProfileIdBadgeComponent;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

public class PublicProfileListEntryComponent
extends InteractiveComponent {
    private final PublicProfileIdBadgeComponent idBadge;
    private final PublicProfile publicProfile;
    private boolean pressed;
    private final TruncatedTextComponent title;
    private static final String ELLIPSIS = "...";


    @Override
    public void u() {
        if (this.pressed && !this.w$src$Z$e457mb()) {
            this.pressed = false;
        }
    }

    @Override
    public double x() {
        return 92.0;
    }

    @Override
    public double C() {
        return 18.0;
    }

    public PublicProfileListEntryComponent(PublicProfile publicProfile) {
        this.publicProfile = publicProfile;
        this.title = new TruncatedTextComponent(publicProfile.getName(), ELLIPSIS, 0.0, 0.85, PublicProfileListEntryComponent.J.A, false);
        this.idBadge = new PublicProfileIdBadgeComponent(this.getUnreadCount());
        this.setPropagateMouseEvents(true);
        this.addChildren(this.title, this.idBadge);
    }

    public PublicProfile getPublicProfile() {
        return this.publicProfile;
    }

    @Override
    public void F() {
        this.pressed = true;
    }

    @Override
    public void H() {
        float f;
        double d = this.title.getTextHeight();
        double d2 = this.n() + this.L() / 2.0 - d / 2.0;
        Color color = PublicProfileListEntryComponent.J.m;
        Color color2 = PublicProfileListEntryComponent.J.Z;
        if (this.pressed) {
            color = PublicProfileListEntryComponent.J.a;
            color2 = PublicProfileListEntryComponent.J.A;
        }
        this.title.K(this.G$src$D$1b2f02a() + 7.0);
        this.title.S(d2);
        double d3 = this.A();
        this.getClass();
        double d4 = d3 - 5.0;
        if (this.idBadge.getCount() > 0L) {
            f = 15.0f;
        } else {
            this.getClass();
            f = 5.0f;
        }
        this.title.setMaxWidth(d4 - (double)f);
        this.title.setTextColor(color2);
        this.idBadge.setCount(this.getUnreadCount());
        this.idBadge.K(this.G$src$D$1b2f02a() + this.A() - this.idBadge.A() - 8.0);
        this.idBadge.S(this.n() + this.L() / 2.0 - this.idBadge.L() / 2.0);
        GuiRenderPrimitives.d(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), color);
    }

    private long getUnreadCount() {
        return this.publicProfile.getShareInfo() != null ? this.publicProfile.getShareInfo().getUnreadNotifications() : 0L;
    }
}

