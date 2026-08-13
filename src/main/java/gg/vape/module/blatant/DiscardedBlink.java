package gg.vape.module.blatant;

import gg.vape.Vape;
import gg.vape.config.ClientSettings;
import gg.vape.event.EventHandler;
import gg.vape.event.EventPriority;
import gg.vape.event.impl.EventPacketReceive;
import gg.vape.event.impl.EventPacketSend;
import gg.vape.event.impl.EventRender3D;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.unmap.ModeOption;
import gg.vape.unmap.ModeSelection;
import gg.vape.utils.Vec3d;
import gg.vape.utils.network.PacketDispatchTask;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.utils.render.RenderUtil;
import gg.vape.value.BooleanValue;
import gg.vape.value.ModeValue;
import gg.vape.value.NumberValue;
import gg.vape.wrapper.impl.EntityOtherPlayerMP;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GuiScreen;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.Packet;
import gg.vape.wrapper.impl.RenderManager;
import java.awt.Color;
import java.util.List;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import org.lwjgl.opengl.GL11;

public class DiscardedBlink
extends Mod {
    private final ModeOption bidirectionalMode;
    private int fakePlayerEntityId;
    private int packetCount;
    private final ModeValue packetType;
    private final Queue<PacketDispatchTask> queuedPackets;
    private boolean flushPending;
    private final BooleanValue breadcrumbs;
    private final List<Vec3d> breadcrumbPositions = new CopyOnWriteArrayList<Vec3d>();
    private final BooleanValue autoSend;
    private final ModeValue directionMode;
    private final BooleanValue spawnFakePlayer;
    private final NumberValue sendThreshold;
    private final ModeOption allPacketsMode;
    private boolean dispatchingPackets;
    private final ModeOption outgoingOnlyMode = new ModeOption("Outgoing only");
    private static final long MODULE_ID = -1017515784348886721L;
    private EntityOtherPlayerMP fakePlayer;
    private final ModeOption movementOnlyMode;

    @EventHandler(priority=EventPriority.LOW)
    public void onPacketSend(EventPacketSend event) {
        try {
            EntityPlayerSP entityPlayerSP;
            if (event.isCanceled()) {
                return;
            }
            GuiScreen guiScreen = Minecraft.currentScreen();
            if (guiScreen.isInstance(MappedClasses.u5) || guiScreen.isInstance(MappedClasses.D6) || guiScreen.isInstance(MappedClasses.F_) || Minecraft.thePlayer().isNull()) {
                this.flushPending = true;
                this.flushQueuedPackets();
                if (!this.autoSend.getEffectiveValue()) {
                    super.setEnabled(false);
                }
                return;
            }
            if (this.dispatchingPackets) {
                return;
            }
            Packet packet = event.getPacket();
            if (this.isIncomingPacket(packet)) {
                return;
            }
            if (packet.isInstance(MappedClasses.qD)) {
                if (this.isPlayerMoving() && this.packetCount % 5 == 0) {
                    entityPlayerSP = Minecraft.thePlayer();
                    boolean alternateSide = this.packetCount % 2 == 0;
                    int side = alternateSide ? -1 : 1;
                    double yawRadians = Math.toRadians(entityPlayerSP.J());
                    double offsetX = 0.2 * side * Math.cos(yawRadians);
                    double offsetZ = 0.2 * side * Math.sin(yawRadians);
                    this.breadcrumbPositions.add(new Vec3d(
                            entityPlayerSP.z() + offsetX, entityPlayerSP.N(), entityPlayerSP.h() + offsetZ));
                }
            } else if (((ModeSelection)this.packetType.getValue()).equals(this.movementOnlyMode)) {
                return;
            }
            ++this.packetCount;
            if (this.autoSend.getEffectiveValue() && (Double)this.sendThreshold.getValue() > 0.0
                    && this.packetCount >= (Double)this.sendThreshold.getValue()) {
                this.flushPending = true;
                if (this.fakePlayer != null && this.fakePlayer.isNotNull()) {
                    entityPlayerSP = Minecraft.thePlayer();
                    this.fakePlayer.t(entityPlayerSP.z(), ClientSettings.IS_LEGACY_1_7 ? entityPlayerSP.N() : entityPlayerSP.N() - 1.5, entityPlayerSP.h(), entityPlayerSP.J(), entityPlayerSP.V());
                    this.fakePlayer.z(entityPlayerSP.s());
                }
                this.packetCount = 0;
                this.breadcrumbPositions.clear();
            }
            this.queuedPackets.add(new PacketDispatchTask(packet, true, event.getNetworkManager()));
            event.setCancelled(true);
            this.flushQueuedPackets();
        }
        catch (Exception exception) {
            Vape.debugLog("ex1");
            Vape.logThrowable(exception);
        }
    }

    public DiscardedBlink() {
        super("Blink", (int)MODULE_ID, Category.UTILITY, "Chokes packets until disabled.");
        this.bidirectionalMode = new ModeOption("Bi-directional");
        this.directionMode = ModeValue.create((Object)this, "Direction", "Outgoing only - only chokes packets that you're sending\nBi-directional - additionally chokes incoming packets from the server", (ModeSelection)this.outgoingOnlyMode, this.outgoingOnlyMode, this.bidirectionalMode);
        this.allPacketsMode = new ModeOption("All");
        this.movementOnlyMode = new ModeOption("Movement only");
        this.packetType = ModeValue.create((Object)this, "Type", "All - chokes all packets of all types\nMovement only - Only chokes movement related packets", (ModeSelection)this.allPacketsMode, this.allPacketsMode, this.movementOnlyMode);
        this.breadcrumbs = BooleanValue.create(this, "Breadcrumbs", false, "Drops breadcrumbs in case you forgot where you came from.");
        this.spawnFakePlayer = BooleanValue.create(this, "Spawn fake", false, "Spawns a fake player where you started/are on the servers side.");
        this.autoSend = BooleanValue.create(this, "Auto send", false, "Automatically sends packets after a limit is reached");
        this.sendThreshold = NumberValue.create(this, "Send threshold", "#", "", 0.0, 50.0, 100.0, 1.0, "Allows you to choke your packets for a custom set limit.\nAfter the limit is reached, all packets will be sent.\nNOTE: 0 = No Limit");
        this.queuedPackets = new ConcurrentLinkedQueue<PacketDispatchTask>();
        this.autoSend.addDependentValues(this.sendThreshold);
        this.addValue(this.directionMode, this.packetType, this.breadcrumbs,
                this.spawnFakePlayer, this.autoSend, this.sendThreshold);
    }

    @Override
    public void onEnable() {
        this.resetState();
        EntityPlayerSP player = Minecraft.thePlayer();
        if (this.spawnFakePlayer.getEffectiveValue()) {
            this.fakePlayer = EntityOtherPlayerMP.create(
                    Minecraft.theWorld(), player.c$src$Lgg_vape_wrapper_impl_GameProfile_$ir8937());
            this.fakePlayer.M(player, true);
            if (ForgeVersion.MC_1_17.d()) {
                this.fakePlayer.Q(-420);
                this.fakePlayer.y(UUID.randomUUID());
            }
            this.fakePlayer.z(player.s());
            this.fakePlayerEntityId = ClientSettings.reserveEntityId();
            Minecraft.theWorld().D(this.fakePlayerEntityId, this.fakePlayer);
        }
    }

    private boolean isPlayerMoving() {
        EntityPlayerSP player = Minecraft.thePlayer();
        return player.t() != 0.0 || player.T() != 0.0;
    }

    @Override
    public String getSimpleSuffix() {
        if (this.packetCount == 0) {
            return "";
        }
        return String.valueOf(this.packetCount);
    }

    @EventHandler
    public void onPacketReceive(EventPacketReceive event) {
        try {
            Packet packet = event.getPacket();
            if (packet.isInstance(MappedClasses.zw)) {
                this.flushPending = true;
                this.flushQueuedPackets();
                if (!this.autoSend.getEffectiveValue()) {
                    super.setEnabled(false);
                }
                return;
            }
            if (((ModeSelection)this.directionMode.getValue()).equals(this.outgoingOnlyMode)) {
                return;
            }
            if (this.dispatchingPackets) {
                return;
            }
            ++this.packetCount;
            if (this.autoSend.getEffectiveValue() && (Double)this.sendThreshold.getValue() > 0.0
                    && this.packetCount >= (Double)this.sendThreshold.getValue()) {
                this.flushPending = true;
                this.packetCount = 0;
                this.breadcrumbPositions.clear();
            }
            this.queuedPackets.add(new PacketDispatchTask(packet, false, event.getNetworkManager()));
            event.setCancelled(true);
        }
        catch (Exception exception) {
            Vape.debugLog("ex2");
            Vape.logThrowable(exception);
        }
    }

    @EventHandler
    public void onRender3D(EventRender3D event) {
        if (!this.breadcrumbs.getEffectiveValue() || this.breadcrumbPositions.isEmpty()) {
            return;
        }
        event.getEntityRenderer().B(1.0);
        RenderUtil.d();
        if (!GuiRenderPrimitives.d()) {
            OpenGlBackendHolder.backend.enableCapability(3042);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glLineWidth((float)1.5f);
            OpenGlBackendHolder.backend.disableCapability(3553);
            OpenGlBackendHolder.backend.enableCapability(2848);
            OpenGlBackendHolder.backend.disableCapability(2929);
            GL11.glDepthMask((boolean)false);
        }
        double renderX = RenderManager.getInterpolatedRenderPosX();
        double renderY = RenderManager.getInterpolatedRenderPosY();
        double renderZ = RenderManager.getInterpolatedRenderPosZ();
        boolean firstBreadcrumb = true;
        for (Vec3d position : this.breadcrumbPositions) {
            Color color = Color.RED;
            if (firstBreadcrumb) {
                color = Color.YELLOW;
                firstBreadcrumb = false;
            }
            RenderUtil.u(position.getX() - 0.1, position.getY(), position.getZ() - 0.1,
                    0.2, 0.0, 0.2, 1.0, Color.BLACK, color, renderX, renderY, renderZ);
        }
        if (!GuiRenderPrimitives.d()) {
            GL11.glDepthMask((boolean)true);
            OpenGlBackendHolder.backend.enableCapability(2929);
            OpenGlBackendHolder.backend.enableCapability(3553);
            OpenGlBackendHolder.backend.disableCapability(2848);
            OpenGlBackendHolder.backend.disableCapability(3042);
        }
        RenderUtil.Y();
        event.getEntityRenderer().O(1.0);
    }

    private void resetState() {
        this.packetCount = 0;
        this.queuedPackets.clear();
        this.breadcrumbPositions.clear();
    }

    private void flushQueuedPackets() {
        if (this.flushPending) {
            this.dispatchingPackets = true;
            while (!this.queuedPackets.isEmpty()) {
                this.queuedPackets.poll().t();
            }
            this.dispatchingPackets = false;
            this.flushPending = false;
        }
    }

    @Override
    public void onDisable() {
        if (Minecraft.thePlayer().isNull() || Minecraft.theWorld().isNull()) {
            this.breadcrumbPositions.clear();
            this.fakePlayer = null;
            this.packetCount = 0;
        }
        if (this.fakePlayer != null && Minecraft.theWorld().V(this.fakePlayerEntityId).isNotNull()) {
            Minecraft.theWorld().M(this.fakePlayer);
        }
        this.fakePlayer = null;
    }

    @Override
    public void onBeforeDisable() {
        this.flushPending = true;
        this.flushQueuedPackets();
    }

    private boolean isIncomingPacket(Packet packet) {
        return packet.getObject().getClass().toString().contains("play.server") || packet.getObject().toString().contains("SPacket");
    }
}
