package gg.vape.module.macro;

import gg.vape.module.Macro;
import gg.vape.module.macro.ItemMacroAction;
import gg.vape.module.macro.MacroAction;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.Minecraft;

public class ItemMacro
extends Macro {
    public ItemMacro(String name) {
        super(name);
    }

    @Override
    public MacroAction createAction() {
        int hotbarSlot = this.findHotbarSlot();
        if (hotbarSlot == -1) {
            return null;
        }
        return new ItemMacroAction(this);
    }

    int findHotbarSlot() {
        try {
            for (int hotbarSlot = 0; hotbarSlot < 9; ++hotbarSlot) {
                ItemStack itemStack = Minecraft.thePlayer().V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().c(hotbarSlot);
                if (itemStack.getObject() == null || itemStack.getItem().getObject() == null) continue;
                if (String.valueOf(Item.f(itemStack.getItem())).equals(this.getName())) {
                    return hotbarSlot;
                }
                if (!itemStack.x().equalsIgnoreCase(this.getName()) && !itemStack.getItem().getItemStackDisplayName(itemStack).equalsIgnoreCase(this.getName())) continue;
                return hotbarSlot;
            }
        }
        catch (Exception ignored) {
            // empty catch block
        }
        return -1;
    }
}

