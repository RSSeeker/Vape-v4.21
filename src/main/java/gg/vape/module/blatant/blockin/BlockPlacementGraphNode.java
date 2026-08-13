package gg.vape.module.blatant.blockin;

import gg.vape.rotation.AdaptiveRotationController;
import gg.vape.rotation.FixedRotationController;
import gg.vape.rotation.MouseRotationController;
import java.util.List;

public class BlockPlacementGraphNode {
    public final float targetYaw;
    public final float yawDistance;
    public final float pitchDistance;
    public final float pendingYawDelta;
    public final float currentYaw;
    public final boolean complete;
    public final float currentPitch;
    public final float pendingPitchDelta;
    public final float targetPitch;

    public BlockPlacementGraphNode(MouseRotationController controller, float currentYaw, float currentPitch) {
        this.pendingYawDelta = controller.getPendingYawDelta();
        this.pendingPitchDelta = controller.getPendingPitchDelta();
        if (controller instanceof FixedRotationController) {
            this.targetYaw = ((FixedRotationController)controller).getTargetYaw();
            this.targetPitch = ((FixedRotationController)controller).getTargetPitch();
        } else {
            this.targetYaw = -1.0f;
            this.targetPitch = -1.0f;
        }
        this.yawDistance = controller.getYawDistance();
        this.pitchDistance = controller.getPitchDistance();
        this.complete = controller.isComplete();
        this.currentYaw = currentYaw;
        this.currentPitch = currentPitch;
    }


    public BlockPlacementGraphNode(AdaptiveRotationController adaptiveRotationController) {
        this(adaptiveRotationController, adaptiveRotationController.getRenderedYaw(), adaptiveRotationController.getRenderedPitch());
    }

    public String describeDifferences(BlockPlacementGraphNode snapshot) {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.pendingYawDelta != snapshot.pendingYawDelta) {
            stringBuilder.append("DeltaX: ").append(snapshot.pendingYawDelta).append(" != ").append(this.pendingYawDelta).append("\n");
        }
        if (this.pendingPitchDelta != snapshot.pendingPitchDelta) {
            stringBuilder.append("DeltaY: ").append(snapshot.pendingPitchDelta).append(" != ").append(this.pendingPitchDelta).append("\n");
        }
        if (this.targetYaw != snapshot.targetYaw) {
            stringBuilder.append("TargetYaw: ").append(snapshot.targetYaw).append(" != ").append(this.targetYaw).append("\n");
        }
        if (this.targetPitch != snapshot.targetPitch) {
            stringBuilder.append("TargetPitch: ").append(snapshot.targetPitch).append(" != ").append(this.targetPitch).append("\n");
        }
        if (this.yawDistance != snapshot.yawDistance) {
            stringBuilder.append("YawChanged: ").append(snapshot.yawDistance).append(" != ").append(this.yawDistance).append("\n");
        }
        if (this.pitchDistance != snapshot.pitchDistance) {
            stringBuilder.append("PitchChanged: ").append(snapshot.pitchDistance).append(" != ").append(this.pitchDistance).append("\n");
        }
        if (this.complete != snapshot.complete) {
            stringBuilder.append("IsCompleted: ").append(snapshot.complete).append(" != ").append(this.complete).append("\n");
        }
        if (this.currentYaw != snapshot.currentYaw) {
            stringBuilder.append("CurrentYaw: ").append(snapshot.currentYaw).append(" != ").append(this.currentYaw).append("\n");
        }
        if (this.currentPitch != snapshot.currentPitch) {
            stringBuilder.append("CurrentPitch: ").append(snapshot.currentPitch).append(" != ").append(this.currentPitch).append("\n");
        }
        return stringBuilder.toString();
    }

    public static String toCsv(List<BlockPlacementGraphNode> nodes) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("targetYaw,targetPitch,deltaX,deltaY,yawChanged,pitchChanged,isCompleted,currentYaw,currentPitch\n");
        for (BlockPlacementGraphNode node : nodes) {
            stringBuilder.append(node.targetYaw).append(",").append(node.targetPitch).append(",")
                    .append(node.pendingYawDelta).append(",").append(node.pendingPitchDelta).append(",")
                    .append(node.yawDistance).append(",").append(node.pitchDistance).append(",")
                    .append(node.complete).append(",").append(node.currentYaw).append(",")
                    .append(node.currentPitch).append("\n");
        }
        return stringBuilder.toString();
    }
}
