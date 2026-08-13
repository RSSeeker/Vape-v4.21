package gg.vape.event.listener;

import gg.vape.Vape;
import gg.vape.event.EventHandler;
import gg.vape.event.EventListener;
import gg.vape.event.EventPriority;
import gg.vape.event.impl.EventPostRenderTick;
import gg.vape.notification.NotificationType;
import org.lwjgl.opengl.GL11;

public class NotificationAlertEventListener
implements EventListener {
    private boolean errorReported = false;
    private static int obfuscationState;

    public static void setObfuscationState(int state) {
        obfuscationState = state;
    }

    public static int getObfuscationState() {
        return obfuscationState;
    }

    public static int getObfuscationConstant() {
        int state = NotificationAlertEventListener.getObfuscationState();
        return 0;
    }


    static {
        NotificationAlertEventListener.setObfuscationState(77);
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onPostRenderTick(EventPostRenderTick eventPostRenderTick) {
        int errorCode;
        if (!this.errorReported && (errorCode = GL11.glGetError()) != 0) {
            Vape.INSTANCE.getNotificationManager().show("GL Error " + errorCode, "Please contact support and report this error code", NotificationType.ALERT, 10000L);
            this.errorReported = true;
        }
    }
}

