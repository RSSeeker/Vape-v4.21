package gg.vape.utils;

import gg.vape.utils.EnchantmentUtil;
import gg.vape.utils.ItemStackScoreUtil;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.EnumHand;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemArmor;
import gg.vape.wrapper.impl.ItemStack;
import java.util.Arrays;

public class ItemStackFingerprint {
    private static final long I = -7046029254386353131L;

    public static long t(ItemStack[] itemStackArray) {
        long l = -7046029254386353131L;
        for (ItemStack itemStack : itemStackArray) {
            if (itemStack == null) continue;
            long l2 = ItemStackFingerprint.t(itemStack);
            l ^= l2 + -7046029254386353131L + (l << 6) + (l >> 2);
        }
        return l;
    }

    public static long t(ItemStack itemStack) {
        ItemArmor itemArmor;
        int n;
        boolean bl;
        Item item = itemStack.getItem();
        int n2 = item.P();
        int n3 = itemStack.L();
        int[] nArray = EnchantmentUtil.e(itemStack);
        long l = ItemStackFingerprint.d(n2, n3, nArray);
        int n4 = item.a();
        boolean bl2 = bl = n4 > 0 && n3 > 0;
        if (ItemStackScoreUtil.R(item) && ForgeVersion.MC_1_8_9.B() && (n = (itemArmor = new ItemArmor(item)).Y(itemStack)) != -1) {
            l = l * 31L + (long)n;
        }
        return l * 31L + (long)(bl ? 1 : 0);
    }

    public static int h(int n, int n2) {
        if (n < 0 || n >= 65536) {
            throw new IllegalArgumentException("enchantId must be between 0 and 65535");
        }
        if (n2 < 0 || n2 >= 65536) {
            throw new IllegalArgumentException("enchantLevel must be between 0 and 65535");
        }
        return (n & 0xFFFF) << 16 | n2 & 0xFFFF;
    }

    public static int d(int n, int n2) {
        if (n < 0 || n >= 65536) {
            throw new IllegalArgumentException("itemId must be between 0 and 65535");
        }
        if (n2 < 0 || n2 >= 65536) {
            throw new IllegalArgumentException("metaId must be between 0 and 65535");
        }
        return (n & 0xFFFF) << 16 | n2 & 0xFFFF;
    }

    public static ItemStack[] T$src$ALgg_vape_wrapper_impl_ItemStack_$f6ukg1(EntityPlayer entityPlayer) {
        boolean bl = ForgeVersion.MC_1_12_2.d();
        if (bl) {
            ItemStack[] itemStackArray = new ItemStack[6];
            ItemStack itemStack = entityPlayer.B$src$Lgg_vape_wrapper_impl_ItemStack_$impdvt();
            if (itemStack.isNotNull()) {
                itemStackArray[0] = itemStack;
            }
            Object[] objectArray = entityPlayer.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().i();
            int n = objectArray.length;
            for (int i = 0; i < n; ++i) {
                ItemStack itemStack2 = new ItemStack(objectArray[n - 1 - i]);
                if (itemStack2.isNull()) continue;
                itemStackArray[i + 1] = itemStack2;
            }
            ItemStack itemStack3 = entityPlayer.i(EnumHand.offHand());
            if (itemStack3 != null && itemStack3.isNotNull()) {
                itemStackArray[5] = itemStack3;
            }
            return itemStackArray;
        }
        ItemStack[] itemStackArray = new ItemStack[5];
        ItemStack itemStack = entityPlayer.B$src$Lgg_vape_wrapper_impl_ItemStack_$impdvt();
        if (itemStack.isNotNull()) {
            itemStackArray[0] = itemStack;
        }
        Object[] objectArray = entityPlayer.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().i();
        int n = objectArray.length;
        for (int i = 0; i < n; ++i) {
            ItemStack itemStack4 = new ItemStack(objectArray[n - 1 - i]);
            if (itemStack4.isNull()) continue;
            itemStackArray[i + 1] = itemStack4;
        }
        return itemStackArray;
    }

    private static IllegalArgumentException a(IllegalArgumentException illegalArgumentException) {
        return illegalArgumentException;
    }

    public static long d(int n, int n2, int[] nArray) {
        int n3 = ItemStackFingerprint.d(n, n2);
        int n4 = ItemStackFingerprint.b(nArray);
        return ((long)n4 & 0xFFFFFFFFL) << 32 | (long)n3 & 0xFFFFFFFFL;
    }

    public static int b(int[] nArray) {
        int n;
        if (nArray == null) {
            return 0;
        }
        if (nArray.length % 2 != 0) {
            throw new IllegalArgumentException("enchantments array length must be even " + nArray);
        }
        int n2 = nArray.length / 2;
        int[] nArray2 = new int[n2];
        for (n = 0; n < n2; ++n) {
            nArray2[n] = ItemStackFingerprint.h(nArray[2 * n], nArray[2 * n + 1]);
        }
        Arrays.sort(nArray2);
        n = 1;
        for (int n3 : nArray2) {
            n = 31 * n + n3;
        }
        return n;
    }

    public static long T(EntityPlayer entityPlayer) {
        return ItemStackFingerprint.t(ItemStackFingerprint.T$src$ALgg_vape_wrapper_impl_ItemStack_$f6ukg1(entityPlayer));
    }
}
