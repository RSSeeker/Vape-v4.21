package gg.vape.utils;

import gg.vape.mapping.MappedClasses;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.impl.AxisAlignedBB;
import gg.vape.wrapper.impl.Block;
import gg.vape.wrapper.impl.BlockPos;
import gg.vape.wrapper.impl.BlockReaderBridge;
import gg.vape.wrapper.impl.BlockState;
import gg.vape.wrapper.impl.Blocks;
import gg.vape.wrapper.impl.Chunk;
import gg.vape.wrapper.impl.ChunkSection;
import gg.vape.wrapper.impl.EntityFishHook;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemBlock;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.Material;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.NonNullList;
import gg.vape.wrapper.impl.World;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class BlockUtil {
    private static final Pattern bedNamePattern = Pattern.compile("block.minecraft.(.+_bed)");
    private static int[] cachedBedBlockIds = null;

    public static boolean b(Block block) {
        if (ForgeVersion.MC_1_20_6.d()) {
            return block.a().g() && block.a().Y();
        }
        Material material = block.H();
        return material.isSolid() && material.blocksMovement();
    }

    public static boolean t(Block block) {
        if (!BlockUtil.C(block)) {
            return false;
        }
        return false;
    }

    public static int[] r(String string) {
        Object object;
        int[] nArray = new int[]{-1, 0};
        Map map = Item.y();
        if (map != null) {
            Set set = map.keySet();
            Iterator iterator = set.iterator();
            while (iterator.hasNext()) {
                Object k = iterator.next();
                Object v = map.get(k);
                Block block = new Block(k);
                Item item = new Item(v);
                int n = BlockUtil.resolveItemMetadata(string, item);
                if (n == -1) continue;
                nArray[0] = Block.R(block);
                nArray[1] = n;
            }
        }
        if ((object = Block.t(string.replace(" ", "_"))) != null) {
            nArray[0] = Block.R((Block)object);
        }
        return nArray;
    }

    public static BlockState E(ItemStack itemStack) {
        if (itemStack.isNull() || itemStack.getItem().isNull()) {
            return Blocks.air().Z();
        }
        Item item = itemStack.getItem();
        if (!item.isInstance(MappedClasses.Vw)) {
            return Blocks.air().Z();
        }
        ItemBlock itemBlock = new ItemBlock(item);
        Block block = itemBlock.C();
        return block.Z();
    }

    public static boolean k(Block block) {
        return !BlockUtil.J(block) && BlockUtil.b(block);
    }

    public static boolean p(Block block) {
        if (ForgeVersion.MC_1_20_6.d()) {
            return block.isInstance(MappedClasses.uY);
        }
        Material material = block.H();
        return material.equals(Material.air());
    }

    public static boolean u(Block block) {
        if (ForgeVersion.MC_1_20_6.d()) {
            return block.isInstance(MappedClasses.uY) || block.a().u();
        }
        Material material = block.H();
        return material.equals(Material.air()) || material.isReplaceable();
    }

    private static int resolveItemMetadata(String itemName, Item item) {
        if (item.isNull()) {
            return -1;
        }
        ItemStack itemStack = ItemStack.S(item);
        if (itemStack.isNull()) {
            return -1;
        }
        List list = new ArrayList();
        if (ForgeVersion.MC_1_12_2.d()) {
            list = (List)NonNullList.create().getObject();
        }
        item.D(item, list);
        if (list.size() > 0) {
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                Object e = iterator.next();
                ItemStack itemStack2 = new ItemStack(e);
                if (!itemStack2.x().equalsIgnoreCase(itemName)) continue;
                return itemStack2.L();
            }
        }
        String resolvedItemName = itemStack.x().toLowerCase();
        if (resolvedItemName.equalsIgnoreCase(itemName)) {
            return itemStack.L();
        }
        return -1;
    }

    public static boolean z(World world, BlockPos blockPos, BlockState blockState) {
        BlockPos blockPos2 = blockPos;
        Chunk chunk = world.j(blockPos);
        int n = blockPos2.getY();
        int n2 = blockPos2.getX() & 0xF;
        int n3 = n & 0xF;
        int n4 = blockPos2.getZ() & 0xF;
        Block block = blockState.getBlock();
        int n5 = chunk.q(n);
        Object object = chunk.R()[n5];
        if (object == null) {
            if (block.equals(Blocks.air())) {
                return false;
            }
            Object object2 = ChunkSection.u(n5 << 4, ForgeVersion.MC_1_16_5.v() && !world.getWorldProvider().hasNoSky());
            chunk.R()[n5] = object2;
            object = object2;
        }
        ChunkSection chunkSection = new ChunkSection(object);
        if (ForgeVersion.MC_1_8_9.Y()) {
            chunkSection.g(n2, n3, n4, blockState);
        } else {
            int n6 = Block.R(blockState.getBlock()) << 4;
            chunkSection.C()[n3 << 8 | n4 << 4 | n2] = (char)n6;
        }
        return true;
    }

    private static int[] getBedBlockIds() {
        if (cachedBedBlockIds == null) {
            cachedBedBlockIds = ForgeVersion.MC_1_20_6.d() ? BlockUtil.Z(" bed") : new int[]{26};
        }
        return cachedBedBlockIds;
    }

    public static int[] Z(String string) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        Map map = Item.y();
        if (map != null) {
            Set set = map.keySet();
            for (Object k : set) {
                Object v = map.get(k);
                Block block = new Block(k);
                Item item = new Item(v);
                int n = BlockUtil.E(string, item);
                if (n == -1) continue;
                arrayList.add(Block.R(block));
            }
        }
        return arrayList.stream().mapToInt(Integer::intValue).distinct().toArray();
    }

    public static float K(Block block) {
        return block.P();
    }

    public static boolean C(Block block) {
        if (ForgeVersion.MC_1_20_6.d()) {
            return block.a().x();
        }
        return block.H().isLiquid();
    }

    public static boolean e(EntityPlayerSP entityPlayerSP, BlockData blockData) {
        AxisAlignedBB axisAlignedBB = entityPlayerSP.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl().expand(0.01, 0.0, 0.01);
        boolean bl = axisAlignedBB.getMinX() > (double)blockData.D() && axisAlignedBB.getMaxX() < (double)(blockData.D() + 1);
        boolean bl2 = axisAlignedBB.getMinZ() > (double)blockData.G() && axisAlignedBB.getMaxZ() < (double)(blockData.G() + 1);
        return bl && bl2;
    }


    public static AxisAlignedBB F(World world, BlockData blockData) {
        AxisAlignedBB axisAlignedBB = AxisAlignedBB.create((double)blockData.D() + 0.0, (double)blockData.B() + 0.0, (double)blockData.G() + 0.0, (double)blockData.D() + 1.0, (double)blockData.B() + 1.0, (double)blockData.G() + 1.0);
        Block block = world.getBlockByPos(blockData.D(), blockData.B(), blockData.G());
        if (block.isNotNull() && !BlockUtil.J(block)) {
            if (ForgeVersion.MC_1_7_10.B()) {
                axisAlignedBB = block.M(world, blockData.D(), blockData.B(), blockData.G());
            } else {
                BlockPos blockPos = BlockPos.d(blockData);
                if (ForgeVersion.MC_1_16_5.d()) {
                    BlockState blockState = world.getBlockState(blockPos);
                    if (blockState.isInstance(MappedClasses.Fj)) {
                        BlockReaderBridge blockReaderBridge = new BlockReaderBridge(blockState);
                        EntityFishHook entityFishHook = blockReaderBridge.getShape(Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf().l().x$src$Lgg_vape_wrapper_impl_BlockReader_$120g8sh(), blockPos);
                        if (entityFishHook.isNotNull()) {
                            axisAlignedBB = entityFishHook.n();
                            if (ForgeVersion.MC_1_16_5.d()) {
                                axisAlignedBB = axisAlignedBB.A(blockData.D(), blockData.B(), blockData.G());
                            }
                        }
                    } else {
                        axisAlignedBB = block.Q(world, blockPos);
                    }
                } else {
                    axisAlignedBB = ForgeVersion.MC_1_12_2.L() ? block.Y(world.getBlockState(blockPos), world, blockPos) : block.Q(world, blockPos);
                }
            }
        }
        return axisAlignedBB;
    }

    public static char t(int n, int n2) {
        return (char)(n << 4 | n2 & 0xF);
    }

    public static float O(ItemStack itemStack) {
        if (itemStack.isNull() || itemStack.getItem().isNull()) {
            return 0.0f;
        }
        Item item = itemStack.getItem();
        if (!item.isInstance(MappedClasses.Vw)) {
            return 0.0f;
        }
        ItemBlock itemBlock = new ItemBlock(item);
        return BlockUtil.K(itemBlock.C());
    }

    public static boolean J(Block block) {
        if (ForgeVersion.MC_1_20_6.d()) {
            return BlockUtil.C(block) || BlockUtil.p(block);
        }
        Material material = block.H();
        return material.isLiquid() || material.equals(Material.air()) || material.equals(Material.fire()) && ForgeVersion.MC_1_16_5.v();
    }

    public static boolean f(Block block) {
        int n = Block.R(block);
        String string = block.U();
        return n == 26 || string != null && bedNamePattern.matcher(string).matches();
    }

    private static int E(String string, Item item) {
        if (item.isNull()) {
            return -1;
        }
        ItemStack itemStack = ItemStack.S(item);
        if (itemStack.isNull()) {
            return -1;
        }
        List list = new ArrayList();
        if (ForgeVersion.MC_1_12_2.d()) {
            list = (List)NonNullList.create().getObject();
        }
        item.D(item, list);
        if (list.size() > 0) {
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                Object e = iterator.next();
                ItemStack itemStack2 = new ItemStack(e);
                if (!itemStack2.x().contains(string)) continue;
                return itemStack2.L();
            }
        }
        String itemName = itemStack.x().toLowerCase();
        if (itemName.contains(string)) {
            return itemStack.L();
        }
        return -1;
    }

    public static boolean v(Character c) {
        char c2 = c.charValue();
        int n = c2 >> 4;
        for (int n2 : BlockUtil.getBedBlockIds()) {
            if (n != n2) continue;
            return true;
        }
        return false;
    }

    public static int E() {
        Block block = Minecraft.p$src$Lgg_vape_wrapper_impl_RayTraceResult_$5rw6n0().Z$src$Lgg_vape_wrapper_impl_Block_$6x2c9a();
        if (block.isNull()) {
            return -1;
        }
        return Block.R(block);
    }

    public static boolean e(Block block) {
        if (ForgeVersion.MC_1_20_6.d()) {
            return block.a().Y();
        }
        return block.H().blocksMovement();
    }
}
