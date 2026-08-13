package gg.vape.module.utility.inventory.cleaner.ui;

import gg.vape.module.none.ClientSettings;
import gg.vape.module.utility.inventory.cleaner.InventoryCleanerProfile;
import gg.vape.module.utility.inventory.cleaner.InventoryCleanerProfileValue;
import gg.vape.module.utility.inventory.cleaner.ui.InventoryCleanerPopupFrame;
import gg.vape.module.utility.inventory.cleaner.ui.InventoryCleanerProfileRow;
import gg.vape.ui.click.GuiMouseListener;
import gg.vape.ui.click.MouseClickButton;
import gg.vape.ui.click.frame.FrameStackManager;
import gg.vape.ui.click.frame.impl.main.ClickGuiFrameManager;
import java.awt.Point;

public class InventoryCleanerProfileRowClickHandler
implements GuiMouseListener {
    final InventoryCleanerProfile profile;
    final InventoryCleanerProfileValue profileValue;
    final Runnable onClose;

    private static void runCloseCallback(Runnable runnable) {
        ClientSettings.UI_EXECUTOR.execute(runnable);
    }

    public InventoryCleanerProfileRowClickHandler(InventoryCleanerProfileRow inventoryCleanerProfileRow, InventoryCleanerProfile inventoryCleanerProfile, InventoryCleanerProfileValue inventoryCleanerProfileValue, Runnable runnable) {
        this.profile = inventoryCleanerProfile;
        this.profileValue = inventoryCleanerProfileValue;
        this.onClose = runnable;
    }

    @Override
    public void g(Point point, MouseClickButton mouseClickButton) {
        if (this.profile.equals(this.profileValue.getValue())) {
            InventoryCleanerPopupFrame inventoryCleanerPopupFrame = ClientSettings.getFrame(InventoryCleanerPopupFrame.class);
            inventoryCleanerPopupFrame.editProfile(this.profileValue, this.profile, () -> InventoryCleanerProfileRowClickHandler.runCloseCallback(this.onClose));
            InventoryCleanerPopupFrame.returnToProfileEditor();
            if (ClientSettings.INSTANCE.getActiveStack() instanceof ClickGuiFrameManager) {
                ClickGuiFrameManager clickGuiFrameManager = (ClickGuiFrameManager)ClientSettings.INSTANCE.getActiveStack();
                inventoryCleanerPopupFrame.setParentStack(clickGuiFrameManager);
                clickGuiFrameManager.setSidecarFrame(inventoryCleanerPopupFrame);
            } else {
                inventoryCleanerPopupFrame.setParentStack(null);
                ClientSettings.INSTANCE.switchFrameStack(ClientSettings.inventoryCleanerStack);
            }
        } else {
            this.profileValue.setValue(this.profile);
        }
    }
}

