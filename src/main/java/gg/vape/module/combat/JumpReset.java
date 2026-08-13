package gg.vape.module.combat;

import gg.vape.config.ClientSettings;
import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPacketReceive;
import gg.vape.event.impl.EventPostPlayerTick;
import gg.vape.event.impl.EventPrePlayerTick;
import gg.vape.event.impl.EventPreTick;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.utils.MathUtil;
import gg.vape.utils.RotationUtil;
import gg.vape.value.BooleanValue;
import gg.vape.value.NumberValue;
import gg.vape.value.RandomValue;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.KeyBinding;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.Packet;
import gg.vape.wrapper.impl.PotionRegistry;
import gg.vape.wrapper.impl.SPacketEntityVelocity;
import java.util.Random;

public class JumpReset
extends Mod {
    private final RandomValue accuracy;
    public final NumberValue chance;
    private static final long MODULE_ID = 4814624859532464040L;
    private boolean jumping;
    private boolean shouldJump;
    private final BooleanValue waterCheck;
    private double motionZ;
    private double motionX;
    private boolean waitingForReset;
    private double motionY;
    public final BooleanValue onlyWhenTargeting;
    private final Random random = new Random();

    private boolean shouldReduce() {
        if (this.jumping) {
            return false;
        }
        if (Minecraft.thePlayer().i(PotionRegistry.Z)) {
            return false;
        }
        if (this.isInWater()) {
            return false;
        }
        if (this.onlyWhenTargeting.getEffectiveValue().booleanValue()) {
            EntityPlayerSP player = Minecraft.thePlayer();
            boolean facingYaw = RotationUtil.H(player);
            boolean facingPitch = RotationUtil.F(player);
            if (!facingYaw || !facingPitch) {
                return false;
            }
        }
        int roll = MathUtil.randomExclusiveUpper(this.random, 0, 100);
        return roll >= 100.0 - (Double)this.chance.getValue();
    }

    @EventHandler
    public void onPacketReceive(EventPacketReceive event) {
        if (Minecraft.thePlayer().isNull()) {
            return;
        }
        Packet packet = event.getPacket();
        Packet.n(packet, this::handleVelocityPacket);
    }

    @EventHandler
    public void onPlayerTick(EventPostPlayerTick event) {
        if (this.jumping) {
            KeyBinding jumpKey = Minecraft.gameSettings().O();
            ClientSettings.setPhysicalKeyState(jumpKey, false);
            Minecraft.gameSettings().O().setPressed(false);
            if (ForgeVersion.MC_1_21_4.v()) {
                Minecraft.thePlayer().movementInput().V(false);
            }
            this.jumping = false;
        }
    }

    @EventHandler
    public void onTick(EventPreTick event) {
        EntityPlayerSP player = Minecraft.thePlayer();
        if (player.isNull()) {
            return;
        }
        if (!this.waitingForReset) {
            return;
        }
        double posX = MathUtil.roundToScale(player.t(), 3);
        double posY = MathUtil.roundToScale(player.q(), 3);
        double posZ = MathUtil.roundToScale(player.T(), 3);
        double expectedX = MathUtil.roundToScale(this.motionX, 3);
        double expectedY = MathUtil.roundToScale(this.motionY, 3);
        double expectedZ = MathUtil.roundToScale(this.motionZ, 3);
        if (posX == expectedX && posY == expectedY && posZ == expectedZ) {
            this.motionZ = 0.0;
            this.motionY = 0.0;
            this.motionX = 0.0;
            this.waitingForReset = false;
            if (!Minecraft.gameSettings().O().isKeyDown()) {
                this.shouldJump = true;
            }
        }
    }

    public boolean isInWater() {
        EntityPlayerSP player = Minecraft.thePlayer();
        if (player.isNull()) {
            return true;
        }
        return this.waterCheck.getEffectiveValue() && player.h$src$Z$ftwoya();
    }

    @EventHandler
    public void onTick(EventPrePlayerTick event) {
        if (this.shouldJump) {
            float roll = MathUtil.randomExclusiveUpper(this.random, 0, 100);
            double accuracyChance = this.accuracy.getRandomValue();
            if (roll < 100.0 - accuracyChance) {
                return;
            }
            KeyBinding jumpKey = Minecraft.gameSettings().O();
            ClientSettings.setPhysicalKeyState(jumpKey, true);
            jumpKey.setPressed(true);
            if (ForgeVersion.MC_1_21_4.v()) {
                Minecraft.thePlayer().movementInput().V(true);
            }
            this.jumping = true;
            this.shouldJump = false;
        }
    }

    private void handleVelocityPacket(Packet packet) {
        if (!packet.isInstance(MappedClasses.YX)) {
            return;
        }
        SPacketEntityVelocity velocityPacket = new SPacketEntityVelocity(packet);
        if (velocityPacket.getEntityId() != Minecraft.thePlayer().S()) {
            return;
        }
        boolean unsuitableVelocity = velocityPacket.getMotionX() == 0 && velocityPacket.getMotionZ() == 0
                || velocityPacket.getMotionY() < 0;
        if (!unsuitableVelocity && this.shouldReduce()) {
            this.motionX = velocityPacket.getMotionX() / 8000.0;
            this.motionY = velocityPacket.getMotionY() / 8000.0;
            this.motionZ = velocityPacket.getMotionZ() / 8000.0;
            this.waitingForReset = true;
        }
    }

    @Override
    public String getDetailedSuffix() {
        return this.chance.getDisplayValue() + "%";
    }

    public JumpReset() {
        super("JumpReset", (int)MODULE_ID, Category.COMBAT, "Reduces knockback taken by jumping when hit");
        this.onlyWhenTargeting = BooleanValue.create(this, "Only when targeting", false, "Only reduce knockback while being face to face with opponent");
        this.accuracy = RandomValue.createWithDescription(this, "Accuracy", "#", "%", 0.0, 40.0, 60.0, 100.0, 1.0, "If you will jump, this is the chance that you will actually land a perfect jump reset on time");
        this.chance = NumberValue.createWithDescription(this, "Chance", "#", "%", 0.0, 40.0, 100.0, "Chance of reducing knockback");
        this.waterCheck = BooleanValue.create(this, "Water check", false, "Won't reduce knockback if in water");
        this.addValue(this.chance, this.accuracy, this.onlyWhenTargeting, this.waterCheck);
        this.chance.setMaximumFractionDigits(0);
    }

}

