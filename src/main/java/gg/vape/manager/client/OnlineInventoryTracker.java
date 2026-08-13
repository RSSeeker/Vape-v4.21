package gg.vape.manager.client;

import gg.vape.friend.OnlineFriendActivityState;
import gg.vape.friend.activity.ActivityItemStack;
import gg.vape.friend.activity.ActivityItemStackPayload;
import gg.vape.manager.client.OnlineConnectionManager;
import gg.vape.protocol.ZeusConnectionManager;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.ItemStack;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.Nullable;

public class OnlineInventoryTracker {
    private final ActivityItemStack[] armor = new ActivityItemStack[4];
    private int selectedHotbarSlot;
    private int unchangedTickCount;
    private boolean initialized;
    private static boolean obfuscationState;
    private final ActivityItemStack[] inventory = new ActivityItemStack[36];

    public ActivityItemStack[] getInventory() {
        return this.inventory;
    }

    public Map<Integer, @Nullable ActivityItemStack> collectChanges(EntityPlayer player, boolean scanFullInventory) {
        if (!this.initialized) {
            this.initialized = true;
        }
        HashMap<Integer, @Nullable ActivityItemStack> changes = new HashMap<Integer, ActivityItemStack>();
        Object[] armorContents = player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().i();
        for (int armorSlot = 0; armorSlot < armorContents.length; ++armorSlot) {
            ActivityItemStack previousStack = this.armor[armorSlot];
            ActivityItemStack updatedStack = OnlineFriendActivityState.fromItemStack(new ItemStack(armorContents[armorSlot]));
            if (previousStack == null && updatedStack == null || updatedStack != null && updatedStack.equals(previousStack)) continue;
            this.armor[armorSlot] = updatedStack;
            changes.put(36 + armorSlot, updatedStack);
        }
        boolean limitToHeldItem = !OnlineConnectionManager.INSTANCE.getSettings().getShareInventory().getEffectiveValue();
        int currentHotbarSlot = player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().v();
        this.selectedHotbarSlot = currentHotbarSlot;
        if (scanFullInventory && !limitToHeldItem) {
            Object[] inventoryContents = player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().M();
            for (int inventorySlot = 0; inventorySlot < inventoryContents.length; ++inventorySlot) {
                ActivityItemStack previousStack = this.inventory[inventorySlot];
                ActivityItemStack updatedStack = OnlineFriendActivityState.fromItemStack(new ItemStack(inventoryContents[inventorySlot]));
                if (previousStack == null && updatedStack == null || updatedStack != null && updatedStack.equals(previousStack)) continue;
                changes.put(inventorySlot, updatedStack);
                this.inventory[inventorySlot] = updatedStack;
            }
        } else {
            if (limitToHeldItem) {
                for (int inventorySlot = 0; inventorySlot < this.inventory.length; ++inventorySlot) {
                    ActivityItemStack previousStack = this.inventory[inventorySlot];
                    if (inventorySlot == currentHotbarSlot || previousStack == null) continue;
                    this.inventory[inventorySlot] = null;
                    changes.put(inventorySlot, null);
                }
            }
            ActivityItemStack previousHeldStack = this.inventory[currentHotbarSlot];
            ActivityItemStack updatedHeldStack = OnlineFriendActivityState.fromItemStack(player.B$src$Lgg_vape_wrapper_impl_ItemStack_$impdvt());
            if (!(previousHeldStack == null && updatedHeldStack == null || updatedHeldStack != null && updatedHeldStack.equals(previousHeldStack))) {
                changes.put(currentHotbarSlot, updatedHeldStack);
                this.inventory[currentHotbarSlot] = updatedHeldStack;
            }
        }
        return changes;
    }

    public void sendChanges(Map<Integer, ActivityItemStack> changes) {
        HashMap<Integer, ActivityItemStackPayload> payloads = new HashMap<Integer, ActivityItemStackPayload>();
        for (Map.Entry<Integer, ActivityItemStack> entry : changes.entrySet()) {
            payloads.put(entry.getKey(), entry.getValue() != null ? entry.getValue().toPayload() : null);
        }
        ZeusConnectionManager.T().u().P(payloads);
    }

    public static void setObfuscationState(boolean state) {
        obfuscationState = state;
    }


    public void reset() {
        this.initialized = false;
        this.unchangedTickCount = 0;
        Arrays.fill(this.armor, null);
        Arrays.fill(this.inventory, null);
    }

    public static boolean isObfuscationStateUnset() {
        boolean state = OnlineInventoryTracker.getObfuscationState();
        return !state;
    }

    public boolean isInitialized() {
        return this.initialized;
    }

    static {
        if (OnlineInventoryTracker.isObfuscationStateUnset()) {
            OnlineInventoryTracker.setObfuscationState(true);
        }
    }

    public void setUnchangedTickCount(int unchangedTickCount) {
        this.unchangedTickCount = unchangedTickCount;
    }

    public ActivityItemStack[] getArmor() {
        return this.armor;
    }

    public static boolean getObfuscationState() {
        return obfuscationState;
    }

    public void sendSnapshot() {
        HashMap<Integer, @Nullable ActivityItemStackPayload> payloads = new HashMap<Integer, ActivityItemStackPayload>();
        for (int armorSlot = 0; armorSlot < this.armor.length; ++armorSlot) {
            ActivityItemStack stack = this.armor[armorSlot];
            if (stack == null) continue;
            payloads.put(36 + armorSlot, stack.toPayload());
        }
        for (int inventorySlot = 0; inventorySlot < this.inventory.length; ++inventorySlot) {
            ActivityItemStack stack = this.inventory[inventorySlot];
            if (stack == null) continue;
            payloads.put(inventorySlot, stack.toPayload());
        }
        ZeusConnectionManager.T().u().N(this.selectedHotbarSlot, payloads);
    }

    public int getUnchangedTickCount() {
        return this.unchangedTickCount;
    }

    public int getSelectedHotbarSlot() {
        return this.selectedHotbarSlot;
    }
}
