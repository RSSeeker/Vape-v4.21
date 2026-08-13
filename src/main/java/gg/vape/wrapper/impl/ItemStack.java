package gg.vape.wrapper.impl;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.mappings.MItemStack;
import gg.vape.wrapper.Wrapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;

public class ItemStack
extends Wrapper {
    private static int[] f;

    public String x() {
        if (ForgeVersion.MC_1_16_5.d()) {
            try {
                ITextComponent iTextComponent = new ITextComponent(MItemStack.c(ItemStack.vapeInstance.getMappings().q8, this.I));
                return iTextComponent.getFormattedText();
            }
            catch (Throwable throwable) {
                Item item = this.getItem();
                return item.isNull() ? "" : item.Y$src$Ljava_lang_String_$uel3xb();
            }
        }
        try {
            return MItemStack.k(ItemStack.vapeInstance.getMappings().q8, this.I);
        }
        catch (Exception exception) {
            return "";
        }
    }

    public boolean W(ItemStack itemStack) {
        if (ForgeVersion.MC_1_17.d()) {
            return ItemStack.vapeInstance.getMappings().q8.e(this.I, itemStack.getItem().getObject());
        }
        return ItemStack.vapeInstance.getMappings().q8.e(this.I, itemStack.getObject());
    }

    public void i(Object object) {
        ItemStack.vapeInstance.getMappings().q8.D(this.I, object);
    }

    public boolean W(DataComponentTypeBridge dataComponentTypeBridge) {
        if (ForgeVersion.MC_1_21_4.d()) {
            return ItemStack.vapeInstance.getMappings().q8.Y(this.getObject(), dataComponentTypeBridge.getObject());
        }
        return true;
    }

    public static ItemStack S(Item item) {
        if (item == null || item.isNull()) {
            return new ItemStack(null);
        }
        if (ForgeVersion.MC_26_1.d()) {
            try {
                Holder holder = item.Q();
                if (holder.isNotNull()) {
                    return new ItemStack(ItemStack.vapeInstance.getMappings().q8.O(
                            holder.getObject(), 1));
                }
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            return new ItemStack(null);
        }
        return new ItemStack(ItemStack.vapeInstance.getMappings().q8.p(item.getObject()));
    }

    @Override
    public boolean isNull() {
        if (super.isNull()) {
            return true;
        }
        if (ForgeVersion.MC_1_21_11.d()) {
            return ItemStack.vapeInstance.getMappings().q8.j(this.getObject());
        }
        if (this.toString().contains("tile.air")) {
            return true;
        }
        if (this.toString().contains("minecraft:air")) {
            return true;
        }
        String string = this.x();
        return string.isEmpty() || string.equals("Air");
    }

    public void s(int n) {
        MItemStack.b(ItemStack.vapeInstance.getMappings().q8, this.I, n);
    }

    public void Y(int n) {
        ItemStack.vapeInstance.getMappings().q8.k(this.I, n);
    }

    public Object l() {
        return ItemStack.vapeInstance.getMappings().q8.r(this.I);
    }

    public ItemStack(Object object) {
        super(object);
    }

    public static ItemStack G(Item item) {
        if (item == null || item.isNull()) {
            return new ItemStack(null);
        }
        if (ForgeVersion.MC_26_1.d()) {
            try {
                Holder holder = item.Q();
                if (holder.isNotNull()) {
                    return new ItemStack(ItemStack.vapeInstance.getMappings().q8.O(holder.getObject(), 1));
                }
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        return ItemStack.S(item);
    }

    public Object A(EntityPlayer entityPlayer, Object object) {
        if (ForgeVersion.MC_1_8_9.Y()) {
            return MItemStack.g(ItemStack.vapeInstance.getMappings().q8, this.I, entityPlayer.getObject(), object);
        }
        return MItemStack.X(ItemStack.vapeInstance.getMappings().q8, this.I, entityPlayer.getObject(), (Boolean)object);
    }

    public ItemStack k() {
        return new ItemStack(MItemStack.J(ItemStack.vapeInstance.getMappings().q8, this.I));
    }

    public boolean r() {
        if (ForgeVersion.MC_26_1.d()) {
            return this.isNull() || ItemStack.vapeInstance.getMappings().q8.j(this.getObject());
        }
        if (this.isNull()) {
            return true;
        }
        if (this.t() <= 0) {
            return true;
        }
        Item item = this.getItem();
        if (item.isNull()) {
            return true;
        }
        if (ForgeVersion.MC_1_12_2.v()) {
            if (this.isNull()) {
                return true;
            }
            return item.P() == 0;
        }
        return false;
    }

    public static int[] Q() {
        return f;
    }

    public void v(Enchantment enchantment, int n) {
        MItemStack.r(ItemStack.vapeInstance.getMappings().q8, this.I, enchantment.getObject(), n);
    }

    public ArrayList<Enchantment> p() {
        PotionVersionRange potionVersionRange = EnchantmentHelper.m(this);
        Iterator iterator = potionVersionRange.entrySet().iterator();
        ArrayList<Enchantment> arrayList = new ArrayList<Enchantment>();
        while (iterator.hasNext()) {
            Holder holder = new Holder(new ObjectToIntMapEntry(iterator.next()).getKey());
            arrayList.add(new Enchantment(holder.N()));
        }
        return arrayList;
    }

    public static void C(int[] nArray) {
        f = nArray;
    }

    public int y() {
        return MItemStack.A(ItemStack.vapeInstance.getMappings().q8, this.I);
    }

    public ItemAttributeModifiers o() {
        if (ForgeVersion.MC_1_20_6.d()) {
            EmptyItemAttributeModifiers emptyItemAttributeModifiers = EmptyItemAttributeModifiers.create();
            this.k(EntityEquipmentSlot.L(), emptyItemAttributeModifiers::put);
            return emptyItemAttributeModifiers;
        }
        if (ForgeVersion.MC_1_12_2.d()) {
            return new ItemAttributeModifiers(MItemStack.g(ItemStack.vapeInstance.getMappings().q8, this.I, EntityEquipmentSlot.L().getObject()));
        }
        return new ItemAttributeModifiers(MItemStack.A$src$Ljava_lang_Object_$2ove6c(ItemStack.vapeInstance.getMappings().q8, this.I));
    }

    public Holder H() {
        if (ForgeVersion.MC_26_1.d()) {
            return new Holder(null);
        }
        return new Holder(MItemStack.M(ItemStack.vapeInstance.getMappings().q8, this.I));
    }

    public float V(int n, int n2, int n3) {
        WorldClient worldClient = Minecraft.theWorld();
        if (ForgeVersion.MC_1_12_2.d()) {
            BlockState blockState = worldClient.getBlockState(BlockPos.create(n, n2, n3));
            return MItemStack.u(ItemStack.vapeInstance.getMappings().q8, this.I, blockState.getObject());
        }
        return MItemStack.u(ItemStack.vapeInstance.getMappings().q8, this.I, worldClient.getBlockByPos(n, n2, n3).getObject());
    }

    static {
        ItemStack.C(null);
    }

    public List<String> z() {
        ArrayList<String> arrayList = new ArrayList<String>();
        TagCompound tagCompound = new TagCompound(this.l());
        if (tagCompound.isNull()) {
            return arrayList;
        }
        TagBase tagBase = tagCompound.getTag("display");
        if (tagBase.isNull() || !tagBase.isInstance(MappedClasses.Yg)) {
            return arrayList;
        }
        TagCompound tagCompound2 = (TagCompound)tagBase;
        TagList tagList = tagCompound2.getTagList("Lore", 8);
        for (int i = 0; i < tagList.tagCount(); ++i) {
            String string = tagList.a(i);
            arrayList.add(string);
        }
        return arrayList;
    }

    public EnumActionResult C(World world, EntityPlayer entityPlayer, EnumHand enumHand) {
        return new EnumActionResult(ItemStack.vapeInstance.getMappings().q8.n(this.getObject(), world.getObject(), entityPlayer.getObject(), enumHand.getObject()));
    }

    public TagList a() {
        return new TagList(MItemStack.H(ItemStack.vapeInstance.getMappings().q8, this.I));
    }

    public void k(EntityEquipmentSlot entityEquipmentSlot, BiConsumer biConsumer) {
        MItemStack.Q(ItemStack.vapeInstance.getMappings().q8, this.I, entityEquipmentSlot.getObject(), biConsumer);
    }

    private static Throwable a(Throwable throwable) {
        return throwable;
    }

    public int P() {
        return MItemStack.l(ItemStack.vapeInstance.getMappings().q8, this.I);
    }

    public Item getItem() {
        return new Item(ItemStack.vapeInstance.getMappings().q8.V(this.I));
    }

    public int L() {
        return MItemStack.v(ItemStack.vapeInstance.getMappings().q8, this.I);
    }

    public Object w(DataComponentType dataComponentType) {
        return MItemStack.A(ItemStack.vapeInstance.getMappings().q8, this.I, dataComponentType.getObject());
    }

    public boolean x$src$Z$1nwfctq() {
        if (ForgeVersion.MC_1_20_6.d()) {
            return EnchantmentHelper.Q(this);
        }
        TagCompound tagCompound = new TagCompound(this.l());
        if (tagCompound.isNull()) {
            return false;
        }
        boolean bl = tagCompound.hasKeyOfType("ench", 9) || tagCompound.hasKeyOfType("Enchantments", 9);
        return bl;
    }

    public int t() {
        return ItemStack.vapeInstance.getMappings().q8.n(this.I);
    }

    public boolean e(ItemStack itemStack) {
        return this.W(itemStack) && this.x().equals(itemStack.x());
    }

    public String f() {
        if (ForgeVersion.MC_1_21_0.d()) {
            return this.getItem().A();
        }
        return ItemStack.vapeInstance.getMappings().q8.F(this.getObject());
    }
}
