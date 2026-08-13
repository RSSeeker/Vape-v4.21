package gg.vape.module.blatant.autoladder;

import gg.vape.config.ClientSettings;
import gg.vape.module.blatant.blockin.BlockPathPlanner;
import gg.vape.module.blatant.blockin.BlockPlacementGraph;
import gg.vape.module.utility.clutch.ClutchPlacementPathUtils;
import gg.vape.module.utility.clutch.PlacementTarget;
import gg.vape.utils.BlockUtil;
import gg.vape.utils.MathUtil;
import gg.vape.utils.RotationVectorMath;
import gg.vape.utils.datas.BlockCoordinate;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.impl.AxisAlignedBB;
import gg.vape.wrapper.impl.Block;
import gg.vape.wrapper.impl.Blocks;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.EnumFacing;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GameSettings;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RayTraceResult;
import gg.vape.wrapper.impl.Vec3;
import gg.vape.wrapper.impl.World;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jetbrains.annotations.Nullable;

/**
 * Searches from future climbable player cells back to the placement actions needed
 * to create them. The exact placement and climb checks remain runtime-authoritative.
 */
public final class AutoLadderPlanner {
    private static final int MAX_SIMULATION_TICKS = 30;
    private static final double LADDER_SIDE_ENTRY_DEPTH = 0.28;
    private static final double CATCH_CELL_INSET = 0.02;
    private static final double CATCH_CANDIDATE_RADIUS = 0.67;
    private static final double LANDING_CENTER_SCORE_WEIGHT = 600.0;
    private static final double FALL_ADJUSTMENT_SCORE = 20.0;
    private static final double PLAN_COUNT_BONUS = 25.0;
    private static final EnumFacing[] HORIZONTAL_FACINGS =
            EnumFacing.c$src$ALgg_vape_wrapper_impl_EnumFacing_$1i3g4ft();
    private static final EnumFacing[] ALL_FACINGS = EnumFacing.t();

    private final World world;
    private final EntityPlayerSP player;
    private final boolean ladderAvailable;
    private final boolean supportBlockAvailable;
    private final boolean allowSupportBlock;
    private final boolean controlMovement;
    private final Set<String> rejectedPlans;
    private final BlockCoordinate landingBlock;
    private final double reach;
    private final boolean[] physicalInput;
    private int catchGeometryCount;
    private int existingLadderCount;
    private int directSupportCount;
    private int directOpportunityCount;
    private int directLadderTopRejectedCount;
    private int fallbackSpaceCount;
    private int fallbackAnchorCount;
    private int fallbackBlockOpportunityCount;
    private int fallbackCollisionRejectedCount;
    private int fallbackLadderOpportunityCount;
    private int fallbackTimingRejectedCount;
    private int fallbackMovementRejectedCount;
    private int fallbackControlledCollisionRejectedCount;
    private int fallbackLadderTopRejectedCount;
    private AutoLadderFallAdjustment recommendedFallAdjustment =
            AutoLadderFallAdjustment.PHYSICAL;

    public AutoLadderPlanner(World world, EntityPlayerSP player, boolean ladderAvailable,
                             boolean supportBlockAvailable, boolean allowSupportBlock,
                             boolean controlMovement, Set<String> rejectedPlans,
                             BlockCoordinate landingBlock) {
        this.world = world;
        this.player = player;
        this.ladderAvailable = ladderAvailable;
        this.supportBlockAvailable = supportBlockAvailable;
        this.allowSupportBlock = allowSupportBlock;
        this.controlMovement = controlMovement;
        this.rejectedPlans = rejectedPlans;
        this.landingBlock = landingBlock;
        this.reach = Math.max(0.0, Minecraft.playerController().N());
        GameSettings settings = Minecraft.gameSettings();
        this.physicalInput = new boolean[]{
                ClientSettings.isPhysicalKeyDown(settings.Y()),
                ClientSettings.isPhysicalKeyDown(settings.s()),
                ClientSettings.isPhysicalKeyDown(
                        settings.x$src$Lgg_vape_wrapper_impl_KeyBinding_$1cf7isg()),
                ClientSettings.isPhysicalKeyDown(
                        settings.g$src$Lgg_vape_wrapper_impl_KeyBinding_$qqn5n3())
        };
    }

    @Nullable
    public AutoLadderPlan findBestPlan() {
        List<TrajectoryCandidate> trajectories = this.simulateTrajectories();
        if (trajectories.isEmpty()) {
            return null;
        }

        List<CandidateEvaluation> evaluations = new ArrayList<>();
        for (TrajectoryCandidate trajectory : trajectories) {
            CounterSnapshot before = new CounterSnapshot(this);
            CandidateEvaluation evaluation = new CandidateEvaluation(
                    trajectory, new OpportunityMemo());
            evaluation.directPlans = this.findDirectPlans(trajectory, evaluation.memo);
            evaluation.progressScore = before.progressSince(this);
            evaluations.add(evaluation);
        }
        PlanSelection direct = this.selectPlanEvaluation(evaluations, true);
        if (direct != null) {
            this.recordRecommendation(direct.evaluation, direct.plan);
            return direct.plan;
        }
        if (!this.allowSupportBlock || !this.supportBlockAvailable || !this.ladderAvailable) {
            this.recordRecommendation(this.selectProgressEvaluation(evaluations), null);
            return null;
        }

        for (CandidateEvaluation evaluation : evaluations) {
            CounterSnapshot before = new CounterSnapshot(this);
            evaluation.supportPlans = this.findSupportPlans(
                    evaluation.trajectory, evaluation.memo);
            evaluation.progressScore += before.progressSince(this);
        }
        PlanSelection support = this.selectPlanEvaluation(evaluations, false);
        AutoLadderPlan selected = support == null ? null : support.plan;
        this.recordRecommendation(support == null
                ? this.selectProgressEvaluation(evaluations) : support.evaluation, selected);
        return selected;
    }

    public AutoLadderFallAdjustment getRecommendedFallAdjustment() {
        return this.recommendedFallAdjustment;
    }

    private List<TrajectoryCandidate> simulateTrajectories() {
        BlockPlacementGraph graph = new BlockPlacementGraph(this.player);
        List<TrajectoryCandidate> trajectories = new ArrayList<>();
        for (AutoLadderFallAdjustment adjustment : AutoLadderFallAdjustment.values()) {
            if (!this.controlMovement && adjustment.overridesInput()) {
                continue;
            }
            List<TrajectoryPoint> points = this.simulateTrajectory(graph, adjustment);
            if (points.size() < 2) {
                continue;
            }
            TrajectoryPoint finalPoint = points.get(points.size() - 1);
            double centerError = Math.hypot(
                    finalPoint.x - (this.landingBlock.B() + 0.5),
                    finalPoint.z - (this.landingBlock.A() + 0.5));
            trajectories.add(new TrajectoryCandidate(
                    points, this.findCatchSamples(points), adjustment, centerError));
        }
        return trajectories;
    }

    private List<TrajectoryPoint> simulateTrajectory(BlockPlacementGraph graph,
                                                      AutoLadderFallAdjustment adjustment) {
        BlockPathPlanner simulation = new BlockPathPlanner(this.player, this.player, this.world, graph);
        simulation.applySnapshot(graph);
        if (adjustment.overridesInput()) {
            simulation.setInput(adjustment.isForward(), adjustment.isBackward(),
                    adjustment.isLeft(), adjustment.isRight(), false, false);
        } else {
            this.applyPhysicalInput(simulation);
        }
        EntityPlayer simulatedPlayer = simulation.getSimulatedPlayer();
        List<TrajectoryPoint> points = new ArrayList<>();
        for (int tick = 0; tick <= MAX_SIMULATION_TICKS; ++tick) {
            boolean onGround = simulatedPlayer.b$src$Z$fqlxe4();
            points.add(TrajectoryPoint.capture(
                    tick, simulatedPlayer, onGround, new BlockPlacementGraph(simulation)));
            if (tick > 0 && onGround) {
                break;
            }
            simulation.simulateTick();
            if (tick == 0) {
                this.applyPhysicalInput(simulation);
            }
        }
        return points;
    }

