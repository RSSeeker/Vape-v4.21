package gg.vape.module.blatant.autoladder;

import gg.vape.module.blatant.blockin.BlockPlacementGraph;
import gg.vape.movement.MovementInputHelper;
import gg.vape.utils.MathUtil;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.impl.AxisAlignedBB;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.EnumFacing;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GameSettings;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.World;

/** Receding-horizon movement controller that keeps the player over a block center. */
public final class AutoLadderMovementController {
    private static final double LEGACY_LADDER_THICKNESS = 0.125;
    private static final double MODERN_LADDER_THICKNESS = 0.1875;
    private static final double CONTACT_PRESS_OVERLAP = 0.005;
    private static final double AIR_DECAY_PER_TICK = 0.91;
    private static final double GROUND_DECAY_PER_TICK = 0.546;
    private static final double CENTERING_DEADZONE = 0.03;
    private static final double AXIS_PRESS_THRESHOLD = 0.5;

    private AutoLadderMovementController() {
    }

    public static CenterInput chooseCentering(EntityPlayerSP player, World world,
                                              double centerX, double centerZ) {
        return chooseCentering(player, player, world,
                new BlockPlacementGraph(player), centerX, centerZ);
    }

    public static CenterInput chooseCentering(EntityPlayerSP player, World world,
                                              double centerX, double centerZ,
                                              BlockData ladderBlock, EnumFacing facing) {
        return chooseCentering(player, player, world,
                new BlockPlacementGraph(player), centerX, centerZ, ladderBlock, facing);
    }

    public static CenterInput chooseCentering(EntityPlayer sourcePlayer, EntityPlayerSP localPlayer,
                                              World world, BlockPlacementGraph graph,
                                              double centerX, double centerZ) {
        return chooseCentering(sourcePlayer, localPlayer, world, graph,
                centerX, centerZ, null, null);
    }

    public static CenterInput chooseCentering(EntityPlayer sourcePlayer, EntityPlayerSP localPlayer,
                                              World world, BlockPlacementGraph graph,
                                              double centerX, double centerZ,
                                              BlockData ladderBlock, EnumFacing facing) {
        double[] target = resolveCenteringTarget(sourcePlayer, centerX, centerZ,
                ladderBlock, facing);
        return chooseCenteringAnalytic(sourcePlayer, target[0], target[1],
                ladderBlock, facing);
    }

    /**
     * On versions where {@code isOnLadder} additionally requires a horizontal contact
     * (1.8.9/1.12.2), steering to the ladder cell center never touches the ladder box,
     * so the grab can never trigger. Aim the player's hitbox slightly INTO the ladder
     * face instead, so the collision keeps the contact pressed every tick.
     */
    private static double[] resolveCenteringTarget(EntityPlayer player, double centerX, double centerZ,
                                                   BlockData ladderBlock, EnumFacing facing) {
        if (!requiresLadderContact() || ladderBlock == null || facing == null) {
            return new double[]{centerX, centerZ};
        }
        AxisAlignedBB bounds = player.u$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$kogbsu();
        double halfWidth = (bounds.getMaxX() - bounds.getMinX()) / 2.0;
        double halfDepth = (bounds.getMaxZ() - bounds.getMinZ()) / 2.0;
        double thickness = getLadderThickness();
        double targetX = centerX;
        double targetZ = centerZ;
        int directionX = facing.getDirectionVector().getX();
        int directionZ = facing.getDirectionVector().getZ();
        if (directionX > 0) {
            targetX = ladderBlock.D() + halfWidth + thickness - CONTACT_PRESS_OVERLAP;
        } else if (directionX < 0) {
            targetX = ladderBlock.D() + 1.0 - halfWidth - thickness + CONTACT_PRESS_OVERLAP;
        } else if (directionZ > 0) {
            targetZ = ladderBlock.G() + halfDepth + thickness - CONTACT_PRESS_OVERLAP;
        } else if (directionZ < 0) {
            targetZ = ladderBlock.G() + 1.0 - halfDepth - thickness + CONTACT_PRESS_OVERLAP;
        }
        return new double[]{targetX, targetZ};
    }

