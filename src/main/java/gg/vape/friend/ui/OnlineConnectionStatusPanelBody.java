package gg.vape.friend.ui;

import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.component.gui.TextButton;
import gg.vape.ui.click.component.layout.PaddedComponent;
import gg.vape.ui.click.frame.impl.online.OnlineConnectionSettingsFrame;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ImageRenderer;
import java.awt.Color;

public class OnlineConnectionStatusPanelBody
extends PanelComponent {
    private static boolean reservedState;


    private static void openConnectionSettings() {
        OnlineConnectionSettingsFrame.getInstance().e(true);
    }

    public static void setReservedState(boolean state) {
        reservedState = state;
    }

    public static boolean getReservedState() {
        return reservedState;
    }

    public static boolean isAlwaysEnabled() {
        boolean reserved = OnlineConnectionStatusPanelBody.getReservedState();
        return true;
    }

    static {
        OnlineConnectionStatusPanelBody.setReservedState(false);
    }

    @Override
    public void c() {
        super.c();
        GuiRenderPrimitives.V((float)((double)((float)this.G$src$D$1b2f02a()) + this.A() / 2.0 - 10.0), (float)(this.n() - 2.0), 20.0, 1.0, OnlineConnectionStatusPanelBody.J.l);
        ImageRenderer.drawResWithShadow(Color.WHITE, (float)((double)((float)this.G$src$D$1b2f02a()) + this.A() / 2.0 - 8.0), (float)this.n(), "avatar", 0.5f, false);
        this.getAlternateFontRenderer(0.9).W("Sign in required", this.G$src$D$1b2f02a() + this.A() / 2.0, this.n() + this.L() - 35.0, Color.WHITE);
    }

    public OnlineConnectionStatusPanelBody() {
        super(64.0, 60.0);
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        TextButton signInButton = new TextButton("SIGN IN", 0.7, OnlineConnectionStatusPanelBody.J.B, OnlineConnectionStatusPanelBody.J.O, null, 2.0f, 0.0f, 50.0, 14.0);
        signInButton.setDeriveTextColorFromBackground(false);
        signInButton.setNormalTextColor(Color.WHITE);
        signInButton.setUseAlternateFont(true);
        this.addChildren(new SpacerComponent(this.A(), this.L() - signInButton.L()), new PaddedComponent(7.0, 0.0, signInButton));
        signInButton.addClickListener(OnlineConnectionStatusPanelBody::openConnectionSettings);
    }
}