    private void applyPhysicalInput(BlockPathPlanner simulation) {
        simulation.setInput(this.physicalInput[0], this.physicalInput[1],
                this.physicalInput[2], this.physicalInput[3], false, false);
    }

    private List<AutoLadderPlan> findDirectPlans(TrajectoryCandidate candidate,
                                                  OpportunityMemo memo) {
        Map<String, AutoLadderPlan> plans = new HashMap<>();
        List<TrajectoryPoint> trajectory = candidate.points;
        for (CatchSample catchSample : candidate.catchSamples) {
            TrajectoryPoint point = catchSample.point;
            Map<EnumFacing, List<CellCandidate>> facets = new LinkedHashMap<>();
            this.enumerateCatchCells(point, catchSample.ladderY,
                    (ladderBlock, facing, catchX, catchZ, movementError) -> {
                    BlockData supportBlock = ladderBlock.R(facing.getOpposite());
                    PlacementTarget ladderTarget = new PlacementTarget(supportBlock, facing);
                    Block ladderState = this.blockAt(ladderBlock);
                    if (this.isLadder(ladderState)) {
                        ++this.existingLadderCount;
                        double existingCatchX = ladderBlock.D() + 0.5;
                        double existingCatchZ = ladderBlock.G() + 0.5;
                        double existingMovementError = Math.hypot(
                                existingCatchX - point.x, existingCatchZ - point.z);
                        AutoLadderPlan plan = new AutoLadderPlan(
                                AutoLadderPlan.Mode.EXISTING_LADDER, null, ladderTarget,
                                existingCatchX, existingCatchZ, point.tick, -1, -1,
                                existingMovementError * 1000.0 + point.tick * 4.0
                                        + this.trajectoryCost(candidate),
                                candidate.adjustment);
                        this.addPlan(plans, plan);
                        return;
                    }
                    if (!this.ladderAvailable || !BlockUtil.u(ladderState)
                            || !this.isStableSupport(supportBlock)) {
                        return;
                    }
                    ++this.directSupportCount;
                    facets.computeIfAbsent(facing, key -> new ArrayList<>())
                            .add(new CellCandidate(ladderBlock, supportBlock, ladderTarget,
                                    catchX, catchZ, movementError));
            });
            for (Map.Entry<EnumFacing, List<CellCandidate>> entry : facets.entrySet()) {
                this.findDirectPlansForFacet(candidate, trajectory, catchSample,
                        entry.getKey(), entry.getValue(), memo, plans);
            }
        }
        return new ArrayList<>(plans.values());
    }

    private void findDirectPlansForFacet(TrajectoryCandidate candidate,
                                         List<TrajectoryPoint> trajectory,
                                         CatchSample catchSample, EnumFacing facing,
                                         List<CellCandidate> cells, OpportunityMemo memo,
                                         Map<String, AutoLadderPlan> plans) {
        boolean anyViable = false;
        for (CellCandidate cell : cells) {
            PlacementOpportunity opportunity = this.findPlacementOpportunity(
                    cell.ladderTarget, trajectory, 0,
                    catchSample.latestPlacementTick, false, memo);
            if (opportunity == null) {
                continue;
            }
            cell.ladderOpportunity = opportunity;
            ++this.directOpportunityCount;
            anyViable = true;
        }
        if (!anyViable) {
            return;
        }
        cells.sort(Comparator.comparingDouble(cell -> cell.movementError));
        List<CatchPathPoint> path = null;
        for (CellCandidate cell : cells) {
            if (cell.ladderOpportunity == null) {
                continue;
            }
            int placementTick = cell.ladderOpportunity.tick;
            path = this.simulateControlledCatchPath(
                    trajectory, placementTick, cell, facing, placementTick, placementTick);
            if (path != null) {
                break;
            }
        }
        if (path == null) {
            return;
        }
        int supportExistsTick = path.get(0).tick;
        for (CellCandidate cell : cells) {
            if (cell.ladderOpportunity == null) {
                continue;
            }
            ControlledCatch caught = this.evaluateCatchOnPath(
                    path, cell, facing, supportExistsTick, cell.ladderOpportunity.tick);
            if (caught == null) {
                ++this.directLadderTopRejectedCount;
                continue;
            }
            int slack = caught.catchTick - cell.ladderOpportunity.tick;
            double score = cell.movementError * 1000.0
                    + caught.remainingCenterError * 250.0
                    + cell.ladderOpportunity.rotationDistance * 2.0
                    + caught.catchTick * 4.0 - slack * 35.0
                    + this.trajectoryCost(candidate);
            AutoLadderPlan plan = new AutoLadderPlan(
                    AutoLadderPlan.Mode.DIRECT, null, cell.ladderTarget,
                    cell.catchX, cell.catchZ, caught.catchTick,
                    -1, cell.ladderOpportunity.tick, score,
                    candidate.adjustment);
            this.addPlan(plans, plan);
        }
    }

    private List<AutoLadderPlan> findSupportPlans(TrajectoryCandidate candidate,
                                                  OpportunityMemo memo) {
        Map<String, AutoLadderPlan> plans = new HashMap<>();
        List<TrajectoryPoint> trajectory = candidate.points;
        for (CatchSample catchSample : candidate.catchSamples) {
            TrajectoryPoint point = catchSample.point;
            Map<EnumFacing, List<CellCandidate>> facets = new LinkedHashMap<>();
            this.enumerateCatchCells(point, catchSample.ladderY,
                    (ladderBlock, ladderFacing, catchX, catchZ, movementError) -> {
                    if (!BlockUtil.u(this.blockAt(ladderBlock))) {
                        return;
                    }
                    BlockData supportBlock = ladderBlock.R(ladderFacing.getOpposite());
                    if (!BlockUtil.u(this.blockAt(supportBlock))) {
                        return;
                    }
                    if (this.isUnsafeLandingSupport(candidate, supportBlock)) {
                        return;
                    }
                    if (!ClutchPlacementPathUtils.isPlacementSpaceClear(
                            this.world, this.player, supportBlock)) {
                        return;
                    }
                    ++this.fallbackSpaceCount;
                    PlacementTarget ladderTarget = new PlacementTarget(
                            supportBlock, ladderFacing);
                    facets.computeIfAbsent(ladderFacing, key -> new ArrayList<>())
                            .add(new CellCandidate(ladderBlock, supportBlock, ladderTarget,
                                    catchX, catchZ, movementError));
            });
            for (Map.Entry<EnumFacing, List<CellCandidate>> entry : facets.entrySet()) {
                this.findSupportPlansForFacet(candidate, trajectory, catchSample,
                        entry.getKey(), entry.getValue(), memo, plans);
            }
        }
        return new ArrayList<>(plans.values());
    }

