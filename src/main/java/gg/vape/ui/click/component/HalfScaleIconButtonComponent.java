package gg.vape.ui.click.component;

import gg.vape.ui.click.component.IconButtonComponent;
import gg.vape.utils.render.ImageRenderer;

public class HalfScaleIconButtonComponent
extends IconButtonComponent {
    @Override
    public void H() {
        float sourceWidth = (float)ImageRenderer.getImageWidth(this.getIconResource());
        float sourceHeight = (float)ImageRenderer.getImageHeight(this.getIconResource());
        float imageX = (float)(this.G$src$D$1b2f02a() + this.A() / 2.0);
        imageX -= sourceWidth / 4.0f;
        float imageY = (float)(this.n() + this.L() / 2.0);
        imageY -= sourceHeight / 4.0f;
        imageX = (int)imageX;
        imageY = (int)imageY;
        if (this.getOverrideColor() != null) {
            ImageRenderer.drawImage(this.getOverrideColor(), imageX, imageY, this.getIconResource(), sourceWidth / 2.0f, sourceHeight / 2.0f, false);
        } else {
            ImageRenderer.drawImage(this.w$src$Z$e457mb() ? this.getHoverColor() : this.getNormalColor(), imageX, imageY, this.getIconResource(), sourceWidth / 2.0f, sourceHeight / 2.0f, false);
        }
    }

    public HalfScaleIconButtonComponent(String iconResource) {
        super(iconResource);
    }

}

