package gg.vape.module.render;

import gg.vape.config.ClientSettings;
import gg.vape.event.EventBus;
import gg.vape.input.KeyboardInput;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.control.SharedModuleControlClaims;
import gg.vape.module.render.freecam.FreecamController;
import gg.vape.module.render.freecam.FreecamLegacyController;
import gg.vape.module.render.freecam.FreecamModernController;
import gg.vape.rotation.RotationControlClaim;
import gg.vape.rotation.RotationManager;
import gg.vape.utils.PlayerSimulationUtil;
import gg.vape.value.BooleanValue;
import gg.vape.value.NumberValue;
import gg.vape.wrapper.impl.EntityOtherPlayerMP;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.NetworkManager;
import gg.vape.wrapper.impl.Packet;
import gg.vape.wrapper.impl.UseEntityPacketBridge;
import java.util.function.Predicate;

public class Freecam
extends Mod {
    public final BooleanValue moveFakePlayer;
    public double lastReportedX;
    public double lastReportedY;
    private final FreecamController<Freecam> controller;
    public final BooleanValue allowInteracting;
    private static final long MODULE_ID = -3715766461932661926L;
    public int positionUpdateTicks;
    public float lastReportedPitch;
    public NetworkManager networkManager;
    public float lastReportedYaw;
    public final BooleanValue spawnFakePlayer;
    public final NumberValue verticalSpeed;
    public final RotationControlClaim rotationClaim = SharedModuleControlClaims.rotation;
    public double lastReportedZ;
    public boolean initializationPending;
    public boolean disableRequested;
    public EntityOtherPlayerMP fakePlayer;
    public final NumberValue horizontalSpeed;
    public Object bypassPacket;
    public int fakeEntityId;

    public boolean shouldCancelPacket(Packet packet) {
        if (UseEntityPacketBridge.isUseEntityPacket(packet) && !this.allowInteracting.getEffectiveValue().booleanValue()) {
            return true;
        }
        if (!this.allowInteracting.getEffectiveValue().booleanValue()) {
            if (packet.isInstance(MappedClasses.DN)) {
                return true;
            }
            return packet.isInstance(MappedClasses.YB);
        }
        return false;
    }

    public void spawnLegacyFakePlayer() {
        this.fakePlayer = PlayerSimulationUtil.y();
        this.fakePlayer.M(0.0f);
        this.fakePlayer.k$src$V$5315b7(0.0f);
        this.fakePlayer.R(false);
        RotationManager rotationManager = RotationManager.INSTANCE;
        if (rotationManager.hasAdaptiveController()) {
            this.fakePlayer.H(rotationManager.getManagedYaw());
            this.fakePlayer.D(rotationManager.getManagedYaw());
            this.fakePlayer.z(rotationManager.getManagedYaw());
            this.fakePlayer.o(rotationManager.getManagedYaw());
            this.fakePlayer.C(rotationManager.getManagedPitch());
        }
        this.positionUpdateTicks = Minecraft.thePlayer().y$src$I$1ub55de();
        this.lastReportedX = Minecraft.thePlayer().o$src$D$1u5n7bh();
        this.lastReportedY = Minecraft.thePlayer().Q$src$D$1tp5din();
        this.lastReportedZ = Minecraft.thePlayer().X$src$D$1tszxo6();
        this.lastReportedYaw = Minecraft.thePlayer().g();
        this.lastReportedPitch = Minecraft.thePlayer().a$src$F$1txy325();
        if (this.spawnFakePlayer.getEffectiveValue().booleanValue()) {
            this.fakeEntityId = ClientSettings.reserveEntityId();
            this.fakePlayer.Q(this.fakeEntityId);
            Minecraft.theWorld().D(this.fakeEntityId, this.fakePlayer);
        }
    }

    public void removeFakePlayer() {
        if (this.fakeEntityId != 0 && this.fakePlayer != null && Minecraft.theWorld().isNotNull()) {
            Minecraft.theWorld().M(this.fakePlayer);
            ClientSettings.releaseReservedEntityId(this.fakeEntityId);
            this.fakeEntityId = 0;
        }
        this.fakePlayer = null;
    }

    @Override
    public void onDisable() {
        this.controller.onDisable();
        EventBus.getInstance().unregisterListener(this.controller);
    }

    public Freecam() {
        super("Freecam", (int)MODULE_ID, Category.WORLD, "Lets you fly and clip through walls freely\nwithout moving your player server-sided.");
        this.allowInteracting = BooleanValue.create(this, "Allow Interacting", true, "Allows you to interact with blocks and entities while in freecam.");
        this.horizontalSpeed = NumberValue.create(this, "Speed", "#.#", "", 1.0, 3.0, 5.0, 0.1, "Horizontal speed multiplier");
        this.moveFakePlayer = BooleanValue.create(this, "Move Fake", false, "Move your fake entity with your arrow keys.");
        this.spawnFakePlayer = BooleanValue.create(this, "Spawn Fake", true, "Spawns an entity on where your player is server-sided.\nUsing this will allow for simulated physics.");
        this.verticalSpeed = NumberValue.create(this, "Vertical Speed", "#.#", "", 1.0, 3.0, 5.0, 0.1, "Vertical speed multiplier");
        this.spawnFakePlayer.addDependentValues(this.moveFakePlayer);
        this.addValue(this.horizontalSpeed, this.verticalSpeed);
        this.U(this.allowInteracting, ForgeVersion.MC_1_21_11.b());
        this.addValue(this.spawnFakePlayer, this.moveFakePlayer);
        this.rotationClaim.setPriority(this, 100);
        this.controller = ForgeVersion.MC_1_21_11.d() ? new FreecamModernController(this) : new FreecamLegacyController(this);
    }

    public void completeDisable() {
        this.disableRequested = false;
        if (this.fakePlayer != null) {
            this.restorePlayerFromFake();
        }
        super.setEnabled(false, false);
        this.fakePlayer = null;
        this.rotationClaim.release(this);
    }

    @Override
    public void onEnable() {
        this.initializationPending = true;
        this.rotationClaim.acquire(this);
        EventBus.getInstance().registerListener(this.controller, new Predicate[0]);
        this.controller.onEnable();
    }

    public void restorePlayerFromFake() {
        EntityPlayerSP player = Minecraft.thePlayer();
        player.H(this.fakePlayer.z());
        player.u(this.fakePlayer.N());
        player.l(this.fakePlayer.h());
        player.n(this.fakePlayer.f());
        player.w(this.fakePlayer.H());
        player.A(this.fakePlayer.R());
        player.C(this.fakePlayer.z());
        player.L(this.fakePlayer.N());
        player.s(this.fakePlayer.h());
        player.H(this.fakePlayer.J());
        player.C(this.fakePlayer.V());
        player.U(this.fakePlayer.b$src$Z$fqlxe4());
        player.Y(this.fakePlayer.z());
        player.I(this.fakePlayer.N());
        player.z(this.fakePlayer.h());
        player.Z(this.fakePlayer.J());
        player.A(this.fakePlayer.V());
        player.D(this.fakePlayer.j());
        player.l(this.fakePlayer.D());
        player.B(this.fakePlayer.z(), this.fakePlayer.N(), this.fakePlayer.h());
        player.E(this.fakePlayer.t(), this.fakePlayer.q(), this.fakePlayer.T());
        player.E(this.positionUpdateTicks);
        player.z(false);
        if (this.fakeEntityId != 0) {
            Minecraft.theWorld().M(this.fakePlayer);
            ClientSettings.releaseReservedEntityId(this.fakeEntityId);
            this.fakeEntityId = 0;
        }
    }

    public void updateLegacyFakePlayerMovement() {
        this.fakePlayer.M(0.0f);
        this.fakePlayer.k$src$V$5315b7(0.0f);
        if (this.moveFakePlayer.getEffectiveValue().booleanValue() && Minecraft.currentScreen().isNull()) {
            if (KeyboardInput.isKeyDown(38)) {
                this.fakePlayer.M(1.0f);
            } else if (KeyboardInput.isKeyDown(40)) {
                this.fakePlayer.M(-1.0f);
            }
            if (KeyboardInput.isKeyDown(37)) {
                this.fakePlayer.k$src$V$5315b7(1.0f);
            } else if (KeyboardInput.isKeyDown(39)) {
                this.fakePlayer.k$src$V$5315b7(-1.0f);
            }
        }
        PlayerSimulationUtil.t(this.fakePlayer, true);
    }

    @Override
    public void setEnabled(boolean enabled, boolean bypassVisibilityCheck) {
        if (enabled) {
            super.setEnabled(enabled, bypassVisibilityCheck);
        } else {
            this.disableRequested = true;
        }
    }

}
