package gg.vape.wrapper.impl;

public class GuiChest
extends GuiContainer {
    public String getTitle() {
        if (ForgeVersion.MC_1_16_5.d()) {
            return this.F().getFormattedText();
        }
        return this.getLowerChestInventory().getName();
    }

    public GuiChest(Object chestScreenHandle) {
        super(chestScreenHandle);
    }

    public Inventory getLowerChestInventory() {
        if (ForgeVersion.MC_1_16_5.d()) {
            Object containerScreenHandle = GuiChest.vapeInstance.getMappingsMapperCompat().hK.t(this.I);
            Object inventoryHandle = GuiChest.vapeInstance.getMappingsMapperCompat().guiChest
                    .getLowerChestInventory(containerScreenHandle);
            return new Inventory(inventoryHandle);
        }
        return new Inventory(GuiChest.vapeInstance.getMappingsMapperCompat().guiChest.getLowerChestInventory(this.I));
    }

}

