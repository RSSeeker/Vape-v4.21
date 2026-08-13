package gg.vape.notification;

import gg.vape.notification.Notification;
import gg.vape.notification.NotificationType;
import gg.vape.notification.TextNotificationContent;

public class SettingsSyncStatusNotification
extends Notification {
    private boolean fixedWidth = true;
    private String apiErrorDetail;
    private String ioExceptionMessage;
    private int settingsSaveStatus;
    private String ioExceptionClassName;
    private int profilesSaveStatus;

    public void setProfilesSaveStatus(int status) {
        this.profilesSaveStatus = status;
        this.apiErrorDetail = null;
    }

    private void updateMessage(String message) {
        ((TextNotificationContent)super.getContent()).setText(message);
        this.setTargetX(-this.getWidth());
    }

    @Override
    public double getWidth() {
        return this.fixedWidth ? 100.0 : super.getWidth();
    }

    public int getProfilesSaveStatus() {
        return this.profilesSaveStatus;
    }

    public void setSettingsSaveStatus(int status) {
        this.settingsSaveStatus = status;
    }

    public void setIoExceptionClassName(String className) {
        this.ioExceptionClassName = className;
    }

    public int getSettingsSaveStatus() {
        return this.settingsSaveStatus;
    }

    public void complete() {
        this.fixedWidth = false;
        if (!this.hasSaveError()) {
            this.setDuration(5000L);
            this.updateMessage("Your settings have been successfully saved!");
            return;
        }
        this.setDuration(15000L);
        String message = "Your settings failed to save\nError " + this.settingsSaveStatus + ":" + this.profilesSaveStatus + " ";
        message = message + (this.ioExceptionClassName != null
                ? this.ioExceptionClassName + " " + this.ioExceptionMessage : "1");
        if (this.apiErrorDetail != null) {
            message = message + "\n" + this.apiErrorDetail;
        }
        message = message + "\nPlease contact support";
        this.updateMessage(message);
        super.setType(NotificationType.WARNING);
    }

    public boolean hasSaveError() {
        return this.settingsSaveStatus != 1 || this.profilesSaveStatus != 1;
    }

    public void setApiErrorDetail(String detail) {
        this.apiErrorDetail = detail;
    }

    public void setIoExceptionMessage(String message) {
        this.ioExceptionMessage = message;
    }


    public SettingsSyncStatusNotification() {
        super(NotificationType.INFO, "Saving settings", new TextNotificationContent("Please wait while your settings save..."), 0.0, 0.0, 10000L);
    }
}

