package gg.vape.ui.click.component.value;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.value.RandomRangeSliderComponent;
import gg.vape.ui.click.component.value.RangeEndpoint;
import gg.vape.ui.click.component.value.SliderInputHandle;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;

public class RandomRangeSliderInputHandle
extends SliderInputHandle {
    private RangeEndpoint endpoint;
    private RandomRangeSliderComponent rangeSlider;

    private static Exception passthroughException(Exception exception) {
        return exception;
    }

    public RandomRangeSliderInputHandle(RandomRangeSliderComponent rangeSlider, RangeEndpoint endpoint) {
        this.rangeSlider = rangeSlider;
        this.endpoint = endpoint;
        this.actionButton.setVisible(false);
    }

    @Override
    public void H() {
        SmoothFontRenderer fontRenderer = this.getFontRenderer(0.75);
        String displayText = this.isEditing() ? this.getText() : this.getFormattedEndpointValue();
        fontRenderer.d(displayText, this.G$src$D$1b2f02a() + (this.getAvailableTextWidth() - fontRenderer.N(displayText)), this.n(), RandomRangeSliderInputHandle.J.Z);
        if (this.isFocused()) {
            this.cursorPosition = displayText.length();
            this.renderCaret(fontRenderer, this.G$src$D$1b2f02a() + this.getAvailableTextWidth(), this.n());
        }
        GuiRenderPrimitives.C(this.G$src$D$1b2f02a(), this.n() + 5.0 + 2.0, this.A(), 1.0, this.getUnderlineColor());
    }

    @Override
    public void submit() {
        try {
            String normalizedText = this.getText().replace(this.rangeSlider.getRandomValue().getEndpointFormat().getDecimalFormatSymbols().getDecimalSeparator(), '.');
            double value = Double.parseDouble(normalizedText);
            switch (this.endpoint) {
                case MINIMUM: {
                    if (value > this.rangeSlider.getRandomValue().getMaximumValue()) {
                        this.rangeSlider.getRandomValue().setMinimumValue(this.rangeSlider.getRandomValue().getMaximumValue());
                        this.rangeSlider.getRandomValue().setMaximumValue(value);
                        break;
                    }
                    this.rangeSlider.getRandomValue().setMinimumValue(value);
                    break;
                }
                case MAXIMUM: {
                    if (value < this.rangeSlider.getRandomValue().getMinimumValue()) {
                        this.rangeSlider.getRandomValue().setMaximumValue(this.rangeSlider.getRandomValue().getMinimumValue());
                        this.rangeSlider.getRandomValue().setMinimumValue(value);
                        break;
                    }
                    this.rangeSlider.getRandomValue().setMaximumValue(value);
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        this.rangeSlider.getRandomValue().setRange(new double[]{this.rangeSlider.getRandomValue().getMinimumValue(), this.rangeSlider.getRandomValue().getMaximumValue()});
        ClientSettings.activeComponent = null;
    }

    @Override
    public void loadCurrentValueForEditing() {
        this.setText(this.getFormattedEndpointValue());
    }

    public String getFormattedEndpointValue() {
        switch (this.endpoint) {
            case MINIMUM: {
                return this.rangeSlider.getRandomValue().getFormattedMinimum();
            }
            case MAXIMUM: {
                return this.rangeSlider.getRandomValue().getFormattedMaximum();
            }
        }
        return null;
    }
}