    static boolean requiresLadderContact() {
        return ForgeVersion.MC_1_8_9.d() || ForgeVersion.MC_1_12_2.d();
    }

    /**
     * Clearance margin used when checking the player against the support block. Contact
     * versions must be allowed to rest flush against the wall (the margin becomes slightly
     * negative to tolerate floating point while still rejecting real penetration).
     */
    static double getSupportClearanceMargin() {
        return requiresLadderContact() ? -CONTACT_PRESS_OVERLAP : 0.04;
    }

    static double getLadderTopClearanceMargin() {
        return requiresLadderContact() ? -CONTACT_PRESS_OVERLAP : 0.002;
    }

    /**
     * Analytic steering law replacing the former exhaustive 9-input x 2-tick physics
     * enumeration. The player's horizontal drift over the next two ticks is predicted
     * from the current velocity (vanilla exponential friction), then each movement axis
     * is pressed when its projection onto the drift-compensated target direction
     * exceeds the threshold. Closed-loop per tick, so per-tick suboptimality is
     * corrected on the following tick.
     * <p>
     * The idle deadzone is suppressed while the ladder contact zone is relevant: the
     * contact-press target lies inside the ladder's thin collision volume, and only a
     * held press keeps {@code isCollidedHorizontally} true so the vanilla ladder grab
     * registers when the feet cross the ladder top. Releasing the input there would
     * let the player descend onto the ladder's top edge and stand on it instead of
     * grabbing the ladder.
     */
    private static CenterInput chooseCenteringAnalytic(EntityPlayer player,
                                                       double centerX, double centerZ,
                                                       BlockData ladderBlock,
                                                       EnumFacing facing) {
        double residualX = centerX - player.z();
        double residualZ = centerZ - player.h();
        double decay = player.b$src$Z$fqlxe4()
                ? GROUND_DECAY_PER_TICK : AIR_DECAY_PER_TICK;
        double driftFactor = decay * (1.0 + decay);
        residualX -= player.t() * driftFactor;
        residualZ -= player.T() * driftFactor;
        double residualMagnitude = Math.sqrt(residualX * residualX + residualZ * residualZ);
        boolean maintainPress = residualMagnitude < CENTERING_DEADZONE
                && maintainsLadderPress(player, ladderBlock, facing, centerX, centerZ);
        if (residualMagnitude < CENTERING_DEADZONE && !maintainPress) {
            return new CenterInput(false, false, false, false);
        }
        double unitX;
        double unitZ;
        if (residualMagnitude < 1.0E-9) {
            if (ladderBlock == null || facing == null) {
                return new CenterInput(false, false, false, false);
            }
            AxisAlignedBB ladderBounds = getExpectedLadderBounds(ladderBlock, facing);
            unitX = (ladderBounds.getMinX() + ladderBounds.getMaxX()) / 2.0 - player.z();
            unitZ = (ladderBounds.getMinZ() + ladderBounds.getMaxZ()) / 2.0 - player.h();
            double magnitude = Math.sqrt(unitX * unitX + unitZ * unitZ);
            if (magnitude < 1.0E-9) {
                return new CenterInput(false, false, false, false);
            }
            unitX /= magnitude;
            unitZ /= magnitude;
        } else {
            unitX = residualX / residualMagnitude;
            unitZ = residualZ / residualMagnitude;
        }
        float yawRadians = player.J() * ((float)Math.PI / 180.0f);
        float sinYaw = MathUtil.sin(yawRadians);
        float cosYaw = MathUtil.cos(yawRadians);
        double forwardProjection = unitX * (double)(-sinYaw) + unitZ * (double)cosYaw;
        double rightProjection = unitX * (double)(-cosYaw) + unitZ * (double)(-sinYaw);
        return new CenterInput(forwardProjection > AXIS_PRESS_THRESHOLD,
                forwardProjection < -AXIS_PRESS_THRESHOLD,
                rightProjection < -AXIS_PRESS_THRESHOLD,
                rightProjection > AXIS_PRESS_THRESHOLD);
    }

