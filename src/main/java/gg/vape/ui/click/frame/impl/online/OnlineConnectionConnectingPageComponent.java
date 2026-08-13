package gg.vape.ui.click.frame.impl.online;

import gg.vape.manager.client.OnlineConnectionManager;
import gg.vape.ui.click.component.gui.UnderlinedTextLabel;
import gg.vape.ui.click.component.layout.PaddedComponent;
import gg.vape.ui.click.frame.impl.online.OnlineConnectionSettingsPageComponent;
import gg.vape.utils.MutableColor;
import gg.vape.utils.TimerUtil;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

public class OnlineConnectionConnectingPageComponent
extends OnlineConnectionSettingsPageComponent {
    float[] E7;
    UnderlinedTextLabel Ee;
    int Ev = 0;
    private TimerUtil Ei = new TimerUtil();

    @Override
    public void s() {
    }

    public OnlineConnectionConnectingPageComponent() {
        this.E7 = new float[]{0.0f, 0.0f, 0.0f};
        this.Ee = new UnderlinedTextLabel("Cancel", (double)0.8f, OnlineConnectionConnectingPageComponent.J.A, new Color(255, 255, 255, 255));
        this.Ee.addClickListener(OnlineConnectionConnectingPageComponent::lambda$new$0);
        this.Ee.o(50.0);
        this.Ee.Y(10.0);
        this.h(new PaddedComponent(125.0, 0.0, 20.0, 0.0, this.Ee), new Object[0]);
    }

    @Override
    public void c() {
        super.c();
        this.getAlternateFontRenderer(1.0).W("Connecting...", this.G$src$D$1b2f02a() + this.A() / 2.0, this.n() + 52.0, OnlineConnectionConnectingPageComponent.J.A);
        this.Ee.setExplicitWidth(60.0);
        if (this.Ei.hasTimeElapsed(50L)) {
            int n = (this.Ev + 1) % 3;
            for (int i = 0; i < 3; ++i) {
                if (i == this.Ev) {
                    int n2 = i;
                    this.E7[n2] = (float)((double)this.E7[n2] - 0.2);
                    if (!((double)this.E7[i] <= 0.0)) continue;
                    this.E7[i] = 0.0f;
                    continue;
                }
                if (i == n) {
                    int n3 = i;
                    this.E7[n3] = this.E7[n3] + 0.3f;
                    if (!(this.E7[i] >= 1.0f)) continue;
                    this.E7[i] = 1.0f;
                    this.Ev = n;
                    continue;
                }
                this.E7[i] = 0.0f;
            }
            this.Ei.reset();
        }
        double d = this.G$src$D$1b2f02a() + this.A() / 2.0;
        double d2 = this.n() + 35.0;
        float f = 1.0f;
        float f2 = 4.0f;
        float f3 = f2 + this.E7[0] * f;
        float f4 = f2 + this.E7[1] * f;
        float f5 = f2 + this.E7[2] * f;
        float f6 = 1.5f;
        GuiRenderPrimitives.B(d - (double)(f3 / 2.0f) - 10.0, d2 - (double)(f3 / 2.0f), f3, f3, new MutableColor(OnlineConnectionConnectingPageComponent.J.A).withAlpha(200 + (int)(55.0f * this.E7[0])), f6);
        GuiRenderPrimitives.B(d - (double)(f4 / 2.0f), d2 - (double)(f4 / 2.0f), f4, f4, new MutableColor(OnlineConnectionConnectingPageComponent.J.A).withAlpha(200 + (int)(55.0f * this.E7[1])), f6);
        GuiRenderPrimitives.B(d - (double)(f5 / 2.0f) + 10.0, d2 - (double)(f5 / 2.0f), f5, f5, new MutableColor(OnlineConnectionConnectingPageComponent.J.A).withAlpha(200 + (int)(55.0f * this.E7[2])), f6);
    }

    private static void lambda$new$0() {
        OnlineConnectionManager.INSTANCE.cancelConnectionAttempt();
    }

}