    private void findSupportPlansForFacet(TrajectoryCandidate candidate,
                                          List<TrajectoryPoint> trajectory,
                                          CatchSample catchSample, EnumFacing ladderFacing,
                                          List<CellCandidate> cells, OpportunityMemo memo,
                                          Map<String, AutoLadderPlan> plans) {
        boolean anyViable = false;
        for (CellCandidate cell : cells) {
            for (EnumFacing blockFacing : ALL_FACINGS) {
                BlockData anchorBlock = cell.supportBlock.R(blockFacing.getOpposite());
                if (!this.isStableSupport(anchorBlock)) {
                    continue;
                }
                ++this.fallbackAnchorCount;
                PlacementTarget blockTarget = new PlacementTarget(anchorBlock, blockFacing);
                PlacementOpportunity blockOpportunity = this.findPlacementOpportunity(
                        blockTarget, trajectory, 0,
                        catchSample.latestPlacementTick, true, memo);
                if (blockOpportunity == null) {
                    continue;
                }
                ++this.fallbackBlockOpportunityCount;
                int earliestLadderTick = blockOpportunity.tick + 1;
                if (earliestLadderTick > catchSample.latestPlacementTick) {
                    ++this.fallbackTimingRejectedCount;
                    continue;
                }
                PlacementOpportunity ladderOpportunity = this.findFutureFaceOpportunity(
                        cell.ladderTarget, trajectory, earliestLadderTick,
                        catchSample.latestPlacementTick, memo);
                if (ladderOpportunity == null) {
                    continue;
                }
                ++this.fallbackLadderOpportunityCount;
                if (this.intersectsBlockBeforeCatchControl(
                        trajectory, cell.supportBlock, blockOpportunity.tick,
                        ladderOpportunity.tick)) {
                    ++this.fallbackCollisionRejectedCount;
                    continue;
                }
                cell.anchors.add(new AnchorCandidate(
                        blockTarget, blockOpportunity, ladderOpportunity));
                anyViable = true;
            }
        }
        if (!anyViable) {
            return;
        }
        cells.sort(Comparator.comparingDouble(cell -> cell.movementError));
        List<CatchPathPoint> path = null;
        for (CellCandidate cell : cells) {
            if (cell.anchors.isEmpty()) {
                continue;
            }
            int supportExistsTick = Integer.MAX_VALUE;
            int ladderExistsTick = Integer.MAX_VALUE;
            for (AnchorCandidate anchor : cell.anchors) {
                supportExistsTick = Math.min(supportExistsTick, anchor.blockOpportunity.tick);
                ladderExistsTick = Math.min(ladderExistsTick, anchor.ladderOpportunity.tick);
            }
            path = this.simulateControlledCatchPath(
                    trajectory, ladderExistsTick, cell, ladderFacing,
                    supportExistsTick, ladderExistsTick);
            if (path != null) {
                break;
            }
        }
        if (path == null) {
            return;
        }
        for (CellCandidate cell : cells) {
            if (cell.anchors.isEmpty()) {
                continue;
            }
            for (AnchorCandidate anchor : cell.anchors) {
                ControlledCatch caught = this.evaluateCatchOnPath(
                        path, cell, ladderFacing, anchor.blockOpportunity.tick,
                        anchor.ladderOpportunity.tick);
                if (caught == null) {
                    ++this.fallbackMovementRejectedCount;
                    continue;
                }
                int slack = caught.catchTick - anchor.ladderOpportunity.tick;
                double score = cell.movementError * 1000.0
                        + caught.remainingCenterError * 250.0
                        + (anchor.blockOpportunity.rotationDistance
                        + anchor.ladderOpportunity.rotationDistance) * 2.0
                        + caught.catchTick * 5.0 - slack * 30.0
                        + this.trajectoryCost(candidate);
                AutoLadderPlan plan = new AutoLadderPlan(
                        AutoLadderPlan.Mode.BUILD_SUPPORT, anchor.blockTarget, cell.ladderTarget,
                        cell.catchX, cell.catchZ, caught.catchTick,
                        anchor.blockOpportunity.tick,
                        anchor.ladderOpportunity.tick, score, candidate.adjustment);
                this.addPlan(plans, plan);
            }
        }
    }

    private double trajectoryCost(TrajectoryCandidate candidate) {
        return candidate.centerError * LANDING_CENTER_SCORE_WEIGHT
                + (candidate.adjustment.overridesInput() ? FALL_ADJUSTMENT_SCORE : 0.0);
    }

    @Nullable
    private PlanSelection selectPlanEvaluation(List<CandidateEvaluation> evaluations,
                                               boolean direct) {
        PlanSelection selected = null;
        double selectedScore = Double.POSITIVE_INFINITY;
        for (CandidateEvaluation evaluation : evaluations) {
            List<AutoLadderPlan> plans = direct
                    ? evaluation.directPlans : evaluation.supportPlans;
            if (plans == null || plans.isEmpty()) {
                continue;
            }
            AutoLadderPlan bestPlan = direct
                    ? evaluation.bestDirectPlan() : evaluation.bestSupportPlan();
            double score = bestPlan.getScore()
                    - Math.min(8, plans.size()) * PLAN_COUNT_BONUS;
            if (selected == null || score < selectedScore
                    || score == selectedScore
                    && evaluation.trajectory.centerError < selected.evaluation.trajectory.centerError) {
                selected = new PlanSelection(evaluation, bestPlan);
                selectedScore = score;
            }
        }
        return selected;
    }

    @Nullable
    private CandidateEvaluation selectProgressEvaluation(List<CandidateEvaluation> evaluations) {
        CandidateEvaluation selected = null;
        for (CandidateEvaluation evaluation : evaluations) {
            if (selected == null || evaluation.progressScore > selected.progressScore
                    || evaluation.progressScore == selected.progressScore
                    && evaluation.trajectory.centerError < selected.trajectory.centerError
                    || evaluation.progressScore == selected.progressScore
                    && evaluation.trajectory.centerError == selected.trajectory.centerError
                    && !evaluation.trajectory.adjustment.overridesInput()
                    && selected.trajectory.adjustment.overridesInput()) {
                selected = evaluation;
            }
        }
        return selected;
    }

    private void recordRecommendation(@Nullable CandidateEvaluation evaluation,
                                      @Nullable AutoLadderPlan plan) {
        if (plan != null) {
            this.recommendedFallAdjustment = plan.getFallAdjustment();
            return;
        }
        if (evaluation == null) {
            this.recommendedFallAdjustment = AutoLadderFallAdjustment.PHYSICAL;
            return;
        }
        this.recommendedFallAdjustment = evaluation.trajectory.adjustment;
    }

    /**
     * Runs one controlled-catch integration for the steering cell of a facet. The
     * resulting path serves every cell of the same facet: per-cell feasibility is
     * derived afterwards by {@link #evaluateCatchOnPath}, so the number of physics
     * integrations per catch window drops from one per cell to one per facet.
     * Checks against the steering cell's support block and ladder bounds are only
     * applied once those blocks exist (they may be placed mid-fall).
     */
    @Nullable
    private List<CatchPathPoint> simulateControlledCatchPath(List<TrajectoryPoint> trajectory,
                                                              int controlStartTick,
                                                              CellCandidate cell,
                                                              EnumFacing facing,
                                                              int supportExistsTick,
                                                              int ladderExistsTick) {
        TrajectoryPoint start = this.pointAtTick(trajectory, controlStartTick);
        if (start == null || start.snapshot == null) {
            return null;
        }
        BlockPathPlanner simulation = new BlockPathPlanner(
                this.player, this.player, this.world, start.snapshot);
        simulation.applySnapshot(start.snapshot);
        EntityPlayer simulatedPlayer = simulation.getSimulatedPlayer();
        List<CatchPathPoint> path = new ArrayList<>();
        CatchPathPoint previous = CatchPathPoint.capture(
                controlStartTick, simulatedPlayer, simulatedPlayer.b$src$Z$fqlxe4());
        path.add(previous);
        if (controlStartTick >= ladderExistsTick
                && this.isSafeCatchPosition(previous, cell.ladderBlock, cell.supportBlock)
                && this.verticallyOverlapsLadder(previous, cell.ladderBlock)) {
            return path;
        }
        double catchCenterX = cell.ladderBlock.D() + 0.5;
        double catchCenterZ = cell.ladderBlock.G() + 0.5;
        double ladderTop = cell.ladderBlock.B() + 1.0;
        AxisAlignedBB ladderBounds = AutoLadderMovementController.getExpectedLadderBounds(
                cell.ladderBlock, facing);
        int lastTick = Math.min(MAX_SIMULATION_TICKS,
                controlStartTick + MAX_SIMULATION_TICKS);
        for (int tick = controlStartTick + 1; tick <= lastTick; ++tick) {
            AutoLadderMovementController.CenterInput input =
                    AutoLadderMovementController.chooseCentering(
                            simulatedPlayer, this.player, this.world, start.snapshot,
                            catchCenterX, catchCenterZ, cell.ladderBlock, facing);
            simulation.setInput(input.isForward(), input.isBackward(),
                    input.isLeft(), input.isRight(), false, false);
            simulation.simulateTick(false);
            CatchPathPoint current = CatchPathPoint.capture(
                    tick, simulatedPlayer, simulatedPlayer.b$src$Z$fqlxe4());
            path.add(current);
            if (previous.tick >= supportExistsTick
                    && (current.intersectsUnitBlock(cell.supportBlock, this.supportClearanceMargin())
                    || previous.sweptIntersectsUnitBlock(
                    current, cell.supportBlock, this.supportClearanceMargin()))) {
                return null;
            }
            if (previous.tick >= ladderExistsTick && previous.y >= ladderTop
                    && current.y < ladderTop
                    && previous.topCrossingIntersects(current, ladderBounds,
                    this.ladderTopClearanceMargin(),
                    (ladderTop - previous.y) / (current.y - previous.y))) {
                return null;
            }
            if (current.onGround) {
                return null;
            }
            if (current.tick >= ladderExistsTick
                    && this.isSafeCatchPosition(current, cell.ladderBlock, cell.supportBlock)
                    && this.verticallyOverlapsLadder(current, cell.ladderBlock)) {
                return path;
            }
            if (current.y < cell.ladderBlock.B() - 0.05 || current.motionY >= 0.0) {
                return null;
            }
            previous = current;
        }
        return null;
    }

