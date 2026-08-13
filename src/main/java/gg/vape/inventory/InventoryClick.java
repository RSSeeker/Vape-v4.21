package gg.vape.inventory;

import gg.vape.wrapper.impl.Minecraft;

public class InventoryClick {
    private final int windowId;
    private final int slotId;
    private final int mouseButton;
    private final int clickMode;

    public int getWindowId() {
        return this.windowId;
    }

    public int getClickMode() {
        return this.clickMode;
    }

    public int getMouseButton() {
        return this.mouseButton;
    }

    public static InventoryClickBuilder builder() {
        return new InventoryClickBuilder();
    }

    public int getSlotId() {
        return this.slotId;
    }

    public void execute() {
        Minecraft.playerController().O(this.windowId, this.slotId, this.mouseButton,
                this.clickMode, Minecraft.thePlayer());
    }

    public InventoryClick(int windowId, int slotId, int mouseButton, int clickMode) {
        this.windowId = windowId;
        this.slotId = slotId;
        this.mouseButton = mouseButton;
        this.clickMode = clickMode;
    }
}
