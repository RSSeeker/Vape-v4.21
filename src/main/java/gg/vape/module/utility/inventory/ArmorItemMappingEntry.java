package gg.vape.module.utility.inventory;

import gg.vape.mapping.ItemMappingEntry;
import gg.vape.module.utility.armorswitch.ArmorMaterialType;
import org.jetbrains.annotations.Nullable;

/** Item mapping enriched with the armor material resolved for the item. */
public class ArmorItemMappingEntry extends ItemMappingEntry {
    private final ArmorMaterialType armorMaterial;

    public ArmorItemMappingEntry(ItemMappingEntry entry, ArmorMaterialType armorMaterial) {
        this(entry.getResourceKey(), entry.getModernId(), entry.getLegacyIdString(), armorMaterial);
    }

    public ArmorItemMappingEntry(String name, String legacyName, @Nullable String modernName,
                                 ArmorMaterialType armorMaterial) {
        super(name, legacyName, modernName);
        this.armorMaterial = armorMaterial;
    }

    public ArmorMaterialType getArmorMaterial() {
        return this.armorMaterial;
    }
}
