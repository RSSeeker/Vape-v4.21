package gg.vape.utils;

import gg.vape.Vape;
import gg.vape.mapping.ItemMappingEntry;
import gg.vape.mapping.MappedClasses;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ArmorMaterial;
import gg.vape.wrapper.impl.AttributeModifier;
import gg.vape.wrapper.impl.ChestTypeHolder;
import gg.vape.wrapper.impl.Container;
import gg.vape.wrapper.impl.DataComponentMap;
import gg.vape.wrapper.impl.DataComponents;
import gg.vape.wrapper.impl.Enchantment;
import gg.vape.wrapper.impl.EnchantmentHelper;
import gg.vape.wrapper.impl.EntityEquipmentSlot;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.EnumCreatureAttribute;
import gg.vape.wrapper.impl.EquipmentSlotGroup;
import gg.vape.wrapper.impl.Equippable;
import gg.vape.wrapper.impl.FoodProperties;
import gg.vape.wrapper.impl.Foods;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemArmor;
import gg.vape.wrapper.impl.ItemAttributeModifiers;
import gg.vape.wrapper.impl.ItemAttributeModifiersComponent;
import gg.vape.wrapper.impl.ItemAttributeModifiersComponent$Entry;
import gg.vape.wrapper.impl.ItemCameraTransformBase;
import gg.vape.wrapper.impl.ItemCameraTransformLeaf;
import gg.vape.wrapper.impl.ItemCameraTransformSubtypeValue;
import gg.vape.wrapper.impl.ItemCameraTransformType;
import gg.vape.wrapper.impl.ItemSplashPotion;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.ItemSword;
import gg.vape.wrapper.impl.ItemTool;
import gg.vape.wrapper.impl.MonsterAttributesBridge;
import gg.vape.wrapper.impl.NonNullList;
import gg.vape.wrapper.impl.PotionEffect;
import gg.vape.wrapper.impl.PotionRegistry;
import gg.vape.wrapper.impl.ResourceKey;
import gg.vape.wrapper.impl.ResourceKeyHolder;
import gg.vape.wrapper.impl.ResourceLocation;
import gg.vape.wrapper.impl.Slot;
import gg.vape.wrapper.impl.StatusEffect;
import gg.vape.wrapper.impl.ToolMaterial;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ItemStackScoreUtil {
    private static final List<ItemStack> w = new ArrayList<ItemStack>();

    public static float a$src$F$2aw1mh(ItemStack itemStack) {
        if (itemStack == null || itemStack.isNull()) {
            return 0.0f;
        }
        Item item = itemStack.getItem();
        if (!ItemStackScoreUtil.h(item) && !ItemStackScoreUtil.I(item)) {
            return 0.0f;
        }
        float f = 0.0f;
        if (ForgeVersion.MC_1_21_4.d()) {
            DataComponentMap dataComponentMap = item.g();
            Object object = dataComponentMap.E(DataComponents.E());
            ItemAttributeModifiersComponent itemAttributeModifiersComponent = new ItemAttributeModifiersComponent(object);
            EquipmentSlotGroup equipmentSlotGroup = MonsterAttributesBridge.t();
            List<ItemAttributeModifiersComponent$Entry> list = itemAttributeModifiersComponent.getEntries();
            for (ItemAttributeModifiersComponent$Entry itemAttributeModifiersComponent$Entry : list) {
                EquipmentSlotGroup equipmentSlotGroup2 = itemAttributeModifiersComponent$Entry.getEquipmentSlotGroup();
                AttributeModifier attributeModifier = itemAttributeModifiersComponent$Entry.getModifier();
                if (!equipmentSlotGroup2.equals(equipmentSlotGroup)) continue;
                double d = attributeModifier.getAmount();
                f += (float)d;
            }
        } else if (ItemStackScoreUtil.h(item)) {
            ItemTool itemTool = new ItemTool(item.getObject());
            f = 4.0f + itemTool.p$src$F$7elb4m();
        } else {
            ItemSword itemSword = new ItemSword(item.getObject());
            f = itemSword.R();
        }
        return f;
    }

    public static boolean V$src$Z$dcbuai(ItemStack itemStack) {
        return ItemStackScoreUtil.I(itemStack.getItem());
    }

    public static boolean g$src$Z$z0w7fv(ItemStack itemStack) {
        return ItemStackScoreUtil.T(itemStack.getItem());
    }

    public static double d(ItemStack itemStack) {
        if (itemStack.isNull()) {
            return 0.0;
        }
        Item item = itemStack.getItem();
        if (!ItemStackScoreUtil.R(item)) {
            return 0.0;
        }
        return ItemStackScoreUtil.P(itemStack) + (double)EnchantmentHelper.q(0, itemStack);
    }

    public static boolean o(ItemStack itemStack, boolean bl) {
        if (itemStack.isNull() || itemStack.getItem().isNull()) {
            return false;
        }
        if (bl && ItemStackScoreUtil.D(itemStack)) {
            return true;
        }
        if (!itemStack.getItem().isInstance(MappedClasses.Di)) {
            return false;
        }
        ItemSplashPotion itemSplashPotion = new ItemSplashPotion(itemStack.getItem().getObject());
        if (itemSplashPotion.getRawPotionEffects(itemStack) == null) {
            return false;
        }
        int n = 6;
        int n2 = 10;
        if (ForgeVersion.MC_1_20_6.d()) {
            --n;
            --n2;
        }
        StatusEffect statusEffect = PotionRegistry.z.getStatusEffect();
        StatusEffect statusEffect2 = PotionRegistry.i.getStatusEffect();
        if (ForgeVersion.MC_1_20_6.d() && (statusEffect == null || statusEffect2 == null)) {
            Vape.debugLog("WTF?");
            return false;
        }
        boolean bl2 = ForgeVersion.MC_1_20_6.d() && statusEffect != null && statusEffect2 != null;
        for (PotionEffect potionEffect : itemSplashPotion.getPotionEffects(itemStack)) {
            int n3;
            StatusEffect statusEffect3;
            if (!(ForgeVersion.MC_1_20_6.d() ? statusEffect.equals(statusEffect3 = potionEffect.i()) || statusEffect2.equals(statusEffect3) : (n3 = potionEffect.C()) == n || n3 == n2)) continue;
            return true;
        }
        return false;
    }

    public static double T(ItemStack itemStack) {
        double d = ItemStackScoreUtil.p(itemStack);
        if (d == 0.0) {
            return d;
        }
        if (!ItemStackScoreUtil.m(itemStack.getItem())) {
            return d;
        }
        int n = EnchantmentHelper.q(Enchantment.efficiency().getId(), itemStack);
        n = n > 0 ? n * n + 1 : 0;
        d += (double)n;
        d += 0.1 * (double)Math.max(0, EnchantmentHelper.q(Enchantment.unbreaking().getId(), itemStack));
        d += 0.1 * (double)Math.max(0, EnchantmentHelper.q(Enchantment.fortune().getId(), itemStack));
        return d += 0.1 * (double)Math.max(0, EnchantmentHelper.q(Enchantment.mending().getId(), itemStack));
    }

    public static boolean D(ItemStack itemStack) {
        return ItemStackScoreUtil.v(itemStack.getItem());
    }

    public static double a(ItemStack itemStack) {
        double d = ItemStackScoreUtil.a$src$F$2aw1mh(itemStack);
        return d += (double)EnchantmentHelper.C(itemStack, EnumCreatureAttribute.undefined());
    }

    public static float I$src$F$dh3k81(ItemStack itemStack) {
        if (itemStack == null || itemStack.isNull()) {
            return 0.0f;
        }
        Item item = itemStack.getItem();
        if (!ItemStackScoreUtil.h(item) && !ItemStackScoreUtil.I(item)) {
            return 0.0f;
        }
        float f = ItemStackScoreUtil.a$src$F$2aw1mh(itemStack);
        return (float)EnchantmentHelper.q(Enchantment.sharpness().getId(), itemStack) * 1.25f + f + (float)EnchantmentHelper.q(Enchantment.fireAspect().getId(), itemStack) * 0.01f;
    }

    public static float O(EntityPlayerSP entityPlayerSP) {
        float f = 0.0f;
        Container container = entityPlayerSP.F$src$Lgg_vape_wrapper_impl_Container_$152y6lm();
        for (int i = 0; i < 45; ++i) {
            ItemStack itemStack;
            float f2;
            Slot slot = container.getSlot(i);
            if (!slot.hasStack() || !((f2 = ItemStackScoreUtil.I$src$F$dh3k81(itemStack = slot.getStack())) > f)) continue;
            f = f2;
        }
        return f;
    }

    public static double u(ItemStack itemStack) {
        double d = ItemStackScoreUtil.p(itemStack);
        if (d == 0.0) {
            return d;
        }
        if (!ItemStackScoreUtil.k(itemStack.getItem())) {
            return d;
        }
        d += 0.1 * (double)Math.max(0, EnchantmentHelper.q(Enchantment.unbreaking().getId(), itemStack));
        return d += 0.1 * (double)Math.max(0, EnchantmentHelper.q(Enchantment.mending().getId(), itemStack));
    }

    public static boolean y(ItemStack itemStack) {
        return ItemStackScoreUtil.k(itemStack.getItem());
    }

    public static List<ItemStack> S() {
        if (w.isEmpty()) {
            ItemStackScoreUtil.w();
        }
        return w;
    }

    public static double k(ItemStack itemStack) {
        double d = ItemStackScoreUtil.a(itemStack);
        d += 0.01 * (double)Math.max(0, EnchantmentHelper.q(Enchantment.smite().getId(), itemStack));
        d += 0.01 * (double)Math.max(0, EnchantmentHelper.q(Enchantment.baneOfArthropods().getId(), itemStack));
        d += 0.01 * (double)Math.max(0, EnchantmentHelper.q(Enchantment.fireAspect().getId(), itemStack));
        d += 0.05 * (double)Math.max(0, EnchantmentHelper.q(Enchantment.knockback().getId(), itemStack));
        d += 0.01 * (double)Math.max(0, EnchantmentHelper.q(Enchantment.looting().getId(), itemStack));
        d += 0.01 * (double)Math.max(0, EnchantmentHelper.q(Enchantment.mending().getId(), itemStack));
        d += 0.01 * (double)Math.max(0, EnchantmentHelper.q(Enchantment.unbreaking().getId(), itemStack));
        return d += 0.05 * (double)Math.max(0, EnchantmentHelper.q(Enchantment.sweepingEdge().getId(), itemStack));
    }

    public static double V(ItemStack itemStack) {
        double d = 0.0;
        if (itemStack.isNull() || itemStack.getItem().isNull() || !ItemStackScoreUtil.I(itemStack.getItem())) {
            return d;
        }
        if (ForgeVersion.MC_1_21_4.d()) {
            d = ItemStackScoreUtil.a$src$F$2aw1mh(itemStack);
        } else {
            ItemAttributeModifiers itemAttributeModifiers = itemStack.o();
            if (itemAttributeModifiers.size() > 0) {
                int n = ForgeVersion.MC_1_12_2.L() ? 1 : 0;
                AttributeModifier attributeModifier = new AttributeModifier(itemAttributeModifiers.values().toArray()[n]);
                d = attributeModifier.getAmount();
            }
        }
        d += (double)EnchantmentHelper.C(itemStack, EnumCreatureAttribute.undefined());
        if (!ItemStackScoreUtil.T(itemStack.getItem())) {
            return d;
        }
        d += 0.1 * (double)Math.max(0, EnchantmentHelper.q(Enchantment.efficiency().getId(), itemStack));
        d += 0.01 * (double)Math.max(0, EnchantmentHelper.q(Enchantment.unbreaking().getId(), itemStack));
        d += 0.01 * (double)Math.max(0, EnchantmentHelper.q(Enchantment.fortune().getId(), itemStack));
        d += 0.01 * (double)Math.max(0, EnchantmentHelper.q(Enchantment.smite().getId(), itemStack));
        d += 0.01 * (double)Math.max(0, EnchantmentHelper.q(Enchantment.baneOfArthropods().getId(), itemStack));
        return d += 0.1 * (double)Math.max(0, EnchantmentHelper.q(Enchantment.mending().getId(), itemStack));
    }


    public static int t(ItemStack itemStack) {
        if (itemStack.isNull() || itemStack.getItem().isNull()) {
            return -1;
        }
        return ItemStackScoreUtil.H(itemStack.getItem());
    }

    public static boolean l(ItemStack itemStack) {
        return ItemStackScoreUtil.R(itemStack.getItem());
    }

    public static int H(Item item) {
        if (item.isNull()) {
            return -1;
        }
        if (!ItemStackScoreUtil.R(item)) {
            return -1;
        }
        int n = -1;
        if (ForgeVersion.MC_1_21_4.d()) {
            DataComponentMap dataComponentMap = item.g();
            Object object = dataComponentMap.E(DataComponents.k());
            if (object != null) {
                Equippable equippable = new Equippable(object);
                EntityEquipmentSlot entityEquipmentSlot = equippable.m$src$Lgg_vape_wrapper_impl_EntityEquipmentSlot_$bzr9md();
                n = entityEquipmentSlot.W();
            }
        } else {
            ItemArmor itemArmor = new ItemArmor(item);
            n = itemArmor.b$src$I$1fmozr4();
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            n = 3 - n;
        }
        return n;
    }

    public static boolean A(ItemStack itemStack) {
        return ItemStackScoreUtil.g(itemStack.getItem());
    }

    public static boolean T(Item item) {
        if (ForgeVersion.MC_1_21_4.d()) {
            String string = item.A();
            if (string == null || string.isEmpty()) {
                return false;
            }
            return string.contains("_axe");
        }
        return item.isInstance(MappedClasses.YP);
    }

    public static boolean i(ItemStack itemStack) {
        return ItemStackScoreUtil.o(itemStack, false);
    }

    public static Item g(String string) {
        Iterator iterator = Item.Y().D().iterator();
        String string2 = "";
        while (iterator.hasNext()) {
            Object object;
            if (ForgeVersion.MC_1_7_10.L()) {
                string2 = (String)iterator.next();
                object = Item.Y().S(string2);
            } else {
                ResourceLocation resourceLocation = new ResourceLocation(iterator.next());
                string2 = resourceLocation.getResourcePath();
                object = Item.Y().S(resourceLocation.getObject());
            }
            string2 = string2.replace("minecraft:", "");
            string2 = string2.toLowerCase();
            if (!MappedClasses.lb.isInstance(object)) continue;
            Item item = new Item(object);
            if (!string2.equalsIgnoreCase(string)) continue;
            return item;
        }
        return null;
    }

    private static int lambda$populateItems$0(ItemStack itemStack, ItemStack itemStack2) {
        int n;
        ItemMappingEntry itemMappingEntry = Vape.INSTANCE.getItemStackResolver().resolve(itemStack);
        ItemMappingEntry itemMappingEntry2 = Vape.INSTANCE.getItemStackResolver().resolve(itemStack2);
        int n2 = itemMappingEntry != null && itemMappingEntry.getLegacyId() != null ? itemMappingEntry.getLegacyId().intValue() : itemStack.getItem().P();
        int n3 = n = itemMappingEntry2 != null && itemMappingEntry2.getLegacyId() != null ? itemMappingEntry2.getLegacyId().intValue() : itemStack2.getItem().P();
        if (n2 == n) {
            n2 += itemMappingEntry != null && itemMappingEntry.getMetadata() != null ? itemMappingEntry.getMetadata() : 0;
            n += itemMappingEntry2 != null && itemMappingEntry2.getMetadata() != null ? itemMappingEntry2.getMetadata() : 0;
        }
        return Integer.compare(n2, n);
    }

    public static boolean R(Item item) {
        if (ForgeVersion.MC_1_21_6.d()) {
            Equippable equippable = new Equippable(item.b().w(DataComponents.k()));
            if (equippable.isNull()) {
                return false;
            }
            return equippable.m$src$Lgg_vape_wrapper_impl_EntityEquipmentSlot_$bzr9md().v();
        }
        if (ForgeVersion.MC_1_21_4.d()) {
            EquipmentSlotGroup equipmentSlotGroup = MonsterAttributesBridge.K();
            DataComponentMap dataComponentMap = item.g();
            Object object = dataComponentMap.E(DataComponents.E());
            ItemAttributeModifiersComponent itemAttributeModifiersComponent = new ItemAttributeModifiersComponent(object);
            List<ItemAttributeModifiersComponent$Entry> list = itemAttributeModifiersComponent.getEntries();
            for (ItemAttributeModifiersComponent$Entry itemAttributeModifiersComponent$Entry : list) {
                EquipmentSlotGroup equipmentSlotGroup2 = itemAttributeModifiersComponent$Entry.getEquipmentSlotGroup();
                if (!equipmentSlotGroup2.equals(equipmentSlotGroup)) continue;
                return true;
            }
            return false;
        }
        return item.isInstance(MappedClasses.qU);
    }

    public static boolean v(Item item) {
        if (ForgeVersion.MC_1_21_4.d()) {
            DataComponentMap dataComponentMap = item.g();
            Object object = dataComponentMap.E(DataComponents.d());
            if (MappedClasses.foodPropertiesClass.isInstance(object)) {
                FoodProperties foodProperties = new FoodProperties(object);
                if (Foods.mushroomStew().equals(foodProperties)) {
                    return true;
                }
            }
            return false;
        }
        return item.isInstance(MappedClasses.z);
    }

    public static boolean m(Item item) {
        if (ForgeVersion.MC_1_21_4.d()) {
            String string = item.A();
            if (string == null || string.isEmpty()) {
                return false;
            }
            return string.contains("_pickaxe");
        }
        return item.isInstance(MappedClasses.DU);
    }

    public static boolean h(Item item) {
        if (ForgeVersion.MC_1_21_4.d()) {
            String string = item.A();
            if (string == null || string.isEmpty()) {
                return false;
            }
            return string.contains("_sword");
        }
        return item.isInstance(MappedClasses.V5);
    }

    public static double P(ItemStack itemStack) {
        if (itemStack.isNull()) {
            return 0.0;
        }
        Item item = itemStack.getItem();
        if (!ItemStackScoreUtil.R(item)) {
            return 0.0;
        }
        double d = 0.0;
        if (ForgeVersion.MC_1_21_4.d()) {
            DataComponentMap dataComponentMap = item.g();
            Object object = dataComponentMap.E(DataComponents.E());
            ItemAttributeModifiersComponent itemAttributeModifiersComponent = new ItemAttributeModifiersComponent(object);
            EquipmentSlotGroup equipmentSlotGroup = MonsterAttributesBridge.K();
            List<ItemAttributeModifiersComponent$Entry> list = itemAttributeModifiersComponent.getEntries();
            for (ItemAttributeModifiersComponent$Entry itemAttributeModifiersComponent$Entry : list) {
                EquipmentSlotGroup equipmentSlotGroup2 = itemAttributeModifiersComponent$Entry.getEquipmentSlotGroup();
                AttributeModifier attributeModifier = itemAttributeModifiersComponent$Entry.getModifier();
                if (!equipmentSlotGroup2.equals(equipmentSlotGroup)) continue;
                double d2 = attributeModifier.getAmount();
                d += d2;
            }
        } else {
            ItemArmor itemArmor = new ItemArmor(item);
            d = itemArmor.S();
        }
        return d;
    }

    public static double L(ItemStack itemStack) {
        int n;
        int n2;
        if (itemStack.isNull()) {
            return 0.0;
        }
        Item item = itemStack.getItem();
        if (!ItemStackScoreUtil.R(item)) {
            return 0.0;
        }
        double d = ItemStackScoreUtil.I(itemStack);
        int n3 = EnchantmentHelper.q(Enchantment.featherFalling().getId(), itemStack);
        if (n3 > 0) {
            d += (double)n3 * 0.1;
        }
        if ((n2 = EnchantmentHelper.q(Enchantment.fireProtection().getId(), itemStack)) > 0) {
            d += (double)n2 * 0.1;
        }
        if ((n = EnchantmentHelper.q(Enchantment.blastProtection().getId(), itemStack)) > 0) {
            d += (double)n * 0.1;
        }
        return d;
    }

    public static double I(ItemStack itemStack) {
        if (itemStack.isNull()) {
            return 0.0;
        }
        Item item = itemStack.getItem();
        if (!ItemStackScoreUtil.R(item)) {
            return 0.0;
        }
        double d = ItemStackScoreUtil.d(itemStack);
        double d2 = ItemStackScoreUtil.e(itemStack);
        return d + d2;
    }

    public static boolean g(Item item) {
        if (ForgeVersion.MC_1_21_4.d()) {
            String string = item.A();
            if (string == null || string.isEmpty()) {
                return false;
            }
            return string.contains("_shovel");
        }
        return item.isInstance(MappedClasses.FM);
    }

    public static boolean T$src$Z$2fnsig(ItemStack itemStack) {
        if (itemStack.isNull() || itemStack.getItem().isNull()) {
            return false;
        }
        if (!ItemStackScoreUtil.l(itemStack)) {
            return false;
        }
        if (ForgeVersion.MC_1_21_4.d()) {
            Equippable equippable;
            ResourceKey resourceKey;
            DataComponentMap dataComponentMap = itemStack.getItem().g();
            Object object = dataComponentMap.E(DataComponents.k());
            if (object != null && (resourceKey = (equippable = new Equippable(object)).j()) != null && resourceKey.equals(ResourceKeyHolder.gold())) {
                return true;
            }
        } else {
            ItemArmor itemArmor = new ItemArmor(itemStack.getItem());
            if (ArmorMaterial.gold().equals(itemArmor.Y$src$Lgg_vape_wrapper_impl_ArmorMaterial_$11f9rp6())) {
                return true;
            }
        }
        return false;
    }

    public static boolean I(Item item) {
        if (ForgeVersion.MC_1_21_4.d()) {
            DataComponentMap dataComponentMap = item.g();
            Object object = dataComponentMap.E(DataComponents.F());
            return object != null;
        }
        return item.isInstance(MappedClasses.Ye);
    }

    private static void w() {
        for (Object e : Item.Y().D()) {
            Wrapper wrapper;
            Object object;
            if (ForgeVersion.MC_1_7_10.L()) {
                object = Item.Y().S(e);
            } else {
                wrapper = new ResourceLocation(e);
                object = Item.Y().S(wrapper.getObject());
            }
            if (!MappedClasses.lb.isInstance(object)) continue;
            wrapper = new Item(object);
            List list = new ArrayList();
            if (ForgeVersion.MC_1_12_2.d()) {
                list = (List)NonNullList.create().getObject();
            }
            ((Item)wrapper).D((Item)wrapper, list);
            if (list.isEmpty()) {
                if (!ForgeVersion.MC_1_7_10.Y()) continue;
                w.add(((Item)wrapper).b());
                continue;
            }
            for (Object e2 : list) {
                ItemStack itemStack = new ItemStack(e2);
                if (itemStack.getItem().P() == 62) continue;
                w.add(itemStack);
            }
        }
        w.sort(ItemStackScoreUtil::lambda$populateItems$0);
    }

    public static boolean k(Item item) {
        if (ForgeVersion.MC_1_21_4.d()) {
            String string = item.A();
            if (string == null || string.isEmpty()) {
                return false;
            }
            return string.contains("_hoe");
        }
        return item.isInstance(MappedClasses.Ff);
    }

    public static double g(ItemStack itemStack) {
        double d = ItemStackScoreUtil.p(itemStack);
        if (d == 0.0) {
            return d;
        }
        if (!ItemStackScoreUtil.g(itemStack.getItem())) {
            return d;
        }
        int n = EnchantmentHelper.q(Enchantment.efficiency().getId(), itemStack);
        n = n > 0 ? n * n + 1 : 0;
        d += (double)n;
        d += 0.1 * (double)Math.max(0, EnchantmentHelper.q(Enchantment.unbreaking().getId(), itemStack));
        d += 0.1 * (double)Math.max(0, EnchantmentHelper.q(Enchantment.fortune().getId(), itemStack));
        return d += 0.1 * (double)Math.max(0, EnchantmentHelper.q(Enchantment.mending().getId(), itemStack));
    }

    public static double e(ItemStack itemStack) {
        if (ForgeVersion.MC_1_12_2.v()) {
            return 0.0;
        }
        if (itemStack.isNull()) {
            return 0.0;
        }
        Item item = itemStack.getItem();
        if (!ItemStackScoreUtil.R(item)) {
            return 0.0;
        }
        double d = 0.0;
        if (ForgeVersion.MC_1_21_4.d()) {
            DataComponentMap dataComponentMap = item.g();
            Object object = dataComponentMap.E(DataComponents.E());
            ItemAttributeModifiersComponent itemAttributeModifiersComponent = new ItemAttributeModifiersComponent(object);
            EquipmentSlotGroup equipmentSlotGroup = MonsterAttributesBridge.H();
            List<ItemAttributeModifiersComponent$Entry> list = itemAttributeModifiersComponent.getEntries();
            for (ItemAttributeModifiersComponent$Entry itemAttributeModifiersComponent$Entry : list) {
                EquipmentSlotGroup equipmentSlotGroup2 = itemAttributeModifiersComponent$Entry.getEquipmentSlotGroup();
                AttributeModifier attributeModifier = itemAttributeModifiersComponent$Entry.getModifier();
                if (!equipmentSlotGroup2.equals(equipmentSlotGroup)) continue;
                double d2 = attributeModifier.getAmount();
                d += d2;
            }
        } else {
            ItemArmor itemArmor = new ItemArmor(item);
            d = itemArmor.p$src$F$1fue3zl();
        }
        return d;
    }

    public static boolean Y(ItemStack itemStack) {
        return ItemStackScoreUtil.h(itemStack.getItem());
    }

    public static boolean K(ItemStack itemStack) {
        if (itemStack.isNull() || itemStack.getItem().isNull()) {
            return false;
        }
        if (!ItemStackScoreUtil.I(itemStack.getItem())) {
            return false;
        }
        if (ForgeVersion.MC_1_21_4.d()) {
            DataComponentMap components = itemStack.getItem().g();
            Object repairableHandle = components.E(DataComponents.X());
            if (repairableHandle == null) {
                return false;
            }
            ItemCameraTransformType repairable = new ItemCameraTransformType(repairableHandle);
            ItemCameraTransformBase items = repairable.getItems();
            if (!items.isInstance(MappedClasses.zo)) {
                return false;
            }
            ItemCameraTransformLeaf namedItems = new ItemCameraTransformLeaf(items);
            ItemCameraTransformSubtypeValue itemTagKey = namedItems.getKey();
            return itemTagKey.equals(ChestTypeHolder.goldToolMaterials());
        }
        ItemSword itemSword = new ItemSword(itemStack.getItem());
        return itemSword.s().equals(ToolMaterial.S());
    }

    public static double f(ItemStack itemStack) {
        double d = 2.0;
        int n = EnchantmentHelper.q(Enchantment.power().getId(), itemStack);
        if (n > 0) {
            d += (double)n * 0.5 + 0.5;
        }
        return d;
    }

    public static double p(ItemStack itemStack) {
        if (itemStack.isNull() || itemStack.getItem().isNull() || !ItemStackScoreUtil.I(itemStack.getItem())) {
            return 0.0;
        }
        if (ForgeVersion.MC_1_21_4.d()) {
            return ItemStackScoreUtil.a$src$F$2aw1mh(itemStack);
        }
        return new ItemSword(itemStack.getItem().getObject()).Q$src$F$vp4c40();
    }

    public static double O(ItemStack itemStack) {
        double d = ItemStackScoreUtil.f(itemStack);
        d += 0.1 * (double)Math.max(0, EnchantmentHelper.q(Enchantment.punch().getId(), itemStack));
        d += 0.1 * (double)Math.max(0, EnchantmentHelper.q(Enchantment.flame().getId(), itemStack));
        d += (double)(1 * Math.max(0, EnchantmentHelper.q(Enchantment.infinity().getId(), itemStack)));
        d += 0.1 * (double)Math.max(0, EnchantmentHelper.q(Enchantment.mending().getId(), itemStack));
        return d += 0.1 * (double)Math.max(0, EnchantmentHelper.q(Enchantment.unbreaking().getId(), itemStack));
    }

    public static boolean Z(Item item) {
        return item.isInstance(MappedClasses.Vb) || item.isInstance(MappedClasses.YH) || item.isInstance(MappedClasses.ZH) || item.isInstance(MappedClasses.Y5) || item.isInstance(MappedClasses.YJ);
    }
}
