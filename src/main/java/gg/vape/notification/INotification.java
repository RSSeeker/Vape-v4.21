package gg.vape.notification;

public interface INotification {
    void render();

    void setTargetX(double targetX);

    void dismiss();

    void handleClick(double mouseX, double mouseY);

    double getCurrentY();

    double getTargetY();

    void setTargetY(double targetY);

    void setCurrentX(double currentX);

    void setCurrentY(double currentY);

    double getWidth();

    double getHeight();

    NotificationType getType();

    double getCurrentX();

    boolean shouldRemove();

    double getTargetX();
}