    /**
     * True while the ladder contact press must be held: either the player's hitbox
     * already overlaps the ladder's collision volume (releasing would strand the
     * player on the ladder's top edge as they descend), or the steering target lies
     * within hitbox-reach of the ladder face (the press is about to establish the
     * contact that the ladder grab needs).
     */
    private static boolean maintainsLadderPress(EntityPlayer player, BlockData ladderBlock,
                                                EnumFacing facing, double centerX, double centerZ) {
        if (ladderBlock == null || facing == null) {
            return false;
        }
        AxisAlignedBB ladderBounds = getExpectedLadderBounds(ladderBlock, facing);
        AxisAlignedBB playerBounds = player.u$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$kogbsu();
        if (playerBounds.getMaxX() > ladderBounds.getMinX()
                && playerBounds.getMinX() < ladderBounds.getMaxX()
                && playerBounds.getMaxZ() > ladderBounds.getMinZ()
                && playerBounds.getMinZ() < ladderBounds.getMaxZ()) {
            return true;
        }
        double directionX = facing.getDirectionVector().getX();
        double directionZ = facing.getDirectionVector().getZ();
        double thickness = getLadderThickness();
        double contactReach = CENTERING_DEADZONE
                + (playerBounds.getMaxX() - playerBounds.getMinX()) / 2.0;
        double contactDepthReach = CENTERING_DEADZONE
                + (playerBounds.getMaxZ() - playerBounds.getMinZ()) / 2.0;
        if (directionX > 0) {
            return centerX < ladderBlock.D() + thickness + contactReach;
        }
        if (directionX < 0) {
            return centerX > ladderBlock.D() + 1.0 - thickness - contactReach;
        }
        if (directionZ > 0) {
            return centerZ < ladderBlock.G() + thickness + contactDepthReach;
        }
        if (directionZ < 0) {
            return centerZ > ladderBlock.G() + 1.0 - thickness - contactDepthReach;
        }
        return false;
    }

    static double getLadderThickness() {
        return ForgeVersion.MC_1_16_5.d()
                ? MODERN_LADDER_THICKNESS : LEGACY_LADDER_THICKNESS;
    }

    static AxisAlignedBB getExpectedLadderBounds(BlockData ladder, EnumFacing facing) {
        int directionX = facing.getDirectionVector().getX();
        int directionZ = facing.getDirectionVector().getZ();
        double thickness = getLadderThickness();
        double minX = ladder.D();
        double maxX = ladder.D() + 1.0;
        double minZ = ladder.G();
        double maxZ = ladder.G() + 1.0;
        if (directionX > 0) {
            maxX = minX + thickness;
        } else if (directionX < 0) {
            minX = maxX - thickness;
        } else if (directionZ > 0) {
            maxZ = minZ + thickness;
        } else if (directionZ < 0) {
            minZ = maxZ - thickness;
        }
        return AxisAlignedBB.create(minX, ladder.B(), minZ,
                maxX, ladder.B() + 1.0, maxZ);
    }

    public static void apply(CenterInput input) {
        applyDirectional(input.forward, input.backward, input.left, input.right);
    }

    public static void apply(AutoLadderFallAdjustment adjustment) {
        if (!adjustment.overridesInput()) {
            MovementInputHelper.restorePhysicalInput(false);
            return;
        }
        applyDirectional(adjustment.isForward(), adjustment.isBackward(),
                adjustment.isLeft(), adjustment.isRight());
    }

    private static void applyDirectional(boolean forward, boolean backward,
                                         boolean left, boolean right) {
        MovementInputHelper.synchronizeDirectionalInput(forward, backward, left, right);
        MovementInputHelper.setJumpPressed(false);
        GameSettings settings = Minecraft.gameSettings();
        MovementInputHelper.synchronizeKeyState(
                settings.d$src$Lgg_vape_wrapper_impl_KeyBinding_$adn2z0(), false);
    }

    public static final class CenterInput {
        private final boolean forward;
        private final boolean backward;
        private final boolean left;
        private final boolean right;

        private CenterInput(boolean forward, boolean backward, boolean left, boolean right) {
            this.forward = forward;
            this.backward = backward;
            this.left = left;
            this.right = right;
        }

        public boolean isForward() {
            return this.forward;
        }

        public boolean isBackward() {
            return this.backward;
        }

        public boolean isLeft() {
            return this.left;
        }

        public boolean isRight() {
            return this.right;
        }
    }

}