    /**
     * Derives one cell's catch result from a shared facet path. Checks against the
     * cell's own support block and ladder bounds are only applied from the ticks at
     * which those blocks exist (support placement tick / ladder placement tick), so
     * cells placed later than the path start are not rejected for collisions with
     * blocks that do not exist yet.
     */
    @Nullable
    private ControlledCatch evaluateCatchOnPath(List<CatchPathPoint> path, CellCandidate cell,
                                                EnumFacing facing, int supportExistsTick,
                                                int ladderExistsTick) {
        double ladderTop = cell.ladderBlock.B() + 1.0;
        AxisAlignedBB ladderBounds = AutoLadderMovementController.getExpectedLadderBounds(
                cell.ladderBlock, facing);
        CatchPathPoint previous = null;
        for (CatchPathPoint current : path) {
            if (previous != null) {
                if (previous.tick >= supportExistsTick
                        && (current.intersectsUnitBlock(
                        cell.supportBlock, this.supportClearanceMargin())
                        || previous.sweptIntersectsUnitBlock(
                        current, cell.supportBlock, this.supportClearanceMargin()))) {
                    return null;
                }
                if (previous.tick >= ladderExistsTick && previous.y >= ladderTop
                        && current.y < ladderTop
                        && previous.topCrossingIntersects(current, ladderBounds,
                        this.ladderTopClearanceMargin(),
                        (ladderTop - previous.y) / (current.y - previous.y))) {
                    return null;
                }
            }
            if (current.tick >= ladderExistsTick
                    && this.isSafeCatchPosition(current, cell.ladderBlock, cell.supportBlock)
                    && this.verticallyOverlapsLadder(current, cell.ladderBlock)) {
                return new ControlledCatch(current.tick,
                        this.centerError(current, cell.ladderBlock));
            }
            previous = current;
        }
        return null;
    }

    @Nullable
    private TrajectoryPoint pointAtTick(List<TrajectoryPoint> trajectory, int tick) {
        for (TrajectoryPoint point : trajectory) {
            if (point.tick == tick) {
                return point;
            }
        }
        return null;
    }

    private boolean verticallyOverlapsLadder(CatchPathPoint point,
                                             BlockData ladderBlock) {
        return point.maxY > ladderBlock.B() && point.minY < ladderBlock.B() + 1.0;
    }

    private double centerError(CatchPathPoint point, BlockData ladderBlock) {
        return Math.hypot(point.x - (ladderBlock.D() + 0.5),
                point.z - (ladderBlock.G() + 0.5));
    }

    private boolean isSafeCatchPosition(CatchPathPoint point,
                                        BlockData ladderBlock,
                                        BlockData supportBlock) {
        boolean centerInsideLadderCell = point.x >= ladderBlock.D() + CATCH_CELL_INSET
                && point.x <= ladderBlock.D() + 1.0 - CATCH_CELL_INSET
                && point.z >= ladderBlock.G() + CATCH_CELL_INSET
                && point.z <= ladderBlock.G() + 1.0 - CATCH_CELL_INSET;
        return centerInsideLadderCell
                && !point.horizontallyIntersectsUnitBlock(
                supportBlock, this.supportClearanceMargin());
    }

    private double supportClearanceMargin() {
        return AutoLadderMovementController.getSupportClearanceMargin();
    }

    private double ladderTopClearanceMargin() {
        return AutoLadderMovementController.getLadderTopClearanceMargin();
    }

    private List<CatchSample> findCatchSamples(List<TrajectoryPoint> trajectory) {
        Map<Long, CatchSample> samples = new LinkedHashMap<>();
        int minimumLadderY = this.landingBlock.E() + 1;
        TrajectoryPoint initial = trajectory.get(0);
        int initialLadderY = MathUtil.floor(initial.y + 1.0E-4);
        double initialEntryY = initialLadderY + 1.0 - LADDER_SIDE_ENTRY_DEPTH;
        if (!initial.onGround && initial.motionY < 0.0
                && initialLadderY >= minimumLadderY
                && initial.y <= initialEntryY + 1.0E-4) {
            this.addCatchSample(samples,
                    new CatchSample(initial, initial.tick, initialLadderY), false);
        }
        for (int index = 1; index < trajectory.size(); ++index) {
            TrajectoryPoint previous = trajectory.get(index - 1);
            TrajectoryPoint current = trajectory.get(index);
            if (current.y >= previous.y || previous.motionY >= 0.0 && current.motionY >= 0.0) {
                continue;
            }
            int highestCrossedLayer = MathUtil.floor(
                    previous.y - 1.0 + LADDER_SIDE_ENTRY_DEPTH + 1.0E-4);
            int lowestCrossedLayer = Math.max(minimumLadderY, MathUtil.floor(
                    current.y - 1.0 + LADDER_SIDE_ENTRY_DEPTH + 1.0E-4) + 1);
            for (int ladderY = highestCrossedLayer;
                 ladderY >= lowestCrossedLayer; --ladderY) {
                double sideEntryY = ladderY + 1.0 - LADDER_SIDE_ENTRY_DEPTH;
                if (previous.y < sideEntryY || current.y >= sideEntryY) {
                    continue;
                }
                TrajectoryPoint sweptPoint = TrajectoryPoint.interpolateAtY(
                        previous, current, sideEntryY);
                this.addCatchSample(samples,
                        new CatchSample(sweptPoint, previous.tick, ladderY), true);
            }
            int ladderY = MathUtil.floor(current.y + 1.0E-4);
            double sideEntryY = ladderY + 1.0 - LADDER_SIDE_ENTRY_DEPTH;
            if (!current.onGround && current.motionY < 0.0
                    && ladderY >= minimumLadderY
                    && current.y >= ladderY && current.y <= sideEntryY + 1.0E-4) {
                this.addCatchSample(samples,
                        new CatchSample(current, current.tick, ladderY), false);
            }
        }
        return new ArrayList<>(samples.values());
    }

    private void addCatchSample(Map<Long, CatchSample> samples,
                                CatchSample sample, boolean prefer) {
        long key = ((long)sample.ladderY << 32)
                | (sample.point.tick & 0xFFFFFFFFL);
        CatchSample previous = samples.get(key);
        if (previous != null && !prefer) {
            return;
        }
        samples.put(key, sample);
    }

    private void enumerateCatchCells(TrajectoryPoint point, int ladderY,
                                     CatchCellConsumer consumer) {
        int searchRadius = this.controlMovement ? 1 : 0;
        int baseX = MathUtil.floor(point.x);
        int baseZ = MathUtil.floor(point.z);
        for (int xOffset = -searchRadius; xOffset <= searchRadius; ++xOffset) {
            for (int zOffset = -searchRadius; zOffset <= searchRadius; ++zOffset) {
                BlockData ladderBlock = new BlockData(
                        baseX + xOffset, ladderY, baseZ + zOffset);
                ++this.catchGeometryCount;
                for (EnumFacing facing : HORIZONTAL_FACINGS) {
                    double catchX = ladderBlock.D() + 0.5;
                    double catchZ = ladderBlock.G() + 0.5;
                    double movementError = Math.hypot(catchX - point.x, catchZ - point.z);
                    if (movementError > CATCH_CANDIDATE_RADIUS) {
                        continue;
                    }
                    ++this.catchGeometryCount;
                    consumer.accept(ladderBlock, facing, catchX, catchZ, movementError);
                }
            }
        }
    }

