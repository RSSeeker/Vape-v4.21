package gg.vape.module.utility.inventory.cleaner;

import com.google.gson.JsonObject;
import gg.vape.module.utility.inventory.cleaner.AttackDamageFilterCondition;
import gg.vape.module.utility.inventory.cleaner.DisplayNameFilterCondition;
import gg.vape.module.utility.inventory.cleaner.EnchantmentFilterCondition;
import gg.vape.module.utility.inventory.cleaner.InventoryFilterCondition;
import gg.vape.module.utility.inventory.cleaner.ItemDurabilityFilterCondition;
import gg.vape.module.utility.inventory.cleaner.ItemNameFilterCondition;
import gg.vape.module.utility.inventory.cleaner.LoreFilterCondition;
import gg.vape.module.utility.inventory.cleaner.MaterialFilterCondition;
import gg.vape.module.utility.inventory.cleaner.PotionEffectFilterCondition;
import gg.vape.module.utility.inventory.cleaner.QuantityFilterCondition;
import gg.vape.unmap.INamed;
import java.util.function.Function;
import java.util.function.Supplier;
import gg.vape.module.utility.inventory.cleaner.DescribedOption;

public enum InventoryFilterConditionType
implements INamed,
DescribedOption {
    ITEM_DURABILITY("Durability", "The amount of durability the item has remaining", ItemDurabilityFilterCondition::new, ItemDurabilityFilterCondition::new),
    ATTACK_DAMAGE("Attack Damage", "The amount of attack damage the item has", AttackDamageFilterCondition::new, AttackDamageFilterCondition::new),
    ENCHANTMENT("Enchantment", "The enchantment the item has", EnchantmentFilterCondition::new, EnchantmentFilterCondition::new),
    POTION_EFFECT("Potion Effect", "The potion effect the item has", PotionEffectFilterCondition::new, PotionEffectFilterCondition::new),
    QUANTITY("Quantity", "The item stack size", QuantityFilterCondition::new, QuantityFilterCondition::new),
    DISPLAY_NAME("Display Name", "The display name of the item, such as \"John's Sword\"", DisplayNameFilterCondition::new, DisplayNameFilterCondition::new),
    ITEM_NAME("Item Name", "The name of the item, such as \"Diamond Sword\"", ItemNameFilterCondition::new, ItemNameFilterCondition::new),
    LORE("Lore", "The lore of the item", LoreFilterCondition::new, LoreFilterCondition::new),
    MATERIAL("Material", "The material of the item", MaterialFilterCondition::new, MaterialFilterCondition::new);

    private final String name;
    private final Supplier<? extends InventoryFilterCondition> factory;
    private final String description;
    private final Function<JsonObject, ? extends InventoryFilterCondition> jsonFactory;

    private InventoryFilterConditionType(String name, String description, Supplier<? extends InventoryFilterCondition> factory, Function<JsonObject, ? extends InventoryFilterCondition> jsonFactory) {
        this.name = name;
        this.description = description;
        this.factory = factory;
        this.jsonFactory = jsonFactory;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public Function<JsonObject, ? extends InventoryFilterCondition> getJsonFactory() {
        return this.jsonFactory;
    }

    public Supplier<? extends InventoryFilterCondition> getFactory() {
        return this.factory;
    }
}
