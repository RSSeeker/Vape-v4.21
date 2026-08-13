package gg.vape.notification;

import gg.vape.notification.Notification;
import gg.vape.notification.NotificationType;
import gg.vape.notification.TextNotificationContent;

public class ReusableTextNotification
extends Notification {
    private long defaultDurationMillis;

    public ReusableTextNotification(NotificationType type, String title, String message, long durationMillis) {
        super(type, title, new TextNotificationContent(message), 0.0, 0.0, durationMillis);
        this.defaultDurationMillis = durationMillis;
    }

    public ReusableTextNotification withTitle(String title) {
        this.setTitle(title);
        return this;
    }

    public void setDefaultDuration(long durationMillis) {
        this.defaultDurationMillis = durationMillis;
    }

    public ReusableTextNotification withMessage(String message) {
        ((TextNotificationContent)super.getContent()).setText(message);
        return this;
    }

    public ReusableTextNotification reset() {
        this.setDuration(this.defaultDurationMillis);
        this.setTargetX(-this.getWidth());
        return this;
    }
}