    @Nullable
    private PlacementOpportunity findPlacementOpportunity(PlacementTarget target,
                                                           List<TrajectoryPoint> trajectory,
                                                           int earliestTick,
                                                           int latestTick,
                                                           boolean placingSolidBlock,
                                                           OpportunityMemo memo) {
        if (latestTick < earliestTick) {
            return null;
        }
        if (earliestTick != 0) {
            return this.scanPlacementOpportunity(target, trajectory,
                    earliestTick, latestTick, placingSolidBlock);
        }
        Map<OpportunityKey, HitPointMemo> memoMap = placingSolidBlock
                ? memo.solidHitPoints : memo.nonSolidHitPoints;
        OpportunityKey key = new OpportunityKey(target.supportBlock, target.facing);
        HitPointMemo hitPoint = memoMap.get(key);
        if (hitPoint == null) {
            hitPoint = this.computeFirstHitPointOpportunity(
                    target, trajectory, placingSolidBlock);
            memoMap.put(key, hitPoint);
        }
        if (hitPoint.firstValidTick < 0 || hitPoint.firstValidTick > latestTick) {
            return null;
        }
        return new PlacementOpportunity(hitPoint.firstValidTick,
                hitPoint.firstValidRotationDistance);
    }

    @Nullable
    private PlacementOpportunity scanPlacementOpportunity(PlacementTarget target,
                                                           List<TrajectoryPoint> trajectory,
                                                           int earliestTick,
                                                           int latestTick,
                                                           boolean placingSolidBlock) {
        for (TrajectoryPoint point : trajectory) {
            if (point.tick < earliestTick) {
                continue;
            }
            if (point.tick > latestTick) {
                break;
            }
            if (placingSolidBlock && point.intersectsUnitBlock(target.getPlacedBlock())) {
                continue;
            }
            Vec3 eye = point.eyePosition();
            if (!ClutchPlacementPathUtils.isBlockFaceVisible(
                    eye, this.world, target.supportBlock, target.facing)) {
                continue;
            }
            if (!this.isFaceWithinReach(eye, target.supportBlock, target.facing)) {
                continue;
            }
            Vec3 hit = ClutchPlacementPathUtils.findBestPlacementHitPointWithinReach(
                    this.player, this.world, eye, target, point.yaw, point.pitch, this.reach);
            if (hit == null) {
                continue;
            }
            return new PlacementOpportunity(point.tick,
                    this.rotationDistance(eye, hit, point.yaw, point.pitch));
        }
        return null;
    }

    private HitPointMemo computeFirstHitPointOpportunity(PlacementTarget target,
                                                          List<TrajectoryPoint> trajectory,
                                                          boolean placingSolidBlock) {
        HitPointMemo result = new HitPointMemo();
        for (TrajectoryPoint point : trajectory) {
            if (placingSolidBlock && point.intersectsUnitBlock(target.getPlacedBlock())) {
                continue;
            }
            Vec3 eye = point.eyePosition();
            if (!ClutchPlacementPathUtils.isBlockFaceVisible(
                    eye, this.world, target.supportBlock, target.facing)) {
                continue;
            }
            if (!this.isFaceWithinReach(eye, target.supportBlock, target.facing)) {
                continue;
            }
            Vec3 hit = ClutchPlacementPathUtils.findBestPlacementHitPointWithinReach(
                    this.player, this.world, eye, target, point.yaw, point.pitch, this.reach);
            if (hit == null) {
                continue;
            }
            result.firstValidTick = point.tick;
            result.firstValidRotationDistance = this.rotationDistance(
                    eye, hit, point.yaw, point.pitch);
            break;
        }
        return result;
    }

    /**
     * O(1) geometric pre-filter used before the expensive hit-point search: the
     * closest point on the target face rectangle must be within reach. This is a
     * necessary condition for any reachable face point, so it rejects without ever
     * mis-filtering; during a fast fall the eye is close to a given wall only for a
     * few trajectory ticks, so most per-point scans are skipped entirely.
     */
    private boolean isFaceWithinReach(Vec3 eye, BlockData block, EnumFacing facing) {
        if (facing == null) {
            return true;
        }
        double directionX = facing.getDirectionVector().getX();
        double directionY = facing.getDirectionVector().getY();
        double directionZ = facing.getDirectionVector().getZ();
        double eyeX = eye.getX();
        double eyeY = eye.getY();
        double eyeZ = eye.getZ();
        double blockMinX = block.D();
        double blockMinY = block.B();
        double blockMinZ = block.G();
        double dx;
        double dy;
        double dz;
        if (directionX != 0.0) {
            dx = eyeX - (directionX > 0.0 ? blockMinX + 1.0 : blockMinX);
            dy = Math.max(blockMinY - eyeY,
                    Math.max(0.0, eyeY - (blockMinY + 1.0)));
            dz = Math.max(blockMinZ - eyeZ,
                    Math.max(0.0, eyeZ - (blockMinZ + 1.0)));
        } else if (directionZ != 0.0) {
            dz = eyeZ - (directionZ > 0.0 ? blockMinZ + 1.0 : blockMinZ);
            dx = Math.max(blockMinX - eyeX,
                    Math.max(0.0, eyeX - (blockMinX + 1.0)));
            dy = Math.max(blockMinY - eyeY,
                    Math.max(0.0, eyeY - (blockMinY + 1.0)));
        } else {
            dy = eyeY - (directionY > 0.0 ? blockMinY + 1.0 : blockMinY);
            dx = Math.max(blockMinX - eyeX,
                    Math.max(0.0, eyeX - (blockMinX + 1.0)));
            dz = Math.max(blockMinZ - eyeZ,
                    Math.max(0.0, eyeZ - (blockMinZ + 1.0)));
        }
        double distanceLimit = this.reach + 1.0E-4;
        return dx * dx + dy * dy + dz * dz <= distanceLimit * distanceLimit;
    }

    @Nullable
    private PlacementOpportunity findFutureFaceOpportunity(PlacementTarget target,
                                                            List<TrajectoryPoint> trajectory,
                                                            int earliestTick,
                                                            int latestTick,
                                                            OpportunityMemo memo) {
        if (latestTick < earliestTick) {
            return null;
        }
        OpportunityKey key = new OpportunityKey(target.supportBlock, target.facing);
        int[] validTicks = memo.faceCenterTicks.get(key);
        if (validTicks == null) {
            validTicks = this.computeFaceCenterValidTicks(target, trajectory);
            memo.faceCenterTicks.put(key, validTicks);
        }
        int index = lowerBound(validTicks, earliestTick);
        if (index >= validTicks.length || validTicks[index] > latestTick) {
            return null;
        }
        int tick = validTicks[index];
        TrajectoryPoint point = this.pointAtTick(trajectory, tick);
        if (point == null) {
            return null;
        }
        Vec3 eye = point.eyePosition();
        Vec3 faceCenter = this.faceCenter(target.supportBlock, target.facing);
        return new PlacementOpportunity(tick,
                this.rotationDistance(eye, faceCenter, point.yaw, point.pitch));
    }

    private int[] computeFaceCenterValidTicks(PlacementTarget target,
                                              List<TrajectoryPoint> trajectory) {
        Vec3 faceCenter = this.faceCenter(target.supportBlock, target.facing);
        List<Integer> valid = new ArrayList<>();
        for (TrajectoryPoint point : trajectory) {
            Vec3 eye = point.eyePosition();
            if (!ClutchPlacementPathUtils.isBlockFaceVisible(
                    eye, this.world, target.supportBlock, target.facing)
                    || eye.distanceTo(faceCenter) > this.reach
                    || !this.isFutureFaceUnobstructed(eye, faceCenter)) {
                continue;
            }
            valid.add(point.tick);
        }
        int[] result = new int[valid.size()];
        for (int index = 0; index < valid.size(); ++index) {
            result[index] = valid.get(index);
        }
        return result;
    }

