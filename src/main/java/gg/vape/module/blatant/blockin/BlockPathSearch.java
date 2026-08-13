package gg.vape.module.blatant.blockin;

import gg.vape.utils.MathUtil;
import gg.vape.utils.RotationUtil;
import gg.vape.utils.datas.BlockCoordinate;
import gg.vape.wrapper.impl.BlockPos;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class BlockPathSearch {
    private int sameLevelStepCount = -1;
    private int verticalStepCount = -1;
    private final HashMap<Long, BlockPathSearchNode> nodesByPosition = new HashMap<>();
    private final BlockPathSearchNode destinationNode;
    private final BlockPathSearchNode startNode;

    public BlockPathSearch(BlockPos start, BlockPos destination) {
        this(start.getX(), start.getY(), start.getZ(), destination.getX(), destination.getY(), destination.getZ());
    }

    public BlockPathSearch(BlockCoordinate start, BlockCoordinate destination) {
        this(start.B(), start.E(), start.A(), destination.B(), destination.E(), destination.A());
    }

    public BlockPathSearch(double startX, double startY, double startZ,
                           double destinationX, double destinationY, double destinationZ) {
        this.destinationNode = this.getOrCreateNode(destinationX, destinationY, destinationZ);
        this.startNode = this.getOrCreateNode(startX, startY, startZ);
    }

    public BlockPathSearchNode getDestinationNode() {
        return this.destinationNode;
    }

    public BlockPathSearchNode getStartNode() {
        return this.startNode;
    }

    public int getSameLevelStepCount() {
        this.calculateStepCounts();
        return this.sameLevelStepCount;
    }

    public int getVerticalStepCount() {
        this.calculateStepCounts();
        return this.verticalStepCount;
    }

    private double squaredDistance(BlockPathSearchNode first, BlockPathSearchNode second) {
        double deltaX = first.x - second.x;
        double deltaY = first.y - second.y;
        double deltaZ = first.z - second.z;
        return deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
    }

    public List<BlockPathSearchNode> getPath() {
        if (this.startNode.parent == null) {
            this.search();
        }
        ArrayList<BlockPathSearchNode> path = new ArrayList<>();
        BlockPathSearchNode node = this.startNode;
        path.add(node);
        while (!node.equals(this.destinationNode)) {
            node = node.parent;
            path.add(node);
        }
        return path;
    }

    private List<BlockPathSearchNode> getNeighbors(BlockPathSearchNode node) {
        ArrayList<BlockPathSearchNode> neighbors = new ArrayList<>();
        double x = node.x;
        double y = node.y;
        double z = node.z;
        if (node.equals(this.destinationNode)) {
            x = MathUtil.floor(x) + 0.5;
            y = MathUtil.floor(y);
            z = MathUtil.floor(z) + 0.5;
        }
        for (int offset = -1; offset <= 1; ++offset) {
            if (offset == 0) {
                continue;
            }
            neighbors.add(this.getOrCreateNode(x + offset, y, z));
            neighbors.add(this.getOrCreateNode(x, y + offset, z));
            neighbors.add(this.getOrCreateNode(x, y, z + offset));
        }
        return neighbors;
    }

    public void search() {
        ArrayList<BlockPathSearchNode> openNodes = new ArrayList<>();
        HashSet<BlockPathSearchNode> closedNodes = new HashSet<>();
        openNodes.add(this.destinationNode);
        float initialYaw = RotationUtil.k(this.destinationNode.x, this.destinationNode.z,
                this.startNode.x + 0.5, this.startNode.z + 0.5);
        while (!openNodes.isEmpty()) {
            BlockPathSearchNode current = openNodes.get(0);
            for (int index = 1; index < openNodes.size(); ++index) {
                BlockPathSearchNode candidate = openNodes.get(index);
                if (candidate.getTotalCost() < current.getTotalCost()
                        || candidate.getTotalCost() == current.getTotalCost()
                        && candidate.heuristicCost < current.heuristicCost) {
                    current = candidate;
                }
            }
            openNodes.remove(current);
            closedNodes.add(current);
            if (current == this.startNode) {
                return;
            }
            for (BlockPathSearchNode neighbor : this.getNeighbors(current)) {
                if (closedNodes.contains(neighbor)) {
                    continue;
                }
                double turnPenalty = neighbor.y - current.y == 0.0 ? 0.0 : 20.0;
                turnPenalty += RotationUtil.C(this.destinationNode.x, this.destinationNode.z, initialYaw,
                        neighbor.x, neighbor.z);
                turnPenalty += RotationUtil.V(this.destinationNode.x, this.destinationNode.z,
                        neighbor.x, neighbor.z) * 3.0;
                double newPathCost = current.pathCost + this.squaredDistance(current, neighbor) + turnPenalty;
                if (newPathCost >= neighbor.pathCost && openNodes.contains(neighbor)) {
                    continue;
                }
                neighbor.pathCost = newPathCost;
                neighbor.heuristicCost = this.squaredDistance(neighbor, this.destinationNode);
                neighbor.parent = current;
                if (!openNodes.contains(neighbor)) {
                    openNodes.add(neighbor);
                }
            }
        }
    }

    private BlockPathSearchNode getOrCreateNode(double x, double y, double z) {
        long positionKey = BlockPos.p(x, y, z);
        BlockPathSearchNode node = this.nodesByPosition.get(positionKey);
        if (node == null) {
            node = new BlockPathSearchNode(x, y, z);
            this.nodesByPosition.put(positionKey, node);
        }
        return node;
    }

    private void calculateStepCounts() {
        if (this.sameLevelStepCount != -1) {
            return;
        }
        List<BlockPathSearchNode> path = this.getPath();
        if (path.isEmpty()) {
            return;
        }
        int previousY = (int)path.get(0).y;
        for (BlockPathSearchNode node : path) {
            if (node.y == previousY) {
                ++this.sameLevelStepCount;
            } else {
                ++this.verticalStepCount;
            }
            previousY = (int)node.y;
        }
    }
}
