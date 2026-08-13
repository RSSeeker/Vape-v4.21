package gg.vape.utils;

import gg.vape.wrapper.impl.AxisAlignedBB;
import gg.vape.wrapper.impl.EntityPlayer;
import java.util.Comparator;

public final class AxisAlignedBBDistanceComparator
implements Comparator<AxisAlignedBB> {
    final EntityPlayer player;

    @Override
    public int compare(AxisAlignedBB firstBounds, AxisAlignedBB secondBounds) {
        return this.compareByDistance(firstBounds, secondBounds);
    }

    public int compareByDistance(AxisAlignedBB firstBounds, AxisAlignedBB secondBounds) {
        double firstDistance = this.player.i(firstBounds.getMinX() + 0.5, this.player.N(), firstBounds.getMinZ() + 0.5);
        double secondDistance = this.player.i(secondBounds.getMinX() + 0.5, this.player.N(), secondBounds.getMinZ() + 0.5);
        return Double.compare(firstDistance, secondDistance);
    }

    public AxisAlignedBBDistanceComparator(EntityPlayer entityPlayer) {
        this.player = entityPlayer;
    }
}
