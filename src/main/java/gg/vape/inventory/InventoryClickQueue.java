package gg.vape.inventory;

import java.util.Queue;

public class InventoryClickQueue {
    private final int targetSlot;
    private final InventoryClickAction action;
    private static String[] controlFlowMarker;
    private final int sourceSlot;

    public static void enqueueClick(int slot, int relatedSlot, int windowId, Queue<InventoryClick> queue) {
        new InventoryClickQueue(InventoryClickAction.CLICK, slot, relatedSlot).appendTo(windowId, queue);
    }

    public static void enqueueDropSlot(int slot, int windowId, Queue<InventoryClick> queue) {
        new InventoryClickQueue(InventoryClickAction.DROP_SLOT, slot, 0).appendTo(windowId, queue);
    }


    public static void enqueueShiftClick(int slot, int windowId, Queue<InventoryClick> queue) {
        new InventoryClickQueue(InventoryClickAction.SHIFT_CLICK, slot, 0).appendTo(windowId, queue);
    }

    public static String[] getControlFlowMarker() {
        return controlFlowMarker;
    }

    public InventoryClickQueue(InventoryClickAction action, int sourceSlot, int targetSlot) {
        this.action = action;
        this.sourceSlot = sourceSlot;
        this.targetSlot = targetSlot;
    }

    public static void enqueueSwap(int sourceSlot, int targetSlot, int windowId, Queue<InventoryClick> queue) {
        new InventoryClickQueue(InventoryClickAction.SWAP, sourceSlot, targetSlot).appendTo(windowId, queue);
    }

    public static void enqueueDropMouseStack(int windowId, Queue<InventoryClick> queue) {
        new InventoryClickQueue(InventoryClickAction.DROP_MOUSE_STACK, 0, 0).appendTo(windowId, queue);
    }

    public void appendTo(int windowId, Queue<InventoryClick> queue) {
        if (this.action == InventoryClickAction.MOVE) {
            queue.add(new InventoryClick(windowId, this.sourceSlot, 0, 0));
            queue.add(new InventoryClick(windowId, this.targetSlot, 0, 0));
        } else if (this.action == InventoryClickAction.SHIFT_CLICK) {
            queue.add(new InventoryClick(windowId, this.sourceSlot, 0, 1));
        } else if (this.action == InventoryClickAction.SWAP) {
            queue.add(new InventoryClick(windowId, this.sourceSlot, 0, 0));
            queue.add(new InventoryClick(windowId, this.targetSlot, 0, 0));
            queue.add(new InventoryClick(windowId, this.sourceSlot, 0, 0));
        } else if (this.action == InventoryClickAction.CLICK) {
            queue.add(new InventoryClick(windowId, this.sourceSlot, 0, 0));
        } else if (this.action == InventoryClickAction.DROP_SLOT) {
            queue.add(new InventoryClick(windowId, this.sourceSlot, 1, 4));
        } else if (this.action == InventoryClickAction.DROP_MOUSE_STACK) {
            queue.add(new InventoryClick(windowId, -999, 0, 0));
        }
    }

    public static void setControlFlowMarker(String[] marker) {
        controlFlowMarker = marker;
    }

    public static void enqueueMove(int sourceSlot, int targetSlot, int windowId, Queue<InventoryClick> queue) {
        new InventoryClickQueue(InventoryClickAction.MOVE, sourceSlot, targetSlot).appendTo(windowId, queue);
    }

    static {
        if (InventoryClickQueue.getControlFlowMarker() != null) {
            InventoryClickQueue.setControlFlowMarker(new String[2]);
        }
    }
}

