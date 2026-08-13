package gg.vape.inventory;

public interface InventoryClickOptionsStage
extends InventoryClickSlotStage {
    InventoryClickBuildStage swapWithFirstHotbarSlot();

    InventoryClickBuildStage throwItem(boolean entireStack);

    InventoryClickBuildStage pickupAll(boolean rightButton);

    InventoryClickBuildStage swapWithHotbarSlot(int hotbarSlot);

    InventoryClickBuildStage quickMove();

    InventoryClickBuildStage pickup();

    InventoryClickBuildStage quickCraft(int dragEvent);

    InventoryClickBuildStage cloneStack();
}
