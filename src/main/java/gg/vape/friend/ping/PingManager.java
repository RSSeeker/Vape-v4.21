package gg.vape.friend.ping;

import gg.vape.Vape;
import gg.vape.event.EventHandler;
import gg.vape.event.EventListener;
import gg.vape.event.EventPriority;
import gg.vape.event.impl.EventRender2D;
import gg.vape.event.impl.EventRender3D;
import gg.vape.friend.OnlineFriend;
import gg.vape.friend.OnlineFriendActivityState;
import gg.vape.friend.ping.EntityPingMarker;
import gg.vape.friend.ping.OnlineFriendPingMarker;
import gg.vape.friend.ping.PingMarker;
import gg.vape.mapping.MappedClasses;
import gg.vape.notification.NotificationType;
import gg.vape.protocol.ZeusConnectionManager;
import gg.vape.protocol.packet.PingResponsePacket;
import gg.vape.utils.RayTraceUtil;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.utils.render.RenderUtils;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.GlStateManager;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RayTraceResult;
import gg.vape.wrapper.impl.WorldClient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

public class PingManager
implements EventListener {
    private long cooldownEndNanos = -1L;
    private final List<PingMarker> markers = new CopyOnWriteArrayList<PingMarker>();
    private int queuedPingRequests = 0;
    private int remainingPingAllowance = (int)OBFUSCATION_SEED;
    private static final long OBFUSCATION_SEED;
    public static PingManager INSTANCE;
    private static final String RATE_LIMIT_MESSAGE;
    private PingMarker pendingMarker;

    @Nullable
    public PingMarker getMarker(OnlineFriend onlineFriend) {
        try {
            return this.getMarkerByUserId(onlineFriend.getUser().getId());
        }
        catch (Exception exception) {
            Vape.logThrowable(exception);
            return null;
        }
    }

    private void lambda$pickPing$1(PingMarker pingMarker, PingResponsePacket pingResponsePacket) {
        this.remainingPingAllowance = pingResponsePacket.getRemainingPingAllowance();
        this.cooldownEndNanos = System.nanoTime() + pingResponsePacket.getCooldownEndNanos();
        if (pingResponsePacket.isRequestAccepted()) {
            this.pendingMarker = pingMarker;
        } else {
            this.removeMarker(pingMarker);
            this.pendingMarker = null;
            this.showRateLimitNotification();
        }
    }


    public void removeMarker(PingMarker pingMarker) {
        this.markers.remove(pingMarker);
    }

    private void lambda$pickPing$0(boolean nearPreviousPosition, PingMarker previousMarker, PingMarker candidateMarker, double[] previousPosition, PingResponsePacket pingResponsePacket) {
        this.remainingPingAllowance = pingResponsePacket.getRemainingPingAllowance();
        this.cooldownEndNanos = System.nanoTime() + pingResponsePacket.getCooldownEndNanos();
        if (pingResponsePacket.isRequestAccepted()) {
            if (nearPreviousPosition) {
                previousMarker.retrigger();
            } else {
                this.removeMarker(previousMarker);
                this.pendingMarker = candidateMarker;
                this.addMarker(candidateMarker);
            }
        } else {
            previousMarker.setWorldPosition(previousPosition);
            this.showRateLimitNotification();
        }
    }

    private void showRateLimitNotification() {
        Vape.INSTANCE.getNotificationManager().show(RATE_LIMIT_MESSAGE, "", NotificationType.FRIENDS_GENERAL, 2000L);
    }

    private List<PingMarker> getMarkersInReverseOrder() {
        ArrayList<PingMarker> reversedMarkers = new ArrayList<PingMarker>(this.markers);
        Collections.reverse(reversedMarkers);
        return reversedMarkers;
    }

    public void addMarker(PingMarker pingMarker) {
        PingMarker pingMarker2 = this.getMarker(pingMarker.getFriend());
        if (pingMarker2 != null) {
            this.markers.remove(pingMarker2);
        }
        this.markers.add(pingMarker);
    }

    public void clear() {
        this.markers.clear();
    }

    @EventHandler(priority=EventPriority.LOW)
    public void onRender3DPickPing(EventRender3D eventRender3D) {
        if (this.pendingMarker != null && this.pendingMarker.isExpired()) {
            this.pendingMarker = null;
        }
        if (this.cooldownEndNanos != -1L && System.nanoTime() > this.cooldownEndNanos) {
            ++this.remainingPingAllowance;
            this.cooldownEndNanos = -1L;
        }
        if (this.queuedPingRequests < 1) {
            return;
        }
        double[] position;
        OnlineFriendPingMarker candidateMarker;
        EntityLivingBase targetedEntity = RayTraceUtil.l((EntityLivingBase)Minecraft.thePlayer(), 1000.0, 1000.0);
        if (targetedEntity != null) {
            Long ownerUserId = null;
            if (targetedEntity.isInstance(MappedClasses.Yl)) {
                EntityPlayer entityPlayer = new EntityPlayer(targetedEntity);
                OnlineFriendActivityState activityState = Vape.INSTANCE.getOnlineManager().getActivityManager().getActivityStateByMinecraftUsername(entityPlayer.getName());
                if (activityState != null) {
                    ownerUserId = activityState.getFriend().getUser().getId();
                }
            }
            candidateMarker = new EntityPingMarker(Vape.INSTANCE.getOnlineManager().getLocalFriend(), ownerUserId, targetedEntity);
            position = candidateMarker.getWorldPosition();
        } else {
            RayTraceResult rayTraceResult = Minecraft.thePlayer().W(1000.0, 1.0f);
            position = new double[]{rayTraceResult.getHitVec().getX(), rayTraceResult.getHitVec().getY(), rayTraceResult.getHitVec().getZ()};
            candidateMarker = new OnlineFriendPingMarker(Vape.INSTANCE.getOnlineManager().getLocalFriend(), position);
        }
        PingMarker previousMarker = this.pendingMarker;
        if (previousMarker != null) {
            if (previousMarker.getClass().equals(candidateMarker.getClass())) {
                boolean nearPreviousPosition = previousMarker.isNear(position);
                double[] previousPosition = previousMarker.getWorldPosition();
                previousMarker.setWorldPosition(position);
                ZeusConnectionManager.T().u().o(candidateMarker.toTargetData(), response -> this.lambda$pickPing$0(nearPreviousPosition, previousMarker, candidateMarker, previousPosition, response));
            } else {
                this.pendingMarker = null;
                this.removeMarker(candidateMarker);
            }
        }
        if (this.pendingMarker == null) {
            this.pendingMarker = candidateMarker;
            this.addMarker(candidateMarker);
            ZeusConnectionManager.T().u().o(candidateMarker.toTargetData(), response -> this.lambda$pickPing$1(candidateMarker, response));
        }
        --this.queuedPingRequests;
    }

    @EventHandler
    public void onRender2D(EventRender2D eventRender2D) {
        if (this.markers.isEmpty()) {
            return;
        }
        OpenGlBackendHolder.backend.pushMatrix();
        GlStateManager.enableAlpha();
        boolean blendEnabled = GL11.glIsEnabled((int)3042);
        RenderUtils.g();
        for (PingMarker pingMarker : this.getMarkersInReverseOrder()) {
            if (pingMarker.getClippedScreenPosition() == null) continue;
            pingMarker.renderScreenMarker();
        }
        RenderUtils.f();
        if (blendEnabled) {
            GlStateManager.enableBlend();
        } else {
            GlStateManager.disableBlend();
        }
        OpenGlBackendHolder.backend.popMatrix();
    }

    @EventHandler
    public void onRender3D(EventRender3D eventRender3D) {
        if (this.markers.isEmpty()) {
            return;
        }
        EntityPlayerSP entityPlayerSP = Minecraft.thePlayer();
        if (entityPlayerSP.isNull()) {
            return;
        }
        WorldClient worldClient = Minecraft.theWorld();
        if (worldClient.isNull()) {
            return;
        }
        for (PingMarker pingMarker : this.getMarkersInReverseOrder()) {
            if (!pingMarker.isTriggered()) {
                pingMarker.trigger();
            }
            if (pingMarker.isExpired()) {
                this.markers.remove(pingMarker);
                continue;
            }
            pingMarker.update(worldClient);
            pingMarker.updateScreenPosition();
            pingMarker.render3D();
        }
    }

    public void onEnable() {
        if (Vape.INSTANCE.getOnlineManager().isOffline()) {
            return;
        }
        if (Minecraft.currentScreen().isNotNull()) {
            return;
        }
        if (this.remainingPingAllowance == 0) {
            this.showRateLimitNotification();
            return;
        }
        ++this.queuedPingRequests;
    }

    static {
        RATE_LIMIT_MESSAGE = "Too many pings!";
        OBFUSCATION_SEED = 6217503732679049226L;
        INSTANCE = new PingManager();
    }

    @Nullable
    public PingMarker getMarkerByUserId(long userId) {
        try {
            for (PingMarker pingMarker : this.markers) {
                if (pingMarker.getFriend().getUser().getId() != userId) continue;
                return pingMarker;
            }
        }
        catch (Exception exception) {
            Vape.logThrowable(exception);
        }
        return null;
    }
}
