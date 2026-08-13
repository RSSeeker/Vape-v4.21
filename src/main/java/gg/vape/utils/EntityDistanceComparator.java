package gg.vape.utils;

import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.Minecraft;
import java.util.Comparator;

public class EntityDistanceComparator
implements Comparator<Entity> {
    @Override
    public int compare(Entity firstEntity, Entity secondEntity) {
        return this.compareByDistance(firstEntity, secondEntity);
    }

    public int compareByDistance(Entity firstEntity, Entity secondEntity) {
        EntityPlayerSP player = Minecraft.thePlayer();
        return Float.compare(player.getDistanceToEntity(firstEntity), player.getDistanceToEntity(secondEntity));
    }
}
