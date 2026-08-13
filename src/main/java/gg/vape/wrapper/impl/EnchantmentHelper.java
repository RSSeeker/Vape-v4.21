package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MEnchantmentHelper;
import gg.vape.utils.EnchantmentUtil;
import gg.vape.wrapper.Wrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class EnchantmentHelper
extends Wrapper {
    public static float C(ItemStack itemStack, EnumCreatureAttribute enumCreatureAttribute) {
        if (ForgeVersion.MC_1_21_0.d()) {
            return EnchantmentHelper.d(itemStack, enumCreatureAttribute);
        }
        Object object = enumCreatureAttribute == null ? null : enumCreatureAttribute.getObject();
        return MEnchantmentHelper.w(EnchantmentHelper.vapeInstance.getMappings().Rk, itemStack.getObject(), object);
    }

    public EnchantmentHelper(Object object) {
        super(object);
    }

    public static HashMap<Holder, Integer> Z(ItemStack itemStack) {
        HashMap<Holder, Integer> hashMap = new HashMap<Holder, Integer>();
        if (itemStack.isNull() || itemStack.t() <= 0) {
            return hashMap;
        }
        PotionVersionRange potionVersionRange = new PotionVersionRange(itemStack.w(DataComponents.O()));
        for (Object entryObject : potionVersionRange.entrySet()) {
            ObjectToIntMapEntry entry = new ObjectToIntMapEntry(entryObject);
            Holder holder = new Holder(entry.getKey());
            hashMap.put(holder, entry.getIntValue());
        }
        return hashMap;
    }

    public static boolean Q(ItemStack itemStack) {
        return MEnchantmentHelper.z(EnchantmentHelper.vapeInstance.getMappings().Rk, itemStack.getObject());
    }

    public static EnchantmentModifierDamage getDamageModifier() {
        if (ForgeVersion.MC_1_16_5.d()) {
            return null;
        }
        return new EnchantmentModifierDamage(MEnchantmentHelper.getDamageModifier(EnchantmentHelper.vapeInstance.getMappings().Rk));
    }

    public static void J(EnchantmentModifier enchantmentModifier, ItemStack[] itemStackArray) {
        if (ForgeVersion.MC_1_12_2.d()) {
            ArrayList<Object> arrayList = new ArrayList<Object>();
            for (ItemStack itemStack : itemStackArray) {
                arrayList.add(itemStack.getObject());
            }
            MEnchantmentHelper.T(EnchantmentHelper.vapeInstance.getMappings().Rk, enchantmentModifier.getObject(), arrayList);
            return;
        }
        Object[] objectArray = new Object[itemStackArray.length];
        for (int i = 0; i < itemStackArray.length; ++i) {
            objectArray[i] = itemStackArray[i].getObject();
        }
        MEnchantmentHelper.o(EnchantmentHelper.vapeInstance.getMappings().Rk, enchantmentModifier.getObject(), objectArray);
    }

    private static float d(ItemStack itemStack, EnumCreatureAttribute enumCreatureAttribute) {
        EnumCreatureAttribute enumCreatureAttribute2;
        int n;
        if (itemStack == null || itemStack.isNull()) {
            return 0.0f;
        }
        float f = 0.0f;
        Enchantment enchantment = Enchantment.sharpness();
        if (enchantment != null && !enchantment.isNull() && (n = EnchantmentHelper.q(enchantment.getId(), itemStack)) > 0) {
            f += 0.5f + 0.5f * (float)n;
        }
        if (enumCreatureAttribute != null && !enumCreatureAttribute.isNull() && (enumCreatureAttribute2 = EnumCreatureAttribute.undefined()) != null && !enumCreatureAttribute2.isNull() && enumCreatureAttribute.getObject() != enumCreatureAttribute2.getObject()) {
            int n2;
            Enchantment enchantment2;
            int n3;
            Enchantment enchantment3 = Enchantment.smite();
            if (enchantment3 == null || enchantment3.isNull() || (n3 = EnchantmentHelper.q(enchantment3.getId(), itemStack)) > 0) {
                // empty if block
            }
            if ((enchantment2 = Enchantment.baneOfArthropods()) == null || enchantment2.isNull() || (n2 = EnchantmentHelper.q(enchantment2.getId(), itemStack)) > 0) {
                // empty if block
            }
        }
        return f;
    }

    public static int q(int n, ItemStack itemStack) {
        if (n < 0) {
            return 0;
        }
        if (ForgeVersion.MC_1_12_2.d()) {
            Enchantment enchantment = Enchantment.getEnchantmentById(n);
            if (ForgeVersion.MC_1_21_4.d()) {
                return EnchantmentHelper.q(enchantment, itemStack);
            }
            return MEnchantmentHelper.W(EnchantmentHelper.vapeInstance.getMappings().Rk, enchantment.getObject(), itemStack.getObject());
        }
        return MEnchantmentHelper.h(EnchantmentHelper.vapeInstance.getMappings().Rk, n, itemStack.getObject());
    }

    public static int e(String string, ItemStack itemStack) {
        if (!ForgeVersion.MC_1_21_4.d()) {
            return 0;
        }
        Optional<Holder> optional = EnchantmentUtil.K(string);
        if (!optional.isPresent()) {
            return 0;
        }
        return MEnchantmentHelper.X(EnchantmentHelper.vapeInstance.getMappings().Rk, optional.get().getObject(), itemStack.getObject());
    }

    private static void lambda$getEnchantmentLevel$0(ResourceKey resourceKey, int[] nArray, Holder holder, Integer n) {
        if (holder.F(resourceKey) && nArray[0] > n) {
            nArray[0] = n;
        }
    }

    public static int B(ItemStack[] itemStackArray, DamageSource damageSource) {
        if (ForgeVersion.MC_1_21_0.d()) {
            return 0;
        }
        if (ForgeVersion.MC_1_12_2.d()) {
            ArrayList<Object> arrayList = new ArrayList<Object>();
            for (ItemStack itemStack : itemStackArray) {
                arrayList.add(itemStack.getObject());
            }
            return MEnchantmentHelper.N(EnchantmentHelper.vapeInstance.getMappings().Rk, arrayList, damageSource.getObject());
        }
        Object[] objectArray = new Object[itemStackArray.length];
        for (int i = 0; i < itemStackArray.length; ++i) {
            objectArray[i] = itemStackArray[i].getObject();
        }
        return MEnchantmentHelper.p(EnchantmentHelper.vapeInstance.getMappings().Rk, objectArray, damageSource.getObject());
    }

    public static float N(Object object, ItemStack itemStack, Entity entity, DamageSource damageSource, float f) {
        return MEnchantmentHelper.F(EnchantmentHelper.vapeInstance.getMappings().Rk, object, itemStack.getObject(), entity.getObject(), damageSource.getObject(), f);
    }


    public static int q(Enchantment enchantment, ItemStack itemStack) {
        if (ForgeVersion.MC_1_21_4.d()) {
            WorldClient worldClient = Minecraft.theWorld();
            if (worldClient.isNull()) {
                return 0;
            }
            RegistryAccess registryAccess = worldClient.e();
            Registry registry = registryAccess.lookupOrThrow(ResourceKeyEnchantmentBridge.enchantment());
            Holder holder = registry.J(enchantment.getObject());
            return MEnchantmentHelper.X(EnchantmentHelper.vapeInstance.getMappings().Rk, holder.getObject(), itemStack.getObject());
        }
        return MEnchantmentHelper.X(EnchantmentHelper.vapeInstance.getMappings().Rk, enchantment.getObject(), itemStack.getObject());
    }

    public static int y(Entity entity) {
        if (ForgeVersion.MC_1_21_0.d()) {
            return EnchantmentHelper.a(Enchantments.O(), new EntityLiving(entity.getObject()));
        }
        return MEnchantmentHelper.a(EnchantmentHelper.vapeInstance.getMappings().Rk, entity.getObject());
    }

    public static PotionVersionRange m(ItemStack itemStack) {
        return new PotionVersionRange(MEnchantmentHelper.l(EnchantmentHelper.vapeInstance.getMappings().Rk, itemStack.getObject()));
    }

    public static int a(ResourceKey resourceKey, EntityLiving entityLiving) {
        ArrayList<ItemStack> arrayList = entityLiving.I$src$Ljava_util_ArrayList_$15zosdi();
        arrayList.add(entityLiving.B$src$Lgg_vape_wrapper_impl_ItemStack_$impdvt());
        int[] nArray = new int[]{0};
        for (ItemStack itemStack : arrayList) {
            HashMap<Holder, Integer> hashMap = EnchantmentHelper.Z(itemStack);
            hashMap.forEach((arg_0, arg_1) -> EnchantmentHelper.lambda$getEnchantmentLevel$0(resourceKey, nArray, arg_0, arg_1));
        }
        return nArray[0];
    }
}

