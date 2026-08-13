package gg.vape.utils.datas;

import gg.vape.utils.datas.BlockCoordinate;
import gg.vape.wrapper.impl.EnumFacing;
import java.util.Objects;

public class DirectionalPosition
extends BlockCoordinate {
    private int facingIndex;

    public DirectionalPosition(BlockCoordinate blockCoordinate, int facingIndex) {
        this(blockCoordinate.B(), blockCoordinate.E(), blockCoordinate.A(), facingIndex);
    }

    public DirectionalPosition(double x, double y, double z, int facingIndex) {
        super(x, y, z);
        this.facingIndex = facingIndex;
    }

    public DirectionalPosition(int x, int y, int z, int facingIndex) {
        super(x, y, z);
        this.facingIndex = facingIndex;
    }

    @Override
    public String toString() {
        return "BlockLocation.SideHit{x=" + this.B() + ", y=" + this.E() + ", z=" + this.A() + ", sideHit=" + this.facingIndex + '}';
    }

    public EnumFacing getFacing() {
        return this.facingIndex == -1 ? null : EnumFacing.T(this.facingIndex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.facingIndex);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        if (!super.equals(object)) {
            return false;
        }
        DirectionalPosition directionalPosition = (DirectionalPosition)object;
        return this.facingIndex == directionalPosition.facingIndex;
    }


    public int getFacingIndex() {
        return this.facingIndex;
    }
}

