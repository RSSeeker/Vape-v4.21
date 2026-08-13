package gg.vape.friend;

import gg.vape.Vape;
import gg.vape.account.MinecraftSessionWrapper;
import gg.vape.config.Profile;
import gg.vape.event.EventHandler;
import gg.vape.event.EventListener;
import gg.vape.event.impl.EventPreAttack;
import gg.vape.event.impl.EventPrePlayerTick;
import gg.vape.event.impl.EventPreTick;
import gg.vape.event.impl.EventRender3D;
import gg.vape.event.impl.EventWorldChange;
import gg.vape.event.impl.ProfileChangeEvent;
import gg.vape.friend.activity.ActivitySnapshotPayload;
import gg.vape.input.MouseClickRateTracker;
import gg.vape.manager.client.OnlineActivityManager;
import gg.vape.manager.client.OnlineConnectionManager;
import gg.vape.manager.client.OnlineConnectionState;
import gg.vape.manager.client.OnlineSettings;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.blatant.KillAura;
import gg.vape.module.combat.AimAssist;
import gg.vape.module.combat.SilentAura;
import gg.vape.module.render.NameTags;
import gg.vape.protocol.ZeusConnectionManager;
import gg.vape.render.OffscreenRenderContext;
import gg.vape.utils.MutableColor;
import gg.vape.utils.RotationUtil;
import gg.vape.utils.TimerUtil;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ImageRenderer;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.utils.render.RenderUtil;
import gg.vape.utils.render.RenderUtils;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ActiveRenderInfo;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GlStateManager;
import gg.vape.wrapper.impl.GuiScreen;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RenderHelper;
import gg.vape.wrapper.impl.RenderManager;
import gg.vape.wrapper.impl.ServerData;
import gg.vape.wrapper.impl.WorldClient;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

