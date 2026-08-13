package gg.vape.ui.click.frame.impl.profile;

import gg.vape.Vape;
import gg.vape.config.Profile;
import gg.vape.ui.click.frame.FrameNavigationButtonComponent;
import gg.vape.ui.click.frame.impl.profile.ProfilesSettingsFrame;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.unmap.ColorUtil;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

public class ProfilesFrameNavigationButtonComponent
extends FrameNavigationButtonComponent {

    public ProfilesFrameNavigationButtonComponent() {
        super("Profiles", null, ProfilesSettingsFrame.class);
    }

    @Override
    public void H() {
        double d;
        super.H();
        Profile profile = Vape.INSTANCE.getProfilesManager().getActiveProfile();
        String string = profile.getName();
        boolean bl = profile.isPublicProfileFlag();
        if (bl) {
            int n = 8;
            if (string.length() > n) {
                string = string.substring(0, n) + "...";
            }
            SmoothFontRenderer smoothFontRenderer = this.getAlternateFontRenderer(0.8);
            double d2 = this.n() + this.L() / 2.0;
            double d3 = smoothFontRenderer.N(string);
            double d4 = smoothFontRenderer.d(string);
            double d5 = d4 + 6.0;
            double d6 = d3 + (double)18;
            double d7 = 25.0;
            double d8 = d7 + 7.0;
            double d9 = d8 + 4.0;
            GuiRenderPrimitives.d(this.G$src$D$1b2f02a() + this.A() - d9 - d3, d2 - d5 / 2.0, d6, d5, ColorUtil.withAlpha(Color.WHITE, 7));
            smoothFontRenderer.d(string, this.G$src$D$1b2f02a() + this.A() - d8 - d3, d2 - d4 / 2.0 + 0.5, new Color(115, 113, 115));
            GuiRenderPrimitives.F("update_available_active@2x", this.G$src$D$1b2f02a() + this.A() - d7, this.n() + this.L() / 2.0, 8.0, 8.0, J.z());
            return;
        }
        int n = 10;
        if (string.length() > n) {
            string = string.substring(0, n) + "...";
        }
        SmoothFontRenderer smoothFontRenderer = this.getAlternateFontRenderer(0.8);
        double d10 = this.n() + this.L() / 2.0;
        double d11 = smoothFontRenderer.N(string);
        double d12 = smoothFontRenderer.d(string);
        double d13 = d12 + 6.0;
        double d14 = d11 + (double)9;
        double d15 = d = 23.0;
        double d16 = d15 + 4.0;
        GuiRenderPrimitives.d(this.G$src$D$1b2f02a() + this.A() - d16 - d11, d10 - d13 / 2.0, d14, d13, ColorUtil.withAlpha(Color.WHITE, 7));
        smoothFontRenderer.d(string, this.G$src$D$1b2f02a() + this.A() - d15 - d11, d10 - d12 / 2.0 + 0.5, new Color(115, 113, 115));
    }
}

