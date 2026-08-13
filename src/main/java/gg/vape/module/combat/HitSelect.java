package gg.vape.module.combat;

import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.combat.wtap.WTapRightClickUseCancelMode;
import gg.vape.module.combat.wtap.WTapSprintResetMode;
import gg.vape.unmap.ModeSelection;
import gg.vape.value.ModeValue;
import gg.vape.value.NumberValue;
import gg.vape.value.SubModuleValue;

public class HitSelect
extends Mod {
    private final SubModuleValue<WTapRightClickUseCancelMode> rightClickUseCancel = new WTapRightClickUseCancelMode(this, "Right-click use cancel").getSelectionValue();
    private final NumberValue chance;
    private final SubModuleValue<WTapSprintResetMode> sprintReset = new WTapSprintResetMode(this, "Normal").getSelectionValue();
    private final ModeValue mode = ModeValue.create((Object)this, "Mode", this.rightClickUseCancel, this.rightClickUseCancel, this.sprintReset);

    @Override
    public String getDetailedSuffix() {
        return this.getSimpleSuffix() + " " + this.chance.getDisplayValue() + "%";
    }


    public HitSelect() {
        super("HitSelect", 82881230, Category.COMBAT, "");
        this.chance = NumberValue.create(this, "Chance", "#", "%", 0.0, 90.0, 100.0);
        this.addValue(this.mode, this.chance);
        this.mode.setDescription("Mode");
        this.chance.setMaximumFractionDigits(0);
    }

    public boolean shouldTrigger() {
        return (Double)this.chance.java_lang_Object_K() >= Math.random() * 100.0;
    }

    public boolean isRightClickCancelActive() {
        if (!this.rightClickUseCancel.isSelected()) {
            return false;
        }
        return this.rightClickUseCancel.getInstance().isCancelActive();
    }

    @Override
    public String getSimpleSuffix() {
        return ((ModeSelection)this.mode.java_lang_Object_K()).getName();
    }
}
