package gg.vape.module.utility.inventory.cleaner.ui;

import gg.vape.module.utility.inventory.cleaner.InventoryCleanerProfile;
import gg.vape.module.utility.inventory.cleaner.InventoryCleanerProfileValue;

public class InventoryCleanerProfileEditContext {
    public InventoryCleanerProfileValue profileValue;
    public InventoryCleanerProfile profile;
    public Runnable onClose;

    public InventoryCleanerProfileEditContext(InventoryCleanerProfileValue inventoryCleanerProfileValue, InventoryCleanerProfile inventoryCleanerProfile, Runnable runnable) {
        this.profileValue = inventoryCleanerProfileValue;
        this.profile = inventoryCleanerProfile;
        this.onClose = runnable;
    }
}