public class OnlineFriendActivityListener
implements EventListener {
    public static OnlineFriendActivityListener INSTANCE = new OnlineFriendActivityListener();
    private TimerUtil serverUpdateTimer = new TimerUtil();
    private String lastServerAddress;
    private final SilentAura silentAura;
    private final AimAssist aimAssist;
    private final KillAura killAura = Vape.INSTANCE.getModManager().getMod(KillAura.class);
    private long manualTargetTimestamp;
    @Nullable
    private EntityPlayer manualTarget;
    private int lastClicksPerSecond;

    @EventHandler
    public void onPreTick(EventPreTick eventPreTick) {
        if (OnlineConnectionManager.INSTANCE.getConnectionState() != OnlineConnectionState.ONLINE) {
            return;
        }
        if (!this.serverUpdateTimer.hasTimeElapsed(1000L)) {
            return;
        }
        this.serverUpdateTimer.reset();
        LocalOnlineFriend localOnlineFriend = Vape.INSTANCE.getOnlineManager().getLocalFriend();
        this.syncMinecraftProfile(localOnlineFriend);
        this.updateServerAddress(localOnlineFriend);
    }

    @EventHandler
    public void onPrePlayerTick(EventPrePlayerTick eventPrePlayerTick) {
        if (OnlineConnectionManager.INSTANCE.getConnectionState() != OnlineConnectionState.ONLINE) {
            return;
        }
        OnlineActivityManager onlineActivityManager = Vape.INSTANCE.getOnlineManager().getActivityManager();
        OnlineFriendActivityState onlineFriendActivityState = Vape.INSTANCE.getOnlineManager().getLocalFriend().getActivityState();
        EntityPlayer entityPlayer = eventPrePlayerTick.getPlayer();
        WorldClient worldClient = eventPrePlayerTick.getWorld();
        if (this.manualTarget != null && System.currentTimeMillis() - this.manualTargetTimestamp >= 10000L) {
            this.setManualTarget(null);
        }
        onlineActivityManager.tickNearbyFriends(entityPlayer, worldClient);
        ActivitySnapshotPayload activitySnapshotPayload = OnlineFriendActivityState.createSnapshot(entityPlayer);
        onlineFriendActivityState.applySnapshot(activitySnapshotPayload);
        int clicksPerSecond = MouseClickRateTracker.getClicksPerSecond();
        onlineFriendActivityState.setClicksPerSecond(clicksPerSecond);
        if (!onlineActivityManager.isEmpty()) {
            if (this.lastClicksPerSecond != clicksPerSecond) {
                ZeusConnectionManager.T().u().H(clicksPerSecond);
            }
            this.lastClicksPerSecond = clicksPerSecond;
            onlineActivityManager.tickLocalSnapshot(activitySnapshotPayload);
        }
    }

    private static int lambda$onRenderWorldLast$2(OnlineFriendActivityState onlineFriendActivityState, OnlineFriendActivityState onlineFriendActivityState2) {
        return onlineFriendActivityState.getFriend().getDisplayName().compareTo(onlineFriendActivityState2.getFriend().getDisplayName());
    }

    public OnlineFriendActivityListener() {
        this.silentAura = Vape.INSTANCE.getModManager().getMod(SilentAura.class);
        this.aimAssist = Vape.INSTANCE.getModManager().getMod(AimAssist.class);
    }

    private void updateServerAddress(OnlineFriend onlineFriend) {
        OnlineActivityManager onlineActivityManager;
        ServerData serverData = Minecraft.H();
        String visibleServerAddress = Minecraft.V() ? "Singleplayer" : (serverData.isNotNull() ? serverData.getServerIp() : null);
        String actualServerAddress = visibleServerAddress;
        if (actualServerAddress != null) {
            if (!OnlineConnectionManager.INSTANCE.getSettings().getShareServer().getEffectiveValue().booleanValue()) {
                visibleServerAddress = null;
            }
            if (onlineFriend.getMinecraftServer() == null && visibleServerAddress != null) {
                onlineFriend.setMinecraftServer(visibleServerAddress);
                ZeusConnectionManager.T().u().a(visibleServerAddress);
            } else if (onlineFriend.getMinecraftServer() != null && visibleServerAddress == null) {
                onlineFriend.setMinecraftServer(visibleServerAddress);
                ZeusConnectionManager.T().u().a(visibleServerAddress);
            }
            if (this.lastServerAddress != null) {
                if (this.lastServerAddress != null) {
                    // empty if block
                }
            } else {
                OnlineActivityManager onlineActivityManager2 = Vape.INSTANCE.getOnlineManager().getActivityManager();
                if (!onlineActivityManager2.isEmpty()) {
                    onlineActivityManager2.resetForWorldChange();
                }
            }
            this.lastServerAddress = actualServerAddress;
            return;
        }
        if (!OnlineConnectionManager.INSTANCE.getSettings().getShareServer().getEffectiveValue().booleanValue()) {
            visibleServerAddress = null;
        }
        if (onlineFriend.getMinecraftServer() == null && visibleServerAddress != null) {
            onlineFriend.setMinecraftServer(visibleServerAddress);
            ZeusConnectionManager.T().u().a(visibleServerAddress);
        } else if (onlineFriend.getMinecraftServer() != null && visibleServerAddress == null) {
            onlineFriend.setMinecraftServer(visibleServerAddress);
            ZeusConnectionManager.T().u().a(visibleServerAddress);
        }
        if (this.lastServerAddress == null) {
            // empty if block
        }
        if (this.lastServerAddress != null && !(onlineActivityManager = Vape.INSTANCE.getOnlineManager().getActivityManager()).isEmpty()) {
            onlineActivityManager.resetForWorldChange();
        }
        this.lastServerAddress = actualServerAddress;
    }

    private static List<OnlineFriendActivityState> lambda$onRenderWorldLast$0(OnlineFriendActivityState onlineFriendActivityState, EntityPlayer entityPlayer, List<OnlineFriendActivityState> list) {
        List<OnlineFriendActivityState> list2 = list != null ? list : new ArrayList<>();
        list2.add(onlineFriendActivityState);
        return list2;
    }

    @EventHandler
    public void onRender3D(EventRender3D eventRender3D) {
        if (OffscreenRenderContext.isRenderingOffscreen()) {
            return;
        }
        PartyState partyState = Vape.INSTANCE.getOnlineManager().getPartyManager().getCurrentParty();
        if (partyState == null) {
            return;
        }
        WorldClient worldClient = eventRender3D.getWorld();
        if (worldClient.isNull()) {
            return;
        }
        LinkedHashMap<UUID, EntityPlayer> linkedHashMap = new LinkedHashMap<UUID, EntityPlayer>();
        for (Object entityHandle : worldClient.X()) {
            EntityPlayer entityPlayer = new EntityPlayer(entityHandle);
            linkedHashMap.put(entityPlayer.X$src$Ljava_util_UUID_$1o5dyg6(), entityPlayer);
        }
        if (linkedHashMap.isEmpty()) {
            return;
        }
        this.renderPlayerIndicators(linkedHashMap.values());
        OnlineSettings onlineSettings = OnlineConnectionManager.INSTANCE.getSettings();
        LinkedHashMap<EntityPlayer, List<OnlineFriendActivityState>> statesByPlayer = new LinkedHashMap<>();
        Collection<OnlineFriendActivityState> activityStates = Vape.INSTANCE.getOnlineManager().getActivityManager().getActivityStates();
        for (OnlineFriendActivityState activityState : activityStates) {
            EntityPlayer entityPlayer;
            if (!partyState.getMembers().contains(activityState.getFriend()) || activityState.equals(Vape.INSTANCE.getOnlineManager().getLocalFriend().getActivityState()) && !onlineSettings.getSelfTargetIndicators().getEffectiveValue().booleanValue() || !activityState.hasTarget() || (entityPlayer = linkedHashMap.get(activityState.getTargetUuid())) == null || entityPlayer.equals(Minecraft.thePlayer())) continue;
            statesByPlayer.compute(entityPlayer, (player, states) -> OnlineFriendActivityListener.lambda$onRenderWorldLast$0(activityState, player, states));
        }
        OnlineFriendActivityState localActivityState;
        EntityPlayer localEntityPlayer;
        if (onlineSettings.getSelfTargetIndicators().getEffectiveValue().booleanValue() && (localActivityState = Vape.INSTANCE.getOnlineManager().getLocalFriend().getActivityState()).hasTarget() && (localEntityPlayer = linkedHashMap.get(localActivityState.getTargetUuid())) != null && !localEntityPlayer.equals(Minecraft.thePlayer())) {
            statesByPlayer.compute(localEntityPlayer, (player, states) -> OnlineFriendActivityListener.lambda$onRenderWorldLast$1(localActivityState, player, states));
        }
        if (statesByPlayer.isEmpty()) {
            return;
        }
        RenderUtils.g();
        double renderX = RenderManager.getInterpolatedRenderPosX();
        double renderY = RenderManager.getInterpolatedRenderPosY();
        double renderZ = RenderManager.getInterpolatedRenderPosZ();
        for (Map.Entry<EntityPlayer, List<OnlineFriendActivityState>> entry : statesByPlayer.entrySet()) {
            EntityPlayer entityPlayer = entry.getKey();
            List<OnlineFriendActivityState> list = entry.getValue();
            list.sort(OnlineFriendActivityListener::lambda$onRenderWorldLast$2);
            float partialTicks = eventRender3D.getTicks();
            double interpolatedX = entityPlayer.M() + (entityPlayer.z() - entityPlayer.M()) * (double)partialTicks;
            double interpolatedY = entityPlayer.W() + (entityPlayer.N() - entityPlayer.W()) * (double)partialTicks;
            double interpolatedZ = entityPlayer.m$src$D$fwnne5() + (entityPlayer.h() - entityPlayer.m$src$D$fwnne5()) * (double)partialTicks;
            GL11.glPushMatrix();
            GL11.glTranslated((double)(interpolatedX - renderX + (double)0.03f), (double)(interpolatedY - renderY + 0.001), (double)(interpolatedZ - renderZ + (double)0.03f));
            GL11.glRotatef((float)90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
            GL11.glScaled((double)0.1, (double)0.1, (double)0.1);
            float baseScale = 10.0f;
            GL11.glPopMatrix();
            if (list.size() <= 0 || !onlineSettings.getTargetIndicators().getEffectiveValue().booleanValue()) continue;
            OnlineFriendActivityState onlineFriendActivityState = list.get(0);
            MutableColor mutableColor = new MutableColor(OnlineFriendColorUtil.getDisplayColor(onlineFriendActivityState.getFriend())).withAlpha(150);
            GuiRenderPrimitives.R(entityPlayer.c(), entityPlayer.A(), entityPlayer.Z(), 50.0f, 0.7f, entityPlayer.Y(), mutableColor);
        }
        RenderUtils.f();
    }

    @EventHandler
    public void onProfileChange(ProfileChangeEvent profileChangeEvent) {
        Profile profile = profileChangeEvent.getPreviousProfile();
        Profile profile2 = profileChangeEvent.getNewProfile();
        if (profile != null && profile.getRemoteMetadata() != null) {
            if (profile2.getRemoteMetadata() != null) {
                ZeusConnectionManager.T().u().p(profile2.getRemoteMetadata().getPublicProfileId());
            } else {
                ZeusConnectionManager.T().u().M();
            }
        } else if (profile2.getRemoteMetadata() != null) {
            ZeusConnectionManager.T().u().p(profile2.getRemoteMetadata().getPublicProfileId());
        }
    }

    private static List<OnlineFriendActivityState> findObserversTargeting(String playerName) {
        ArrayList<OnlineFriendActivityState> matchingStates = new ArrayList<OnlineFriendActivityState>();
        for (OnlineFriendActivityState onlineFriendActivityState : Vape.INSTANCE.getOnlineManager().getActivityManager().getActivityStates()) {
            if (!onlineFriendActivityState.hasTarget() || !onlineFriendActivityState.getTargetName().equals(playerName)) continue;
            matchingStates.add(onlineFriendActivityState);
        }
        OnlineFriendActivityState onlineFriendActivityState = Vape.INSTANCE.getOnlineManager().getLocalFriend().getActivityState();
        if (OnlineConnectionManager.INSTANCE.getSettings().getSelfTargetIndicators().getEffectiveValue().booleanValue() && onlineFriendActivityState.hasTarget() && onlineFriendActivityState.getTargetName().equals(playerName)) {
            matchingStates.add(onlineFriendActivityState);
        }
        return matchingStates;
    }

    @Nullable
    public EntityPlayer getCombatTarget() {
        Wrapper wrapper;
        if (this.killAura.isEnabled() && !this.killAura.targets.isEmpty()) {
            wrapper = Minecraft.currentScreen();
            if (!this.killAura.guiCheck.getEffectiveValue().booleanValue() || ((GuiScreen)wrapper).isNull()) {
                for (EntityLivingBase entityLivingBase : this.killAura.targets) {
                    if (!entityLivingBase.isInstance(MappedClasses.Yl)) continue;
                    return new EntityPlayer(entityLivingBase.getObject());
                }
            }
        }
        if (this.silentAura.isEnabled()) {
            wrapper = this.silentAura.getTarget();
            GuiScreen currentScreen = Minecraft.currentScreen();
            if (currentScreen.isNull() && wrapper != null && wrapper.isInstance(MappedClasses.Yl)) {
                return new EntityPlayer(wrapper);
            }
        }
        EntityLivingBase aimAssistTarget;
        if (this.aimAssist.isEnabled() && ((GuiScreen)(wrapper = Minecraft.currentScreen())).isNull() && (aimAssistTarget = this.aimAssist.getCurrentTarget()) != null && aimAssistTarget.isInstance(MappedClasses.Yl)) {
            return new EntityPlayer(aimAssistTarget.getObject());
        }
        if (this.manualTarget != null && System.currentTimeMillis() - this.manualTargetTimestamp < 5000L) {
            return this.manualTarget;
        }
        return null;
    }

    @EventHandler
    public void onWorldChange(EventWorldChange eventWorldChange) {
        Vape.INSTANCE.getOnlineManager().getActivityManager().resetForWorldChange();
        Vape.INSTANCE.getOnlineManager().getInventoryTracker().reset();
    }

    private void renderTargetIndicators(Entity entity, double verticalOffset, double viewerX, double viewerY, double viewerZ, double renderX, double renderY, double renderZ) {
        double relativeZ;
        double relativeY;
        if (entity.M$src$Z$ff28xj()) {
            return;
        }
        double relativeX = entity.c() - renderX;
        double distance = RotationUtil.y(relativeX, relativeY = entity.A() - renderY, relativeZ = entity.Z() - renderZ, viewerX, viewerY, viewerZ);
        float distanceScale = (float)distance;
        float clampedScale = (double)distanceScale / 5.0 <= 2.0 ? 2.0f : (float)((double)distanceScale / 5.0);
        float worldScale = 0.016666668f * clampedScale;
        RenderUtil.d();
        RenderHelper.e();
        if (ForgeVersion.MC_1_16_5.d()) {
            if (Minecraft.gameSettings().x() == 0) {
                GL11.glTranslated((double)relativeX, (double)(relativeY + (double)entity.Y() + 0.2), (double)relativeZ);
                GL11.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glRotatef((float)(-Minecraft.D().getPlayerViewX()), (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glRotatef((float)(-Minecraft.D().getPlayerViewY()), (float)-1.0f, (float)0.0f, (float)0.0f);
            } else {
                ActiveRenderInfo activeRenderInfo = Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf().l();
                double cameraOffsetX = GuiRenderPrimitives.d() ? 0.0 : RenderManager.getInterpolatedRenderPosX() - activeRenderInfo.o().getX();
                double cameraOffsetY = GuiRenderPrimitives.d() ? 0.0 : RenderManager.getInterpolatedRenderPosY() - activeRenderInfo.o().getY();
                double cameraOffsetZ = GuiRenderPrimitives.d() ? 0.0 : RenderManager.getInterpolatedRenderPosZ() - activeRenderInfo.o().getZ();
                GL11.glTranslated((double)(relativeX + cameraOffsetX), (double)(relativeY + cameraOffsetY + (double)entity.Y() + (double)0.4f), (double)(relativeZ + cameraOffsetZ));
                GL11.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glRotatef((float)(-Minecraft.D().getPlayerViewX()), (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glRotatef((float)Minecraft.D().getPlayerViewY(), (float)1.0f, (float)0.0f, (float)0.0f);
            }
        } else {
            GL11.glTranslated((double)(relativeX + 0.0), (double)(relativeY + (double)entity.Y() + 0.5), (double)relativeZ);
            GL11.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
            if (Minecraft.gameSettings().x() == 2) {
                GL11.glRotatef((float)(-Minecraft.D().getPlayerViewX()), (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glRotatef((float)Minecraft.D().getPlayerViewY(), (float)-1.0f, (float)0.0f, (float)0.0f);
            } else {
                GL11.glRotatef((float)(-Minecraft.D().getPlayerViewX()), (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glRotatef((float)Minecraft.D().getPlayerViewY(), (float)1.0f, (float)0.0f, (float)0.0f);
            }
        }
        GL11.glScalef((float)(-worldScale), (float)(-worldScale), (float)worldScale);
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        float distanceFactor = (float)(distance / 5.0);
        float iconScale = 0.01f * distanceFactor;
        GL11.glTranslated((double)0.0, (double)(-verticalOffset), (double)0.0);
        GL11.glScaled((double)(1.0f / worldScale), (double)(1.0f / worldScale), (double)(-(1.0f / worldScale)));
        GL11.glScaled((double)iconScale, (double)iconScale, (double)iconScale);
        OnlineFriendActivityListener.renderIndicators(entity);
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        RenderUtil.Y();
        Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf().O(1.0);
    }


    private void setManualTarget(@Nullable EntityPlayer entityPlayer) {
        this.manualTarget = entityPlayer;
        this.manualTargetTimestamp = System.currentTimeMillis();
    }

    @EventHandler
    public void onPreAttack(EventPreAttack eventPreAttack) {
        if (Vape.INSTANCE.getOnlineManager().getPartyManager().getCurrentParty() == null) {
            return;
        }
        if (eventPreAttack.getTarget().isInstance(MappedClasses.Yl)) {
            this.setManualTarget(new EntityPlayer(eventPreAttack.getTarget().getObject()));
        }
    }

    public static void renderIndicators(Entity entity) {
        PartyState partyState = Vape.INSTANCE.getOnlineManager().getPartyManager().getCurrentParty();
        if (partyState == null) {
            return;
        }
        if (!entity.isInstance(MappedClasses.Yl)) {
            return;
        }
        EntityPlayer entityPlayer = new EntityPlayer(entity.getObject());
        double indicatorWidth = 10.0;
        double indicatorSpacing = 20.0;
        int verticalOffset = 5;
        OnlineSettings onlineSettings = OnlineConnectionManager.INSTANCE.getSettings();
        OnlineFriendActivityState primaryState;
        if (OnlineConnectionManager.INSTANCE.getSettings().getPartyOverheadIndicator().getEffectiveValue().booleanValue() && (primaryState = Vape.INSTANCE.getOnlineManager().getActivityManager().getActivityStateByMinecraftUsername(entityPlayer.getName())) != null && partyState.getMembers().contains(primaryState.getFriend())) {
            Color color = OnlineFriendColorUtil.getDisplayColor(primaryState.getFriend());
            color = new Color(color.getRed(), color.getGreen(), color.getBlue(), 255);
            GuiRenderPrimitives.V(-6.0, -verticalOffset - 1, 12.0, 1.0, new Color(0, 0, 0, 96));
            GuiRenderPrimitives.V(-5.0, -verticalOffset, 10.0, 1.0, color);
            verticalOffset = (int)((double)verticalOffset + 15.0);
        }
        if (OnlineConnectionManager.INSTANCE.getSettings().getTargetIndicators().getEffectiveValue().booleanValue()) {
            Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf().B(0.0);
            OpenGlBackendHolder.backend.disableCapability(2896);
            GlStateManager.enableAlpha();
            GL11.glBlendFunc((int)770, (int)771);
            List<OnlineFriendActivityState> activityStates = OnlineFriendActivityListener.findObserversTargeting(entityPlayer.getName());
            if (!activityStates.isEmpty() && onlineSettings.getTargetIndicators().getEffectiveValue().booleanValue()) {
                double iconX = -5.0 - (double)activityStates.size() * indicatorSpacing / 2.0 + indicatorWidth - 2.0;
                for (OnlineFriendActivityState onlineFriendActivityState : activityStates) {
                    Color color = OnlineFriendColorUtil.getDisplayColor(onlineFriendActivityState.getFriend());
                    color = new Color(color.getRed(), color.getGreen(), color.getBlue(), 255);
                    ImageRenderer.drawImage(new Color(0, 0, 0, 150), (float)iconX - 1.0f, (float)(-verticalOffset) - 1.0f, "triangle", 16.0f, 16.0f, false);
                    ImageRenderer.drawImage(color, (float)iconX, -verticalOffset, "triangle", 14.0f, 14.0f, false);
                    iconX += indicatorSpacing;
                }
            }
            OpenGlBackendHolder.backend.enableCapability(2896);
            Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf().O(0.0);
        }
    }

    private void syncMinecraftProfile(OnlineFriend onlineFriend) {
        MinecraftSessionWrapper minecraftSessionWrapper = Minecraft.Q$src$Lgg_vape_account_MinecraftSessionWrapper_$1ftnn3u();
        if (minecraftSessionWrapper.isNull()) {
            return;
        }
        UUID minecraftUuid = onlineFriend.getMinecraftUuid();
        if (!onlineFriend.getMinecraftUsername().equals(minecraftSessionWrapper.getUsername()) || minecraftUuid == null || !minecraftUuid.equals(minecraftSessionWrapper.getProfileId())) {
            onlineFriend.updateMinecraftProfile(minecraftSessionWrapper.getProfileId(), minecraftSessionWrapper.getUsername());
            ZeusConnectionManager.T().u().C(minecraftSessionWrapper.getProfileId(), minecraftSessionWrapper.getUsername());
        }
    }

    private void renderPlayerIndicators(Collection<EntityPlayer> players) {
        double renderX = RenderManager.getInterpolatedRenderPosX();
        double renderY = RenderManager.getInterpolatedRenderPosY();
        double renderZ = RenderManager.getInterpolatedRenderPosZ();
        EntityPlayerSP entityPlayerSP = Minecraft.thePlayer();
        double viewerX = entityPlayerSP.c() - renderX;
        double viewerY = entityPlayerSP.A() - renderY;
        double viewerZ = entityPlayerSP.Z() - renderZ;
        double verticalOffset = 7.0;
        NameTags nameTags = Vape.INSTANCE.getModManager().getMod(NameTags.class);
        if (nameTags.isEnabled()) {
            verticalOffset += 7.0;
        }
        for (EntityPlayer entityPlayer : players) {
            this.renderTargetIndicators(entityPlayer, verticalOffset, viewerX, viewerY, viewerZ, renderX, renderY, renderZ);
        }
    }

    private static List<OnlineFriendActivityState> lambda$onRenderWorldLast$1(OnlineFriendActivityState onlineFriendActivityState, EntityPlayer entityPlayer, List<OnlineFriendActivityState> list) {
        List<OnlineFriendActivityState> list2 = list != null ? list : new ArrayList<>();
        list2.add(onlineFriendActivityState);
        return list2;
    }
}
