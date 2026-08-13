package gg.vape.ui.notification;

import gg.vape.ui.notification.NotificationType;
import java.awt.Color;

public class NotificationMessage {
    int Q;
    Color I;
    Color s;
    String p;

    public Color t() {
        return this.I;
    }

    public void D(Color color) {
        this.I = color;
    }

    public void P(Color color) {
        this.s = color;
    }

    public void c(String string) {
        this.p = string;
    }

    public void T(int n) {
        this.Q = n;
    }

    public String S() {
        return this.p;
    }

    public Color Y() {
        return this.s;
    }

    public NotificationMessage(NotificationType yh_02, String string) {
        this(yh_02);
        this.c(string);
    }

    public NotificationMessage(String string, Color color, Color color2, int n) {
        this.p = string;
        this.I = color;
        this.s = color2;
        this.Q = n;
    }

    public int c() {
        return this.Q;
    }

    public NotificationMessage(NotificationType yh_02) {
        this(yh_02.K(), yh_02.s(), yh_02.S(), yh_02.G());
    }
}