    private static int lowerBound(int[] array, int value) {
        int low = 0;
        int high = array.length;
        while (low < high) {
            int mid = (low + high) >>> 1;
            if (array[mid] < value) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }

    private double rotationDistance(Vec3 eye, Vec3 hit, float yaw, float pitch) {
        gg.vape.rotation.RotationAngles rotation = RotationVectorMath.d(eye, hit, yaw, pitch);
        return Math.abs(MathUtil.wrapAngleTo180(rotation.getYaw() - yaw))
                + Math.abs(rotation.getPitch() - pitch);
    }

    private Vec3 faceCenter(BlockData block, EnumFacing facing) {
        double x = block.D() + 0.5 + facing.getDirectionVector().getX() * 0.5;
        double y = block.B() + 0.5 + facing.getDirectionVector().getY() * 0.5;
        double z = block.G() + 0.5 + facing.getDirectionVector().getZ() * 0.5;
        return Vec3.create(x, y, z);
    }

    private boolean intersectsBlockBeforeCatchControl(List<TrajectoryPoint> trajectory,
                                                       BlockData block, int firstTick, int lastTick) {
        TrajectoryPoint previous = null;
        for (TrajectoryPoint point : trajectory) {
            if (point.tick < firstTick - 1) {
                continue;
            }
            if (point.tick > lastTick) {
                break;
            }
            if (point.tick >= firstTick
                    && (point.intersectsUnitBlock(block, this.supportClearanceMargin())
                    || previous != null && previous.sweptIntersectsUnitBlock(
                    point, block, this.supportClearanceMargin()))) {
                return true;
            }
            previous = point;
        }
        return false;
    }

    private boolean isUnsafeLandingSupport(TrajectoryCandidate candidate,
                                           BlockData supportBlock) {
        TrajectoryPoint landingPoint = candidate.points.get(candidate.points.size() - 1);
        return supportBlock.B() == MathUtil.floor(landingPoint.y + 1.0E-4)
                && landingPoint.horizontallyIntersectsUnitBlock(
                supportBlock, this.supportClearanceMargin());
    }

    private boolean isFutureFaceUnobstructed(Vec3 eye, Vec3 faceCenter) {
        RayTraceResult rayTrace = this.world.K(eye, faceCenter, false, false,
                ForgeVersion.MC_1_16_5.v(), this.player);
        return rayTrace.isNull() || !rayTrace.isBlockHit();
    }

    private void addPlan(Map<String, AutoLadderPlan> plans, AutoLadderPlan plan) {
        String key = plan.rejectionKey();
        if (this.rejectedPlans.contains(key)) {
            return;
        }
        AutoLadderPlan previous = plans.get(key);
        if (previous == null || plan.getScore() < previous.getScore()) {
            plans.put(key, plan);
        }
    }

    private Block blockAt(BlockData block) {
        return this.world.getBlockByPos(block.D(), block.B(), block.G());
    }

    private boolean isLadder(Block block) {
        return block.isNotNull() && block.equals(Blocks.ladder());
    }

    private boolean isStableSupport(BlockData blockData) {
        Block block = this.blockAt(blockData);
        return block.isNotNull() && BlockUtil.k(block)
                && !ClutchPlacementPathUtils.isBlacklistedPlacementBlock(block);
    }

    private interface CatchCellConsumer {
        void accept(BlockData ladderBlock, EnumFacing facing,
                    double catchX, double catchZ, double movementError);
    }

    private static final class CatchSample {
        private final TrajectoryPoint point;
        private final int latestPlacementTick;
        private final int ladderY;

        private CatchSample(TrajectoryPoint point, int latestPlacementTick,
                            int ladderY) {
            this.point = point;
            this.latestPlacementTick = latestPlacementTick;
            this.ladderY = ladderY;
        }
    }

    private static final class TrajectoryCandidate {
        private final List<TrajectoryPoint> points;
        private final List<CatchSample> catchSamples;
        private final AutoLadderFallAdjustment adjustment;
        private final double centerError;

        private TrajectoryCandidate(List<TrajectoryPoint> points,
                                    List<CatchSample> catchSamples,
                                    AutoLadderFallAdjustment adjustment,
                                    double centerError) {
            this.points = points;
            this.catchSamples = catchSamples;
            this.adjustment = adjustment;
            this.centerError = centerError;
        }
    }

    private static final class CandidateEvaluation {
        private final TrajectoryCandidate trajectory;
        private final OpportunityMemo memo;
        private List<AutoLadderPlan> directPlans = new ArrayList<>();
        private List<AutoLadderPlan> supportPlans = new ArrayList<>();
        private int progressScore;

        private CandidateEvaluation(TrajectoryCandidate trajectory, OpportunityMemo memo) {
            this.trajectory = trajectory;
            this.memo = memo;
        }

        private AutoLadderPlan bestDirectPlan() {
            return this.directPlans.stream()
                    .min(Comparator.comparingDouble(AutoLadderPlan::getScore)).orElse(null);
        }

        private AutoLadderPlan bestSupportPlan() {
            return this.supportPlans.stream()
                    .min(Comparator.comparingDouble(AutoLadderPlan::getScore)).orElse(null);
        }
    }

    private static final class PlanSelection {
        private final CandidateEvaluation evaluation;
        private final AutoLadderPlan plan;

        private PlanSelection(CandidateEvaluation evaluation, AutoLadderPlan plan) {
            this.evaluation = evaluation;
            this.plan = plan;
        }
    }

    private static final class CounterSnapshot {
        private final int catchGeometry;
        private final int existingLadders;
        private final int directSupports;
        private final int directOpportunities;
        private final int directLadderTopRejects;
        private final int fallbackSpaces;
        private final int fallbackAnchors;
        private final int fallbackBlockOpportunities;
        private final int fallbackLadderOpportunities;
        private final int fallbackPreControlRejects;
        private final int fallbackTimingRejects;
        private final int fallbackMovementRejects;
        private final int fallbackControlledRejects;
        private final int fallbackLadderTopRejects;

        private CounterSnapshot(AutoLadderPlanner planner) {
            this.catchGeometry = planner.catchGeometryCount;
            this.existingLadders = planner.existingLadderCount;
            this.directSupports = planner.directSupportCount;
            this.directOpportunities = planner.directOpportunityCount;
            this.directLadderTopRejects = planner.directLadderTopRejectedCount;
            this.fallbackSpaces = planner.fallbackSpaceCount;
            this.fallbackAnchors = planner.fallbackAnchorCount;
            this.fallbackBlockOpportunities = planner.fallbackBlockOpportunityCount;
            this.fallbackLadderOpportunities = planner.fallbackLadderOpportunityCount;
            this.fallbackPreControlRejects = planner.fallbackCollisionRejectedCount;
            this.fallbackTimingRejects = planner.fallbackTimingRejectedCount;
            this.fallbackMovementRejects = planner.fallbackMovementRejectedCount;
            this.fallbackControlledRejects = planner.fallbackControlledCollisionRejectedCount;
            this.fallbackLadderTopRejects = planner.fallbackLadderTopRejectedCount;
        }

        private int progressSince(AutoLadderPlanner planner) {
            int catchGeometryDelta = planner.catchGeometryCount - this.catchGeometry;
            int existingDelta = planner.existingLadderCount - this.existingLadders;
            int directSupportDelta = planner.directSupportCount - this.directSupports;
            int directOpportunityDelta = planner.directOpportunityCount - this.directOpportunities;
            int directLadderTopRejectDelta = planner.directLadderTopRejectedCount
                    - this.directLadderTopRejects;
            int fallbackSpaceDelta = planner.fallbackSpaceCount - this.fallbackSpaces;
            int fallbackAnchorDelta = planner.fallbackAnchorCount - this.fallbackAnchors;
            int fallbackBlockDelta = planner.fallbackBlockOpportunityCount
                    - this.fallbackBlockOpportunities;
            int fallbackLadderDelta = planner.fallbackLadderOpportunityCount
                    - this.fallbackLadderOpportunities;
            int rejectionDelta = planner.fallbackCollisionRejectedCount
                    - this.fallbackPreControlRejects
                    + directLadderTopRejectDelta
                    + planner.fallbackTimingRejectedCount - this.fallbackTimingRejects
                    + planner.fallbackMovementRejectedCount - this.fallbackMovementRejects
                    + planner.fallbackControlledCollisionRejectedCount
                    - this.fallbackControlledRejects
                    + planner.fallbackLadderTopRejectedCount - this.fallbackLadderTopRejects;
            int viableLadderWindows = Math.max(0, fallbackLadderDelta - rejectionDelta);
            return viableLadderWindows * 1000
                    + (existingDelta + directOpportunityDelta) * 800
                    + fallbackLadderDelta * 120
                    + fallbackBlockDelta * 30
                    + fallbackAnchorDelta * 8
                    + fallbackSpaceDelta * 4
                    + directSupportDelta * 4
                    + catchGeometryDelta
                    - rejectionDelta * 5;
        }
    }

    private static final class ControlledCatch {
        private final int catchTick;
        private final double remainingCenterError;

        private ControlledCatch(int catchTick, double remainingCenterError) {
            this.catchTick = catchTick;
            this.remainingCenterError = remainingCenterError;
        }
    }

    /** A candidate ladder cell of a catch window, shared by the direct and support search phases. */
    private static final class CellCandidate {
        private final BlockData ladderBlock;
        private final BlockData supportBlock;
        private final PlacementTarget ladderTarget;
        private final double catchX;
        private final double catchZ;
        private final double movementError;
        private PlacementOpportunity ladderOpportunity;
        private final List<AnchorCandidate> anchors = new ArrayList<>();

        private CellCandidate(BlockData ladderBlock, BlockData supportBlock,
                              PlacementTarget ladderTarget, double catchX, double catchZ,
                              double movementError) {
            this.ladderBlock = ladderBlock;
            this.supportBlock = supportBlock;
            this.ladderTarget = ladderTarget;
            this.catchX = catchX;
            this.catchZ = catchZ;
            this.movementError = movementError;
        }
    }

    /** One support-block anchor option of a {@link CellCandidate} (BUILD_SUPPORT phase). */
    private static final class AnchorCandidate {
        private final PlacementTarget blockTarget;
        private final PlacementOpportunity blockOpportunity;
        private final PlacementOpportunity ladderOpportunity;

        private AnchorCandidate(PlacementTarget blockTarget,
                                PlacementOpportunity blockOpportunity,
                                PlacementOpportunity ladderOpportunity) {
            this.blockTarget = blockTarget;
            this.blockOpportunity = blockOpportunity;
            this.ladderOpportunity = ladderOpportunity;
        }
    }

    /**
     * Light per-tick record of a controlled-catch integration. Deliberately snapshot-
     * free: one shared facet path is evaluated against many cells, so keeping a full
     * {@link BlockPlacementGraph} per tick would allocate dozens of snapshots that
     * only one cell would ever read.
     */
    private static final class CatchPathPoint {
        private final int tick;
        private final double x;
        private final double y;
        private final double z;
        private final double motionY;
        private final double minX;
        private final double minY;
        private final double minZ;
        private final double maxX;
        private final double maxY;
        private final double maxZ;
        private final boolean onGround;

        private CatchPathPoint(int tick, double x, double y, double z, double motionY,
                               AxisAlignedBB bounds, boolean onGround) {
            this.tick = tick;
            this.x = x;
            this.y = y;
            this.z = z;
            this.motionY = motionY;
            this.minX = bounds.getMinX();
            this.minY = bounds.getMinY();
            this.minZ = bounds.getMinZ();
            this.maxX = bounds.getMaxX();
            this.maxY = bounds.getMaxY();
            this.maxZ = bounds.getMaxZ();
            this.onGround = onGround;
        }

        private static CatchPathPoint capture(int tick, EntityPlayer player, boolean onGround) {
            return new CatchPathPoint(tick, player.z(), player.N(), player.h(), player.q(),
                    player.u$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$kogbsu(), onGround);
        }

        private boolean intersectsUnitBlock(BlockData block, double horizontalMargin) {
            return this.maxX + horizontalMargin > block.D()
                    && this.minX - horizontalMargin < block.D() + 1.0
                    && this.maxY > block.B() && this.minY < block.B() + 1.0
                    && this.maxZ + horizontalMargin > block.G()
                    && this.minZ - horizontalMargin < block.G() + 1.0;
        }

        private boolean horizontallyIntersectsUnitBlock(BlockData block,
                                                         double horizontalMargin) {
            return this.maxX + horizontalMargin > block.D()
                    && this.minX - horizontalMargin < block.D() + 1.0
                    && this.maxZ + horizontalMargin > block.G()
                    && this.minZ - horizontalMargin < block.G() + 1.0;
        }

        private boolean sweptIntersectsUnitBlock(CatchPathPoint next,
                                                 BlockData block, double horizontalMargin) {
            double startX = (this.minX + this.maxX) / 2.0;
            double startY = (this.minY + this.maxY) / 2.0;
            double startZ = (this.minZ + this.maxZ) / 2.0;
            double endX = (next.minX + next.maxX) / 2.0;
            double endY = (next.minY + next.maxY) / 2.0;
            double endZ = (next.minZ + next.maxZ) / 2.0;
            double halfWidthX = (this.maxX - this.minX) / 2.0 + horizontalMargin;
            double halfHeight = (this.maxY - this.minY) / 2.0;
            double halfWidthZ = (this.maxZ - this.minZ) / 2.0 + horizontalMargin;
            double xEntry = axisEntry(startX, endX,
                    block.D() - halfWidthX, block.D() + 1.0 + halfWidthX);
            double yEntry = axisEntry(startY, endY,
                    block.B() - halfHeight, block.B() + 1.0 + halfHeight);
            double zEntry = axisEntry(startZ, endZ,
                    block.G() - halfWidthZ, block.G() + 1.0 + halfWidthZ);
            double xExit = axisExit(startX, endX,
                    block.D() - halfWidthX, block.D() + 1.0 + halfWidthX);
            double yExit = axisExit(startY, endY,
                    block.B() - halfHeight, block.B() + 1.0 + halfHeight);
            double zExit = axisExit(startZ, endZ,
                    block.G() - halfWidthZ, block.G() + 1.0 + halfWidthZ);
            double entry = Math.max(0.0, Math.max(xEntry, Math.max(yEntry, zEntry)));
            double exit = Math.min(1.0, Math.min(xExit, Math.min(yExit, zExit)));
            return entry <= exit;
        }

        private boolean topCrossingIntersects(CatchPathPoint end, AxisAlignedBB bounds,
                                              double margin, double progress) {
            double minX = lerp(this.minX, end.minX, progress) - margin;
            double maxX = lerp(this.maxX, end.maxX, progress) + margin;
            double minZ = lerp(this.minZ, end.minZ, progress) - margin;
            double maxZ = lerp(this.maxZ, end.maxZ, progress) + margin;
            return maxX > bounds.getMinX() && minX < bounds.getMaxX()
                    && maxZ > bounds.getMinZ() && minZ < bounds.getMaxZ();
        }

        private static double axisEntry(double start, double end,
                                        double minimum, double maximum) {
            double delta = end - start;
            if (Math.abs(delta) < 1.0E-9) {
                return start >= minimum && start <= maximum
                        ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
            }
            return Math.min((minimum - start) / delta, (maximum - start) / delta);
        }

        private static double axisExit(double start, double end,
                                       double minimum, double maximum) {
            double delta = end - start;
            if (Math.abs(delta) < 1.0E-9) {
                return start >= minimum && start <= maximum
                        ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
            }
            return Math.max((minimum - start) / delta, (maximum - start) / delta);
        }

        private static double lerp(double start, double end, double progress) {
            return start + (end - start) * progress;
        }
    }

    /** Per-candidate cache of placement opportunity results, keyed by placement target. */
    private static final class OpportunityMemo {
        private final Map<OpportunityKey, HitPointMemo> solidHitPoints = new HashMap<>();
        private final Map<OpportunityKey, HitPointMemo> nonSolidHitPoints = new HashMap<>();
        private final Map<OpportunityKey, int[]> faceCenterTicks = new HashMap<>();
    }

    private static final class OpportunityKey {
        private final BlockData supportBlock;
        private final EnumFacing facing;

        private OpportunityKey(BlockData supportBlock, EnumFacing facing) {
            this.supportBlock = supportBlock;
            this.facing = facing;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof OpportunityKey)) {
                return false;
            }
            OpportunityKey other = (OpportunityKey)object;
            return this.supportBlock.equals(other.supportBlock)
                    && this.facing.Y() == other.facing.Y();
        }

