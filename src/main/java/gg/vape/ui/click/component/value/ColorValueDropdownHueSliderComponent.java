package gg.vape.ui.click.component.value;

import gg.vape.ui.click.component.value.ColorPaletteSliderComponent;
import gg.vape.ui.click.component.value.ColorValueDropdownComponent;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.value.ColorValue;
import java.awt.Color;

public class ColorValueDropdownHueSliderComponent
extends ColorPaletteSliderComponent {
    final ColorValueDropdownComponent dropdownComponent;
    private static final String HANDLE_ICON = "teamdot";

    @Override
    protected void renderHandle() {
        GuiRenderPrimitives.F(HANDLE_ICON, this.G$src$D$1b2f02a() + (double)this.getHandlePositionAnimation().getInterpolatedValue().floatValue(), this.handleBounds.W() + this.handleBounds.R() / 2.0, this.handleBounds.e(), this.handleBounds.R(), this.getHandleColorAnimation().getInterpolatedColor());
    }

    @Override
    public boolean isCustomColor() {
        return false;
    }

    public ColorValueDropdownHueSliderComponent(ColorValueDropdownComponent dropdownComponent, String label, ColorValue colorValue, Color[] paletteColors) {
        super(label, colorValue, paletteColors);
        this.dropdownComponent = dropdownComponent;
    }
}
