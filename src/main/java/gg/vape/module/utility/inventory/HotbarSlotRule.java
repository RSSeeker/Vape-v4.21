package gg.vape.module.utility.inventory;

import com.google.gson.JsonObject;
import gg.vape.mapping.MappedClasses;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemSplashPotion;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.PotionEffect;
import java.util.List;

public class HotbarSlotRule {
    private boolean anyMetadata;
    private boolean onlyBest;
    private boolean anyMaterial;
    private int metadata = 0;
    private int itemId;

    public int getItemId() {
        return this.itemId;
    }

    public void loadJson(JsonObject jsonObject) {
        if (jsonObject.get("item-id") != null) {
            this.itemId = jsonObject.get("item-id").getAsInt();
        }
        if (jsonObject.get("meta") != null) {
            this.metadata = jsonObject.get("meta").getAsInt();
        }
        if (jsonObject.get("anyMeta") != null) {
            this.anyMetadata = jsonObject.get("anyMeta").getAsBoolean();
        }
        if (jsonObject.get("anyMaterial") != null) {
            this.anyMaterial = jsonObject.get("anyMaterial").getAsBoolean();
        }
        if (jsonObject.get("onlyBest") != null) {
            this.onlyBest = jsonObject.get("onlyBest").getAsBoolean();
        }
    }


    public HotbarSlotRule setMetadata(int metadata) {
        this.metadata = metadata;
        return this;
    }

    public boolean isOnlyBest() {
        return this.onlyBest;
    }

    public static HotbarSlotRule fromItemStack(ItemStack itemStack) {
        if (itemStack.isNull()) {
            return new HotbarSlotRule(0);
        }
        HotbarSlotRule hotbarSlotRule = new HotbarSlotRule(itemStack.getItem().P());
        hotbarSlotRule.setMetadata(itemStack.L());
        return hotbarSlotRule;
    }

    public boolean matches(ItemStack itemStack) {
        if (itemStack.isNotNull()) {
            Item item = itemStack.getItem();
            if (item.P() == this.getItemId()) {
                if (this.anyMetadata || itemStack.L() == this.metadata) {
                    return true;
                }
                if (item.isInstance(MappedClasses.Di)) {
                    ItemSplashPotion configuredPotion = new ItemSplashPotion(this.getItem());
                    ItemSplashPotion candidatePotion = new ItemSplashPotion(itemStack.getItem());
                    ItemStack configuredStack = ItemStack.S(configuredPotion);
                    configuredStack.s(this.metadata);
                    List<PotionEffect> configuredEffects = configuredPotion.getPotionEffects(configuredStack);
                    List<PotionEffect> candidateEffects = candidatePotion.getPotionEffects(itemStack);
                    if (Boolean.compare(ItemSplashPotion.isSplashPotion(configuredStack), ItemSplashPotion.isSplashPotion(itemStack)) == 0 && !configuredEffects.isEmpty()) {
                        PotionEffect configuredEffect = configuredEffects.get(0);
                        for (PotionEffect candidateEffect : candidateEffects) {
                            if (candidateEffect.C() != configuredEffect.C()) continue;
                            return true;
                        }
                    }
                }
            }
            if (this.anyMaterial) {
                return item.getObject().getClass().equals(this.getItem().getObject().getClass());
            }
        }
        return false;
    }

    public ItemStack createItemStack() {
        Item item = Item.T(this.itemId);
        if (item.isNull()) {
            return null;
        }
        ItemStack itemStack = ItemStack.S(item);
        if (itemStack.isNotNull()) {
            itemStack.s(this.getMetadata());
        }
        return itemStack;
    }

    public Item getItem() {
        return Item.T(this.getItemId());
    }

    public int getMetadata() {
        return this.metadata;
    }

    public HotbarSlotRule(int itemId) {
        this.itemId = itemId;
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("item-id", (Number)this.itemId);
        jsonObject.addProperty("meta", (Number)this.metadata);
        jsonObject.addProperty("anyMeta", Boolean.valueOf(this.anyMetadata));
        jsonObject.addProperty("anyMaterial", Boolean.valueOf(this.anyMaterial));
        jsonObject.addProperty("onlyBest", Boolean.valueOf(this.onlyBest));
        return jsonObject;
    }
}

