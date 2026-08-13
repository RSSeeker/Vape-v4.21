package gg.vape.ui.click.frame.impl.profile;

import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.impl.profile.PublicProfilesFrame;
import gg.vape.ui.click.frame.impl.profile.PublicProfilesFrameHeaderActionComponent;

class PublicProfilesFrameHeaderComponent
extends PublicProfilesFrameHeaderActionComponent {
    final PublicProfilesFrame profilesFrame;

    PublicProfilesFrameHeaderComponent(PublicProfilesFrame publicProfilesFrame, Frame frame, String string, String string2, double d) {
        super(frame, string, string2, d);
        this.profilesFrame = publicProfilesFrame;
    }

    @Override
    public double x() {
        return this.profilesFrame.x();
    }
}
