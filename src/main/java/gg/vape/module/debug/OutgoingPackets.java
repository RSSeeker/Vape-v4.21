package gg.vape.module.debug;

import gg.vape.Vape;
import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPacketSend;
import gg.vape.event.impl.EventPostTick;
import gg.vape.event.impl.EventPreTick;
import gg.vape.event.impl.EventRender2D;
import gg.vape.event.impl.EventWorldChange;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.debug.rotation.RotationDebugSample;
import gg.vape.utils.MathUtil;
import gg.vape.utils.MutableFloatTriple;
import gg.vape.utils.Vec3d;
import gg.vape.utils.datas.DirectionalPosition;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.value.BooleanValue;
import gg.vape.wrapper.impl.C03PacketPlayer;
import gg.vape.wrapper.impl.CPacketPlayerBlockPlacement;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GlStateManager;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.Packet;
import gg.vape.wrapper.impl.UseEntityPacketBridge;
import gg.vape.wrapper.impl.Vec3;
import gg.vape.wrapper.impl.Vec3i;
import gg.vape.wrapper.impl.WorldClient;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.function.ToDoubleFunction;
import org.lwjgl.opengl.GL11;

public class OutgoingPackets
extends Mod {
    private final BooleanValue logInteractions;
    private final ArrayList<RotationDebugSample> samples = new ArrayList();
    private final BooleanValue logPlacements;
    private final BooleanValue logToFile;
    private String sessionId = "";
    private RotationDebugSample currentSample;
    private final ArrayList<RotationDebugSample> pendingFileSamples = new ArrayList();
    private Entity nearbyTarget;

    public OutgoingPackets() {
        super("OutgoingPackets", -256, Category.UTILITY, "");
        this.logToFile = BooleanValue.create(this, "Log to file", false);
        this.logInteractions = BooleanValue.create(this, "Interactions", false);
        this.logPlacements = BooleanValue.create(this, "Placements", false);
        this.addValue(this.logToFile, this.logInteractions, this.logPlacements);
    }

    @Override
    public void onEnable() {
        this.sessionId = Long.toString(System.currentTimeMillis());
        this.resetSamples();
    }

    @EventHandler
    public void onWorldChange(EventWorldChange eventWorldChange) {
        this.resetSamples();
    }

    @EventHandler
    public void onRender2D(EventRender2D eventRender2D) {
        if (this.samples.isEmpty()) {
            return;
        }
        EntityPlayerSP entityPlayerSP = eventRender2D.getThePlayer();
        WorldClient worldClient = eventRender2D.getWorld();
        if (entityPlayerSP.isNull() || worldClient.isNull()) {
            this.resetSamples();
            return;
        }
        GuiRenderPrimitives.Y();
        OpenGlBackendHolder.backend.pushMatrix();
        boolean blendEnabled = OpenGlBackendHolder.backend.isCapabilityEnabled(3042);
        boolean textureEnabled = OpenGlBackendHolder.backend.isCapabilityEnabled(3553);
        if (!blendEnabled) {
            GlStateManager.enableBlend();
        }
        if (textureEnabled) {
            GlStateManager.disableTexture2D();
        }
        double chartWidth = 200.0;
        double chartHeight = 90.0;
        this.drawYawChart(120.0, 20.0, chartWidth, chartHeight);
        this.drawYawDeltaChart(340.0, 20.0, chartWidth, chartHeight);
        this.drawPitchChart(120.0, 130.0, chartWidth, chartHeight);
        this.drawPitchDeltaChart(340.0, 130.0, chartWidth, chartHeight);
        if (textureEnabled) {
            GlStateManager.enableTexture2D();
        }
        if (!blendEnabled) {
            GlStateManager.disableBlend();
        }
        OpenGlBackendHolder.backend.popMatrix();
        GuiRenderPrimitives.D();
    }

    @EventHandler
    public void onTick(EventPreTick eventPreTick) {
        EntityPlayerSP entityPlayerSP = eventPreTick.getThePlayer();
        WorldClient worldClient = eventPreTick.getWorld();
        if (entityPlayerSP.isNull() || worldClient.isNull()) {
            this.resetSamples();
            return;
        }
        this.currentSample = new RotationDebugSample(entityPlayerSP.l());
        this.nearbyTarget = null;
        ArrayList<Object> loadedEntities = new ArrayList<Object>(worldClient.z());
        for (Object rawEntity : loadedEntities) {
            Entity entity = new Entity(rawEntity);
            if (!entity.isInstance(MappedClasses.zm) || entity.equals(entityPlayerSP) || !(entityPlayerSP.getDistanceToEntity(entity) < 6.0f)) continue;
            this.nearbyTarget = entity;
        }
    }

    private void drawPitchDeltaChart(double x, double y, double width, double height) {
        this.drawDeltaChart(x, y, width, height, "Pitch Change/Time", true, sample -> sample.getRotation().getPitch());
    }

    private void drawDeltaChart(double x, double y, double width, double height, String title, boolean wrapDelta, ToDoubleFunction<RotationDebugSample> angleProvider) {
        double padding = 5.0;
        Color background = new Color(0, 0, 0, 200);
        Color border = new Color(255, 255, 255, 100);
        Color lineColor = new Color(0, 255, 0, 255);
        GuiRenderPrimitives.q(x - padding, y - padding, width + padding * 2.0, height + padding * 2.0, 1.0, border, background);
        GuiRenderPrimitives.u(x, y + height / 2.0, x + width, y + height / 2.0, 0.25f, Color.RED);
        if (this.samples.size() < 2) {
            return;
        }
        float maxDelta = 1.0f;
        double previousAngle = angleProvider.applyAsDouble(this.samples.get(0));
        for (RotationDebugSample sample : this.samples) {
            float delta = this.angleDelta(angleProvider.applyAsDouble(sample), previousAngle, wrapDelta);
            maxDelta = Math.max(maxDelta, (float)Math.floor(Math.abs(delta)));
            previousAngle = angleProvider.applyAsDouble(sample);
        }
        Vape.INSTANCE.getFontManager().H(true).v(title + " (+/- " + (int)maxDelta + ")", x + 10.0, y, Color.WHITE);
        double xStep = width / 100.0;
        double currentX = x;
        float minDelta = -maxDelta;
        if (GuiRenderPrimitives.d()) {
            double firstBaseline = angleProvider.applyAsDouble(this.samples.get(0));
            double secondBaseline = angleProvider.applyAsDouble(this.samples.get(1));
            for (int i = 1; i < this.samples.size(); ++i) {
                RotationDebugSample first = this.samples.get(i - 1);
                RotationDebugSample second = this.samples.get(i);
                float firstDelta = this.angleDelta(angleProvider.applyAsDouble(first), firstBaseline, wrapDelta);
                float secondDelta = this.angleDelta(angleProvider.applyAsDouble(second), secondBaseline, wrapDelta);
                double firstY = y + height / 2.0 + (double)(firstDelta / (minDelta - maxDelta)) * height / 2.0;
                double secondY = y + height / 2.0 + (double)(secondDelta / (minDelta - maxDelta)) * height / 2.0;
                GuiRenderPrimitives.u(currentX, firstY, currentX + xStep, secondY, 1.0f, lineColor);
                currentX += xStep;
                firstBaseline = angleProvider.applyAsDouble(first);
                secondBaseline = angleProvider.applyAsDouble(second);
            }
        } else {
            this.beginLegacyLine(lineColor);
            double baseline = angleProvider.applyAsDouble(this.samples.get(0));
            for (RotationDebugSample sample : this.samples) {
                float delta = this.angleDelta(angleProvider.applyAsDouble(sample), baseline, wrapDelta);
                double pointY = y + height / 2.0 + (double)(delta / (minDelta - maxDelta)) * height / 2.0;
                GL11.glVertex2d(currentX, pointY);
                currentX += xStep;
                baseline = angleProvider.applyAsDouble(sample);
            }
            OpenGlBackendHolder.backend.endPrimitive();
        }
    }

    private float angleDelta(double angle, double baseline, boolean wrap) {
        float delta = (float)(angle - baseline);
        return wrap ? MathUtil.wrapAngleTo180(delta) : delta;
    }

    private void beginLegacyLine(Color color) {
        OpenGlBackendHolder.backend.setColor((double)color.getRed() / 255.0, (double)color.getGreen() / 255.0, (double)color.getBlue() / 255.0, (double)color.getAlpha() / 255.0);
        OpenGlBackendHolder.backend.setLineWidth(1.0f);
        OpenGlBackendHolder.backend.enableCapability(2848);
        OpenGlBackendHolder.backend.beginPrimitive(3);
    }

    @EventHandler
    public void onPacketSend(EventPacketSend eventPacketSend) {
        EntityPlayerSP entityPlayerSP = eventPacketSend.getThePlayer();
        WorldClient worldClient = eventPacketSend.getWorld();
        if (entityPlayerSP.isNull() || worldClient.isNull()) {
            this.resetSamples();
            return;
        }
        Packet packet = eventPacketSend.getPacket();
        if (this.currentSample == null) {
            this.currentSample = new RotationDebugSample(Minecraft.thePlayer().l());
        }
        if (packet.isInstance(MappedClasses.qD)) {
            if (this.nearbyTarget != null) {
                this.currentSample.setTarget(new Vec3d(this.nearbyTarget.z(), this.nearbyTarget.N(), this.nearbyTarget.h()));
            }
            this.currentSample.capturePacket(new C03PacketPlayer(packet.getObject()), eventPacketSend.getThePlayer());
        }
        if (packet.isInstance(MappedClasses.VF)) {
            this.currentSample.markAuxiliaryPacketSent();
        }
        if (UseEntityPacketBridge.isUseEntityPacket(packet)) {
            UseEntityPacketBridge interactionPacket = new UseEntityPacketBridge(packet.getObject());
            Entity interactedEntity = interactionPacket.getEntity(eventPacketSend.getWorld());
            if (interactionPacket.isAttack() && interactedEntity.equals(this.nearbyTarget)) {
                this.currentSample.markTargetAttackSent();
            }
            if (this.logInteractions.getEffectiveValue().booleanValue() && interactedEntity != null && interactedEntity.isNotNull()) {
                C02Debug debugEntry = new C02Debug(entityPlayerSP.l(), interactedEntity, interactionPacket.getActionName());
                Vec3 vec3 = interactionPacket.getHitLocation();
                if (vec3 != null && vec3.isNotNull()) {
                    debugEntry.setHitVector(new Vec3d(vec3.getX(), vec3.getY(), vec3.getZ()));
                }
                Vape.debugLog(debugEntry.toString());
            }
        }
        if (this.logPlacements.getEffectiveValue().booleanValue() && packet.isInstance(MappedClasses.YB)) {
            CPacketPlayerBlockPlacement placementPacket = new CPacketPlayerBlockPlacement(packet.getObject());
            Vec3i blockPosition = placementPacket.getBlockPosition();
            MutableFloatTriple facingVector = placementPacket.getFacingVector();
            int side = placementPacket.getPlacedBlockDirection();
            DirectionalPosition directionalPosition = new DirectionalPosition(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ(), side);
            C08Debug rotationDebugState = new C08Debug(entityPlayerSP.l(), directionalPosition, facingVector);
            if (ForgeVersion.MC_1_16_5_ACTUAL.d()) {
                rotationDebugState.setInsideBlock(placementPacket.getBlockHit().isInside());
                if (ForgeVersion.MC_1_21_11.d()) {
                    rotationDebugState.setSequence(placementPacket.getSequence());
                }
            }
            Vape.debugLog(rotationDebugState.toString());
        }
    }

    private void flushSamplesToFile() {
        if (!this.logToFile.getEffectiveValue().booleanValue()) {
            this.pendingFileSamples.clear();
            return;
        }
        String outputPath = "C:\\dump\\outgoing_packets_" + this.sessionId + ".txt";
        File file = new File(outputPath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            FileWriter fileWriter = new FileWriter(outputPath, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (RotationDebugSample rotationDebugSample : this.pendingFileSamples) {
                bufferedWriter.write(rotationDebugSample.toString() + '\n');
            }
            bufferedWriter.flush();
            bufferedWriter.close();
            this.pendingFileSamples.clear();
        }
        catch (Exception exception) {
            Vape.logThrowable(exception);
        }
    }

    @EventHandler
    public void onPostTick(EventPostTick eventPostTick) {
        EntityPlayerSP entityPlayerSP = eventPostTick.getThePlayer();
        WorldClient worldClient = eventPostTick.getWorld();
        if (entityPlayerSP.isNull() || worldClient.isNull()) {
            this.resetSamples();
            return;
        }
        if (this.currentSample != null && this.currentSample.getPosition() != null) {
            while (this.samples.size() >= 100) {
                this.samples.remove(0);
            }
            if (this.logToFile.getEffectiveValue().booleanValue()) {
                if (this.pendingFileSamples.size() >= 100) {
                    this.flushSamplesToFile();
                }
                this.pendingFileSamples.add(this.currentSample);
            }
            this.samples.add(this.currentSample);
        }
    }

    private void drawYawDeltaChart(double x, double y, double width, double height) {
        this.drawDeltaChart(x, y, width, height, "Yaw Change/Time", false, sample -> sample.getRotation().getYaw());
    }

    private void drawYawChart(double x, double y, double width, double height) {
        this.drawAngleChart(x, y, width, height, "Yaw/Time", -180.0f, 180.0f, sample -> sample.getRotation().getYaw());
    }

    @Override
    public void onDisable() {
        this.flushSamplesToFile();
        this.resetSamples();
    }

    private void resetSamples() {
        this.pendingFileSamples.clear();
        this.samples.clear();
        this.currentSample = null;
    }

    private void drawPitchChart(double x, double y, double width, double height) {
        this.drawAngleChart(x, y, width, height, "Pitch/Time", -90.0f, 90.0f, sample -> sample.getRotation().getPitch());
    }

    private void drawAngleChart(double x, double y, double width, double height, String title, float minAngle, float maxAngle, ToDoubleFunction<RotationDebugSample> angleProvider) {
        double padding = 5.0;
        Color background = new Color(0, 0, 0, 200);
        Color border = new Color(255, 255, 255, 100);
        Color lineColor = new Color(0, 255, 0, 255);
        GuiRenderPrimitives.q(x - padding, y - padding, width + padding * 2.0, height + padding * 2.0, 1.0, border, background);
        GuiRenderPrimitives.u(x, y + height / 2.0, x + width, y + height / 2.0, 0.25f, Color.RED);
        Vape.INSTANCE.getFontManager().H(true).v(title + " " + this.samples.size(), x + 10.0, y, Color.WHITE);
        if (this.samples.size() < 2) {
            return;
        }
        double xStep = width / 100.0;
        double currentX = x;
        if (GuiRenderPrimitives.d()) {
            for (int i = 1; i < this.samples.size(); ++i) {
                RotationDebugSample first = this.samples.get(i - 1);
                RotationDebugSample second = this.samples.get(i);
                float firstAngle = MathUtil.wrapAngleTo180((float)angleProvider.applyAsDouble(first));
                float secondAngle = MathUtil.wrapAngleTo180((float)angleProvider.applyAsDouble(second));
                double firstY = y + height - (double)((firstAngle - maxAngle) / (minAngle - maxAngle)) * height;
                double secondY = y + height - (double)((secondAngle - maxAngle) / (minAngle - maxAngle)) * height;
                GuiRenderPrimitives.u(currentX, firstY, currentX + xStep, secondY, 1.0f, lineColor);
                currentX += xStep;
            }
        } else {
            this.beginLegacyLine(lineColor);
            for (RotationDebugSample sample : this.samples) {
                float angle = MathUtil.wrapAngleTo180((float)angleProvider.applyAsDouble(sample));
                double pointY = y + height - (double)((angle - maxAngle) / (minAngle - maxAngle)) * height;
                GL11.glVertex2d(currentX, pointY);
                currentX += xStep;
            }
            OpenGlBackendHolder.backend.endPrimitive();
        }
    }
}