        @Override
        public int hashCode() {
            return this.supportBlock.hashCode() * 31 + this.facing.Y();
        }
    }

    private static final class HitPointMemo {
        private int firstValidTick = -1;
        private double firstValidRotationDistance;
    }

    private static final class PlacementOpportunity {
        private final int tick;
        private final double rotationDistance;

        private PlacementOpportunity(int tick, double rotationDistance) {
            this.tick = tick;
            this.rotationDistance = rotationDistance;
        }
    }

    private static final class TrajectoryPoint {
        private final int tick;
        private final double x;
        private final double y;
        private final double z;
        private final double eyeY;
        private final double motionY;
        private final float yaw;
        private final float pitch;
        private final double minX;
        private final double minY;
        private final double minZ;
        private final double maxX;
        private final double maxY;
        private final double maxZ;
        private final boolean onGround;
        private final BlockPlacementGraph snapshot;
        private TrajectoryPoint(int tick, double x, double y, double z, double eyeY,
                                double motionY, float yaw, float pitch, AxisAlignedBB bounds,
                                boolean onGround, BlockPlacementGraph snapshot) {
            this.tick = tick;
            this.x = x;
            this.y = y;
            this.z = z;
            this.eyeY = eyeY;
            this.motionY = motionY;
            this.yaw = yaw;
            this.pitch = pitch;
            this.minX = bounds.getMinX();
            this.minY = bounds.getMinY();
            this.minZ = bounds.getMinZ();
            this.maxX = bounds.getMaxX();
            this.maxY = bounds.getMaxY();
            this.maxZ = bounds.getMaxZ();
            this.onGround = onGround;
            this.snapshot = snapshot;
        }

        private static TrajectoryPoint capture(int tick, EntityPlayer player, boolean onGround,
                                               BlockPlacementGraph snapshot) {
            AxisAlignedBB bounds = player.u$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$kogbsu();
            return new TrajectoryPoint(tick, player.z(), player.N(), player.h(),
                    player.N() + player.X(), player.q(), player.J(), player.V(), bounds,
                    onGround, snapshot);
        }

        private static TrajectoryPoint interpolateAtY(TrajectoryPoint start,
                                                      TrajectoryPoint end,
                                                      double targetY) {
            double verticalDelta = end.y - start.y;
            double progress = Math.abs(verticalDelta) < 1.0E-9
                    ? 0.0 : (targetY - start.y) / verticalDelta;
            progress = Math.max(0.0, Math.min(1.0, progress));
            double x = lerp(start.x, end.x, progress);
            double z = lerp(start.z, end.z, progress);
            double eyeY = lerp(start.eyeY, end.eyeY, progress);
            double motionY = lerp(start.motionY, end.motionY, progress);
            float yaw = start.yaw + MathUtil.wrapAngleTo180(end.yaw - start.yaw)
                    * (float)progress;
            float pitch = (float)lerp(start.pitch, end.pitch, progress);
            AxisAlignedBB bounds = AxisAlignedBB.create(
                    lerp(start.minX, end.minX, progress),
                    lerp(start.minY, end.minY, progress),
                    lerp(start.minZ, end.minZ, progress),
                    lerp(start.maxX, end.maxX, progress),
                    lerp(start.maxY, end.maxY, progress),
                    lerp(start.maxZ, end.maxZ, progress));
            return new TrajectoryPoint(end.tick, x, targetY, z, eyeY,
                    motionY, yaw, pitch, bounds, false, end.snapshot);
        }

        private static double lerp(double start, double end, double progress) {
            return start + (end - start) * progress;
        }

        private Vec3 eyePosition() {
            return Vec3.create(this.x, this.eyeY, this.z);
        }

        private boolean intersectsUnitBlock(BlockData block) {
            return this.maxX > block.D() && this.minX < block.D() + 1.0
                    && this.maxY > block.B() && this.minY < block.B() + 1.0
                    && this.maxZ > block.G() && this.minZ < block.G() + 1.0;
        }

        private boolean intersectsUnitBlock(BlockData block, double horizontalMargin) {
            return this.maxX + horizontalMargin > block.D()
                    && this.minX - horizontalMargin < block.D() + 1.0
                    && this.maxY > block.B() && this.minY < block.B() + 1.0
                    && this.maxZ + horizontalMargin > block.G()
                    && this.minZ - horizontalMargin < block.G() + 1.0;
        }

        private boolean horizontallyIntersectsUnitBlock(BlockData block,
                                                         double horizontalMargin) {
            return this.maxX + horizontalMargin > block.D()
                    && this.minX - horizontalMargin < block.D() + 1.0
                    && this.maxZ + horizontalMargin > block.G()
                    && this.minZ - horizontalMargin < block.G() + 1.0;
        }

        private boolean sweptIntersectsUnitBlock(TrajectoryPoint next,
                                                 BlockData block, double horizontalMargin) {
            double startX = (this.minX + this.maxX) / 2.0;
            double startY = (this.minY + this.maxY) / 2.0;
            double startZ = (this.minZ + this.maxZ) / 2.0;
            double endX = (next.minX + next.maxX) / 2.0;
            double endY = (next.minY + next.maxY) / 2.0;
            double endZ = (next.minZ + next.maxZ) / 2.0;
            double halfWidthX = (this.maxX - this.minX) / 2.0 + horizontalMargin;
            double halfHeight = (this.maxY - this.minY) / 2.0;
            double halfWidthZ = (this.maxZ - this.minZ) / 2.0 + horizontalMargin;
            double xEntry = axisEntry(startX, endX,
                    block.D() - halfWidthX, block.D() + 1.0 + halfWidthX);
            double yEntry = axisEntry(startY, endY,
                    block.B() - halfHeight, block.B() + 1.0 + halfHeight);
            double zEntry = axisEntry(startZ, endZ,
                    block.G() - halfWidthZ, block.G() + 1.0 + halfWidthZ);
            double xExit = axisExit(startX, endX,
                    block.D() - halfWidthX, block.D() + 1.0 + halfWidthX);
            double yExit = axisExit(startY, endY,
                    block.B() - halfHeight, block.B() + 1.0 + halfHeight);
            double zExit = axisExit(startZ, endZ,
                    block.G() - halfWidthZ, block.G() + 1.0 + halfWidthZ);
            double entry = Math.max(0.0, Math.max(xEntry, Math.max(yEntry, zEntry)));
            double exit = Math.min(1.0, Math.min(xExit, Math.min(yExit, zExit)));
            return entry <= exit;
        }

        private static double axisEntry(double start, double end,
                                        double minimum, double maximum) {
            double delta = end - start;
            if (Math.abs(delta) < 1.0E-9) {
                return start >= minimum && start <= maximum
                        ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
            }
            return Math.min((minimum - start) / delta, (maximum - start) / delta);
        }

        private static double axisExit(double start, double end,
                                       double minimum, double maximum) {
            double delta = end - start;
            if (Math.abs(delta) < 1.0E-9) {
                return start >= minimum && start <= maximum
                        ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
            }
            return Math.max((minimum - start) / delta, (maximum - start) / delta);
        }
    }
}
