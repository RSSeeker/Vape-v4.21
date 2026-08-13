package gg.vape.module.world;

import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPreTick;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.control.SharedModuleControlClaims;
import gg.vape.unmap.ModeOption;
import gg.vape.unmap.ModeSelection;
import gg.vape.utils.ItemStackScoreUtil;
import gg.vape.value.ModeValue;
import gg.vape.value.NumberValue;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.EnumHand;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.Minecraft;

public class FastPlace
extends Mod {
    private final ModeOption projectilesOption;
    private final ModeValue heldItemMode;
    private final ModeOption allOption;
    private static final long MODULE_COLOR = 506362964711178016L;
    private final NumberValue delayValue = NumberValue.create((Object)this, "Delay", "#", "", 0.0, 1.0, 4.0, 1.0);
    private final ModeOption blocksOption;

    public FastPlace() {
        super("FastPlace", (int)MODULE_COLOR, Category.WORLD, "Changes the block place delay.");
        this.allOption = new ModeOption("All");
        this.blocksOption = new ModeOption("Blocks");
        this.projectilesOption = new ModeOption("Projectiles");
        this.heldItemMode = ModeValue.create((Object)this, "Held Item", "What kind of items should FastPlace function with?\nAll - All items/blocks\nBlocks - All blocks\nProjectiles - Snowballs & Eggs", (ModeSelection)this.allOption, this.allOption, this.blocksOption, this.projectilesOption);
        this.addValue(this.heldItemMode, this.delayValue);
    }

    @EventHandler
    public void onTick(EventPreTick eventPreTick) {
        EntityPlayerSP entityPlayerSP = Minecraft.thePlayer();
        if (entityPlayerSP.isNull()) {
            return;
        }
        if (SharedModuleControlClaims.rightClickUse.isClaimed()) {
            return;
        }
        ItemStack mainHandStack = entityPlayerSP.getHeldItemHand();
        ItemStack offHandStack = ForgeVersion.MC_1_12_2.d() ? entityPlayerSP.i(EnumHand.offHand()) : new ItemStack(null);
        if (this.heldItemMode.getValue() == this.blocksOption && !this.isBlock(mainHandStack) && !this.isBlock(offHandStack)) {
            return;
        }
        if (this.heldItemMode.getValue() == this.projectilesOption && !this.isProjectile(mainHandStack) && !this.isProjectile(offHandStack)) {
            return;
        }
        if ((double)Minecraft.w() > (Double)this.delayValue.getValue()) {
            Minecraft.E(((Double)this.delayValue.getValue()).intValue());
        }
    }

    private boolean isProjectile(ItemStack itemStack) {
        return itemStack.isNotNull() && itemStack.getItem().isNotNull() && ItemStackScoreUtil.Z(itemStack.getItem());
    }

    private boolean isBlock(ItemStack itemStack) {
        return itemStack.isNotNull() && itemStack.getItem().isNotNull() && itemStack.getItem().isInstance(MappedClasses.Vw);
    }

}

