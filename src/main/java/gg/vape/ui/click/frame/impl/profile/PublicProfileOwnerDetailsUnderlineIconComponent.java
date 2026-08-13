package gg.vape.ui.click.frame.impl.profile;

import gg.vape.ui.click.component.GlyphIconComponent;
import gg.vape.ui.click.frame.impl.profile.PublicProfileOwnerDetailsPanel;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

class PublicProfileOwnerDetailsUnderlineIconComponent
extends GlyphIconComponent {
    PublicProfileOwnerDetailsUnderlineIconComponent(String icon, double iconWidth, double iconHeight, double width, double height, Color color, Color hoverColor, Color pressedColor) {
        super(icon, iconWidth, iconHeight, width, height, color, hoverColor, pressedColor);
    }

    @Override
    public void c() {
        super.c();
        GuiRenderPrimitives.d(this.G$src$D$1b2f02a() - 6.0, this.n(), 6.0, 1.0f, PublicProfileOwnerDetailsUnderlineIconComponent.J.y);
    }
}
