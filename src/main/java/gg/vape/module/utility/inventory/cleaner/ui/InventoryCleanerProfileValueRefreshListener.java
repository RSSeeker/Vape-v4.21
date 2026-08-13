package gg.vape.module.utility.inventory.cleaner.ui;

import gg.vape.event.EventHandler;
import gg.vape.event.EventListener;
import gg.vape.event.impl.ProfileChangeEvent;
import gg.vape.module.none.ClientSettings;
import gg.vape.module.utility.inventory.cleaner.ui.InventoryCleanerProfileValueComponent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.module.ModuleComponent;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.impl.ClientSettingsSearchFrame;
import gg.vape.ui.click.frame.impl.ModuleCategoryFrame;

public class InventoryCleanerProfileValueRefreshListener
implements EventListener {
    @EventHandler
    public void H(ProfileChangeEvent profileChangeEvent) {
        for (Frame frame : ClientSettings.mainStack.Y()) {
            if (!(frame instanceof ModuleCategoryFrame)) continue;
            ModuleCategoryFrame moduleCategoryFrame = (ModuleCategoryFrame)frame;
            for (GuiComponent guiComponent : moduleCategoryFrame.f()) {
                if (!(guiComponent instanceof ModuleComponent)) continue;
                ModuleComponent moduleComponent = (ModuleComponent)guiComponent;
                for (GuiComponent guiComponent2 : moduleComponent.getValueComponents()) {
                    if (!(guiComponent2 instanceof InventoryCleanerProfileValueComponent)) continue;
                    InventoryCleanerProfileValueComponent inventoryCleanerProfileValueComponent = (InventoryCleanerProfileValueComponent)guiComponent2;
                    inventoryCleanerProfileValueComponent.populate();
                }
            }
        }
        ClientSettings.getFrame(ClientSettingsSearchFrame.class).resetSearchAsync();
    }

}

