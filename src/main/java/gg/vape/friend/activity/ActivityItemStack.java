package gg.vape.friend.activity;

import gg.vape.friend.activity.ActivityItemStackPayload;
import gg.vape.utils.EnchantmentUtil;
import gg.vape.wrapper.impl.Enchantment;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemStack;
import java.util.Map;
import org.jetbrains.annotations.Nullable;

public class ActivityItemStack {
    private final int count;
    private final Map<Short, Short> enchantments;
    private final int itemId;
    private final int metadata;

    public ActivityItemStack(int itemId, int count, int metadata, Map<Short, Short> enchantments) {
        this.itemId = itemId;
        this.count = count;
        this.metadata = metadata;
        this.enchantments = enchantments;
    }

    public boolean hasEnchantments() {
        boolean hasEnchantments = !this.enchantments.isEmpty();
        return hasEnchantments;
    }


    public int getItemId() {
        return this.itemId;
    }

    public int getMetadata() {
        return this.metadata;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }
        ActivityItemStack activityItemStack = (ActivityItemStack)other;
        if (this.itemId != activityItemStack.itemId) {
            return false;
        }
        if (this.count != activityItemStack.count) {
            return false;
        }
        if (this.metadata != activityItemStack.metadata) {
            return false;
        }
        boolean equalEnchantments = this.enchantments.size() == activityItemStack.enchantments.size() && this.enchantments.equals(activityItemStack.enchantments);
        return equalEnchantments;
    }

    public int getCount() {
        return this.count;
    }

    public Map<Short, Short> getEnchantments() {
        return this.enchantments;
    }

    public ActivityItemStackPayload toPayload() {
        return new ActivityItemStackPayload(this.itemId, this.count, this.metadata, this.enchantments);
    }

    public int hashCode() {
        int result = this.itemId;
        result = 31 * result + this.count;
        result = 31 * result + this.metadata;
        result = 31 * result + this.enchantments.hashCode();
        return result;
    }

    @Nullable
    public static ActivityItemStack fromPayload(@Nullable ActivityItemStackPayload activityItemStackPayload) {
        if (activityItemStackPayload == null || activityItemStackPayload.getItemId() == 0) {
            return null;
        }
        return new ActivityItemStack(activityItemStackPayload.getItemId(), activityItemStackPayload.getCount(), activityItemStackPayload.getMetadata(), activityItemStackPayload.getEnchantments());
    }

    @Nullable
    public ItemStack toItemStack() {
        if (this.itemId == 0) {
            return null;
        }
        Item item = Item.T(this.itemId);
        if (item.isNotNull()) {
            ItemStack itemStack = ItemStack.S(item);
            itemStack.s(this.metadata);
            itemStack.Y(this.count);
            if (!this.enchantments.isEmpty()) {
                for (Map.Entry<Short, Short> entry : this.enchantments.entrySet()) {
                    Enchantment enchantment = EnchantmentUtil.k(entry.getKey());
                    if (enchantment == null) continue;
                    itemStack.v(enchantment, entry.getValue().shortValue());
                }
            }
            return itemStack;
        }
        return null;
    }
}

