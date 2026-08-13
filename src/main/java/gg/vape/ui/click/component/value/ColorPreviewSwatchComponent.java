package gg.vape.ui.click.component.value;

import func.skidline.RectData;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.gui.InteractiveComponent;
import gg.vape.utils.TimerUtil;
import gg.vape.utils.render.ImageRenderer;
import gg.vape.utils.render.RenderUtils;
import gg.vape.value.ColorValue;

public class ColorPreviewSwatchComponent
extends InteractiveComponent {
    private final ColorValue colorValue;
    private TimerUtil animationTimer;
    private boolean hovered;
    int animationFrame = 0;
    private RectData swatchBounds = new RectData(0.0, 0.0, 0.0, 0.0);

    @Override
    public void dispatchMouseEvent(GuiMouseEvent guiMouseEvent) {
        if (this.swatchBounds.J(guiMouseEvent.getX(), guiMouseEvent.getY())) {
            this.colorValue.setRainbowEnabled(!this.colorValue.isRainbowEnabled());
            if (this.colorValue.isRainbowEnabled()) {
                this.colorValue.getSaturationValue().setValue(255.0);
                this.colorValue.getBrightnessValue().setValue(255.0);
            }
        }
    }

    @Override
    public void u() {
        if (this.hovered && !this.swatchBounds.Z(RenderUtils.h())) {
            this.hovered = false;
        }
    }

    @Override
    public void H() {
        this.swatchBounds = new RectData(this.G$src$D$1b2f02a(), this.n(), 6.0, 6.0);
        ImageRenderer.drawImage(this.colorValue.isRainbowEnabled() ? ColorPreviewSwatchComponent.J.W : (this.hovered ? ColorPreviewSwatchComponent.J.f : ColorPreviewSwatchComponent.J.W), (float)this.swatchBounds.o(), (float)this.swatchBounds.W(), "rainbow_4", (float)this.swatchBounds.e(), (float)this.swatchBounds.R(), false);
        ImageRenderer.drawImage(this.animationFrame >= 3 ? ColorPreviewSwatchComponent.J.d : (this.hovered ? ColorPreviewSwatchComponent.J.f : ColorPreviewSwatchComponent.J.W), (float)this.swatchBounds.o(), (float)this.swatchBounds.W(), "rainbow_3", (float)this.swatchBounds.e(), (float)this.swatchBounds.R(), false);
        ImageRenderer.drawImage(this.animationFrame >= 2 ? ColorPreviewSwatchComponent.J.I : (this.hovered ? ColorPreviewSwatchComponent.J.f : ColorPreviewSwatchComponent.J.W), (float)this.swatchBounds.o(), (float)this.swatchBounds.W(), "rainbow_2", (float)this.swatchBounds.e(), (float)this.swatchBounds.R(), false);
        ImageRenderer.drawImage(this.animationFrame >= 1 ? ColorPreviewSwatchComponent.J.B : (this.hovered ? ColorPreviewSwatchComponent.J.f : ColorPreviewSwatchComponent.J.W), (float)this.swatchBounds.o(), (float)this.swatchBounds.W(), "rainbow_1", (float)this.swatchBounds.e(), (float)this.swatchBounds.R(), false);
        if (this.animationTimer.hasTimeElapsed(100L)) {
            if (this.colorValue.isRainbowEnabled()) {
                if (this.animationFrame < 3) {
                    ++this.animationFrame;
                }
            } else if (this.animationFrame > 0) {
                --this.animationFrame;
            }
            this.animationTimer.reset();
        }
    }


    @Override
    public double x() {
        return 6.0;
    }

    @Override
    public double C() {
        return 6.0;
    }

    public ColorPreviewSwatchComponent(ColorValue colorValue) {
        this.animationTimer = new TimerUtil();
        this.colorValue = colorValue;
    }
}

