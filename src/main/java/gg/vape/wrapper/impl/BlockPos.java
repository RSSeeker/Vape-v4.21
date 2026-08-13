package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MBlockPos;
import gg.vape.utils.MathUtil;
import gg.vape.utils.datas.BlockCoordinate;
import gg.vape.utils.datas.BlockData;
import gg.vape.utils.math.NumericMathUtil;

import java.util.Objects;

public class BlockPos
extends Vec3i {
    public static long L(BlockPos blockPos) {
        return BlockPos.f(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    @Override
    public String toString() {
        return this.getX() + " " + this.getY() + " " + this.getZ();
    }

    public static long f(int n, int n2, int n3) {
        long l = 3241L;
        l = 3457689L * l + (long)n;
        l = 8734625L * l + (long)n2;
        l = 2873465L * l + (long)n3;
        return l;
    }

    public BlockPos(Object object) {
        super(object);
    }

    public BlockPos J(int n) {
        return this.g(EnumFacing.g("east"), n);
    }

    public BlockPos y() {
        return new BlockPos(MBlockPos.s(BlockPos.vapeInstance.getMappings().RP, this.I));
    }

    public BlockPos W(int n) {
        return this.g(EnumFacing.g("up"), n);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof BlockData) {
            return ((BlockData)object).y(this);
        }
        if (object instanceof BlockPos) {
            return ((BlockPos)object).getX() == this.getX() && ((BlockPos)object).getY() == this.getY() && ((BlockPos)object).getZ() == this.getZ();
        }
        return false;
    }


    public BlockPos C(int n, int n2, int n3) {
        return BlockPos.create(this.getX() + n, this.getY() + n2, this.getZ() + n3);
    }

    public BlockPos X$src$Lgg_vape_wrapper_impl_BlockPos_$jlnp6b() {
        return new BlockPos(MBlockPos.B(BlockPos.vapeInstance.getMappings().RP, this.I));
    }

    public static BlockPos P(Vec3 vec3) {
        if (ForgeVersion.MC_1_20_6.d()) {
            return BlockPos.create(NumericMathUtil.floorDouble(vec3.getX()), NumericMathUtil.floorDouble(vec3.getY()), NumericMathUtil.floorDouble(vec3.getZ()));
        }
        return new BlockPos(BlockPos.vapeInstance.getMappings().RP.D(vec3.getObject()));
    }

    public BlockPos n(int n) {
        return this.g(EnumFacing.g("down"), n);
    }

    public BlockPos j() {
        return new BlockPos(MBlockPos.O(BlockPos.vapeInstance.getMappings().RP, this.I));
    }

    public BlockPos g(EnumFacing enumFacing, int n) {
        return new BlockPos(BlockPos.vapeInstance.getMappings().RP.offset(this.I, enumFacing.getObject(), n));
    }

    public static long p(double d, double d2, double d3) {
        return BlockPos.f(MathUtil.floor(d), MathUtil.floor(d2), MathUtil.floor(d3));
    }

    public BlockPos E(int n) {
        return this.g(EnumFacing.g("south"), n);
    }

    public BlockPos v(int n) {
        return this.g(EnumFacing.g("north"), n);
    }

    public BlockPos G() {
        return new BlockPos(MBlockPos.b(BlockPos.vapeInstance.getMappings().RP, this.I));
    }

    public BlockPos offset(EnumFacing enumFacing) {
        return this.g(enumFacing, 1);
    }

    public int hashCode() {
        return Objects.hash(this.getX(), this.getY(), this.getZ());
    }

    public static BlockPos d(BlockData blockData) {
        return BlockPos.create(blockData.D(), blockData.B(), blockData.G());
    }

    public BlockCoordinate X() {
        return new BlockCoordinate(this.getX(), this.getY(), this.getZ());
    }

    public static BlockPos D(double d, double d2, double d3) {
        if (ForgeVersion.MC_1_20_6.d()) {
            return BlockPos.create(NumericMathUtil.floorDouble(d), NumericMathUtil.floorDouble(d2), NumericMathUtil.floorDouble(d3));
        }
        return new BlockPos(BlockPos.vapeInstance.getMappings().RP.H(d, d2, d3));
    }

    public BlockPos e(int n) {
        return this.g(EnumFacing.g("west"), n);
    }

    public BlockPos d$src$Lgg_vape_wrapper_impl_BlockPos_$6vry9r() {
        return new BlockPos(MBlockPos.z(BlockPos.vapeInstance.getMappings().RP, this.I));
    }

    public BlockPos o$src$Lgg_vape_wrapper_impl_BlockPos_$10np7re() {
        return new BlockPos(MBlockPos.H(BlockPos.vapeInstance.getMappings().RP, this.I));
    }

    public static BlockPos create(int n, int n2, int n3) {
        return new BlockPos(BlockPos.vapeInstance.getMappings().RP.q(n, n2, n3));
    }
}

