package gg.vape.module.combat;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gg.vape.Vape;
import gg.vape.config.ConfigJsonUtils;
import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPacketReceive;
import gg.vape.event.impl.EventPrePlayerTick;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.notification.NotificationType;
import gg.vape.unmap.NumberFormat;
import gg.vape.utils.MathUtil;
import gg.vape.utils.RotationUtil;
import gg.vape.utils.Vec3d;
import gg.vape.value.BooleanValue;
import gg.vape.value.NumberValue;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.Packet;
import gg.vape.wrapper.impl.PacketVelocityBridge;
import gg.vape.wrapper.impl.SPacketEntityVelocity;
import java.util.Random;

public class Velocity
extends Mod {
    private final BooleanValue kiteMode;
    private Vec3d pendingVelocity = null;
    private final BooleanValue waterCheck;
    private static final long COLOR_ID = 2393379545127256196L;
    private final NumberValue kiteVertical;
    private final NumberValue vertical;
    private final BooleanValue onlyWhenTargeting = BooleanValue.create(this, "Only when targeting", false, "Only reduce knockback while being face to face with opponent");
    private final NumberValue chance;
    private final NumberValue ticks;
    private int ticksRemaining = 0;
    private final NumberValue horizontal;
    private final NumberValue kiteHorizontal;
    private final BooleanValue alwaysKite;

    private double[] computeReducedPercents() {
        double horizontalPercent = (Double)this.horizontal.getValue();
        double verticalPercent = (Double)this.vertical.getValue();
        Random random = new Random();
        double jitter = random.nextDouble();
        if (horizontalPercent > 0.0) {
            horizontalPercent = Math.min(100.0, horizontalPercent + 5.0 * jitter);
        }
        if (verticalPercent > 0.0) {
            verticalPercent += 5.0 * jitter;
            if (verticalPercent >= 90.0) {
                verticalPercent = 100.0;
            }
        }
        return new double[]{horizontalPercent, verticalPercent};
    }

    private boolean shouldSkip() {
        EntityPlayerSP player = Minecraft.thePlayer();
        if (player.isNull()) {
            return true;
        }
        return this.waterCheck.getEffectiveValue() && player.h$src$Z$ftwoya();
    }

    @Override
    public String getDetailedSuffix() {
        return this.horizontal.getDisplayValue() + "h " + this.vertical.getDisplayValue() + "v";
    }

    public Velocity() {
        super("Velocity", (int)COLOR_ID, Category.UTILITY, "Reduces knockback taken");
        this.waterCheck = BooleanValue.create(this, "Water check", false, "Won't reduce knockback if in water");
        this.chance = NumberValue.createWithDescription(this, "Chance", "#", "%", 0.0, 40.0, 100.0, "Chance of reducing knockback");
        this.kiteMode = BooleanValue.create(this, "Kite mode", false, "Increases knockback while not facing opponent");
        this.alwaysKite = BooleanValue.create(this, "Always Kite", false, "Increase knockback regardless if not facing opponent");
        this.horizontal = NumberValue.create(this, "Horizontal", "#", "%", 0.0, 90.0, 100.0);
        this.vertical = NumberValue.create(this, "Vertical", "#", "%", 0.0, 100.0, 100.0);
        this.ticks = NumberValue.create(this, "Ticks", "#", "", 0.0, 1.0, 10.0, 1.0, "How many ticks to wait before activating\nDoes not delay Kite");
        this.kiteHorizontal = NumberValue.create(this, "Kite horizontal", "#", "%", 100.0, 120.0, 300.0);
        this.kiteVertical = NumberValue.create(this, "Kite vertical", "#", "%", 100.0, 120.0, 300.0);
        this.kiteMode.addDependentValues(this.kiteHorizontal, this.kiteVertical, this.alwaysKite);
        this.addValue(this.chance, this.horizontal, this.vertical, this.ticks, this.kiteMode, this.kiteHorizontal, this.kiteVertical, this.alwaysKite, this.onlyWhenTargeting, this.waterCheck);
        this.chance.setMaximumFractionDigits(0);
        this.vertical.setMaximumFractionDigits(0);
        this.horizontal.setMaximumFractionDigits(0);
    }

    private SPacketEntityVelocity buildVelocityPacket(SPacketEntityVelocity originalPacket,
                                                       double motionX, double motionY, double motionZ) {
        Object packetHandle = Vape.INSTANCE.getMappings().s.createPacket(
                originalPacket.getEntityId(), motionX, motionY, motionZ);
        return new SPacketEntityVelocity(packetHandle);
    }

    private boolean rollChance() {
        int roll = MathUtil.randomExclusiveUpper(new Random(), 0, 100);
        return roll >= 100.0 - (Double)this.chance.getValue();
    }

    private void applyVelocity(Packet packet, EventPacketReceive event) {
        if (packet.isInstance(MappedClasses.qe)) {
            this.applyBridgeVelocity(new PacketVelocityBridge(packet));
        }
        if (packet.isInstance(MappedClasses.YX)) {
            this.applyEntityVelocity(new SPacketEntityVelocity(packet), event);
        }
    }

    private void applyBridgeVelocity(PacketVelocityBridge velocityPacket) {
        EntityPlayerSP player = Minecraft.thePlayer();
        boolean playerFacingTarget = RotationUtil.H(player);
        boolean targetFacingPlayer = RotationUtil.F(player);
        if (this.kiteMode.getEffectiveValue() && this.alwaysKite.getEffectiveValue()) {
            targetFacingPlayer = false;
        }
        if (playerFacingTarget && !targetFacingPlayer && !this.kiteMode.getEffectiveValue() && this.onlyWhenTargeting.getEffectiveValue()) {
            return;
        }
        if (!this.rollChance()) {
            return;
        }

        double motionX = velocityPacket.getMotionX();
        double motionY = velocityPacket.getMotionY();
        double motionZ = velocityPacket.getMotionZ();
        if (playerFacingTarget && !targetFacingPlayer && this.kiteMode.getEffectiveValue()) {
            double horizontalScale = (Double)this.kiteHorizontal.getValue() / 100.0;
            double verticalScale = (Double)this.kiteVertical.getValue() / 100.0;
            velocityPacket.setMotionX((float)this.scaleMotion(motionX, horizontalScale));
            velocityPacket.setMotionY((float)this.scaleMotion(motionY, verticalScale));
            velocityPacket.setMotionZ((float)this.scaleMotion(motionZ, horizontalScale));
            return;
        }
        if ((Double)this.ticks.getValue() > 0.0) {
            boolean hasMeaningfulMotion = Math.abs(motionX) >= 0.005
                    || Math.abs(motionY) >= 0.005 || Math.abs(motionZ) >= 0.005;
            if (hasMeaningfulMotion) {
                this.ticksRemaining = ((Double)this.ticks.getValue()).intValue();
                this.pendingVelocity = new Vec3d(motionX, motionY, motionZ);
            }
            return;
        }
        double[] reductionPercents = this.computeReducedPercents();
        double horizontalScale = reductionPercents[0] / 100.0;
        double verticalScale = reductionPercents[1] / 100.0;
        velocityPacket.setMotionX((float)this.scaleMotion(motionX, horizontalScale));
        velocityPacket.setMotionY((float)this.scaleMotion(motionY, verticalScale));
        velocityPacket.setMotionZ((float)this.scaleMotion(motionZ, horizontalScale));
        if (horizontalScale == 0.0 && verticalScale == 0.0) {
            velocityPacket.setMotionX(0.0f);
            velocityPacket.setMotionY(0.0f);
            velocityPacket.setMotionZ(0.0f);
        }
    }

    private void applyEntityVelocity(SPacketEntityVelocity velocityPacket, EventPacketReceive event) {
        EntityPlayerSP player = Minecraft.thePlayer();
        if (player.isNull() || velocityPacket.getEntityId() != player.S()) {
            return;
        }
        boolean playerFacingTarget = RotationUtil.H(player);
        boolean targetFacingPlayer = RotationUtil.F(player);
        if (this.kiteMode.getEffectiveValue() && this.alwaysKite.getEffectiveValue()) {
            targetFacingPlayer = false;
        }
        if (!playerFacingTarget && !targetFacingPlayer && !this.kiteMode.getEffectiveValue() && this.onlyWhenTargeting.getEffectiveValue()) {
            return;
        }
        if (!this.rollChance()) {
            return;
        }

        double motionX = velocityPacket.getMotionX();
        double motionY = velocityPacket.getMotionY();
        double motionZ = velocityPacket.getMotionZ();
        if (ForgeVersion.MC_1_21_10.d()) {
            motionX /= 8000.0;
            motionY /= 8000.0;
            motionZ /= 8000.0;
        }
        if (playerFacingTarget && !targetFacingPlayer && this.kiteMode.getEffectiveValue()) {
            double horizontalScale = (Double)this.kiteHorizontal.getValue() / 100.0;
            double verticalScale = (Double)this.kiteVertical.getValue() / 100.0;
            this.writeEntityVelocity(velocityPacket, event,
                    motionX * horizontalScale, motionY * verticalScale, motionZ * horizontalScale);
            return;
        }
        if ((Double)this.ticks.getValue() > 0.0) {
            this.ticksRemaining = ((Double)this.ticks.getValue()).intValue();
            this.pendingVelocity = new Vec3d(
                    velocityPacket.getMotionX() / 8000.0,
                    velocityPacket.getMotionY() / 8000.0,
                    velocityPacket.getMotionZ() / 8000.0);
            return;
        }
        double[] reductionPercents = this.computeReducedPercents();
        double horizontalScale = reductionPercents[0] / 100.0;
        double verticalScale = reductionPercents[1] / 100.0;
        this.writeEntityVelocity(velocityPacket, event,
                motionX * horizontalScale, motionY * verticalScale, motionZ * horizontalScale);
        if (horizontalScale == 0.0 && verticalScale == 0.0) {
            this.writeEntityVelocity(velocityPacket, event, 0.0, 0.0, 0.0);
        }
    }

    private void writeEntityVelocity(SPacketEntityVelocity velocityPacket, EventPacketReceive event,
                                     double motionX, double motionY, double motionZ) {
        if (ForgeVersion.MC_26_1.d()) {
            this.replacePacket(event, this.buildVelocityPacket(velocityPacket, motionX, motionY, motionZ));
            return;
        }
        velocityPacket.setMotionX(motionX);
        velocityPacket.setMotionY(motionY);
        velocityPacket.setMotionZ(motionZ);
    }

    private double scaleMotion(double motion, double scale) {
        String motionText = Double.toString(Math.abs(motion));
        String decimalSeparator = motionText.contains(",") ? "," : ".";
        int decimalIndex = motionText.indexOf(decimalSeparator);
        int decimalPlaces = motionText.length() - decimalIndex - 1;
        NumberFormat numberFormat = new NumberFormat(decimalPlaces);
        double scaledMotion = motion * Math.abs(scale);
        if (scale < 0.0) {
            scaledMotion = -scaledMotion;
        }
        return numberFormat.truncate(scaledMotion);
    }

    @Override
    public void loadJson(JsonObject moduleJson) {
        JsonObject profileJson = Vape.INSTANCE.getProfilesManager().getActiveProfile().getEnabledModuleStates();
        JsonArray values = moduleJson.getAsJsonArray("values");
        if (profileJson != null && profileJson.has("Velocity") && values != null) {
            for (JsonElement valueElement : values) {
                JsonObject valueJson = valueElement.getAsJsonObject();
                String valueId = ConfigJsonUtils.getString(valueJson, "id");
                if (!"Mode".equals(valueId)) {
                    continue;
                }
                String modeName = ConfigJsonUtils.getString(valueJson, "value");
                if (modeName != null && modeName.contains("Jump")) {
                    profileJson.remove("Velocity");
                    if (this.isEnabled()) {
                        this.setEnabled(false);
                    }
                    Vape.INSTANCE.getNotificationManager().show("Velocity disabled", "Velocity turned off since JumpReset mode is now a standalone module.", NotificationType.WARNING, 10000L);
                }
                if (modeName == null || !modeName.contains("Lag")) {
                    continue;
                }
                profileJson.remove("Velocity");
                if (this.isEnabled()) {
                    this.setEnabled(false);
                }
                KnockbackDelay knockbackDelay = Vape.INSTANCE.getModManager().getMod(KnockbackDelay.class);
                if (knockbackDelay != null) {
                    knockbackDelay.setEnabled(true);
                }
                Vape.INSTANCE.getNotificationManager().show("Velocity disabled", "Velocity Lag mode is now KnockbackDelay under Network.\nKnockbackDelay has been enabled.", NotificationType.WARNING, 10000L);
            }
        }
        super.loadJson(moduleJson);
    }

    @EventHandler
    public void onPacketReceive(EventPacketReceive event) {
        if (this.shouldSkip()) {
            return;
        }
        try {
            if (event.getPacketInstance() == null) {
                return;
            }
            Packet packet = event.getPacket();
            Packet.n(packet, resolvedPacket -> this.handlePacket(event, resolvedPacket));
        }
        catch (Exception exception) {
            Vape.logThrowable(exception);
        }
    }

    private void handlePacket(EventPacketReceive event, Packet packet) {
        this.applyVelocity(packet, event);
    }

    @EventHandler
    public void onTick(EventPrePlayerTick event) {
        if (this.shouldSkip()) {
            this.pendingVelocity = null;
            this.ticksRemaining = 0;
            return;
        }
        if (this.pendingVelocity != null) {
            if (this.ticksRemaining <= 0) {
                EntityPlayerSP player = Minecraft.thePlayer();
                double[] reductionPercents = this.computeReducedPercents();
                double horizontalScale = reductionPercents[0] / 100.0;
                double verticalScale = reductionPercents[1] / 100.0;
                double currentVerticalMotion = player.q();
                if (this.pendingVelocity.getY() != 0.0 && currentVerticalMotion > 0.0) {
                    player.k(this.scaleMotion(currentVerticalMotion, verticalScale));
                }
                player.r(this.scaleMotion(player.t(), horizontalScale));
                player.i(this.scaleMotion(player.T(), horizontalScale));
                this.pendingVelocity = null;
            }
            --this.ticksRemaining;
        }
    }

    private void replacePacket(EventPacketReceive event, SPacketEntityVelocity velocityPacket) {
        event.setPacket(new Packet(velocityPacket.getObject()));
    }
}
