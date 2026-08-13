package gg.vape.inventory;

public enum InventoryClickAction {
    MOVE,
    SWAP,
    SHIFT_CLICK,
    CLICK,
    DROP_SLOT,
    DROP_MOUSE_STACK;

    private static final InventoryClickAction[] cachedValues;

    static {
        String[] actionNames = new String[]{"DROP_MOUSE_STACK", "MOVE", "CLICK", "SHIFTCLICK", "DROP_SLOT", "SWAP"};






        cachedValues = new InventoryClickAction[]{MOVE, SWAP, SHIFT_CLICK, CLICK, DROP_SLOT, DROP_MOUSE_STACK};
    }
}
