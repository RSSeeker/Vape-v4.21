package gg.vape.friend.ui;

import gg.vape.friend.ui.PartyMemberEntryComponent;
import gg.vape.friend.ui.PartyMemberEntryModeSwitchMap;
import gg.vape.ui.click.animation.ColorAnimation;
import gg.vape.ui.click.component.gui.InteractiveComponent;
import gg.vape.utils.render.ImageRenderer;

final class PartyInviteStatusIconComponent
extends InteractiveComponent {
    private final PartyMemberEntryComponent entry;
    private final ColorAnimation hoverAnimation;
    private static final String ICON_RESOURCE = "newclose";

    PartyInviteStatusIconComponent(PartyMemberEntryComponent partyMemberEntryComponent, PartyMemberEntryModeSwitchMap partyMemberEntryModeSwitchMap) {
        this(partyMemberEntryComponent);
    }

    private PartyInviteStatusIconComponent(PartyMemberEntryComponent partyMemberEntryComponent) {
        this.entry = partyMemberEntryComponent;
        this.hoverAnimation = new ColorAnimation(0.15, PartyMemberEntryComponent.getDeclineIconColor(), PartyMemberEntryComponent.getDeclineIconHoverColor());
        this.o(12.0);
        this.Y(12.0);
        this.setShowDisabledOverlay(false);
    }

    @Override
    public void H() {
        this.hoverAnimation.u(this.w$src$Z$e457mb());
        double iconX = this.G$src$D$1b2f02a() + (this.A() - 6.0) / 2.0;
        double iconY = this.n() + (this.L() - 6.0) / 2.0;
        ImageRenderer.drawImage(this.hoverAnimation.getInterpolatedColor(), (float)iconX, (float)iconY, ICON_RESOURCE, 6.0f, 6.0f, false);
    }

    @Override
    public double C() {
        return 12.0;
    }

    @Override
    public double x() {
        return 12.0;
    }
}
