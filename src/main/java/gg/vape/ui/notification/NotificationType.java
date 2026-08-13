package gg.vape.ui.notification;

import java.awt.Color;

public enum NotificationType {
    FRIENDINVITESENT("Friend invite sent", new Color(6, 161, 126), new Color(4, 106, 83)),
    SUCCESS("Success message", new Color(6, 161, 126), new Color(4, 106, 83)),
    WARNING("Warning message", new Color(236, 129, 44), new Color(156, 85, 29)),
    ERROR("Error Message", new Color(240, 100, 100), new Color(165, 33, 37));

    private static boolean F;
    private static final /* synthetic */ NotificationType[] u;
    Color b;
    Color D;
    int M;
    String p;

    public void p(String string) {
        this.p = string;
    }

    public void f(Color color) {
        this.b = color;
    }

    public static boolean u() {
        return F;
    }

    static {
        if (!NotificationType.i()) {
            NotificationType.S(true);
        }
        String[] stringArray = new String[]{"Error Message", "SUCCESS", "FRIENDINVITESENT", "ERROR", "Warning message", "Success message", "WARNING", "Friend invite sent"};




        u = new NotificationType[]{FRIENDINVITESENT, SUCCESS, WARNING, ERROR};
    }

    private NotificationType(String string2, Color color, Color color2, int n2) {
        this.p = string2;
        this.D = color;
        this.b = color2;
        this.M = n2;
    }

    private NotificationType(String string2, Color color, Color color2) {
        this.p = string2;
        this.D = color;
        this.b = color2;
        this.M = 3000;
    }


    public void p(int n) {
        this.M = n;
    }

    public static boolean i() {
        boolean bl = NotificationType.u();
        return !bl;
    }

    private NotificationType(Color color, Color color2, int n2) {
        this.p = "";
        this.D = color;
        this.b = color2;
        this.M = n2;
    }

    private NotificationType(Color color, Color color2) {
        this.p = "";
        this.D = color;
        this.b = color2;
        this.M = 3000;
    }

    public int G() {
        return this.M;
    }

    public void V(Color color) {
        this.D = color;
    }

    public Color S() {
        return this.b;
    }

    private NotificationType(String string2, Color color) {
        this.p = string2;
        this.D = color;
        this.b = color.darker();
        this.M = 3000;
    }

    public String K() {
        return this.p;
    }

    public Color s() {
        return this.D;
    }

    public static void S(boolean bl) {
        F = bl;
    }
}

