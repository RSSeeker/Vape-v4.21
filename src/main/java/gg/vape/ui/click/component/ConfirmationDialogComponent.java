package gg.vape.ui.click.component;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.IconButtonComponent;
import gg.vape.ui.click.component.IconGlyphComponent;
import gg.vape.ui.click.component.SquareIconButtonComponent;
import gg.vape.ui.click.component.WrappingTextLabelComponent;
import gg.vape.ui.click.component.gui.TextButton;
import gg.vape.ui.click.frame.DimmedCenteredPopupFrame;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.PopupFrame;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;
import java.util.concurrent.CompletableFuture;
import org.jetbrains.annotations.Nullable;

public class ConfirmationDialogComponent
extends GuiComponent {
    private IconGlyphComponent dialogIcon;
    private final WrappingTextLabelComponent messageLabel;
    private double requestedHeight = 80.0;
    private final IconButtonComponent closeButton = new SquareIconButtonComponent("newclose", 1.0, new Color(255, 255, 255, 0), new Color(255, 255, 255, 25), 10.0, 10.0);
    private Color borderColor;
    private final boolean hasIcon;
    private final TextButton cancelButton;
    private double renderedHeight = 0.0;
    private boolean showBorder;
    private final TextButton confirmButton;

    public void setRequestedHeight(double requestedHeight) {
        this.requestedHeight = requestedHeight;
    }

    public TextButton getConfirmButton() {
        return this.confirmButton;
    }

    public static CompletableFuture<Void> show(Frame ownerFrame, String message, String confirmText, String iconResource, Runnable confirmAction, double requestedHeight, @Nullable String cancelText, @Nullable Runnable cancelAction) {
        CompletableFuture<Void> completion = new CompletableFuture<Void>();
        ConfirmationDialogComponent dialog = new ConfirmationDialogComponent(message, confirmText, iconResource);
        if (requestedHeight != -1.0) {
            dialog.setRequestedHeight(requestedHeight);
        }
        if (cancelText != null) {
            dialog.cancelButton.setVisible(true);
            dialog.cancelButton.setLabelText(cancelText);
            dialog.cancelButton.setNormalTextColor(ConfirmationDialogComponent.J.Z);
            dialog.cancelButton.setHoverTextColor(Color.WHITE);
            dialog.cancelButton.setUseAlternateFont(true);
            dialog.cancelButton.setAnimateTextColor(true);
            dialog.confirmButton.setBackgroundAnimationColors(ConfirmationDialogComponent.J.B, ConfirmationDialogComponent.J.O);
        }
        DimmedCenteredPopupFrame popupFrame = ClientSettings.createPopup(ownerFrame, dialog, DimmedCenteredPopupFrame.class);
        dialog.getConfirmButton().addClickListener(() -> ConfirmationDialogComponent.handleConfirm(popupFrame, confirmAction, completion));
        dialog.getCloseButton().addClickListener(() -> ConfirmationDialogComponent.handleClose(popupFrame, completion));
        dialog.getCancelButton().addClickListener(() -> ConfirmationDialogComponent.handleCancel(popupFrame, cancelAction, completion));
        popupFrame.q(ownerFrame, popupFrame);
        return completion;
    }

    private static void handleClose(PopupFrame popupFrame, CompletableFuture<Void> completion) {
        ClientSettings.removePopup(popupFrame);
        completion.complete(null);
    }

    public ConfirmationDialogComponent(String message, String confirmText, @Nullable String iconResource) {
        this.borderColor = ConfirmationDialogComponent.J.y;
        this.showBorder = true;
        this.messageLabel = new WrappingTextLabelComponent(message, 0.9f, ConfirmationDialogComponent.J.Z);
        this.confirmButton = new TextButton(confirmText, 0.7, ConfirmationDialogComponent.J.d, ConfirmationDialogComponent.J.c, 36.0, 14.0);
        this.cancelButton = new TextButton("Cancel", 0.7, ConfirmationDialogComponent.J.d, ConfirmationDialogComponent.J.c, 36.0, 14.0);
        this.cancelButton.setVisible(false);
        this.setShowDisabledOverlay(false);
        this.setDisabledOverlayColor(ConfirmationDialogComponent.J.m.brighter());
        this.confirmButton.setDeriveTextColorFromBackground(false);
        this.confirmButton.setNormalTextColor(Color.WHITE);
        this.confirmButton.setHoverTextColor(Color.WHITE);
        this.hasIcon = iconResource != null;
        if (this.hasIcon) {
            this.dialogIcon = new IconGlyphComponent(iconResource, 12.0f, 12.0f, Color.white);
            this.addChildren(this.dialogIcon);
        }
        this.addChildren(this.confirmButton, this.closeButton, this.messageLabel, this.cancelButton);
    }


    @Override
    public double x() {
        return 100.0;
    }

    @Override
    public void H() {
        double height = this.requestedHeight;
        double messageOffsetY = 0.0;
        if (!this.hasIcon) {
            height -= 20.0;
            messageOffsetY += 20.0;
        }
        this.renderedHeight = height;
        GuiRenderPrimitives.d(this.G$src$D$1b2f02a(), this.n(), 100.0, height, this.getDisabledOverlayColor());
        if (this.showBorder) {
            GuiRenderPrimitives.P(this.G$src$D$1b2f02a(), this.n(), 100.0, height, this.borderColor, 2.0f, 1.0f, 1.0f);
        }
        if (this.hasIcon) {
            this.dialogIcon.K(this.G$src$D$1b2f02a() + (100.0 - this.dialogIcon.A()) / 2.0);
            this.dialogIcon.S(this.n() + 10.0);
        }
        this.closeButton.K(this.G$src$D$1b2f02a() + 100.0 - this.closeButton.A() - 2.0);
        this.closeButton.S(this.n() + 2.0);
        this.messageLabel.K(this.G$src$D$1b2f02a() + 5.0);
        this.messageLabel.setExplicitWidth(this.A() - 10.0);
        this.messageLabel.S(this.n() + 32.0 - messageOffsetY);
        double messageHeight = this.messageLabel.getMeasuredTextHeight();
        this.confirmButton.K(this.G$src$D$1b2f02a() + (100.0 - this.confirmButton.A()) / 2.0);
        this.confirmButton.S(this.messageLabel.n() + messageHeight + 10.0);
        if (this.cancelButton.V$src$Z$1xhop3l()) {
            this.cancelButton.o(36.0);
            this.cancelButton.setBackgroundAnimationColors(ConfirmationDialogComponent.J.t, ConfirmationDialogComponent.J.t);
            this.cancelButton.setDeriveTextColorFromBackground(false);
            double buttonGroupWidth = this.cancelButton.A() + this.confirmButton.A() + 5.0;
            double buttonGroupX = this.G$src$D$1b2f02a() + this.A() / 2.0 - buttonGroupWidth / 2.0;
            this.confirmButton.K(buttonGroupX);
            this.cancelButton.K(this.confirmButton.G$src$D$1b2f02a() + this.cancelButton.A() + 2.5);
            this.cancelButton.S(this.confirmButton.n());
        }
    }

    @Override
    public double C() {
        return this.renderedHeight;
    }

    public void setShowBorder(boolean showBorder) {
        this.showBorder = showBorder;
    }

    private static void handleConfirm(PopupFrame popupFrame, Runnable confirmAction, CompletableFuture<Void> completion) {
        ClientSettings.removePopup(popupFrame);
        confirmAction.run();
        completion.complete(null);
    }

    private static void handleCancel(PopupFrame popupFrame, Runnable cancelAction, CompletableFuture<Void> completion) {
        ClientSettings.removePopup(popupFrame);
        if (cancelAction != null) {
            cancelAction.run();
        }
        completion.complete(null);
    }

    public TextButton getCancelButton() {
        return this.cancelButton;
    }

    public static CompletableFuture<Void> showStandard(Frame ownerFrame, String message, String confirmText, String iconResource, Runnable confirmAction) {
        return ConfirmationDialogComponent.show(ownerFrame, message, confirmText, iconResource, confirmAction, -1.0, null, null);
    }

    public boolean isShowBorder() {
        return this.showBorder;
    }

    public Color getBorderColor() {
        return this.borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public IconButtonComponent getCloseButton() {
        return this.closeButton;
    }
}

