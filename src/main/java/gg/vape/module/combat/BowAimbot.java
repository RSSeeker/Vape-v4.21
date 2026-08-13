package gg.vape.module.combat;

import gg.vape.Vape;
import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventEntityJoinWorld;
import gg.vape.event.impl.EventPreTick;
import gg.vape.input.InputEventDispatcher;
import gg.vape.input.KeyBindingInputState;
import gg.vape.input.MouseButtonInputLock;
import gg.vape.input.MovementInputLock;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.blatant.InvWalk;
import gg.vape.module.control.SharedModuleControlClaims;
import gg.vape.rotation.AdaptiveRotationController;
import gg.vape.rotation.FixedRotationController;
import gg.vape.rotation.RotationControlClaim;
import gg.vape.rotation.RotationManager;
import gg.vape.utils.MathUtil;
import gg.vape.utils.RotationUtil;
import gg.vape.utils.Vec3d;
import gg.vape.value.BooleanValue;
import gg.vape.value.NumberValue;
import gg.vape.wrapper.impl.AxisAlignedBB;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityArrow;
import gg.vape.wrapper.impl.EntityArrowBridge;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GameSettings;
import gg.vape.wrapper.impl.KeyBinding;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RayTraceResult;
import gg.vape.wrapper.impl.RayTraceResult_type;
import gg.vape.wrapper.impl.Vec3;
import gg.vape.wrapper.impl.World;
import gg.vape.wrapper.impl.WorldClient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BowAimbot
extends Mod {
    private int swingTicks;
    private EntityArrowBridge targetArrow;
    private final HashMap<Integer, Entity> arrowOwners;
    private final NumberValue angleLimit = NumberValue.create((Object)this, "Angle limit", "#", "", 1.0, 45.0, 180.0, 5.0);
    private boolean attackKeyPending;
    private FixedRotationController rotationController;
    public BooleanValue stopMovement;
    private final RotationControlClaim rotationClaim;
    private final MovementInputLock movementLock;
    public final NumberValue aimSpeed = NumberValue.create((Object)this, "Aim speed", "#.#", "", 1.0, 9.0, 10.0, 0.1);
    private final BooleanValue silentAim;
    private static final long MODULE_ID = -321105662139610036L;
    private final MouseButtonInputLock mouseButtonLock;
    private boolean attackTriggered;
    public BooleanValue moveOnFinish;
    private boolean toggledOff;

    private void releaseRotation() {
        if (this.rotationController != null && RotationManager.INSTANCE.getActiveController() == this.rotationController) {
            this.rotationController.setSpeed(Math.min(((Double)this.aimSpeed.getValue()).floatValue() * 0.6f, 6.0f));
            this.rotationController.setRandomizeMovement(false);
            this.rotationController.setCubicAcceleration(false);
            this.rotationController.setAngleBasedAcceleration(true);
            RotationManager.INSTANCE.releaseController(this.rotationController);
        }
    }

    public BowAimbot() {
        super("AntiFireball", (int)MODULE_ID, Category.UTILITY, "Aims and swings at a fireball to reflect it.\nBy default will only attack fireballs heading towards you.");
        this.stopMovement = BooleanValue.create(this, "Stop movement", false, "Forces you to stand when attacking fireball");
        this.moveOnFinish = BooleanValue.create(this, "Move on finish", false, "Will repress your movement keys after attacking");
        this.silentAim = BooleanValue.create(this, "Silent aim", false, "Uses Silent Aim system");
        this.arrowOwners = new HashMap();
        this.rotationClaim = SharedModuleControlClaims.rotation;
        this.mouseButtonLock = SharedModuleControlClaims.mouseButtons;
        this.movementLock = SharedModuleControlClaims.movementInput;
        this.stopMovement.addDependentValues(this.moveOnFinish);
        this.addValue(this.angleLimit, this.aimSpeed, this.stopMovement, this.moveOnFinish, this.silentAim);
        this.rotationClaim.setPriority(this, 7);
    }

    private boolean isValidArrowOwner(EntityPlayerSP player, Entity owner) {
        if (owner != null && owner.isNotNull() && owner.isInstance(MappedClasses.Yl)) {
            EntityPlayer ownerPlayer = new EntityPlayer(owner);
            return !Vape.INSTANCE.getClientSettings().isTeammate(player, ownerPlayer)
                    && !Vape.INSTANCE.getFriendManager().isFriend(ownerPlayer);
        }
        return true;
    }

    private static boolean isOwnerEntryStale(World world, Map.Entry<Integer, Entity> entry) {
        Entity owner = entry.getValue();
        return owner != null && owner.M$src$Z$ff28xj() || world.V(entry.getKey()).isNull();
    }

    private void suppressMovement() {
        if (this.stopMovement.getEffectiveValue().booleanValue()) {
            this.movementLock.lock();
            GameSettings gameSettings = Minecraft.gameSettings();
            gameSettings.Y().setPressed(false);
            gameSettings.s().setPressed(false);
            gameSettings.x$src$Lgg_vape_wrapper_impl_KeyBinding_$1cf7isg().setPressed(false);
            gameSettings.g$src$Lgg_vape_wrapper_impl_KeyBinding_$qqn5n3().setPressed(false);
        }
    }

    private double predictArrowDistance(EntityPlayerSP player, WorldClient world, EntityArrow arrow) {
        double closestDistance = 999.0;
        double positionX = arrow.z();
        double positionY = arrow.N();
        double positionZ = arrow.h();
        double velocityX = arrow.t();
        double velocityY = arrow.q();
        double velocityZ = arrow.T();
        double accelerationX = arrow.L();
        double accelerationY = arrow.X$src$D$xt9pjp();
        double accelerationZ = arrow.o();
        double drag = 0.95f;
        for (int predictionTick = 0; predictionTick < 10; ++predictionTick) {
            float width = arrow.Y();
            float height = arrow.f$src$F$fst3ac();
            Vec3 segmentStart = Vec3.create(positionX, positionY, positionZ);
            Vec3 segmentEnd = Vec3.create(
                    positionX + velocityX, positionY + velocityY, positionZ + velocityZ);
            RayTraceResult collision = world.K(
                    segmentStart, segmentEnd, false, true, false, arrow);
            if (collision.isBlockHit()) {
                segmentEnd = Vec3.create(collision.getHitVec().getX(),
                        collision.getHitVec().getY(), collision.getHitVec().getZ());
            }
            AxisAlignedBB arrowBox = AxisAlignedBB.create(
                    positionX - width, positionY, positionZ - width,
                    positionX + width, positionY + height, positionZ + width);
            List<?> collisionCandidates = world.F(arrow,
                    arrowBox.addCoord(velocityX, velocityY, velocityZ).expand(1.0, 1.0, 1.0));
            double nearestInterceptDistance = 0.0;
            for (Object underlyingEntity : collisionCandidates) {
                Entity entity = new Entity(underlyingEntity);
                if (!entity.isInstance(MappedClasses.zm) || !entity.n$src$Z$fx7gig()) {
                    continue;
                }
                arrowBox = entity.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl()
                        .expand(0.3f, 0.3f, 0.3f);
                RayTraceResult entityIntercept = arrowBox.calculateIntercept(segmentStart, segmentEnd);
                if (ForgeVersion.MC_1_16_5.d()) {
                    if (!entityIntercept.isNotNull()
                            || !entityIntercept.getTypeOfHit().equals(RayTraceResult_type.miss())) {
                        continue;
                    }
                    double interceptDistance = segmentStart.distanceTo(entityIntercept.getHitVec());
                    if (interceptDistance >= nearestInterceptDistance && nearestInterceptDistance != 0.0) {
                        continue;
                    }
                    nearestInterceptDistance = interceptDistance;
                    entityIntercept.setEntity(entity);
                    collision = entityIntercept;
                    continue;
                }
                if (!entityIntercept.isNotNull()) {
                    continue;
                }
                double interceptDistance = segmentStart.distanceTo(entityIntercept.getHitVec());
                if (interceptDistance >= nearestInterceptDistance && nearestInterceptDistance != 0.0) {
                    continue;
                }
                nearestInterceptDistance = interceptDistance;
                entityIntercept.setEntity(entity);
                collision = entityIntercept;
            }
            if (collision.isNotNull() && !collision.getTypeOfHit().equals(RayTraceResult_type.miss())) {
                if (collision.isEntityHit() && collision.getEntity().equals(player)) {
                    return 0.0;
                }
                return player.i(collision.getHitVec().getX(),
                        collision.getHitVec().getY(), collision.getHitVec().getZ());
            }
            positionX += velocityX;
            positionY += velocityY;
            positionZ += velocityZ;
            Vec3d nearestArrowPoint = RotationUtil.T(
                    player, arrowBox.expand(1.0, 1.0, 1.0), 0.0, 0.0, 0.0);
            double distance = player.i(nearestArrowPoint.getX(), nearestArrowPoint.getY(), nearestArrowPoint.getZ());
            if (distance <= 4.0 && distance < closestDistance) {
                closestDistance = distance;
            }
            velocityX = (velocityX + accelerationX) * drag;
            velocityY = (velocityY + accelerationY) * drag;
            velocityZ = (velocityZ + accelerationZ) * drag;
        }
        return closestDistance;
    }

    private boolean canAcquireTarget() {
        if (!InputEventDispatcher.getInstance().getFocusState().isFocused()) {
            return false;
        }
        if (this.isBlockedByScreen()) {
            return false;
        }
        if (this.rotationClaim.isBlockedFor(this)) {
            return false;
        }
        return !this.toggledOff;
    }

    private void restoreMovement() {
        if (this.stopMovement.getEffectiveValue().booleanValue()) {
            this.movementLock.unlock();
            if (this.moveOnFinish.getEffectiveValue().booleanValue()) {
                GameSettings gameSettings = Minecraft.gameSettings();
                KeyBinding[] movementKeys = new KeyBinding[]{gameSettings.Y(), gameSettings.g$src$Lgg_vape_wrapper_impl_KeyBinding_$qqn5n3(), gameSettings.x$src$Lgg_vape_wrapper_impl_KeyBinding_$1cf7isg(), gameSettings.s()};
                boolean screenBlocksInput = this.isBlockedByScreen();
                for (KeyBinding keyBinding : movementKeys) {
                    if (screenBlocksInput) {
                        keyBinding.setPressed(false);
                        continue;
                    }
                    keyBinding.Z();
                }
            }
        }
    }

    @EventHandler
    public void onEntityJoinWorld(EventEntityJoinWorld event) {
        if (event.getEntity().isInstance(MappedClasses.uf)) {
            EntityArrowBridge arrow = new EntityArrowBridge(event.getEntity());
            Entity closestOwner = null;
            double closestOwnerDistance = Double.MAX_VALUE;
            AxisAlignedBB ownerSearchBox = arrow.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl()
                    .expand(2.0, 2.0, 2.0);
            List<?> nearbyEntities = event.getWorld().F(arrow, ownerSearchBox);
            for (Object underlyingEntity : nearbyEntities) {
                Entity candidateOwner = new Entity(underlyingEntity);
                double ownerDistance = candidateOwner.getDistanceToEntity(arrow);
                if (candidateOwner.isInstance(MappedClasses.Yl) && ownerDistance < closestOwnerDistance) {
                    closestOwner = candidateOwner;
                    closestOwnerDistance = ownerDistance;
                }
            }
            if (this.isValidArrowOwner(Minecraft.thePlayer(), closestOwner)) {
                this.arrowOwners.put(arrow.S(), closestOwner);
            }
        }
    }

    private void releaseCompletedRotationClaim() {
        if (this.rotationController != null && !this.rotationController.shouldRetainAfterCompletion() && this.rotationController.isComplete()) {
            this.rotationController = null;
        }
        if (this.rotationController == null) {
            this.rotationClaim.release(this);
        }
    }

    @Override
    public void setEnabled(boolean enabled, boolean bypassVisibilityCheck) {
        if (!enabled && this.rotationController instanceof AdaptiveRotationController) {
            this.toggledOff = !this.toggledOff;
        } else {
            this.toggledOff = false;
            super.setEnabled(enabled, bypassVisibilityCheck);
        }
    }


    private void cleanOwnerMap(World world) {
        this.arrowOwners.entrySet().removeIf(entry -> BowAimbot.isOwnerEntryStale(world, entry));
    }

    public boolean isArrowIncoming(EntityArrowBridge arrow, EntityPlayerSP player) {
        double previousMotionX = arrow.z() - arrow.f();
        double previousMotionY = arrow.N() - arrow.H();
        double previousMotionZ = arrow.h() - arrow.R();
        Vec3d predictedPosition = this.predictArrowPosition(arrow);
        float currentDistance = player.getDistanceToEntity(arrow);
        double predictedDistance = player.i(
                predictedPosition.getX(), predictedPosition.getY(), predictedPosition.getZ());
        double nextDistance = player.i(arrow.z() + previousMotionX,
                arrow.N() + previousMotionY, arrow.h() + previousMotionZ);
        boolean incoming = predictedDistance < currentDistance && nextDistance < currentDistance;
        if (incoming) {
            float relativeAngle = RotationUtil.a(player, arrow);
            if (arrow.l() <= 4 && currentDistance <= 2.5 && Math.abs(relativeAngle) > 90.0f) {
                incoming = false;
            }
        }
        return incoming;
    }

    public float[] computeRotationTo(double targetX, double targetY, double targetZ) {
        EntityPlayerSP player = Minecraft.thePlayer();
        float yaw = RotationUtil.k(player.z(), player.h(), targetX, targetZ);
        float pitch = (float)RotationUtil.h(player, targetX, targetY, targetZ);
        return new float[]{yaw, pitch};
    }

    private void selectTargetArrow(EntityPlayerSP player, WorldClient world) {
        if (this.targetArrow == null || this.swingTicks > 0) {
            EntityArrowBridge closestArrow = null;
            double closestDistance = Double.MAX_VALUE;
            for (Map.Entry<Integer, Entity> entry : this.arrowOwners.entrySet()) {
                EntityArrowBridge arrow = new EntityArrowBridge(world.V(entry.getKey()));
                if (arrow.isNull()) {
                    continue;
                }
                double predictedDistance = this.predictArrowDistance(player, world, arrow);
                if (!this.isArrowIncoming(arrow, player)
                        || predictedDistance > 6.0 || predictedDistance >= closestDistance) {
                    continue;
                }
                float[] rotation = this.computeRotationTo(arrow.z(), arrow.N(), arrow.h());
                float yawDifference = Math.abs(MathUtil.wrapAngleTo180(-(player.J() - rotation[0])));
                float pitchDifference = player.V() - rotation[1];
                if (yawDifference > (Double)this.angleLimit.getValue()
                        || pitchDifference > (Double)this.angleLimit.getValue() / 2.0) {
                    continue;
                }
                closestDistance = predictedDistance;
                closestArrow = arrow;
            }
            if (closestArrow != null && !closestArrow.equals(this.targetArrow) && (this.rotationClaim.isOwnedBy(this) || this.rotationClaim.acquire(this, this.silentAim.getEffectiveValue()))) {
                this.swingTicks = 0;
                this.attackTriggered = false;
                this.targetArrow = closestArrow;
            }
        }
    }

    private boolean isBlockedByScreen() {
        if (Minecraft.currentScreen().isNull()) {
            return false;
        }
        InvWalk invWalk = Vape.INSTANCE.getModManager().getMod(InvWalk.class);
        return invWalk == null || !invWalk.isEnabled() || !invWalk.shouldHandleCurrentScreen();
    }

    private Vec3d predictArrowPosition(EntityArrowBridge arrow) {
        double drag = 0.95f;
        double predictedX = arrow.z() + (arrow.t() + arrow.L()) * drag;
        double predictedY = arrow.N() + (arrow.q() + arrow.X$src$D$xt9pjp()) * drag;
        double predictedZ = arrow.h() + (arrow.T() + arrow.o()) * drag;
        return new Vec3d(predictedX, predictedY, predictedZ);
    }

    private void clearTarget() {
        if (this.targetArrow != null && this.mouseButtonLock.isClaimed()) {
            this.mouseButtonLock.unlock();
        }
        this.targetArrow = null;
        this.swingTicks = 0;
        this.attackTriggered = false;
        this.restoreMovement();
        this.releaseRotation();
    }

    @EventHandler
    public void onTick(EventPreTick event) {
        WorldClient world = event.getWorld();
        EntityPlayerSP player = Minecraft.thePlayer();
        this.cleanOwnerMap(world);
        if (this.attackKeyPending) {
            this.mouseButtonLock.unlock();
            KeyBindingInputState.sendLeftButtonUp();
            this.attackKeyPending = false;
        }
        boolean adaptiveRotationActive = RotationManager.INSTANCE.hasAdaptiveController()
                && RotationManager.INSTANCE.getActiveController() == this.rotationController;
        this.releaseCompletedRotationClaim();
        if (this.canAcquireTarget()) {
            this.selectTargetArrow(player, world);
        }
        if (this.silentAim.getEffectiveValue() && adaptiveRotationActive
                && this.stopMovement.getEffectiveValue() && !this.moveOnFinish.getEffectiveValue()) {
            this.suppressMovement();
        }
        if (this.targetArrow == null) {
            this.releaseRotation();
            return;
        }

        double collisionBorder = this.targetArrow.b();
        float arrowWidth = this.targetArrow.Y();
        float arrowHeight = this.targetArrow.f$src$F$fst3ac();
        double arrowX = this.targetArrow.z();
        double arrowY = this.targetArrow.N();
        double arrowZ = this.targetArrow.h();
        AxisAlignedBB expandedArrowBox = AxisAlignedBB.create(
                arrowX - arrowWidth, arrowY, arrowZ - arrowWidth,
                arrowX + arrowWidth, arrowY + arrowHeight, arrowZ + arrowWidth)
                .expand(collisionBorder, collisionBorder, collisionBorder);
        double velocityX = this.targetArrow.t();
        double velocityY = this.targetArrow.q();
        double velocityZ = this.targetArrow.T();
        Vec3d aimPoint = RotationUtil.T(
                player, expandedArrowBox.expand(-1.0, -1.0, -1.0), 0.0, 0.0, 0.0);
        float[] aimRotation = this.computeRotationTo(aimPoint.getX(), aimPoint.getY(), aimPoint.getZ());
        if (this.rotationController == null) {
            this.rotationController = this.silentAim.getEffectiveValue()
                    ? new AdaptiveRotationController()
                    : new FixedRotationController(aimRotation[0], aimRotation[1]);
            this.rotationController.setClampStepToRemaining(true);
            this.rotationController.setTolerance(0.5f);
            this.rotationController.setScaleAxesProportionally(true);
            RotationManager.INSTANCE.setController(this.rotationController);
        }
        if (this.rotationController instanceof AdaptiveRotationController) {
            ((AdaptiveRotationController)this.rotationController).setRelativeMode(false);
        }
        this.rotationController.setRetainAfterCompletion(true);
        this.rotationController.setCubicAcceleration(true);
        this.rotationController.setSpeed(((Double)this.aimSpeed.getValue()).floatValue() * 1.5f);
        this.rotationController.setTargetRotation(aimRotation[0], aimRotation[1]);
        this.rotationController.setRandomizeMovement(true);

        boolean targetRemainsThreat = this.attackTriggered
                || this.isArrowIncoming(this.targetArrow, player)
                && this.predictArrowDistance(player, world, this.targetArrow) <= 6.0;
        if (this.targetArrow.M$src$Z$ff28xj()
                || !this.rotationClaim.isOwnedBy(this)
                || !this.canAcquireTarget()
                || !targetRemainsThreat) {
            this.clearTarget();
            return;
        }
        if (this.stopMovement.getEffectiveValue()) {
            this.suppressMovement();
        }

        AxisAlignedBB originalArrowBox = this.targetArrow.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl();
        AxisAlignedBB predictedArrowBox = ForgeVersion.MC_1_16_5.d()
                ? originalArrowBox : originalArrowBox.A(velocityX, velocityY, velocityZ);
        Vec3d attackPoint = RotationUtil.T(player,
                predictedArrowBox.expand(collisionBorder - 0.5, collisionBorder - 0.5, collisionBorder - 0.5),
                0.0, 0.0, 0.0);
        float[] attackRotation = this.computeRotationTo(attackPoint.getX(), attackPoint.getY(), attackPoint.getZ());
        float originalYaw = player.J();
        float originalPitch = player.V();
        if (ForgeVersion.MC_1_16_5.v()) {
            player.H(attackRotation[0]);
            player.C(attackRotation[1]);
            this.targetArrow.D(predictedArrowBox);
        }
        RayTraceResult crosshairHit = RotationManager.INSTANCE.getExtendedReachRayTrace();
        if (ForgeVersion.MC_1_16_5.v()) {
            this.targetArrow.D(originalArrowBox);
            player.H(originalYaw);
            player.C(originalPitch);
        }
        if (!this.attackTriggered && crosshairHit.isEntityHit()
                && this.targetArrow.equals(crosshairHit.getEntity())) {
            this.mouseButtonLock.lock();
            if (Minecraft.gameSettings().F().isKeyDown()) {
                KeyBindingInputState.sendLeftButtonUp();
                return;
            }
            KeyBindingInputState.sendLeftButtonDown();
            this.attackKeyPending = true;
            this.attackTriggered = true;
            this.swingTicks = 0;
        }
        if (this.attackTriggered) {
            this.rotationController.setSpeed(2.0f);
            if (this.swingTicks++ > 5) {
                this.clearTarget();
            }
        }
    }
}
