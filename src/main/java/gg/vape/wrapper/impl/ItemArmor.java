package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MItemArmor;

@Deprecated
public class ItemArmor
extends Item {
    public float p$src$F$1fue3zl() {
        return MItemArmor.e(ItemArmor.vapeInstance.getMappings().DZ, this.getObject());
    }

    public ItemArmor(Object object) {
        super(object);
        if (ForgeVersion.MC_1_21_6.d()) {
            throw new UnsupportedOperationException("Unsupported");
        }
    }

    private static UnsupportedOperationException a(UnsupportedOperationException unsupportedOperationException) {
        return unsupportedOperationException;
    }

    public int Y(ItemStack itemStack) {
        if (ForgeVersion.MC_1_20_6.d()) {
            throw new UnsupportedOperationException("Unimplemented");
        }
        return MItemArmor.w(ItemArmor.vapeInstance.getMappings().DZ, this.I, itemStack.getObject());
    }

    public ArmorMaterial Y$src$Lgg_vape_wrapper_impl_ArmorMaterial_$11f9rp6() {
        if (ForgeVersion.MC_1_21_0.d()) {
            throw new UnsupportedOperationException("Unimplemented");
        }
        return new ArmorMaterial(MItemArmor.o(ItemArmor.vapeInstance.getMappings().DZ, this.I));
    }

    public int S() {
        if (ForgeVersion.MC_1_21_4.d()) {
            throw new UnsupportedOperationException("Unsupported");
        }
        return MItemArmor.q(ItemArmor.vapeInstance.getMappings().DZ, this.I);
    }

    public int b$src$I$1fmozr4() {
        if (ForgeVersion.MC_1_20_6.d()) {
            EntityEquipmentSlot entityEquipmentSlot = new EntityEquipmentSlotHolder(MItemArmor.D(ItemArmor.vapeInstance.getMappings().DZ, this.I)).getSlot();
            return entityEquipmentSlot.W();
        }
        if (ForgeVersion.MC_1_12_2.d()) {
            EntityEquipmentSlot entityEquipmentSlot = new EntityEquipmentSlot(MItemArmor.D(ItemArmor.vapeInstance.getMappings().DZ, this.I));
            return entityEquipmentSlot.W();
        }
        return MItemArmor.F(ItemArmor.vapeInstance.getMappings().DZ, this.I);
    }
}
