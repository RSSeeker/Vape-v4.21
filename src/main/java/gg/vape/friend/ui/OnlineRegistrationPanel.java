package gg.vape.friend.ui;

import gg.vape.Vape;
import gg.vape.manager.client.OnlineAccountState;
import gg.vape.manager.client.OnlineConnectionManager;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.gui.TextButton;
import gg.vape.ui.click.component.gui.TextLabel;
import gg.vape.ui.click.component.gui.WrappedTextComponent;
import gg.vape.ui.click.frame.SettingsSectionComponent;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

public class OnlineRegistrationPanel
extends GuiComponent {
    private long retryAvailableAt;
    private final TextLabel dismissLabel;
    private boolean retryPending;
    private final TextButton retryButton;
    private final WrappedTextComponent statusText;

    @Override
    public double x() {
        return 105.0;
    }

    private void retryRegistration() {
        if (OnlineConnectionManager.INSTANCE.getAccountState() == OnlineAccountState.CONNECTING) {
            return;
        }
        if (this.retryPending) {
            return;
        }
        if (this.retryAvailableAt != -1L) {
            if (this.retryAvailableAt - System.currentTimeMillis() > 0L) {
                return;
            }
            this.retryAvailableAt = -1L;
        }
        this.retryPending = true;
        try {
            OnlineConnectionManager.INSTANCE.initialize();
            if (OnlineConnectionManager.INSTANCE.getAccountState() != OnlineAccountState.REGISTRATION_OFFLINE) {
                ClientSettings.getFrame(OnlineFriendsFrame.class).closeRegistrationPopup();
                this.retryAvailableAt = -1L;
            }
        }
        catch (Exception exception) {
            this.retryAvailableAt = System.currentTimeMillis() + 10000L;
            Vape.logThrowable(exception);
        }
        this.retryPending = false;
    }

    public OnlineRegistrationPanel() {
        this.retryButton = new TextButton("Reattempt", 0.8, OnlineRegistrationPanel.J.B, OnlineRegistrationPanel.J.O);
        this.dismissLabel = new TextLabel("Maybe later", 0.8, false);
        this.statusText = new WrappedTextComponent("", 0.8, OnlineRegistrationPanel.J.Z, false);
        this.retryPending = false;
        this.retryAvailableAt = -1L;
        this.addChildren(this.statusText, this.retryButton, this.dismissLabel);
        this.dismissLabel.o(32.0);
        this.dismissLabel.Y(10.0);
        this.dismissLabel.addClickListener(this::dismissRegistration);
        this.statusText.setFontScale(0.9);
        this.statusText.setWrapWidth(90.0);
        this.retryButton.setDeriveTextColorFromBackground(false);
        this.retryButton.setNormalTextColor(OnlineRegistrationPanel.J.A);
        this.retryButton.addClickListener(this::retryRegistration);
    }

    @Override
    public void H() {
        GuiRenderPrimitives.d(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), OnlineRegistrationPanel.J.i);
    }

    private void dismissRegistration() {
        OnlineFriendsFrame onlineFriendsFrame = ClientSettings.getFrame(OnlineFriendsFrame.class);
        onlineFriendsFrame.closeRegistrationPopup();
        onlineFriendsFrame.getModeToggle().setLeftSelected(false);
        for (GuiComponent child : ClientSettings.getFrame(OnlineFriendsFrame.class).h()) {
            if (!(child instanceof SettingsSectionComponent) || !((SettingsSectionComponent)child).A$src$Ljava_lang_String_$9tmd4u().equals("Online Settings")) continue;
            child.setVisible(false);
        }
    }

    @Override
    public void F() {
    }

    @Override
    public void I() {
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    private static Exception identityException(Exception exception) {
        return exception;
    }

    @Override
    public double C() {
        return 155.0;
    }

    @Override
    public void u() {
    }

    @Override
    public void c() {
        this.retryButton.setNormalTextColor(Color.white);
        double padding = 8.0;
        this.statusText.K(this.G$src$D$1b2f02a() + padding);
        this.statusText.S(this.n() + 30.0);
        this.retryButton.K(this.G$src$D$1b2f02a() + padding);
        this.retryButton.S(this.n() + 45.0);
        this.retryButton.setExplicitWidth(this.A() - padding * 2.0);
        this.retryButton.o(this.A() - padding * 2.0);
        this.retryButton.Y(14.0);
        if (OnlineConnectionManager.INSTANCE.getAccountState() == OnlineAccountState.CONNECTING) {
            this.retryButton.setVisible(false);
            this.statusText.setText("Checking Account");
        } else {
            this.retryButton.setVisible(true);
            this.statusText.setText("Authentication Error");
            if (this.retryAvailableAt != -1L) {
                int secondsRemaining = (int)((this.retryAvailableAt - System.currentTimeMillis()) / 1000L);
                if (secondsRemaining >= 0) {
                    this.retryButton.setLabelText("Reattempt in " + secondsRemaining + " second" + (secondsRemaining == 1 ? "" : "s") + "...");
                } else {
                    this.retryAvailableAt = -1L;
                    this.retryButton.setLabelText("Reattempt");
                }
            }
        }
        this.dismissLabel.K(this.G$src$D$1b2f02a() + this.A() - this.dismissLabel.A() - padding);
        this.dismissLabel.S(this.n() + this.L() + 3.0 - this.dismissLabel.L() - 12.0);
        super.c();
        GuiRenderPrimitives.L(this.G$src$D$1b2f02a() + this.A() - this.dismissLabel.A() - padding, this.n() + this.L() - 10.0, this.dismissLabel.A(), OnlineRegistrationPanel.J.Z);
    }
}
