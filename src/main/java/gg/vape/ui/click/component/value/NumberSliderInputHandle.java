package gg.vape.ui.click.component.value;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.value.NumberSliderComponent;
import gg.vape.ui.click.component.value.SliderInputHandle;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;

public class NumberSliderInputHandle
extends SliderInputHandle {
    private NumberSliderComponent numberSlider;


    @Override
    public void loadCurrentValueForEditing() {
        this.setText(((Double)this.numberSlider.getNumberValue().getValue()).toString());
    }

    public NumberSliderInputHandle(NumberSliderComponent numberSlider) {
        this.numberSlider = numberSlider;
        this.actionButton.setVisible(false);
    }

    @Override
    public void H() {
        SmoothFontRenderer fontRenderer = this.getFontRenderer(0.75);
        String displayText = this.isEditing() ? this.getText() : (this.hovered ? this.numberSlider.getDecimalFormat().format(this.numberSlider.getNumberValue().getValue()) : this.numberSlider.getDecimalFormat().format(this.numberSlider.getNumberValue().getValue()) + " " + this.numberSlider.getUnitSuffix());
        if (!this.isEditing() && !this.hovered && this.numberSlider.getUnitSuffix().length() <= 1) {
            displayText = this.numberSlider.getDecimalFormat().format(this.numberSlider.getNumberValue().getValue());
        }
        fontRenderer.d(displayText, this.G$src$D$1b2f02a() + (this.getAvailableTextWidth() - fontRenderer.N(displayText)), this.n(), NumberSliderInputHandle.J.Z);
        if (this.isFocused()) {
            this.cursorPosition = displayText.length();
            this.renderCaret(fontRenderer, this.G$src$D$1b2f02a() + this.getAvailableTextWidth(), this.n());
        }
        GuiRenderPrimitives.C(this.G$src$D$1b2f02a(), this.n() + 5.0 + 2.0, this.A(), 1.0, this.getUnderlineColor());
    }

    @Override
    public void submit() {
        try {
            String normalizedText = this.getText().replace(this.numberSlider.getNumberValue().getInputFormat().getDecimalFormatSymbols().getDecimalSeparator(), '.');
            double value = Double.parseDouble(normalizedText);
            this.numberSlider.getNumberValue().setRawValue(value);
        }
        catch (Exception exception) {
            // empty catch block
        }
        ClientSettings.activeComponent = null;
    }
}
