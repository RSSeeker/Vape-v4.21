package gg.vape.utils.datas;

import gg.vape.utils.MathUtil;
import gg.vape.wrapper.impl.AxisAlignedBB;
import gg.vape.wrapper.impl.BlockPos;
import gg.vape.wrapper.impl.EnumFacing;
import gg.vape.wrapper.impl.Vec3i;
import java.util.Objects;

public class BlockData {
    private final int x;
    private final int y;
    private final int z;
    private static String[] sharedStrings = new String[3];

    public BlockData(int n, int n2, int n3) {
        this.x = n;
        this.y = n2;
        this.z = n3;
    }

    public BlockData(double d, double d2, double d3) {
        this(MathUtil.floor(d), MathUtil.floor(d2), MathUtil.floor(d3));
    }

    public static BlockData E(BlockPos blockPos) {
        return new BlockData(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    public static BlockData P(AxisAlignedBB axisAlignedBB) {
        return new BlockData(axisAlignedBB.getMinX(), axisAlignedBB.getMinY(), axisAlignedBB.getMinZ());
    }

    public static void y(String[] stringArray) {
        sharedStrings = stringArray;
    }

    public static String[] W() {
        return sharedStrings;
    }

    public int D() {
        return this.x;
    }

    public int B() {
        return this.y;
    }

    public int G() {
        return this.z;
    }

    public boolean L(BlockData blockData) {
        return this.D() == blockData.D() && this.B() == blockData.B() && this.G() == blockData.G();
    }

    public boolean L(int n, int n2, int n3) {
        return this.D() == n && this.B() == n2 && this.G() == n3;
    }

    public boolean y(BlockPos blockPos) {
        return this.D() == blockPos.getX() && this.B() == blockPos.getY() && this.G() == blockPos.getZ();
    }

    public BlockData y(int n, int n2, int n3) {
        return new BlockData(this.D() + n, this.B() + n2, this.G() + n3);
    }

    public BlockData R(EnumFacing enumFacing) {
        Vec3i directionVector = enumFacing.getDirectionVector();
        return new BlockData(this.D() + directionVector.getX(), this.B() + directionVector.getY(), this.G() + directionVector.getZ());
    }

    public boolean equals(Object object) {
        return object == this || object instanceof BlockData && this.L((BlockData)object);
    }

    public int hashCode() {
        return Objects.hash(this.x, this.y, this.z);
    }

    public String toString() {
        return "[" + this.x + ", " + this.y + ", " + this.z + "]";
    }
}
