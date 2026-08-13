package gg.vape.utils.datas;

import gg.vape.utils.MathUtil;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.impl.BlockPos;
import gg.vape.wrapper.impl.EnumFacing;
import gg.vape.wrapper.impl.Vec3;
import gg.vape.wrapper.impl.Vec3i;
import java.util.Objects;

public class BlockCoordinate {
    private final int B;
    private final int F;
    private final int o;


    public Vec3 P() {
        return Vec3.create(this.o, this.B, this.F);
    }

    public BlockCoordinate F(int n, int n2, int n3) {
        return new BlockCoordinate(this.B() + n, this.E() + n2, this.A() + n3);
    }

    public int hashCode() {
        return Objects.hash(this.o, this.B, this.F);
    }

    public int E() {
        return this.B;
    }

    public BlockCoordinate(int n, int n2, int n3) {
        this.o = n;
        this.B = n2;
        this.F = n3;
    }

    public int A() {
        return this.F;
    }

    public BlockPos E$src$Lgg_vape_wrapper_impl_BlockPos_$1bb1czr() {
        return BlockPos.create(this.o, this.B, this.F);
    }

    public BlockCoordinate i(EnumFacing enumFacing) {
        Vec3i directionVector = enumFacing.getDirectionVector();
        return new BlockCoordinate(this.B() + directionVector.getX(), this.E() + directionVector.getY(), this.A() + directionVector.getZ());
    }

    public BlockCoordinate(double d, double d2, double d3) {
        this.o = MathUtil.floor(d);
        this.B = MathUtil.floor(d2);
        this.F = MathUtil.floor(d3);
    }

    public BlockData O() {
        return new BlockData(this.o, this.B, this.F);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        BlockCoordinate blockCoordinate = (BlockCoordinate)object;
        return this.o == blockCoordinate.o && this.B == blockCoordinate.B && this.F == blockCoordinate.F;
    }

    public String toString() {
        return "BlockLocation{x=" + this.o + ", y=" + this.B + ", z=" + this.F + '}';
    }

    public int B() {
        return this.o;
    }

    public BlockCoordinate(BlockData blockData) {
        this.o = blockData.D();
        this.B = blockData.B();
        this.F = blockData.G();
    }
}

