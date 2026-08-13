package gg.vape.utils;

import gg.vape.utils.RotationUtil;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.Minecraft;
import java.util.Comparator;

public class EntityAngleComparator
implements Comparator<Entity> {
    @Override
    public int compare(Entity firstEntity, Entity secondEntity) {
        return this.compareByAngle(firstEntity, secondEntity);
    }

    public int compareByAngle(Entity firstEntity, Entity secondEntity) {
        EntityPlayerSP player = Minecraft.thePlayer();
        return Integer.compare(RotationUtil.a(player, firstEntity), RotationUtil.a(player, secondEntity));
    }
}
