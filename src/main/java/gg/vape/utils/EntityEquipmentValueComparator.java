package gg.vape.utils;

import gg.vape.mapping.MappedClasses;
import gg.vape.utils.ItemStackScoreUtil;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.Minecraft;
import java.util.Comparator;

public class EntityEquipmentValueComparator
implements Comparator<Entity> {
    @Override
    public int compare(Entity firstEntity, Entity secondEntity) {
        return this.compareByEquipmentValue(firstEntity, secondEntity);
    }


    public int compareByEquipmentValue(Entity firstEntity, Entity secondEntity) {
        if (firstEntity.isInstance(MappedClasses.Yl) && secondEntity.isInstance(MappedClasses.Yl)) {
            return Double.compare(this.calculateEquipmentValue(firstEntity), this.calculateEquipmentValue(secondEntity));
        }
        EntityPlayerSP player = Minecraft.thePlayer();
        return Float.compare(player.getDistanceToEntity(firstEntity), player.getDistanceToEntity(secondEntity));
    }

    private double calculateEquipmentValue(Entity entity) {
        double equipmentValue = 0.0;
        for (Object itemStackHandle : new EntityPlayer(entity.getObject()).V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().i()) {
            ItemStack itemStack = new ItemStack(itemStackHandle);
            equipmentValue += ItemStackScoreUtil.L(itemStack);
        }
        return equipmentValue;
    }
}
