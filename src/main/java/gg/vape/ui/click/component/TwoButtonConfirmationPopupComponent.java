package gg.vape.ui.click.component;

import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.SimpleTextLabelComponent;
import gg.vape.ui.click.component.gui.TextButton;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

public class TwoButtonConfirmationPopupComponent
extends GuiComponent {
    private Color borderColor;
    private static final String CANCEL_TEXT = "CANCEL";
    private boolean borderVisible;
    private final TextButton cancelButton;
    private final TextButton confirmButton;
    private final SimpleTextLabelComponent messageLabel;

    public TwoButtonConfirmationPopupComponent(String message, String confirmText) {
        this.borderColor = TwoButtonConfirmationPopupComponent.J.y;
        this.borderVisible = false;
        this.messageLabel = new SimpleTextLabelComponent(message, 0.8f, TwoButtonConfirmationPopupComponent.J.A, false);
        this.messageLabel.o(this.messageLabel.getTextWidth() * (double)0.76f);
        this.confirmButton = new TextButton(confirmText, 0.65, TwoButtonConfirmationPopupComponent.J.d, TwoButtonConfirmationPopupComponent.J.c, 36.0, 14.0);
        this.cancelButton = new TextButton(CANCEL_TEXT, 0.65, TwoButtonConfirmationPopupComponent.J.d, TwoButtonConfirmationPopupComponent.J.c, 36.0, 14.0);
        this.setShowDisabledOverlay(false);
        this.setDisabledOverlayColor(TwoButtonConfirmationPopupComponent.J.m.brighter());
        this.confirmButton.setDeriveTextColorFromBackground(false);
        this.confirmButton.setUseAlternateFont(true);
        this.confirmButton.o(20.0);
        this.confirmButton.Y(9.0);
        this.confirmButton.setCornerRadius(1.0f);
        this.confirmButton.setNormalTextColor(Color.WHITE);
        this.confirmButton.setHoverTextColor(Color.WHITE);
        this.cancelButton.setUseAlternateFont(false);
        this.cancelButton.setAnimateTextColor(true);
        this.cancelButton.setDeriveTextColorFromBackground(false);
        this.cancelButton.o(28.0);
        this.cancelButton.Y(9.0);
        this.cancelButton.setBorderAlpha(0.8f);
        this.cancelButton.setNormalTextColor(TwoButtonConfirmationPopupComponent.J.Z);
        this.cancelButton.setHoverTextColor(TwoButtonConfirmationPopupComponent.J.A);
        this.cancelButton.setTransparentBackgroundBorder(new Color(255, 255, 255, 20));
        this.cancelButton.setBackgroundAnimationColors(TwoButtonConfirmationPopupComponent.J.t, TwoButtonConfirmationPopupComponent.J.t);
        this.addChildren(this.messageLabel, this.confirmButton, this.cancelButton);
    }

    public TextButton getConfirmButton() {
        return this.confirmButton;
    }


    @Override
    public double C() {
        return 25.0;
    }

    public boolean isBorderVisible() {
        return this.borderVisible;
    }

    public Color getBorderColor() {
        return this.borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public void setBorderVisible(boolean borderVisible) {
        this.borderVisible = borderVisible;
    }

    @Override
    public double x() {
        return this.messageLabel.A() + 20.0 + this.confirmButton.A() + this.cancelButton.A() + 8.0;
    }

    public TextButton getCancelButton() {
        return this.cancelButton;
    }

    @Override
    public void H() {
        double height = this.L();
        GuiRenderPrimitives.d(this.G$src$D$1b2f02a(), this.n(), this.A(), height, this.getDisabledOverlayColor());
        if (this.borderVisible) {
            GuiRenderPrimitives.P(this.G$src$D$1b2f02a(), this.n(), this.A(), height, this.borderColor, 2.0f, 1.0f, 1.0f);
        }
        this.messageLabel.K(this.G$src$D$1b2f02a() + 2.0);
        this.messageLabel.S(this.n() + this.L() / 2.0 - this.messageLabel.getTextHeight() / 2.0 - 2.0);
        this.confirmButton.K(this.G$src$D$1b2f02a() + this.A() - (this.confirmButton.A() + 5.0));
        this.confirmButton.S(this.n() + this.L() / 2.0 - this.confirmButton.L() / 2.0);
        this.cancelButton.K(this.confirmButton.G$src$D$1b2f02a() - (this.cancelButton.A() + 4.0));
        this.cancelButton.S(this.confirmButton.n());
    }
}

